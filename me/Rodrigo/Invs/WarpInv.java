package me.Rodrigo.Invs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.Rodrigo.Metodos;

public class WarpInv implements Listener {

	public static void warp(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9, "§cWarps");
		
		
		inv.addItem(Metodos.itemStack(Material.LAVA_BUCKET, "§a» §7Lava"));
		inv.addItem(Metodos.itemStack(Material.GLASS, "§a» §7FPS"));
		inv.addItem(Metodos.itemStack(Material.BLAZE_ROD, "§a» §71v1"));
		inv.addItem(Metodos.itemStack(Material.POTION, "§a» §7Pote"));
		
		p.openInventory(inv);
	}
	
	
	@EventHandler
	public void click(InventoryClickEvent e){
		try {
			if(e.getInventory().getTitle().equalsIgnoreCase("§cWarps")){
				e.setCancelled(true);
				Player p = (Player)e.getWhoClicked();
				if(!e.getCurrentItem().getItemMeta().getDisplayName().contains("§71v1")){
					p.chat("/warp "+e.getCurrentItem().getItemMeta().getDisplayName().replace("§a» §7", ""));
				}else{
					p.chat("/1v1");
				}
			}
		} catch (Exception e2) {
			
		}
	}

}
