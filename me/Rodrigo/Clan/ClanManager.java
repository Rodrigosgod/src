package me.Rodrigo.Clan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.Rodrigo.Main;

public class ClanManager {

	public ClanManager(Main m) {
		setup();
	}

	public ClanManager() {
	}

	public static ArrayList<ClanC> clans = new ArrayList<>();
	public static HashMap<Player, Boolean> chatClan = new HashMap<>();
	
	
	public Boolean hasChat(Player p) {
		Boolean b = false;
		if (hasClanPlayer(p)) {
			if(chatClan.containsKey(p)){
				b = chatClan.get(p);
			}
		}
		return b;
	}

	private void setup() {
		try {
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `clan`");
			while (rs.next()) {
				String nome = rs.getString("nome");
				String tag = rs.getString("tag");
				String dono = rs.getString("dono");
				String inte = rs.getString("integrantes");
				int kills = rs.getInt("kills");
				int deaths = rs.getInt("deaths");
				int elo = rs.getInt("elo");
				int money = rs.getInt("money");
				ArrayList<String> integrantes = new ArrayList<>();
				for (String s : inte.split(",")) {
					integrantes.add(s);
				}
				clans.add(new ClanC(nome, tag, dono, integrantes, kills, deaths, money, elo));
			}
			rs.close();
		} catch (Exception e) {
			System.err.println("SETUP " + e.getMessage());
		}
	}

	public boolean hasClan(String name) {
		Boolean r = false;
		for (ClanC c : clans) {
			if (c.nome.equalsIgnoreCase(name)) {
				r = true;
			}
		}
		return r;
	}

	public Boolean createClan(String nome, String tag, String dono, String integrante) {
		Boolean erro = false;
		try {
			PreparedStatement pr = Main.mysql.conectar().prepareStatement("INSERT INTO `clan`(`nome`, `tag`, `dono`, `integrantes`, `kills`, `deaths`, `elo`, `money`) VALUES ('"+ nome + "','" + tag + "','" + dono + "','" + integrante + ",','0','0','0','0')");
			pr.execute();
			pr.close();
			List<String> integrantess = new ArrayList<>();
			integrantess.add(integrante);
			clans.add(new ClanC(nome, tag, dono, integrantess, 0, 0, 0, 0));
			erro = true;
		} catch (Exception e) {

		}
		return erro;
	}
	
	public Boolean deleteClan(Player p) {
		Boolean b = false;

		try {
			PreparedStatement pr = Main.mysql.conectar().prepareStatement("DELETE FROM `clan` WHERE `nome`='" + getClanNome(p) + "'");
			pr.execute();
			pr.close();
			b = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return b;
	}
	
	
	public void update(Player p,int kills,int deaths,int elo,int money){
		try {
			ClanC c = getClan(p);
			PreparedStatement pr = Main.mysql.conectar().prepareStatement("UPDATE `clan` SET `kills`=`kills`+"+kills+",`deaths`=`deaths`+"+deaths+",`elo`=`elo`+"+elo+",`money`=`money`+"+money+" WHERE nome='"+c.nome+"'");
			pr.execute();
			pr.close();
			
			c.kills += kills;
			c.deaths += deaths;
			c.elo += elo;
			c.money += money;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Boolean hasClanPlayer(Player p) {
		Boolean b = false;
		if (!getClanTag(p).isEmpty()) {
			b = true;
		}
		return b;
	}

	public String getClanTag(Player p) {
		String tem = "";

		for (ClanC c : clans) {
			for (String s : c.integrantes) {
				if (s.equalsIgnoreCase(p.getName())) {
					tem = c.tag;
				}
			}
		}

		return tem;
	}
	
	public String getClanNome(Player p) {
		String tem = "";

		for (ClanC c : clans) {
			for (String s : c.integrantes) {
				if (s.equalsIgnoreCase(p.getName())) {
					tem = c.nome;
				}
			}
		}

		return tem;
	}
	
	public List<String> getIntegrantes(Player p){
		List<String> i = new ArrayList<>();
		for(ClanC c : clans){
			if(c.tag.equalsIgnoreCase(getClanTag(p))){
				i = c.integrantes;
			}
		}
		return i;
	}
	
	public Boolean hasDono(Player p) {
		Boolean b = false;

		for (ClanC c : clans) {
			if (c.dono.equalsIgnoreCase(p.getName())) {
				b = true;
			}
		}

		return b;
	}
	
	public ClanC getClan(Player p) {
		ClanC cc = null;
		for (ClanC c : clans) {
			for (String s : c.integrantes) {
				if (s.equalsIgnoreCase(p.getName())) {
					cc = c;
				}
			}
		}
		return cc;
	}

	public Boolean addIntegrante(String clanname, String name) {
		Boolean erro = false;

		try {
			ClanC clan = null;
			for (ClanC c : clans) {
				if (c.nome.equalsIgnoreCase(clanname)) {
					clan = c;
				}
			}
			clan.integrantes.add(name);
			String names = "";
			for (String s : clan.integrantes) {
				names += s + ",";
			}
			PreparedStatement pr = Main.mysql.conectar().prepareStatement("UPDATE `clan` SET `integrantes`='" + names + "' WHERE nome='" + clanname + "'");
			pr.execute();
			pr.close();

			erro = true;
		} catch (Exception e) {
			System.out.println("ERRO AO ADD: "+e.getMessage());
		}

		return erro;
	}
	public Boolean removeIntegrante(String clanname, String name) {
		Boolean erro = false;

		try {
			
			ClanC clan = null;
			for (ClanC c : clans) {
				if (c.nome.equalsIgnoreCase(clanname)) {
					clan = c;
				}
			}
			clan.integrantes.remove(name);
			String rem = "";
			for(String s : clan.integrantes){
				rem += s + ",";
			}
			PreparedStatement pr = Main.mysql.conectar().prepareStatement("UPDATE `clan` SET `integrantes`='" + rem + ",' WHERE nome='" + clanname + "'");
			pr.execute();
			pr.close();

			erro = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return erro;
	}

}
