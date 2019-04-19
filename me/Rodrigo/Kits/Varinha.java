package me.Rodrigo.Kits;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.KitManager;

public class Varinha implements Listener {

	@EventHandler
	public void peca(PlayerFishEvent e) {
		Player p = e.getPlayer();
		if (e.getCaught().getType() == EntityType.PLAYER) {
			Player r = (Player) e.getCaught();
			if (KitManager.checkKit(p, KitC.Fisherman)) {
				r.teleport(p.getLocation());
			}
			if (KitManager.checkKit(p, KitC.Namrehsif)) {
				p.teleport(r.getLocation());
			}
			if(KitManager.checkKit(p, KitC.Jumper)){
				r.setVelocity(new Vector(0, 1.5, 0));
			}
		}
	}

}
