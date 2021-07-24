package server.model.card.trap;

import server.model.card.UtilActions;

public class TorrentialTribute extends Trap {
    public TorrentialTribute() {
        super("TorrentialTribute", "Trap", TrapType.NORMAL, "When a monster(s) is Summoned: Destroy all monsters on the field.", "Unlimited", 2000);
    }


    public String action(int myIndex) {
        UtilActions.removeAllMonsters();
        return super.action(myIndex) + "and removed all monsters";
    }
}