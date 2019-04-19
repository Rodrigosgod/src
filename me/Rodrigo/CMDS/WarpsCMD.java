package me.Rodrigo.CMDS;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;
import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.WARPS.WarpConfig;

public class WarpsCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("setwarp")) {
			if (!p.hasPermission("cmd.warp")) {
				return true;
			}
			if (args.length != 1) {
				p.sendMessage("§c§lERRO§f, use /setwarp §[§aFPS - RDM - LAVA - RDMPOS1 - RDMPOS2 - RDMSPAWN - MDRPOS1 - MDRSPAWN]");
				return true;
			}
			if (args[0].equalsIgnoreCase("fps") ||args[0].equalsIgnoreCase("pote") ||args[0].equalsIgnoreCase("rdmspawn") ||args[0].equalsIgnoreCase("rdmpos2") || args[0].equalsIgnoreCase("mdrspawn") || args[0].equalsIgnoreCase("mdrpos1")  ||args[0].equalsIgnoreCase("rdm") || args[0].equalsIgnoreCase("lava")) {
				p.sendMessage("§a§lAVISO§f, você setou warp: §a" + args[0].toLowerCase());
				new WarpConfig().setWarp(args[0].toUpperCase(), p.getLocation());
			}
		}
		if (label.equalsIgnoreCase("warp")) {
			if (Main.combatLog.containsKey(p) || (!KitManager.getKit(p).equalsIgnoreCase("None"))) {
				return true;
			}
			if (args.length != 1) {
				p.sendMessage("§c§lERRO§f, use /warp §[§aFPS - RDM - LAVA]");
				return true;
			}
			if (args[0].equalsIgnoreCase("fps") || args[0].equalsIgnoreCase("rdm") ||args[0].equalsIgnoreCase("pote")|| args[0].equalsIgnoreCase("lava")) {
				p.sendMessage("§a§lAVISO§f, você foi para warp: §a" + args[0].toLowerCase());
				Metodos.sendWarp(p, args[0]);
				p.teleport(new WarpConfig().getLocation(args[0].toUpperCase()));

			}
		}
		return false;
	}

}
