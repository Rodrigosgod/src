package me.Rodrigo.CMDS;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;

public class AdminCMDandEvent implements Listener, CommandExecutor {

	public static ArrayList<String> inAdmin = new ArrayList<>();
	public static HashMap<String, ItemStack[]> item = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("admin")) {
			if (!p.hasPermission("kitpvp.staff")) {
				return true;
			}
			if (inAdmin.contains(p.getName())) {
				inAdmin.remove(p.getName());
				Metodos.Clear(p);
				p.getInventory().setContents(item.get(p.getName()));
				p.sendMessage("§c§lADMIN§f, você saiu do modo!");
				for (Player all : Metodos.allPlayer()) {
					all.showPlayer(p);
				}
				p.setGameMode(GameMode.SURVIVAL);
			} else {
				inAdmin.add(p.getName());
				item.put(p.getName(), p.getInventory().getContents());
				Metodos.Clear(p);

				Metodos.Clear(p);
				p.sendMessage("§c§lADMIN§f, você entrou no modo!");
				for (Player all : Metodos.allPlayer()) {
					if (!all.hasPermission("kitpvp.staff")) {
						all.hidePlayer(p);
					}
				}
				p.setGameMode(GameMode.CREATIVE);
				item(p);
			}
		}
		return false;
	}

	public void item(Player p) {
		p.getInventory().addItem(Metodos.itemStack(Material.SLIME_BALL, "§cQuick Admin"));
		p.getInventory().addItem(Metodos.itemStack(Material.BEDROCK, "§cArena"));
	}

	@EventHandler
	public void a(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (inAdmin.contains(p.getName())) {
			if (p.getItemInHand().getType() == Material.SLIME_BALL) {
				p.chat("/admin");
				new BukkitRunnable() {
					public void run() {
						p.chat("/admin");
					}
				}.runTaskLater(Main.main, 5);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for (String s : inAdmin) {
			Player r = Bukkit.getPlayer(s);
			if (r != null) {
				p.hidePlayer(r);
			}
		}
	}

	@EventHandler
	public void interact(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked().getType() == EntityType.PLAYER) {
			if (!inAdmin.contains(p.getName())) {
				return;
			}
			Player r = (Player) e.getRightClicked();
			if(p.getItemInHand().getType() == Material.AIR){
				p.openInventory(r.getInventory());
				p.sendMessage("§cVocê esta vendo inventario de: §f" + r.getName());
			}
			if(p.getItemInHand().getType() == Material.BEDROCK){
				Metodos.CriarArena(p, r);
			}
		}
	}

}
