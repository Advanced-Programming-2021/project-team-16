package server.model.card.spell;

import controller.GameMenu;
import server.model.card.UtilActions;
import model.person.Player;
import view.CommandProcessor;

public class SupplySquad extends Spell {
    Player owner;
    boolean isUsedInThisTurn = false;

    public SupplySquad() {
        super("SupplySquad", "Spell", SpellType.CONTINUES
                , "Once per turn, if a monster(s) you control is " +
                        "destroyed by battle or card effect: Draw 1 card.", "Unlimited", 4000);
    }


    public String action() {
        owner = GameMenu.getCurrentGame().getCurrentPlayer();
        super.action();
        return "Supply squad's effect was activated";
    }

    public String draw() {
        String message;
        if (CommandProcessor.yesNoQuestion("do you want to activate " + this.name + "?")) {
            message = UtilActions.drawCardsForCurrentPlayer(1);
            if (message == null) {
                isUsedInThisTurn = true;
                return "spell activated";
            } else return message;
        }
        return "";
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isUsedInThisTurn() {
        return isUsedInThisTurn;
    }

    public void setUsedInThisTurn(boolean usedInThisTurn) {
        isUsedInThisTurn = usedInThisTurn;
    }
}
