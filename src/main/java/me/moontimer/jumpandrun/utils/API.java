package me.moontimer.jumpandrun.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.moontimer.jumpandrun.JumpAndRun;
import me.moontimer.jumpandrun.manager.MySQL;
import org.bukkit.Bukkit;

import javax.security.auth.callback.Callback;
import java.awt.image.PackedColorModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class API {

    public static void playerExists(String uuid, Consumer<Boolean> callback) {

        CompletableFuture.runAsync(() -> {
            ResultSet resultSet = MySQL.getResult("SELECT * FROM time WHERE UUID='" + uuid + "'");
            try {
                if (resultSet.next())
                    callback.accept(resultSet.getString("UUID") != null);
                callback.accept(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public static void create(String uuid, int min, int sec) {
        playerExists(uuid, exists -> {
            if (!exists) {
                try {
                    PreparedStatement preparedStatement = MySQL.getStatement("INSERT INTO time (UUID, MIN, SECONDS) VALUES (?, ?, ?)");
                    preparedStatement.setString(1, uuid);
                    preparedStatement.setInt(2, min);
                    preparedStatement.setInt(3, sec);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void getObject(String whereResult, String where, String select, Consumer<Object> callback) {

        CompletableFuture.runAsync(() -> {
            ResultSet resultSet = MySQL.getResult("SELECT " + select + " FROM time WHERE " + where + "='" + whereResult + "'");
            try {
                if (resultSet.next()) {
                    callback.accept(resultSet.getObject(select));
                }
            } catch (SQLException ex) {
                callback.accept("ERROR");
            }
            callback.accept("ERROR");
        });

    }

    public static void setMin(String uuid, int time) {


        CompletableFuture.runAsync(() -> {
            MySQL.update("UPDATE time SET " + "MIN" + "='" + time + "' WHERE UUID='" + uuid + "'");

        });
    }

    public static void setSec(String uuid, int time) {
        CompletableFuture.runAsync(() -> {
            MySQL.update("UPDATE time SET " + "SECONDS" + "='" + time + "' WHERE UUID='" + uuid + "'");
        });
    }
}
