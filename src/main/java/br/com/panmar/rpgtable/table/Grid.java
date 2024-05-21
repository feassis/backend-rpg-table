package br.com.panmar.rpgtable.table;

import java.util.List;

import br.com.panmar.rpgtable.tools.GridMovement;

public class Grid {
    public GridCell[][] grid;
    public int moveCost = 2;

    public void InitializeGrid(int rows, int cols) {
        grid = new GridCell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new GridCell();
            }
        }
    }

    public List<int[]> GetPossibleMovement(int posX, int posY){
        return GridMovement.GetPossiblePositions(this, posX, posY, moveCost);
    }

    public int MoveTo(int initPosX, int initPosY, int endPosX, int endPosY){
        int totalMoveCost = GridMovement.FindPathCost(this, initPosX, initPosY, endPosX, endPosY);

        return totalMoveCost;
    }
}
