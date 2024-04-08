package br.com.panmar.rpgtable.service;

import org.springframework.stereotype.Service;

import br.com.panmar.rpgtable.table.Master;
import br.com.panmar.rpgtable.table.Table;

import java.util.ArrayList;


@Service
public class TableService {

	private ArrayList<Table> availableTables = new ArrayList<Table>();
	
	
	public void CreateTable(Master master) {
		
		Table currentActiveTable = GetTableByMasterId(master.id);
		
		if(currentActiveTable != null) {
			this.availableTables.remove(currentActiveTable);
		}
		
		Table newTable = new Table(master);
		this.availableTables.add(newTable);
		
		System.out.println("Table Created for master: " + master.id);
	}
	
	private Table GetTableByMasterId(String masterId) {
		for(int i = 0; i < this.availableTables.size(); i++) {
			if(this.availableTables.get(i).GetMasterId() == masterId) {
				return this.availableTables.get(i);
			}
		}
		
		return null;
	}
}
