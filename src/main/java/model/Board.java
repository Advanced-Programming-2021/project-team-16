package model;

import controller.GameMenu;
import graphicview.GameView;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.fieldspells.FieldSpell;
import model.person.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Board {
    private final Player player;
    private final ArrayList<Card> deck;
    private final ArrayList<Card> grave = new ArrayList<>();
    private final Monster[] monsterZone = new Monster[5];
    private final Card[] spellAndTrapZone = new Card[5];
    private final Card[] hand = new Card[6];
    private final CardPosition[][] cardPositions = new CardPosition[2][5];
    // first row-> monster zone
    // second row -> spell and trap
    private FieldSpell fieldSpell = null;
    private final boolean[] didMonsterAttack = new boolean[5];


    public Board(ArrayList<Card> deck, Player player) {
        this.deck = deck;
        Collections.shuffle(this.deck);
        this.player = player;
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

    public Card getCardByIndexAndZone(int zoneIndex, Zone zone) {
        if (zone == Zone.MONSTER) return monsterZone[zoneIndex];
        if (zone == Zone.SPELL_AND_TRAP) return spellAndTrapZone[zoneIndex];
        if (zone == Zone.GRAVE) return grave.get(zoneIndex);
        if (zone == Zone.DECK) return deck.get(zoneIndex);
        if (zone == Zone.HAND) return hand[zoneIndex];

        return null;
    }

    public int getNumberOfMonsters() {
        int n = 0;
        for (Monster monster : monsterZone) if (monster != null) n++;
        return n;
    }

    public int getNumberOfSpellAndTraps() {
        int n = 0;
        for (Card card : spellAndTrapZone) if (card != null) n++;
        return n;
    }

    public int getNumberOfHandCards() {
        int n = 0;
        for (Card card : hand) if (card != null) n++;
        return n;
    }

    public boolean doesGraveHaveMonster() {
        for (Card card : grave) if (card instanceof Monster) return true;
        return false;
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

    public FieldSpell getFieldSpell() {
        return fieldSpell;
    }

  public void setFieldSpell(FieldSpell fieldSpell , Card fakeCard) {
      this.fieldSpell = fieldSpell;
   //f (GameMenu.getCurrentGame().isGraphical()) {
   //   if (fieldSpell == null) {
   //       getGameView().myFieldSpell.getChildren().set(0, Card.getBlackRectangle(false));
   //       getRivalGameView().rivalFieldSpell.getChildren().set(0, Card.getBlackRectangle(false));
   //   } else {
   //       getGameView().myFieldSpell.getChildren().set(0, fieldSpell);
   //       getRivalGameView().rivalFieldSpell.getChildren().set(0, fakeCard);
   //    //fieldSpell.setSide(true);
   //    //fieldSpell.setSizes(false);
   //   }
   //
  }

    public GameView getGameView() {
        return player.getGameView();
    }

    public GameView getRivalGameView() {
        return player.getRival().getGameView();
    }

    public void refreshHand() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(hand));
        cards.removeIf(Objects::isNull);
        Arrays.fill(hand, null);
        for (int i = 0; i < cards.size(); i++) hand[i] = cards.get(i);
   // (GameMenu.getCurrentGame().isGraphical()) for (int i = 0; i < hand.length; i++)
   //  if (hand[i] == null) {
   //      getGameView().myHand.getChildren().set(i, Card.getBlackRectangle(true));
   //      getRivalGameView().rivalHand.getChildren().set(i, Card.getBlackRectangle(true));
   //  } else {
   //      Card card = hand[i];
   //      Card fakeCard = Card.make(card.getName());
   //    fakeCard.setSizes(true);
   //    fakeCard.setSide(false);
   //      getGameView().myHand.getChildren().set(i, card);
   //      getRivalGameView().rivalHand.getChildren().set(i, fakeCard);
   //      GameMenu.getCurrentGame().setOnMouseClickedSelect(card, i, Zone.HAND, false);
   //      GameMenu.getCurrentGame().setOnMouseClickedSelect(fakeCard, i, Zone.HAND, true);
   //  }
    }


    public enum CardPosition {
        HIDE_DEF,
        REVEAL_DEF,
        ATK,
        ACTIVATED
    }

    public enum Zone {
        HAND,
        MONSTER,
        SPELL_AND_TRAP,
        FIELD_SPELL,
        GRAVE,
        DECK
    }

    public boolean[] getDidMonsterAttack() {
        return didMonsterAttack;
    }

    public void noMonsterAttacked() {
        Arrays.fill(didMonsterAttack, false);
    }
}
