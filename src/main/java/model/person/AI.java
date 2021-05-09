package model.person;

import controller.GameMenu;
import model.Board;
import model.Deck;
import model.Game;
import model.card.monster.Monster;

import java.util.Arrays;

public class AI extends Player {
    public AI() {
        super(null);
        board = new Board(Deck.getRandomDeck());
    }

    public void playMainPhase() {
        setOrSummon();
        activeEffect();
    }

    public void setOrSummon() {
        //دنیا
    }

    public void activeEffect() {
        //درسا
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


    public int[] getTribute(int numberOfTributes) {
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
    }

//    public void bringInMonster() {
//    }
//
//    public void bringInSpellAndTrap() {
//    }

}
