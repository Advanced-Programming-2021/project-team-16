package model.card.spell;

import model.Game;

public class AdvancedRitualArt extends Spell {
    public AdvancedRitualArt() {
        super("Advanced Ritual Art", "Spell", SpellType.RITUAL
                , "This card can be used to Ritual Summon any 1 Ritual Monster. You must also send Normal Monsters from your Deck to the Graveyard whose total Levels equal the Level of that Ritual Monster.", "Unlimited", 3000);
    }

    public void action(Game game) {
    }

}
