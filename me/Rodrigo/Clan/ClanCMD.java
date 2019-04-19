package me.Rodrigo.Clan;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import me.Rodrigo.Metodos;
import me.Rodrigo.Score.Score;

public class ClanCMD implements CommandExecutor {
	
	
	public static HashMap<String, String> convite = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("clan")) {
			if (args.length == 0) {
				p.sendMessage("§c§lCLAN§f, para saber os comandos use §a/clan help");
				return true;
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {
					p.sendMessage("§b§l§m----------------------\n");
					p.sendMessage("§fPara criar um clan use §b/clan criar §7[§bTAG§7] §7[§bNOME§7]");
					p.sendMessage("§fPara convidar um jogador use §b/clan convidar §7[§bNICK§7]");
					p.sendMessage("§fPara remover um jogador use §b/clan remove §7[§bNICK§7]");
					p.sendMessage("§fPara entrar em um clan use §b/clan aceitar");
					p.sendMessage("§fPara ver o status do clan use §b/clan status");
					p.sendMessage("§fPara ver os integrantes do clan use §b/clan integrantes");
					p.sendMessage("§fPara excluir seu clan use §b/clan delete");
					p.sendMessage("§fPara entrar no chat do clan use §b/clan chat");
					p.sendMessage("§b§l§m----------------------");
					return true;
				}
				if(args[0].equalsIgnoreCase("chat")){
					ClanManager clan = new ClanManager();
					if(clan.hasClanPlayer(p)){
						if(clan.hasChat(p)){
							p.sendMessage("§c§lCLAN§f, você saiu do chat do clan!");
							ClanManager.chatClan.put(p, false);
						}else{
							p.sendMessage("§c§lCLAN§f, você entrou no chat do clan!");
							ClanManager.chatClan.put(p, true);
						}
					}
				}
				if(args[0].equalsIgnoreCase("integrantes")){
					ClanManager clan = new ClanManager();
					if(!clan.hasClanPlayer(p)){
						return true;
					}
					p.sendMessage("§a§lIntegrantes §f↓");
					
					for(String s : clan.getClan(p).integrantes){
						String sta = "";
						if(Bukkit.getPlayer(s) == null){
							sta = "§coffline";
						}else{
							sta = "§aonline";
						}
						p.sendMessage("§a- §7"+s + " §f(" + sta + "§f)");
					}
				}
				if (args[0].equalsIgnoreCase("delete")) {
					ClanManager clan = new ClanManager();
					if(!clan.hasDono(p)){
						p.sendMessage("§c§lCLAN§f, você não tem um clan!");
						return true;
					}
					List<String> inte = clan.getClan(p).integrantes;
					Boolean delete = clan.deleteClan(p);
					if(delete){
						p.sendMessage("§c§lCLAN§f, você excluiu seu clan!");
						Score.send(p);
						for(String s : inte){
							Bukkit.getPlayer(s).sendMessage("§c§lCLAN§f, seu clan foi excluido!");
							Bukkit.getPlayer(s).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
							Bukkit.getPlayer(s).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
							Score.send(Bukkit.getPlayer(s));
						}
					}
				}
				if (args[0].equalsIgnoreCase("status")) {
					ClanC clan = new ClanManager().getClan(p);
					if(!new ClanManager().hasClanPlayer(p)){
						p.sendMessage("§c§lCLAN§f, você nao tem um clan!");
						return true;
					}
					p.sendMessage("§a§l§m-------------------");
					p.sendMessage("§7Dono: §c" + clan.dono);
					p.sendMessage("§7Nome: §c" + clan.nome);
					p.sendMessage("§7Tag: §c" + clan.tag);
					p.sendMessage("§7Kills: §c" + clan.kills);
					p.sendMessage("§7Deaths: §c" + clan.deaths);
					p.sendMessage("§7Elo: §c" + clan.elo);
					p.sendMessage("§7Money: §c" + clan.money);
					p.sendMessage("§a§l§m-------------------");
					
				}
				
				if(args[0].equalsIgnoreCase("aceitar")){
					if(!convite.containsKey(p.getName())){
						p.sendMessage("§c§lCLAN§f, você nao tem convite!");
						return true;
					}
					p.sendMessage("§c§lCLAN§f, você entrou no clan: " + convite.get(p.getName()));
					new ClanManager().addIntegrante(convite.get(p.getName()), p.getName());
					p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					Score.send(p);
				}
				
			} else if (args.length == 2) {
				if(args[0].equalsIgnoreCase("remove")){
					ClanManager clan = new ClanManager();
					
					if(!clan.hasDono(p)){
						p.sendMessage("§c§lCLAN§f, você não tem um clan!");
						return true;
					}
					if(Bukkit.getPlayer(args[1]) == null){
						p.sendMessage("§c§lCLAN§f, erro player offline!");
						return true;
					}
					Player r = Bukkit.getPlayer(args[1]);
					if(!clan.getIntegrantes(p).contains(r.getName())){
						p.sendMessage("§c§lCLAN§f, erro este jogador não esta em seu clan!");
					}else{
						p.sendMessage("§c§lCLAN§f, você removeu §e" + Bukkit.getPlayer(args[1]).getName()+ " §fdo seu clan!");
						r.sendMessage("§c§lCLAN§f, você foi removido do clan §e" + clan.getClanNome(p));
						clan.removeIntegrante(clan.getClanNome(p), r.getName());
						r.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						Score.send(r);
						for(Player all : Metodos.allPlayer()){
							Score.setNick(all);
						}
					
					}
				}
				if(args[0].equalsIgnoreCase("convidar")){
					ClanManager clan = new ClanManager();
				
					if(!clan.hasDono(p)){
						p.sendMessage("§c§lCLAN§f, você não tem um clan!");
						return true;
					}
					if(Bukkit.getPlayer(args[1]) == null){
						p.sendMessage("§c§lCLAN§f, erro player offline!");
						return true;
					}
					Player r = Bukkit.getPlayer(args[1]);
					if(clan.getIntegrantes(p).contains(r.getName())){
						p.sendMessage("§c§lCLAN§f, erro este jogador ja esta em seu clan!");
					}else{
						p.sendMessage("§c§lCLAN§f, você convidou §e" + Bukkit.getPlayer(args[1]).getName()+ " §fpara seu clan!");
						r.sendMessage("§c§lCLAN§f, você foi convidado para o clan §e" + clan.getClanNome(p));
						convite.put(r.getName(), clan.getClanNome(p));
					}
				}

			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("criar")) {
					String tag = args[1];
					String nome = args[2];
					if (tag.length() > 3 || tag.length() < 1) {
						p.sendMessage("§c§lCLAN§f, a tag deve conter até 3 letras");
						return true;
					}
					if (nome.length() > 6 || nome.length() < 1) {
						p.sendMessage("§c§lCLAN§f, o nome deve conter até 6 letras");
						return true;
					}
					if(new ClanManager().hasClanPlayer(p)){
						p.sendMessage("§c§lCLAN§f, você ja esta em um clan!");
						return true;
					}
					ClanManager clan = new ClanManager();
					if(!clan.hasClan(nome)){
						Boolean create = clan.createClan(nome, tag, p.getName(), p.getName());
						if(create){
							p.sendMessage("§c§lCLAN§f, seu clan foi criado com sucesso!");
							Score.setNick(p);
						}else{
							p.sendMessage("§c§lCLAN§f, ocorreu um erro ao criar seu clan :/");
						}
					}else{
						p.sendMessage("§c§lCLAN§f, já existe um clan com este nome!");
					}
				}
			}
		}
		return false;
	}

}
