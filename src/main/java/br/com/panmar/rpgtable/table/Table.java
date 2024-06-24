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
	
	public void InitializeTable(int[] size) {
		grid.InitializeGrid(size[0], size[1]);
		SendEventToAll();
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
		Player owner = GetPlayerById(action.owner);
		Player target = GetPlayerById(action.target);
		
		switch (action.name.trim()) {
			case "movement":
				ProcessMovementAction(action);
				break;
			default:
				System.out.println(action.name);
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
			if(actions.get(i).actionId.equals(actionId)){
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
		action.actionId = RandomStringGenerator.GenerateRandomString(10);
		actions.add(action);
		
		for(int i  = 0; i < actions.size(); i++) {
			System.out.println("Action: " + actions.get(i).name + " - Owner: " + actions.get(i).owner + " - Target: " + actions.get(i).target);
		}

		SendEventToMaster();
	}

	public void SendEventToMaster(){
		System.out.println("Master event");
		for (SseEmitter emitter : masterEventEmitter) {
			try {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(new TableEventData("Master Test", tableDescription, players, actions, grid.GetGridSummary()));
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
				String json = gson.toJson(new TableEventData("Player Test", tableDescription, players, actions, grid.GetGridSummary()));
				SseEventBuilder event = SseEmitter.event();
				event.data(json);
				emitter.send(event);
				System.out.println("player Sent");
			} catch (Exception ex) {
			 System.out.println("Erro ao enviar Player, error " + ex.getMessage());
			}
		 
		}
	}

	public void SetPlayerPosition(PlayerPosRequest playerPosRequest){
		Player desiredPlayer = GetPlayerById(playerPosRequest.playerId);
		int[] pos = grid.GetPlayerPos(desiredPlayer);

		if(grid.cols < playerPosRequest.yPos || grid.rows < playerPosRequest.xPos){
			return;
		}

		if (pos[0] == -1 && pos[1] == -1) {
			grid.SetPlayerAtPosition(desiredPlayer, playerPosRequest.xPos, playerPosRequest.yPos);
			
		}
		else{
			grid.CleanPosition(pos[0], pos[1]);
			grid.SetPlayerAtPosition(desiredPlayer, playerPosRequest.xPos, playerPosRequest.yPos);
		}

		SendEventToAll();
	}

	private void ProcessMovementAction(Action action){
		int xPos = action.intParams[0];
		int yPos = action.intParams[1];

		if(!grid.CanMoveTo(xPos, yPos)){
			return;
		}

		Player player = GetPlayerById(action.target);

		int[] playerPos = grid.GetPlayerPos(player);

		int moveToPos = grid.MoveToCost(playerPos[0], playerPos[1], xPos, yPos);

		if(player.currentMovement < moveToPos){
			return;
		}

		grid.CleanPosition(playerPos[0], playerPos[1]);

		player.currentMovement -= moveToPos;

		grid.SetPlayerAtPosition(player, xPos, yPos);

		RemoveAction(action);
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
