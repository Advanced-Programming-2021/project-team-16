package server.modell.card.spell;

import server.modell.Board;
import server.modell.card.UtilActions;

public class Raigeki extends Spell {

    public Raigeki() {
        super("Raigeki", "Spell", SpellType.NORMAL
                , "Destroy all monsters your opponent controls.", "Limited", 2500);
    }

    public String action() {
        UtilActions.removeRivalCards(Board.Zone.MONSTER);
        return super.action() + "and all rival's monsters are destroyed";
    }


}
