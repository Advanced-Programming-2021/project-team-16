package model.person;

import controller.GameMenu;
import model.Board;
import model.Deck;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell.*;
import model.card.trap.MagicCylinder;
import model.card.trap.Trap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class AI extends Player {
    public AI() {
        super(null);
        board = new Board(Deck.getRandomMainDeck());
        user = new User("AI", "", "ai");
    }

    public void playMainPhase() {
        setOrSummon();
        activeEffect();
    }

    public void setOrSummon() {
        Game game = GameMenu.getCurrentGame();
        Card[] hand = board.getHand();
        ArrayList<Integer> monsterIndexes = new ArrayList<>();
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] instanceof Monster) monsterIndexes.add(i);
        }
        for (int i = monsterIndexes.size() - 1; i > 0; i--)
            for (int j = 0; j < i; j++)
                if (((Monster) hand[monsterIndexes.get(j)]).getLevel() < ((Monster) hand[monsterIndexes.get(j + 1)]).getLevel())
                    Collections.swap(monsterIndexes, j, j + 1);
        for (Integer monsterIndex : monsterIndexes) {
            if (game.hasSummonedOrSet()) break;
            game.selectCard(Board.Zone.HAND, monsterIndex, false);
            game.summon();
        }
        for (int i = 0; i < hand.length; i++) {
            if (game.hasSummonedOrSet()) break;
            if (hand[i] instanceof Trap) {
                game.selectCard(Board.Zone.HAND, i, false);
                game.set();
            }
        }
    }

    public void activeEffect() {
        Game game = GameMenu.getCurrentGame();
        Card[] myHand = board.getHand();
        Board rivalBoard = game.getRival().getBoard();
        Card[] rivalSpellAndTrap = rivalBoard.getSpellAndTrapZone();
        Monster[] rivalMonsters = rivalBoard.getMonsterZone();
        Monster[] myMonsters = board.getMonsterZone();
        for (int i = 0; i < myHand.length; i++) {
            if (myHand[i] instanceof Spell) {
                if (myHand[i] instanceof HarpiesFeatherDuster) {
                    if (rivalSpellAndTrap != null && rivalSpellAndTrap.length > 2) {
                        game.setSelectedCard(myHand[i]);
                        game.activeEffect();
                    }
                }

                if (myHand[i] instanceof DarkHole) {
                    int sumOfRivalATK = 0;
                    int sumOfMyATK = 0;
                    if (rivalMonsters != null) {
                        for (int j = 0; j < rivalMonsters.length; j++) {
                            sumOfRivalATK += rivalMonsters[i].getATK();
                        }
                        for (int j = 0; j < myMonsters.length; j++) {
                            sumOfMyATK += myMonsters[i].getATK();
                        }
                        if (sumOfMyATK < sumOfRivalATK) {
                            game.setSelectedCard(myHand[i]);
                            game.activeEffect();
                        }
                    }
                }
                if (myHand[i] instanceof ChangeOfHeart) {
                    int rivalBiggestATK = 0;
                    int rivalsBiggestDEF = 0;
                    int myBiggestATK = 0;
                    int myBiggestDEF = 0;
                    int rivalIndexFotATK = 0;
                    int rivalIndexForDEF;
                    for (int j = 0; j < Objects.requireNonNull(rivalMonsters).length; j++) {
                        if (rivalMonsters[i].getATK() > rivalBiggestATK) {
                            rivalBiggestATK = rivalMonsters[i].getATK();
                            rivalIndexFotATK = i;
                        }
                        if (rivalMonsters[i].getDEF() > rivalsBiggestDEF) {
                            rivalsBiggestDEF = rivalMonsters[i].getDEF();
                            rivalIndexForDEF = i;
                        }
                    }
                    for (int j = 0; j < myMonsters.length; j++) {
                        if (myMonsters[i].getATK() > myBiggestATK) myBiggestATK = myMonsters[i].getATK();
                        if (myMonsters[i].getDEF() > myBiggestDEF) myBiggestDEF = myMonsters[i].getDEF();
                    }
                    if (rivalBiggestATK > rivalsBiggestDEF && rivalBiggestATK > myBiggestATK) {
                        Card monster = board.getCardByIndexAndZone(rivalIndexFotATK, Board.Zone.MONSTER);
                        game.setSelectedCard(myHand[i]);
                        game.activeEffect();
                    }
                }
                if (myHand[i] instanceof PotOfGreed) {
                    if (myHand.length <= 2)
                        game.setSelectedCard(myHand[i]);
                    game.activeEffect();
                }
                if (myHand[i] instanceof Raigeki) {
                    if (rivalMonsters != null && rivalMonsters.length > myMonsters.length) {
                        game.setSelectedCard(myHand[i]);
                        game.activeEffect();
                    }
                }
                if (myHand[i] instanceof RingOfDefense) {
                    boolean use = false;
                    for (Card card : rivalSpellAndTrap) {
                        if (card instanceof MagicCylinder)
                            use = true;
                    }
                    if (use) {
                        game.setSelectedCard(myHand[i]);
                        game.activeEffect();
                    }
                }
                if (myHand[i] instanceof SupplySquad) {
                    game.setSelectedCard(myHand[i]);
                    game.activeEffect();
                }
            }
            if (myHand[i] instanceof SwordsOfRevealingLight) {
                int faceDownMonsters = 0;
                if (rivalMonsters != null) {

                    for (int j = 0; j < rivalMonsters.length; j++) {
                        if (board.getCardPositions()[0][i] == Board.CardPosition.HIDE_DEF) faceDownMonsters++;
                    }
                    if (faceDownMonsters > 2) {
                        game.setSelectedCard(myHand[i]);
                        game.activeEffect();
                    }
                }
            }
        }
    }

    public void playBattlePhase() {
        Game game = GameMenu.getCurrentGame();
        Monster[] myMonsters = board.getMonsterZone();
        Board rivalBoard = game.getRival().getBoard();
        Monster[] rivalMonsters = rivalBoard.getMonsterZone();
        Board.CardPosition[] rivalMonsterPositions = rivalBoard.getCardPositions()[0];
        int myMonsterIndex = getMyMonsterIndex();
        while (myMonsterIndex != -1) {
            game.selectCard(Board.Zone.MONSTER, myMonsterIndex, false);
            int rivalMonsterIndex = -1;
            int minATKOrDEF = 9999999;
            for (int i = 0; i < rivalMonsters.length; i++)
                if (rivalMonsters[i] != null) {
                    int ATKOrDEF = rivalMonsterPositions[i] == Board.CardPosition.ATK ?
                            rivalMonsters[i].getATK() : rivalMonsters[i].getDEF();
                    if (ATKOrDEF < minATKOrDEF) {
                        minATKOrDEF = ATKOrDEF;
                        rivalMonsterIndex = i;
                    }
                }
            if (myMonsters[myMonsterIndex].getATK() < minATKOrDEF) return;
            game.attack(rivalMonsterIndex);
            myMonsterIndex = getMyMonsterIndex();
        }
    }

    private int getMyMonsterIndex() {
        Monster[] myMonsters = board.getMonsterZone();
        Board.CardPosition[] myMonsterPositions = board.getCardPositions()[0];
        boolean[] didMonsterAttack = board.getDidMonsterAttack();
        int myMonsterIndex = -1;
        int maxATK = -1;
        for (int i = 0; i < myMonsters.length; i++)
            if (myMonsters[i] != null && myMonsterPositions[i] == Board.CardPosition.ATK && !didMonsterAttack[i])
                if (myMonsters[i].getATK() > maxATK) {
                    maxATK = myMonsters[i].getATK();
                    myMonsterIndex = i;
                }
        return myMonsterIndex;
    }


    public int[] getTribute(int numberOfTributes, boolean isFromMonsterZone) {
        if (isFromMonsterZone) {
            Monster[] monsters = board.getMonsterZone();
            int[] indexes = new int[numberOfTributes];
            Arrays.fill(indexes, -1);
            for (int i = 0; i < numberOfTributes; i++) {
                int min = 100;
                outer:
                for (int j = 0; j < 5; j++) {
                    for (int index : indexes) if (index == j) continue outer;
                    if (monsters[j] != null && monsters[j].getLevel() < min) {
                        min = monsters[j].getLevel();
                        indexes[i] = j;
                    }
                }
            }
            return indexes;
        } else {
            Card[] cards = board.getHand();
            for (int i = 0; i < cards.length; i++) if (cards[i] != null) return new int[]{i};
        }
        return null;
    }

    public int getMonsterFromGrave(boolean isMyGrave) {
        Monster mostLeveledMonster = null;
        int mostLeveledMonsterIndex = -1;
        Board board = isMyGrave ? getBoard() : GameMenu.getCurrentGame().getRival().getBoard();
        ArrayList<Card> grave = board.getGrave();
        for (int i = 0, graveSize = grave.size(); i < graveSize; i++) {
            Card card = grave.get(i);
            if (grave.get(i) instanceof Monster) {
                mostLeveledMonster = (Monster) card;
                mostLeveledMonsterIndex = i;
                break;
            }
        }
        if (mostLeveledMonster == null) return -1;
        ArrayList<Card> boardGrave = board.getGrave();
        for (int i = 0, boardGraveSize = boardGrave.size(); i < boardGraveSize; i++) {
            Card card = boardGrave.get(i);
            if (card instanceof Monster) if (((Monster) card).getLevel() > mostLeveledMonster.getLevel()) {
                mostLeveledMonster = (Monster) card;
                mostLeveledMonsterIndex = i;
            }
        }
        return mostLeveledMonsterIndex;
    }

}
