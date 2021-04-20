package model.card.spell.done;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

import java.util.ArrayList;

import static model.Board.CardPosition.*;

public class MonsterReborn extends Spell {
    public MonsterReborn() {
        super("Monster Reborn", "Spell", SpellType.NORMAL
                , "Target 1 monster in either GY; Special Summon it.", "Limited", 2500);
    }

    public String action(String player, String monstername, Board.CardPosition position) {
        Game game = Game.getInstance();
        Card monster = getCardByName(monstername);
        if ("current".equalsIgnoreCase(player)) {
            Board board = game.getCurrentPlayer().getBoard();
            ArrayList<Card> grave = board.getGrave();
            if (!grave.contains(monster)) return "This card is not in your graveyard.";
            if (!(monster instanceof Monster)) return "This is not a monster.";
            if (board.isZoneFull(Board.Zone.MONSTER)) return "Monster zone is full.";
            if (position == HIDE_DEF) return "you can only set position as attack or reveal defence";
            if (position == ATK) {

                game.putCardInZone(monster, Board.Zone.MONSTER, ATK, board);
                game.removeCardFromZone(monster, Board.Zone.GRAVE, 0, board);
            }
            if (position == REVEAL_DEF) {
                game.putCardInZone(monster, Board.Zone.MONSTER, REVEAL_DEF, board);
                game.removeCardFromZone(monster, Board.Zone.GRAVE, 0, board);
            }

        }
        if ("rival".equalsIgnoreCase(player)) {

            Board board = game.getRival().getBoard();
            ArrayList<Card> grave = board.getGrave();
            if (!grave.contains(monster)) return "This card is not in rival's graveyard.";
            if (!(monster instanceof Monster)) return "This is not a monster.";
            if (board.isZoneFull(Board.Zone.MONSTER)) return "Monster zone is full.";
            if (position == HIDE_DEF) return "you can only set position as attack or reveal defence";
            if (position == ATK) {

                game.putCardInZone(monster, Board.Zone.MONSTER, ATK, board);
                game.removeCardFromZone(monster, Board.Zone.GRAVE, 0, board);
            }
            if (position == REVEAL_DEF) {
                game.putCardInZone(monster, Board.Zone.MONSTER, REVEAL_DEF, board);
                game.removeCardFromZone(monster, Board.Zone.GRAVE, 0, board);
            }
        }

        return monstername + " special summoned successfully!";

    }

}

