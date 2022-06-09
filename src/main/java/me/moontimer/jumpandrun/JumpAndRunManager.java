package me.moontimer.jumpandrun;

import me.moontimer.jumpandrun.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class JumpAndRunManager {

    public static HashMap<Player, Instant> timer = new HashMap<>();
    private static List<JumpAndRunManager> jumpAndRunManagerList = new ArrayList<>();
    private final Player player;
    private final ItemStack item;
    private Block currentBlock, nextBlock;

    public JumpAndRunManager(Player player) {
        this.player = player;
        this.item = new ItemStack(Material.WOOL, 0);
        this.getJumpAndRunManagerList().add(this);
    }

    public void start() {
        setCurrentBlock(BlockUtil.getRandomStartPosition(player.getLocation()));
        getCurrentBlock().setType(getItem().getType());
        player.teleport(BlockUtil.teleportOnBlock(getCurrentBlock()));
        setNextBlock(BlockUtil.getNextBlock(player.getLocation(), BlockUtil.generateNextJump()));
        getNextBlock().setType(getItem().getType());
        timer.put(player, Instant.now());
    }

    public void end(Player player) {
        getJumpAndRunManagerList().remove(this);
        if (currentBlock.getType() != Material.AIR)
            currentBlock.setType(Material.AIR);
        if (nextBlock.getType() != Material.AIR)
            nextBlock.setType(Material.AIR);
        if (player.isOnline()) {
                //tp to spawn
            timer.remove(player);
            player.sendTitle("§cDu bist", "§cruntergefallen");
        }
    }

    public void endWithFinish() {
        getJumpAndRunManagerList().remove(this);
        if (currentBlock.getType() != Material.AIR)
            currentBlock.setType(Material.AIR);
        if (nextBlock.getType() != Material.AIR)
            nextBlock.setType(Material.AIR);
        if (player.isOnline()) {
            Instant start = timer.get(player);
            if (start == null) {
                return;
            }
            Instant finish = Instant.now();
            int timeElapsedMin = (int) Duration.between(start, finish).toMinutes();
            int timeElapsedSec = (int) (Duration.between(start, finish).getSeconds()%60);

            int bestTimeMin = (int) API.getObject(player.getUniqueId().toString(), "UUID", "MIN");
            int bestTimeSec = (int) API.getObject(player.getUniqueId().toString(), "UUID", "SECONDS");

            if ((bestTimeMin >= timeElapsedMin && bestTimeSec > timeElapsedSec) || bestTimeMin == 0 && bestTimeSec == 0) {
                API.setMin(player.getUniqueId().toString(), timeElapsedMin);
                API.setSec(player.getUniqueId().toString(), timeElapsedSec);
            }

            player.sendTitle("§a" + timeElapsedMin + ":" + timeElapsedSec, "§e" + bestTimeMin + ":" + bestTimeSec);

            timer.remove(player);
        }
    }
    public Player getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Block currentBlock) {
        this.currentBlock = currentBlock;
        update();
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
        update();
    }

    public void update() {
        if (getJumpAndRunManagerList().contains(this))
            getJumpAndRunManagerList().remove(this);
        getJumpAndRunManagerList().add(this);
    }

    public static List<JumpAndRunManager> getJumpAndRunManagerList() {
        return jumpAndRunManagerList;
    }

    public static JumpAndRunManager getEntryFromPlayer(Player player) {
        Iterator<JumpAndRunManager> var3 = getJumpAndRunManagerList().iterator();
        while (var3.hasNext()) {
            JumpAndRunManager all = var3.next();
            if (all.getPlayer() == player) {
                return all;
            }
        }
        return null;
    }
}
