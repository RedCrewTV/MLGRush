package dev.redcrew.mlgrush.data;

import dev.redcrew.mlgrush.MLGRush;
import dev.redcrew.mlgrush.match.Match;
import dev.redcrew.mlgrush.utils.InventoryBuilder;
import dev.redcrew.mlgrush.utils.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

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
@Getter
@AllArgsConstructor
public class DataPlayer {

    private final UUID uuid;
    private Inventory inventory;

    private @Setter UUID challenge = null;
    private @Setter Match match = null;

    public DataPlayer(UUID uuid) {
        this.uuid = uuid;
        this.inventory = new InventoryBuilder(1, "", MLGRush.getInstance())
                .setItem(0, DataHandler.getInstance().getStick())
                .setItem(1, DataHandler.getInstance().getPickaxe())
                .setItem(2, DataHandler.getInstance().getBlocks())
                .build();
    }


    public void setInventory(Inventory inventory) {
        if(inventory.contains(DataHandler.getInstance().getStick()) &&
                inventory.contains(DataHandler.getInstance().getPickaxe()) && inventory.contains(DataHandler.getInstance().getBlocks())) {
            this.inventory = inventory;
        }
    }

    public void save() {
        //TODO save the data e.g. in a DB or file
    }



}
