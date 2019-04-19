package me.Rodrigo.Listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;
import me.Rodrigo.C.KitC;
import me.Rodrigo.Manager.KitManager;

public class Secundarios implements Listener {

	@EventHandler
	public void semVhcarwaraw(WeatherChangeEvent e) {

		e.setCancelled(true);

	}

	@EventHandler
	public void OnPlayerSoup(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getHealth() == 20) {
		} else {
			int soup = +7;
			if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					&& player.getItemInHand().getType() == Material.MUSHROOM_SOUP) {
				player.setHealth(player.getHealth() + soup > player.getMaxHealth() ? player.getMaxHealth()
						: player.getHealth() + soup);
				event.getPlayer().getItemInHand().setType(Material.BOWL);
			}

		}
	}
	

	@EventHandler
	public void drop(PlayerDropItemEvent e) {
		if (KitManager.getKit(e.getPlayer()).equalsIgnoreCase("none")) {
			e.setCancelled(true);
		} else {
			ItemStack item = e.getItemDrop().getItemStack();
			if(item.getType() == Material.WOOD_SWORD || item.getType() == Material.STONE_SWORD || item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD){
				e.setCancelled(true);
			}
			try {
				KitC k = KitC.valueOf(KitManager.getKit(e.getPlayer()));
				for(Material m : k.mat){
					if(item.getType() == m){
						e.setCancelled(true);
					}
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	@EventHandler
	public void semlixo(ItemSpawnEvent e) {
		Item i = e.getEntity();

		i.setVelocity(new Vector(0, 0.55, 0));

		new BukkitRunnable() {

			@Override
			public void run() {
				i.remove();

			}
		}.runTaskLater(Main.main, 60);
	}

	@EventHandler
	public void semfomenobrasil(FoodLevelChangeEvent e) {
		if (e.getFoodLevel() < 20) {
			e.setFoodLevel(20);
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void semQuebrar(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void semPor(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void semDano2(EntityDamageByBlockEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (KitManager.getKit(p).equalsIgnoreCase("none")) {
			e.setDamage(0);
			e.setCancelled(true);
		} else {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void clickMob(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (!e.getRightClicked().getName().isEmpty()) {
			if (e.getRightClicked().getName().contains("§7Sopas")) {
				e.setCancelled(true);
				Inventory inv = Bukkit.createInventory(p, 54, "SOPAS");

				for (int x = 0; x < 60; x++) {
					inv.addItem(Metodos.itemStack(Material.MUSHROOM_SOUP, "§aSopa"));
				}

				p.openInventory(inv);
			}
			if (e.getRightClicked().getName().contains("§7Pote")) {
				e.setCancelled(true);
				Inventory inv = Bukkit.createInventory(p, 54, "POTES");

				for (int x = 0; x < 60; x++) {
					inv.addItem(Metodos.itemStack(Material.POTION, "§cPote", 1, 16453, null));
				}

				p.openInventory(inv);
			}
			if (e.getRightClicked().getName().contains("§7Recraft")) {
				e.setCancelled(true);
				Inventory inv = Bukkit.createInventory(p, 9, "§eRecraft");

				inv.setItem(3, Metodos.itemStack(Material.RED_MUSHROOM, "§cRed", 64));
				inv.setItem(4, Metodos.itemStack(Material.BOWL, "§7Pote", 64));
				inv.setItem(5, Metodos.itemStack(Material.BROWN_MUSHROOM, "§6Marrom", 64));

				p.openInventory(inv);
			}

		}
	}

	@EventHandler
	public void dano(EntityDamageByEntityEvent e) {
		if (e.getEntity().getType() != EntityType.PLAYER) {
			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				if (p.getGameMode() == GameMode.SURVIVAL) {
					e.setCancelled(true);
				} else {
					e.setCancelled(false);
				}
			}
		}
	}

	public static ArrayList<String> inJump = new ArrayList<>();

	@EventHandler
	public void dano(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if (e.getDamage() > 2) {
					if (inJump.contains(p.getName())) {
						e.setDamage(0);
						e.setCancelled(true);
						inJump.remove(p.getName());
					}
				}
			}
		}
	}

	@EventHandler
	public void blr(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK) {
			p.setVelocity(new Vector(0, 2.3, 0));
			if (!inJump.contains(p.getName())) {
				inJump.add(p.getName());
			}
			return;
		}
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK) {
			p.setVelocity(new Vector(0, 3.0, 0));
			if (!inJump.contains(p.getName())) {
				inJump.add(p.getName());
			}
			return;
		}
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.PUMPKIN) {
			p.setVelocity(p.getEyeLocation().getDirection().multiply(2).add(new Vector(0, 1.5, 0)));
			if (!inJump.contains(p.getName())) {
				inJump.add(p.getName());
			}
			return;
		}
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
			if (inJump.contains(p.getName())) {
				new BukkitRunnable() {

					@Override
					public void run() {
						if (inJump.contains(p.getName())) {
							inJump.remove(p.getName());
						}

					}
				}.runTaskLater(Main.main, 20);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void NerfSwords(final EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			final Player player = (Player) event.getDamager();
			if (event.getDamage() > 1.0) {
				event.setDamage(event.getDamage() - 1.0);
			}
			if (event.getDamager() instanceof Player) {
				if (player.getFallDistance() > 0.0f && !player.isOnGround()
						&& !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
					final int NewDamage = (int) (event.getDamage() * 1.5) - (int) event.getDamage();
					if (event.getDamage() > 1.0) {
						event.setDamage(event.getDamage() - NewDamage);
					}
				}
				if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
					for (final PotionEffect Effect : player.getActivePotionEffects()) {
						if (Effect.getType().equals((Object) PotionEffectType.INCREASE_DAMAGE)) {
							final double Division = (Effect.getAmplifier() + 2) * 1.3 + 1.0;
							int NewDamage2;
							if (event.getDamage() / Division <= 1.0) {
								NewDamage2 = (Effect.getAmplifier() + 2) * 3 + 3;
							} else {
								NewDamage2 = (int) (event.getDamage() / Division);
							}
							event.setDamage((double) NewDamage2);
							break;
						}
					}
				}
				if (player.getItemInHand().getType() == Material.AIR) {
					event.setDamage(0.5);
				}
				if (player.getItemInHand().getType() == Material.WOOD_SWORD) {
					event.setDamage(1.5);
				}
				if (player.getItemInHand().getType() == Material.STONE_SWORD) {
					event.setDamage(2.5);
				}
				if (player.getItemInHand().getType() == Material.GOLD_SWORD) {
					event.setDamage(3.5);
				}
				if (player.getItemInHand().getType() == Material.IRON_SWORD) {
					event.setDamage(3.5);
				}
				if (player.getItemInHand().getType() == Material.DIAMOND_SWORD) {
					event.setDamage(4.5);
				}
				if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
					for (final PotionEffect Effect : player.getActivePotionEffects()) {
						if (Effect.getType().equals((Object) PotionEffectType.INCREASE_DAMAGE)
								&& player.getItemInHand() != null
								&& player.getItemInHand().getType().name().contains("SWORD")) {
							final Random r = new Random();
							if (r.nextInt(3) == 0) {
								event.setDamage(event.getDamage() + 2.0);
								break;
							}
							event.setDamage(event.getDamage() + 1.5);
						}
					}
				}
				if (player.getFallDistance() > 0.0f && !player.isOnGround()
						&& !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
					if (player.getItemInHand().getType() == Material.AIR) {
						event.setDamage(0.5);
					}
					if (player.getItemInHand().getType() == Material.WOOD_SWORD) {
						event.setDamage(event.getDamage() + 1.0);
					}
					if (player.getItemInHand().getType() == Material.STONE_SWORD) {
						event.setDamage(event.getDamage() + 1.0);
					}
					if (player.getItemInHand().getType() == Material.GOLD_SWORD) {
						event.setDamage(event.getDamage() + 1.5);
					}
					if (player.getItemInHand().getType() == Material.IRON_SWORD) {
						event.setDamage(event.getDamage() + 1.0);
					}
					if (player.getItemInHand().getType() == Material.DIAMOND_SWORD) {
						event.setDamage(event.getDamage() + 1.0);
					}
				}
			}
		}
	}

	@EventHandler
	public void da(final EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getEntity();
		ItemStack[] armorContents;
		for (int length = (armorContents = p.getInventory().getArmorContents()).length, j = 0; j < length; ++j) {
			final ItemStack i = armorContents[j];
			i.setData(i.getData());
			i.setDurability((short) 0);
			p.updateInventory();
		}
		if (e.getDamager() instanceof Player) {
			if (p.getItemInHand().getType() != Material.POTION) {
				p.getItemInHand().setData(p.getItemInHand().getData());
				p.getItemInHand().setDurability((short) 0);
				p.updateInventory();
			}
		}
	}

	@EventHandler
	public void bussola(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.COMPASS) {
			if (KitManager.getKit(p) != "None") {
				Boolean foi = false;
				Player r = null;
				for (Player all : Metodos.allPlayer()) {
					if (all != p) {
						if(p.getLocation().distance(all.getLocation()) < 70){
							if (!foi) {
								foi = true;
								r = all;
							}
						}
					}
				}
				if(r == null){
					p.sendMessage("§c§lCOMPASS§f, nenhum player encontrado!");
				}else{
					p.sendMessage("§c§lCOMPASS§f, apontando para: §e"+r.getName());
					p.setCompassTarget(r.getLocation());
				}
			}
		}
	}

}
