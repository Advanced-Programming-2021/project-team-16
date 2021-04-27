package model.card.trap;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.done.RingOfDefense;

public class MagicCylinder extends Trap {
    public MagicCylinder() {
        super("MagicCylinder", "Trap", TrapType.NORMAL, "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, and if you do, inflict damage to your opponent equal to its ATK.", "Unlimited", 2000);
    }

    boolean done = false;
    int damageAmount = 0;

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
            damageAmount = ((Monster) card).getATK();
            RingOfDefense ringOfDefense = new RingOfDefense();
            ringOfDefense.action(game);
        }
        return "done!";
    }

    public int getDamageAmount() {
        return damageAmount;
    }
}

