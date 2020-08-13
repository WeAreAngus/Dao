package software.angus.dao.weapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import software.angus.dao.DamageAdder;
import software.angus.dao.Quality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class WeaponCreator {

    HashMap<Quality, String> ColourQuality= new HashMap<>();
    HashMap<Quality, ChatColor> NameColour = new HashMap<>();
    {
        ColourQuality.put(Quality.NORMAL, ChatColor.WHITE + "" + ChatColor.BOLD + "NORMAL");
        ColourQuality.put(Quality.RARE, ChatColor.BLUE + "" + ChatColor.BOLD + "RARE");
        ColourQuality.put(Quality.EPIC, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "EPIC");
        ColourQuality.put(Quality.LEGENDARY, ChatColor.YELLOW + "" + ChatColor.BOLD + "LEGENDARY");
        ColourQuality.put(Quality.MYTHICAL, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" +
                ChatColor.MAGIC + "M" + ChatColor.RESET + "" + ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "MYTHICAL" +
                ChatColor.BOLD + "" + ChatColor.MAGIC + "M");
    }
    List<String> lore = new ArrayList<>();
    ItemStack itemStack = new ItemStack(Material.WOODEN_SWORD);
    double PhysicalDamage = 0;
    double PoisonDamage = 0;
    double FireDamage = 0;
    double IceDamage = 0;
    String weaponType;
    String weaponName;
    Quality quality = Quality.NORMAL;


    public void setFireDamage(double fireDamage) {
        this.FireDamage = fireDamage;
    }
    public void setIceDamage(double iceDamage) {
        this.IceDamage = iceDamage;
    }
    public void setPoisonDamage(double poisonDamage) {
        this.PoisonDamage = poisonDamage;
    }
    public void setPhysicalDamage(double physicalDamage) {
        this.PhysicalDamage = physicalDamage;
    }
    public void setQuality(Quality quality) {
        this.quality = quality;
    }
    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }
    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemMeta build() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setDisplayName(weaponName);

        lore.add(weaponType);

        if(this.PhysicalDamage != 0)   {
            AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", PhysicalDamage,
                    AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
            lore.add(ChatColor.BLUE + "ADDS " + Math.round(PhysicalDamage) + " to Physical Damage");
        }
        if(this.PoisonDamage != 0) {
            DamageAdder.addPoisonDamage(itemMeta, PoisonDamage);
            lore.add(ChatColor.BLUE + "ADDS " + Math.round(PoisonDamage) + " to Poison Damage");
        }
        if(this.FireDamage != 0) {
            DamageAdder.addFireDamage(itemMeta, FireDamage);
            lore.add(ChatColor.BLUE + "ADDS " + Math.round(FireDamage) + " to Fire Damage");
        }
        if(this.IceDamage != 0) {
            DamageAdder.addIceDamage(itemMeta, IceDamage);
            lore.add(ChatColor.BLUE + "ADDS " + Math.round(IceDamage) + " to Ice Damage");
        }
        lore.add(ColourQuality.get(quality));

        itemMeta.setLore(lore);

        return itemMeta;
    }
}
