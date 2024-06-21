package br.com.panmar.rpgtable.table;

public class Action {
    public String actionId;
    public String name;
    public String owner;
    public String target;
    public String causes;
    public String dependsOn;
    public int actionResult;
    public int actionDiceNum;
    public int actionDiceFaces;
    public String actionDiceStatus;

    // Construtor
    public Action(String actionId, String name, String owner, String target, String causes, String dependsOn, int actionResult,
    int actionDiceNum, int actionDiceFaces, String actionDiceStatus) {
        this.actionId = actionId;
        this.name = name;
        this.owner = owner;
        this.target = target;
        this.causes = causes;
        this.dependsOn = dependsOn;
        this.actionResult = actionResult;
        this.actionDiceNum = actionDiceNum;
        this.actionDiceFaces = actionDiceFaces;
        this.actionDiceStatus = actionDiceStatus;
    }

    // toString method
    @Override
    public String toString() {
        return "Action{" +
                "actionId='" + actionId + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", target='" + target + '\'' +
                ", causes='" + causes + '\'' +
                ", dependsOn='" + dependsOn + '\'' +
                ", actionResult=" + actionResult +
                ", actionDiceFaces=" + actionDiceFaces +
                ", actionDiceNum=" + actionDiceNum +
                ", actionDiceStatus=" + actionDiceStatus +
                '}';
    }
}
