package software.angus.dao;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Enchants implements Listener {
    NamespacedKey keyPoisonDamage = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "poison_damage");
    NamespacedKey keyPoisonArmour = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "poison_armour");
    NamespacedKey keyPoisonToughness = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "poison_toughness");
    NamespacedKey keyFireArmour = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "fire_armour");
    NamespacedKey keyFireToughness = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "fire_toughness");
    NamespacedKey keyIceDamage = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "ice_damage");
    NamespacedKey keyIceArmour = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "ice_armour");
    NamespacedKey keyIceToughness = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "fire_armour");

    private double getPD(ItemStack itemStack, NamespacedKey key) {
        double result = 0;
        //noinspection ConstantConditions
        if (!itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) {
            PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
            if (container.has(key, PersistentDataType.DOUBLE)) {
                result = container.get(key, PersistentDataType.DOUBLE);
            }
        }
        return result;
    }
    private List<ItemStack> getEQ(LivingEntity entity) {
        List<ItemStack> Equipment = new ArrayList<>();
        EntityEquipment entityQ = entity.getEquipment();
        //noinspection ConstantConditions
        if(entityQ.getBoots().getType() != Material.AIR) Equipment.add(entityQ.getBoots());
        if(entityQ.getLeggings().getType() != Material.AIR)Equipment.add(entityQ.getLeggings());
        if(entityQ.getChestplate().getType() != Material.AIR)Equipment.add(entityQ.getChestplate());
        if(entityQ.getHelmet().getType() != Material.AIR) Equipment.add(entityQ.getHelmet());
        return Equipment;
    }
    public ItemMeta addPoisonDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyPoisonDamage, PersistentDataType.DOUBLE, damage);
        Bukkit.broadcastMessage("SUCCESS");
        return itemMeta;
    }
    public ItemMeta addPoisonProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyPoisonArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyPoisonToughness, PersistentDataType.DOUBLE, toughness);
        return itemMeta;
    }
    @EventHandler
    public void OnPoisonDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof LivingEntity) {
            LivingEntity entityATT = (LivingEntity) e.getDamager();
            LivingEntity entityDEF = (LivingEntity) e.getEntity();
            //noinspection ConstantConditions
            ItemStack itemStackATT = entityATT.getEquipment().getItemInMainHand();
            //noinspection ConstantConditions
            if(itemStackATT == null || itemStackATT.getItemMeta().getPersistentDataContainer().isEmpty()) return;
            PersistentDataContainer container = itemStackATT.getItemMeta().getPersistentDataContainer();
            if(!container.has(keyPoisonDamage, PersistentDataType.DOUBLE)) return;

            List<ItemStack> Equipment = getEQ(entityDEF);
            double defense = 20;
            double toughness = 0;
            double damage = getPD(itemStackATT, keyPoisonDamage);
            if(!Equipment.isEmpty()) {
                for (ItemStack stack : Equipment) {
                    defense = +getPD(stack, keyPoisonArmour);
                }
                for (ItemStack stack : Equipment) {
                    toughness=+getPD(stack, keyPoisonToughness);
                }
            }
            double damageTaken = damage * (1- ((Math.min(20, Math.max((defense/5), (defense-(damage/(2+(toughness/4)))))))/25));

            if(e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();
                player.sendMessage("You've dealt " + damageTaken + " poison damage ");
            }
            double remainderHealth = entityDEF.getHealth() - damageTaken;
            if(damageTaken > entityDEF.getHealth()) remainderHealth = 0;
            entityDEF.setHealth(remainderHealth);
        }
    }

    public ItemMeta addFireDamage(ItemMeta itemMeta, double damage) {
        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getPlugin(Dao.class), "fire_damage");
        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.DOUBLE, damage);
        return itemMeta;
    }
    public ItemMeta addFireProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyFireArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyFireToughness, PersistentDataType.DOUBLE, toughness);
        return itemMeta;
    }

    public ItemMeta addIceDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyIceDamage, PersistentDataType.DOUBLE, damage);
        return itemMeta;
    }
    public ItemMeta addIceProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyIceArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyIceToughness, PersistentDataType.DOUBLE, toughness);
        return itemMeta;
    }
}
