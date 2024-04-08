package br.com.panmar.rpgtable.table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.panmar.rpgtable.tools.RandomStringGenerator;

public class Table {
	private ArrayList<Creature> onTableCreatures;
	private Master master;
	private String tableId;
	
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
	
	private void SortCreaturesOnTable() {
		Collections.sort(onTableCreatures, Comparator.comparingInt(Creature::GetInitiative).reversed());
	}
	
}
