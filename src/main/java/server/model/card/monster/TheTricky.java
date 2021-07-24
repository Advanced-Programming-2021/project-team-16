package server.model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;
import server.model.card.Card;

public class TheTricky extends Monster implements specialSummonable {
    public TheTricky() {
        super("TheTricky", "You can Special Summon this card (from your hand) by discarding 1 card."
                , 4300, MonsterType.SPELL_CASTER, 5, 2000, 1200);
    }

    public String specialSummon(int handIndexOfTribute) {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        Card[] hand = board.getHand();
        Card tribute = hand[handIndexOfTribute];
        if (tribute == null) return "empty hand index";
        game.removeCardFromZone(tribute, Board.Zone.HAND, handIndexOfTribute, board);
        game.putCardInZone(tribute, Board.Zone.GRAVE, null, board);
        int handIndexOfThis = 0;
        for (int i = 0; i < hand.length; i++) if (hand[i] == this) handIndexOfThis = i;
        game.removeCardFromZone(this, Board.Zone.HAND, handIndexOfThis, board);
        game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        return this.getName() + "special summoned successfully";

    }
}
