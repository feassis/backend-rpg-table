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
	private ArrayList<Player> players = new ArrayList<Player>();
	private Master master;
	private String tableId;
	private String tableDescription;
	public ArrayList<SseEmitter> masterEventEmitter = new ArrayList<SseEmitter>();
	public ArrayList<SseEmitter> playersEventEmitter = new ArrayList<SseEmitter>();
	public Grid grid = new Grid();
	
	
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
		masterEventEmitter.clear();
		this.master = master;
	}
	
	public void InitializeTable() {
		
	}

	private void SendEventToAll(){
		SendEventToMaster();
		SendEventToPlayers();
	}

	public void AproveAction(String actionId){
		Action desiredAction = GetActionById(actionId);

		if(desiredAction == null){
			return;
		}

		ProcessAction(desiredAction);

		RemoveAction(desiredAction);

		SendEventToAll();
	}

	public void RejectAction(String actionId){
		Action desiredAction = GetActionById(actionId);

		if(desiredAction == null){
			return;
		}

		RemoveAction(desiredAction);
		SendEventToAll();
	}

	private void RemoveAction(Action action){
		actions.remove(action);
	}
	
	private void ProcessAction(Action action){
		System.err.println(action.toString());
		Player owner = GetPlayerById(action.Owner);
		Player target = GetPlayerById(action.Target);
		
		switch (action.Name.trim()) {
			case "attack":
				DamagePlayer(target, owner.attackPower);
				break;
			case "heal":
				HealPlayer(target, 1);
				break;
			default:
				System.out.println(action.Name);
				throw new AssertionError();
		}
	}

	private void HealPlayer(Player player, int amount){
		player.currentHP = Math.clamp(player.currentHP + amount, 0, player.maxHP);
	}

	private void DamagePlayer(Player player, int dmg){
		player.currentHP = Math.clamp(player.currentHP - dmg, 0, player.maxHP);
		System.err.println(player.toString());
	}

	private Action GetActionById(String actionId){
		for(int i = 0; i < actions.size(); i++)
		{
			if(actions.get(i).ActionId.equals(actionId)){
				return actions.get(i);
			}
		}

		return null;
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
		Player registeredPlayer = GetPlayerById(player.playerId);
		
		if(registeredPlayer != null) {
			players.remove(registeredPlayer);
		}
		
		players.add(player);
	}
	
	public void RequestAction(Action requestedAction) {
		Action action = requestedAction;
		action.ActionId = RandomStringGenerator.GenerateRandomString(10);
		actions.add(action);
		
		for(int i  = 0; i < actions.size(); i++) {
			System.out.println("Action: " + actions.get(i).Name + " - Owner: " + actions.get(i).Owner + " - Target: " + actions.get(i).Target);
		}

		SendEventToMaster();
	}

	public void SendEventToMaster(){
		System.out.println("Master event");
		for (SseEmitter emitter : masterEventEmitter) {
			try {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(new TableEventData("Master Test", tableDescription, players, actions));
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
				String json = gson.toJson(new TableEventData("Player Test", tableDescription, players, actions));
				SseEventBuilder event = SseEmitter.event();
				event.data(json);
				emitter.send(event);
				System.out.println("player Sent");
			} catch (Exception ex) {
			 System.out.println("Erro ao enviar Player, error " + ex.getMessage());
			}
		 
		}
	}

	private Player GetPlayerById(String playerId) {
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).playerId.equals(playerId)) {
				return players.get(i);
			}
		}
		
		return null;
	}
}
