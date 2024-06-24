package br.com.panmar.rpgtable.table;

import java.util.List;

import br.com.panmar.rpgtable.tools.GridMovement;

public class Grid {
    public GridCell[][] grid;
    public int moveCost = 2;

    public int rows;
    public int cols;

    public void InitializeGrid(int rows, int cols) {
        grid = new GridCell[rows][cols];

        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new GridCell();
            }
        }
    }

    public List<int[]> GetPossibleMovement(int posX, int posY){
        return GridMovement.GetPossiblePositions(this, posX, posY, moveCost);
    }

    public int MoveToCost(int initPosX, int initPosY, int endPosX, int endPosY){
        int totalMoveCost = GridMovement.FindPathCost(this, initPosX, initPosY, endPosX, endPosY);

        return totalMoveCost;
    }

    public void SetPlayerAtPosition(Player player, int x, int y){
        grid[x][y].unitOnCell = player;
    }

    public int[] GetPlayerPos(Player player){
        int[] pos = new int[2];
        pos[0] = -1;
        pos[1] = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if(grid[i][j].unitOnCell == null){
                    continue;
                }


                if(grid[i][j].unitOnCell.playerId == player.playerId){
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
    }

    public void CleanPosition(int x, int y){
        grid[x][y].unitOnCell = null;
    }

    public boolean CanMoveTo(int x, int y){
        return grid[x][y].unitOnCell == null;
    }

    public String[][] GetGridSummary(){
        String[][] gridSummery = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(grid[i][j].unitOnCell != null){
                    gridSummery[i][j] = grid[i][j].unitOnCell.playerId;
                }
            }
        }

        return gridSummery;
    }
}
