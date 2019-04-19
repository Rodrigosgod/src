package me.Rodrigo.CMDS;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;
import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.Manager.TagManager;
import me.Rodrigo.Manager.TagManager.Tags;
import me.Rodrigo.RDM.RdmManager;
import me.Rodrigo.Manager.TpManager;
import me.Rodrigo.Score.Score;

public class ComandosBasicos implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("bc") || label.equalsIgnoreCase("broadcast") || label.equalsIgnoreCase("aviso")) {
			if (sender.hasPermission("kitpvp.staff")) {
				if (args.length == 0) {
					sender.sendMessage("§c§lERRO§f, use §a/" + label.toLowerCase() + " §7[§aMSG§7]");
					return true;
				}
				String msg = "";

				for (String s : args) {
					msg += s.replace("&", "§") + " ";
				}

				Bukkit.broadcastMessage("§7[§c§l" + label.toUpperCase() + "§7] " + msg);
			} else {
				sender.sendMessage("§c§lERRO§f, você não tem permissao para este comando!");
			}
		}

		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("sc")) {
			if(!p.hasPermission("staff.sc")){
				return true;
			}
			if (args.length < 1) {
				p.sendMessage("§c§lSC§f, use /sc§c {MSG}");
			} else {
				for (Player all : Metodos.allPlayer()) {
					if (all.hasPermission("staff.sc")) {
						all.sendMessage("§7[§c§lSC§7]§e" + p.getName() + "§b > §f" + Metodos.GetALlArgs(args, 0));
					}
				}
			}
		}
		
		if(label.equalsIgnoreCase("settp")){
			if(!p.hasPermission("cmd.settp")){
				return true;
			}
			p.sendMessage("§c§lAVISO§f, você setou TP!");
			TpManager.set(p.getLocation());
		}

		if (label.equalsIgnoreCase("mob")) {
			if (args.length != 1) {
				p.sendMessage("§c§lERRO§f, use /mob §7[§aSOPA - RECRAFT - POTE§7]");
				return true;
			}
			if (args[0].equalsIgnoreCase("sopa")) {
				Villager blaze = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
				blaze.setCustomName("§a» §7Sopas §a«");
				blaze.setCustomNameVisible(true);
				Metodos.cancelarMovimento(blaze);
				p.sendMessage("§cVocê colocou um mob de §f" + args[0].toLowerCase());
			}
			if (args[0].equalsIgnoreCase("recraft")) {
				Villager blaze = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
				blaze.setCustomName("§a» §7Recraft §a«");
				blaze.setCustomNameVisible(true);
				Metodos.cancelarMovimento(blaze);
				p.sendMessage("§cVocê colocou um mob de §f" + args[0].toLowerCase());
			}
			if (args[0].equalsIgnoreCase("pote")) {
				Villager blaze = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
				blaze.setCustomName("§a» §7Pote §a«");
				blaze.setCustomNameVisible(true);
				Metodos.cancelarMovimento(blaze);
				p.sendMessage("§cVocê colocou um mob de §f" + args[0].toLowerCase());
			}
		}

		if (label.equalsIgnoreCase("tp")) {
			if (!p.hasPermission("kitpvp.staff")) {
				p.sendMessage("§cVocê não tem permissão para usar esse comando, NIUBA! ");
				return true;
			}
			if (args.length == 0) {
				p.sendMessage("§aAVISO§f, Para se teleportar para player use §c/tp <NICK>");
				p.sendMessage("§aAVISO§f, Para se teleportar para cords use §c/tp <X> <Y> <Z>");
				return false;
			} else if (args.length == 1) {
				Player r = Bukkit.getPlayer(args[0]);
				if (r != null) {
					p.teleport(r.getLocation());
					for (Player all : Metodos.allPlayer()) {
						if (all.hasPermission("kitpvp.staff")) {
							all.sendMessage("§7" + p.getName() + "§a teleportou-se para §f" + r.getName());
						}
					}
				}

				return true;
			}else if (args.length == 2) {
				Player one = Bukkit.getPlayer(args[0]);
				Player two = Bukkit.getPlayer(args[1]);
				if(one != null && two != null){
					one.teleport(two.getLocation());
					p.sendMessage("§c§lTP§f, você teleportou §e" + one.getName() + "§f para §e" + two.getName());
				}
			
			}
			
			else if (args.length == 3) {
				try {
					double x = Double.valueOf(args[0]);
					double y = Double.valueOf(args[1]);
					double z = Double.valueOf(args[2]);
					p.teleport(new Location(p.getWorld(), x, y, z));
					for (Player all : Metodos.allPlayer()) {
						if (all.hasPermission("kitpvp.staff")) {
							all.sendMessage("§7" + p.getName() + "§a teleportou-se para §f" + x + " " + y + " " + z);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				p.sendMessage("§aAVISO§f, Para se teleportar para player use §c/tp <NICK>");
				p.sendMessage("§aAVISO§f, Para se teleportar para cords use §c/tp <X> <Y> <Z>");
			}
		}

		if (label.equalsIgnoreCase("spawn")) {
			if (Main.combatLog.containsKey(p)) {
				p.sendMessage("§c§lAVISO§f, Você esta em combat!");
				return true;
			}
			if(RdmManager.rdm.contains(p.getName())){
				return true;
			}
			p.sendMessage("§a§lAVISO§f, Você será enviado ao spawn!");
			p.teleport(Bukkit.getWorld("world").getSpawnLocation());
			KitManager.kit.remove(p);
			Metodos.ItemSpawn(p);
		}
		if (label.equalsIgnoreCase("tag")) {
			String tem = "";
			String ntem = "";
			ArrayList<Tags> tags = new ArrayList<>();
			for (Tags t : Tags.values()) {
				tags.add(t);
				if (p.hasPermission("tag." + t.color)) {
					if (tem.isEmpty()) {
						tem = "§7" + t.name();
					} else {
						tem += "§a, §7" + t.color;
					}
				} else {
					if (ntem.isEmpty()) {
						ntem = "§7" + t.color;
					} else {
						ntem += "§c, §7" + t.color;
					}
				}
			}
			if (args.length != 1) {
				if (!tem.isEmpty()) {
					p.sendMessage("§a§lSUAS TAGS: §f" + tem);
				} else {
					p.sendMessage("§a§lSUAS TAGS: §fNormal");
				}
				if (!ntem.isEmpty()) {
					p.sendMessage("§a§lOUTRAS TAGS: §f" + ntem);
				} else {
					p.sendMessage("§a§lOUTRAS TAGS: §fVocê tem todas tags");
				}
				return true;
			}
			if (args.length == 1) {
				for (Tags t : tags) {
					if (args[0].equalsIgnoreCase(t.name())) {
						if (p.hasPermission("tag." + args[0])) {
							p.sendMessage("§a§lAVISO§f, Você pegou tag: " + t.color );
							TagManager.setTag(p, t);
							Score.send(p);
							p.setDisplayName(t.color + p.getName());
						} else {
							p.sendMessage("§cVocê nao tem esta tag,seu niuba!");
						}
					}
				}

				return true;
			}

		}
		if (label.equalsIgnoreCase("kit")) {
			if(!KitManager.getKit(p).equalsIgnoreCase("None")){
				p.sendMessage("§c§lERRO§f, você ja esta usando um kit!");
				return true; 
			}
			if(RdmManager.rdm.contains(p.getName())){
				return true;
			}
			String tem = "";
			String ntem = "";
			ArrayList<String> kits = new ArrayList<>();
			for (KitC k : KitC.values()) {
				kits.add(k.name().toLowerCase());
				if (p.hasPermission("kit." + k.name())) {
					if (tem.isEmpty()) {
						tem = k.name();
					} else {
						tem += "§a,§f " + k.name();
					}
				} else {
					if (ntem.isEmpty()) {
						ntem = k.name();
					} else {
						ntem += "§c,§f " + k.name();
					}
				}
			}
			if (args.length != 1) {
				if (!tem.isEmpty()) {
					p.sendMessage("§a§lSEUS KITS: §f" + tem);
				} else {
					p.sendMessage("§c§lERRO§f, você não tem nenhum kit! seu pobre");
				}
				if (!ntem.isEmpty()) {
					p.sendMessage("§a§lOUTROS KITS: §f" + ntem);
				} else {
					p.sendMessage("§c§lERRO§f, você tem todos kits! :D");
				}
				return true;
			}
			if (args.length == 1) {
				String kit = args[0];
				if (!kits.contains(kit.toLowerCase())) {
					p.sendMessage("§c§lERRO§f, este kit nao existe!");
					return true;
				}
				if (p.hasPermission("kit." + kit)) {
					p.sendMessage("§a§lAVISO§f, Você pegou kit: §a" + kit);
					KitManager.setKitAndItem(p, kit);
				} else {
					p.sendMessage("§cvocê não tem este kit!");
				}
				return true;
			}

		}

		return false;
	}

}
