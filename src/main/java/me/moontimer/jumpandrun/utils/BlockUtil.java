package me.moontimer.jumpandrun.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class BlockUtil {

    public static Block getNextBlock(Location location, DirectionPacket packet) {
        Block nextBlock = whileNext(location, packet);

        while(nextBlock.getX() < -16 || nextBlock.getX() > 29 || nextBlock.getZ() < -48 || nextBlock.getZ() > -1 || nextBlock.getType() != Material.AIR)
            nextBlock = whileNext(location, generateNextJump());

        return nextBlock;
    }

    private static Block whileNext(Location location, DirectionPacket packet) {
        return Bukkit.getWorld(location.getWorld().getName()).getBlockAt(
                new Location(location.getWorld(),
                        location.getX() + packet.getX(),
                        location.getY() + packet.getY(),
                        location.getZ() + packet.getZ())
        );
    }


    public static Block getRandomStartPosition(Location loc) {
        int x = (new Random()).nextInt(45) + -16;
        int z = (new Random()).nextInt(47) + -48;
        Block b = Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(x, 87, z);
        while (b.getType() != Material.AIR)
            b = whileRandomStartPosition(loc);
        return b;
    }

    private static Block whileRandomStartPosition(Location loc) {
        int x = (new Random()).nextInt(45) + -16;
        int z = (new Random()).nextInt(47) + -48;
        return Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(x, 87, z);
    }

    public static Location teleportOnBlock(Block block) {
        return new Location(block.getWorld(), block.getX(), (block.getY() + 1), block.getZ());
    }

    public static DirectionPacket generateNextJump() {

        switch ((new Random()).nextInt(24)) {
            case 0:
                return new DirectionPacket(3.0D, 0.0D, 0.0D);
            case 1:
                return new DirectionPacket(0.0D, 0.0D, 3.0D);
            case 2:
                return new DirectionPacket(-3.0D, 0.0D, 0.0D);
            case 3:
                return new DirectionPacket(0.0D, 0.0D, -3.0D);
            case 4:
                return new DirectionPacket(4.0D, -1.0D, 4.0D);
            case 5:
                return new DirectionPacket(-4.0D, -1.0D, -4.0D);
            case 6:
                return new DirectionPacket(4.0D, -1.0D, -4.0D);
            case 7:
                return new DirectionPacket(-4.0D, -1.0D, 4.0D);
            case 8:
                return new DirectionPacket(3.0D, 0.0D, 3.0D);
            case 9:
                return new DirectionPacket(-3.0D, 0.0D, -3.0D);
            case 10:
                return new DirectionPacket(3.0D, 0.0D, -3.0D);
            case 11:
                return new DirectionPacket(-3.0D, 0.0D, 3.0D);
            case 12:
                return new DirectionPacket(4.0D, 0.0D, 0.0D);
            case 13:
                return new DirectionPacket(0.0D, 0.0D, 4.0D);
            case 14:
                return new DirectionPacket(-4.0D, 0.0D, 0.0D);
            case 15:
                return new DirectionPacket(0.0D, 0.0D, -4.0D);
            case 16:
                return new DirectionPacket(3.0D, -1.0D, 0.0D);
            case 17:
                return new DirectionPacket(-3.0D, -1.0D, 0.0D);
            case 18:
                return new DirectionPacket(0.0D, -1.0D, 3.0D);
            case 19:
                return new DirectionPacket(0.0D, -1.0D, -3.0D);
            case 20:
                return new DirectionPacket(4.0D, 0.0D, 0.0D);
            case 21:
                return new DirectionPacket(-4.0D, 0.0D, 0.0D);
            case 22:
                return new DirectionPacket(0.0D, 0.0D, 4.0D);
            case 23:
                return new DirectionPacket(0.0D, 0.0D, -4.0D);
        }
        return null;
    }

}
