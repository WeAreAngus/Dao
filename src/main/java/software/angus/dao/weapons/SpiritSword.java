package software.angus.dao.weapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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

        int resultPhysical = random.nextInt(50);
        int resultPoison = random.nextInt(50);
        int resultFire = random.nextInt(50);
        int resultIce = random.nextInt(50);

        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", resultPhysical,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        goldenSwordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        enchants.addPoisonDamage(goldenSwordMeta, resultPoison);
        enchants.addFireDamage(goldenSwordMeta, resultFire);
        enchants.addIceDamage(goldenSwordMeta, resultIce);
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + String.valueOf(resultPhysical) + ChatColor.BLUE + " Physical Damage");
        lore.add(ChatColor.DARK_GREEN + String.valueOf(resultPoison) + ChatColor.BLUE + " Poison Damage");
        lore.add(ChatColor.DARK_RED + String.valueOf(resultFire) + ChatColor.BLUE + " Fire Damage");
        lore.add(ChatColor.DARK_BLUE + String.valueOf(resultIce) + ChatColor.BLUE + " Ice Damage");

        goldenSwordMeta.setLore(lore);
        goldenSword.setItemMeta(goldenSwordMeta);
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
