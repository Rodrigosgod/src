package me.Rodrigo.Oneb1;

import org.bukkit.inventory.*;
import java.util.*;

public class CustomManager {
	private ItemStack espada;
	private ItemStack capacete;
	private ItemStack peitoral;
	private ItemStack cal\u00e7a;
	private ItemStack bota;
	private ItemStack sopa;
	private ItemStack cogu;
	public Boolean refil;
	public Boolean recraft;
	public static HashMap<String, CustomManager> custom;

	static {
		CustomManager.custom = new HashMap<String, CustomManager>();
	}

	public CustomManager(final ItemStack espada, final ItemStack capacete, final ItemStack peitoral,final ItemStack cal\u00e7a, final ItemStack bota, final ItemStack sopa, final ItemStack cogu,final Boolean refil, final Boolean recraft) {
		this.refil = false;
		this.recraft = false;
		this.espada = espada;
		this.capacete = capacete;
		this.peitoral = peitoral;
		this.cal\u00e7a = cal\u00e7a;
		this.bota = bota;
		this.recraft = recraft;
		this.refil = refil;
		this.sopa = sopa;
		this.cogu = cogu;
	}

	public ItemStack getEspada() {
		return this.espada;
	}

	public ItemStack getSopa() {
		return this.sopa;
	}

	public ItemStack getCogu() {
		return this.cogu;
	}

	public ItemStack getCapacete() {
		return this.capacete;
	}

	public ItemStack getPeitoral() {
		return this.peitoral;
	}

	public ItemStack getCal\u00e7a() {
		return this.cal\u00e7a;
	}

	public ItemStack getBota() {
		return this.bota;
	}

	public Boolean hasRefil() {
		return this.refil;
	}

	public Boolean hasRecraft() {
		return this.recraft;
	}
}
