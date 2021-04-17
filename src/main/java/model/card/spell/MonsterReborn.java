package model.card.spell;

import model.Game;

public class MonsterReborn extends Spell {
    public MonsterReborn() {
        super("Monster Reborn", "Spell", SpellType.NORMAL
                , "Target 1 monster in either GY; Special Summon it.", "Limited", 2500);
    }

    public void action(Game game) {

    }
}
