package me.commandkind.homeplugin.util;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

public class HomeList {
    private UUID id;
    private Location location;
    private Material material;
    private String name;

    public HomeList(Location location, Material material, String name) {
        this.location = location;
        this.material = material;
        this.name = name;
    }

    public HomeList() {

    }

    public void createHome(Location location, Material material, String name) {
    }

    public Location getLocation() {
        return this.location;
    }

    public Material getMaterial() {
        return this.material;
    }

    public String getName() {
        return this.name;
    }
}
