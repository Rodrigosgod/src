package me.Rodrigo.Ban;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;

public class ManagerBan {

	public void banPlayer(String banido, CommandSender staff, String motivo) {
		try {
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `ban` WHERE `nick`='"+banido+"'");
			
			if(rs.next()){
				staff.sendMessage("§c§lBAN§f, este jogador ja esta banido!");
			}else{
				Main.tt.updateStatus("🔐 O jogador: "+banido + "\nFoi banido do servidor\nMotivo: "+motivo+"\nStaff: "+staff.getName());
				Bukkit.broadcastMessage("§c[BAN] §eo jogador: §f"+banido+" §efoi banido do servidor Motivo: §f"+motivo);
				
				PreparedStatement pr = Main.mysql.conectar().prepareStatement("INSERT INTO `ban`(`nick`, `motivo`, `staff`, `tempban`, `expira`) VALUES ('"+banido+"','"+motivo+"','"+staff.getName()+"','0','0');");
				pr.execute();
				pr.close();
				
				Player b = Bukkit.getPlayer(banido);
				if(b != null){
					b.kickPlayer("§cVocê foi banido do servidor\n§cMotivo: §f"+motivo+"\n§cStaff: §f"+staff.getName());
				}
			}
			
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void unBan(String banido, CommandSender staff) {
		try {
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `ban` WHERE `nick`='" + banido + "'");
			if (!rs.next()) {
				staff.sendMessage("§cEste player nao esta banido!");
			} else {
				PreparedStatement pr = Main.mysql.conectar().prepareStatement("DELETE FROM `ban` WHERE `nick`='" + banido + "'");
				pr.execute();
				pr.close();
				Bukkit.broadcastMessage("§c§lBAN§f, jogador §e"+banido+" §ffoi desbanido!");
				Main.tt.updateStatus("🔐 O jogador: "+banido + "\nFoi desbanido do servidor");
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void unBanNoMSG(String banido) {
		try {

			PreparedStatement pr = Main.mysql.conectar().prepareStatement("DELETE FROM `ban` WHERE `nick`='" + banido + "'");
			pr.execute();
			pr.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	
	public void tempBan(String banido, CommandSender staff, String motivo,long tempo) {
		try {
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `ban` WHERE `nick`='"+banido+"'");
			
			if(rs.next()){
				staff.sendMessage("§c§lBAN§f, este jogador ja esta banido!");
			}else{
				Main.tt.updateStatus("🔐 O jogador: "+banido + "\nFoi banido do servidor\nMotivo: "+motivo+"\nStaff: "+staff.getName() + "\nTempo: "+Metodos.msg(tempo));
				Bukkit.broadcastMessage("§c[BAN] §eo jogador: §f"+banido+" §efoi TempBanido do servidor Motivo: §f"+motivo+" §eTempo: §f"+Metodos.msg(tempo));
				
				PreparedStatement pr = Main.mysql.conectar().prepareStatement("INSERT INTO `ban`(`nick`, `motivo`, `staff`, `tempban`, `expira`) VALUES ('"+banido+"','"+motivo+"','"+staff.getName()+"','1','"+tempo+"');");
				pr.execute();
				pr.close();
				
				Player b = Bukkit.getPlayer(banido);
				if(b != null){
					b.kickPlayer("§cVocê foi banido do servidor\n§cMotivo: §f"+motivo+"\n§cStaff: §f"+staff.getName());
				}
			}
			
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
