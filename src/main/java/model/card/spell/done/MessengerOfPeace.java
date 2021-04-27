package model.card.spell.done;

import model.Game;
import model.Phase;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class MessengerOfPeace extends Spell {
    boolean monsterCantAttack;

    public MessengerOfPeace() {
        super("Messenger of peace", "Spell", SpellType.CONTINUES
                , "Monsters with 1500 or more ATK cannot declare an attack. Once per turn, during your Standby Phase, pay 100 LP or destroy this card.", "Unlimited", 4000);
    }

    public String action(Game game, Monster monster, Phase phase) {
        if (monster.getATK() >= 1500) monsterCantAttack = true;

//        if(phase.getPhaseName().equalsIgnoreCase("standby"))     TODO: standby stuff
        super.action(game);
        return null;
    }

}
