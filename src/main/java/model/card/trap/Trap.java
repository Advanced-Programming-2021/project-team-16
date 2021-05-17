package model.card.trap;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;

public abstract class Trap extends Card {
    protected TrapType trapType;
    protected String icon;
    protected String status;
    private boolean isActivated = false;


    public Trap(String name, String icon, TrapType trapType, String description, String status, int price) {
        super(name, description, price);
        this.name = name;
        this.description = description;
        this.price = price;
        this.trapType = trapType;
        this.icon = icon;
        this.status = status;
    }


    public String getIcon() {
        return icon;
    }

    public String getStatus() {
        return status;
    }

    public Trap.TrapType getTrapType() {
        return trapType;
    }

    public String action(int myIndex) {
        Game game = GameMenu.getCurrentGame();
        Board myBoard = game.getCurrentPlayer().getBoard();
        game.removeCardFromZone(this, Board.Zone.SPELL_AND_TRAP, myIndex, myBoard);
        game.putCardInZone(this, Board.Zone.GRAVE, null, myBoard);
        isActivated = true;
        return this.getName() + " activated ";
    }


    public enum TrapType {
        NORMAL,
        COUNTER,
        CONTINUOUS
    }

    public boolean isActivated() {
        return isActivated;
    }
}
