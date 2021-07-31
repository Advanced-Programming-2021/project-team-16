package model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Activatable;
import model.card.Card;
import consoleView.CommandProcessor;


public class HeraldOfCreation extends Monster implements Activatable {
    boolean isUsed;

    public HeraldOfCreation() {
        super("Herald of Creation", "Once per turn: You can discard 1 card, then target 1 Level 7 or " +
                        "higher monster in your Graveyard; add that target to your hand.",
                2700, MonsterType.SPELL_CASTER, 4, 1800, 600);
    }

    public String action() {
        if (isUsed) return "you can activate Herald of Creation once in a turn";
        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (!board.doesGraveHaveMonster())
            return "there is no monster in your grave. operation cancelled";
        int[] tributes = CommandProcessor.getTribute(1, false);
        if (tributes == null) return "cancelled";
        int indexOfHandZone = tributes[0];
        int graveIndex = CommandProcessor.getMonsterFromGrave(true);
        if (graveIndex == -1) return "cancelled";
        Monster monster = (Monster) board.getCardByIndexAndZone(graveIndex, Board.Zone.GRAVE);
        if (monster.getLevel() < 7) return "The level can't be lower than 7.";
        Card[] handZone = board.getHand();
        game.removeCardFromZone(handZone[indexOfHandZone], Board.Zone.HAND, indexOfHandZone, board);
        game.putCardInZone(handZone[indexOfHandZone], Board.Zone.GRAVE, null, board);
        game.removeCardFromZone(monster, Board.Zone.GRAVE,0,board);
        game.putCardInZone(monster, Board.Zone.HAND,null,board);
        isUsed = true;
        return  monster.getName() + " is in your hand";
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}


