package br.com.panmar.rpgtable.table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.panmar.rpgtable.tools.DiceRollType;
import static br.com.panmar.rpgtable.tools.DiceRoller.rollDice;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
	public int initiative;
    public String owner;
    public int currentHP;
    public int maxHP;
    public int currentMP;
    public int maxMP;
    public int movement;
    public int currentMovement;
    public int combatant;
    public int keen;
    public int magic;
    public int protector;
    public int eloquence;
    public int create;
    public int partialActionsPerTurn;
    public int currentPartialActionsPerTurn;
    public int bonusActionsPerTurn;
    public int currentBonusActionsPerTurn;

    public String playerId;

    // Getters and Setters

    @Override
    public String toString() {
        return "Player{" +
                "initiative=" + initiative +
                ", owner='" + owner + '\'' +
                ", currentHP=" + currentHP +
                ", maxHP=" + maxHP +
                ", currentMP=" + currentMP +
                ", maxMP=" + maxMP +
                ", movement=" + movement +
                ", current movement=" + currentMovement +
                ", combatant=" + combatant +
                ", keen=" + keen +
                ", magic=" + magic +
                ", protector=" + protector +
                ", eloquence=" + eloquence +
                ", create=" + create +
                ", partialActionsPerTurn = " + partialActionsPerTurn +
                ", currentPartialActionsPerTurn = " + currentPartialActionsPerTurn + 
                ", bonusActionsPerTurn = " + bonusActionsPerTurn +
                ", currentBonusActionsPerTurn = " + currentBonusActionsPerTurn + 
                ", playerId='" + playerId + '\'' +
                '}'; 
    }

    // Method to simulate rolling dice
    public int rollMartialTest() {
        return rollDice(1, 20, combatant + combatant, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Arcana: magic + magic
    public int rollArcanaTest() {
        return rollDice(1, 20, magic + magic, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Sneak: keen + keen
    public int rollSneakTest() {
        return rollDice(1, 20, keen + keen, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Create: create + create
    public int rollCreateTest() {
        return rollDice(1, 20, create + create, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Concentration: protector + protector
    public int rollConcentrationTest() {
        return rollDice(1, 20, protector + protector, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Act: eloquence + eloquence
    public int rollActTest() {
        return rollDice(1, 20, eloquence + eloquence, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Meridian: combatant + magic
    public int rollMeridianTest() {
        return rollDice(1, 20, combatant + magic, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Acrobatics: combatant + keen
    public int rollAcrobaticsTest() {
        return rollDice(1, 20, combatant + keen, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Training: combatant + create
    public int rollTrainingTest() {
        return rollDice(1, 20, combatant + create, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Athletics: combatant + protector
    public int rollAthleticsTest() {
        return rollDice(1, 20, combatant + protector, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Presence: combatant + eloquence
    public int rollPresenceTest() {
        return rollDice(1, 20, combatant + eloquence, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate World: magic + create
    public int rollWorldTest() {
        return rollDice(1, 20, magic + create, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Medicine: magic + protector
    public int rollMedicineTest() {
        return rollDice(1, 20, magic + protector, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate SmoothTalk: magic + eloquence
    public int rollSmoothTalkTest() {
        return rollDice(1, 20, magic + eloquence, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Sleight of Hand: keen + create
    public int rollSleightOfHandTest() {
        return rollDice(1, 20, keen + create, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Monster: create + protector
    public int rollMonsterTest() {
        return rollDice(1, 20, create + protector, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Cultural: create + eloquence
    public int rollCulturalTest() {
        return rollDice(1, 20, create + eloquence, DiceRollType.STANDARD).result;
    }

    // Method to roll dice and calculate Will: protector + eloquence
    public int rollWillTest() {
        return rollDice(1, 20, protector + eloquence, DiceRollType.STANDARD).result;
    }

}
