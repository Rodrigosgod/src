package me.Rodrigo.Oneb1;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Rodrigo.Main;

public class Onev1Config
{
    private File file;
    private FileConfiguration files;
    private String name;
    
    public Onev1Config(final String name) {
        this.file = new File(Main.main.getDataFolder() + "/1v1.yml");
        this.files = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
        this.name = name;
        this.save();
    }
    
    public void save() {
        try {
            this.files.save(this.file);
        }
        catch (Exception ex) {}
    }
    
    public void setWarp(final Location loc) {
        final int x = loc.getBlockX();
        final int y = loc.getBlockY();
        final int z = loc.getBlockZ();
        final int pitch = (int)loc.getPitch();
        final int yau = (int)loc.getYaw();
        this.files.set(String.valueOf(this.name) + ".X", (Object)x);
        this.files.set(String.valueOf(this.name) + ".Y", (Object)y);
        this.files.set(String.valueOf(this.name) + ".Z", (Object)z);
        this.files.set(String.valueOf(this.name) + ".YAW", (Object)yau);
        this.files.set(String.valueOf(this.name) + ".PITCH", (Object)pitch);
        this.save();
    }
    
    public boolean existe() {
        return this.files.getString(this.name) != null;
    }
    
    public Location getLocation() {
        Location loc = null;
        final int x = this.files.getInt(String.valueOf(this.name) + ".X");
        final int y = this.files.getInt(String.valueOf(this.name) + ".Y");
        final int z = this.files.getInt(String.valueOf(this.name) + ".Z");
        final int yaw = this.files.getInt(String.valueOf(this.name) + ".YAW");
        final int pitch = this.files.getInt(String.valueOf(this.name) + ".PITCH");
        loc = new Location(Bukkit.getWorld("world"), (double)x, (double)y, (double)z, (float)yaw, (float)pitch);
        return loc;
    }
}
