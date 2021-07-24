package server.modell.card.spell;

import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.monster.Monster;
import view.CommandProcessor;


public class MonsterReborn extends Spell {

    public MonsterReborn() {
        super("MonsterReborn", "Spell", SpellType.NORMAL
                , "Target 1 monster in either GY; Special Summon it.", "Limited", 2500);
    }

    public String action() {
        Game game = GameServer.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (board.isZoneFull(Board.Zone.MONSTER)) return "activation cancelled (monster zone is full)";
        if ((!board.doesGraveHaveMonster() && !game.getRival().getBoard().doesGraveHaveMonster()) ||
                board.isZoneFull(Board.Zone.MONSTER))
            return "there is no way you could special summon a monster";
        boolean isMyGrave = CommandProcessor.yesNoQuestion("""
                you can special summon a monster.
                do you want to choose it from your graveyard?
                (no means you want to choose it from rival's graveyard)""");
        int graveIndex = CommandProcessor.getMonsterFromGrave(isMyGrave);
        if (graveIndex == -1) return "cancelled";
        board = isMyGrave ? game.getCurrentPlayer().getBoard() : game.getRival().getBoard();
        Monster monster = (Monster) board.getCardByIndexAndZone(graveIndex, Board.Zone.GRAVE);
        game.removeCardFromZone(monster, Board.Zone.GRAVE, graveIndex, board);
        game.putCardInZone(monster, Board.Zone.MONSTER, null, game.getCurrentPlayer().getBoard());
        super.action();
        return "spell activated and special summoned " + monster.getName() + " successfully";
    }

    public boolean isActivated() {
        return isActivated;
    }
}

