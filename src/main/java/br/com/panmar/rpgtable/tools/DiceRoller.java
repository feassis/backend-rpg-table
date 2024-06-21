package br.com.panmar.rpgtable.tools;

import java.util.Random;

public class DiceRoller {

    public static DiceRollInfo rollDice(int numDice, int numFaces, int modifier, DiceRollType rollType) {
        Random random = new Random();
        DiceRollInfo diceInfo = new DiceRollInfo();
        diceInfo.numberOfFaces = numFaces;
        diceInfo.modifier = modifier;
        diceInfo.rollType = rollType;

        for (int i = 0; i < numDice; i++) {
            int sortedNum = random.nextInt(numFaces) + 1;
            diceInfo.standardRollArray.add(sortedNum);
            diceInfo.standardRollTotal += sortedNum;
        }

        if (rollType == DiceRollType.STANDARD) {
            diceInfo.result = diceInfo.standardRollTotal + modifier;
            return diceInfo;
        }

        for (int i = 0; i < numDice; i++) {
            int sortedNum = random.nextInt(numFaces) + 1;
            diceInfo.complementaryRollArray.add(sortedNum);
            diceInfo.complementaryRollTotal += sortedNum;
        }

        if (rollType == DiceRollType.ADVANTAGE) {
            diceInfo.result = Math.max(diceInfo.standardRollTotal, diceInfo.complementaryRollTotal) + modifier;
        }

        if (rollType == DiceRollType.DISADVANTAGE) {
            diceInfo.result = Math.min(diceInfo.standardRollTotal, diceInfo.complementaryRollTotal) + modifier;
        }

        return diceInfo;
    }
}
