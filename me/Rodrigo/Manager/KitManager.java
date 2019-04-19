package me.Rodrigo.Manager;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import me.Rodrigo.Metodos;
import me.Rodrigo.C.KitC;

public class KitManager {

	public static HashMap<Player, String> kit = new HashMap<>();

	public static String getKit(Player p) {
		if (!kit.containsKey(p)) {
			kit.put(p, "None");
		}
		return kit.get(p);
	}

	public static void setKit(Player p, String kit) {
		KitManager.kit.put(p, kit);
	}
	public static Boolean checkKit(Player p,KitC k){
		return getKit(p).equalsIgnoreCase(k.name());
	}

	public static void setKitAndItem(Player p, String s) {
		setKit(p, s);
		p.teleport(TpManager.getLocation());
		KitC k = KitC.valueOf(s);
		Metodos.Clear(p);
		if (k == KitC.PvP) {
			p.getInventory().setItem(0, Metodos.itemStack(Material.STONE_SWORD, "§fEspada §c"+k.name(),"", Enchantment.DAMAGE_ALL, 1));
		} else {
			p.getInventory().setItem(0, Metodos.itemStack(Material.WOOD_SWORD, "§fEspada §c"+k.name(),"", Enchantment.DAMAGE_ALL, 1));
		}
		if(k.item){
			for (Material m : k.mat) {
				p.getInventory().addItem(Metodos.itemStack(m, "§7Item §c" + m.name()));
			}
		}
		p.getInventory().setItem(8, Metodos.itemStack(Material.COMPASS, "§aCompass"));
		for(int x = 0; x < 50;x++){
			p.getInventory().addItem(Metodos.itemStack(Material.MUSHROOM_SOUP, "§aSopinha"));
		}
		p.getScoreboard().getTeam("kit").setSuffix(KitManager.getKit(p));

	}

}
