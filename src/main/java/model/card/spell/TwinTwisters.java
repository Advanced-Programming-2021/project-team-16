package model.card.spell;

import model.Board;
import model.Game;
import model.card.Card;

public class TwinTwisters extends Spell {
    public TwinTwisters() {
        super("Twin Twisters", "Spell", SpellType.QUICK_PLAY
                , "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.", "Unlimited", 3500);
    }

    public void action(Game game) {
        Card[] rivalPlayerSpellAndTrap = game.getRival().getBoard().getSpellAndTrapZone();
        Board board1 = game.getCurrentPlayer().getBoard();
        Card[] currentPlayerSpellAndTrap = board1.getSpellAndTrapZone();
        int int_random = random.nextInt(4);
        Card[] hand = board1.getHand();
        Card randomCard = hand[int_random];
        game.removeCardFromZone(randomCard, Board.Zone.HAND, int_random, board1);
        game.putCardInZone(randomCard, Board.Zone.GRAVE, null, board1);

        for (int i = 0; i < rivalPlayerSpellAndTrap.length; i++) {
            Card card = rivalPlayerSpellAndTrap[i];
            for (int j = 0; j < currentPlayerSpellAndTrap.length; j++) {
                Card card2 = rivalPlayerSpellAndTrap[j];
                for (int b = 0; b < 2; b++) {
                    game.removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, i, game.getRival().getBoard());
                    game.removeCardFromZone(card2, Board.Zone.SPELL_AND_TRAP, j, game.getCurrentPlayer().getBoard());
                }
            }
        }

    }
}