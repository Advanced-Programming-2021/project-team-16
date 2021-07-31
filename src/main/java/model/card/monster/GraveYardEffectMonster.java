package model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;

public class GraveYardEffectMonster extends Monster {
    private boolean doesLPChange;

    public GraveYardEffectMonster(String name, String description, int price, MonsterType monsterType,
                                  int level, int ATK, int DEF, boolean doesLPChange) {
        super(name, description, price, monsterType, level, ATK, DEF);
        this.doesLPChange = doesLPChange;
    }

    public String action(int indexOfThis) {
        Game game = GameMenu.getCurrentGame();
        game.removeCardFromZone(game.getSelectedCard(), Board.Zone.MONSTER, game.getSelectedZoneIndex(), game.getCurrentPlayer().getBoard());
        game.putCardInZone(game.getSelectedCard(), Board.Zone.GRAVE, null, game.getCurrentPlayer().getBoard());
        Board.CardPosition position = game.getRival().getBoard().getCardPositions()[0][indexOfThis];
        int LPOfThis = position == Board.CardPosition.ATK ? this.ATK : this.DEF;
        int lpDamage = (doesLPChange && position == Board.CardPosition.ATK) ?
                ((Monster) game.getSelectedCard()).getATK() - LPOfThis : 0;
        game.getRival().decreaseLP(lpDamage);
        return "You attacked " + this.getName() + "so both monsters are destroyed and your opponent receives" + lpDamage + " battle damage";
    }
}
