package br.com.panmar.rpgtable.table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import br.com.panmar.rpgtable.tools.RandomStringGenerator;
import java.io.IOException;

public class Table {
	private ArrayList<Creature> onTableCreatures = new ArrayList<Creature>();
	private ArrayList<ActivePlayer> players = new ArrayList<ActivePlayer>();
	private Master master;
	private String tableId;
	public SseEmitter masterEventEmitter = new SseEmitter(Long.MAX_VALUE);
	public SseEmitter playersEventEmitter = new SseEmitter(Long.MAX_VALUE);
	
	
	private ArrayList<Action> actions = new ArrayList<Action>();
	
	public String GetMasterId() {
		return master.id;
	}
	
	public String GetTableId() {
		return tableId;
	}
	
	public Table(Master master) {
		this.master = master;
		
		this.tableId = RandomStringGenerator.GenerateRandomString(20);
	}
	
	public void SetMaster(Master master) {
		this.master = master;
	}
	
	public void InitializeTable() {
		masterEventEmitter = new SseEmitter(Long.MAX_VALUE);
		
		try {
			masterEventEmitter.send(SseEmitter.event().name("INIT"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		playersEventEmitter = new SseEmitter(Long.MAX_VALUE);
		
		try {
			playersEventEmitter.send(SseEmitter.event().name("INIT"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public SseEmitter SubscribeToMasterEvent() {
		return masterEventEmitter;
	}
	
	public SseEmitter SubscribeToPlayerEvent() {
		return playersEventEmitter;
	}
	
	private void SortCreaturesOnTable() {
		Collections.sort(onTableCreatures, Comparator.comparingInt(Creature::GetInitiative).reversed());
	}
	
	public void AddPlayer(Player player) {
		ActivePlayer registeredPlayer = GetPlayerById(player.playerId);
		
		if(registeredPlayer != null) {
			players.remove(registeredPlayer);
		}
		
		players.add(new ActivePlayer(player));
		
		//System.out.println("Players: " + players.size());
	}
	
	public void RequestAction(Action requestedAction) {
		actions.add(requestedAction);
		
		for(int i  = 0; i < actions.size(); i++) {
			System.out.println("Action: " + actions.get(i).Name + " - Owner: " + actions.get(i).Owner + " - Target: " + actions.get(i).Target);
		}
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
