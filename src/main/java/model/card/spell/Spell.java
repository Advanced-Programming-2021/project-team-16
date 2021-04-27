package model.card.spell;

import model.Game;
import model.card.Card;
import model.card.spell.done.SpellAbsorption;

public abstract class Spell extends Card {
    protected SpellType spellType;
    public String icon;
    public String status;

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

    public String getIcon() {
        return icon;
    }

    public String getStatus() {
        return status;
    }

    public SpellType getSpellType() {
        return spellType;
    }

    public String action(Game game) {
        for (Card card : game.getCurrentPlayer().getBoard().getSpellAndTrapZone()) {
            if (card instanceof SpellAbsorption) {
                ((SpellAbsorption) card).action(game, game.getCurrentPlayer());
            }
        }
        for (Card card : game.getRival().getBoard().getSpellAndTrapZone()) {
            if (card instanceof SpellAbsorption) {
                ((SpellAbsorption) card).action(game, game.getRival());
            }
        }
        return null;
    }

}

