import org.h2.tools.DeleteDbFiles;

import java.sql.*;
import java.util.Scanner;

public class JdbcExample {
    /**
     * Database connection configuration
     */
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";
    private static final String DATABASE_URL = "jdbc:h2:~/test";
    private static final String DATABASE_USER = "";
    private static final String DATABASE_USER_PASSWORD = "";

    public static void main(String[] args) {
        DeleteDbFiles.execute("~", "test", true);
        Connection connection = initializeDatabaseConnection();
        initializeDatabaseSchema(connection);
        insertExampleValues(connection);
        selectValues(connection);
        closeDatabaseConnection(connection);
    }

    private static Connection initializeDatabaseConnection() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_USER_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.err);
            throw new IllegalStateException(e);
        }
    }

    private static void initializeDatabaseSchema(Connection connection) {
        try {
            String createTableCommand = "CREATE TABLE CAR(id IDENTITY, make varchar(30), model varchar (30))";
            PreparedStatement createPreparedStatement = connection.prepareStatement(createTableCommand);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private static void insertExampleValues(Connection connection) {
        try {
            String insertValuesCommand = "INSERT INTO CAR(id, make, model) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertValuesCommand);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Toyota");
            preparedStatement.setString(3, "Corolla");
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Ford");
            preparedStatement.setString(3, "Focus");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private static void selectValues(Connection connection) {
        try {
            String selectValuesCommand = "SELECT id, make, model FROM CAR";
            PreparedStatement createPreparedStatement = connection.prepareStatement(selectValuesCommand);
            ResultSet resultSet = createPreparedStatement.executeQuery();
            while (resultSet.next()) {
                int carId = resultSet.getInt("id");
                String make = resultSet.getString("make");
                String model = resultSet.getString(3);
                System.out.println(String.format("Car fetched from database: id = %d, make = %s, model = %s", carId, make, model));
            }
            createPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private static void closeDatabaseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}