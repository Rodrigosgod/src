package me.Rodrigo.Manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import me.Rodrigo.Main;
import me.Rodrigo.C.StatusC;

public class StatusManager {

	public Player p;

	public StatusManager(Player p) {
		this.p = p;
	}
	
	public StatusC getStatus(){
		return Main.status.get(p.getName());
	}
	
	public void setup(){
		try {
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `status` WHERE nick='"+p.getName() + "'");
			if(rs.next()){
				Main.status.put(p.getName(), new StatusC(p.getName(), rs.getInt("matou"),  rs.getInt("morreu"),  rs.getInt("kay"),  rs.getInt("elo"),  rs.getInt("money")));
			}else{
				if (!Main.status.containsKey(p.getName())) {
					Main.status.put(p.getName(), new StatusC(p.getName(), 0, 0, 0, 0, 0));
				}
				PreparedStatement pr = Main.mysql.conectar().prepareStatement("INSERT INTO `status`(`nick`, `matou`, `morreu`, `kay`, `elo`, `money`) VALUES ('"+p.getName()+"','0','0','0','0','0')");
				pr.execute();
				pr.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	
	public void update(int matou, int morreu, int elo, int money, int key) {
		if (!Main.status.containsKey(p.getName())) {
			Main.status.put(p.getName(), new StatusC(p.getName(), 0, 0, 0, 0, 0));
		}
		StatusC status = Main.status.get(p.getName());
		status.matou += matou;
		status.morreu += morreu;
		status.elo += elo;
		status.money += money;
		status.key += key;
		try {
			PreparedStatement pr = Main.mysql.conectar().prepareStatement("UPDATE `status` SET `matou`=`matou`+"+matou+",`morreu`=`morreu`+"+morreu+",`kay`=`kay`+"+key+",`elo`=`elo`+"+elo+",`money`=`money`+"+money+" WHERE nick='"+p.getName()+"'");
			pr.execute();
			pr.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
