package me.Rodrigo.C;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public enum KitC {
	
	PvP(Arrays.asList("§a» §7venha com uma espada melhor",""),false,Material.STONE_SWORD),
	Kangaroo(Arrays.asList("§a» §7de double jump com seu firework",""), true, Material.FIREWORK),
	Ninja(Arrays.asList("§a» §7aperte shift e teleporte-se ao ultimo hitado",""), false, Material.COAL_BLOCK),
	Ajnin(Arrays.asList("§a» §7aperte shift e teleporte o ultimo player hitado",""), false, Material.EMERALD),
	Camel(Arrays.asList( "§a» §7na areia você vira um camelo", ""), false, Material.SAND),
	NetherMan(Arrays.asList("§a» §7na nether vire o rei diabo", ""), false, Material.NETHERRACK),
	Combo(Arrays.asList("§a» §7ao acertar 5 hits seguidos um combo será aplicado",""),false,Material.DIAMOND_SWORD),
	Fisherman(Arrays.asList( "§a» §7puxe o player pescando ele!", ""), true, Material.FISHING_ROD),
	Namrehsif(Arrays.asList( "§a» §7vá até o player pescando ele!", ""), true, Material.FISHING_ROD),
	Jumper(Arrays.asList( "§a» §7jogue seu inimigo para cima pescando ele", ""), true, Material.FISHING_ROD),
	Stomper(Arrays.asList( "§a» §7esmague seu inimigo caindo nele!", ""), true, Material.DIAMOND_BOOTS),
	AntStomper(Arrays.asList( "§a» §7anule o kit stomper!", ""), false, Material.IRON_BOOTS),
	Boxer(Arrays.asList( "§a» §7de mais dano e tome menos dano!", ""), false, Material.DIAMOND_CHESTPLATE),
	Viking(Arrays.asList( "§a» §7com seu machado de mais dano!", ""), true, Material.STONE_AXE),
	Viper(Arrays.asList( "§a» §775% de chance de dar veneno", ""), false, Material.FERMENTED_SPIDER_EYE),
	Snail(Arrays.asList( "§a» §775% de chance de dar lentidão!", ""), false, Material.SOUL_SAND),
	Magma(Arrays.asList( "§a» §775% de chance de dar fogo!", ""), false, Material.MAGMA_CREAM),
	Terrorista(Arrays.asList( "§a» §7faça uma explosao ao bater a bunda no chao!", ""), true, Material.MAGMA_CREAM),
	Sonic(Arrays.asList( "§a» §7leve um boost para frente e deixe seus inimigos envenenados!", ""), true, Material.LAPIS_BLOCK),
	Deshfire(Arrays.asList( "§a» §7leve um boost para frente e deixe seus inimigos queimando!", ""), true, Material.LAPIS_BLOCK),
	Forcefield(Arrays.asList( "§a» §7ligue o hack forcefield", ""), true, Material.IRON_FENCE),
	BlackSonic(Arrays.asList( "§a» §7leve um boost para frente e deixe seus inimigos envenenados!", ""), true, Material.COAL_BLOCK),
	;

	public Material[] mat;
	public Boolean item;
	public List<String> lore;

	private KitC(List<String> lore, Boolean item, Material... mat) {
		this.mat = mat;
		this.item = item;
		this.lore = lore;
	}

}
