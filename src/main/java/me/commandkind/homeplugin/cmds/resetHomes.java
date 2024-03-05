package me.commandkind.homeplugin.cmds;

import me.commandkind.homeplugin.util.HomeList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class resetHomes implements CommandExecutor {

    private Map<UUID, List<HomeList>> homes;

    public resetHomes(Map<UUID, List<HomeList>> homes) {
        this.homes = homes;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            homes.clear();
            Bukkit.getLogger().warning("Success! Homeplugin won't work until next rejoin!");
            sender.sendMessage(ChatColor.RED + "Success! Homeplugin won't work until next rejoin!");
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        homes.get(player.getUniqueId()).clear();

        return true;
    }
}
