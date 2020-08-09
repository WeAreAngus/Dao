package software.angus.dao;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(10+(Math.pow(level, 1.5)));
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(100+Math.pow(level, 2.5));
        e.getPlayer().setHealthScaled(true);
    }
}
