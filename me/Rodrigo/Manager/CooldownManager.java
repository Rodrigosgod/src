package me.Rodrigo.Manager;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class CooldownManager {

	public static HashMap<String, Long> cooldown = new HashMap<>();
	public Player p;

	public CooldownManager(Player p) {
		this.p = p;
	}

	public Boolean hasCooldown() {
		if (!cooldown.containsKey(p.getName())) {
			cooldown.put(p.getName(), (long) 0);
		}
		long tempo = (cooldown.get(p.getName()) - System.currentTimeMillis()) / 1000;
		return tempo > 0;
	}

	public void addCooldown(int sec) {
		long seconds = System.currentTimeMillis() + sec * 1000;
		cooldown.put(p.getName(), seconds);
	}

	public String getCooldown() {
		long dateFormatted = (cooldown.get(p.getName()) - System.currentTimeMillis())/1000;
		return "§c§lAVISO§f, aguarde §e" + dateFormatted + "s§f para usar o kit novamente!";
	}

}
