package model.card.spell.done;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.spell.Spell;

public class TwinTwisters extends Spell {
    public TwinTwisters() {
        super("Twin Twisters", "Spell", SpellType.QUICK_PLAY
                , "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.", "Unlimited", 3500);
    }

    /**
     * @param index index of your card you want to remove from your hand
     * @param cards spell or traps to remove
     */

    public String action(Game game, int index, Card... cards) {
        if (game.getCurrentPlayer().getBoard().getHand()[index] == null) {
            return "This index is empty!";
        }
        if (cards.length > 2) {
            return "You can't choose more than 2 cards!";
        }
        if (cards.length == 0) {
            return "You should pick at least 1 card!";
        }

        game.removeCardFromZone(game.getCurrentPlayer().getBoard().getHand()[index], Board.Zone.HAND, index, game.getCurrentPlayer().getBoard());
        for (Card card : cards) {
            game.removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, 0, game.getCurrentPlayer().getBoard());
            game.removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, 0, game.getRival().getBoard());
        }
        super.action(game);
        return "Removed!";

    }
}