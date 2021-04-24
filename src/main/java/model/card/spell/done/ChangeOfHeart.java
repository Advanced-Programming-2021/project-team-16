package model.card.spell.done;

import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.Spell;

public class ChangeOfHeart extends Spell {
    public ChangeOfHeart() {
        super("Change of Heart", "Spell", SpellType.NORMAL
                , "Target 1 monster your opponent controls; take control of it until the End Phase.", "Limited", 2500);
    }

    public String action(Game game, Monster monster, Board.CardPosition position) {
        boolean isValid = false;
        Card selectedMonster = null;
        Board board = game.getRival().getBoard();
        Monster[] monsterZone = game.getRival().getBoard().getMonsterZone();
        for (Monster value : monsterZone) {
            if (value == monster) {
                selectedMonster = value;
                isValid = true;
            }
        }
        if (!isValid) return "The opponent doesn't have this monster!";
        Board board1 = game.getCurrentPlayer().getBoard();
        if (board1.isZoneFull(Board.Zone.MONSTER)) return "Your monster zone is full!";
        game.removeCardFromZone(selectedMonster, Board.Zone.MONSTER, 0, board);
        game.putCardInZone(selectedMonster, Board.Zone.MONSTER, position, board);
        return "Selected card is under your control now!";
    }

    public boolean isOnBoard(Game game) {
        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        Board.CardPosition[] monsterPositions = game.getCurrentPlayer().getBoard().getCardPositions()[0];
        for (int i = 0; i < monsterZone.length; i++) {
            if (monsterZone[i] != null)
                if (this.name.equals(monsterZone[i].getName()))
                    if (monsterPositions[i] != Board.CardPosition.HIDE_DEF)
                        return true;
        }
        return false;
    }
}


