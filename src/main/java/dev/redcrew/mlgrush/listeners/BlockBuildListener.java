package dev.redcrew.mlgrush.listeners;

import dev.redcrew.mlgrush.data.DataHandler;
import dev.redcrew.mlgrush.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

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
public class BlockBuildListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getWorld().getName().equals("world")) {
            event.setCancelled(true);
            return;
        }

        Match match = DataHandler.getInstance().getPlayer(player.getUniqueId()).getMatch();
        if(match != null) {
            if(event.getBlock().getWorld().equals(match.getWorld())) {
                if(match.getBlocks().contains(event.getBlock())) {
                    match.getBlocks().remove(event.getBlock());
                    event.getBlock().setType(Material.AIR);
                    ItemStack clone = DataHandler.getInstance().getBlocks().clone();
                    clone.setAmount(1);
                    player.getInventory().addItem(clone);
                }
                if(event.getBlock().getBlockData() instanceof Bed) {
                    if(match.getBlue().equals(player)) {
                        //Blue Player
                        if(event.getBlock().getLocation().equals(match.getRedFoot()) || event.getBlock().getLocation().equals(match.getRedHead())) {
                            match.blueWinRound();
                        }
                    } else if(match.getRed().equals(player)) {
                        //Red Player
                        if(event.getBlock().getLocation().equals(match.getBlueFoot()) || event.getBlock().getLocation().equals(match.getBlueHead())) {
                            match.redWinRound();
                        }
                    }
                }
                event.setCancelled(true);
            }
        }


    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(player.getWorld().getName().equals("world")) {
            event.setCancelled(true);
            return;
        }

        Match match = DataHandler.getInstance().getPlayer(player.getUniqueId()).getMatch();
        if(match != null) {
            if(event.getBlock().getWorld().equals(match.getWorld())) {
                match.getBlocks().add(event.getBlock());
            }
        }
    }


}
