package me.Rodrigo.Kits;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.KitManager;

public class Andarilho implements Listener {

	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (KitManager.checkKit(p, KitC.Camel)) {
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SAND) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 1));
			}
		}
		
		if (KitManager.checkKit(p, KitC.NetherMan)) {
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.NETHERRACK) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 1));
			}
		}
	}

}
