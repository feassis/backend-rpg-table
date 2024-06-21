package br.com.panmar.rpgtable.table;
import java.util.ArrayList;

public class GridCell {
    public Player unitOnCell;

    public ArrayList<Neighbours> GetNeighbours(){
        ArrayList<Neighbours> neighbours = new ArrayList<Neighbours>();

        //left
        neighbours.add(new Neighbours(-1, 0));
        //right
        neighbours.add(new Neighbours(1, 0));
        //up
        neighbours.add(new Neighbours(0, 1));
        //down
        neighbours.add(new Neighbours(0, -1));

        return neighbours;
    }
}

