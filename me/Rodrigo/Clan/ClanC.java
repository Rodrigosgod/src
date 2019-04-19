package me.Rodrigo.Clan;

import java.util.List;

public class ClanC {

	public String nome;
	public String tag;
	public String dono;
	public List<String> integrantes;
	public int kills;
	public int deaths;
	public int money;
	public int elo;

	public ClanC(String nome, String tag, String dono, List<String> integrantes, int kills, int deaths, int money,int elo) {
		this.nome = nome;
		this.tag = tag;
		this.dono = dono;
		this.integrantes = integrantes;
		this.kills = kills;
		this.deaths = deaths;
		this.money = money;
		this.elo = elo;
	}

}
