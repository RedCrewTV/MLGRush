package dev.redcrew.mlgrush.listeners;

import dev.redcrew.mlgrush.MLGRush;
import dev.redcrew.mlgrush.data.DataHandler;
import dev.redcrew.mlgrush.queue.QueueHandler;
import dev.redcrew.mlgrush.utils.InventoryBuilder;
import dev.redcrew.mlgrush.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

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
public class PlayerInteractEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!event.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }

        //If Lobby
        if(player.getWorld().getName().equals("world")) {
            event.setCancelled(true);
            switch(event.getRightClicked().getType()) {
                case BREEZE -> {
                    if (QueueHandler.getInstance().containsQueue(player)) {
                        QueueHandler.getInstance().leaveQueue(player);
                    } else {
                        QueueHandler.getInstance().joinQueue(player);
                    }

                }
                case WANDERING_TRADER -> {
                    player.openInventory(new InventoryBuilder(3, ChatColor.BLUE + "Einstellungen", MLGRush.getInstance())
                                    .setGUI(true)
                                    .setItem(13, new ItemBuilder(Material.STICK)
                                            .setName(ChatColor.GRAY + "Inventarsortierung")
                                            .build())
                                    .addInteraction(Material.STICK, target -> {
                                        int stick = DataHandler.getInstance().getPlayer(target.getUniqueId()).getInventory()
                                                .first(DataHandler.getInstance().getStick().getType());
                                        int pickaxe = DataHandler.getInstance().getPlayer(target.getUniqueId()).getInventory()
                                                .first(DataHandler.getInstance().getPickaxe().getType());
                                        int blocks = DataHandler.getInstance().getPlayer(target.getUniqueId()).getInventory()
                                                .first(DataHandler.getInstance().getBlocks().getType());

                                        target.openInventory(new InventoryBuilder(1, ChatColor.BLUE + "Inventarsortierung", MLGRush.getInstance())
                                                .setItem(stick, DataHandler.getInstance().getStick())
                                                .setItem(pickaxe, DataHandler.getInstance().getPickaxe())
                                                .setItem(blocks, DataHandler.getInstance().getBlocks())
                                                .setCloseAction(closeEvent -> {
                                                    DataHandler.getInstance().getPlayer(target.getUniqueId()).setInventory(closeEvent.getInventory());
                                                    MLGRush.getInstance().setLobbyItems(target);
                                                    target.sendMessage(MLGRush.getInstance().getPrefix() +
                                                            "Deine " + ChatColor.BLUE + " Inventarsortierung " + ChatColor.GRAY + "wurde gespeichert.");
                                                })
                                                .build());
                                    })
                                    .build());
                }
            }
        }
    }

}
