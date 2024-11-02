package dev.redcrew.mlgrush.match;

import dev.redcrew.mlgrush.data.DataHandler;
import dev.redcrew.mlgrush.queue.QueueHandler;
import lombok.Getter;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class MatchHandler {

    @Getter
    private static final MatchHandler instance = new MatchHandler();

    private final Map<World, Boolean> boxes = new HashMap<>();

    private MatchHandler() {}

    public void registerBox(World world) {
        world.setTime(5000);
        world.setClearWeatherDuration(200000);
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        boxes.put(world, false);
    }

    public void createMatch(Player player, Player player2) {
        for (World world : boxes.keySet()) {
            if(!boxes.get(world)) {
                Match match = new Match(player, player2, world);
                boxes.replace(world, true);
                DataHandler.getInstance().getPlayer(player.getUniqueId()).setMatch(match);
                DataHandler.getInstance().getPlayer(player2.getUniqueId()).setMatch(match);
                match.start();
                break;
            }
        }
    }

    public void unblockBox(World world) {
        boxes.replace(world, false);
    }


}
