package me.moontimer.jumpandrun.listener;

import com.sun.org.apache.bcel.internal.generic.I2F;
import me.moontimer.jumpandrun.JumpAndRunManager;
import me.moontimer.jumpandrun.utils.BlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class JumpAndRunListener implements Listener {

    private static ConcurrentHashMap<Player, Integer> jumps = new ConcurrentHashMap<>();


    @EventHandler
    public void onMove(PlayerMoveEvent event) {


        Player player = event.getPlayer();
        JumpAndRunManager jumpAndRunManager = JumpAndRunManager.getEntryFromPlayer(player);
        if (jumpAndRunManager != null) {



            if (player.getLocation().getBlockX() == jumpAndRunManager.getNextBlock().getX()
                    && player.getLocation().getBlockY() - 1 == jumpAndRunManager.getNextBlock().getY()
                    && player.getLocation().getBlockZ() == jumpAndRunManager.getNextBlock().getZ()) {


                addJumps(player);
                int jumpInt = jumps.get(player);
                if (!(jumpInt >= 5)) {
                    jumpAndRunManager.getCurrentBlock().setType(Material.AIR);
                    jumpAndRunManager.getNextBlock().setType(Material.WOOL);
                    jumpAndRunManager.setCurrentBlock(jumpAndRunManager.getNextBlock());
                    jumpAndRunManager.setNextBlock(BlockUtil.getNextBlock(player.getLocation(), BlockUtil.generateNextJump()));
                    jumpAndRunManager.getNextBlock().setType(Material.WOOL);
                } else {
                    player.sendMessage("§cDu hast es geschafft!");
                    jumpAndRunManager.endWithFinish();
                    jumps.remove(player);

                    return;
                }
            }

            if (player.getLocation().getBlockY() < jumpAndRunManager.getCurrentBlock().getY()) {
                jumpAndRunManager.end(player);
                jumps.remove(player);
            }

        } else {
            if (player.getLocation().getWorld().getBlockAt(player.getLocation()).getType().equals(Material.IRON_PLATE)) {
                JumpAndRunManager jumpAndRun = new JumpAndRunManager(player);

                jumpAndRun.start();
            }
        }
    }


    public static synchronized void addJumps(Player player) {
        try {
            int jumpInt = jumps.get(player);
            jumpInt++;
            jumps.put(player, jumpInt);
        } catch (NullPointerException exception) {
            jumps.put(player, 1);
        }
    }
}
