package model.card.trap;

import model.Board;
import model.Game;
import model.card.Card;
import view.CommandProcessor;


public class MindCrush extends Trap {
    public MindCrush() {
        super("MindCrush", "Trap", TrapType.NORMAL, "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.", "Unlimited", 2000);
    }


    public String action(Game game, Card card1, int i) {
        Board board = game.getRival().getBoard();
        Board board1 = game.getCurrentPlayer().getBoard();
        int int_random = random.nextInt(4);
        Card[] hand = board1.getHand();
        Card randomCard = hand[int_random];
        for (Card rivalCard : board.getHand()) {
            for (Card rivalCard2 : board.getMonsterZone()) {
                for (Card rivalCard3 : board.getDeck()) {
                    for (Card rivalCard4 : board.getSpellAndTrapZone()) {
                        if ((rivalCard.getName().equals(CommandProcessor.scan())) ||
                                (rivalCard2.getName().equals(CommandProcessor.scan())) ||
                                (rivalCard3.getName().equals(CommandProcessor.scan())) ||
                                (rivalCard4.getName().equals(CommandProcessor.scan()))) {
                            game.removeCardFromZone(rivalCard, Board.Zone.HAND, game.getSelectedZoneIndex(), board);
                            game.putCardInZone(rivalCard, Board.Zone.GRAVE, null, board);
                            game.removeCardFromZone(rivalCard2, Board.Zone.MONSTER, game.getSelectedZoneIndex(), board);
                            game.putCardInZone(rivalCard2, Board.Zone.GRAVE, null, board);
                            game.removeCardFromZone(rivalCard3, Board.Zone.DECK, game.getSelectedZoneIndex(), board);
                            game.putCardInZone(rivalCard3, Board.Zone.GRAVE, null, board);
                            game.removeCardFromZone(rivalCard4, Board.Zone.SPELL_AND_TRAP, game.getSelectedZoneIndex(), board);
                            game.putCardInZone(rivalCard4, Board.Zone.GRAVE, null, board);
                        } else {
                            game.removeCardFromZone(randomCard, Board.Zone.HAND, int_random, board1);
                            game.putCardInZone(randomCard, Board.Zone.GRAVE, null, board1);
                        }

                    }
                }
            }
        }
        return "done!";
        // game.removeCardFromZone(card, Board.Zone.HAND, game.getSelectedZoneIndex(), board);
    }
}
