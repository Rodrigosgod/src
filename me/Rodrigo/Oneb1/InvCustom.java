package me.Rodrigo.Oneb1;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;

public class InvCustom extends Onev1Listener implements Listener
{
    public static void Menu(final Player p, final Player r) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)p, 9, "§cDesafiar §f" + r.getName());
        inv.setItem(0, Metodos.itemStack(Material.WOOD_SWORD, "§eEspada de Madeira"));
        inv.setItem(1, Metodos.itemStack(Material.GLASS, "§eSem capacete"));
        inv.setItem(2, Metodos.itemStack(Material.GLASS, "§eSem peitoral"));
        inv.setItem(3, Metodos.itemStack(Material.GLASS, "§eSem cal\u00e7a"));
        inv.setItem(4, Metodos.itemStack(Material.GLASS, "§eSem bota"));
        inv.setItem(5, Metodos.itemStack(Material.MUSHROOM_SOUP, "§eRefil: §eoff"));
        inv.setItem(6, Metodos.itemStack(Material.RED_MUSHROOM, "§eRecraft: §eoff"));
        inv.setItem(7, Metodos.itemstack(Material.WOOL, 14, 1, "§cCancelar"));
        inv.setItem(8, Metodos.itemstack(Material.WOOL, 5, 1, "§aDesafiar"));
        p.openInventory(inv);
    }
    
    @EventHandler
    public void click(final InventoryClickEvent e) {
        try {
            final Player p = (Player)e.getWhoClicked();
            if (e.getInventory().getName().contains("§cDesafiar §f")) {
                final Player p2 = Bukkit.getPlayer(e.getInventory().getName().replace("§cDesafiar §f", ""));
                e.setCancelled(true);
                if (e.getCurrentItem().getType() == Material.WOOD_SWORD) {
                    e.getInventory().setItem(0, Metodos.itemStack(Material.STONE_SWORD, "§eEspada de Pedra"));
                }
                else if (e.getCurrentItem().getType() == Material.STONE_SWORD) {
                    e.getInventory().setItem(0, Metodos.itemStack(Material.IRON_SWORD, "§eEspada de Ferro"));
                }
                else if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
                    e.getInventory().setItem(0, Metodos.itemStack(Material.DIAMOND_SWORD, "§eEspada de Diamante"));
                }
                else if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                    e.getInventory().setItem(0, Metodos.itemStack(Material.WOOD_SWORD, "§eEspada de Madeira"));
                }
                final Inventory inv = e.getInventory();
                final String name = e.getCurrentItem().getItemMeta().getDisplayName();
                if (name.contains("capacete") || name.contains("Capacete")) {
                    final int slot = e.getSlot();
                    if (e.getCurrentItem().getType() == Material.GLASS) {
                        inv.setItem(slot, Metodos.itemStack(Material.LEATHER_HELMET, "§eCapacete de couro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.LEATHER_HELMET) {
                        inv.setItem(slot, Metodos.itemStack(Material.GOLD_HELMET, "§eCapacete de ouro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.GOLD_HELMET) {
                        inv.setItem(slot, Metodos.itemStack(Material.IRON_HELMET, "§eCapacete de ferro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.IRON_HELMET) {
                        inv.setItem(slot, Metodos.itemStack(Material.DIAMOND_HELMET, "§eCapacete de diamante"));
                    }
                    else if (e.getCurrentItem().getType() == Material.DIAMOND_HELMET) {
                        inv.setItem(slot, Metodos.itemStack(Material.GLASS, "§eSem capacete"));
                    }
                }
                if (name.contains("peitoral") || name.contains("Peitoral")) {
                    final int slot = e.getSlot();
                    if (e.getCurrentItem().getType() == Material.GLASS) {
                        inv.setItem(slot, Metodos.itemStack(Material.LEATHER_CHESTPLATE, "§ePeitoral de couro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE) {
                        inv.setItem(slot, Metodos.itemStack(Material.GOLD_CHESTPLATE, "§ePeitoral de ouro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.GOLD_CHESTPLATE) {
                        inv.setItem(slot, Metodos.itemStack(Material.IRON_CHESTPLATE, "§ePeitoral de ferro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.IRON_CHESTPLATE) {
                        inv.setItem(slot, Metodos.itemStack(Material.DIAMOND_CHESTPLATE, "§ePeitoral de diamante"));
                    }
                    else if (e.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE) {
                        inv.setItem(slot, Metodos.itemStack(Material.GLASS, "§eSem Peitoral"));
                    }
                }
                if (name.contains("cal\u00e7a") || name.contains("Cal\u00e7a")) {
                    final int slot = e.getSlot();
                    if (e.getCurrentItem().getType() == Material.GLASS) {
                        inv.setItem(slot, Metodos.itemStack(Material.LEATHER_LEGGINGS, "§eCal\u00e7a de couro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS) {
                        inv.setItem(slot, Metodos.itemStack(Material.GOLD_LEGGINGS, "§eCal\u00e7a de ouro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.GOLD_LEGGINGS) {
                        inv.setItem(slot, Metodos.itemStack(Material.IRON_LEGGINGS, "§eCal\u00e7a de ferro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.IRON_LEGGINGS) {
                        inv.setItem(slot, Metodos.itemStack(Material.DIAMOND_LEGGINGS, "§eCal\u00e7a de diamante"));
                    }
                    else if (e.getCurrentItem().getType() == Material.DIAMOND_LEGGINGS) {
                        inv.setItem(slot, Metodos.itemStack(Material.GLASS, "§eSem Cal\u00e7a"));
                    }
                }
                if (name.contains("bota") || name.contains("Bota")) {
                    final int slot = e.getSlot();
                    if (e.getCurrentItem().getType() == Material.GLASS) {
                        inv.setItem(slot, Metodos.itemStack(Material.LEATHER_BOOTS, "§eBota de couro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                        inv.setItem(slot, Metodos.itemStack(Material.GOLD_BOOTS, "§eBota de ouro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.GOLD_BOOTS) {
                        inv.setItem(slot, Metodos.itemStack(Material.IRON_BOOTS, "§eBota de ferro"));
                    }
                    else if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
                        inv.setItem(slot, Metodos.itemStack(Material.DIAMOND_BOOTS, "§eBota de diamante"));
                    }
                    else if (e.getCurrentItem().getType() == Material.DIAMOND_BOOTS) {
                        inv.setItem(slot, Metodos.itemStack(Material.GLASS, "§eSem Bota"));
                    }
                }
                if (name.contains("off")) {
                    final int slot = e.getSlot();
                    final String nome = e.getCurrentItem().getItemMeta().getDisplayName().replace("off", "on");
                    inv.setItem(slot, Metodos.itemStack(e.getCurrentItem().getType(), nome));
                }
                if (name.contains("on")) {
                    final int slot = e.getSlot();
                    final String nome = e.getCurrentItem().getItemMeta().getDisplayName().replace("on", "off");
                    inv.setItem(slot, Metodos.itemStack(e.getCurrentItem().getType(), nome));
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§ccancelar")) {
                    p.closeInventory();
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§adesafiar")) {
                    final ItemStack espada = e.getInventory().getItem(0);
                    final ItemStack capacete = e.getInventory().getItem(1);
                    final ItemStack peitoral = e.getInventory().getItem(2);
                    final ItemStack cal\u00e7a = e.getInventory().getItem(3);
                    final ItemStack bota = e.getInventory().getItem(4);
                    final ItemStack sopa = e.getInventory().getItem(5);
                    final ItemStack cogu = e.getInventory().getItem(6);
                    Boolean refil = false;
                    Boolean recraft = false;
                    if (e.getInventory().getItem(5).getItemMeta().getDisplayName().contains("on")) {
                        refil = true;
                    }
                    if (e.getInventory().getItem(6).getItemMeta().getDisplayName().contains("on")) {
                        recraft = true;
                    }
                    final CustomManager c = new CustomManager(espada, capacete, peitoral, cal\u00e7a, bota, sopa, cogu, refil, recraft);
                    CustomManager.custom.put(p.getName(), c);
                    InvCustom.convite.put(p2.getName(), p.getName());
                    p.closeInventory();
                    new BukkitRunnable() {
                        public void run() {
                            InvCustom.convite.remove(p2.getName());
                        }
                    }.runTaskLater((Plugin)Main.main, 100L);
                    p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea desafiou §e" + p2.getName());
                    p2.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea foi desafiado por §e" + p.getName());
                }
            }
            if (e.getInventory().getName().contains("Aceitar §e")) {
                e.setCancelled(true);
                final Player p2 = Bukkit.getPlayer(e.getInventory().getName().replace("Aceitar §e", ""));
                if (p2 != null && e.getCurrentItem().getItemMeta().getDisplayName().contains("Aceitar")) {
                    final ItemStack espada2 = e.getInventory().getItem(0);
                    final ItemStack capacete2 = e.getInventory().getItem(1);
                    final ItemStack peitoral2 = e.getInventory().getItem(2);
                    final ItemStack cal\u00e7a2 = e.getInventory().getItem(3);
                    final ItemStack bota2 = e.getInventory().getItem(4);
                    final ItemStack sopa2 = e.getInventory().getItem(5);
                    final ItemStack cogu2 = e.getInventory().getItem(6);
                    Boolean refil2 = false;
                    Boolean recraft2 = false;
                    if (e.getInventory().getItem(5).getItemMeta().getDisplayName().contains("on")) {
                        refil2 = true;
                    }
                    if (e.getInventory().getItem(6).getItemMeta().getDisplayName().contains("on")) {
                        recraft2 = true;
                    }
                    final CustomManager c2 = new CustomManager(espada2, capacete2, peitoral2, cal\u00e7a2, bota2, sopa2, cogu2, refil2, recraft2);
                    CustomManager.custom.put(p.getName(), c2);
                    p.closeInventory();
                    this.iniciar1v1Custom(p, p2);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void menuAceitar(final Player p, final Player r) {
        try {
            final Inventory inv = Bukkit.createInventory((InventoryHolder)p, 9, "Aceitar §e" + r.getName());
            final CustomManager c = CustomManager.custom.get(r.getName());
            inv.setItem(0, c.getEspada());
            inv.setItem(1, c.getCapacete());
            inv.setItem(2, c.getPeitoral());
            inv.setItem(3, c.getCal\u00e7a());
            inv.setItem(4, c.getBota());
            inv.setItem(5, c.getSopa());
            inv.setItem(6, c.getCogu());
            inv.setItem(7, Metodos.itemstack(Material.WOOL, 14, 1, "§cCancelar"));
            inv.setItem(8, Metodos.itemstack(Material.WOOL, 5, 1, "§aAceitar"));
            p.openInventory(inv);
        }
        catch (Exception ex) {}
    }
    
    public void iniciar1v1Custom(final Player p1, final Player p2) {
        this.item1v1custom(p1);
        this.item1v1custom(p2);
        final CustomManager c = CustomManager.custom.get(p1.getName());
        p1.getInventory().setHelmet(c.getCapacete());
        p2.getInventory().setHelmet(c.getCapacete());
        p1.getInventory().setChestplate(c.getPeitoral());
        p2.getInventory().setChestplate(c.getPeitoral());
        p1.getInventory().setLeggings(c.getCal\u00e7a());
        p2.getInventory().setLeggings(c.getCal\u00e7a());
        p1.getInventory().setBoots(c.getBota());
        p2.getInventory().setBoots(c.getBota());
        if (c.hasRecraft()) {
            p1.getInventory().setItem(13, Metodos.itemStack(Material.RED_MUSHROOM, "§cCogumelo", 64));
            p1.getInventory().setItem(14, Metodos.itemStack(Material.BOWL, "§7Pote", 64));
            p1.getInventory().setItem(15, Metodos.itemStack(Material.BROWN_MUSHROOM, "§6Cogumelo", 64));
            p2.getInventory().setItem(13, Metodos.itemStack(Material.RED_MUSHROOM, "§cCogumelo", 64));
            p2.getInventory().setItem(14, Metodos.itemStack(Material.BOWL, "§7Pote", 64));
            p2.getInventory().setItem(15, Metodos.itemStack(Material.BROWN_MUSHROOM, "§6Cogumelo", 64));
        }
        p1.getInventory().setItem(0, c.getEspada());
        p2.getInventory().setItem(0, c.getEspada());
        if (c.hasRefil()) {
            for (int x = 0; x < 8; ++x) {
                p1.getInventory().addItem(new ItemStack[] { Metodos.itemStack(Material.MUSHROOM_SOUP, "§aSopa") });
                p2.getInventory().addItem(new ItemStack[] { Metodos.itemStack(Material.MUSHROOM_SOUP, "§aSopa") });
            }
        }
        else {
            for (int x = 0; x < 8; ++x) {
                p1.getInventory().addItem(new ItemStack[] { Metodos.itemStack(Material.MUSHROOM_SOUP, "§aSopa") });
                p2.getInventory().addItem(new ItemStack[] { Metodos.itemStack(Material.MUSHROOM_SOUP, "§aSopa") });
            }
        }
        InvCustom.lutando.put(p1.getName(), p2.getName());
        InvCustom.lutando.put(p2.getName(), p1.getName());
        p1.teleport(new Onev1Config("Pos1").getLocation());
        p1.teleport(new Onev1Config("Pos2").getLocation());
        Onev1Manager.sumir(p1, p2);
        p1.sendMessage("");
        p1.sendMessage("");
        p1.sendMessage("");
        p1.sendMessage(String.valueOf(Main.Prefix) + " Seu 1v1 \u00e9 contra: §e" + p2.getName());
        p1.sendMessage("");
        p2.sendMessage("");
        p2.sendMessage("");
        p2.sendMessage("");
        p2.sendMessage(String.valueOf(Main.Prefix) + " Seu 1v1 \u00e9 contra: §e" + p1.getName());
        p2.sendMessage("");
    }
    
    public void item1v1custom(final Player p) {
        if (InvCustom.fast1v1.contains(p.getName())) {
            InvCustom.fast1v1.remove(p.getName());
        }
        p.getInventory().clear();
        for (final PotionEffect e : p.getActivePotionEffects()) {
            if (e != null) {
                p.removePotionEffect(e.getType());
            }
        }
    }
}
