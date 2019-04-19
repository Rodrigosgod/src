package me.Rodrigo;

import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import me.Rodrigo.Ban.BanListener;
import me.Rodrigo.Ban.BansCMD;
import me.Rodrigo.C.StatusC;
import me.Rodrigo.CMDS.AdminCMDandEvent;
import me.Rodrigo.CMDS.ComandosBasicos;
import me.Rodrigo.CMDS.WarpsCMD;
import me.Rodrigo.CMDS.reportCMD;
import me.Rodrigo.Clan.ClanCMD;
import me.Rodrigo.Clan.ClanManager;
import me.Rodrigo.Invs.KitSelectorInv;
import me.Rodrigo.Invs.WarpInv;
import me.Rodrigo.Kits.Andarilho;
import me.Rodrigo.Kits.Combo;
import me.Rodrigo.Kits.Kangaroo;
import me.Rodrigo.Kits.KitsQueBatem;
import me.Rodrigo.Kits.SonicDeshfireFf;
import me.Rodrigo.Kits.StomperAndAntStomper;
import me.Rodrigo.Kits.Varinha;
import me.Rodrigo.Listeners.CombatLog;
import me.Rodrigo.Listeners.Primarios;
import me.Rodrigo.Listeners.Secundarios;
import me.Rodrigo.Manager.BC;
import me.Rodrigo.Manager.RankManager;
import me.Rodrigo.Manager.StatusManager;
import me.Rodrigo.Manager.TOP10;
import me.Rodrigo.Oneb1.InvCustom;
import me.Rodrigo.Oneb1.Onev1Listener;
import me.Rodrigo.RDM.RdmCmd;
import me.Rodrigo.RDM.RdmListener;
import me.Rodrigo.SQL.Mysql;
import me.Rodrigo.Score.Score;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Main extends JavaPlugin {

	public static Main main;
	public static Mysql mysql;
	public static HashMap<String, StatusC> status = new HashMap<>();
	public static String Prefix = "§e§lIrineu§f§lMC";
	public static String nome = "PVPMC";
	public static HashMap<Player, Player> combatLog = new HashMap<>();
	public static String loja = "www.god-network.com";
	public static String ts = "www.loja.irineu-mc.com";
	public static String ip = "pvp.irineu-mc.com";
	public static Twitter tt;

	public void onEnable() {
		main = this;
		setup();
		setupMysql();
		new RankManager();
		new ClanManager(this);
		ConfigurationBuilder cf = new ConfigurationBuilder();
		cf.setDebugEnabled(true);
		cf.setOAuthConsumerKey("SyKfZNoNjTzFZsnozvfKsOxKv");
		cf.setOAuthConsumerSecret("V7YyTIkTaFS1QwdWGFYBAQMw41liaUEjQAnD4A2Zl4nQ0HKNlJ");
		cf.setOAuthAccessToken("827350426538373120-T9wjY7VYsA2y4rS6gnS4YyjadenEL2D");
		cf.setOAuthAccessTokenSecret("CwylFQW943W3wwKbdoHkcuiER9q2hSO75QA3IsS0KhUeK");
		
		TwitterFactory tf = new TwitterFactory(cf.build());
		tt = tf.getInstance();
		new BC().start();
		new TOP10().topCLAN();
		new TOP10().topELO();
		
	}


	private void setupMysql() {
		mysql = new Mysql("rodrigo", "localhost", "3306", "rodrigo", "rodrigo");
		try {
			mysql.conectar().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS status (nick varchar(512),matou int,morreu int,kay int,elo int,money int)");
			mysql.conectar().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS kitdiario (nick varchar(512), kit varchar(255), expira long)");
			mysql.conectar().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS ban(nick varchar(512), motivo varchar(512), staff varchar(512), tempban boolean, expira long)");
			mysql.conectar().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS clan (nome varchar(512), tag varchar(255), dono varchar(255), integrantes varchar(255), kills int, deaths int,elo int, money int)");
		} catch (SQLException e) {
		}

	}

	private void setup() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new Primarios(), this);
		pm.registerEvents(new Secundarios(), this);
		pm.registerEvents(new KitSelectorInv(), this);
		pm.registerEvents(new CombatLog(), this);
		pm.registerEvents(new Kangaroo(), this);
		pm.registerEvents(new AdminCMDandEvent(), this);
		pm.registerEvents(new Varinha(), this);
		pm.registerEvents(new StomperAndAntStomper(), this);
		pm.registerEvents(new Andarilho(), this);
		pm.registerEvents(new Combo(), this);
		pm.registerEvents(new Onev1Listener(), this);
		pm.registerEvents(new InvCustom(), this);
		pm.registerEvents(new WarpInv(), this);
		pm.registerEvents(new KitsQueBatem(), this);
		pm.registerEvents(new RdmListener(), this);
		pm.registerEvents(new BanListener(), this);
		pm.registerEvents(new SonicDeshfireFf(), this);

		getCommand("spawn").setExecutor(new ComandosBasicos());
		getCommand("kit").setExecutor(new ComandosBasicos());
		getCommand("tag").setExecutor(new ComandosBasicos());
		getCommand("tp").setExecutor(new ComandosBasicos());
		getCommand("bc").setExecutor(new ComandosBasicos());
		getCommand("aviso").setExecutor(new ComandosBasicos());
		getCommand("broadcast").setExecutor(new ComandosBasicos());
		getCommand("settp").setExecutor(new ComandosBasicos());
		getCommand("sc").setExecutor(new ComandosBasicos());
		getCommand("mob").setExecutor(new ComandosBasicos());
		getCommand("rdm").setExecutor(new RdmCmd());
		getCommand("warp").setExecutor(new WarpsCMD());
		getCommand("setwarp").setExecutor(new WarpsCMD());
		getCommand("admin").setExecutor(new AdminCMDandEvent());
		getCommand("clan").setExecutor(new ClanCMD());
		getCommand("ban").setExecutor(new BansCMD());
		getCommand("unban").setExecutor(new BansCMD());
		getCommand("tempban").setExecutor(new BansCMD());
		getCommand("1v1").setExecutor(new Onev1Listener());
		getCommand("report").setExecutor(new reportCMD());

		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player p : Metodos.allPlayer()) {
					p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					Metodos.ItemSpawn(p);
					new StatusManager(p).setup();
					Score.send(p);
					Metodos.Holograma();
				}

			}
		}.runTaskLater(this, 40);

	}

}
