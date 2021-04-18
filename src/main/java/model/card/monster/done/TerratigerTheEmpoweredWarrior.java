package model.card.monster.done;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;

public class TerratigerTheEmpoweredWarrior extends Monster {
    public TerratigerTheEmpoweredWarrior() {
        super("Terratiger, the Empowered Warrior", "When this card is Normal Summoned: You can Special" +
                        " Summon 1 Level 4 or lower Normal Monster from your hand in Defense Position.",
                3200, MonsterType.WARRIOR, 4, 1800, 1200);
    }

    public String action(int handIndex, Game game) {
        Board board = game.getCurrentPlayer().getBoard();
        Card card = board.getHand()[handIndex];
        if (card == null) return "There is no card in this hand index";
        if (!(card instanceof Monster)) return "This is not a monster.";
        if (((Monster) card).getLevel() > 4) return "The level can't be more than 4.";
        if (board.isZoneFull(Board.Zone.MONSTER)) return "Monster zone is full.";
        game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.HIDE_DEF, board);
        game.removeCardFromZone(card, Board.Zone.HAND, handIndex, board);
        return "Card set successfully.";
    }
}
