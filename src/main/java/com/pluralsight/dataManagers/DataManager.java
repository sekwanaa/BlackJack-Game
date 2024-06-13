package com.pluralsight.dataManagers;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

public abstract class DataManager {
    protected Connection connection;

    public void openConnection() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        try (BasicDataSource basicDataSource = new BasicDataSource()) {
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(user);
            basicDataSource.setPassword(password);
            this.connection = basicDataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void executeUpdate(String query, List<T> arguments) {
        openConnection();
        try (PreparedStatement insertStmt = connection.prepareStatement(query)) {
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i) instanceof Integer) {
                    insertStmt.setInt(i + 1, (Integer) arguments.get(i));
                } else if (arguments.get(i) instanceof Double) {
                    insertStmt.setDouble(i + 1, (Double) arguments.get(i));
                } else if (arguments.get(i) instanceof String) {
                    insertStmt.setString(i + 1, (String) arguments.get(i));
                } else {
                    throw new IllegalArgumentException("Unsupported argument type");
                }
            }

            insertStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

}
