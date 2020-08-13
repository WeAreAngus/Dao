package software.angus.dao.weapons;

import org.bukkit.ChatColor;
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
    ItemMeta weapon;
    double PhysicalDamage = 0;
    double PoisonDamage = 0;
    double FireDamage = 0;
    double IceDamage = 0;

    public ItemMeta build() {
        return this.weapon;
    }
    public WeaponCreator(ItemStack itemStack, String itemName, String weaponType, Quality quality, double levelRequirement, double PhysicalDamage, double PoisonDamage, double FireDamage, double IceDamage) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        HashMap<Quality, ChatColor> ColourQuality= new HashMap<>();

                    ColourQuality.put(Quality.NORMAL, ChatColor.WHITE);ColourQuality.put(Quality.RARE, ChatColor.BLUE);
                    ColourQuality.put(Quality.EPIC, ChatColor.DARK_PURPLE);ColourQuality.put(Quality.LEGENDARY, ChatColor.YELLOW);
                    ColourQuality.put(Quality.MYTHICAL, ChatColor.LIGHT_PURPLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setDisplayName(itemName);
        lore.add(weaponType);
        lore.add(ColourQuality.get(quality) + "Quality:" + quality.name());
        lore.add("Level requirement:" + levelRequirement);

        if(PhysicalDamage != 0)   {
            AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", PhysicalDamage,
                    AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
            lore.add(ChatColor.BLUE + "ADDS " + PhysicalDamage + " to Physical Damage");
        }
        if(PoisonDamage != 0) {
            DamageAdder.addPoisonDamage(itemMeta, PoisonDamage);
            lore.add(ChatColor.BLUE + "ADDS " + PoisonDamage + " to Poison Damage");
        }
        if(FireDamage != 0) {
            DamageAdder.addFireDamage(itemMeta, FireDamage);
            lore.add(ChatColor.BLUE + "ADDS " + FireDamage + " to Fire Damage");
        }
        if(IceDamage != 0) {
            DamageAdder.addIceDamage(itemMeta, IceDamage);
            lore.add(ChatColor.BLUE + "ADDS " + IceDamage + " to Ice Damage");
        }

        itemMeta.setLore(lore);

        this.weapon = itemMeta;
    }
}
