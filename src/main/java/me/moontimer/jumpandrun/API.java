package me.moontimer.jumpandrun;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class API {

    public static boolean playerExists(String uuid) {
        ResultSet resultSet = MySQL.getResult("SELECT * FROM time WHERE UUID='" + uuid + "'");
        try {
            if (resultSet.next())
                return (resultSet.getString("UUID") != null);
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void create(String uuid, int min, int sec) {
        if (!playerExists(uuid.toString())) {
            try {
                PreparedStatement preparedStatement = MySQL.getStatement("INSERT INTO time (UUID, MIN, SECONDS) VALUES (?, ?, ?)");
                preparedStatement.setString(1, uuid);
                preparedStatement.setInt(2, min);
                preparedStatement.setInt(3, sec);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Object getObject(String whereResult, String where, String select) {
        ResultSet resultSet = MySQL.getResult("SELECT " + select + " FROM time WHERE " + where + "='" + whereResult + "'");
        try {
            if (resultSet.next()) {
                return resultSet.getObject(select);
            }
        } catch (SQLException ex) {
            return "ERROR";
        }
        return "ERROR";
    }

    public static void setMin(String uuid, int time) {
        MySQL.update("UPDATE time SET " + "MIN" + "='" + time + "' WHERE UUID='" + uuid + "'");
    }

    public static void setSec(String uuid, int time) {
        MySQL.update("UPDATE time SET " + "SECONDS" + "='" + time + "' WHERE UUID='" + uuid + "'");
    }
}
