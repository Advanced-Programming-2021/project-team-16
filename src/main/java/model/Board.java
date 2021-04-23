package model;

import model.card.Card;
import model.card.monster.Monster;

import java.util.ArrayList;

public class Board {
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> grave = new ArrayList<>();
    private Monster[] monsterZone = new Monster[5];
    private Card[] spellAndTrapZone = new Card[5];
    private Card[] hand = new Card[6];
    private CardPosition[][] cardPositions = new CardPosition[2][5];
    // first row-> monster zone
    // second row -> spell and trap
    private Card fieldSpell;


    public Board(ArrayList<Card> deck) {

    }

    public boolean isZoneFull(Zone zone) {
        Card[] zoneCards = new Card[0];
        switch (zone) {
            case MONSTER -> zoneCards = monsterZone;
            case HAND -> zoneCards = hand;
            case SPELL_AND_TRAP -> zoneCards = spellAndTrapZone;
            case FIELD_SPELL -> {
                return fieldSpell != null;
            }
        }
        for (Card card : zoneCards) {
            if (card == null) return false;
        }
        return true;
    }

    public int getFirstEmptyIndexOfZone(Zone zone) {
        Card[] zoneCards = new Card[0];
        switch (zone) {
            case MONSTER -> zoneCards = monsterZone;
            case HAND -> zoneCards = hand;
            case SPELL_AND_TRAP -> zoneCards = spellAndTrapZone;
        }
        for (int i = 0; i < zoneCards.length; i++) {
            if (zoneCards[i] == null) return i;
        }
        return -1;
    }

    public int howManyMonsters() {
        int numberOfMonsters = 0;
        for (Monster monster : monsterZone) if (monster != null) numberOfMonsters++;
        return numberOfMonsters;
    }

    public int getIndexOfCard(String cardName, Zone zone) {
        //zone = monster | spell&trap
        if (zone == Zone.MONSTER)
            for (int i = 0; i < monsterZone.length; i++) {
                if (cardName.equals(monsterZone[i].getName())) return i;
            }

        else if (zone == Zone.SPELL_AND_TRAP)
            for (int i = 0; i < spellAndTrapZone.length; i++)
                if (cardName.equals(spellAndTrapZone[i].getName())) return i;

        return -1;
    }

    public Card getCardByIndexAndZone(int zoneIndex, Zone zone) {
        if (zone == Zone.MONSTER) return monsterZone[zoneIndex];
        if (zone == Zone.SPELL_AND_TRAP) return spellAndTrapZone[zoneIndex];
        return null;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getGrave() {
        return grave;
    }

    public Monster[] getMonsterZone() {
        return monsterZone;
    }

    public Card[] getSpellAndTrapZone() {
        return spellAndTrapZone;
    }

    public Card[] getHand() {
        return hand;
    }

    public CardPosition[][] getCardPositions() {
        return cardPositions;
    }

    public Card getFieldSpell() {
        return fieldSpell;
    }

    public void setFieldSpell(Card fieldSpell) {
        this.fieldSpell = fieldSpell;
    }

    public enum CardPosition {
        HIDE_DEF,
        REVEAL_DEF,
        ATK
    }

    public enum Zone {
        HAND,
        MONSTER,
        SPELL_AND_TRAP,
        FIELD_SPELL,
        GRAVE,
        DECK
    }

}
