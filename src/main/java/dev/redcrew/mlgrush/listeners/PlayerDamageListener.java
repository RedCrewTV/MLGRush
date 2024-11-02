package dev.redcrew.mlgrush.listeners;

import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

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
public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if(player.getWorld().getName().equals("world") || event.getDamageSource().getDamageType().equals(DamageType.FALL)) event.setCancelled(true);
        }
    }

}
