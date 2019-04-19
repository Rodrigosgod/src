package me.Rodrigo.Score;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;
import me.Rodrigo.C.StatusC;
import me.Rodrigo.Clan.ClanManager;
import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.Manager.RankManager;
import me.Rodrigo.Manager.StatusManager;
import me.Rodrigo.Manager.TagManager;

public class Score {

	public static void send(Player p) {
		Metodos.tab(p);
		try {
			StatusC status = new StatusManager(p).getStatus();
			if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) {
				SimpleScoreboard s = new SimpleScoreboard("§6● §f§lKitPvP §6●");

				s.blankLine();
				s.add("§fKit: §c");
				s.blankLine();
				s.add("§6§lSTATUS§f ↓");
				s.blankLine();
				s.add("§6→ §fMatou: §6");
				s.add("§6→ §fMorreu: §6");
				s.add("§6→ §fElo: §6");
				s.add("§6→ §fRank: §6");
				s.blankLine();

				if (new ClanManager().hasClanPlayer(p)) {
					s.add("§a→ §fClan: §a");
					s.blankLine();
				}

				s.add("§7"+Main.ip);

				s.build();
				s.send(p);

				p.getScoreboard().registerNewTeam("kit").addEntry("§fKit: §c");
				p.getScoreboard().registerNewTeam("matou").addEntry("§6→ §fMatou: §6");
				p.getScoreboard().registerNewTeam("morreu").addEntry("§6→ §fMorreu: §6");
				if (new ClanManager().hasClanPlayer(p)) {
					p.getScoreboard().registerNewTeam("clan").addEntry("§a→ §fClan: §a");
				}
				p.getScoreboard().registerNewTeam("elo").addEntry("§6→ §fElo: §6");
				p.getScoreboard().registerNewTeam("rank").addEntry("§6→ §fRank: §6");

			}

			p.getScoreboard().getTeam("kit").setSuffix(KitManager.getKit(p));
			p.getScoreboard().getTeam("matou").setSuffix("" + status.matou);
			p.getScoreboard().getTeam("morreu").setSuffix("" + status.morreu);
			if (new ClanManager().hasClanPlayer(p)) {
				p.getScoreboard().getTeam("clan").setSuffix(new ClanManager().getClanNome(p));
			}
			p.getScoreboard().getTeam("elo").setSuffix("" + status.elo);
			p.getScoreboard().getTeam("rank").setSuffix("" + new RankManager(p).getPosition() + "º");

			for (Player all : Metodos.allPlayer()) {
				setNick(all);
			}
		} catch (Exception e) {
			System.out.println("SCORE: " + e.getMessage());
		}
	}

	public static void updateTeams(Player p) {
		StatusC status = new StatusManager(p).getStatus();
		try {
			p.getScoreboard().getTeam("kit").setSuffix(KitManager.getKit(p));
			p.getScoreboard().getTeam("matou").setSuffix("" + status.matou);
			p.getScoreboard().getTeam("morreu").setSuffix("" + status.morreu);
			if (new ClanManager().hasClanPlayer(p)) {
				p.getScoreboard().getTeam("clan").setSuffix(new ClanManager().getClanNome(p));
			}
			p.getScoreboard().getTeam("elo").setSuffix("" + status.elo);
			p.getScoreboard().getTeam("rank").setSuffix("" + new RankManager(p).getPosition() + "º");
			setNick(p);
		} catch (Exception e) {
			System.err.println("SCORE: " + e.getMessage());
		}
	}

	public static void setNick(Player player) {
		Metodos.Holograma();
		for (Player online : Bukkit.getOnlinePlayers()) {
			Scoreboard scoreboard = online.getScoreboard();
			if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
				scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			}
			Team team = scoreboard.getTeam(player.getName());
			if (team == null) {
				team = scoreboard.registerNewTeam(player.getName());
			}
			team.setPrefix(TagManager.getTag(player).color);
			player.setDisplayName(TagManager.getTag(player).color + player.getName());

			ClanManager c = new ClanManager();
			String tag = c.getClanTag(player);
			if (!tag.isEmpty()) {
				team.setSuffix(" §7[§b" + c.getClanTag(player).toUpperCase() + "§7]");
			}

			team.addEntry(player.getName());
			online.setScoreboard(scoreboard);
		}

	}

}
