package software.angus.dao;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Enchants implements Listener {
    Dao dao = JavaPlugin.getPlugin(Dao.class);
    NamespacedKey keyPoisonDamage = new NamespacedKey(dao, "poison_damage");
    NamespacedKey keyPoisonArmour = new NamespacedKey(dao, "poison_armour");
    NamespacedKey keyPoisonToughness = new NamespacedKey(dao, "poison_toughness");
    NamespacedKey keyFireDamage = new NamespacedKey(dao, "fire_damage");
    NamespacedKey keyFireArmour = new NamespacedKey(dao, "fire_armour");
    NamespacedKey keyFireToughness = new NamespacedKey(dao, "fire_toughness");
    NamespacedKey keyIceDamage = new NamespacedKey(dao, "ice_damage");
    NamespacedKey keyIceArmour = new NamespacedKey(dao, "ice_armour");
    NamespacedKey keyIceToughness = new NamespacedKey(dao, "fire_armour");

    @SuppressWarnings("ConstantConditions")
    private double getPD(ItemStack itemStack, NamespacedKey key) {
        double result = 0;
        if (!itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) {
            PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
            if (container.has(key, PersistentDataType.DOUBLE)) {
                result = container.get(key, PersistentDataType.DOUBLE);
            }
        }
        return result;
    }
    @SuppressWarnings("ConstantConditions")
    private List<ItemStack> getEQ(LivingEntity entity) {
        List<ItemStack> Equipment = new ArrayList<>();
        EntityEquipment entityQ = entity.getEquipment();

        if(entityQ.getBoots().getType() != Material.AIR) Equipment.add(entityQ.getBoots());
        if(entityQ.getLeggings().getType() != Material.AIR)Equipment.add(entityQ.getLeggings());
        if(entityQ.getChestplate().getType() != Material.AIR)Equipment.add(entityQ.getChestplate());
        if(entityQ.getHelmet().getType() != Material.AIR) Equipment.add(entityQ.getHelmet());
        return Equipment;
    }

    private void DamageHandler(EntityDamageByEntityEvent e, NamespacedKey keyDamage, NamespacedKey keyArmour, NamespacedKey keyToughness) {
        LivingEntity entityATT = (LivingEntity) e.getDamager();
        LivingEntity entityDEF = (LivingEntity) e.getEntity();
        //noinspection ConstantConditions
        ItemStack itemStackATT = entityATT.getEquipment().getItemInMainHand();
        //noinspection ConstantConditions
        if(itemStackATT == null || itemStackATT.getType() == Material.AIR) return;
        PersistentDataContainer container = itemStackATT.getItemMeta().getPersistentDataContainer();
        if(!container.has(keyPoisonDamage, PersistentDataType.DOUBLE)) return;

        List<ItemStack> Equipment = getEQ(entityDEF);
        double defense = 0;
        double toughness = 0;
        double damage = getPD(itemStackATT, keyDamage);
        if(!Equipment.isEmpty()) {
            for (ItemStack stack : Equipment) {
                defense = +getPD(stack, keyArmour);
            }
            for (ItemStack stack : Equipment) {
                toughness=+getPD(stack, keyToughness);
            }
        }
        double damageTaken = damage * (1- ((Math.min(20, Math.max((defense/5), (defense-(damage/(2+(toughness/4)))))))/25));
        double remainderHealth = entityDEF.getHealth() - damageTaken;
        if(damageTaken > entityDEF.getHealth()) remainderHealth = 0;
        entityDEF.setHealth(remainderHealth);
    }

    public void addPoisonDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyPoisonDamage, PersistentDataType.DOUBLE, damage);
    }
    public void addPoisonProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyPoisonArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyPoisonToughness, PersistentDataType.DOUBLE, toughness);
    }
    @EventHandler
    public void OnPoisonDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof LivingEntity) {
            LivingEntity entityDEF = (LivingEntity) e.getEntity();
            LivingEntity entityATT = (LivingEntity) e.getDamager();
            ItemStack itemStackATT = Objects.requireNonNull(entityATT.getEquipment()).getItemInMainHand();
            //noinspection ConstantConditions
            if(itemStackATT == null || itemStackATT.getType() == Material.AIR) return;
            DamageHandler(e, keyPoisonDamage, keyPoisonArmour, keyPoisonToughness);
            entityDEF.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10, 0));
        }
    }

    public void addFireDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyFireDamage, PersistentDataType.DOUBLE, damage);
    }
    public void addFireProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyFireArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyFireToughness, PersistentDataType.DOUBLE, toughness);
    }

    @EventHandler
    public void OnFireDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof LivingEntity) {
            LivingEntity entityDEF = (LivingEntity) e.getEntity();
            LivingEntity entityATT = (LivingEntity) e.getDamager();
            ItemStack itemStackATT = Objects.requireNonNull(entityATT.getEquipment()).getItemInMainHand();
            //noinspection ConstantConditions
            if(itemStackATT == null || itemStackATT.getType() == Material.AIR) return;

            DamageHandler(e, keyFireDamage, keyFireArmour, keyFireToughness);
            entityDEF.setFireTicks(100);
        }
    }

    public void addIceDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyIceDamage, PersistentDataType.DOUBLE, damage);
    }
    public void addIceProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyIceArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyIceToughness, PersistentDataType.DOUBLE, toughness);
    }
    @EventHandler
    public void OnIceDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof LivingEntity) {
            //noinspection DuplicatedCode
            LivingEntity entityDEF = (LivingEntity) e.getEntity();
            LivingEntity entityATT = (LivingEntity) e.getDamager();
            ItemStack itemStackATT = Objects.requireNonNull(entityATT.getEquipment()).getItemInMainHand();
            //noinspection ConstantConditions
            if(itemStackATT == null || itemStackATT.getType() == Material.AIR) return;
            DamageHandler(e, keyIceDamage, keyIceArmour, keyIceToughness);
            entityDEF.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 0));
        }
    }
}
