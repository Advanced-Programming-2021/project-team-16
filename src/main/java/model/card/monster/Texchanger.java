package model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Card;
import view.CommandProcessor;

import java.util.ArrayList;

public class Texchanger extends Monster {
    boolean isUsed = false;

    public Texchanger() {
        super("Texchanger", "Once per turn, when your monster is targeted for an attack: You can negate " +
                        "that attack, then Special Summon 1 Cyberse Normal Monster from your hand, Deck, or GY.", 200,
                MonsterType.CYBERSE, 1, 100, 100);
    }


    public String doAction() {
        isUsed = true;
        Game game = GameMenu.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (board.isZoneFull(Board.Zone.MONSTER)) return "no way you can special summon.(monster zone is full)";
        ArrayList<MonsterWithIndexAndZone> monsterWithIndexAndZones = new ArrayList<>();
        ArrayList<Card> cyberseMonsters = new ArrayList<>();
        int handNum, graveNum;
        for (int i = 0; i < board.getHand().length; i++)
            addCybersesToArray(monsterWithIndexAndZones, cyberseMonsters, i, board.getHand()[i], Board.Zone.HAND);
        handNum = cyberseMonsters.size();
        for (int i = 0; i < board.getGrave().size(); i++)
            addCybersesToArray(monsterWithIndexAndZones, cyberseMonsters, i, board.getGrave().get(i), Board.Zone.GRAVE);
        graveNum = cyberseMonsters.size() - handNum;
        for (int i = 0; i < board.getDeck().size(); i++)
            addCybersesToArray(monsterWithIndexAndZones, cyberseMonsters, i, board.getDeck().get(i), Board.Zone.DECK);
        if (cyberseMonsters.isEmpty()) return "no normal cyberse cards for summoning";
        int index = CommandProcessor.getIndexOfCardArray(cyberseMonsters, "all cyberse monsters you have\n" +
                "(first " + handNum + " cards are from hand, next " + graveNum + " cards from grave and others from deck)");
        if (index == -1) return "cancelled";
        Monster chosenMonster = (Monster) cyberseMonsters.get(index);
        Board.Zone zone = Board.Zone.HAND;
        for (MonsterWithIndexAndZone monsterWithIndexAndZone : monsterWithIndexAndZones)
            if (monsterWithIndexAndZone.monster == chosenMonster) {
                index = monsterWithIndexAndZone.index;
                zone = monsterWithIndexAndZone.zone;
            }
        game.removeCardFromZone(chosenMonster, zone, index, board);
        game.putCardInZone(chosenMonster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        return "special summon is done";
    }

    private void addCybersesToArray(ArrayList<MonsterWithIndexAndZone> monsterWithIndexAndZones, ArrayList<Card> cyberseMonsters, int i, Card card, Board.Zone zone) {
        if (card instanceof Monster && ((Monster) card).getMonsterType() == MonsterType.CYBERSE && isMonsterNormal((Monster) card)) {
            cyberseMonsters.add(card);
            monsterWithIndexAndZones.add(new MonsterWithIndexAndZone((Monster) card, i, zone));
        }
    }


    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}

class MonsterWithIndexAndZone {
    Monster monster;
    int index;
    Board.Zone zone;

    public MonsterWithIndexAndZone(Monster monster, int index, Board.Zone zone) {
        this.monster = monster;
        this.index = index;
        this.zone = zone;
    }
}
