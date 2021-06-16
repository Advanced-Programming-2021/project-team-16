package model.person;

import controller.GameMenu;
import model.Board;
import model.Deck;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.card.monster.RitualMonster;
import model.card.spell.*;
import model.card.spell.fieldspells.FieldSpell;
import model.card.trap.Trap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AI extends Player {
    public AI() {
        super(null);
        board = new Board(Deck.getRandomMainDeck(),this);
        user = new User("AI", "", "ai");
    }

    public void playMainPhase() {
        setOrSummon();
        activeEffect();
        Game game = GameMenu.getCurrentGame();
        game.deselect();
        if (game.isGraphical()) game.goToNextPhase();
    }

    public void setOrSummon() {
        Game game = GameMenu.getCurrentGame();
        if (board.getNumberOfMonsters() < 3) tryToSummonMonster();
        if (!game.hasSummonedOrSet() && board.getNumberOfSpellAndTraps() < 3) tryToSetTrap();
        if (!game.hasSummonedOrSet() && board.getNumberOfMonsters() >= 3) tryToSummonMonster();
        if (!game.hasSummonedOrSet() && board.getNumberOfMonsters() >= 3) tryToSetTrap();
    }

    private void tryToSetTrap() {
        Game game = GameMenu.getCurrentGame();
        Card[] hand = board.getHand();
        for (int i = 0; i < hand.length; i++) {
            if (game.hasSummonedOrSet()) break;
            if (hand[i] instanceof Trap) {
                game.selectCard(Board.Zone.HAND, i, false);
                game.set();
            }
        }
    }

    private void tryToSummonMonster() {
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
    }

    public void activeEffect() {
        Game game = GameMenu.getCurrentGame();
        Card[] myHand = board.getHand();
        Board rivalBoard = game.getRival().getBoard();
        boolean shouldActive;
        for (int i = 0; i < myHand.length; i++) {
            Card card = myHand[i];
            shouldActive = false;
            if (card instanceof Spell) {
                if (card instanceof HarpiesFeatherDuster && rivalBoard.getNumberOfSpellAndTraps() >= 2)
                    shouldActive = true;
                else if (card instanceof DarkHole && board.getNumberOfMonsters() < rivalBoard.getNumberOfMonsters())
                    shouldActive = true;
                else if (card instanceof PotOfGreed && board.getNumberOfHandCards() <= 2) shouldActive = true;
                else if (card instanceof Raigeki && rivalBoard.getNumberOfMonsters() > 1) shouldActive = true;
                else if (card instanceof SupplySquad && board.getNumberOfHandCards() <= 2) shouldActive = true;
                else if (card instanceof MonsterReborn && board.getNumberOfMonsters() <= 3 && board.doesGraveHaveMonster())
                    shouldActive = true;
                else if (card instanceof Terraforming && board.getFieldSpell() == null) shouldActive = true;
                else if (card instanceof SpellAbsorption) shouldActive = true;
                else if (card instanceof MessengerOfPeace && LP >= 900) shouldActive = true;
                else if (card instanceof TwinTwisters && board.getNumberOfHandCards() > 1 && rivalBoard.getNumberOfSpellAndTraps() >= 2)
                    shouldActive = true;
                else if (card instanceof MysticalSpaceTyphoon && rivalBoard.getNumberOfSpellAndTraps() != 0)
                    shouldActive = true;
                else if (card instanceof FieldSpell && (rivalBoard.getFieldSpell() != null || board.getFieldSpell() == null))
                    shouldActive = true;
                else if (card instanceof AdvancedRitualArt && board.getDeck().size() > 15 && doIHaveRitualMonster())
                    shouldActive = true;
            }
            if (shouldActive) {
                game.selectCard(Board.Zone.HAND, i, false);
                game.activeEffect();
            }
        }
    }

    boolean doIHaveRitualMonster() {
        for (Card card : board.getHand()) if (card instanceof RitualMonster) return true;
        return false;
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
            if (myMonsters[myMonsterIndex].getATK() < minATKOrDEF && rivalMonsterIndex!=-1) {
                GameMenu.getCurrentGame().deselect();
                if (game.isGraphical()) game.goToNextPhase();
                return;
            }
            game.attack(rivalMonsterIndex);
            myMonsterIndex = getMyMonsterIndex();
        }
        game.deselect();
        if (game.isGraphical()) game.goToNextPhase();
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
        Game game = GameMenu.getCurrentGame();
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
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] != null) {
                    if (game.getSelectedZone() == Board.Zone.HAND && game.getSelectedZoneIndex() == i) continue;
                    return new int[]{i};
                }
            }
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
