package dev.redcrew.mlgrush.listeners;

import dev.redcrew.mlgrush.data.DataHandler;
import dev.redcrew.mlgrush.match.Match;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

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
public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Match match = DataHandler.getInstance().getPlayer(player.getUniqueId()).getMatch();
        if(match != null) {
            if(player.getWorld().equals(match.getWorld()) && player.getLocation().getBlockY() <= 100) {
                match.resetPlayer(player);
            }
        }
    }

}
