package me.moontimer.jumpandrun;

import lombok.Getter;
import me.moontimer.jumpandrun.commands.TestCommand;
import me.moontimer.jumpandrun.listener.JoinListener;
import me.moontimer.jumpandrun.listener.JumpAndRunListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class JumpAndRun extends JavaPlugin {

    @Getter
    private static JumpAndRun instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        MySQL.loadFile();
        MySQL.connect();
        MySQL.createTables();


        getCommand("test").setExecutor(new TestCommand());
        Bukkit.getPluginManager().registerEvents(new JumpAndRunListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MySQL.close();
    }
}
