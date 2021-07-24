package server.modell.card.monster;

import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import view.CommandProcessor;

import java.util.ArrayList;
import java.util.HashMap;

public class Terratiger extends Monster {
    public Terratiger() {
        super("Terratiger", "When this card is Normal Summoned: You can Special" +
                        " Summon 1 Level 4 or lower Normal Monster from your hand in Defense Position.",
                3200, MonsterType.WARRIOR, 4, 1800, 1200);
    }

    public String action() {
        Game game = GameServer.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (board.isZoneFull(Board.Zone.MONSTER)) return "activation cancelled (monster zone is full)";
        Card[] hand = board.getHand();
        ArrayList<Card> monsters = new ArrayList<>();
        HashMap<Card, Integer> monstersWithIndex = new HashMap<>();
        for (int i = 0; i < hand.length; i++) {
            Card card = hand[i];
            if (card instanceof Monster && card.getLevel() <= 4 && isMonsterNormal((Monster) card)) {
                monsters.add(card);
                monstersWithIndex.put(card, i);
            }
        }
        if (monsters.isEmpty() || !CommandProcessor.yesNoQuestion("do you want to active " + this.getName() + "\n" +
                "and summon one of your normal monsters with less than 4 levels?"))
            return "cancelled";
        int index = CommandProcessor.getIndexOfCardArray(monsters, "(your hand monster for special summon)");
        if (index == -1) return "cancelled";
        int handIndex = monstersWithIndex.get(monsters.get(index));
        Monster monster = board.getMonsterZone()[handIndex];
        game.removeCardFromZone(monster, Board.Zone.HAND, handIndex, board);
        game.putCardInZone(monster, Board.Zone.MONSTER, Board.CardPosition.REVEAL_DEF, board);
        return this.getName() + "activated successfully and " + monster.getName() + " summoned successfully";
    }
}
