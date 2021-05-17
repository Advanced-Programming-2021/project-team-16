package model.card.spell;

import controller.GameMenu;
import model.person.Player;

public class SpellAbsorption extends Spell {

    Player owner;

    public SpellAbsorption() {
        super("Spell Absorption", "Spell", SpellType.CONTINUES
                , "Each time a Spell Card is activated, gain 500 Life Points immediately after it resolves.", "Unlimited", 4000);
    }

    public String action() {
        owner = GameMenu.getCurrentGame().getCurrentPlayer();
        super.action();
        return this.name + " is activated";
    }

    public void increaseLP() {
        owner.increaseLP(500);
    }

}
