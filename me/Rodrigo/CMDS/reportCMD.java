package me.Rodrigo.CMDS;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;

public class reportCMD implements CommandExecutor {

	public static ArrayList<String> coldown = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (coldown.contains(sender.getName())) {
			sender.sendMessage("§c§lREPORT§f, você esta mandando mto reports mizeravi!");
			return true;
		}
		try {
			Player nick = Bukkit.getPlayer(args[0]);
			String motivo = Metodos.GetALlArgs(args, 1);
			if (nick != null) {
				coldown.add(sender.getName());
				new BukkitRunnable() {

					@Override
					public void run() {
						coldown.remove(sender.getName());

					}
				}.runTaskLater(Main.main, 20 * 5);
				sender.sendMessage("§cVocê reportou: §f" + nick.getName() + "§c motivo: §f" + motivo);
				for (Player all : Metodos.allPlayer()) {
					if (all.hasPermission("report.ver")) {
						all.sendMessage("   §c§lREPORT NOVO");
						all.sendMessage("");
						all.sendMessage("§7Reportado: §b" + nick.getName());
						all.sendMessage("§7Motivo: §b" + motivo);
						all.sendMessage("§7Reportador: §b" + sender.getName());
						all.sendMessage("");
						Metodos.title(all, "§c§lREPORT", "§b§lNOVO", 20, 40, 20);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

}
