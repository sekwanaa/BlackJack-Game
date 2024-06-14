package com.pluralsight.dataManagers;

import com.pluralsight.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DataManager {

    public void createUser(String username, String password, double points) {
        openConnection();
        String createSql = """
                INSERT INTO `blackjack`.`user`
                (`username`, `password`, `points`) VALUES (?, ?, ?);
                """;
        try {
            PreparedStatement ps = connection.prepareStatement(createSql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setDouble(3, points);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public Player getUser(String username, String password) {
        openConnection();
        String query = "SELECT * FROM user WHERE password = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, password);

            ResultSet user = ps.executeQuery();

            if (user.next()) {
                String returnedUsername = user.getString("username");
                if (returnedUsername.equals(username)) {
                    Player player = new Player(user.getInt("user_id"), returnedUsername);
                    player.setPoints(user.getDouble("points"));
                    return player;
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public <T> void updateUser(int user_id, String username, String whatAreYouUpdating, T updateThing) {
        openConnection();
        String updateSql = getUpdateFields(whatAreYouUpdating, updateThing);

        try {
            PreparedStatement ps = connection.prepareStatement(updateSql);

            ps.setInt(1, user_id);
            ps.setString(2, username);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    private static <T> String getUpdateFields(String whatAreYouUpdating, T updateThing) {
        String setField = "";

        switch (whatAreYouUpdating) {
            case "points" -> setField = String.format("`points` = %.2f", (double) updateThing);
            case "username" -> setField = String.format("`username` = %s", updateThing);
            case "password" -> setField = String.format("`password` = %s", updateThing);
        }

        return String.format("""
                UPDATE `blackjack`.`user`
                SET
                %s
                WHERE `user_id` = ? AND `username` = ?;
                """, setField);
    }
}
