package me.Rodrigo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.C.TOP10C;
import me.Rodrigo.Manager.Hologram;
import me.Rodrigo.Manager.KitManager;
import me.Rodrigo.Manager.TOP10;
import me.Rodrigo.Score.Score;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Metodos {

	public static void cancelarMovimento(Entity en) {
		net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) en).getHandle();
		NBTTagCompound compound = new NBTTagCompound();
		nmsEn.c(compound);
		compound.setByte("NoAI", (byte) 1);
		nmsEn.f(compound);
	}

	public static String getIpPlayer(Player p) {
		return p.getAddress().getAddress().getHostAddress();
	}
	
	
	public static Hologram holo;
	
	
	public static void Holograma() {
		if (holo != null) {
			holo.hideAll();
		}
		Location loc = new Location(Bukkit.getWorld("world"), -773, 77, -879);
		String[] lines = new String[TOP10.top10.size() + 2];

		lines[0] = "§e§l§m---------§f§l TOP ELO §e§l§m---------";

		int x = 1;
		for (TOP10C c : TOP10.top10) {
			lines[x] = "§f" + x + "§aº §fNick: §a" + c.nick + " §a- §fElo: §a" + c.valor;
			x++;

		}
		lines[x] = "§e§l§m----------------------------------";

		Hologram h = new Hologram(lines, loc);
		h.showAll();
		holo = h;
		HologramaClan();
	}
	public static Hologram holo1;
	public static void HologramaClan() {
		if (holo1 != null) {
			holo1.hideAll();
		}
		Location loc = new Location(Bukkit.getWorld("world"), -779, 77, -879);
		String[] lines = new String[TOP10.topCLAN.size() + 2];

		lines[0] = "§c§l§m---------§f§l TOP CLAN §c§l§m---------";

		int x = 1;
		for (TOP10C c : TOP10.topCLAN) {
			lines[x] = "§f" + x + "§eº §fNome: §e" + c.nick + " §e- §fElo: §e" + c.valor;
			x++;

		}
		lines[x] = "§c§l§m----------------------------------";

		Hologram h = new Hologram(lines, loc);
		h.showAll();
		holo1 = h;
	}
	
	
	public static void title(Player p, String s,String n,int x,int y,int z){
		PacketPlayOutTitle times = new PacketPlayOutTitle(x, y, z); 
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("\""+s+"\""));
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("\""+n+"\""));
		for(Packet<?> packet : new Packet[] {times, title, subtitle}) {
		    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	
    public static long converte(final long dia, final long hora, final long minuto, final long segu) {
        long x = 0L;
        final long min = minuto * 60L;
        final long hor = hora * 3600L;
        final long dias = dia * 86400L;
        x = min + hor + dias + segu;
        final long tempo1 = System.currentTimeMillis() + x * 1000L;
        return tempo1;
    }
    public static ArrayList<Location> getCircle(final Location loc, final Integer r) {
        final ArrayList<Location> locations = new ArrayList<Location>();
        final int y = loc.getBlockY();
        for (double i = 0.0; i < 360.0; i += 0.1) {
            final double angle = i * 3.141592653589793 / 180.0;
            final int x = (int)(loc.getX() + r * Math.cos(angle));
            final int z = (int)(loc.getZ() + r * Math.sin(angle));
            locations.add(new Location(loc.getWorld(), (double)x, (double)y, (double)z));
        }
        return locations;
    }
	public static void sendWarp(Player p, String name) {
		KitManager.setKit(p, name.toUpperCase());
		Clear(p);
		if (name.equalsIgnoreCase("fps")) {
			p.getInventory().setHelmet(itemStack(Material.IRON_HELMET, ""));
			p.getInventory().setChestplate(itemStack(Material.IRON_CHESTPLATE, ""));
			p.getInventory().setLeggings(itemStack(Material.IRON_LEGGINGS, ""));
			p.getInventory().setBoots(itemStack(Material.IRON_BOOTS, ""));

			p.getInventory().setItem(0, itemStack(Material.DIAMOND_SWORD, "§cFPS", null, Enchantment.DAMAGE_ALL, 1));
			for (int x = 0; x < 60; x++) {
				p.getInventory().addItem(itemStack(Material.MUSHROOM_SOUP, "§aSopa"));
			}
		}else if (name.equalsIgnoreCase("pote")) {
			p.getInventory().setHelmet(itemStack(Material.DIAMOND_HELMET, ""));
			p.getInventory().setChestplate(itemStack(Material.DIAMOND_CHESTPLATE, ""));
			p.getInventory().setLeggings(itemStack(Material.DIAMOND_LEGGINGS, ""));
			p.getInventory().setBoots(itemStack(Material.DIAMOND_BOOTS, ""));

			p.getInventory().setItem(0, itemStack(Material.DIAMOND_SWORD, "§cPOTE", null, Enchantment.DAMAGE_ALL, 5));
			for (int x = 0; x < 60; x++) {
				p.getInventory().addItem(Metodos.itemStack(Material.POTION, "§cPote", 1, 16453, null));
			}
		} 
		
		else if (name.equalsIgnoreCase("lava")) {

			for (int x = 0; x < 60; x++) {
				p.getInventory().addItem(itemStack(Material.MUSHROOM_SOUP, "§aSopa"));
			}
		}
		Score.updateTeams(p);
	}

	public static String msg(final long endOfBan) {
		String message = "";
		final long now = System.currentTimeMillis();
		final long diff = endOfBan - now;
		int seconds = (int) (diff / 1000L);
		if (seconds >= 86400) {
			final int days = seconds / 86400;
			seconds %= 86400;
			if (days > 1) {
				message = String.valueOf(message) + days + " dias ";
			} else {
				message = String.valueOf(message) + days + " dia ";
			}
		}
		if (seconds >= 3600) {
			final int hours = seconds / 3600;
			seconds %= 3600;
			if (hours > 1) {
				message = String.valueOf(message) + hours + " horas ";
			} else {
				message = String.valueOf(message) + hours + " hora ";
			}
		}
		if (seconds >= 60) {
			final int min = seconds / 60;
			seconds %= 60;
			if (min > 1) {
				message = String.valueOf(message) + min + " minutos ";
			} else {
				message = String.valueOf(message) + min + " minuto ";
			}
		}
		if (seconds >= 0) {
			if (seconds > 1) {
				message = String.valueOf(message) + seconds + " segundos ";
			} else {
				message = String.valueOf(message) + seconds + " segundo ";
			}
		}
		return message;
	}

	@SuppressWarnings("deprecation")
	public static void Head(Player p, String nome, int slot, List<String> lore) {
		ItemStack skull = new ItemStack(Material.getMaterial(397));

		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setLore(lore);
		skull.setDurability((short) 3);
		sm.hasOwner();
		sm.setOwner(p.getName());
		sm.setDisplayName(nome);
		skull.setItemMeta(sm);

		p.getInventory().setItem(slot, skull);
	}

	public static void tab(Player p) {
		String header = "\n   §c§l§m-§6§l§m-§e§l§m-§a§l§m-§b§l§m-§b§l " + Main.nome
				+ " §b§l§m-§a§l§m-§e§l§m-§6§l§m-§c§l§m-§f   \n";
		String footer = "\n   §eSite: §b" + Main.loja + "\n   §eTeamSpeak: §b" + Main.ts + "\n   ";
		sendTabTitle(p, header, footer);
	}

	public static void sendTabTitle(Player player, String header, String footer) {
		if (header == null)
			header = "";
		header = ChatColor.translateAlternateColorCodes('&', header);

		if (footer == null)
			footer = "";
		footer = ChatColor.translateAlternateColorCodes('&', footer);

		header = header.replaceAll("%player%", player.getDisplayName());
		footer = footer.replaceAll("%player%", player.getDisplayName());

		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFoot);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.sendPacket(headerPacket);
		}
	}

	public static Collection<? extends Player> allPlayer() {
		return Bukkit.getOnlinePlayers();
	}

	public static void SetRopaColorida(Player p, Color cor) {
		ItemStack c = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta cm = (LeatherArmorMeta) c.getItemMeta();
		cm.setColor(cor);
		c.setItemMeta(cm);

		p.getInventory().setHelmet(c);

		ItemStack c1 = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta cm1 = (LeatherArmorMeta) c1.getItemMeta();
		cm1.setColor(cor);
		c1.setItemMeta(cm1);
		p.getInventory().setChestplate(c1);

		ItemStack c11 = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta cm11 = (LeatherArmorMeta) c11.getItemMeta();
		cm11.setColor(cor);
		c11.setItemMeta(cm11);

		p.getInventory().setLeggings(c11);

		ItemStack c111 = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta cm111 = (LeatherArmorMeta) c111.getItemMeta();
		cm111.setColor(cor);
		c111.setItemMeta(cm111);

		p.getInventory().setBoots(c111);

		p.updateInventory();

	}

	public static int getAmount(Player player, Material id) {
		int amout = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			if ((item != null) && (item.getType() == id) && (item.getAmount() > 0)) {
				amout += item.getAmount();
			}
		}
		return amout;
	}

	public static Inventory criarmenu(int slot, String nome) {
		return Bukkit.createInventory(null, slot, nome);
	}

	public static void Item1v1(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(0, itemStack(Material.IRON_FENCE, 0, 1, "§7» §a1v1 FullIron §7«"));
		p.getInventory().setItem(1, itemStack(Material.BLAZE_ROD, 0, 1, "§7» §a1v1 Normal §7«"));
		p.getInventory().setItem(8, itemStack(Material.BLAZE_POWDER, 0, 1, "§7» §aSair da 1v1 §7«"));
	}

	@SuppressWarnings("deprecation")
	public static ItemStack head(String nome, List<String> lore) {
		ItemStack skull = new ItemStack(Material.getMaterial(397));

		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setLore(lore);
		skull.setDurability((short) 3);
		sm.hasOwner();
		sm.setOwner(nome.replace("§7» §a", ""));
		sm.setDisplayName(nome);
		skull.setItemMeta(sm);
		return skull;
	}

	public static ItemStack itemstack(Material mat, int data, int quantia, String nome, List<String> lore) {
		ItemStack a = new ItemStack(mat, quantia, (short) data);
		ItemMeta am = a.getItemMeta();
		am.setLore(lore);
		am.setDisplayName(nome);
		a.setItemMeta(am);
		return a;
	}

	public static void SetItemHotBar(Player p, String nome, int slot, int data, Material mat) {
		ItemStack sopa = new ItemStack(mat, 1, (short) data);
		ItemMeta sopam = sopa.getItemMeta();
		sopam.setDisplayName(nome);
		sopa.setItemMeta(sopam);
		p.getInventory().setItem(slot, sopa);
	}

	public static void spawnRandomFirework(Location loc) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		Random r = new Random();
		int rt = r.nextInt(4) + 1;
		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		if (rt == 1) {
			type = FireworkEffect.Type.BALL;
		}
		if (rt == 2) {
			type = FireworkEffect.Type.BALL_LARGE;
		}
		if (rt == 3) {
			type = FireworkEffect.Type.BURST;
		}
		if (rt == 4) {
			type = FireworkEffect.Type.CREEPER;
		}
		if (rt == 5) {
			type = FireworkEffect.Type.STAR;
		}
		Color c1 = Color.RED;
		Color c2 = Color.YELLOW;
		Color c3 = Color.ORANGE;
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withColor(c2)
				.withFade(c3).with(type).trail(r.nextBoolean()).build();
		fwm.addEffect(effect);
		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);
		fw.setFireworkMeta(fwm);
	}

	public static ItemStack ItemMenu(Material mat, int cor, String nome, List<String> lore) {
		ItemStack item = new ItemStack(mat, 1, (short) cor);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setLore(lore);
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemStack(Material material, int durability, int amount, String nome) {
		ItemStack item = new ItemStack(material, amount, (short) durability);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemStack(Material material, int durability, int amount, String nome, List<String> lore) {
		ItemStack item = new ItemStack(material, amount, (short) durability);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setLore(lore);
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemStack(Material material, String nome, int amount, int durability) {
		ItemStack item = new ItemStack(material, amount, (short) durability);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static void fillInvGlass(Material mat, Inventory inv, int cor) {
		ItemStack a1 = new ItemStack(mat, 1, (short) cor);
		ItemMeta am1 = a1.getItemMeta();
		am1.setDisplayName("§7-");
		a1.setItemMeta(am1);
		inv.addItem(a1);
		ItemStack[] arrayOfItemStack;
		int descpyro = (arrayOfItemStack = inv.getContents()).length;
		for (int metapyro = 0; metapyro < descpyro; metapyro++) {
			ItemStack item = arrayOfItemStack[metapyro];
			if (item == null) {
				inv.setItem(inv.firstEmpty(), a1);
			}
		}
	}

	public static String Convert(Integer i) {
		int minutes = i.intValue() / 60;
		int seconds = i.intValue() % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + ":" + disSec;
		return formattedTime;
	}

	public static String TimePerfeito(Integer i) {
		int minutes = i / 60;
		int seconds = i % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes;
		String disSec = (seconds < 10 ? "" : "") + seconds;
		String formattedTime;
		if (i >= 60) {
			formattedTime = disMinu + "m " + disSec + "s";
		} else {
			formattedTime = disSec + "s";
		}

		if (i % 60 == 0) {
			formattedTime = disMinu + "m";
		}

		return formattedTime;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack head(String owner) {
		ItemStack skull = new ItemStack(Material.getMaterial(397));
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		skull.setDurability((short) 3);
		sm.hasOwner();
		sm.setOwner(owner);
		sm.setDisplayName("§e" + owner);
		skull.setItemMeta(sm);
		return skull;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack head(String owner, String d) {
		ItemStack skull = new ItemStack(Material.getMaterial(397));
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		skull.setDurability((short) 3);
		sm.hasOwner();
		sm.setOwner(owner);
		sm.setDisplayName("§e" + d);
		skull.setItemMeta(sm);
		return skull;
	}

	public static void CriarArena(Player p, Player o) {
		o.getLocation().add(0.0D, 13.0D, 0.0D).getBlock().setType(Material.BEDROCK);
		o.getLocation().add(0.0D, 11.0D, 1.0D).getBlock().setType(Material.BEDROCK);
		o.getLocation().add(1.0D, 11.0D, 0.0D).getBlock().setType(Material.BEDROCK);
		o.getLocation().add(0.0D, 11.0D, -1.0D).getBlock().setType(Material.BEDROCK);
		o.getLocation().add(-1.0D, 11.0D, 0.0D).getBlock().setType(Material.BEDROCK);
		o.getLocation().add(0.0D, 10.0D, 0.0D).getBlock().setType(Material.BEDROCK);
		o.teleport(o.getLocation().add(0.0D, 11.0D, -0.05D));
		o.teleport(o.getLocation().add(0.0D, 0.0D, -0.00D));
		o.teleport(o.getLocation().add(0.0D, 0.0D, -0.00D));
		o.teleport(o.getLocation().add(0.0D, 0.0D, -0.00D));
		o.teleport(o.getLocation().add(0.0D, 0.0D, -0.00D));
		o.teleport(o.getLocation().add(0.0D, 0.0D, -0.00D));
		p.teleport(o);
		p.teleport(p.getLocation().add(0.0D, 4.0D, -0.01D));
		p.sendMessage("§aA arena foi criada!");
		p.sendMessage("§cApos de verificar o player, remova os blocos!");
	}

	public static void sendactionBar(Player player, String message) {
		CraftPlayer p = (CraftPlayer) player;
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}

	public static boolean isCommand(String label, String cmd) {
		return label.equalsIgnoreCase(cmd);
	}

	public static boolean isConsole(CommandSender sender) {
		return sender instanceof Player;
	}

	public static boolean hasPermission(CommandSender sender, String label) {
		return !sender.hasPermission("cmd." + label);
	}

	public static void SemPerm(CommandSender sender, String label) {
		sender.sendMessage("§c§lERRO§7,VocÃª nao Tem Permissao Para O Comando §c§l" + label);
	}

	public static ItemStack itemStack(Material material, String nome, List<String> lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setLore(lore);
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static void filltoInv(Material mat, Inventory inv, int cor, String nome) {
		ItemStack a1 = new ItemStack(mat, 1, (short) cor);
		ItemMeta am1 = a1.getItemMeta();
		am1.setDisplayName(nome);
		a1.setItemMeta(am1);
		inv.addItem(a1);
		ItemStack[] arrayOfItemStack;
		int descpyro = (arrayOfItemStack = inv.getContents()).length;

		for (int metapyro = 0; metapyro < descpyro; metapyro++) {
			ItemStack item = arrayOfItemStack[metapyro];
			if (item == null) {
				inv.setItem(inv.firstEmpty(), a1);
			}
		}
	}

	public static void filltoInv(Material mat, Inventory inv, String nome) {
		ItemStack a1 = new ItemStack(mat, 1, (short) new Random().nextInt(14));
		ItemMeta am1 = a1.getItemMeta();
		am1.setDisplayName(nome);
		a1.setItemMeta(am1);
		inv.addItem(a1);
		ItemStack[] arrayOfItemStack;
		int descpyro = (arrayOfItemStack = inv.getContents()).length;

		for (int metapyro = 0; metapyro < descpyro; metapyro++) {
			ItemStack item = arrayOfItemStack[metapyro];
			if (item == null) {
				inv.setItem(inv.firstEmpty(), a1);
			}
		}
	}

	public static String GetALlArgs(String[] args, Integer inicia) {
		StringBuilder sb = new StringBuilder();
		for (int i = inicia; i < args.length; i++) {
			sb.append(args[i]).append(" ");
		}

		String allArgs = sb.toString().trim();
		return ChatColor.translateAlternateColorCodes('&', allArgs);
	}

	public static void teleporta(Player p, int x, int y, int z) {
		p.teleport(new Location(p.getWorld(), x, y, z));
	}

	public static void DarSopas(Player p, int quantia) {
		for (int x = 0; x < 55; x++) {
			ItemStack a = new ItemStack(Material.MUSHROOM_SOUP);
			ItemMeta am = a.getItemMeta();
			am.setDisplayName("§fSopa");
			a.setItemMeta(am);
			p.getInventory().addItem(a);

		}
	}

	public static void DarPotion(Player p, int quantia) {
		for (int x = 0; x < 55; x++) {
			ItemStack a = new ItemStack(Material.POTION, 1, (short) 16421);
			ItemMeta am = a.getItemMeta();
			am.setDisplayName("§fPotion");
			a.setItemMeta(am);
			p.getInventory().addItem(a);

		}
	}

	public static void DarSopas(Player p) {
		for (int x = 0; x < 55; x++) {
			ItemStack a = new ItemStack(Material.MUSHROOM_SOUP);
			ItemMeta am = a.getItemMeta();
			am.setDisplayName("§fSopa");
			a.setItemMeta(am);
			p.getInventory().addItem(a);

		}
	}

	public static void KitSelector(Inventory inv, Material mat, String nome, int slot, int data, List<String> lore) {
		ItemStack a = new ItemStack(mat, 1, (short) data);
		ItemMeta am = a.getItemMeta();
		am.setLore(lore);
		am.setDisplayName(nome);
		a.setItemMeta(am);

		inv.setItem(slot, a);
	}

	public static void KitSelector2(Inventory inv, Material mat, String nome, int data, List<String> lore) {
		ItemStack a = new ItemStack(mat, 1, (short) data);
		ItemMeta am = a.getItemMeta();
		am.setLore(lore);
		am.setDisplayName(nome);
		a.setItemMeta(am);

		inv.addItem(a);
	}

	public static void Set(Player p, String nome, int slot, Material mat) {
		ItemStack a = new ItemStack(mat);
		ItemMeta am = a.getItemMeta();
		am.setDisplayName(nome);
		a.setItemMeta(am);

		p.getInventory().setItem(slot, a);
	}

	public static HashMap<String, Integer> ID = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static void SetItemKIT(Player p, String nome, int slot, Material mat) {
		ItemStack a = new ItemStack(mat);
		ItemMeta am = a.getItemMeta();
		am.setDisplayName(nome);
		a.setItemMeta(am);

		p.getInventory().setItem(slot, a);
		ID.put(p.getName(), mat.getId());
	}

	public static HashMap<String, BukkitRunnable> task = new HashMap<>();

	public static void ItemSpawn(Player p) {
		task.remove(p.getName());
		Clear(p);
		try {
			task.put(p.getName(), (BukkitRunnable) new BukkitRunnable() {
				int slot = 0;

				@Override
				public void run() {
					if (slot == 9) {
						cancel();
					}

					p.getInventory().setItem(slot, itemStack(Material.INK_SACK, "§aLoading...", 1, 10));

					if (slot == 8) {
						Clear(p);
						Set(p, "§a» §7Kit Selector §a«", 4, Material.ENDER_PORTAL_FRAME);
						Set(p, "§a» §7Kit Diario §a«", 2, Material.ENDER_CHEST);
						Set(p, "§a» §7Warps §a«", 6, Material.COMPASS);

					}

					slot++;
				}
			}.runTaskTimer(Main.main, 0, 2));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void Clear(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		if (p.getActivePotionEffects() != null) {
			for (PotionEffect e : p.getActivePotionEffects()) {
				p.removePotionEffect(e.getType());
			}
		}
	}

	public static ItemStack itemStack(Material material, String nome, String lore, Enchantment enchant,
			int levelEnchant) {
		ItemStack item = new ItemStack(material);
		item.addUnsafeEnchantment(enchant, levelEnchant);
		ItemMeta itemmeta = item.getItemMeta();
		List<String> iteml = new ArrayList<>();
		iteml.add(lore);
		itemmeta.setLore(iteml);
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemStack(Material material, String nome, int amount, int durability, List<String> lore) {
		ItemStack item = new ItemStack(material, amount, (short) durability);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setLore(lore);
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemstack(Material mat, int data, int quantia, String nome) {
		ItemStack a = new ItemStack(mat, quantia, (short) data);
		ItemMeta am = a.getItemMeta();
		am.setDisplayName(nome);
		a.setItemMeta(am);
		return a;
	}

	public static ItemStack itemstack(Material mat, int data, int quantia, String nome, Enchantment enchant,
			int level) {
		ItemStack a = new ItemStack(mat, quantia, (short) data);
		ItemMeta am = a.getItemMeta();
		am.addEnchant(enchant, level, true);
		am.setDisplayName(nome);
		a.setItemMeta(am);
		return a;
	}

	public static ItemStack itemStack(Material material, String nome) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack Head(Player target, String str) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(target.getName());
		meta.setDisplayName(str);
		skull.setItemMeta(meta);

		return skull;
	}

	public static ItemStack itemStack(Material material, String nome, int q) {
		ItemStack item = new ItemStack(material, q);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemStack(Material material, String nome, String lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemmeta = item.getItemMeta();
		List<String> iteml = new ArrayList<>();
		iteml.add(lore);
		itemmeta.setLore(iteml);
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static String TimePerfeito(Long i) {
		long minutes = i / 60;
		long seconds = i % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime;
		if (i >= 60) {
			formattedTime = disMinu + "m " + disSec + "s";
		} else {
			formattedTime = disSec + "s";
		}

		if (i % 60 == 0) {
			formattedTime = disMinu + "m";
		}
		return formattedTime;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public static boolean isValidUUID(String s) {
		try {
			UUID.fromString(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String formatterString(String string) {
		String[] split = string.split(" ");
		int atual = 0;
		String newString = "";
		for (String all : split) {
			atual++;
			newString += all + " ";
			if (atual >= 7) {
				newString += "\n";
				atual = 0;
			}
		}
		return newString;
	}

	public static void formatString(String string, List<String> formatar) {
		String[] split = string.split(" ");
		int atual = 0;
		String newString = "";
		for (String all : split) {
			atual++;
			newString += all + " ";
			if (atual >= 7) {
				formatar.add("§7" + newString);
				newString = "";
				atual = 0;
			}
		}
		formatar.add("§7" + newString);
	}

	public static UUID uuidVini = UUID.fromString("c484790a-7417-44b7-abb5-c0a553d74e64");
	public static UUID uuidDuda = UUID.fromString("917a9d5c-4dc3-4dbf-a467-f56bbea30c02");
	public static UUID uuidMatt = UUID.fromString("f42d9082-ae19-471c-9cc4-39bc686b0fed");

}
