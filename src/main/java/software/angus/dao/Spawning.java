package software.angus.dao;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class Spawning implements Listener {

    @EventHandler
    public void OnMobSpawning(EntitySpawnEvent e) {
        if(e.getEntity() instanceof Monster) {
            LivingEntity entity = (LivingEntity) e.getEntity();

        }

    }
}
