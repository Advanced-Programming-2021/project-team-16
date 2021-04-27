package model.card.spell.done;

import model.Board;
import model.Game;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class SwordsOfRevealingLight extends Spell {
    boolean isAtivated;

    public SwordsOfRevealingLight() {
        super("Swords of Revealing Light", "Spell", SpellType.NORMAL
                , "After this card's activation, it remains on the field, but destroy it during the End Phase of your opponent's 3rd turn. When this card is activated: If your opponent controls a face-down monster, flip all monsters they control face-up. While this card is face-up on the field, your opponent's monsters cannot declare an attack."
                , "Unlimited", 2500);
    }

    public String action(Game game) {
        isAtivated = true;
        Monster[] monsterZone = game.getRival().getBoard().getMonsterZone();
        Board board = game.getRival().getBoard();
        for (int i = 0; i < monsterZone.length; i++) {
            if (monsterZone[i] != null && board.getCardPositions()[0][i] == Board.CardPosition.HIDE_DEF)
                board.getCardPositions()[0][i] = Board.CardPosition.REVEAL_DEF;
            super.action(game);
        }
        return null;
    }

    public boolean isActivated() {
        return isAtivated;
    }
}
