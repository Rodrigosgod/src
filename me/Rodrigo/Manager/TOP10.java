package me.Rodrigo.Manager;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.C.TOP10C;

public class TOP10 {

	public static ArrayList<TOP10C> top10 = new ArrayList<>();

	public void topELO(){
		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.broadcastMessage("§c§lTOP§f, foi atualizado o TOP10 jogadores!");
				top10.clear();
				
					try {
						ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `status` ORDER BY `elo` DESC LIMIT 10");
						while(rs.next()){
							top10.add(new TOP10C(rs.getString("nick"), rs.getInt("elo")));
						}
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
					}
				

			}
		}.runTaskTimer(Main.main, 0, 20 * 60 * 5);
	}
	
	public static ArrayList<TOP10C> topCLAN = new ArrayList<>();

	public void topCLAN(){
		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.broadcastMessage("§c§lTOP§f, foi atualizado o TOP10 Clans!");
				topCLAN.clear();
				
					try {
						ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `clan` ORDER BY `elo` DESC LIMIT 10");
						while(rs.next()){
							topCLAN.add(new TOP10C(rs.getString("nome"), rs.getInt("elo")));
						}
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
					}
				

			}
		}.runTaskTimer(Main.main, 0, 20 * 60 * 5);
	}

}
