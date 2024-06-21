package br.com.panmar.rpgtable.tools;

import java.util.ArrayList;
import java.util.List;

public class DiceRollInfo {
    public int numberOfFaces;
    public DiceRollType rollType;
    public int modifier = 0;
    public List<Integer> standardRollArray = new ArrayList<>();
    public int standardRollTotal = 0;
    public List<Integer> complementaryRollArray = new ArrayList<>();
    public int complementaryRollTotal = 0;
    public int result;

    @Override
    public String toString() {
        return "DiceRollInfo{" +
                "numberOfFaces=" + numberOfFaces +
                ", rollType=" + rollType +
                ", modifier=" + modifier +
                ", standardRollArray=" + standardRollArray +
                ", standardRollTotal=" + standardRollTotal +
                ", complementaryRollArray=" + complementaryRollArray +
                ", complementaryRollTotal=" + complementaryRollTotal +
                ", result=" + result +
                '}';
    }
}
