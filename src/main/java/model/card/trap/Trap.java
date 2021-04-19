package model.card.trap;

import model.card.Card;

public abstract class Trap extends Card {
    protected TrapType TrapType;
    public String icon;
    public String status;

    public Trap(String name, String icon, Trap.TrapType trapType, String description, String status, int price) {
        super(name, description, price);
        this.name = name;
        this.description = description;
        this.price = price;
        this.TrapType = trapType;
        this.icon = icon;
        this.status = status;
    }


    public enum TrapType {
        NORMAL,
        COUNTER,
        CONTINUOUS
    }

    public abstract boolean action();
}
