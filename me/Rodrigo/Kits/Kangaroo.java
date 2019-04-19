package me.Rodrigo.Kits;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.KitManager;

public class Kangaroo implements Listener {

	public static HashMap<String, Integer> pulos = new HashMap<>();

	@EventHandler
	public void clickl(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.FIREWORK) {
			if (KitManager.checkKit(p, KitC.Kangaroo)) {
				e.setCancelled(true);
				if (!pulos.containsKey(p.getName())) {
					pulos.put(p.getName(), 0);
				}
				if (pulos.get(p.getName()) >= 2) {
					return;
				}
				pulos.put(p.getName(), pulos.get(p.getName()) + 1);
				if (p.isSneaking()) {
					p.setVelocity(p.getEyeLocation().getDirection().multiply(2).setY(0.3));
				} else {
					p.setVelocity(new Vector(0, 1.1, 0));
				}
			}
		}
	}

	@EventHandler
	public void remove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (pulos.containsKey(p.getName())) {
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
				pulos.remove(p.getName());
			}
		}
	}

	@EventHandler
	public void dano(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			if (e.getEntity().getType() == EntityType.PLAYER) {
				Player p = (Player) e.getEntity();
				if (KitManager.checkKit(p, KitC.Kangaroo)) {
					if (e.getDamage() > 6) {
						e.setDamage(6);
					}
				}
			}
		}
	}

}
