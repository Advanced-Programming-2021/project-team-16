package model.card.trap;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.person.Player;

public class SolemnWarning extends Trap {
    public SolemnWarning() {
        super("SolemnWarning", "Trap", TrapType.COUNTER, "When a monster(s) would be Summoned, OR when a Spell/Trap Card, or monster effect, is activated that includes an effect that Special Summons a monster(s): Pay 2000 LP; negate the Summon or activation, and if you do, destroy it.", "Unlimited", 3000);
    }

    boolean summon = false;

    public String action(Game game, int index) {// summon and flip summon method, stop (flip summon and summon)
        Board board = game.getCurrentPlayer().getBoard();
        Card card = board.getHand()[index];
        Player player = game.getCurrentPlayer();
        if (card instanceof Monster) {
            game.putCardInZone(card, Board.Zone.MONSTER, null, board);
            game.removeCardFromZone(card, Board.Zone.HAND, game.getSelectedZoneIndex(), board);

            player.decreaseLP(2000);
            summon = true;
        }
        summon = false;
        game.putCardInZone(card, Board.Zone.GRAVE, null, board);
        game.removeCardFromZone(card, Board.Zone.MONSTER, game.getSelectedZoneIndex(), board);
        return "done!";
    }
}