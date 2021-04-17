package model.card.spell;

import model.Game;

public class SupplySquad extends Spell {
    public SupplySquad() {
        super("Supply Squad", "Spell", SpellType.CONTINUES
                , "Once per turn, if a monster(s) you control is destroyed by battle or card effect: Draw 1 card.", "Unlimited", 4000);
    }

    public void action(Game game) {
    }

}
