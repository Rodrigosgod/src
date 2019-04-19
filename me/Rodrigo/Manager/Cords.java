package me.Rodrigo.Manager;

import org.bukkit.Location;

public class Cords {

	private Location loc;

	public Cords(Location loc) {
		this.loc = loc;
	}

	public double getX() {
		return loc.getX();
	}

	public double getY() {
		return loc.getY();
	}

	public double getZ() {
		return loc.getZ();
	}

	public double getPitch() {
		return loc.getPitch();
	}

	public double getYaw() {
		return loc.getYaw();
	}

}
