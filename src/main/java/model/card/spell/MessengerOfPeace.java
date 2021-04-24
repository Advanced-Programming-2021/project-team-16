package model.card.spell;

import model.Game;

public class MessengerOfPeace extends Spell {
    public MessengerOfPeace() {
        super("Messenger of peace", "Spell", SpellType.CONTINUES
                , "Monsters with 1500 or more ATK cannot declare an attack. Once per turn, during your Standby Phase, pay 100 LP or destroy this card.", "Unlimited", 4000);
    }

    public void action(Game game) {

    }

}
