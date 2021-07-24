package server.modell.card.monster;

import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import view.CommandProcessor;

import java.util.ArrayList;
import java.util.Collections;

public class RitualMonster extends Monster {
    protected Card ritualSpell;


    public RitualMonster(String name, String description, int price, MonsterType monsterType, int level, int ATK, int DEF
            , Card ritualSpell) {
        super(name, description, price, monsterType, level, ATK, DEF);
        this.ritualSpell = ritualSpell;
    }

    public String getDeckIndexAndTribute() {
        Game game = GameServer.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        ArrayList<Card> deck = game.getCurrentPlayer().getBoard().getDeck();
        ArrayList<Card> monsters = new ArrayList<>();
        for (Card card : deck) if (card instanceof Monster) monsters.add(card);
        ArrayList<Monster> monsters2 = new ArrayList<>();
        for (Card card : monsters) if (card instanceof Monster) monsters2.add((Monster) card);
        if (!isSumOfLevelsEnough(monsters2)) return "there is no way you could ritual summon a monster";
        Collections.shuffle(monsters);
        ArrayList<Monster> chosenMonsters = new ArrayList<>();
        String message = "choose a tribute for ritual summon";
        do {
            int indexOfChosenMonster = CommandProcessor.getIndexOfCardArray(monsters, message);
            if (indexOfChosenMonster == -1) return "cancelled";
            chosenMonsters.add((Monster) monsters.get(indexOfChosenMonster));
            monsters.remove(indexOfChosenMonster);
            message = "selected monsters levels don’t match with ritual monster\n" +
                    "choose another tribute for ritual summon";
        }
        while (!isSumOfLevelsEnough(chosenMonsters));
        for (Monster chosenMonster : chosenMonsters) {
            game.removeCardFromZone(chosenMonster, Board.Zone.DECK, 0, board);
            game.putCardInZone(chosenMonster, Board.Zone.GRAVE, null, board);
        }
        return null;
    }

    private boolean isSumOfLevelsEnough(ArrayList<Monster> monsters) {
        int sumOfLevels = 0;
        for (Monster monster : monsters) {
            sumOfLevels += monster.getLevel();
        }
        return sumOfLevels >= level;
    }
}
