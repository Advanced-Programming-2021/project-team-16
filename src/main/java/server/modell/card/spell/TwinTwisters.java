package server.modell.card.spell;

import server.modell.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import server.modell.card.UtilActions;
import client.view.CommandProcessor;

import java.util.ArrayList;
import java.util.HashMap;

public class TwinTwisters extends Spell {
    public TwinTwisters() {
        super("TwinTwisters", "Spell", SpellType.QUICK_PLAY
                , "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.", "Unlimited", 3500);
    }


    public String action() {
        Game game = GameServer.getCurrentGame();
        Board board = game.getRival().getBoard();
        Card[] cards = board.getSpellAndTrapZone();
        ArrayList<Card> spells = new ArrayList<>();
        HashMap<Card, Integer> spellsWithIndex = new HashMap<>();
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                spells.add(cards[i]);
                spellsWithIndex.put(cards[i], i);
            }
        }
        if (spells.size() < 1)
            return "you can't activate this (no spells and traps to throw away)";
        int[] tributes = CommandProcessor.getTribute(1, false);
        if (tributes == null) return "cancelled";
        if (UtilActions.getSpellIndexAndThrowAway(spells, spellsWithIndex, tributes[0]) != null) return "cancelled";
        if (spells.size() != 0) {
            if (CommandProcessor.yesNoQuestion("do you want to choose another spell or trap to throw away?"))
                UtilActions.getSpellIndexAndThrowAway(spells, spellsWithIndex, -1);
        }
        return super.action() + "and cards are Removed!";
    }

}