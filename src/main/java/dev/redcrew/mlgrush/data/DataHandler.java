package dev.redcrew.mlgrush.data;

import dev.redcrew.mlgrush.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
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
public class DataHandler {

    @Getter
    private final static DataHandler instance = new DataHandler();

    private final List<DataPlayer> players = new ArrayList<>();

    private DataHandler() {}

    public void registerPlayer(DataPlayer player) {
        players.add(player);
    }

    public DataPlayer getPlayer(UUID uuid) {
        for (DataPlayer player : players) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    public void loadAll() {
        //TODO load the data e.g. in a DB or file
    }

    public void saveAll() {
        players.forEach(DataPlayer::save);
    }

    public ItemStack getStick() {
        return new ItemBuilder(Material.STICK)
                .setName(ChatColor.BLUE + "Stick")
                .addEnchantment(Enchantment.KNOCKBACK, 1)
                .build();
    }

    public ItemStack getPickaxe() {
        return new ItemBuilder(Material.WOODEN_PICKAXE)
                .setName(ChatColor.BLUE + "Holzspitzhacke")
                .addEnchantment(Enchantment.EFFICIENCY, 1)
                .setUnbreakable(true)
                .build();
    }

    public ItemStack getBlocks() {
        return new ItemBuilder(Material.SANDSTONE)
                .setName(ChatColor.BLUE + "Blöcke")
                .setAmount(64)
                .build();
    }

}
