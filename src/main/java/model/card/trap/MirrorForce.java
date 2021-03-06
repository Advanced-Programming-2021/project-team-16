package model.card.trap;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.monster.Monster;

public class MirrorForce extends Trap {
    public MirrorForce() {
        super("MirrorForce",
                "Trap",
                TrapType.NORMAL,
                "When an opponent's monster declares an attack: Destroy all your opponent's Attack Position monsters.",
                "Unlimited",
                2000
        );
    }

    public String action(int myIndex) {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getRival().getBoard();
        Monster[] monsterZone = board.getMonsterZone();
        for (int i = 0, monsterZoneLength = monsterZone.length; i < monsterZoneLength; i++) {
            Monster monster = monsterZone[i];
            if (board.getCardPositions()[0][i] == Board.CardPosition.ATK) {
                game.removeCardFromZone(monster, Board.Zone.MONSTER, i, board);
                game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            }
        }
        return super.action(myIndex) + " all ATK monsters of rival are destroyed";
    }
}