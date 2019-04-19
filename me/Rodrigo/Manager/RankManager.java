package me.Rodrigo.Manager;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;
import me.Rodrigo.C.TOPC;
import me.Rodrigo.Score.Score;

public class RankManager {

	private Player p;
	public static ArrayList<TOPC> rank = new ArrayList<>();

	public RankManager() {
		new BukkitRunnable() {

			@Override
			public void run() {
				setup();
				for(Player p : Metodos.allPlayer()){
					Score.updateTeams(p);
				}

			}
		}.runTaskTimer(Main.main, 0, 20 * 60 * 5);
	}

	public void setup() {
		try {
			int pos = 0;
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `status` ORDER BY `elo` DESC");
			while (rs.next()) {
				pos++;
				rank.add(new TOPC(rs.getString("nick"), 0, pos));
			}
			rs.close();

		} catch (Exception e) {
		}
	}

	public RankManager(Player p) {
		this.p = p;
	}

	public int getPosition() {
		int pot = 0;

		for (TOPC l : rank) {
			if (l.name.equals(p.getName())) {
				pot = l.pos;
			}
		}

		return pot;
	}

}
