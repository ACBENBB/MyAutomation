package io.securitize.tests.ats.queries;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.database.MySqlDatabase;
import io.securitize.tests.ats.pojo.ATSOrder;
import org.testng.Assert;

import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static io.securitize.infra.reporting.MultiReporter.error;


public class ATSOrdersQuery {

    private static final String url = DataManager.getInstance("ats").getAwsProperty("ats_db_ats_order_url");
    private static final String user = DataManager.getInstance("ats").getAwsProperty("ats_db_ats_order_user");
    private static final String password = DataManager.getInstance("ats").getAwsProperty("ats_db_ats_order_password");

    public static ATSOrder findOrderByOrderIdAndReferenceNumber(ATSOrder atsOrder) {
        String query = "SELECT * FROM `order` WHERE `order_id` = ? AND `reference_number` = ? LIMIT 1;";
        try (Connection connection = MySqlDatabase.getConnection("ats", url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, atsOrder.getOrderId());
            preparedStatement.setString(2, atsOrder.getReferenceNumber());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    atsOrder.setFeePercentage(rs.getString("fee_percentage"));
                    atsOrder.setQuantity(rs.getString("quantity"));
                    atsOrder.setPrice(rs.getString("price"));
                }
            }
        } catch (SQLException e) {
            error(e.getMessage());
        }
        return atsOrder;
    }

    public static String findOrderStatusByOrderIdAndReferenceNumber(String orderId, String referenceNumber) {
        String query = "SELECT `status` FROM `order` WHERE `order_id` = ? AND `reference_number` = ? LIMIT 1;";
        try (Connection connection = MySqlDatabase.getConnection("ats", url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, orderId);
            preparedStatement.setString(2, referenceNumber);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                } else {
                    error("The query did not match any order");
                    return null;
                }
            }
        } catch (SQLException e) {
            error(e.getMessage());
            return null;
        }
    }

    public static String findOrderStatusByOrderIdAndReferenceNumber(String orderId, String referenceNumber, String expectedStatus) {
        String query = "SELECT `status` FROM `order` WHERE `order_id` = ? AND `reference_number` = ? LIMIT 1;";

        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(AssertionError.class)
                .withDelay(Duration.ofSeconds(30))
                .withMaxRetries(6)
                .build();

        return Failsafe.with(retryPolicy)
                .get(() -> {
                    try (Connection connection = MySqlDatabase.getConnection("ats", url, user, password);
                         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, orderId);
                        preparedStatement.setString(2, referenceNumber);
                        try (ResultSet rs = preparedStatement.executeQuery()) {
                            if (rs.next()) {
                                String actualStatus = rs.getString("status");
                                Assert.assertEquals(actualStatus, expectedStatus, "The database status is not correct");
                                return actualStatus;
                            } else {
                                error("The query did not match any order");
                                return null;
                            }
                        }
                    } catch (SQLException e) {
                        error(e.getMessage());
                        return null;
                    }
                });
    }


    public static List<ATSOrder> findOrders(String investorId, String status) {
        List<ATSOrder> orders = new ArrayList<>();
        String query = "SELECT `order_id`, `reference_number` FROM `order` WHERE `investor_id` = ? AND `status` = ?;";
        try (Connection connection = MySqlDatabase.getConnection("ats", url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, investorId);
            preparedStatement.setString(2, status);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    String orderId = rs.getString("order_id");
                    String referenceNumber = rs.getString("reference_number");
                    ATSOrder order = new ATSOrder(orderId, referenceNumber);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            error(e.getMessage());
        }
        return orders;
    }

    public static List<ATSOrder> findOrdersBySecurity(String security, String status) {
        List<ATSOrder> orders = new ArrayList<>();
        String query = "SELECT `order_id`, `reference_number` FROM `order` WHERE `security` = ? AND `status` = ?;";
        try (Connection connection = MySqlDatabase.getConnection("ats", url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, security);
            preparedStatement.setString(2, status);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    String orderId = rs.getString("order_id");
                    String referenceNumber = rs.getString("reference_number");
                    ATSOrder order = new ATSOrder(orderId, referenceNumber);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            error(e.getMessage());
        }
        return orders;
    }

    public static ATSOrder findLatestOrder(String security, String side) {
        ATSOrder atsOrder = new ATSOrder();
        String query = "SELECT * FROM `order` WHERE `security` = ? AND `side` = ? ORDER BY `creation_date` DESC LIMIT 1;";
        try (Connection connection = MySqlDatabase.getConnection("ats", url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, security);
            preparedStatement.setString(2, side);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    atsOrder.setOrderId(rs.getString("order_id"));
                    atsOrder.setReferenceNumber(rs.getString("reference_number"));
                    atsOrder.setFeePercentage(rs.getString("fee_percentage"));
                    atsOrder.setQuantity(rs.getString("quantity"));
                    atsOrder.setPrice(rs.getString("price"));
                }
            }
        } catch (SQLException e) {
            error(e.getMessage());
        }
        return atsOrder;
    }

    public static ATSOrder findOrderByOrderId(String orderId) {
        ATSOrder atsOrder = new ATSOrder();
        String query = "SELECT * FROM `order` WHERE `order_id` = ? ORDER BY `creation_date` DESC LIMIT 1;";
        try (Connection connection = MySqlDatabase.getConnection("ats", url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, orderId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    atsOrder.setOrderId(rs.getString("order_id"));
                    atsOrder.setReferenceNumber(rs.getString("reference_number"));
                    atsOrder.setFeePercentage(rs.getString("fee_percentage"));
                    atsOrder.setQuantity(rs.getString("quantity"));
                    atsOrder.setPrice(rs.getString("price"));
                }
            }
        } catch (SQLException e) {
            error(e.getMessage());
        }
        return atsOrder;
    }

}