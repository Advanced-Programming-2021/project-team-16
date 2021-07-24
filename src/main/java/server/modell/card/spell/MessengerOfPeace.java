package server.modell.card.spell;

import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import server.modell.card.monster.Monster;

public class MessengerOfPeace extends Spell {

    public MessengerOfPeace() {
        super("Messengerofpeace", "Spell", SpellType.CONTINUES
                , "Monsters with 1500 or more ATK cannot declare an attack." +
                        " Once per turn, during your Standby Phase, pay 100 LP or destroy this card.", "Unlimited", 4000);
    }

    public String action() {
        return super.action();
    }

    public static boolean canBeActivated() {
        Game game = GameServer.getCurrentGame();
        Monster attacking = (Monster) game.getSelectedCard();
        Board rivalBoard = game.getRival().getBoard();
        if (attacking.getATK() >= 1500) {
            Card[] spellAndTrapZone = rivalBoard.getSpellAndTrapZone();
            for (Card card : spellAndTrapZone)
                if (card instanceof MessengerOfPeace && ((MessengerOfPeace) card).isActivated()) return true;
        }
        return false;
    }

    public static String getActivationMessage() {
        return "you can't attack with this card because rival has Messenger of peace";
    }

}
