package model.card.monster;

import model.card.Card;


public class CommnadKnight extends Monster {
    public CommnadKnight() {
        super("Command Knight", "All Warrior-Type monsters you control gain 400 ATK. If you control another" +
                        " monster, monsters your opponent controls cannot target this card for an attack.", 2100
                , MonsterType.WARRIOR, 4, 1000, 1000);
    }

    public void action() {
        for (Card card : Card.getCards()) {
            if (card instanceof Monster) ((Monster) card).ATK += 400;
        }
    }

    public void undoAction() {
        for (Card card : Card.getCards()) {
            if (card instanceof Monster) ((Monster) card).ATK -= 400;
        }
    }
}
