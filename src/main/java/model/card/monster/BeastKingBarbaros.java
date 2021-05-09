package model.card.monster;

import model.Board;
import model.Game;
import model.card.Card;

public class BeastKingBarbaros extends Monster implements specialSummonable {
    public BeastKingBarbaros() {
        super("Beast King Barbaros", "You can Normal Summon/Set this card without Tributing, but its original " +
                "ATK becomes 1900. You can Tribute 3 monsters to Tribute Summon (but not Set) this card. If Summoned this" +
                " way: Destroy all cards your opponent controls.", 9200, MonsterType.BEAST_WARRIOR, 8, 3000, 1200);
    }

    public void normalSummonOrSet() {
        this.ATK = 1900;
    }

    public void specialSummon(int[] monsterZoneIndex, int handZoneIndexOfThis, Game game) {
        specialSummonable.tribute(monsterZoneIndex, game);
        Board board = game.getCurrentPlayer().getBoard();
        game.removeCardFromZone(this, Board.Zone.HAND, handZoneIndexOfThis, board);
        game.putCardInZone(this, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        board = game.getRival().getBoard();
        for (int monsterZoneIndexx = 0; monsterZoneIndexx < board.getMonsterZone().length; monsterZoneIndexx++) {
            Monster monster = board.getMonsterZone()[monsterZoneIndexx];
            if (monster != null) game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(monster, Board.Zone.MONSTER, monsterZoneIndexx, board);
        }
        for (int spellAndTrapZoneIndex = 0; spellAndTrapZoneIndex < board.getSpellAndTrapZone().length; spellAndTrapZoneIndex++) {
            Card card = board.getMonsterZone()[spellAndTrapZoneIndex];
            if (card != null) game.putCardInZone(card, Board.Zone.GRAVE, null, board);
            game.removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, spellAndTrapZoneIndex, board);
        }
        Card fieldSpell = board.getFieldSpell();
        game.removeCardFromZone(fieldSpell, Board.Zone.FIELD_SPELL, 0, board);
        if (fieldSpell != null) game.putCardInZone(fieldSpell, Board.Zone.GRAVE, null, board);
    }
}

