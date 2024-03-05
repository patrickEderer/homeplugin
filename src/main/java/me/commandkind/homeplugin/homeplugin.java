package me.commandkind.homeplugin;

import me.commandkind.homeplugin.cmds.Homes;
import me.commandkind.homeplugin.cmds.removeHome;
import me.commandkind.homeplugin.cmds.resetHomes;
import me.commandkind.homeplugin.cmds.setHome;
import me.commandkind.homeplugin.handlers.PlayerHandler;
import me.commandkind.homeplugin.util.ConfigUtil;
import me.commandkind.homeplugin.util.DelayedTask;
import me.commandkind.homeplugin.util.HomeList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class homeplugin extends JavaPlugin {
    //memory
    public Map<UUID, List<HomeList>> homes = new HashMap<>();

    @Override
    public void onEnable() {
        // Load Msg
        getLogger().info("Loaded!");

        // Config
        saveDefaultConfig();
        int maxHomes = getConfig().getInt("MaxHomes");
        ConfigUtil config = new ConfigUtil(this, "homes.yml");
        for (String id : config.getConfig().getKeys(false)) {
            UUID readId = UUID.fromString(id);
            for (String name : config.getConfig().getConfigurationSection(id).getKeys(false)) {
                String material = config.getConfig().getString(id + "." + name + ".Material");
                double x = config.getConfig().getDouble(id + "." + name + ".Location.x");
                double y = config.getConfig().getDouble(id + "." + name + ".Location.y");
                double z = config.getConfig().getDouble(id + "." + name + ".Location.z");
                float yaw = (float) config.getConfig().getDouble(id + "." + name + ".Location.yaw");
                float pitch = (float) config.getConfig().getDouble(id + "." + name + ".Location.pitch");
                String world = config.getConfig().getString(id + "." + name + ".Location.world");

                Location readLocation = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                Material readMaterial = Material.valueOf(material);
                if (!homes.containsKey(readId))
                    homes.put(readId, new ArrayList<>());
                homes.get(readId).add(new HomeList(readLocation, readMaterial, name));
            }
        }


        // Commands
        getCommand("setHome").setExecutor(new setHome(homes, maxHomes));
        getCommand("Homes").setExecutor(new Homes(homes, this));
        getCommand("resetHomes").setExecutor(new resetHomes(homes));
        getCommand("removeHome").setExecutor(new removeHome(homes, this));

        // Tasks
        new PlayerHandler(this, homes);
        new DelayedTask(this);
    }

    @Override
    public void onDisable() {
        ConfigUtil configOld = new ConfigUtil(this, "homes.yml");
        configOld.getFile().delete();
        ConfigUtil config = new ConfigUtil(this, "homes.yml");
        for (UUID id : homes.keySet()) {
            for (HomeList home : homes.get(id)) {
                config.getConfig().set(id + "." + home.getName() + ".Material", home.getMaterial().name());
                config.getConfig().set(id + "." + home.getName() + ".Location.x", home.getLocation().getX());
                config.getConfig().set(id + "." + home.getName() + ".Location.y", home.getLocation().getY());
                config.getConfig().set(id + "." + home.getName() + ".Location.z", home.getLocation().getZ());
                config.getConfig().set(id + "." + home.getName() + ".Location.yaw", home.getLocation().getYaw());
                config.getConfig().set(id + "." + home.getName() + ".Location.pitch", home.getLocation().getPitch());
                config.getConfig().set(id + "." + home.getName() + ".Location.world", home.getLocation().getWorld().getName());
            }
        }
        config.save();
    }
}
