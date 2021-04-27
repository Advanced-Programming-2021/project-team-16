package model.card.trap;

import model.card.Card;

public abstract class Trap extends Card {
    protected TrapType trapType;
    public String icon;
    public String status;


    public Trap(String name, String icon, TrapType trapType, String description, String status, int price) {
        super(name, description, price);
        this.name = name;
        this.description = description;
        this.price = price;
        this.trapType = trapType;
        this.icon = icon;
        this.status = status;
    }


    // @Override
    // public abstract Trap();


    public enum TrapType {
        NORMAL,
        COUNTER,
        CONTINUOUS
    }


}
