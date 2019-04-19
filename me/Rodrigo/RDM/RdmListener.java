package me.Rodrigo.RDM;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Rodrigo.Metodos;
import me.Rodrigo.WARPS.WarpConfig;

public class RdmListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void a(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (RdmManager.rdm.contains(p.getName())) {
			Player r = p.getKiller();

			p.sendMessage("§cvocê perdeu o rdm mais sorte da proxima!");
			r.sendMessage("§cvocê venceu a batalha do rdm! continue assim");

			RdmManager.rdm.remove(p.getName());
			RdmManager.lutando.remove(p);
			Metodos.Clear(r);
			r.teleport(new WarpConfig().getLocation("RDMSPAWN"));
			RdmManager.lutando.remove(r);
			RdmManager.iniciarBattle();
		
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (RdmManager.lutando.containsKey(p)) {
			Player r = RdmManager.lutando.get(p);
			RdmManager.lutando.remove(p);
			RdmManager.rdm.remove(p.getName());
			Metodos.Clear(r);
			r.teleport(new WarpConfig().getLocation("RDMSPAWN"));
			RdmManager.lutando.remove(r);
			RdmManager.iniciarBattle();
			
		}
	}

}
