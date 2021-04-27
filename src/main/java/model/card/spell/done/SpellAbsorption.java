package model.card.spell.done;

import model.Game;
import model.card.spell.Spell;
import model.person.Player;

public class SpellAbsorption extends Spell {
    public SpellAbsorption() {
        super("Spell Absorption", "Spell", SpellType.CONTINUES
                , "Each time a Spell Card is activated, gain 500 Life Points immediately after it resolves.", "Unlimited", 4000);
    }

    public void action(Game game, Player player) {
        player.increaseLP(500);
    }

}
