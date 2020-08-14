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
import software.angus.dao.DamageController;
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

        NameColour.put(Quality.NORMAL, ChatColor.WHITE);
        NameColour.put(Quality.RARE, ChatColor.BLUE);
        NameColour.put(Quality.EPIC, ChatColor.DARK_PURPLE);
        NameColour.put(Quality.LEGENDARY, ChatColor.YELLOW);
        NameColour.put(Quality.MYTHICAL, ChatColor.LIGHT_PURPLE);
    }
    List<String> lore = new ArrayList<>();
    ItemStack itemStack = new ItemStack(Material.WOODEN_SWORD);
    double PhysicalDamage = 0;
    double PoisonDamage = 0;
    double FireDamage = 0;
    double IceDamage = 0;
    double CriticalChance = 0;
    double CriticalDamage = 0;
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
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    public void setCriticalDamage(double criticalDamage) {
        this.CriticalDamage = criticalDamage;
    }
    public void setCriticalChance(double criticalChance) {
        this.CriticalChance = criticalChance;
    }

    public ItemMeta build() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setDisplayName(NameColour.get(quality) + weaponName);
        itemMeta.setUnbreakable(true);

        if(this.PhysicalDamage != 0)   {
            DamageAdder.addPhysicalDamage(itemMeta, PhysicalDamage);
            lore.add(ChatColor.DARK_GRAY + "Physical Damage: " + ChatColor.DARK_AQUA + Math.round(PhysicalDamage));
        }

        lore.add(ChatColor.DARK_GRAY + "Elemental Damage: " + ChatColor.DARK_BLUE + (this.PoisonDamage + this.IceDamage + this.FireDamage));
        lore.add(ChatColor.DARK_GRAY + "Critical Chance: " + ChatColor.DARK_AQUA + "+" + this.CriticalChance + "%");
        lore.add(ChatColor.DARK_GRAY + "Critical Multiplier: " + ChatColor.DARK_AQUA + "+" + this.CriticalDamage + "%");

        DamageAdder.addCriticalChance(itemMeta, CriticalChance);DamageAdder.addCriticalDamage(itemMeta, CriticalDamage);
        lore.add("");
        if(this.PoisonDamage != 0) {
            DamageAdder.addPoisonDamage(itemMeta, PoisonDamage);
            lore.add(ChatColor.BLUE + "Adds " + ChatColor.GREEN + Math.round(PoisonDamage) + ChatColor.BLUE + " to " + ChatColor.GREEN + "Poison Damage");
        }
        if(this.FireDamage != 0) {
            DamageAdder.addFireDamage(itemMeta, FireDamage);
            lore.add(ChatColor.BLUE + "Adds " + ChatColor.DARK_RED + Math.round(FireDamage) + ChatColor.BLUE + " to " + ChatColor.DARK_RED + "Fire Damage" );
        }
        if(this.IceDamage != 0) {
            DamageAdder.addIceDamage(itemMeta, IceDamage);
            lore.add(ChatColor.BLUE + "Adds " + ChatColor.DARK_BLUE + Math.round(IceDamage) + ChatColor.BLUE + " to " + ChatColor.DARK_BLUE + "Ice Damage");
        }

        lore.add("");
        lore.add(ColourQuality.get(quality));

        itemMeta.setLore(lore);

        return itemMeta;
    }
}
