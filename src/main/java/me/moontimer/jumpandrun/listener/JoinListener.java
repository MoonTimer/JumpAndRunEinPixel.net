package me.moontimer.jumpandrun.listener;

import me.moontimer.jumpandrun.API;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        API.create(player.getUniqueId().toString(), 0, 0);
    }
}
