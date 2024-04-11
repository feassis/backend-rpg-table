package br.com.panmar.rpgtable.service;

import org.springframework.stereotype.Service;

import br.com.panmar.rpgtable.table.Action;
import br.com.panmar.rpgtable.table.Master;
import br.com.panmar.rpgtable.table.Player;
import br.com.panmar.rpgtable.table.Table;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import java.util.ArrayList;


@Service
public class TableService {

	private ArrayList<Table> availableTables = new ArrayList<Table>();
	private SseEmitter masterEventEmitter = new SseEmitter();
	private SseEmitter playersEventEmitter = new SseEmitter();
	
	public String CreateTable(Master master) {
		
		Table currentActiveTable = GetTableByMasterId(master.id);
		
		if(currentActiveTable != null) {
			this.availableTables.remove(currentActiveTable);
		}
		
		Table newTable = new Table(master);
		this.availableTables.add(newTable);
		
		System.out.println("Table Created for master: " + master.id + " table id: " + newTable.GetTableId());
		return  newTable.GetTableId();
	}
	
	public SseEmitter JoinTable(String tableId, Player player) {
		Table table = GetTableById(tableId);
		
		System.out.println("Tables: " + availableTables.get(0).GetTableId().toString());
		
		if(table == null) {
			return null;
		}
		
		try {
			SseEventBuilder event =  SseEmitter.event().data("Added player: " + player.playerId).id("Master Report").name("Master Reporter");
			masterEventEmitter.send(event);
		} catch (Exception ex) {
			masterEventEmitter.completeWithError(ex);
		}
		
		
		
		table.AddPlayer(player);
		return playersEventEmitter;
	}
	
	public void RequestAction(String tableId, Action action) {
		Table table = GetTableById(tableId);
		
		table.RequestAction(action);
	}
	
	private Table GetTableByMasterId(String masterId) {
		for(int i = 0; i < this.availableTables.size(); i++) {
			if(this.availableTables.get(i).GetMasterId().equals(masterId)) {
				return this.availableTables.get(i);
			}
		}
		
		return null;
	}
	
	private Table GetTableById(String tableId) {
		for(int i = 0; i < this.availableTables.size(); i++) {
			System.out.println("Table: " + availableTables.get(i).GetTableId() + "evaluate value: " + tableId + " - Check value: " + availableTables.get(i).GetTableId() == tableId);
			if(this.availableTables.get(i).GetTableId().equals(tableId)) {
				return this.availableTables.get(i);
			}
		}
		
		return null;
	}

}
