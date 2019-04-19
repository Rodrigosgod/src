package me.Rodrigo.Manager;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;

public class BC {

	String link = "http://maxmovies.com.br";
	public String p = "§7[§c§lAVISO§7] ";

	public void start() {
		String[] oq = { "Filmes", "Animes", "Series" };
		String s = oq[new Random().nextInt(oq.length)];

		String one = p + "§eGosta de " + s + " ? se sim entre já no §c" + link;

		String[] text = { one };
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(text[new Random().nextInt(text.length)]);
				
			}
		}.runTaskTimer(Main.main, 0, 20*60);
	}

}
