package me.Rodrigo.Kits;

import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.KitManager;

public class Combo implements Listener {

	public static HashMap<String, Integer> hits = new HashMap<>();

	@EventHandler
	public void dano(EntityDamageByEntityEvent e) {
		if (e.getEntity().getType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER) {
			Player apanho = (Player) e.getEntity();
			Player bateu = (Player) e.getDamager();
			if (KitManager.checkKit(bateu, KitC.Combo)) {
				if (!hits.containsKey(bateu.getName())) {
					hits.put(bateu.getName(), 0);
				}

				hits.put(bateu.getName(), hits.get(bateu.getName()) + 1);
				if (hits.get(bateu.getName()) == 5) {
					apanho.sendMessage("§c§lCOMBO§f, você foi combado por: §e" + bateu.getName());
					bateu.sendMessage("§c§lCOMBO§f, você deu um combo em: §e" + apanho.getName());
					new BukkitRunnable() {
						int hitss = 0;

						@Override
						public void run() {
							hitss++;
							if (hitss < 5) {
								apanho.damage(2);
								apanho.setVelocity(apanho.getEyeLocation().getDirection().multiply(-0.5).setY(0.5));
								hits.remove(bateu.getName());
							} else {
								cancel();
							}

						}
					}.runTaskTimer(Main.main, 0, 5);
				}
			}

			if (KitManager.checkKit(apanho, KitC.Combo)) {
				if (hits.containsKey(apanho.getName())) {
					hits.remove(apanho.getName());
				}
			}
		}
	}

}
