package server.modell.card.monster;

import controller.GameMenu;
import server.controller.GameServer;
import server.modell.Game;

public class Marshmallon extends Monster {
    public Marshmallon() {
        super("Marshmallon", "Cannot be destroyed by battle. After damage calculation, if this card was" +
                        " attacked, and was face-down at the start of the Damage Step: The attacking player takes 1000 damage.",
                700, MonsterType.FAIRY, 3, 300, 500);
    }

    public String action(int indexOfThis) {
        Game game = GameServer.getCurrentGame();
        game.getCurrentPlayer().decreaseLP(1000);
        int deltaLP = ((Monster) game.getSelectedCard()).getATK() - this.getDEF();
        int damageToYou = deltaLP > 0 ? 1000 : 1000 - deltaLP;
        int damageToRival = Math.max(deltaLP, 0);
        return "opponent’s monster card was " + this.getName() + " and you received " + damageToYou + " battle damage"
                + " and opponent received " + damageToRival + " battle damage";

    }
}
