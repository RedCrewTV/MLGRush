package dev.redcrew.mlgrush.queue;

import dev.redcrew.mlgrush.MLGRush;
import dev.redcrew.mlgrush.match.MatchHandler;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedList;

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
public class QueueHandler {

    @Getter
    private static final QueueHandler instance = new QueueHandler();

    private final LinkedList<Player> queue = new LinkedList<>();

    private QueueHandler() {}

    public void joinQueue(Player player) {
        player.sendMessage(MLGRush.getInstance().getPrefix() +
                "Du bist der" + ChatColor.BLUE + " Queue " + ChatColor.GRAY + "Beigetreten.");
        queue.add(player);
        if(queue.size() >= 2) buildMatch(queue.get(0), queue.get(1));
    }

    public void leaveQueue(Player player) {
        player.sendMessage(MLGRush.getInstance().getPrefix() +
                "Du hast die" + ChatColor.BLUE + " Queue " + ChatColor.GRAY + "verlassen.");
        queue.remove(player);
    }

    public void silentLeaveQueue(Player player) {
        queue.remove(player);
    }

    public boolean containsQueue(Player player) {
        return queue.contains(player);
    }

    public void buildMatch(Player player, Player player2) {
        silentLeaveQueue(player);
        silentLeaveQueue(player2);
        MatchHandler.getInstance().createMatch(player, player2);
    }

}
