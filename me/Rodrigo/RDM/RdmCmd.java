package me.Rodrigo.RDM;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.WARPS.WarpConfig;

public class RdmCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (p.hasPermission("kitpvp.staff")) {
			if (args.length == 0) {
				p.sendMessage("§cRDM§f, use /rdm help");
				if (!RdmTimer.evento) {
					p.sendMessage("§fEvento fexado!");
				} else {
					if (!RdmManager.rdm.contains(p.getName())) {
						RdmManager.rdm.add(p.getName());
						p.teleport(new WarpConfig().getLocation("RDMSPAWN"));
						KitManager.setKit(p, "RDM");
					} else {
						p.teleport(p.getWorld().getSpawnLocation());
						RdmManager.rdm.remove(p.getName());
						KitManager.setKit(p, "None");
					}
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {
					p.sendMessage("§cPara iniciar use /rdm start");
					p.sendMessage("§cPara fexar use /rdm stop");
					p.sendMessage("§cPara mudar o tempo use /rdm time §a{TIME}");
				}
				if (args[0].equalsIgnoreCase("start")) {
					if (RdmTimer.evento) {
						p.sendMessage("§cevento ja aberto!");
					} else {
						RdmTimer.IniciarTimer();
						p.sendMessage("§cvocê abriu o evento");
					}
				}
				if (args[0].equalsIgnoreCase("stop")) {
					if (!RdmTimer.evento) {
						p.sendMessage("§cevento ja está fexado!");
					} else {
						RdmTimer.evento = false;
						p.sendMessage("§cvocê fexou o evento");
					}
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("time")) {
					try {
						int tempo = Integer.valueOf(args[1]);
						p.sendMessage("§cVocê mudou o tempo para: §f" + tempo);
						RdmTimer.tempo = tempo;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			} else {
				p.sendMessage("§cRDM§f, use /rdm help");
			}
		} else {
			if (!RdmTimer.evento) {
				p.sendMessage("§fEvento fexado!");
			} else {
				if (!RdmManager.rdm.contains(p.getName())) {
					RdmManager.rdm.add(p.getName());
					p.teleport(new WarpConfig().getLocation("RDMSPAWN"));
				} else {
					p.teleport(p.getWorld().getSpawnLocation());
					RdmManager.rdm.remove(p.getName());
				}
			}
		}
		return false;
	}

}
