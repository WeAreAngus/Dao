package software.angus.dao.weapons;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import software.angus.dao.Quality;

import java.util.Random;

public class Broadsword implements CommandExecutor {

    private ItemStack createItem() {
        Random random = new Random();

        ItemStack goldenSword = new ItemStack(Material.GOLDEN_SWORD);

        int resultPhysical = random.nextInt(50);
        int resultPoison = random.nextInt(50);
        int resultFire = random.nextInt(50);
        int resultIce = random.nextInt(50);

        WeaponCreator weaponCreator = new WeaponCreator(goldenSword, "Spirit Sword", "Broadsword",
                Quality.NORMAL, 10, resultPhysical, resultPoison,
                resultFire, resultIce);
        ItemMeta itemMeta = weaponCreator.build();
        goldenSword.setItemMeta(itemMeta);
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
