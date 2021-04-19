package model.card.monster;

import model.Board;
import model.Game;

public class GraveYardEffectMonster extends Monster {
    private boolean doesLPChange;

    public GraveYardEffectMonster(String name, String description, int price, MonsterType monsterType,
                                  int level, int ATK, int DEF, boolean doesLPChange) {
        super(name, description, price, monsterType, level, ATK, DEF);
        this.doesLPChange = doesLPChange;
    }

    public void action(Game game, int indexOfThis) {
        game.removeCardFromZone(null, Board.Zone.MONSTER, game.getSelectedZoneIndex(), game.getCurrentPlayer().getBoard());
        game.putCardInZone(game.getSelectedCard(), Board.Zone.GRAVE, null, game.getCurrentPlayer().getBoard());
        game.putCardInZone(this, Board.Zone.GRAVE, null, game.getRival().getBoard());
        game.removeCardFromZone(this, Board.Zone.MONSTER, indexOfThis, game.getRival().getBoard());
        int LPOfThis = game.getRival().getBoard().getCardPositions()[0][indexOfThis] == Board.CardPosition.ATK ? this.ATK : this.DEF;
        if (doesLPChange) game.getRival().decreaseLP(((Monster) game.getSelectedCard()).getATK() - LPOfThis);
    }
}
