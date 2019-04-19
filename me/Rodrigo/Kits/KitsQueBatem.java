package me.Rodrigo.Kits;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.CooldownManager;
import me.Rodrigo.Manager.KitManager;

public class KitsQueBatem implements Listener {

	public static HashMap<Player, Player> tp = new HashMap<>();

	@EventHandler
	public void dano(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player apanho = (Player) e.getEntity();
			Player bateu = (Player) e.getDamager();
			if (KitManager.checkKit(bateu, KitC.Ninja)) {
				tp.put(bateu, apanho);
			}
			if (KitManager.checkKit(bateu, KitC.Ajnin)) {
				tp.put(bateu, apanho);
			}

			if (KitManager.checkKit(bateu, KitC.Boxer)) {
				e.setDamage(e.getDamage() + 2.0);
			}
			if (KitManager.checkKit(apanho, KitC.Boxer)) {
				e.setDamage(e.getDamage() - 2.0);
			}
			if (KitManager.checkKit(bateu, KitC.Viking) && bateu.getItemInHand().getType() == Material.STONE_AXE) {
				e.setDamage(6.0);
			}
			if (KitManager.checkKit(bateu, KitC.Viper) && new Random().nextInt(100) >= 75) {
				apanho.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 0));
			}
			if (KitManager.checkKit(bateu, KitC.Magma) && new Random().nextInt(100) >= 75) {
				apanho.setFireTicks(100);
			}
			if (KitManager.checkKit(bateu, KitC.Snail) && new Random().nextInt(100) >= 75) {
				apanho.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 0));
			}
		}
	}

	@EventHandler
	public void dano(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (KitManager.checkKit(p, KitC.Magma)) {
				if (e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK|| e.getCause() == DamageCause.LAVA) {
					e.setDamage(0);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void shuift(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (KitManager.checkKit(p, KitC.Ninja)) {
			if (tp.containsKey(p)) {
				Player r = tp.get(p);
				if (r == null) {
					return;
				}
				if (p.getLocation().distance(r.getLocation()) < 50) {
					CooldownManager c = new CooldownManager(p);
					if (c.hasCooldown()) {
						p.sendMessage(c.getCooldown());
						return;
					}
					c.addCooldown(5);
					p.teleport(r.getLocation());
					tp.remove(p);
				}
			}
		}

		if (KitManager.checkKit(p, KitC.Ajnin)) {

			if (tp.containsKey(p)) {
				Player r = tp.get(p);
				if (r == null) {
					return;
				}
				if (p.getLocation().distance(r.getLocation()) < 50) {
					CooldownManager c = new CooldownManager(p);
					if (c.hasCooldown()) {
						p.sendMessage(c.getCooldown());
						return;
					}
					c.addCooldown(5);
					r.teleport(p.getLocation());
					tp.remove(p);
				}
			}
		}
	}
}
