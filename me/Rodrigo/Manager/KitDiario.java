package me.Rodrigo.Manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Rodrigo.Main;
import me.Rodrigo.C.KitC;

public class KitDiario {

	public Player p;
	public long expira;
	public String kit;
	public String newKit;

	public KitDiario(Player p) {
		this.p = p;
	}
	
	public boolean setKit() {
		String kit = "";
		ArrayList<KitC> k = new ArrayList<>();
		for(KitC ka : KitC.values()){
			k.add(ka);
		}
		kit = k.get(new Random().nextInt(k.size()-1)).name();
		newKit = kit;
		Boolean seto = false;
		int dia = ((1*60)*60)*24;
		long tempo = System.currentTimeMillis() + dia * 1000;
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+ p.getName() + "add kit."+kit.toLowerCase());
		try {
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * from kitdiario where nick='"+p.getName()+"'");
			if(rs.next()){
				PreparedStatement pr = Main.mysql.conectar().prepareStatement("DELETE FROM `kitdiario` WHERE nick='"+p.getName()+"'");
				pr.execute();
				pr.close();
			}
			rs.close();
			PreparedStatement pr = Main.mysql.conectar().prepareStatement("INSERT INTO `kitdiario`(`nick`, `kit`, `expira`) VALUES ('"+p.getName()+"','"+kit+"','"+tempo+"')");
			pr.execute();
			pr.close();
			seto = true;
		} catch (Exception e) {

		}
		return seto;
	}
	


	public Boolean hasKit() {
		Boolean tem = false;
		
		try {
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `kitdiario` WHERE nick='"+p.getName()+"'");
			if(rs.next()){
				this.expira = rs.getLong("expira");
				this.kit = rs.getString("kit");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			tem = true;
			p.sendMessage("§cerro ao checar!");
		}
		long tempo = (expira - System.currentTimeMillis()) / 1000;
		System.out.println(tempo);
		if (tempo > 0) {
			tem = true;
		}
		return tem;
	}

}
