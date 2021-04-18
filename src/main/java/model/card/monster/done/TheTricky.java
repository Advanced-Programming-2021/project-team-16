package model.card.monster.done;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;

public class TheTricky extends Monster implements specialSummonable {
    public TheTricky() {
        super("The Tricky", "You can Special Summon this card (from your hand) by discarding 1 card."
                , 4300, MonsterType.SPELL_CASTER, 5, 2000, 1200);
    }

    public void specialSummon(int handIndexOfTribute, int handIndexOfThis, Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        Card card = board.getHand()[handIndexOfTribute];
        if (card == null) return;
        game.putCardInZone(card, Board.Zone.GRAVE, null, board);
        game.removeCardFromZone(card, Board.Zone.HAND, handIndexOfTribute, board);
        game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        game.removeCardFromZone(this, Board.Zone.HAND, handIndexOfThis, board);

    }
}
