package me.Rodrigo.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import me.Rodrigo.Main;
import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.Manager.StatusManager;

public class CombatLog implements Listener {

	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player apanho = (Player) e.getEntity();
			Player bateu = (Player) e.getDamager();

			if (KitManager.getKit(apanho) != "None" && KitManager.getKit(bateu) != "None") {
				if ((!Main.combatLog.containsKey(bateu)) && (!Main.combatLog.containsKey(apanho))) {

					Objective objective = bateu.getScoreboard().registerNewObjective("RAID", "dummy");
					objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
					objective.setDisplayName(" §c❤ §7Habilidade: §e" + KitManager.getKit(apanho));

					new BukkitRunnable() {

						@Override
						public void run() {
							bateu.getScoreboard().getObjective(DisplaySlot.BELOW_NAME).unregister();

						}
					}.runTaskLater(Main.main, 20 * 3);

					bateu.sendMessage("§c§lCBL§f, Você entrou em combat com: " + apanho.getName());
					apanho.sendMessage("§c§lCBL§f, Você entrou em combat com: " + bateu.getName());
					Main.combatLog.put(apanho, bateu);
					Main.combatLog.put(bateu, apanho);
					new BukkitRunnable() {
						public void run() {
							Main.combatLog.remove(bateu);
							Main.combatLog.remove(apanho);
						}
					}.runTaskLater(Main.main, 20 * 10);
				}
			}
		}
	}

	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (Main.combatLog.get(p) != null) {
			new StatusManager(p).update(0, 1, -5, -100, 0);

			if (Main.combatLog.get(p) != null) {
				Player t = Main.combatLog.get(p);
				Bukkit.broadcastMessage(
						"§c§lCBL§f, o player §7" + p.getName() + "§f saiu em combat e foi kill para §c" + t.getName());
				new StatusManager(t).update(1, 0, 10, 100, 0);
			}
			Main.combatLog.remove(p);
		}
	}

}
