package br.com.panmar.rpgtable.table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Table {
	private ArrayList<Creature> onTableCreatures;
	private Master master;
	
	public String GetMasterId() {
		return master.id;
	}
	
	public Table(Master master) {
		this.master = master;
	}
	
	private void SortCreaturesOnTable() {
		Collections.sort(onTableCreatures, Comparator.comparingInt(Creature::GetInitiative).reversed());
	}
	
}
