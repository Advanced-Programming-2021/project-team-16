package model.card.spell.done;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

import java.util.ArrayList;

public class AdvancedRitualArt extends Spell {
    public AdvancedRitualArt() {
        super("Advanced Ritual Art", "Spell", SpellType.RITUAL
                , "This card can be used to Ritual Summon any 1 Ritual Monster. You must also send Normal Monsters from your Deck to the Graveyard whose total Levels equal the Level of that Ritual Monster.", "Unlimited", 3000);
    }

    public String action(Game game, Monster ritualMonster, ArrayList<Card> selectedMonsters) {
        int sumOfLevels = 0;
        Board board = game.getCurrentPlayer().getBoard();
        if (ritualMonster.getMonsterType() == Monster.MonsterType.AQUARITUAL ||
                ritualMonster.getMonsterType() == Monster.MonsterType.WARRIORITUAL) {
            for (Card monster : selectedMonsters) {
                if (!(monster instanceof Monster)) return "You can only choose monsters!";
                sumOfLevels += ((Monster) monster).getLevel();
            }
            if (ritualMonster.getLevel() != sumOfLevels)
                return "Sum of selected card levels doesn't equal to ritual monster's level!";
            for (Card monster : selectedMonsters) {
                game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
                game.removeCardFromZone(monster, Board.Zone.DECK, 0, board);          //zone index fot deck?!
            }
            super.action(game);
        }
        super.action(game);
        return null;
    }

}
