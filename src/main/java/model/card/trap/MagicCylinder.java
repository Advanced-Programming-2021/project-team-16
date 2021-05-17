package model.card.trap;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.monster.Monster;

public class MagicCylinder extends Trap {
    public MagicCylinder() {
        super("MagicCylinder", "Trap", TrapType.NORMAL, "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, and if you do, inflict damage to your opponent equal to its ATK.", "Unlimited", 2000);
    }


    public String action(int myIndex) {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getRival().getBoard();
        Monster monster = (Monster) game.getSelectedCard();
        game.removeCardFromZone(monster, Board.Zone.MONSTER, game.getSelectedZoneIndex(), board);
        game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
        //ring of defence
        game.getRival().decreaseLP(monster.getATK());
        return super.action(myIndex) + "and rival monster was removed and rival's Lp decreased";

    }

}

