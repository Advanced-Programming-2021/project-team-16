package model.card.trap;

import model.card.Card;

import java.util.Random;

public abstract class Trap extends Card {
    protected TrapType TrapType;
    public String icon;
    public String status;
    public Random random = new Random();

    public Trap(String name, String icon, Trap.TrapType trapType, String description, String status, int price) {
        super(name, description, price);
        this.name = name;
        this.description = description;
        this.price = price;
        this.TrapType = trapType;
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
