package me.moontimer.jumpandrun.commands;

import me.moontimer.jumpandrun.JumpAndRun;
import me.moontimer.jumpandrun.JumpAndRunManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        JumpAndRunManager jumpAndRun = new JumpAndRunManager(player);

        jumpAndRun.start();

        return false;
    }
}
