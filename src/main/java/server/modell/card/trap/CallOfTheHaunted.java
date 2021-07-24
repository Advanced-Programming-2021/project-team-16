package server.modell.card.trap;


import server.modell.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Activatable;
import server.modell.card.monster.Monster;
import client.view.CommandProcessor;

public class CallOfTheHaunted extends Trap implements Activatable {
    public CallOfTheHaunted() {
        super("CallOfTheHaunted", "Trap", TrapType.NORMAL, "Activate this card by targeting 1 monster in your GY;" +
                " Special Summon that target in Attack Position. When this card leaves the field," +
                " destroy that monster. When that monster is destroyed, destroy this card.", "Unlimited", 3500);
    }

    public String action() {
        Game game = GameServer.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (!board.doesGraveHaveMonster()) return "can't activate this. (no monster in grave)";
        if (board.isZoneFull(Board.Zone.MONSTER))
            return "can't activate this. monsterZone is full";
        int index = CommandProcessor.getMonsterFromGrave(true);
        if (index == -1) return "cancelled";
        Monster monster = (Monster) board.getCardByIndexAndZone(index, Board.Zone.GRAVE);
        game.removeCardFromZone(monster, Board.Zone.GRAVE, index, board);
        game.putCardInZone(monster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        return super.action(game.getSelectedZoneIndex()) + "and " + monster.getName() + " summoned successfully!";
    }
}