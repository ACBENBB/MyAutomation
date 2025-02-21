package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep05Questionnaire extends AbstractJpPage {

    private static final ExtendedBy step05Header = new ExtendedBy("Step 05 Head Line", By.xpath("//h1[text()[contains(., 'アンケート')]]"));
    private static final ExtendedBy howYouKnowByMaruiHomePageCheckbox = new ExtendedBy("How You Know This Bond - 丸井グループのHP", By.xpath("//*[@name='question01' and @value='1']"));
    private static final ExtendedBy howYouKnowByNewsReleaseCheckbox = new ExtendedBy("How You Know This Bond - ニュースリリース", By.xpath("//*[@name='question01' and @value='2']"));
    private static final ExtendedBy howYouKnowByEposCardMailCheckbox = new ExtendedBy("How You Know This Bond - エポスカードからのご案内（メール・アプリ）", By.xpath("//*[@name='question01' and @value='3']"));
    private static final ExtendedBy howYouKnowByFriendCheckbox = new ExtendedBy("How You Know This Bond - 友人知人の紹介", By.xpath("//*[@name='question01' and @value='4']"));
    private static final ExtendedBy howYouKnowBySnsCheckbox = new ExtendedBy("How You Know This Bond - SNS", By.xpath("//*[@name='question01' and @value='5']"));
    private static final ExtendedBy howYouKnowByOtherWayCheckbox = new ExtendedBy("How You Know This Bond - その他", By.xpath("//*[@name='question01' and @value='6']"));
    private static final ExtendedBy howYouKnowByOtherWayCommentInputBox  = new ExtendedBy("How You Know This Bond - その他の方コメント(任意)", By.xpath("//*[@name='question02']"));
    private static final ExtendedBy whyYouChooseBecauseAgreeWithIdeaOfMarui = new ExtendedBy("Why You Choose This Bond - 丸井グループの”応援投資”という考え方に共感したから", By.xpath("//*[@name='question03' and @value='1']"));
    private static final ExtendedBy whyYouChooseBecauseInterestIsGood = new ExtendedBy("Why You Choose This Bond - 利率がいいから", By.xpath("//*[@name='question03' and @value='2']"));
    private static final ExtendedBy whyYouChooseBecauseEposPointsGiven = new ExtendedBy("Why You Choose This Bond - エポスポイントがもらえるから", By.xpath("//*[@name='question03' and @value='3']"));
    private static final ExtendedBy whyYouChooseBecauseBonusPointsAdded = new ExtendedBy("Why You Choose This Bond - ボーナスポイント利用額に加算されるから", By.xpath("//*[@name='question03' and @value='4']"));
    private static final ExtendedBy whyYouChooseBecauseReasonablePrice = new ExtendedBy("Why You Choose This Bond - 一口当たりの金額が手頃だったから", By.xpath("//*[@name='question03' and @value='5']"));
    private static final ExtendedBy whyYouChooseBecauseOtherReason = new ExtendedBy("Why You Choose This Bond - その他", By.xpath("//*[@name='question03' and @value='6']"));
    private static final ExtendedBy whyYouChooseBecauseOtherReasonCommentInputBox = new ExtendedBy("Why You Choose This Bond - その他コメント(任意)", By.xpath("//*[@name='question04']"));
    private static final ExtendedBy backButton = new ExtendedBy("Back Button", By.xpath("//button/*[text()[contains(.,'戻る')]]"));
    private static final ExtendedBy nextStepButton = new ExtendedBy("Next Step Button", By.xpath("//button/*[text()[contains(.,'次のステップに進む')]]"));

    public MaruiRegistrationStep05Questionnaire(Browser browser) {
        super(browser, step05Header);
    }

    /**
     * there are questionnaires. they are checkboxes. if choice is "yes", click the checkbox.
     */
    private MaruiRegistrationStep05Questionnaire questionnaire(String choice, ExtendedBy extendedBy) {
        if (choice.equalsIgnoreCase("Yes")) {
            browser.click(extendedBy);
        }
        return this;
    }

    /**
     * there are questionnaires with comment input. they are checkboxes and comment input field.
     * if choice is "yes", click the checkbox. and put comment. comment can be blank.
     */
    public MaruiRegistrationStep05Questionnaire questionnaireAndComment(String choice, String comment, ExtendedBy checkbox, ExtendedBy commentInput) {
        if (choice.equalsIgnoreCase("Yes")) {
            browser.click(checkbox);
            browser.typeTextElement(commentInput, comment);
        }
        return this;
    }

    public MaruiRegistrationStep05Questionnaire howYouKnowThisBondByHomePage(String choice) {
        return questionnaire(choice, howYouKnowByMaruiHomePageCheckbox);
    }

    public MaruiRegistrationStep05Questionnaire howYouKnowThisBondByNewsRelease(String choice) {
        return questionnaire(choice, howYouKnowByNewsReleaseCheckbox);
    }

    public MaruiRegistrationStep05Questionnaire howYouKnowThisBondByEposCardMail(String choice) {
        return questionnaire(choice, howYouKnowByEposCardMailCheckbox);
    }

    public MaruiRegistrationStep05Questionnaire howYouKnowThisBondByFriend(String choice) {
        return questionnaire(choice, howYouKnowByFriendCheckbox);
    }

    public MaruiRegistrationStep05Questionnaire howYouKnowThisBondBySns(String choice) {
        return questionnaire(choice, howYouKnowBySnsCheckbox);
    }

    public MaruiRegistrationStep05Questionnaire howYouKnowThisBondByOtherWay(String choice, String comment) {
        return questionnaireAndComment(choice, comment, howYouKnowByOtherWayCheckbox, howYouKnowByOtherWayCommentInputBox);
    }

    public MaruiRegistrationStep05Questionnaire whyYouChooseThisBondBecauseAgreeWithIdeaOfMarui(String choice) {
        return questionnaire(choice, whyYouChooseBecauseAgreeWithIdeaOfMarui);
    }

    public MaruiRegistrationStep05Questionnaire whyYouChooseThisBondBecauseInterestIsGood(String choice) {
        return questionnaire(choice, whyYouChooseBecauseInterestIsGood);
    }

    public MaruiRegistrationStep05Questionnaire whyYouChooseThisBondBecauseEposPointsGiven(String choice) {
        return questionnaire(choice, whyYouChooseBecauseEposPointsGiven);
    }

    public MaruiRegistrationStep05Questionnaire whyYouChooseThisBondBecauseBonusPointsAdded(String choice) {
        return questionnaire(choice, whyYouChooseBecauseBonusPointsAdded);
    }

    public MaruiRegistrationStep05Questionnaire whyYouChooseThisBondBecauseReasonablePrice(String choice) {
        return questionnaire(choice, whyYouChooseBecauseReasonablePrice);
    }

    public MaruiRegistrationStep05Questionnaire whyYouChooseThisBondBecauseOtherReason(String choice, String comment) {
        return questionnaireAndComment(choice, comment, whyYouChooseBecauseOtherReason, whyYouChooseBecauseOtherReasonCommentInputBox);
    }

    public MaruiRegistrationStep06Confirm clickNextButton() {
        browser.clickAndWaitForElementToVanish(nextStepButton);
        return new MaruiRegistrationStep06Confirm(browser);
    }

    public MaruiRegistrationStep06Confirm clickNextButton(boolean retry) {
        return RetryOnExceptions.retryFunction(this::clickNextButton, ()-> null, retry);
    }

    public boolean backButtonIsEnabled() {
        return browser.isElementEnabled(backButton);
    }

}
