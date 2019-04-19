package me.Rodrigo.Ban;

import java.sql.ResultSet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;

public class BanListener implements Listener {

	@EventHandler
	public void login(PlayerLoginEvent e) {
		Player p = e.getPlayer();

		try {
			ResultSet rs = Main.mysql.conectar().createStatement().executeQuery("SELECT * FROM `ban` WHERE nick='" + p.getName() + "'");
			if(rs.next()){
				Boolean tempBan = rs.getBoolean("tempban");
				if(tempBan){
					long expire = rs.getLong("expira");
					int seconds = (int) ((expire - System.currentTimeMillis()) / 1000);
					if(seconds <= 0){
						new ManagerBan().unBanNoMSG(p.getName());
						if(!Main.main.getServer().hasWhitelist()){
							e.allow();
						}
					}else{
						e.setKickMessage("§cVocê esta banido\n§fmotivo: §c" + rs.getString("motivo") + "\n§fExpira: §c"+Metodos.msg(expire) + "\n\n§ccompre unban em:\n"+Main.loja);
						e.disallow(Result.KICK_OTHER, e.getKickMessage());
					}
				}else{
					e.setKickMessage("§cVocê esta banido\n§fmotivo: §c" + rs.getString("motivo") + "\n\n§ccompre unban em:\n§f"+Main.loja);
					e.disallow(Result.KICK_OTHER, e.getKickMessage());
				}
			}
			rs.close();
		} catch (Exception e2) {
			p.kickPlayer("Erro ao entrar: " + e2.getMessage());
		}
	}

}
