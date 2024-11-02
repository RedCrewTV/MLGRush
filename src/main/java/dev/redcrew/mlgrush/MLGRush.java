package dev.redcrew.mlgrush;

import dev.redcrew.mlgrush.data.DataHandler;
import dev.redcrew.mlgrush.listeners.*;
import dev.redcrew.mlgrush.match.MatchHandler;
import dev.redcrew.mlgrush.utils.InventoryBuilder;
import dev.redcrew.mlgrush.utils.ItemBuilder;
import dev.redcrew.mlgrush.utils.VoidWorldGenerator;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@Getter
public final class MLGRush extends JavaPlugin {

    @Getter
    private static MLGRush instance;

    private final String prefix = ChatColor.BLUE + "MLGRush" + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY;
    private final ItemStack challengeItem = new ItemBuilder(Material.NETHERITE_SWORD).setName(ChatColor.BLUE + "Herausfordern").build();


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        //Register all Listeners
        registerListeners();

        //Config Lobby
        configLobby(Objects.requireNonNull(Bukkit.getWorld("world")));
        spawnLobbyQueueNPC(Objects.requireNonNull(Bukkit.getWorld("world")));
        spawnLobbySettingsNPC(Objects.requireNonNull(Bukkit.getWorld("world")));

        //load all Player Data
        DataHandler.getInstance().loadAll();

        //Load all Boxes
        registerBoxes();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DataHandler.getInstance().saveAll();
    }

    private void registerBoxes() {
        MatchHandler.getInstance().registerBox(Objects.requireNonNull(Bukkit.createWorld(new WorldCreator("box-1")
                .generator(new VoidWorldGenerator()))));
        MatchHandler.getInstance().registerBox(Objects.requireNonNull(Bukkit.createWorld(new WorldCreator("box-2")
                .generator(new VoidWorldGenerator()))));
        MatchHandler.getInstance().registerBox(Objects.requireNonNull(Bukkit.createWorld(new WorldCreator("box-3")
                .generator(new VoidWorldGenerator()))));
        MatchHandler.getInstance().registerBox(Objects.requireNonNull(Bukkit.createWorld(new WorldCreator("box-4")
                .generator(new VoidWorldGenerator()))));

    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new QuitListener(), this);
        pm.registerEvents(new BlockBuildListener(), this);
        pm.registerEvents(new FoodListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerDamageListener(), this);
        pm.registerEvents(new PlayerInteractEntityListener(), this);
        pm.registerEvents(new PlayerItemDropListener(), this);
        pm.registerEvents(new PlayerInventoryListener(), this);
        pm.registerEvents(new PlayerDamageEntityListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
    }

    private void configLobby(World world) {
        world.setTime(5000);
        world.setClearWeatherDuration(200000);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
    }

    private void spawnLobbyQueueNPC(World world) {
        for (Entity entity : world.getEntities()) {
            if(entity instanceof Breeze) {
                entity.remove();
            }
        }
        Breeze breeze = (Breeze) world.spawnEntity(new Location(world, -13, 207, -5, -45f, 0f),
                EntityType.BREEZE);

        breeze.setAI(false);
        breeze.setInvulnerable(true);
        breeze.setCanPickupItems(false);
        breeze.setSilent(true);
        breeze.setCustomName(ChatColor.GRAY + "Klick hier um der " + ChatColor.BLUE + "Queue " + ChatColor.GRAY + "beizutreten.\n" +
                "0 Spieler sind in der Queue.");
        breeze.setCustomNameVisible(true);
    }

    private void spawnLobbySettingsNPC(World world) {
        for (Entity entity : world.getEntities()) {
            if(entity instanceof WanderingTrader) {
                entity.remove();
            }
        }
        WanderingTrader trader = (WanderingTrader) world.spawnEntity(new Location(world, -18, 206.5, -1, -67.5f, 0f),
                EntityType.WANDERING_TRADER);

        trader.setAI(false);
        trader.setInvulnerable(true);
        trader.setCanPickupItems(false);
        trader.setSilent(true);
        trader.setCustomName(ChatColor.GRAY + "Einstellungen");
        trader.setCustomNameVisible(true);
    }

    public void setLobbyItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, MLGRush.getInstance().getChallengeItem());
    }

    public void configLobbyPlayer(Player player) {
        player.teleport(new Location(Objects.requireNonNull(Bukkit.getWorld("world")),
                -1.5, 206, 3.5, 130f, -5f));
        player.setRespawnLocation(player.getLocation());
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);
        MLGRush.getInstance().setLobbyItems(player);
    }

}
