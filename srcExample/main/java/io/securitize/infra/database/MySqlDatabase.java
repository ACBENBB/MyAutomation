package io.securitize.infra.database;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static io.securitize.infra.reporting.MultiReporter.*;

public class MySqlDatabase {
    private static Map<String, Connection> connections = new HashMap<>();

    private static SessionFactory sessionFactory = null;

    public static synchronized void send(Object objectToSend) {
        SessionFactory sessionFactory = getSessionFactory();
        if (sessionFactory == null) {
            warning("Unable to open sessionFactory to remote MySQL database. Skipping reporting..", false);
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(objectToSend);
            session.flush();
            session.clear();
            session.getTransaction().commit();

            session.detach(objectToSend);
        }
    }

    public static synchronized void executeUpdate(String query, Map<String, Object> params) {
        SessionFactory sessionFactory = getSessionFactory();
        if (sessionFactory == null) {
            errorAndStop("Unable to open sessionFactory to remote MySQL database. Terminating..", false);
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            info("Session to DB created!");
            session.beginTransaction();

            String[] queries = query.split(";");
            for (String currentQuery: queries) {
                NativeQuery nativeQuery = session.createNativeQuery(currentQuery);
                for (String currentParameterKey : params.keySet()) {
                    Object currentParameterValue = params.get(currentParameterKey);
                    if (currentParameterValue.getClass().isArray()) {
                        nativeQuery.setParameterList(currentParameterKey, (Object[]) currentParameterValue);
                    } else {
                        nativeQuery.setParameter(currentParameterKey, currentParameterValue);
                    }
                }
                try {
                    info("Running DB query: " + currentQuery.trim());
                    nativeQuery.executeUpdate();
                    info("Query finished successfully");
                } catch (Exception e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            }
            session.getTransaction().commit();
        }
    }
    public static synchronized <T> List<T> query(String query, String mappingsName, Map<String, Object> params) throws SQLException {
        SessionFactory sessionFactory = getSessionFactory();
        if (sessionFactory == null) {
            errorAndStop("Unable to open sessionFactory to remote MySQL database. Terminating..", false);
            return new ArrayList<>();
        }

        try (Session session = sessionFactory.openSession()) {
            info("Session to DB created!");
            NativeQuery nativeQuery = session.createNativeQuery(query, mappingsName);
            for (String currentParameterKey : params.keySet()) {
                Object currentParameterValue = params.get(currentParameterKey);
                if (currentParameterValue.getClass().isArray()) {
                    nativeQuery.setParameterList(currentParameterKey, (Object[])currentParameterValue);
                } else {
                    nativeQuery.setParameter(currentParameterKey, currentParameterValue);
                }
            }
            info("Running DB query...");
            @SuppressWarnings("unchecked")
            List<T> result = nativeQuery.getResultList();
            info("Query finished with " + result.size() + " results");
            return result;
        }
    }

    private static Properties initializeProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", Users.getProperty(UsersProperty.dashboardMySqlUrl));
        properties.setProperty("hibernate.connection.username", Users.getProperty(UsersProperty.dashboardMySqlUsername));
        properties.setProperty("hibernate.connection.password", Users.getProperty(UsersProperty.dashboardMySqlPassword));
        properties.setProperty("show_sql", "true");
        return properties;
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = initializeProperties();
                Configuration sessionFactoryConfiguration = new Configuration().addProperties(properties);
                sessionFactoryConfiguration.addAnnotatedClass(io.securitize.infra.dashboard.TestStatus.class);
                sessionFactoryConfiguration.addAnnotatedClass(io.securitize.infra.api.anticaptcha.AntiCaptchaResult.class);
                sessionFactory = sessionFactoryConfiguration.buildSessionFactory();
                debug("SessionFactory successfully built to the database!");
            } catch (Exception ex) {
                error("An error occur trying to create session factory for MySql dashboard. Details: " + ex, false);
            }
        }
        return sessionFactory;
    }

    public static Connection getConnection(String name, String url, String user, String password) {
        try {
            if (connections.get(name) == null || connections.get(name).isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connections.put(name, DriverManager.getConnection(url, user, password));
            }
        } catch (ClassNotFoundException | SQLException e) {
            error(e.getMessage());
        }
        return connections.get(name);
    }

    public static Connection closeConnection(String name) {
        try {
            if (connections.get(name) != null && !connections.get(name).isClosed()) {
                connections.get(name).close();
                info("The database connection have been closed");
            }
        } catch (SQLException e) {
            error("An error occurred while closing the connection: " + e.getMessage());
        }
        return connections.get(name);
    }

    public static synchronized void closeAllConnections() {
        try {
            for (Connection connection : connections.values()) {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            }
            connections.clear();
            info("All connections have been closed");
        } catch (SQLException e) {
            error("An error occurred while closing connections: " + e.getMessage());
        }
    }

    @Deprecated
    public void send(String schema, String query) {
        Connection conn = null;
        try {
            String url = Users.getProperty(UsersProperty.databaseMySqlUrl) + "/" + schema;
            conn = DriverManager.getConnection(url,Users.getProperty(UsersProperty.databaseMySqlUsername),Users.getProperty(UsersProperty.databaseMySqlPassword));
            conn.createStatement().executeUpdate(query);
            System.out.println("DB update success");
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Deprecated
    public String sendQuery(String schema, String query) {
        String result = "";
        Connection conn = null;
        try {
            String url = Users.getProperty(UsersProperty.databaseMySqlUrl) + "/" + schema;
            conn = DriverManager.getConnection(url,Users.getProperty(UsersProperty.databaseMySqlUsername),Users.getProperty(UsersProperty.databaseMySqlPassword));
            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while(resultSet.next()) {
                result = resultSet.getString(1);
            }
            System.out.println("DB query success");
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

}