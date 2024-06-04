package br.com.panmar.rpgtable.table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import br.com.panmar.rpgtable.tools.RandomStringGenerator;
import java.io.IOException;

public class Table {
	private ArrayList<Creature> onTableCreatures = new ArrayList<Creature>();
	private ArrayList<ActivePlayer> players = new ArrayList<ActivePlayer>();
	private Master master;
	private String tableId;
	public ArrayList<SseEmitter> masterEventEmitter = new ArrayList<SseEmitter>();
	public ArrayList<SseEmitter> playersEventEmitter = new ArrayList<SseEmitter>();
	
	
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
		
	}
	
	public SseEmitter SubscribeToMasterEvent() {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		System.out.println("Emitter criado");
		masterEventEmitter.add(emitter);
		return emitter;
	}
	
	public SseEmitter SubscribeToPlayerEvent() {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		System.out.println("Emitter criado");
		playersEventEmitter.add(emitter);
		return emitter;
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
	}
	
	public void RequestAction(Action requestedAction) {
		actions.add(requestedAction);
		
		for(int i  = 0; i < actions.size(); i++) {
			System.out.println("Action: " + actions.get(i).Name + " - Owner: " + actions.get(i).Owner + " - Target: " + actions.get(i).Target);
		}
	}

	public void SendEventToMaster(){
		System.out.println("Master event");
		for (SseEmitter emitter : masterEventEmitter) {
			try {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(new TableEventData("test", "Master Test"));
				SseEventBuilder event = SseEmitter.event();
				event.data(json);
				emitter.send(event);
				System.out.println("master Sent");
			} catch (Exception ex) {
			 System.out.println("Erro ao enviar master, error " + ex.getMessage());
			}
		 
		}
	}
	
	public void SendEventToPlayers(){
		System.out.println("Player event");
		for (SseEmitter emitter : playersEventEmitter) {
			try {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(new TableEventData("test", "Player Test"));
				SseEventBuilder event = SseEmitter.event();
				event.data(json);
				emitter.send(event);
				System.out.println("player Sent");
			} catch (Exception ex) {
			 System.out.println("Erro ao enviar Player, error " + ex.getMessage());
			}
		 
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
