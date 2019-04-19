package me.Rodrigo.RDM;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;

public class RdmTimer {

	public static HashMap<String, BukkitRunnable> task = new HashMap<>();
	public static int tempo = 301;
	public static Boolean evento = false;

	public static void IniciarTimer() {
		evento = true;
		task.remove("server");
		tempo = 301;
		try {
			task.put("server", (BukkitRunnable) new BukkitRunnable() {

				@Override
				public void run() {
					if (tempo > 0) {
						tempo--;	
						
						for(String s : RdmManager.rdm){
							Player p = Bukkit.getPlayer(s);
							if(p != null){
								Metodos.sendactionBar(p, "§c§lRDM§e, iniciando em: §c"+Metodos.TimePerfeito(tempo));
							}
						}
						
						if(!evento){
							task.remove("server");
							cancel();
						}
						if (tempo % 30 == 0 && tempo > 0) {
							Bukkit.broadcastMessage("§a§lRDM§f, iniciando em: §c" + tempo + "s, jogadores ("+RdmManager.rdm.size() + ")");
							Bukkit.broadcastMessage("§c§lRDM§f, para entrar use /rdm");
						}
						if (tempo < 10 && tempo > 0) {
							Bukkit.broadcastMessage("§a§lRDM§f, iniciando em: §c" + tempo + "s, jogadores ("+RdmManager.rdm.size() + ")");
							Bukkit.broadcastMessage("§c§lRDM§f, para entrar use /rdm");
						}

						if (tempo == 0) {
							if (RdmManager.rdm.size() < 2) {
								tempo = 301;
								Bukkit.broadcastMessage("§a§lRDM§f, timer reiniciado!");
							} else {
								RdmManager.venceu = false;
								RdmManager.iniciarBattle();
								RdmManager.tempo = 0;
							}
							task.remove("server");
							cancel();
						}
					}

				}
			}.runTaskTimer(Main.main, 0, 20));	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
