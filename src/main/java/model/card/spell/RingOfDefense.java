package model.card.spell;

public class RingOfDefense extends Spell {

    public RingOfDefense() {
        super("Ring of defense", "Spell", SpellType.QUICK_PLAY
                , "When a Trap effect that inflicts damage is activated: Make that effect damage 0.", "Unlimited", 3500);
    }

    public String action() {
        return super.action();
    }

}
