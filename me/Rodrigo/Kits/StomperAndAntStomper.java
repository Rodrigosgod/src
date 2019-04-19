package me.Rodrigo.Kits;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Rodrigo.Main;
import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.CooldownManager;
import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.Manager.StatusManager;

public class StomperAndAntStomper implements Listener {

	@EventHandler
	public void click(InventoryClickEvent e) {
		try {
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem().getType() == Material.DIAMOND_BOOTS) {
				p.closeInventory();
				e.setCancelled(true);

			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

	@EventHandler
	public void click(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (KitManager.checkKit(p, KitC.Stomper)) {
			if (p.getItemInHand().getType() == Material.DIAMOND_BOOTS) {
				e.setCancelled(true);
				p.updateInventory();
				CooldownManager c = new CooldownManager(p);
				if (c.hasCooldown()) {
					p.sendMessage(c.getCooldown());
					return;
				}
				c.addCooldown(30);
				p.setVelocity(new Vector(0, 4, 0));
			}
		}
		if (KitManager.checkKit(p, KitC.Terrorista)) {
			if (p.getItemInHand().getType() == Material.MAGMA_CREAM) {
				e.setCancelled(true);
				p.updateInventory();
				CooldownManager c = new CooldownManager(p);
				if (c.hasCooldown()) {
					p.sendMessage(c.getCooldown());
					return;
				}
				c.addCooldown(30);
				p.setVelocity(new Vector(0, 4, 0));
			}
		}
	}

	@EventHandler
	public void dano(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if (KitManager.checkKit(p, KitC.Stomper)) {
					for (Entity v : p.getNearbyEntities(4, 2, 4)) {
						if (v.getType() == EntityType.PLAYER) {
							Player volta = (Player) v;
							if (!KitManager.checkKit(volta, KitC.AntStomper)) {
								if (volta.isSneaking()) {
									volta.damage(3);
								} else {
									volta.damage(e.getDamage());
									if (volta.isDead()) {
										new BukkitRunnable() {

											@Override
											public void run() {

												new StatusManager(p).update(1, 0, 5, 20, 0);

											}
										}.runTask(Main.main);
									}
								}
							}
						}
					}
					e.setDamage(4);
				}

				if (KitManager.checkKit(p, KitC.Terrorista)) {
					for (Entity v : p.getNearbyEntities(4, 2, 4)) {
						if (v.getType() == EntityType.PLAYER) {
							Player volta = (Player) v;
							e.setDamage(4);
							volta.getWorld().playEffect(volta.getLocation(), Effect.EXPLOSION_LARGE, 2);
							if (volta.isSneaking()) {
								volta.damage(8);
								e.setDamage(4);
							} else {
								volta.damage(2);
								volta.setVelocity(new Vector(0, 1.2, 0));
								e.setDamage(4);

							}
							if (volta.isDead()) {
								new BukkitRunnable() {

									@Override
									public void run() {

										new StatusManager(p).update(1, 0, 5, 20, 0);

									}
								}.runTask(Main.main);
							}

						}
					}
					e.setDamage(4);
				}
			}
		}
	}

}
