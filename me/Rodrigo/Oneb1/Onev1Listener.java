package me.Rodrigo.Oneb1;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import me.Rodrigo.Main;
import me.Rodrigo.Metodos;

public class Onev1Listener extends Onev1Manager implements Listener, CommandExecutor {
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (sender instanceof Player) {
			final Player p = (Player) sender;
			if (label.equalsIgnoreCase("1v1")) {
				if (args.length == 0) {
					if (Onev1Listener.lutando.containsKey(p.getName())) {
						return true;
					}
					final Onev1Config o = new Onev1Config("Spawn");
					if (o.existe()) {
						if (!Onev1Listener.na1v1.contains(p.getName())) {
							p.teleport(o.getLocation());
							Onev1Listener.na1v1.add(p.getName());
							Onev1Manager.item(p);
						} else {
							Onev1Listener.na1v1.remove(p.getName());
							p.teleport(p.getWorld().getSpawnLocation());
							Metodos.ItemSpawn(p);
					
						}
					}
					return true;
				} else if (args.length >= 1) {
					if (!p.hasPermission("cmd.1v1")) {
						return true;
					}
					if (args[0].equalsIgnoreCase("set")) {
						if (args.length == 1) {
							p.sendMessage(String.valueOf(Main.Prefix) + " Use /1v1 set §a[Spawn - Pos1 - Pos2]");
							return true;
						}
						if (args[1].equalsIgnoreCase("spawn")) {
							new Onev1Config("Spawn").setWarp(p.getLocation());
							p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea setou 1v1 §e" + args[1]);
						}
						if (args[1].equalsIgnoreCase("pos1")) {
							new Onev1Config("Pos1").setWarp(p.getLocation());
							p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea setou 1v1 §e" + args[1]);
						}
						if (args[1].equalsIgnoreCase("pos2")) {
							new Onev1Config("Pos2").setWarp(p.getLocation());
							p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea setou 1v1 §e" + args[1]);
						}
					}
				}
			}
		}
		return false;
	}

	@EventHandler
	public void interacr(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (Onev1Listener.na1v1.contains(p.getName()) && p.getItemInHand().getType() == Material.SLIME_BALL
				&& !Onev1Listener.fast1v1.contains(p.getName())) {
			Onev1Listener.fast1v1.add(p.getName());
			p.setItemInHand(Metodos.itemStack(Material.MAGMA_CREAM, "§aProucurando 1v1 Rapido!"));
			new BukkitRunnable() {
				public void run() {
					if (Onev1Listener.fast1v1.contains(p.getName())) {
						Onev1Listener.fast1v1.remove(p.getName());
						Onev1Manager.item(p);
					}
				}
			}.runTaskLater((Plugin) Main.main, 100L);
			if (Onev1Listener.fast1v1.size() == 2) {
				final Player p2 = Bukkit.getPlayer((String) Onev1Listener.fast1v1.get(0));
				final Player p3 = Bukkit.getPlayer((String) Onev1Listener.fast1v1.get(1));
				if (p2 == null) {
					Onev1Listener.fast1v1.remove(Onev1Listener.fast1v1.get(0));
				}
				if (p3 == null) {
					Onev1Listener.fast1v1.remove(Onev1Listener.fast1v1.get(1));
				}
				if (p2 != null && p3 != null) {
					this.iniciar1v1(p2, p3);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void quit(final PlayerQuitEvent e) {
		final Player r = e.getPlayer();
		if (!Onev1Listener.lutando.containsKey(r.getName())) {
			return;
		}
		final Player p = Bukkit.getPlayer((String) Onev1Listener.lutando.get(r.getName()));
		Onev1Listener.lutando.remove(p.getName());
		Onev1Listener.lutando.remove(r.getName());
		p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea venceu §e1v1 §fContra §e" + r.getName());
		Onev1Manager.aparecer(p);
		p.teleport(new Onev1Config("Spawn").getLocation());
		Onev1Manager.item(p);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void kick(final PlayerQuitEvent e) {
		final Player r = e.getPlayer();
		if (!Onev1Listener.lutando.containsKey(r.getName())) {
			return;
		}
		final Player p = Bukkit.getPlayer((String) Onev1Listener.lutando.get(r.getName()));
		Onev1Listener.lutando.remove(p.getName());
		Onev1Listener.lutando.remove(r.getName());
		p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea venceu §e1v1 §fContra §e" + r.getName());
		Onev1Manager.aparecer(p);
		p.teleport(new Onev1Config("Spawn").getLocation());
		Onev1Manager.item(p);
	}

	@EventHandler
	public void desafiar(final PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			final Player p = e.getPlayer();
			final Player r = (Player) e.getRightClicked();
			if (p.getItemInHand().getType() != Material.BLAZE_ROD) {
				return;
			}
			if (!Onev1Listener.na1v1.contains(p.getName())) {
				return;
			}
			if (Onev1Listener.cooldown.contains(p.getName())) {
				p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea esta em cooldown!");
				return;
			}
			if (!Onev1Listener.cooldown.contains(p.getName())) {
				Onev1Listener.cooldown.add(p.getName());
				new BukkitRunnable() {
					public void run() {
						Onev1Listener.cooldown.remove(p.getName());
						if (!Onev1Listener.lutando.containsKey(p.getName())) {
							p.sendMessage(
									String.valueOf(Main.Prefix) + " Seu cooldown acabou! pode desafiar novamente");
						}
					}
				}.runTaskLater((Plugin) Main.main, 100L);
			}
			if (Onev1Listener.convite.containsKey(r.getName())) {
				p.sendMessage(String.valueOf(Main.Prefix) + " Este player ja esta convidado!");
				return;
			}
			if (Onev1Listener.convite.containsKey(p.getName())
					&& Onev1Listener.convite.get(p.getName()).equals(r.getName())) {
				this.iniciar1v1(p, r);
				return;
			}
			Onev1Listener.convite.put(r.getName(), p.getName());
			new BukkitRunnable() {
				public void run() {
					Onev1Listener.convite.remove(r.getName());
				}
			}.runTaskLater((Plugin) Main.main, 100L);
			p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea desafiou §e" + r.getName());
			r.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea foi desafiado por §e" + p.getName());
		}
	}

	@EventHandler
	public void desafiarcustom(final PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			final Player p = e.getPlayer();
			final Player r = (Player) e.getRightClicked();
			if (p.getItemInHand().getType() != Material.IRON_FENCE) {
				return;
			}
			if (!Onev1Listener.na1v1.contains(p.getName())) {
				return;
			}
			if (Onev1Listener.cooldown.contains(p.getName())) {
				p.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea esta em cooldown!");
				return;
			}
			if (!Onev1Listener.cooldown.contains(p.getName())) {
				Onev1Listener.cooldown.add(p.getName());
				new BukkitRunnable() {
					public void run() {
						Onev1Listener.cooldown.remove(p.getName());
						if (!Onev1Listener.lutando.containsKey(p.getName())) {
							p.sendMessage(
									String.valueOf(Main.Prefix) + " Seu cooldown acabou! pode desafiar novamente");
						}
					}
				}.runTaskLater((Plugin) Main.main, 100L);
			}
			if (Onev1Listener.convite.containsKey(r.getName())) {
				p.sendMessage(String.valueOf(Main.Prefix) + " Este player ja esta convidado!");
				return;
			}
			if (Onev1Listener.convite.containsKey(p.getName())
					&& Onev1Listener.convite.get(p.getName()).equals(r.getName())) {
				InvCustom.menuAceitar(p, r);
				return;
			}
			InvCustom.Menu(p, r);
		}
	}

	@EventHandler
	public void entity(final EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL
				&& Onev1Listener.na1v1.contains(e.getEntity().getName())
				&& !Onev1Listener.lutando.containsKey(e.getEntity().getName())) {
			e.setDamage(0.0);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void dano(final EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			final Player bateu = (Player) e.getDamager();
			final Player apanho = (Player) e.getEntity();
			if (Onev1Listener.na1v1.contains(bateu.getName()) && !Onev1Listener.lutando.containsKey(bateu.getName())) {
				e.setCancelled(true);
			}
			if (Onev1Listener.na1v1.contains(apanho.getName())
					&& !Onev1Listener.lutando.containsKey(apanho.getName())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void playerdeath(final PlayerDeathEvent e) {
		final Player vitima = e.getEntity();
		if (!Onev1Listener.lutando.containsKey(vitima.getName())) {
			return;
		}
		final Player matador = Bukkit.getPlayer((String) Onev1Listener.lutando.get(vitima.getName()));
		Metodos.spawnRandomFirework(matador.getLocation());
		Onev1Listener.lutando.remove(matador.getName());
		Onev1Listener.lutando.remove(vitima.getName());
		Onev1Listener.convite.remove(vitima.getName());
		Onev1Listener.fast1v1.remove(vitima.getName());
		Onev1Listener.cooldown.remove(vitima.getName());
		Onev1Listener.na1v1.remove(vitima.getName());
		matador.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea venceu §e1v1 §fContra §e" + vitima.getName());
		vitima.sendMessage(String.valueOf(Main.Prefix) + " Voc\u00ea perdeu §e1v1 §fContra §e" + matador.getName());
		Onev1Manager.aparecer(matador);
		Onev1Manager.aparecer(vitima);
		matador.teleport(new Onev1Config("Spawn").getLocation());
		vitima.teleport(new Onev1Config("Spawn").getLocation());
		Onev1Manager.item(matador);
		Onev1Manager.item(vitima);
	}

	public void iniciar1v1(final Player p1, final Player p2) {
		this.item1v1(p1);
		this.item1v1(p2);
		Onev1Listener.lutando.put(p1.getName(), p2.getName());
		Onev1Listener.lutando.put(p2.getName(), p1.getName());
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

	public void item1v1(final Player p) {
		if (Onev1Listener.fast1v1.contains(p.getName())) {
			Onev1Listener.fast1v1.remove(p.getName());
		}
		p.getInventory().clear();
		for (final PotionEffect e : p.getActivePotionEffects()) {
			if (e != null) {
				p.removePotionEffect(e.getType());
			}
		}
		p.getInventory().setItem(0, Metodos.itemStack(Material.STONE_SWORD, "§c1v1"));
		for (int x = 0; x < 54; ++x) {
			p.getInventory().addItem(new ItemStack[] { Metodos.itemStack(Material.MUSHROOM_SOUP, "§aSopa") });
		}
	}
}
