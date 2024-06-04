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
	
	
	public String CreateTable(Master master) {
		Table newTable = new Table(master);
		this.availableTables.add(newTable);

		newTable.InitializeTable();
		
		System.out.println("Table Created for master: " + master.id + " table id: " + newTable.GetTableId());
		return  newTable.GetTableId();
	}

	
	public  String[] GetTablesByMasterId(String masterId) {
		ArrayList<Table> tables = new ArrayList<Table>();
		
		for(int i = 0; i < this.availableTables.size(); i++) {
			if(this.availableTables.get(i).GetMasterId().equals(masterId)) {
				tables.add(this.availableTables.get(i));
			}
		}
		
		String[] tableArray = new String[tables.size()];
		
		for(int i = 0; i < tables.size(); i++) {
			tableArray[i]=tables.get(i).GetTableId();
		}
		
		return tableArray;
	}
	
	public void JoinTableAsMaster(String tableId, Master master) {
		Table table = GetTableById(tableId);
		
		if(table.GetMasterId() != master.id) {
			return;
		}
		
		table.SetMaster(master);
	}
	
	public void JoinTableAsPlayer(String tableId, Player player) {
		Table table = GetTableById(tableId);
		
		if(table == null) {
			return;
		}

		table.AddPlayer(player);
	}

	public SseEmitter SubscribeToMaster(String tableId){
		Table table = GetTableById(tableId);
		
		if(table == null) {
			return null;
		}

		return table.SubscribeToMasterEvent();
	}

	public SseEmitter SubscribeToPlayer(String tableId){
		Table table = GetTableById(tableId);
		
		if(table == null) {
			return null;
		}

		return table.SubscribeToPlayerEvent();
	}
	
	public void RequestAction(String tableId, Action action) {
		Table table = GetTableById(tableId);
		
		table.RequestAction(action);
	}

	public void TestNotification(String tableId){
		Table table = GetTableById(tableId);

		if(table == null){
			return;
		}

		table.SendEventToMaster();
		table.SendEventToPlayers();
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
