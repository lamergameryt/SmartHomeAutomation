package com.lamergameryt.fdwebview.mysql;

import static com.lamergameryt.fdwebview.mysql.Models.Setting;
import static com.lamergameryt.fdwebview.mysql.Models.User;

import com.lamergameryt.fdwebview.MySQLConfig;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseHandler {

    private final Logger logger = LoggerFactory.getLogger(DatabaseHandler.class);
    private Connection conn;

    public DatabaseHandler(MySQLConfig config) {
        this(
            config.getHost(),
            config.getPort(),
            config.getDatabase(),
            config.getUsername(),
            config.getPassword()
        );
    }

    public DatabaseHandler(String host, int port, String database, String username, String password) {
        try {
            conn =
                DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database,
                    username,
                    password
                );
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Invalid database details entered.", e);
            System.exit(1);
        }
    }

    public User getUserById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();
            if (!result.next()) return null;

            return new User(
                result.getInt("id"),
                result.getString("name"),
                result.getInt("day"),
                result.getInt("month"),
                result.getInt("year")
            );
        } catch (SQLException e) {
            logger.error("Unable to fetch user with the id " + id, e);
        }

        return null;
    }

    public User createNewUser(String name, LocalDate dob, byte[] encoding) {
        int year = dob.getYear();
        int month = dob.getMonthValue();
        int day = dob.getDayOfMonth();

        String insertIntoUsers = "INSERT INTO users(name, day, month, year) VALUES(?, ?, ?, ?)";
        String insertIntoParams = "INSERT INTO parameters VALUES (?, ?)";
        try (
            PreparedStatement psUsers = conn.prepareStatement(
                insertIntoUsers,
                Statement.RETURN_GENERATED_KEYS
            );
            PreparedStatement psParams = conn.prepareStatement(insertIntoParams)
        ) {
            psUsers.setString(1, name);
            psUsers.setInt(2, day);
            psUsers.setInt(3, month);
            psUsers.setInt(4, year);

            int rows = psUsers.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            int id;
            try (ResultSet keys = psUsers.getGeneratedKeys()) {
                if (keys.next()) {
                    id = keys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID found.");
                }
            }

            psParams.setInt(1, id);
            psParams.setBytes(2, encoding);

            psParams.execute();
            conn.commit();
            return new User(id, name, day, month, year);
        } catch (SQLException e) {
            logger.error("Could not create new user \"" + name + "\" in the database.", e);
        }

        return null;
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")) {
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                users.add(
                    new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("day"),
                        result.getInt("month"),
                        result.getInt("year")
                    )
                );
            }
        } catch (SQLException e) {
            logger.error("Unable to fetch users", e);
        }

        return users;
    }

    public ArrayList<Setting> getSettingsById(Location location, int userId) {
        ArrayList<Setting> settings = new ArrayList<>();

        //noinspection SqlSourceToSinkFlow,SqlResolve
        try (
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM " + location.getType() + "settings WHERE user_id = ?"
            )
        ) {
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) settings.add(
                new Setting(location, rs.getInt("user_id"), rs.getInt("pin_id"), rs.getInt("value"))
            );
        } catch (SQLException e) {
            logger.error("Failed to fetch user settings from " + location.getType() + "settings", e);
        }

        return settings;
    }

    public Setting getSettingsById(Location location, int userId, int pinId) {
        //noinspection SqlSourceToSinkFlow,SqlResolve
        try (
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM " + location.getType() + "settings WHERE user_id = ? AND pin_id = ?"
            )
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, pinId);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            return new Setting(location, rs.getInt("user_id"), rs.getInt("pin_id"), rs.getInt("value"));
        } catch (SQLException e) {
            logger.error("Failed to fetch user settings from " + location.getType() + "settings", e);
        }

        return null;
    }

    public Setting updateSettingValue(Location location, int userId, int pinId, int newValue) {
        //noinspection SqlSourceToSinkFlow,SqlResolve
        try (
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE " + location.getType() + "settings SET value = ? WHERE user_id = ? AND pin_id = ?"
            )
        ) {
            ps.setInt(1, newValue);
            ps.setInt(2, userId);
            ps.setInt(3, pinId);

            ps.executeUpdate();
            conn.commit();
            return new Setting(location, userId, pinId, newValue);
        } catch (SQLException e) {
            logger.error(
                "Failed to update the " +
                location.getType() +
                "setting value for user " +
                userId +
                " at pin " +
                pinId,
                e
            );
        }

        return null;
    }

    public enum Location {
        KITCHEN("kitchen_"),
        BEDROOM("bedroom_"),
        HALL("hall_");

        private final String type;

        Location(String type) {
            this.type = type;
        }

        public static Location getFromType(String type) {
            for (Location typeEnum : Location.values()) {
                if (typeEnum.name().equalsIgnoreCase(type)) return typeEnum;
            }

            return null;
        }

        public String getType() {
            return type;
        }
    }
}
