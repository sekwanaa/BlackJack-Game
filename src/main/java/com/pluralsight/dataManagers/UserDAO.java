package com.pluralsight.dataManagers;

import com.pluralsight.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DataManager {

    public void createUser(String username, String password, double points) {
        String createSql = """
                INSERT INTO `blackjack`.`user`
                (`username`, `password`, `points`) VALUES (?, ?, ?);
                """;
        try {
            PreparedStatement ps = connection.prepareStatement(createSql);
            ps.setString(1, username);
            ps.setString(1, password);
            ps.setDouble(1, points);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Player getUser(String username, String password) {
        String query = "SELECT username FROM user WHERE password = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, password);

            ResultSet user = ps.executeQuery();

            if (user.next()) {
                String returnedUsername = user.getString("username");
                if (returnedUsername.equals(username)) {
                    return new Player(returnedUsername);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
