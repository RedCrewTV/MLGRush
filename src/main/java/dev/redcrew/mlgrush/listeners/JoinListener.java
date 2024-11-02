package dev.redcrew.mlgrush.listeners;

import dev.redcrew.mlgrush.MLGRush;
import dev.redcrew.mlgrush.data.DataHandler;
import dev.redcrew.mlgrush.data.DataPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

/**
 * This file is a JavaDoc!
 * Created: 11/1/2024
 * <p>
 * Belongs to MLGRush
 * <p>
 *
 * @author RedCrew <p>
 * Discord: redcrew <p>
 * Website: <a href="https://redcrew.dev/">https://redcrew.dev/</a>
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + player.getName());

        //register Player
        if (DataHandler.getInstance().getPlayer(player.getUniqueId()) == null) {
            DataHandler.getInstance().registerPlayer(new DataPlayer(player.getUniqueId()));
        }

        //Config Player
        MLGRush.getInstance().configLobbyPlayer(player);

    }

}
