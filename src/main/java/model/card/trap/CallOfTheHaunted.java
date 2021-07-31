package model.card.trap;


import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Activatable;
import model.card.monster.Monster;
import consoleView.CommandProcessor;

public class CallOfTheHaunted extends Trap implements Activatable {
    public CallOfTheHaunted() {
        super("CallOfTheHaunted", "Trap", TrapType.NORMAL, "Activate this card by targeting 1 monster in your GY;" +
                " Special Summon that target in Attack Position. When this card leaves the field," +
                " destroy that monster. When that monster is destroyed, destroy this card.", "Unlimited", 3500);
    }

    public String action() {
        Game game = GameMenu.getCurrentGame();
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