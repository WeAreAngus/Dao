package software.angus.dao;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import software.angus.dao.weapons.Broadsword;

import java.util.Objects;

@SuppressWarnings("ConstantConditions")
public final class Dao extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new Power(), this);
        this.getServer().getPluginManager().registerEvents(new DamageController(), this);
        this.getCommand("broadsword").setExecutor(new Broadsword());

        //Bigger health action bar
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                    double health = (Math.round(player.getHealth()));
                    double maxHealth = (Math.round(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()));
                    if (health > maxHealth) player.sendActionBar(ChatColor.GOLD + String.valueOf(Math.round(health)) + "❤/" + ChatColor.RED + Math.round(maxHealth) + "❤");
                    else player.sendActionBar(ChatColor.RED + String.valueOf(Math.round(health)) + "❤/" + Math.round(maxHealth) + "❤");
                }
            }
        }.runTaskTimer(this, 1L, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
