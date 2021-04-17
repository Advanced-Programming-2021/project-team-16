package model.card.spell;

import model.Game;

public class SwordOfDarkDestruction extends Spell {
    public SwordOfDarkDestruction() {
        super("Sword of dark destruction", "Spell", SpellType.EQUIP
                , "A DARK monster equipped with this card increases its ATK by 400 points and decreases its DEF by 200 points.", "Unlimited", 4300);
    }

    public void action(Game game) {
    }
}
