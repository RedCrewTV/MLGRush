package dev.redcrew.mlgrush.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
public final class InventoryBuilder implements Listener {

    private final Inventory inventory;
    private final List<Tupel<Material, Consumer<Player>>> interactions = new ArrayList<>();

    private boolean gui = false;
    private Consumer<InventoryCloseEvent> onClose = null;

    public InventoryBuilder(Inventory inventory, Plugin plugin) {
        this.inventory = inventory;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public InventoryBuilder(int rows, String title, Plugin plugin) {
        this(Bukkit.createInventory(null, rows*9, title), plugin);
    }

    public InventoryBuilder(InventoryType type, String title, Plugin plugin) {
        this(Bukkit.createInventory(null, type, title), plugin);
    }


    /**
     * set an item at a specific slot.
     * @param slot the slot where the item is placed
     * @param item the item that should be placed
     * @return the current instance of the {@link InventoryBuilder}
     */
    public InventoryBuilder setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
        return this;
    }

    /**
     * Changes the value of the gui variable
     * @param gui if true then the {@link Inventory} is a GUI this means that you cant move or collect Items from the {@link Inventory}.
     * @return the current instance of the {@link InventoryBuilder}
     */
    public InventoryBuilder setGUI(boolean gui) {
        this.gui = gui;
        return this;
    }

    /**
     * Adds an Interaction to a specific item.
     * @param item the item type who is clicked.
     * @param action the {@link Consumer} will be called when the item will be clicked and the {@link Player} is the Entity who clicked.
     * @return the current instance of the {@link InventoryBuilder}
     */
    public InventoryBuilder addInteraction(Material item, Consumer<Player> action) {
        interactions.add(new Tupel<>(item, action));
        return this;
    }

    /**
     * Sets the action that will be called when the {@link Inventory} closes.
     * @param action the {@link Consumer} will be called when the {@link Inventory} will be closed.
     * @return the current instance of the {@link InventoryBuilder}
     */
    public InventoryBuilder setCloseAction(Consumer<InventoryCloseEvent> action) {
        this.onClose = action;
        return this;
    }

    /**
     * builds the inventory
     * @return the final Inventory
     */
    public Inventory build() {
        return this.inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == this.inventory) {
            if(this.gui) event.setCancelled(true);
            if(event.getCurrentItem() == null) return;

            for (Tupel<Material, Consumer<Player>> interaction : interactions) {
                if(event.getCurrentItem().getType().equals(interaction.getFirst())) {
                    interaction.getLast().accept((Player) event.getWhoClicked());
                }
            }

        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getInventory() == this.inventory) {
            if(onClose != null) onClose.accept(event);
        }
    }

}
