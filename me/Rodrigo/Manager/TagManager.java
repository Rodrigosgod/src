package me.Rodrigo.Manager;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class TagManager {

	public enum Tags {
		Normal("§7§lNORMAL§7 "),
		VIP("§a§lVIP§7 "),
		PRO("§6§lPRO§7 "),
		DLÇ("§d§lDLÇ§7 "),
		BUILDER("§e§lBUILDER§7 "),
		YOUTUBER("§b§lYOUTUBER§7 "),
		YTPLUS("§3§lYTPLUS§7 "),
		HELPER("§9§lHELPER§7 "),
		TRIAL("§d§lTRIAL§7 "),
		MOD("§5§lMOD§7 "),
		ADMIN("§c§lADMIN§7 "),
		GERENTE("§3§lGERENTE§7 "),
		DONO("§4§lDONO§7 ");
		public String color;

		Tags(String color) {
			this.color = color;
		}
	}

	public static HashMap<Player, Tags> tag = new HashMap<>();
	
	public static void setTag(Player p, Tags t) {
		tag.put(p, t);
	}

	public static Tags getTag(Player p) {
		Tags t = null;
		if (!tag.containsKey(p)) {
			for (Tags tt : Tags.values()) {
				if (p.hasPermission("tag." + tt.name().toLowerCase())) {
					t = tt;
				}
			}
		} else {
			t = tag.get(p);
		}
		if(t == null){
			t = Tags.Normal;
		}

		return t;
	}

}
