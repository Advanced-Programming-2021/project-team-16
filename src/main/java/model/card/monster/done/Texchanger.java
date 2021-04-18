package model.card.monster.done;

import model.Board;
import model.Game;
import model.card.monster.Monster;

public class Texchanger extends Monster {
    boolean isAttacked = false;

    public Texchanger() {
        super("Texchanger", "Once per turn, when your monster is targeted for an attack: You can negate " +
                        "that attack, then Special Summon 1 Cyberse Normal Monster from your hand, Deck, or GY.", 200,
                MonsterType.CYBERSE, 1, 100, 100);
    }

    public void specialSummonACyberseMonster(Monster cyberseMonster, Board.Zone zone, int zoneIndex, Game game) {
        if (cyberseMonster.getMonsterType() != MonsterType.CYBERSE) return;
        Board board = game.getCurrentPlayer().getBoard();
        game.removeCardFromZone(cyberseMonster, zone, zoneIndex, board);
        game.putCardInZone(cyberseMonster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
    }

    public boolean isAttacked() {
        return isAttacked;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }
}
