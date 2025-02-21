package io.securitize.infra.utils;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.exceptions.EmailNotFoundException;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public class EmailWrapper {

    private static final long MAX_AGE_OF_EMAILS_TO_READ = 10 * 60 * 1000; // 10 MINUTES
    private static final int EMAIL_POLLING_INTERVAL_SECONDS = 10;
    private static final int MAX_WAIT_TIME_FOR_EMAIL_SECONDS = 180;
    private static final int MAX_INBOX_CONNECT_ATTEMPTS = 5;

    private static Store store = null;
    private static Folder inbox = null;

    private static void connectToInbox() {
        if ((store != null) && (store.isConnected()) && (connectionWorks())) {
            info("No need for a new connection to the inbox as a connection is already open");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, null);

        for (int i = 0; i < MAX_INBOX_CONNECT_ATTEMPTS; i++) {
            // try to cleanup previous connections if such exist
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    debug("Couldn't close existing store connection before reconnecting: " + e);
                }
            }

            if (inbox != null && inbox.isOpen()) {
                try {
                    inbox.close();
                } catch (MessagingException | IllegalStateException e) {
                    debug("Couldn't close existing inbox connection before reconnecting: " + e);
                }
            }

            // try to establish new connections
            try {
                store = session.getStore("imaps");
                infoAndShowMessageInBrowser("Connecting to email server...");
                store.connect("smtp.gmail.com",
                        Users.getProperty(UsersProperty.automationMailUsername),
                        Users.getProperty(UsersProperty.automationMailPassword));
                infoAndShowMessageInBrowser("Connected!");

                inbox = store.getFolder("inbox");
                inbox.open(Folder.READ_WRITE);
                return;

            } catch (MessagingException e) {
                info(String.format("An error occur trying to connect to inbox. Attempt %s/%s. Details: %s", i, MAX_INBOX_CONNECT_ATTEMPTS, e));
            }
        }

        errorAndStop("Couldn't connect to inbox even after " + MAX_INBOX_CONNECT_ATTEMPTS + " attempts. Terminating!", false);
    }

    private static boolean connectionWorks() {
        try {
            if (inbox == null || !inbox.isOpen()) {
                return false;
            }
            inbox.getMessageCount();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

     private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain") || message.isMimeType("text/HTML")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
                //break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                // result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
                result.append(html);
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }

    private static String getLatestEmailContentByRecipientAddress(String recipientEmailAddress, String contentWithRegex) throws MessagingException, IOException {
        connectToInbox();

        int messageCount = inbox.getMessageCount();

        // search backwards - from newest email to oldest
        for (int i = messageCount; i > 0; i--) {
            // only search in emails that are not older than max allowed age
            Date emailTimePlusMaxAgeAllowed = new Date((inbox.getMessage(i).getSentDate().getTime() + MAX_AGE_OF_EMAILS_TO_READ));
            Date now = new Date();
            boolean youngerThanMaxAllowedAge = now.before(emailTimePlusMaxAgeAllowed);
            if (!youngerThanMaxAllowedAge) {
                return null;
            }

            Message currentMessage = inbox.getMessage(i);
            for (Address currentAddress : currentMessage.getAllRecipients()) {
                if (currentAddress.toString().equalsIgnoreCase(recipientEmailAddress)) {
                    info("Found email for recipient " + recipientEmailAddress + " - checking match for regex if needed");
                    String result = getTextFromMessage(currentMessage);
                    currentMessage.setFlag(Flags.Flag.DELETED, true);
                    if ((contentWithRegex == null) || (RegexWrapper.getFirstGroup(result, contentWithRegex) != null)) {
                        inbox.expunge(); // remove all emails marked for deletion
                        return result;
                    } else {
                        info("content of email didn't match regex.. content found:" + result);
                        break;
                    }
                }
            }
        }

        // even if relevant email not found - clear all emails marked for deletion
        inbox.expunge();
        return null;
    }


    public static synchronized String waitAndGetEmailContentByRecipientAddressAndRegex(String recipientAddress, String contentWithRegex) {
        final String[] result = {null};
        Function<String, Boolean> internalGetEmailByRecipientAndContentRegex = t -> {
            try {
                result[0] = getLatestEmailContentByRecipientAddress(recipientAddress, contentWithRegex);
                if (result[0] != null) {
                    reportHTML("Found email for recipient " + recipientAddress + " with content: " + result[0]);
                    infoAndShowMessageInBrowser("Found email for recipient " + recipientAddress + "!");
                    return true;
                }
                return false;
            } catch (MessageRemovedException e) {
                debug("MessageRemovedException exception occur. Closing inbox connection - it will be reopened on next attempt");
                try {
                    inbox.close();
                } catch (MessagingException ee) {
                    debug("Can't close inbox connection. Details: " + ee);
                }
                inbox = null;
                return false;
            } catch (MessagingException | IOException e) {
                info("An error occur trying to read email. Details: " + e);
                return false;
            }
        };

        EmailNotFoundException emailNotFoundException = new EmailNotFoundException(String.format("Email for address %s with regex content of %s not found", recipientAddress, contentWithRegex));
        String description = String.format("Email for address %s to arrive containing content with regex %s", recipientAddress, contentWithRegex);
        Browser.waitForExpressionToEqual(internalGetEmailByRecipientAndContentRegex, null, true, description, MAX_WAIT_TIME_FOR_EMAIL_SECONDS, EMAIL_POLLING_INTERVAL_SECONDS * 1000, emailNotFoundException);

        return result[0];
    }

    //same as waitAndGetEmailContentByRecipientAddressAndRegex but without report call to html line 168
    public static synchronized String getEmailContentByRecipientAddressAndRegex(String recipientAddress, String contentWithRegex) {
        final String[] result = {null};
        Function<String, Boolean> internalGetEmailByRecipientAndContentRegex = t -> {
            try {
                result[0] = getLatestEmailContentByRecipientAddress(recipientAddress, contentWithRegex);
                if (result[0] != null) {
                    infoAndShowMessageInBrowser("Found email for recipient " + recipientAddress + "!");
                    return true;
                }
                return false;
            } catch (MessageRemovedException e) {
                debug("MessageRemovedException exception occur. Closing inbox connection - it will be reopened on next attempt");
                try {
                    inbox.close();
                } catch (MessagingException ee) {
                    debug("Can't close inbox connection. Details: " + ee);
                }
                inbox = null;
                return false;
            } catch (MessagingException | IOException e) {
                info("An error occur trying to read email. Details: " + e);
                return false;
            }
        };

        EmailNotFoundException emailNotFoundException = new EmailNotFoundException(String.format("Email for address %s with regex content of %s not found", recipientAddress, contentWithRegex));
        String description = String.format("Email for address %s to arrive containing content with regex %s", recipientAddress, contentWithRegex);
        Browser.waitForExpressionToEqual(internalGetEmailByRecipientAndContentRegex, null, true, description, MAX_WAIT_TIME_FOR_EMAIL_SECONDS, EMAIL_POLLING_INTERVAL_SECONDS * 1000, emailNotFoundException);

        return result[0];
    }

    public synchronized static void closeAllEmailConnections() {
        if ((store != null) && (store.isConnected())) {
            try {
                inbox.close();
                store.close();
            } catch (MessagingException e) {
                debug("An error occur trying to close email connection. Details: " + e);
            }
        }
    }
}