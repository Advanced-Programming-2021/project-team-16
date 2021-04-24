package model.card.trap;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;

public class MagicCylinder extends Trap {
    public MagicCylinder() {
        super("MagicCylinder", "Trap", TrapType.NORMAL, "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, and if you do, inflict damage to your opponent equal to its ATK.", "Unlimited", 2000);
    }

    boolean done = false;

    public String action(Game game) { //attack
        Board board = game.getRival().getBoard();
        Card card = game.getSelectedCard();

        if (card instanceof Monster) {
            game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
            // game.removeCardFromZone(card, Board.Zone.MONSTER, game.getSelectedZoneIndex(), board);???
            done = true;
        }
        if (done = true) {

            assert card instanceof Monster;
            game.getRival().decreaseLP(((Monster) card).getATK());
        }
        return "done!";
    }
}

