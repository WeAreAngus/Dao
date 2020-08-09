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

import java.util.UUID;

public class SpiritSword implements CommandExecutor {
    public static ItemStack createItem() {
        ItemStack goldenSword = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta goldenSwordMeta = goldenSword.getItemMeta();
        goldenSwordMeta.setDisplayName(ChatColor.BLUE + "Spirit Sword");
        goldenSwordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        goldenSwordMeta.setUnbreakable(true);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        goldenSwordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        goldenSword.setItemMeta(goldenSwordMeta);
        return goldenSword;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = (Player) sender;
        target.getInventory().addItem(createItem());
        return true;
    }
}
