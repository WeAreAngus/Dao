package software.angus.dao.weapons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import software.angus.dao.Dao;
import software.angus.dao.Enchants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SpiritSword implements CommandExecutor {
    public static ItemStack createItem() {
        Enchants enchants = new Enchants();
        Random random = new Random();
        ItemStack goldenSword = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta goldenSwordMeta = goldenSword.getItemMeta();
        goldenSwordMeta.setDisplayName(ChatColor.BLUE + "Spirit Sword");
        goldenSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        goldenSwordMeta.setUnbreakable(true);
        
        int resultphys = random.nextInt(50);
        int resultpoison = random.nextInt(50);

        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", resultphys,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        goldenSwordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        ItemMeta goldenSwordMetaPoison = enchants.addPoisonDamage(goldenSwordMeta, resultpoison);
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.DARK_RED + String.valueOf(resultphys) + ChatColor.BLUE + " Physical Damage");
        lore.add(ChatColor.DARK_GREEN + String.valueOf(resultpoison) + ChatColor.BLUE + " Poison Damage");
        goldenSwordMetaPoison.setLore(lore);
        goldenSword.setItemMeta(goldenSwordMetaPoison);
        return goldenSword;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = (Player) sender;
        ItemStack item = createItem();
        target.getInventory().addItem(item);
        return true;
    }
}
