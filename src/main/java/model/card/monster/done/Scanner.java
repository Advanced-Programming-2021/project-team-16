package model.card.monster.done;

import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.person.Player;

import java.util.ArrayList;

public class Scanner extends Monster {
    private Card replacement;
    private int monsterZoneIndex;
    private Player player;
    private boolean isReplaced;

    public Scanner() {
        super("Scanner", "Once per turn, you can select 1 of your opponent's monsters that is removed " +
                        "from play. Until the End Phase, this card's name is treated as the selected monster's name, and this " +
                        "card has the same Attribute, Level, ATK, and DEF as the selected monster. If this card is removed " +
                        "from the field while this effect is applied, remove it from play.", 8000, MonsterType.MACHINE,
                1, 0, 0);
    }


    public String setReplacement(String replacementName, int monsterZoneIndex, Game game) {
        Card replacement = getCardByName(replacementName);
        if (!(replacement instanceof Monster)) return "This is not a monster.";
        ArrayList<Card> rivalGrave = game.getRival().getBoard().getGrave();
        if (!rivalGrave.contains(replacement)) return "This card is not in rival's graveyard.";
        this.replacement = replacement;
        this.player = game.getCurrentPlayer();
        this.isReplaced = true;
        this.monsterZoneIndex = monsterZoneIndex;
        game.getCurrentPlayer().getBoard().getMonsterZone()[monsterZoneIndex] = (Monster) replacement;
        return "Replaced successfully.";
    }

    public void undo(Game game) {
        if (isReplaced) {
            isReplaced = false;
            Monster[] monsters = game.getCurrentPlayer().getBoard().getMonsterZone();
            monsters[monsterZoneIndex] = this;
        }

    }

}
