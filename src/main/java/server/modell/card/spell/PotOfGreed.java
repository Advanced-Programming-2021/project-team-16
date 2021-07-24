package server.modell.card.spell;

import server.modell.card.UtilActions;

public class PotOfGreed extends Spell {
    private final boolean isAtivated = false;

    public PotOfGreed() {
        super("PotofGreed", "Spell", SpellType.NORMAL,
                "Draw 2 cards.", "Limited", 2500);
    }

    public String action() {
        String error = UtilActions.drawCardsForCurrentPlayer(2);
        if (error != null) return error;
        super.action();
        return "spell activated and 2 cards where added to your hand";
    }

    public boolean isActivated() {
        return isAtivated;
    }
}

