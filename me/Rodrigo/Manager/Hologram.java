package me.Rodrigo.Manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.Rodrigo.Main;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

public class Hologram {
	private List<EntityArmorStand> entityList = new ArrayList<>();
    private String[] text;
    private Location loc;
    private double distance = 0.25D;
    int count;

    public Hologram(String[] text, Location loc) {
        this.text = text;
        this.loc = loc;
        create();
    }

    public void showPlayerTemp(final Player player, int time) {
        showPlayer(player);
        Bukkit.getScheduler().runTaskLater(Main.main, new Runnable() {
            @Override
            public void run() {
                hidePlayer(player);
            }
        }, time);
    }

    public void showAllTemp(final Player player, int time) {
        showAll();
        Bukkit.getScheduler().runTaskLater(Main.main, new Runnable() {
            @Override
            public void run() {
                hideAll();
            }
        }, time);
    }

    public void move(Vector vector) {
        for (@SuppressWarnings("unused") EntityArmorStand entity : entityList) {
    
        }
    }

    public String[] getString() {
        return text;
    }

    public void showPlayer(Player player) {
        for (EntityArmorStand armour : entityList) {
            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armour);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void hidePlayer(Player player) {
        for (EntityArmorStand armour : entityList) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armour.getId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void showAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (EntityArmorStand armour : entityList) {
                PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armour);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public void hideAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (EntityArmorStand armour : entityList) {
                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armour.getId());
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    private void create() {
        for (String text : this.text) {
            EntityArmorStand entity = new EntityArmorStand(((CraftWorld) this.loc.getWorld()).getHandle(), this.loc.getX(), this.loc.getY(), this.loc.getZ());

            entity.setCustomName(text);
            entity.setCustomNameVisible(true);
            entity.setInvisible(true);
            entity.setGravity(false);
            entityList.add(entity);
            this.loc.subtract(0, this.distance, 0);
            count++;
        }

        for (int i = 0; i < count; i++) {
            this.loc.add(0, this.distance, 0);
        }
        this.count = 0;
    }
}