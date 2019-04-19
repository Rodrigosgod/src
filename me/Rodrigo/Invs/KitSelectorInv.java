package me.Rodrigo.Invs;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.Rodrigo.Metodos;
import me.Rodrigo.C.KitC;

public class KitSelectorInv implements Listener {

	public static Inventory inv;

	public static void menu(Player p) {

		inv = Bukkit.createInventory(p, 54, "§fKit Selector");

		inv.setItem(0, Metodos.itemStack(Material.THIN_GLASS, ""));
		inv.setItem(1, Metodos.itemStack(Material.THIN_GLASS, ""));
		inv.setItem(2, Metodos.itemStack(Material.THIN_GLASS, ""));
		inv.setItem(3, Metodos.itemStack(Material.THIN_GLASS, ""));
		inv.setItem(4, Metodos.Head(p, "§fNick: §e" + p.getName()));
		inv.setItem(5, Metodos.itemStack(Material.THIN_GLASS, ""));
		inv.setItem(6, Metodos.itemStack(Material.THIN_GLASS, ""));
		inv.setItem(7, Metodos.itemStack(Material.THIN_GLASS, ""));
		inv.setItem(8, Metodos.itemStack(Material.THIN_GLASS, ""));

		ArrayList<String> kits = new ArrayList<>();
		for (KitC kit : KitC.values()) {
			kits.add(kit.name());
		}
		Collections.sort(kits);

		for (String s : kits) {
			KitC k = KitC.valueOf(s);
			if (p.hasPermission("kit." + k.name())) {
				inv.addItem(Metodos.itemStack(k.mat[0], "§fKit §b» §7" + k.name(), k.lore));
			}
		}

		p.openInventory(inv);

	}

	@EventHandler
	public void click(InventoryClickEvent e) {
		try {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().getName().equalsIgnoreCase("§fKit Selector")) {
				e.setCancelled(true);
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§fNick: §e")) {

				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§fKit §b» §7")) {
					p.chat("/kit " + e.getCurrentItem().getItemMeta().getDisplayName().replace("§fKit §b» §7", ""));
					p.closeInventory();
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

}
