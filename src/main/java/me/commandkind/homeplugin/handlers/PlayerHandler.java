package me.commandkind.homeplugin.handlers;

import me.commandkind.homeplugin.homeplugin;
import me.commandkind.homeplugin.util.HomeList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;

public class PlayerHandler implements Listener {
    private Map<UUID, List<HomeList>> homes = new HashMap<>();
    public PlayerHandler(homeplugin plugin, Map<UUID, List<HomeList>> homes) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.homes = homes;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {
            if (homes.containsKey(player.getUniqueId())) {
                return;
            }
        } catch (Exception ignored) {}

        player.setFlySpeed(1);

        player.sendMessage("Welcome!");

        homes.put(player.getUniqueId(), new ArrayList<>());

    }
}
