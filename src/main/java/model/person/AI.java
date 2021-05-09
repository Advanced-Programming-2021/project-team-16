package model.person;

import model.Board;
import model.Deck;

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
        //زهرا
    }

    public int[] getTribute(int numberOfTributes) {
        //زهرا
        return null;
    }

//    public void bringInMonster() {
//    }
//
//    public void bringInSpellAndTrap() {
//    }

}
