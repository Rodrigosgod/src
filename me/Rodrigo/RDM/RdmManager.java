package me.Rodrigo.RDM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;
import me.Rodrigo.WARPS.WarpConfig;

public class RdmManager {
	public static ArrayList<String> rdm = new ArrayList<>();
	public static HashMap<Player, Player> lutando = new HashMap<>();
	public static Boolean venceu;
	public static Player vencedor;
	public static int tempo = 0;

	public static ArrayList<Player> getPlayers() {
		ArrayList<Player> getPlayers = new ArrayList<>();

		Player one = null;
		Player two = null;

		Player escolha = Bukkit.getPlayer(rdm.get(new Random().nextInt(rdm.size())));
		one = escolha;

		while (two == null) {
			escolha = Bukkit.getPlayer(rdm.get(new Random().nextInt(rdm.size())));
			if (one != escolha) {
				two = escolha;
			}
		}

		getPlayers.add(one);
		getPlayers.add(two);

		return getPlayers;
	}

	public static void iniciarBattle() {
		check();
		if (venceu) {
			vencedor.teleport(vencedor.getWorld().getSpawnLocation());
			Metodos.ItemSpawn(vencedor);
			return;
		}
		ArrayList<Player> a = getPlayers();
		Player p = a.get(0);
		Player r = a.get(1);
		lutando.put(p, r);
		lutando.put(r, p);

		p.sendMessage("§cvocê foi escolhido e vai batalhar com: §f" + r.getName());
		r.sendMessage("§cvocê foi escolhido e vai batalhar com: §f" + p.getName());

		p.teleport(new WarpConfig().getLocation("RDMPOS1"));
		r.teleport(new WarpConfig().getLocation("RDMPOS1"));
		item(p);
		item(r);
	}

	public static void check() {
		if (rdm.size() == 1) {
			Player p = Bukkit.getPlayer(rdm.get(0));
			vencedor = p;
			venceu = true;
			new BukkitRunnable() {
				

				@Override
				public void run() {
					if (tempo < 10) {
						Bukkit.broadcastMessage("§c§lRDM§f, o vencedor foi: §a" + p.getName());

						Metodos.spawnRandomFirework(p.getLocation());

					} else {
						cancel();
					}
					tempo++;
				}
			}.runTaskTimer(Main.main, 0, 40);
		} else if (rdm.size() == 0) {
			Bukkit.broadcastMessage("§cRDM BUGO E EXPLODIU!");
		}
	}

	public static void item(Player p) {
		Metodos.Clear(p);
		p.getInventory().setItem(0, Metodos.itemStack(Material.STONE_SWORD, "§aRDM"));
		p.getInventory().setItem(13, Metodos.itemStack(Material.BROWN_MUSHROOM, "§aCogu", 64));
		p.getInventory().setItem(14, Metodos.itemStack(Material.BOWL, "§aPOTE", 64));
		p.getInventory().setItem(15, Metodos.itemStack(Material.RED_MUSHROOM, "§aCogu", 64));
		for (int x = 0; x < 50; x++) {
			p.getInventory().addItem(Metodos.itemStack(Material.MUSHROOM_SOUP, "§cSopa"));
		}
	}

}
