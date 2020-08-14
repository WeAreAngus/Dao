package software.angus.dao;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DamageAdder {
    private static final Dao dao = JavaPlugin.getPlugin(Dao.class);
    private static final NamespacedKey keyCriticalChance = new NamespacedKey(dao, "critical_chance");
    private static final NamespacedKey keyCriticalDamage = new NamespacedKey(dao, "critical_damage");
    private static final NamespacedKey keyPhysicalDamage = new NamespacedKey(dao, "physical_damage");
    private static final NamespacedKey keyPhysicalArmour = new NamespacedKey(dao, "physical_armour");
    private static final NamespacedKey keyPhysicalToughness = new NamespacedKey(dao, "physical_toughness");
    private static final NamespacedKey keyPoisonDamage = new NamespacedKey(dao, "poison_damage");
    private static final NamespacedKey keyPoisonArmour = new NamespacedKey(dao, "poison_armour");
    private static final NamespacedKey keyPoisonToughness = new NamespacedKey(dao, "poison_toughness");
    private static final NamespacedKey keyFireDamage = new NamespacedKey(dao, "fire_damage");
    private static final NamespacedKey keyFireArmour = new NamespacedKey(dao, "fire_armour");
    private static final NamespacedKey keyFireToughness = new NamespacedKey(dao, "fire_toughness");
    private static final NamespacedKey keyIceDamage = new NamespacedKey(dao, "ice_damage");
    private static final NamespacedKey keyIceArmour = new NamespacedKey(dao, "ice_armour");
    private static final NamespacedKey keyIceToughness = new NamespacedKey(dao, "fire_armour");

    public static void addCriticalChance(ItemMeta itemMeta, double value) {
        itemMeta.getPersistentDataContainer().set(keyCriticalChance, PersistentDataType.DOUBLE, value);
    }
    public static void addCriticalDamage(ItemMeta itemMeta, double value) {
        itemMeta.getPersistentDataContainer().set(keyCriticalDamage, PersistentDataType.DOUBLE, value);
    }
    public static void addPhysicalDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyPhysicalDamage, PersistentDataType.DOUBLE, damage);
    }
    public static void addPhysicalProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyPhysicalArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyPhysicalToughness, PersistentDataType.DOUBLE, toughness);
    }

    public static void addPoisonDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyPoisonDamage, PersistentDataType.DOUBLE, damage);
    }

    public static void addPoisonProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyPoisonArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyPoisonToughness, PersistentDataType.DOUBLE, toughness);
    }

    public static void addFireDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyFireDamage, PersistentDataType.DOUBLE, damage);
    }

    public static void addFireProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyFireArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyFireToughness, PersistentDataType.DOUBLE, toughness);
    }

    public static void addIceDamage(ItemMeta itemMeta, double damage) {
        itemMeta.getPersistentDataContainer().set(keyIceDamage, PersistentDataType.DOUBLE, damage);
    }

    public static void addIceProtection(ItemMeta itemMeta, double armour, double toughness) {
        itemMeta.getPersistentDataContainer().set(keyIceArmour, PersistentDataType.DOUBLE, armour);
        itemMeta.getPersistentDataContainer().set(keyIceToughness, PersistentDataType.DOUBLE, toughness);
    }
}
