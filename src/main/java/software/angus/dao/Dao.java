package software.angus.dao;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import software.angus.dao.weapons.SpiritSword;

import java.lang.reflect.Field;
import java.util.Objects;

public final class Dao extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new Power(), this);
        this.getServer().getPluginManager().registerEvents(new Enchants(), this);
        this.getCommand("spiritsword").setExecutor(new SpiritSword());

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
