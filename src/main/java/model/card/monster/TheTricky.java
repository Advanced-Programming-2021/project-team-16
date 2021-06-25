package model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;

public class TheTricky extends Monster implements specialSummonable {
    public TheTricky() {
        super("The Tricky", "You can Special Summon this card (from your hand) by discarding 1 card."
                , 4300, MonsterType.SPELL_CASTER, 5, 2000, 1200);
    }

    public String specialSummon(int handIndexOfTribute, int handIndexOfThis) {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        Card card = board.getHand()[handIndexOfTribute];
        if (card == null) return "empty hand index";
        game.removeCardFromZone(card, Board.Zone.HAND, handIndexOfTribute, board);
        game.putCardInZone(card, Board.Zone.GRAVE, null, board);
        game.removeCardFromZone(this, Board.Zone.HAND, handIndexOfThis, board);
        game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        return this.getName() + "special summoned successfully";

    }
}
