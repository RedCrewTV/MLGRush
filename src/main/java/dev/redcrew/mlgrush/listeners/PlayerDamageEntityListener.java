package dev.redcrew.mlgrush.listeners;

import dev.redcrew.mlgrush.MLGRush;
import dev.redcrew.mlgrush.data.DataHandler;
import dev.redcrew.mlgrush.match.MatchHandler;
import dev.redcrew.mlgrush.queue.QueueHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Breeze;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
public class PlayerDamageEntityListener implements Listener {

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player player) {
            if(event.getEntity() instanceof Player target) {
                if(!(player.getWorld().getName().equals("world") && target.getWorld().getName().equals("world"))) return;
                //Challenge
                if(DataHandler.getInstance().getPlayer(target.getUniqueId()).getChallenge() == player.getUniqueId()) {
                    //Accept a challenge
                    MatchHandler.getInstance().createMatch(player, target);

                    DataHandler.getInstance().getPlayer(target.getUniqueId()).setChallenge(null);
                    if (QueueHandler.getInstance().containsQueue(target)) {
                        QueueHandler.getInstance().silentLeaveQueue(target);
                    }

                    DataHandler.getInstance().getPlayer(player.getUniqueId()).setChallenge(null);
                    if (QueueHandler.getInstance().containsQueue(player)) {
                        QueueHandler.getInstance().silentLeaveQueue(player);
                    }

                } else {
                    DataHandler.getInstance().getPlayer(player.getUniqueId()).setChallenge(target.getUniqueId());
                    player.sendMessage(MLGRush.getInstance().getPrefix() +
                            "Du hast " + ChatColor.BLUE + " " + target.getName() + " " + ChatColor.GRAY + "herausgefordert.");
                    target.sendMessage(MLGRush.getInstance().getPrefix() +
                            "Du wurdest von " + ChatColor.BLUE + " " + player.getName() + " " + ChatColor.GRAY + "herausgefordert.");
                }

            }
        }
    }

}
