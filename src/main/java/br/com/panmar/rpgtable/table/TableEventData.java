package br.com.panmar.rpgtable.table;

import java.util.ArrayList;

public class TableEventData {
    public String message;
    public String tableDescription;
    public ArrayList<Player> players;
    public ArrayList<Action> actions;
    public String[][] grid;


    public TableEventData(String message, String tableDescription,
            ArrayList<Player> players, ArrayList<Action> actions, String[][] grid) {
        this.message = message;
        this.tableDescription = tableDescription;
        this.players = players;
        this.actions = actions;
        this.grid = grid;
    }

    
}
