package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import model.card.trap.Trap;

public class MysticalSpaceTyphoon extends Spell {
    private boolean isAtivated = false;

    public MysticalSpaceTyphoon() {
        super("Mystical space typhoon", "Spell", SpellType.QUICK_PLAY
                , "Target 1 Spell/Trap on the field; destroy that target.", "Unlimited", 3500);
    }

    public String action(Card spellOrTrap, int zoneIndex) {
        Game game = GameMenu.getCurrentGame();
        if (!(spellOrTrap instanceof Spell) && !(spellOrTrap instanceof Trap))
            return "This is not a spell or trap class";
        Card[] currSpellNTrap = game.getCurrentPlayer().getBoard().getSpellAndTrapZone();
        Board board = game.getCurrentPlayer().getBoard();
        Card[] rivSpellNTrap = game.getRival().getBoard().getSpellAndTrapZone();
        Board rivBoard = game.getRival().getBoard();
        if (!(currSpellNTrap[zoneIndex] == spellOrTrap || rivSpellNTrap[zoneIndex] == spellOrTrap))
            return "given card is neither in rival's spell and trap zone nor current player's.";
        if (currSpellNTrap[zoneIndex] == spellOrTrap) {
            game.removeCardFromZone(spellOrTrap, Board.Zone.SPELL_AND_TRAP, zoneIndex, board);
            game.putCardInZone(spellOrTrap, Board.Zone.GRAVE, null, board);
        }
        if (rivSpellNTrap[zoneIndex] == spellOrTrap) {
            game.removeCardFromZone(spellOrTrap, Board.Zone.SPELL_AND_TRAP, zoneIndex, rivBoard);
            game.putCardInZone(spellOrTrap, Board.Zone.GRAVE, null, rivBoard);
        }
        isAtivated = true;
        super.action();
        return "Target was destroyed successfully.";
    }

    public boolean isActivated() {
        return isAtivated;
    }
}


