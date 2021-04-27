package model.card.spell.fieldspells;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

import java.util.ArrayList;

public class ClosedForest extends Spell {
    public ClosedForest() {
        super("ClosedForest", "Spell", SpellType.FIELD, "All Beast-Type monsters you control gain 100 ATK for each monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot be activated during the turn this card is destroyed.", "Unlimited", 4300);
    }

    int number = 0;

    public String action(Game game) {
        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        for (Monster monster : monsterZone) {
            if (monster != null) {
                Board board = game.getCurrentPlayer().getBoard();
                ArrayList<Card> grave = board.getGrave();
                for (Card card : grave) {
                    if (card instanceof Monster) {
                        number++;
                    }
                }
                if (number > 0) {
                    if (monster.getMonsterType() == Monster.MonsterType.BEAST) {
                        monster.ATK += number * 100;
                    }

                } else {
                    return "There is no monster in the graveYard";
                }
            }
        }
        super.action(game);
        return "done!";
    }
}
