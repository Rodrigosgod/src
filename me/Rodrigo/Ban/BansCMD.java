package me.Rodrigo.Ban;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Rodrigo.Metodos;

public class BansCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("ban")) {
			if (sender.hasPermission("cmd.ban")) {
				if (args.length < 2) {
					sender.sendMessage("§c§lBAN§f, use §c/ban [NICK] [MOTIVO]");
					return true;
				}
				String nick = args[0];
				String motivo = Metodos.GetALlArgs(args, 1);
				if (motivo == null) {
					motivo = "sem motivo";
				}
				new ManagerBan().banPlayer(nick, sender, motivo);
			}
		}
		if (label.equalsIgnoreCase("tempban")) {
			if (sender.hasPermission("cmd.tempban")) {
				if (args.length < 2) {
					sender.sendMessage("§c§lBAN§f, use §c/tempban [NICK] [TEMPO] [MOTIVO]");
					sender.sendMessage("§c§lEX: §e/tempban KartageLindao 1d,1h,1m | 1d = 1 dia | 1h = 1 hora | 1m = 1 minuto");
					return true;
				}
				String nick = args[0];
				String[] temSplit = args[1].split(",");
				String motivo = Metodos.GetALlArgs(args, 2);
				int dia = 0, hora = 0, min = 0;

				for (String s : temSplit) {
					if (s.contains("D") || s.contains("d")) {
						dia = Integer.valueOf(s.replace("D", "").replace("d", ""));
					}
					if (s.contains("h") || s.contains("H")) {
						hora = Integer.valueOf(s.replace("H", "").replace("h", ""));
					}
					if (s.contains("m") || s.contains("M")) {
						min = Integer.valueOf(s.replace("M", "").replace("m", ""));
					}
				}
				
				long timeStamp = Metodos.converte(dia, hora, min, 1);
				new ManagerBan().tempBan(nick, sender, motivo, timeStamp);
			}
		}
		if (label.equalsIgnoreCase("unban")) {
			if (sender.hasPermission("cmd.unban")) {
				if (args.length != 1) {
					sender.sendMessage("§c§lBAN§f, use /unban [NICK]");
					return true;
				}
				new ManagerBan().unBan(args[0], sender);
			}
		}
		return false;
	}

}
