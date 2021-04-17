package model.card.spell;

import model.Game;

public class BlackPendant extends Spell {
    public BlackPendant() {
        super("Black Pendant", "Spell", SpellType.EQUIP
                , "The equipped monster gains 500 ATK. When this card is sent from the field to the Graveyard: Inflict 500 damage to your opponent.", "Unlimited", 4300);
    }

    public void action(Game game) {
    }

}
