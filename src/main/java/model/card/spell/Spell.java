package model.card.spell;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Activatable;
import model.card.Card;

public class Spell extends Card implements Activatable {
    protected SpellType spellType;
    protected String icon;
    protected String status;
    protected boolean isActivated = false;

    public Spell(String name, String icon, SpellType spellType, String description, String status, int price) {
        super(name, description, price);
        this.name = name;
        this.description = description;
        this.price = price;
        this.spellType = spellType;
        this.icon = icon;
        this.status = status;
    }

    public enum SpellType {
        EQUIP,
        FIELD,
        QUICK_PLAY,
        RITUAL,
        CONTINUES,
        COUNTER,
        NORMAL

    }


    public SpellType getSpellType() {
        return spellType;
    }


    public String action() {
        Game game = GameMenu.getCurrentGame();
        for (Card card : game.getCurrentPlayer().getBoard().getSpellAndTrapZone())
            if (card instanceof SpellAbsorption && ((SpellAbsorption) card).isActivated)
                ((SpellAbsorption) card).increaseLP();
        for (Card card : game.getRival().getBoard().getSpellAndTrapZone())
            if (card instanceof SpellAbsorption && ((SpellAbsorption) card).isActivated)
                ((SpellAbsorption) card).increaseLP();
        isActivated = true;
        Board.Zone selectedZone = game.getSelectedZone();
        if (selectedZone == Board.Zone.SPELL_AND_TRAP) {
            game.getCurrentPlayer().getBoard().getCardPositions()[1][game.getSelectedZoneIndex()] = Board.CardPosition.ACTIVATED;
            if (spellType != SpellType.EQUIP && spellType != SpellType.CONTINUES) {
                game.removeCardFromZone(game.getSelectedCard(), Board.Zone.SPELL_AND_TRAP, game.getSelectedZoneIndex(), game.getCurrentPlayer().getBoard());
                game.putCardInZone(game.getSelectedCard(), Board.Zone.GRAVE, null, game.getCurrentPlayer().getBoard());
            }

        } else if (selectedZone == Board.Zone.HAND) {
            game.removeCardFromZone(game.getSelectedCard(), Board.Zone.HAND, game.getSelectedZoneIndex(), game.getCurrentPlayer().getBoard());
            if (this.spellType != SpellType.QUICK_PLAY) {
                int indexInSpellZone = game.getCurrentPlayer().getBoard().getFirstEmptyIndexOfZone(Board.Zone.SPELL_AND_TRAP);
                game.putCardInZone(game.getSelectedCard(), Board.Zone.SPELL_AND_TRAP, Board.CardPosition.ACTIVATED, game.getCurrentPlayer().getBoard());
                if (spellType == SpellType.NORMAL || spellType == SpellType.RITUAL) {
                    game.removeCardFromZone(game.getSelectedCard(), Board.Zone.SPELL_AND_TRAP, indexInSpellZone, game.getCurrentPlayer().getBoard());
                    game.putCardInZone(game.getSelectedCard(), Board.Zone.GRAVE, null, game.getCurrentPlayer().getBoard());
                }
            } else
                game.putCardInZone(game.getSelectedCard(), Board.Zone.GRAVE, Board.CardPosition.ACTIVATED, game.getCurrentPlayer().getBoard());
        }
        return this.getName() + " activated ";
    }

    public boolean isActivated() {
        return isActivated;
    }
}

