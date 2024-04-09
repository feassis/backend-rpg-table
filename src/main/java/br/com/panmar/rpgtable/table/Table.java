package br.com.panmar.rpgtable.table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.panmar.rpgtable.tools.RandomStringGenerator;

public class Table {
	private ArrayList<Creature> onTableCreatures = new ArrayList<Creature>();
	private ArrayList<ActivePlayer> players = new ArrayList<ActivePlayer>();
	private Master master;
	private String masterAddress;
	private String tableId;
	
	public String GetMasterId() {
		return master.id;
	}
	
	public String GetTableId() {
		return tableId;
	}
	
	public Table(Master master, String masterAddress) {
		this.master = master;
		
		this.tableId = RandomStringGenerator.GenerateRandomString(20);
	}
	
	private void SortCreaturesOnTable() {
		Collections.sort(onTableCreatures, Comparator.comparingInt(Creature::GetInitiative).reversed());
	}
	
	public void AddPlayer(Player player, String playerURL) {
		ActivePlayer registeredPlayer = GetPlayerById(player.playerId);
		
		if(registeredPlayer != null) {
			players.remove(registeredPlayer);
		}
		
		players.add(new ActivePlayer(player, playerURL));
		
		//System.out.println("Players: " + players.size());
	}
	
	private ActivePlayer GetPlayerById(String playerId) {
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).Player.playerId == playerId) {
				return players.get(i);
			}
		}
		
		return null;
	}
}
