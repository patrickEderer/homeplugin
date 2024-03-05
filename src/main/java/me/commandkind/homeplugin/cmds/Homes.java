package me.commandkind.homeplugin.cmds;

import me.commandkind.homeplugin.homeplugin;
import me.commandkind.homeplugin.util.HomeList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Homes implements Listener, CommandExecutor {

    private Map<UUID, List<HomeList>> homes;

    public Homes(Map<UUID, List<HomeList>> homes, homeplugin plugin) {
        this.homes = homes;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Homes"))
            return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        try {
            player.teleport(homes.get(player.getUniqueId()).get(slot).getLocation());
        } catch (Exception ignored) {}

        event.setCancelled(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return true;
        }

        Player player = (Player) sender;

        Inventory inv = Bukkit.createInventory(player, (9 * 3), "Homes");

        try {
            for (int i = 0; i < homes.get(player.getUniqueId()).size(); i++) {
                ItemStack item = new ItemStack(homes.get(player.getUniqueId()).get(i).getMaterial());
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RESET + homes.get(player.getUniqueId()).get(i).getName());
                item.setItemMeta(meta);
                inv.setItem(i, item);
            }
        } catch (Exception ignored) {}

        player.openInventory(inv);

        return true;
    }
}
