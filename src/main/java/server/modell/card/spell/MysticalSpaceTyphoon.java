package server.modell.card.spell;

import server.modell.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import server.modell.card.UtilActions;

import java.util.ArrayList;
import java.util.HashMap;

public class MysticalSpaceTyphoon extends Spell {

    public MysticalSpaceTyphoon() {
        super("Mysticalspacetyphoon", "Spell", SpellType.QUICK_PLAY
                , "Target 1 Spell/Trap on the field; destroy that target.", "Unlimited", 3500);
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
        if (UtilActions.getSpellIndexAndThrowAway(spells, spellsWithIndex, -1) != null) return "cancelled";
        return super.action() + "Target was destroyed successfully.";
    }

}


