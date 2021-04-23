package model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;

import java.util.ArrayList;

public class HeraldOfCreation extends Monster {
    boolean isUsed;

    public HeraldOfCreation() {
        super("Herald of Creation", "Once per turn: You can discard 1 card, then target 1 Level 7 or " +
                        "higher monster in your Graveyard; add that target to your hand.",
                2700, MonsterType.SPELL_CASTER, 4, 1800, 600);
    }

    public String action(int indexOfHandZone, String monsterName) {
        Game game = GameMenu.getCurrentGame();
        Card monster = getCardByName(monsterName);
        if (!(monster instanceof Monster)) return "This is not a monster.";
        Board board = game.getCurrentPlayer().getBoard();
        ArrayList<Card> grave = board.getGrave();
        if (!grave.contains(monster)) return "This card is not in your graveyard.";
        if (((Monster) monster).getLevel() < 7) return "The level can't be lower than 7.";
        Card[] handZone = board.getHand();
        game.putCardInZone(handZone[indexOfHandZone], Board.Zone.GRAVE, null, board);
        game.putCardInZone(monster, Board.Zone.HAND, null, board);
        game.removeCardFromZone(monster, Board.Zone.GRAVE, 0, board);
        isUsed = true;
        return monsterName + " came into your hand successfully.";
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
