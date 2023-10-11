package com.fragmc;

import com.fragmc.StaffApps.AppCreateClass.Question;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ticket_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "CONEmAltgHw1";
    private Connection connection;

    public DatabaseManager(String s, String root, String conEmAltgHw1) {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    // Check if a user has an active ticket
    public boolean hasActiveTicket(String userId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM tickets WHERE user_id = ? AND isActive = 1")) {
            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Insert a ticket into the database
    public void insertTicket(String userId, String username, int ticketNumber) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tickets (user_id, username, ticket_number, isActive) VALUES (?, ?, ?, 1)")) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setInt(3, ticketNumber);
            preparedStatement.executeUpdate();
        }
    }
    public void closeTicket(String userId) {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tickets SET isActive = 2 WHERE user_id = ?")) {
            preparedStatement.setString(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAnswer(String userId, Map<Question, String> answers) {
        // Your code to save the answers to the database
        // For example, using JDBC or an ORM like Hibernate

        // Assuming you have a SQL query
        String sql = "INSERT INTO staffappanswers (username, answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9, answer10) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Execute the SQL query with the provided parameters
        // (You should use prepared statements to prevent SQL injection)
        // connection, preparedStatement, etc. are placeholders for actual implementations
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_schema", "root", "CONEmAltgHw1");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);

            // Assuming the answers map is ordered
            for (int i = 1; i <= 10; i++) {
                String answer = answers.get(Question.values()[i - 1]);
                preparedStatement.setString(i + 1, answer);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Map<Question, String> getAnswers(String userId) {
        // Your code to retrieve answers from the database
        // For example, using JDBC or an ORM like Hibernate

        // Assuming you have a SQL query
        String sql = "SELECT question, answer FROM staff_applications WHERE user_id = ?";
        Map<Question, String> answers = new HashMap<Question, String>();

        // Execute the SQL query with the provided parameter
        // (You should use prepared statements to prevent SQL injection)
        // connection, preparedStatement, resultSet, etc. are placeholders for actual implementations
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_schema", "root", "CONEmAltgHw1");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                answers.put(Question.valueOf(question), answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answers;
    }

    // Additional methods for other database operations can be added as needed.

    public static void main(String[] args) {
        // Example usage
        try {
            DatabaseManager databaseManager = new DatabaseManager(JDBC_URL, USERNAME, PASSWORD);
            String userId = "123"; // Replace with an actual user ID
            boolean hasActiveTicket = databaseManager.hasActiveTicket(userId);
            System.out.println("Has active ticket: " + hasActiveTicket);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getCurrentTicketNumber() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(ticket_number) FROM tickets")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0; // Return 0 if there is an error or no ticket in the database
    }
    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void createTableIfNotExists() {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS tickets (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id VARCHAR(255)," +
                    "username VARCHAR(255)," +
                    "ticket_number INT," +
                    "isActive BOOLEAN" +
                    ")";
            try (var preparedStatement = connection.prepareStatement(createTableQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
