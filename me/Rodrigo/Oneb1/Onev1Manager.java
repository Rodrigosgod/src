package me.Rodrigo.Oneb1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.Rodrigo.Metodos;

public class Onev1Manager {
	public static List<String> na1v1;
	public static List<String> cooldown;
	public static List<String> fast1v1;
	public static HashMap<String, String> convite;
	public static HashMap<String, String> lutando;

	static {
		Onev1Manager.na1v1 = new ArrayList<String>();
		Onev1Manager.cooldown = new ArrayList<String>();
		Onev1Manager.fast1v1 = new ArrayList<String>();
		Onev1Manager.convite = new HashMap<String, String>();
		Onev1Manager.lutando = new HashMap<String, String>();
	}

	public static void sumir(final Player p, final Player r) {
		for (final Player all : Bukkit.getOnlinePlayers()) {
			all.hidePlayer(p);
			all.hidePlayer(r);
			p.showPlayer(r);
			r.showPlayer(p);
		}
	}

	public static void aparecer(final Player p) {
		for (final Player t : Metodos.allPlayer()) {
			if (t.getGameMode() == GameMode.SURVIVAL) {
				if (!Onev1Manager.lutando.containsKey(t.getName())) {
					t.showPlayer(p);
				}
				p.showPlayer(t);
			}
		}
	}

	public static void sumirPlayers1v1(final Player p) {
		for (final Player t : Metodos.allPlayer()) {
			if (Onev1Manager.lutando.containsKey(t.getName())) {
				p.hidePlayer(t);
				t.hidePlayer(p);
			}
		}
	}

	public static void sumirPlayersAdmin(final Player p) {
		for (final Player t : Metodos.allPlayer()) {
			if (t.getGameMode() == GameMode.SURVIVAL) {
				p.hidePlayer(t);
			}
		}
	}

	public static void item(final Player p) {
		p.getInventory().clear();
		for (final PotionEffect e : p.getActivePotionEffects()) {
			if (e != null) {
				p.removePotionEffect(e.getType());
			}
		}
		p.getInventory().setItem(2, Metodos.itemStack(Material.IRON_FENCE, "§c1v1 Custom"));
		p.getInventory().setItem(4, Metodos.itemStack(Material.SLIME_BALL, "§c1v1 Rapido"));
		p.getInventory().setItem(6, Metodos.itemStack(Material.BLAZE_ROD, "§c1v1 Normal"));
	}
}
