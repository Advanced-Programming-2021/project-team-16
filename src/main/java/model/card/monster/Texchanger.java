package model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;

public class Texchanger extends Monster {
    boolean isAttacked = false;

    public Texchanger() {
        super("Texchanger", "Once per turn, when your monster is targeted for an attack: You can negate " +
                        "that attack, then Special Summon 1 Cyberse Normal Monster from your hand, Deck, or GY.", 200,
                MonsterType.CYBERSE, 1, 100, 100);
    }

    public String specialSummonACyberseMonster(Card cyberseMonster, Board.Zone zone, int zoneIndex) {
        Game game = GameMenu.getCurrentGame();
        if (!(cyberseMonster instanceof Monster)) return "that's not a monster";
        if (((Monster) cyberseMonster).getMonsterType() != MonsterType.CYBERSE) return "it's not a cyberse.";
        Board board = game.getCurrentPlayer().getBoard();
        game.removeCardFromZone(cyberseMonster, zone, zoneIndex, board);
        game.putCardInZone(cyberseMonster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        setAttacked(true);
        return "special summon is done";
    }

    public boolean isAttacked() {
        return isAttacked;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }
}
