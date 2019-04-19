package me.Rodrigo.WARPS;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Rodrigo.Main;
import me.Rodrigo.Manager.Cords;

public class WarpConfig {

	public File file;
	public FileConfiguration files;

	public WarpConfig() {
		file = new File(Main.main.getDataFolder() + "/warps.yml");
		files = YamlConfiguration.loadConfiguration(file);

	}

	public void setWarp(String name, Location loc) {
		Cords c = new Cords(loc);

		files.set(name + ".X", c.getX());
		files.set(name + ".Y", c.getY());
		files.set(name + ".Z", c.getZ());
		files.set(name + ".PITCH", c.getPitch());
		files.set(name + ".YAW", c.getYaw());

		try {
			files.save(file);
		} catch (IOException e) {

		}
	}
	
	public Location getLocation(String name){
		Location loc = null;
		loc = new Location(Bukkit.getWorld("world"), files.getInt(name + ".X"), files.getInt(name + ".Y"), files.getInt(name + ".Z"), files.getInt(name + ".YAW"), files.getInt(name + ".PITCH"));
		return loc;
	}

}
