package software.angus.dao;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.util.Objects;

public class Power implements Listener {

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setHealthScaled(true);
    }
    @EventHandler
    public void OnLevelUp(PlayerLevelChangeEvent e) {
        Player player = e.getPlayer(); int level = player.getLevel();
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(100+Math.pow(level, 2));
        e.getPlayer().setHealthScaled(true);
    }
    @EventHandler
    public void OnRegen(EntityRegainHealthEvent e) {
        if(e.getEntity() instanceof Player && e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
            Player player = (Player) e.getEntity();
            double maxHealth = (Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            e.setAmount(maxHealth * 0.005);
        }
    }
}
