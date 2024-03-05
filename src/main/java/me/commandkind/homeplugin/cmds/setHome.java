package me.commandkind.homeplugin.cmds;

import me.commandkind.homeplugin.util.HomeList;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class setHome implements CommandExecutor {

    private Map<UUID, List<HomeList>> homes;
    private int maxHomes;

    public setHome(Map<UUID, List<HomeList>> homes, int maxHomes) {
        this.homes = homes;
        this.maxHomes = maxHomes;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return true;
        }

        Player player = (Player) sender;

        try {
            if (homes.get(player.getUniqueId()).size() >= maxHomes) {
                player.sendMessage("You already have too many homes, try to remove some using /removeHome <Name>");
                return true;
            }
        } catch (Exception ignored) {}

        if (args.length == 0) {
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                homes.get(player.getUniqueId()).add(new HomeList(player.getLocation(), player.getInventory().getItemInMainHand().getType(), "Home " + (homes.get(player.getUniqueId()).size() + 1)));
            } else {
                homes.get(player.getUniqueId()).add(new HomeList(player.getLocation(), Material.BEACON, "Home " + (homes.get(player.getUniqueId()).size() + 1)));
            }

            player.sendMessage("Home 'Home " + (homes.get(player.getUniqueId()).size()) + "' has been set successfully!");
        } else {
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                homes.get(player.getUniqueId()).add(new HomeList(player.getLocation(), player.getInventory().getItemInMainHand().getType(), args[0]));
            } else {
                homes.get(player.getUniqueId()).add(new HomeList(player.getLocation(), Material.BEACON, args[0]));
            }

            player.sendMessage("Home '" + args[0] + "' has been set successfully!");
        }

        return true;
    }
}
