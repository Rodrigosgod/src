package me.Rodrigo.Listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import me.Rodrigo.Main;
import me.Rodrigo.Metodos;
import me.Rodrigo.C.KitC;
import me.Rodrigo.Clan.ClanManager;
import me.Rodrigo.Invs.KitSelectorInv;
import me.Rodrigo.Invs.WarpInv;
import me.Rodrigo.Manager.KitDiario;
import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.Manager.StatusManager;
import me.Rodrigo.Oneb1.Onev1Config;
import me.Rodrigo.Oneb1.Onev1Manager;
import me.Rodrigo.RDM.RdmManager;
import me.Rodrigo.Score.Score;

public class Primarios implements Listener {

	@EventHandler
	public void aoMorrer(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		Player vitima = e.getEntity();

		int elo = new Random().nextInt(10) + 1;
		int money = new Random().nextInt(100) + 1;
		new BukkitRunnable() {
			public void run() {
				vitima.spigot().respawn();
			}
		}.runTaskLater(Main.main, 10);
		if(RdmManager.rdm.contains(vitima.getName())){
			return;
		}
		
		try {
			KitC k = KitC.valueOf(KitManager.getKit(e.getEntity()));
			List<ItemStack> drop = e.getDrops();
			for(Material m : k.mat){
				for(ItemStack d : e.getDrops()){
					if(d.getType() == m || d.getType() == Material.WOOD_SWORD || d.getType() == Material.STONE_SWORD){
						drop.remove(d);
					}
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
		if (e.getEntity().getKiller() instanceof Player) {
			Player matador = e.getEntity().getKiller();
			if(RdmManager.rdm.contains(matador.getName())){
				return;
			}
			if (!Onev1Manager.lutando.containsKey(matador.getName())) {
				vitima.sendMessage("\n§b§l§m---------------------------");
				vitima.sendMessage("§7Você morreu para: §c" + matador.getName());
				vitima.sendMessage("§cElo: §7-" + elo);
				vitima.sendMessage("§cMoney: §7-" + money);
				vitima.sendMessage("§cMortes §7+1");
				vitima.sendMessage("§b§l§m---------------------------");
				new StatusManager(vitima).update(0, 1, -elo, -money, 0);

				matador.sendMessage("\n§b§l§m---------------------------");
				matador.sendMessage("§7Você matou: §c" + vitima.getName());
				matador.sendMessage("§cElo: §7+" + elo);
				matador.sendMessage("§cMoney: §7+" + money);
				matador.sendMessage("§cMatou §7+1");
				new StatusManager(matador).update(1, 0, elo, money, 0);
				matador.sendMessage("§b§l§m---------------------------");

			} else {
				matador.sendMessage("§c§l1v1§f, você venceu contra: §e" + vitima.getName());
				vitima.sendMessage("§c§l1v1§f, você perdeu para: §e" + matador.getName());
				matador.teleport(new Onev1Config("Spawn").getLocation());
				new StatusManager(matador).update(1, 0, elo, money, 0);
				new StatusManager(vitima).update(0, 1, -elo, -money, 0);
			}

			new ClanManager().update(vitima, 0, 1, -elo, -money);
			new ClanManager().update(matador, 1, 0, elo, money);

			Score.updateTeams(matador);
			Score.updateTeams(vitima);
		} else {
			if (!Onev1Manager.lutando.containsKey(vitima.getName())) {
				vitima.sendMessage("\n§b§l§m---------------------------");
				vitima.sendMessage("§7Você morreu para: §cmundo");
				vitima.sendMessage("§cElo: §7-" + elo);
				vitima.sendMessage("§cMoney: §7-" + money);
				vitima.sendMessage("§cMortes §7+1");
				vitima.sendMessage("§b§l§m---------------------------");
				new StatusManager(vitima).update(0, 1, -elo, -money, 0);
				new ClanManager().update(vitima, 0, 1, -elo, -money);
				Score.updateTeams(vitima);

			}else{
				new StatusManager(vitima).update(0, 1, -elo, -money, 0);
				new ClanManager().update(vitima, 0, 1, -elo, -money);

				try {
					new ClanManager().update(Bukkit.getPlayer(Onev1Manager.lutando.get(vitima.getName())), 1, 0, elo, money);
					Bukkit.getPlayer(Onev1Manager.lutando.get(vitima.getName())).teleport(new Onev1Config("Spawn").getLocation());
					Bukkit.getPlayer(Onev1Manager.lutando.get(vitima.getName())).sendMessage("§c§l1v1§f, você venceu contra: §e" + vitima.getName());
				} catch (Exception e2) {
				}
			}
		}
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (Onev1Manager.na1v1.contains(p.getName())) {
			p.teleport(new Onev1Config("Spawn").getLocation());
			Metodos.Item1v1(p);
			Player r = Bukkit.getPlayer(Onev1Manager.lutando.get(p.getName()));
			for (Player all : Metodos.allPlayer()) {
				all.showPlayer(p);
				if (all.getGameMode() == GameMode.SURVIVAL) {
					p.showPlayer(all);
				}
			}
			if (r != null) {
				for (Player all : Metodos.allPlayer()) {
					all.showPlayer(r);
					if (all.getGameMode() == GameMode.SURVIVAL) {
						p.showPlayer(r);
					}
				}
			}
			Onev1Manager.lutando.remove(p.getName());
			Onev1Manager.lutando.remove(r.getName());
		} else {
			if(RdmManager.rdm.contains(p.getName())){
				return;
			}
			KitManager.kit.remove(p);
			p.teleport(p.getWorld().getSpawnLocation());
			Metodos.ItemSpawn(p);
			new BukkitRunnable() {

				@Override
				public void run() {
					Score.updateTeams(p);

				}
			}.runTaskLater(Main.main, 20);
		}
	}

	@EventHandler
	public void login(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (Main.main.getServer().hasWhitelist()) {
			if (Main.main.getServer().getWhitelistedPlayers().contains(p)) {
				e.allow();
			} else {
				e.disallow(Result.KICK_OTHER, "§fVocê nao esta na lista!");
			}
		}
	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();

		p.teleport(Bukkit.getWorld("world").getSpawnLocation());

		Metodos.ItemSpawn(p);
		new StatusManager(p).setup();
		Score.send(p);

		p.sendMessage("");
		p.sendMessage("§fOlá, §e" + p.getName() + " §fBem vindo servidor");
		p.sendMessage("§fServidor em nova versao aproveite!");
		p.sendMessage("§fPara comprar §bVIP §fe §bKITS §facesse:");
		p.sendMessage("§a" + Main.loja);
		p.sendMessage("");
	}

	@EventHandler
	public void quit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}

	public static HashMap<Player, Long> cooldown = new HashMap<>();

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		ClanManager clan = new ClanManager();
		if (clan.hasChat(p)) {
			e.setCancelled(true);
			for (String s : clan.getClan(p).integrantes) {
				Player r = Bukkit.getPlayer(s);
				if (r != null) {
					r.sendMessage("§7[§c§lCLAN§7] " + p.getDisplayName() + " §b» §f" + e.getMessage());
				}
			}
			return;
		}

		if (!p.hasPermission("chat.flood") || !p.hasPermission("kitpvp.staff")) {
			if (cooldown.containsKey(p)) {
				long tempo = (cooldown.get(p) - System.currentTimeMillis()) / 1000;
				if (tempo > 0) {
					p.sendMessage("§cAguarde §f" + tempo + "'s§c para falar novamente");
					e.setCancelled(true);
					return;
				} else {
					cooldown.remove(p);
				}
			}
			if (!cooldown.containsKey(p)) {
				cooldown.put(p, System.currentTimeMillis() + 5 * 1000);
			}
		}

		if (p.hasPermission("kitpvp.vip")) {
			e.setFormat(p.getDisplayName() + " §b» §f" + e.getMessage().replace("&", "§").replace("%", ""));
			return;
		} else {
			e.setFormat(p.getDisplayName() + " §b» §f" + e.getMessage().replace("%", ""));

		}

	}

	@EventHandler
	public void motd(ServerListPingEvent e) {
		e.setMotd(Main.Prefix + "§f Servidor de §lKitPvP\n§" + new Random().nextInt(10)+ "Venha mostrar suas habilidades");
	}

	@EventHandler
	public void interac(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if (item.getType().equals(Material.COMPASS)) {
			if(KitManager.getKit(p).equalsIgnoreCase("none")){
				WarpInv.warp(p);
			}
		}
		if (item.getType().equals(Material.ENDER_PORTAL_FRAME)) {
			e.setCancelled(true);
			KitSelectorInv.menu(p);
		}
		if (item.getType().equals(Material.ENDER_CHEST)) {
			KitDiario k = new KitDiario(p);
			if (k.hasKit()) {
				long tempo = k.expira - System.currentTimeMillis();
				long second = (tempo / 1000) % 60;
				long minute = (tempo / (1000 * 60)) % 60;
				long hour = (tempo / (1000 * 60 * 60)) % 24;
				String dateFormatted = hour + " hora(s) " + minute + " minuto(s) " + second + " segundo(s)";
				p.sendMessage("§c§lAVISO§f, Você deve aguardar §c" + dateFormatted + "§f para pegar o kit novamente!");
			} else {
				k.setKit();
				p.sendMessage("§a§lSUCESSO§f, Você pegou o kit diario e veio: §e" + k.newKit);
				Bukkit.broadcastMessage("§c§lKIT§f, o jogador §e" + p.getName() + "§f usou o kit diario e ganhou §e" + k.newKit);

			}
		}
	}

}
