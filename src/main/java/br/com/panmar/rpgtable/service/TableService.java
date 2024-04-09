package br.com.panmar.rpgtable.service;

import org.springframework.stereotype.Service;

import br.com.panmar.rpgtable.table.Master;
import br.com.panmar.rpgtable.table.Player;
import br.com.panmar.rpgtable.table.Table;

import java.util.ArrayList;


@Service
public class TableService {

	private ArrayList<Table> availableTables = new ArrayList<Table>();
	
	
	public String CreateTable(Master master, String masterAddress) {
		
		Table currentActiveTable = GetTableByMasterId(master.id);
		
		if(currentActiveTable != null) {
			this.availableTables.remove(currentActiveTable);
		}
		
		Table newTable = new Table(master, masterAddress);
		this.availableTables.add(newTable);
		
		System.out.println("Table Created for master: " + master.id + " table id: " + newTable.GetTableId());
		return newTable.GetTableId();
	}
	
	public String JoinTable(String tableId, Player player, String address) {
		Table table = GetTableById(tableId);
		
		System.out.println("Tables: " + availableTables.get(0).GetTableId().toString());
		
		if(table == null) {
			return "Failed to join";
		}
		
		table.AddPlayer(player, address);
		return "joined successefuly";
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
