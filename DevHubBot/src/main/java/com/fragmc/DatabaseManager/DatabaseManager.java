package com.fragmc.DatabaseManager;

import java.sql.*;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ticket_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "CONEmAltgHw1";
    private Connection connection;

    public DatabaseManager() {
        try {
            this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    // Insert a ticket into the database
    public void insertTicket(String username, int ticketNumber, Long channelId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO number (username, ticket_number, channel_id) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, ticketNumber);
            preparedStatement.setLong(3, channelId);
            preparedStatement.executeUpdate();
        }
    }


    public void insertChannelID(Long channelId)throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO number (channel_id) VALUES (?)")) {
            preparedStatement.executeUpdate();
        }
    }


    // Additional methods for other database operations can be added as needed.

    public static void main(String[] args) {
        // Example usage
        DatabaseManager databaseManager = new DatabaseManager();

    }
    public int getCurrentTicketNumber() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(ticket_number) FROM number")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0; // Return 0 if there is an error or no ticket in the database
    }

    public String getTicketOwner(long channel_id) {
        String ticketOwner = null;
        String query = "SELECT username FROM number WHERE channel_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, channel_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ticketOwner = resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return ticketOwner;
    }
    public String insertAnswerOne(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer1");
    }

    public String insertAnswerTwo(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer2");
    }
    public String insertAnswerThree(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer1");
    }

    public String insertAnswerFour(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer2");
    }
    public String insertAnswerFive(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer1");
    }

    public String insertAnswerSix(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer2");
    }
    public String insertAnswerSeven(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer1");
    }

    public String insertAnswerEight(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer2");
    }
    public String insertAnswerNine(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer2");
    }

    public String insertAnswerTen(String answer, long channelId) throws SQLException {
        return insertAnswer(answer, channelId, "answer10");
    }

    // Common method to insert an answer
    private String insertAnswer(String answer, long channelId, String answerColumnName) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO fragmc_schema.applicationanswers (channel_id, " + answerColumnName + ") VALUES (?, ?)")) {
            preparedStatement.setLong(1, channelId);
            preparedStatement.setString(2, answer);
            preparedStatement.executeUpdate();
            return answer;
        }
    }


    public void getStaffAppOwner(int channelId) throws SQLException {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM applicationanswers WHERE channel_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, channelId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Channel ID exists in the database
                        System.out.println("Channel ID exists in the database.");
                    } else {
                        // Channel ID does not exist in the database
                        System.out.println("Channel ID does not exist in the database.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAnsweredQuestion(){

    }


}
