package model.card.spell;

import controller.GameMenu;
import model.Game;
import model.Phase;
import model.card.monster.Monster;

public class MessengerOfPeace extends Spell {
    boolean monsterCantAttack;
    private boolean isAtivated = false;

    public MessengerOfPeace() {
        super("Messenger of peace", "Spell", SpellType.CONTINUES
                , "Monsters with 1500 or more ATK cannot declare an attack. Once per turn, during your Standby Phase, pay 100 LP or destroy this card.", "Unlimited", 4000);
    }

    public String action(Monster monster, Phase phase) {
        Game game = GameMenu.getCurrentGame();
        if (monster.getATK() >= 1500) monsterCantAttack = true;
        isAtivated = true;
        super.action();
        return null;
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
