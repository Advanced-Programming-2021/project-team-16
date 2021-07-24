package server.modell.card.spell;

import controller.GameMenu;
import server.controller.GameServer;
import server.modell.Player;

public class SpellAbsorption extends Spell {

    Player owner;

    public SpellAbsorption() {
        super("SpellAbsorption", "Spell", SpellType.CONTINUES
                , "Each time a Spell Card is activated, gain 500 Life Points immediately after it resolves.", "Unlimited", 4000);
    }

    public String action() {
        owner = GameServer.getCurrentGame().getCurrentPlayer();
        super.action();
        return this.name + " is activated";
    }

    public void increaseLP() {
        owner.increaseLP(500);
    }

}
