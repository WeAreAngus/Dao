package software.angus.dao.weapons;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import software.angus.dao.Quality;

import java.util.Random;

public class Broadsword implements CommandExecutor {

    private ItemStack createItem() {
        Random random = new Random();

        ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD);

        int resultPhysical = random.nextInt(50);
        int resultPoison = random.nextInt(50);
        int resultFire = random.nextInt(50);
        int resultIce = random.nextInt(50);

        WeaponCreator weapon = new WeaponCreator();
        weapon.setItemStack(woodenSword);
        weapon.setWeaponName("Broken Sword");
        weapon.setWeaponType("Broadsword");
        weapon.setQuality(Quality.MYTHICAL);
        weapon.setFireDamage(resultFire);
        weapon.setIceDamage(resultIce);
        weapon.setPhysicalDamage(resultPhysical);
        weapon.setPoisonDamage(resultPoison);
        ItemMeta itemMeta = weapon.build();
        woodenSword.setItemMeta(itemMeta);
        return woodenSword;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = (Player) sender;
        ItemStack item = createItem();
        target.getInventory().addItem(item);
        return true;
    }
}
