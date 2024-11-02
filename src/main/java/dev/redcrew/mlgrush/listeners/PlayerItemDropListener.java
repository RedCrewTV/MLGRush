package dev.redcrew.mlgrush.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * This file is a JavaDoc!
 * Created: 11/2/2024
 * <p>
 * Belongs to MLGRush
 * <p>
 *
 * @author RedCrew <p>
 * Discord: redcrew <p>
 * Website: <a href="https://redcrew.dev/">https://redcrew.dev/</a>
 */
public class PlayerItemDropListener implements Listener {

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        if(event.getPlayer().getWorld().getName().equals("world")) event.setCancelled(true);
    }

}
