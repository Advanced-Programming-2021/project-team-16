package model.card.trap;

import model.Board;
import model.Game;
import model.card.monster.Monster;
import model.person.Player;

public abstract class SolemnWarning extends Trap {
    public SolemnWarning() {
        super("SolemnWarning", "Trap", TrapType.COUNTER, "When a monster(s) would be Summoned, OR when a Spell/Trap Card, or monster effect, is activated that includes an effect that Special Summons a monster(s): Pay 2000 LP; negate the Summon or activation, and if you do, destroy it.", "Unlimited", 3000);
    }

    boolean summon = false;

    public String action(Game game, int index) {// summon and flip summon method, stop (flip summon and summon)
        Board board = game.getRival().getBoard();
        Monster monster = board.getMonsterZone()[index];
        Player player = game.getCurrentPlayer();

        game.putCardInZone(monster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        game.removeCardFromZone(monster, Board.Zone.HAND, game.getSelectedZoneIndex(), board);
        summon = true;
        player.decreaseLP(2000);


        summon = false;
        game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
        game.removeCardFromZone(monster, Board.Zone.MONSTER, game.getSelectedZoneIndex(), board);
        return "Stop summon!";
    }
    //  public abstract void action(Game game);
}