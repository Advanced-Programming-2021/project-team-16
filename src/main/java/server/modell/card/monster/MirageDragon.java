package server.modell.card.monster;

import server.modell.Board;
import server.controller.GameServer;
import server.modell.Game;

public class MirageDragon extends Monster {
    public MirageDragon() {
        super("MirageDragon", "Your opponent cannot activate Trap Cards during the Battle Phase.",
                100, MonsterType.DRAGON, 4, 1600, 600);
    }

    public static boolean isOn(boolean isMyTurn) {
        Game game = GameServer.getCurrentGame();
        Monster[] monsterZone = isMyTurn ? game.getCurrentPlayer().getBoard().getMonsterZone() :
                game.getRival().getBoard().getMonsterZone();
        Board.CardPosition[] monsterPositions = game.getRival().getBoard().getCardPositions()[0];
        for (int i = 0; i < monsterZone.length; i++) {
            if (monsterZone[i] instanceof MirageDragon && monsterPositions[i] != Board.CardPosition.HIDE_DEF)
                return true;
        }
        return false;
    }
}
