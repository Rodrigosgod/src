package me.Rodrigo.Kits;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;
import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.CooldownManager;
import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.Manager.StatusManager;

public class SonicDeshfireFf implements Listener {

	public static HashMap<String, BukkitRunnable> task = new HashMap<>();

	@EventHandler
	public void click(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		try {

			if (p.getItemInHand().getType() == Material.LAPIS_BLOCK) {
				if (KitManager.checkKit(p, KitC.Sonic)) {
					e.setCancelled(true);
					CooldownManager c = new CooldownManager(p);
					if (c.hasCooldown()) {
						p.sendMessage(c.getCooldown());
						return;
					}
					c.addCooldown(30);
					p.setVelocity(p.getLocation().getDirection().multiply(3).setY(0.5));
					Metodos.SetRopaColorida(p, Color.BLUE);
					task.put(p.getName(), (BukkitRunnable) new BukkitRunnable() {
						int tempo = 10;

						@Override
						public void run() {
							tempo--;
							if (tempo == 0) {
								task.remove(p.getName());
								p.getInventory().setArmorContents(null);
								cancel();
							}
							for (Entity e : p.getNearbyEntities(3, 2, 3)) {
								if (e.getType() == EntityType.PLAYER) {
									Player r = (Player) e;
									if (r.getGameMode() == GameMode.SURVIVAL) {
										r.damage(1);
										r.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
										if (r.isDead()) {
											new StatusManager(p).update(1, 0, 5, 50, 0);
										}
									}
								}
							}

						}
					}.runTaskTimer(Main.main, 0, 10));
					new BukkitRunnable() {

						@Override
						public void run() {
							p.getInventory().setArmorContents(null);

						}
					}.runTaskLater(Main.main, 20 * 5);
				}
			}

			if (p.getItemInHand().getType() == Material.COAL_BLOCK) {
				if (KitManager.checkKit(p, KitC.BlackSonic)) {
					e.setCancelled(true);
					CooldownManager c = new CooldownManager(p);
					if (c.hasCooldown()) {
						p.sendMessage(c.getCooldown());
						return;
					}
					c.addCooldown(30);
					p.setVelocity(p.getLocation().getDirection().multiply(3).setY(0.5));
					Metodos.SetRopaColorida(p, Color.BLACK);
					task.put(p.getName(), (BukkitRunnable) new BukkitRunnable() {
						int tempo = 10;

						@Override
						public void run() {
							tempo--;
							if (tempo == 0) {
								task.remove(p.getName());
								p.getInventory().setArmorContents(null);
								cancel();
							}
							for (Entity e : p.getNearbyEntities(3, 2, 3)) {
								if (e.getType() == EntityType.PLAYER) {
									Player r = (Player) e;
									if (r.getGameMode() == GameMode.SURVIVAL) {
										r.damage(1);
										r.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
										if (r.isDead()) {
											new StatusManager(p).update(1, 0, 5, 50, 0);
										}
									}
								}
							}

						}
					}.runTaskTimer(Main.main, 0, 10));
					new BukkitRunnable() {

						@Override
						public void run() {
							p.getInventory().setArmorContents(null);

						}
					}.runTaskLater(Main.main, 20 * 5);
				}
			}

			if (p.getItemInHand().getType() == Material.IRON_FENCE) {
				if (KitManager.checkKit(p, KitC.Forcefield)) {
					e.setCancelled(true);
					CooldownManager c = new CooldownManager(p);
					if (c.hasCooldown()) {
						p.sendMessage(c.getCooldown());
						return;
					}
					c.addCooldown(30);
					task.put(p.getName(), (BukkitRunnable) new BukkitRunnable() {
						int tempo = 10;

						@Override
						public void run() {
							tempo--;
							if (tempo == 0) {
								task.remove(p.getName());
								cancel();
							}
							for (Entity e : p.getNearbyEntities(3, 2, 3)) {
								if (e.getType() == EntityType.PLAYER) {
									Player r = (Player) e;
									if (r.getGameMode() == GameMode.SURVIVAL) {
										r.damage(3);
										r.setVelocity(r.getLocation().getDirection().multiply(-0.5).setY(0));
										if (r.isDead()) {
											new StatusManager(p).update(1, 0, 5, 50, 0);
										}
									}
								}
							}

						}
					}.runTaskTimer(Main.main, 0, 10));
				}
			}

			if (p.getItemInHand().getType() == Material.REDSTONE_BLOCK) {
				if (KitManager.checkKit(p, KitC.Deshfire)) {
					e.setCancelled(true);
					CooldownManager c = new CooldownManager(p);
					if (c.hasCooldown()) {
						p.sendMessage(c.getCooldown());
						return;
					}
					c.addCooldown(30);
					p.setVelocity(p.getLocation().getDirection().multiply(3).setY(0.5));
					Metodos.SetRopaColorida(p, Color.RED);
					task.put(p.getName(), (BukkitRunnable) new BukkitRunnable() {
						int tempo = 10;

						@Override
						public void run() {
							tempo--;
							if (tempo == 0) {
								task.remove(p.getName());
								p.getInventory().setArmorContents(null);
								cancel();
							}
							for (Entity e : p.getNearbyEntities(3, 2, 3)) {
								if (e.getType() == EntityType.PLAYER) {
									Player r = (Player) e;
									if (r.getGameMode() == GameMode.SURVIVAL) {
										r.damage(1);
										r.setFireTicks(100);
										if (r.isDead()) {
											new StatusManager(p).update(1, 0, 5, 50, 0);
										}
									}
								}
							}

						}
					}.runTaskTimer(Main.main, 0, 10));
					new BukkitRunnable() {

						@Override
						public void run() {
							p.getInventory().setArmorContents(null);

						}
					}.runTaskLater(Main.main, 20 * 5);
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

}
