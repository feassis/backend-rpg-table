package br.com.panmar.rpgtable.tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import br.com.panmar.rpgtable.table.Grid;
import br.com.panmar.rpgtable.table.Neighbours;

public class GridMovement {

        public static class Node {
        int x, y, cost, heuristicCost;
        
        Node(int x, int y, int cost, int heuristicCost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.heuristicCost = heuristicCost;
        }
    }
    
    public static int FindPathCost(Grid grid, int startX, int startY, int endX, int endY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost + node.heuristicCost));
        Set<String> closedSet = new HashSet<>();

        openSet.add(new Node(startX, startY, 0, heuristic(startX, startY, endX, endY)));
        closedSet.add(startX + "," + startY);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.x == endX && current.y == endY) {
                return current.cost;
            }

            for (Neighbours neighbour : grid.grid[current.x][current.y].GetNeighbours()) {
                int newX = current.x + neighbour.dx;
                int newY = current.y + neighbour.dy;
                int newCost = current.cost + grid.moveCost;

                if (isValidPosition(grid, newX, newY) && !closedSet.contains(newX + "," + newY)) {
                    openSet.add(new Node(newX, newY, newCost, heuristic(newX, newY, endX, endY)));
                    closedSet.add(newX + "," + newY);
                }
            }
        }
        
        return -1; // Retorna -1 se não houver caminho
    }

    private static int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }




    public static List<int[]> GetPossiblePositions(Grid grid, int startX, int startY, int movePoints) {
        List<int[]> possiblePositions = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(new int[]{startX, startY, 0});
        visited.add(startX + "," + startY);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int moves = current[2];

            possiblePositions.add(new int[]{x, y});

            if (moves + grid.moveCost <= movePoints) {
                for (Neighbours neighbour : grid.grid[x][y].GetNeighbours()) {
                    int newX = x + neighbour.dx;
                    int newY = y + neighbour.dy;

                    if (isValidPosition(grid, newX, newY) && !visited.contains(newX + "," + newY)) {
                        queue.add(new int[]{newX, newY, moves + grid.moveCost});
                        visited.add(newX + "," + newY);
                    }
                }
            }
        }

        return possiblePositions;
    }

    private static boolean isValidPosition(Grid grid, int x, int y) {
        return x >= 0 && y >= 0 && x < grid.grid.length && y < grid.grid[0].length;
    }
}

