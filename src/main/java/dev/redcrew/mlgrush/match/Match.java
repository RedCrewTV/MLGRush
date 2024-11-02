package dev.redcrew.mlgrush.match;

import dev.redcrew.mlgrush.MLGRush;
import dev.redcrew.mlgrush.data.DataHandler;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

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
@Setter
public class Match {
    
    private final Player blue;
    private final Player red;
    private final World world;
    
    private final Location blueSpawn;
    private final Location blueFoot;
    private final Location blueHead;

    private final Location redSpawn;
    private final Location redFoot;
    private final Location redHead;

    private final List<Block> blocks = new ArrayList<>();

    private int bluePoints = 0;
    private int redPoints = 0;

    public Match(Player blue, Player red, World world) {
        this.blue = blue;
        this.red = red;
        this.world = world;

        this.blueSpawn = new Location(getWorld(), -0.5, 120, -13.5, 0f, 0f);
        this.blueFoot = new Location(getWorld(), -1, 120, -16);
        this.blueHead = new Location(getWorld(), -1, 120, -17);

        this.redSpawn = new Location(getWorld(), -0.5, 120, 8.5, -180f, 0f);
        this.redFoot = new Location(getWorld(), -1, 120, 10);
        this.redHead = new Location(getWorld(), -1, 120, 11);

    }

    public void start() {
        preparePlayer(blue, blueSpawn);
        preparePlayer(red, redSpawn);

        setScoreboard(blue);
        setScoreboard(red);

    }

    public void stop() {
        MLGRush.getInstance().configLobbyPlayer(blue);
        MLGRush.getInstance().configLobbyPlayer(red);

        blue.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        red.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        blocks.forEach(block -> block.setType(Material.AIR));
        MatchHandler.getInstance().unblockBox(world);

        DataHandler.getInstance().getPlayer(blue.getUniqueId()).setMatch(null);
        DataHandler.getInstance().getPlayer(red.getUniqueId()).setMatch(null);

        if(bluePoints >= 10) {
            blue.sendMessage(MLGRush.getInstance().getPrefix() +
                    "Du hast das Spiel gegen " + ChatColor.BLUE + " " + red.getName() + " " + ChatColor.GRAY + "gewonnen.");
            red.sendMessage(MLGRush.getInstance().getPrefix() +
                    "Du hast das Spiel gegen " + ChatColor.BLUE + " " + blue.getName() + " " + ChatColor.GRAY + "verloren.");
        } else if (redPoints >= 10) {
            red.sendMessage(MLGRush.getInstance().getPrefix() +
                    "Du hast das Spiel gegen " + ChatColor.BLUE + " " + blue.getName() + " " + ChatColor.GRAY + "gewonnen.");
            blue.sendMessage(MLGRush.getInstance().getPrefix() +
                    "Du hast das Spiel gegen " + ChatColor.BLUE + " " + red.getName() + " " + ChatColor.GRAY + "verloren.");
        }

    }

    public void resetPlayer(Player player) {
        if(player.equals(blue)) {
            preparePlayer(blue, blueSpawn);
        }else if(player.equals(red)) {
            preparePlayer(red, redSpawn);
        }
    }

    public void blueWinRound() {
        preparePlayer(blue, blueSpawn);
        preparePlayer(red, redSpawn);

        blue.sendMessage(MLGRush.getInstance().getPrefix() +
                ChatColor.BLUE + "Blau " + ChatColor.GRAY + "hat die Runde gewonnen.");
        red.sendMessage(MLGRush.getInstance().getPrefix() +
                ChatColor.BLUE + "Blau " + ChatColor.GRAY + "hat die Runde gewonnen.");
        bluePoints++;
        updateScoreboard();
        if(bluePoints >= 10) stop();
    }

    public void redWinRound() {
        preparePlayer(blue, blueSpawn);
        preparePlayer(red, redSpawn);

        blue.sendMessage(MLGRush.getInstance().getPrefix() +
                ChatColor.RED + "Rot " + ChatColor.GRAY + "hat die Runde gewonnen.");
        red.sendMessage(MLGRush.getInstance().getPrefix() +
                ChatColor.RED + "Rot " + ChatColor.GRAY + "hat die Runde gewonnen.");

        redPoints++;
        updateScoreboard();
        if(redPoints >= 10) stop();
    }

    private void preparePlayer(Player player, Location location) {
        int stick = DataHandler.getInstance().getPlayer(player.getUniqueId()).getInventory()
                .first(DataHandler.getInstance().getStick().getType());
        int pickaxe = DataHandler.getInstance().getPlayer(player.getUniqueId()).getInventory()
                .first(DataHandler.getInstance().getPickaxe().getType());
        int blocks = DataHandler.getInstance().getPlayer(player.getUniqueId()).getInventory()
                .first(DataHandler.getInstance().getBlocks().getType());

        player.teleport(location);
        player.setRespawnLocation(location);
        player.getInventory().clear();
        player.getInventory().setItem(stick, DataHandler.getInstance().getStick());
        player.getInventory().setItem(pickaxe, DataHandler.getInstance().getPickaxe());
        player.getInventory().setItem(blocks, DataHandler.getInstance().getBlocks());
        player.setHealth(20);
    }

    private void setScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Scoreboard board = player.getScoreboard();

        Team blueTeam = board.registerNewTeam("blue");
        blueTeam.addEntry(ChatColor.BLUE + blue.getName() + " ");
        blueTeam.setSuffix(ChatColor.GRAY + String.valueOf(bluePoints));

        Team redTeam = board.registerNewTeam("red");
        redTeam.addEntry(ChatColor.RED + red.getName() + " ");
        redTeam.setSuffix(ChatColor.GRAY + String.valueOf(redPoints));

        Objective obj = board.registerNewObjective("match", Criteria.DUMMY, ChatColor.BLUE + "MLG-Rush");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore(ChatColor.BLUE + blue.getName() + " ").setScore(0);
        obj.getScore(ChatColor.RED + red.getName() + " ").setScore(1);

    }

    private void updateScoreboard() {
        blue.getScoreboard().getTeam("blue").setSuffix(ChatColor.GRAY + String.valueOf(bluePoints));
        blue.getScoreboard().getTeam("red").setSuffix(ChatColor.GRAY + String.valueOf(redPoints));
        red.getScoreboard().getTeam("blue").setSuffix(ChatColor.GRAY + String.valueOf(bluePoints));
        red.getScoreboard().getTeam("red").setSuffix(ChatColor.GRAY + String.valueOf(redPoints));
    }
}
