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
import model.card.trap.*;
import server.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AI extends Player {
    public AI() {
        super(null);
        board = new Board(Deck.getRandomMainDeck(), this);
        user = new User("AI", "", "ai");
    }

    public void playMainPhase() {
        Game game = GameMenu.getCurrentGame();
        if (!game.isOver()) setOrSummon();
        if (!game.isOver()) activeEffect();
        if (!game.isOver()) game.deselect();
        if (!game.isOver() && game.isGraphical()) game.goToNextPhase();
    }

    public void setOrSummon() {
        Game game = GameMenu.getCurrentGame();
        if (!game.isOver() && board.getNumberOfMonsters() < 3) tryToSummonMonster();
        if (!game.isOver() && !game.hasSummonedOrSet() && board.getNumberOfSpellAndTraps() < 3) tryToSetTrap();
        if (!game.isOver() && !game.hasSummonedOrSet() && board.getNumberOfMonsters() >= 3) tryToSummonMonster();
        if (!game.isOver() && !game.hasSummonedOrSet() && board.getNumberOfSpellAndTraps() >= 3) tryToSetTrap();
    }

    private void tryToSetTrap() {
        Game game = GameMenu.getCurrentGame();
        Board rivalBoard = rival.getBoard();
        Card[] hand = board.getHand();
        boolean shouldSet;
        for (int i = 0; i < hand.length; i++) {
            if (game.isOver()||game.hasSummonedOrSet()) break;
            shouldSet = false;
            Card card = hand[i];
            if (card instanceof Trap) {
                if (card instanceof MagicJammer &&
                        rivalBoard.getNumberOfSpellAndTraps() + rivalBoard.getNumberOfHandCards() > 3
                        && board.getNumberOfHandCards() > 1) shouldSet = true;
                if (card instanceof NegateAttack && rivalBoard.getNumberOfMonsters() > 1) shouldSet = true;
                if (card instanceof TimeSeal && rivalBoard.getNumberOfHandCards() < 2) shouldSet = true;
                if (card instanceof TorrentialTribute && board.getNumberOfMonsters() < rivalBoard.getNumberOfMonsters())
                    shouldSet = true;
                if (card instanceof TrapHole && rivalBoard.getNumberOfMonsters() > 1) shouldSet = true;
                if (card instanceof MirrorForce && rivalBoard.getNumberOfMonsters() > 1) shouldSet = true;
                if (card instanceof MagicCylinder && rivalBoard.getNumberOfMonsters() != 0) shouldSet = true;
            }
            if (shouldSet) {
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
                if (hand[monsterIndexes.get(j)].getLevel() < hand[monsterIndexes.get(j + 1)].getLevel())
                    Collections.swap(monsterIndexes, j, j + 1);
        for (Integer monsterIndex : monsterIndexes) {
            if (game.isOver()||game.hasSummonedOrSet()) break;
            game.selectCard(Board.Zone.HAND, monsterIndex, false);
            game.summon();
        }
    }

    public void activeEffect() {
        Game game = GameMenu.getCurrentGame();
        Card[] myHand = board.getHand();
        Board rivalBoard = game.getRival().getBoard();
        boolean shouldActive;
        for (int i = myHand.length - 1; i >= 0; i--) {
            if (game.isOver()) break;
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
                if (game.getSelectedCard() != null) game.activeEffect();
            }
        }
        for (int i = 0; i < board.getSpellAndTrapZone().length; i++) {
            if (game.isOver()) break;
            Card card = board.getSpellAndTrapZone()[i];
            shouldActive = false;
            if (card instanceof Trap) {
                if (card instanceof CallOfTheHaunted && board.doesGraveHaveMonster() && board.getNumberOfMonsters() <= 3)
                    shouldActive = true;
                else if (card instanceof MindCrush && board.getNumberOfHandCards() > 2) shouldActive = true;
            }
            if (shouldActive) game.selectCard(Board.Zone.SPELL_AND_TRAP, i, false);
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
        while (myMonsterIndex != -1 && !game.isOver()) {
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
            if (myMonsters[myMonsterIndex].getATK() < minATKOrDEF && rivalMonsterIndex != -1) {
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
            if (card instanceof Monster) if (card.getLevel() > mostLeveledMonster.getLevel()) {
                mostLeveledMonster = (Monster) card;
                mostLeveledMonsterIndex = i;
            }
        }
        return mostLeveledMonsterIndex;
    }

}
