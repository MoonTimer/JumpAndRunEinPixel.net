package me.moontimer.jumpandrun.manager;

import me.moontimer.jumpandrun.JumpAndRun;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.*;

public class MySQL {
    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    public static Connection con;

    public static void connect() {
        if (!MySQL.isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database+"?autoReconnect=true", username, password);
                Bukkit.getConsoleSender().sendMessage("§aDie MySQL Verbindung wurde erfolgreich aufgebaut§8.");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        if (MySQL.isConnected()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage("§cDie MySQL Verbindung wurde erfolgreich geschlossen§8.");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        if (con != null) {
            return true;
        }
        return false;
    }

    public static void update(String query) {
        PreparedStatement ps = null;
        try {
            ps = MySQL.con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet getResult(String query) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = MySQL.con.prepareStatement(query);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createTables() {
        if (MySQL.isConnected()) {
            try {
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS time (UUID TEXT, MIN INT, SECONDS INT)");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadFile()
    {
        try
        {
            File file = new File(JumpAndRun.getInstance().getDataFolder(), "MySQL.yml");
            boolean created = true;
            if (!file.exists())
            {
                file.createNewFile();
                created = false;
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (!created)
            {
                config.set("mysql.Host", "localhost");
                config.set("mysql.Port", "3306");
                config.set("mysql.Database", "System");
                config.set("mysql.Username", "root");
                config.set("mysql.Password", "password");

                config.save(file);
            }
            host = config.getString("mysql.Host");
            port = config.getString("mysql.Port");
            database = config.getString("mysql.Database");
            username = config.getString("mysql.Username");
            password = config.getString("mysql.Password");
        }
        catch (Exception localException) {}
    }

    public static PreparedStatement getStatement(String sql) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = MySQL.con.prepareStatement(sql);
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
