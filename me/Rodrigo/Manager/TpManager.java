package me.Rodrigo.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Rodrigo.Main;

public class TpManager {

	public static void set(Location loc) {
		File file = new File(Main.main.getDataFolder() + "/tp.yml");
		FileConfiguration files = YamlConfiguration.loadConfiguration(file);

		int id = 1;
		if (files.getString("set") != null) {
			id = files.getInt("set") + 1;
		}
		String str = loc.getX() + ":" + loc.getY() + ":" + loc.getZ();
		tps.add(str);
		files.set("set", id);
		files.set("TP." + id, str);

		try {
			files.save(file);
		} catch (IOException e) {
		}
	}

	public static ArrayList<String> tps = new ArrayList<>();

	public static Location getLocation() {
		if (tps.size() == 0) {
			File file = new File(Main.main.getDataFolder() + "/tp.yml");
			FileConfiguration files = YamlConfiguration.loadConfiguration(file);
			int total = files.getInt("set");
			int atual = 0;
			while (atual++ <= total) {
				String str = files.getString("TP." + atual);
				tps.add(str);
			}
		}

		String[] spl = tps.get(new Random().nextInt(tps.size()-1)).split(":");
		Double x = Double.valueOf(spl[0]);
		Double y = Double.valueOf(spl[1]);
		Double z = Double.valueOf(spl[2]);

		return new Location(Bukkit.getWorld("world"), x, y, z);

	}

}
