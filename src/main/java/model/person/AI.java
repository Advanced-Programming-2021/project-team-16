package model.person;

import controller.GameMenu;
import model.Board;
import model.Deck;
import model.Game;
import model.Phase;
import model.card.Card;
import model.card.monster.BeastKingBarbaros;
import model.card.monster.GateGuardian;
import model.card.monster.Monster;
import model.card.spell.*;
import model.card.trap.CallOfTheHaunted;
import model.card.trap.MagicCylinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AI extends Player {
    public AI() {
        super(null);
        board = new Board(Deck.getRandomMainDeck());
        user = new User("AI", "", "ai", 0, 0, new ArrayList<>(), new ArrayList<>(), "");
    }

    public void playMainPhase() {
        setOrSummon();
        activeEffect();
    }

    public void setOrSummon(Card card) {
        ArrayList<Monster> monsterTribute = new ArrayList<>();
        Game game = GameMenu.getCurrentGame();
        Card[] cards = board.getHand();
        Card[] spellAndTrapZone = board.getSpellAndTrapZone();
        Monster[] myMonsters = board.getMonsterZone();
        Board rivalBoard = game.getRival().getBoard();
        boolean done = false;
        Board boardRival = game.getRival().getBoard();

        if (game.getCurrentPhase() == Phase.MAIN_1 && game.getCurrentPhase() == Phase.MAIN_2) {
            for (Card value : cards) {
                if (card == value) {
                    if ((((Monster) card).getLevel() == 5 || ((Monster) card).getLevel() == 6 && !board.isZoneFull(Board.Zone.MONSTER))) {
                        if (board.howManyMonsters() != 0) {
                            for (Monster monster : myMonsters) {
                                if (monster.getLevel() < 4) {
                                    game.removeCardFromZone(monster, Board.Zone.MONSTER, game.getSelectedZoneIndex(), board);
                                    game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
                                    done = true;
                                    break;
                                }
                            }
                        }
                        if (done) {
                            game.removeCardFromZone(card, Board.Zone.HAND, game.getSelectedZoneIndex(), board);
                            game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
                            return;
                        }
                    } else if ((((Monster) card).getLevel() == 7 || ((Monster) card).getLevel() == 8) && !board.isZoneFull(Board.Zone.MONSTER)) {
                        if (board.howManyMonsters() > 1) {
                            for (Monster monster : myMonsters) {
                                if (monster.getLevel() < 4) {
                                    monsterTribute.add(monster);
                                }
                                if (monsterTribute.size() == 2) {
                                    for (int i = 0; i < 2; i++) {
                                        game.removeCardFromZone(monster, Board.Zone.MONSTER, game.getSelectedZoneIndex(), board);
                                        game.putCardInZone(monster, Board.Zone.GRAVE, null, rivalBoard);
                                    }
                                    done = true;
                                }
                            }
                        }
                        if (done) {
                            game.removeCardFromZone(card, Board.Zone.HAND, game.getSelectedZoneIndex(), board);
                            game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
                            return;
                        }
                    } else if (card instanceof GateGuardian && !board.isZoneFull(Board.Zone.MONSTER)) {
                        for (Monster monster : myMonsters) {
                            if (monster.getLevel() < 4) {
                                monsterTribute.add(monster);
                            }
                            if (monsterTribute.size() == 3) {
                                for (int i = 0; i < 3; i++) {
                                    game.removeCardFromZone(monster, Board.Zone.MONSTER, game.getSelectedZoneIndex(), board);
                                    game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
                                }
                                done = true;
                            }
                        }
                        if (done) {
                            game.removeCardFromZone(card, Board.Zone.HAND, game.getSelectedZoneIndex(), board);
                            game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
                            return;
                        }
                    } else if (card instanceof BeastKingBarbaros && !board.isZoneFull(Board.Zone.MONSTER)) {
                        game.removeCardFromZone(card, Board.Zone.HAND, game.getSelectedZoneIndex(), board);
                        game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.ATK, board);

                        //             }

                        ((Monster) card).decreaseATK(1900);
                        return;
                    } else if (card instanceof Monster && !board.isZoneFull(Board.Zone.MONSTER)) {//chera mige card az noe monstere?
                        game.removeCardFromZone(card, Board.Zone.HAND, game.getSelectedZoneIndex(), board);// selectedZoneIndex hamun firstEmptyZoneIndexe?
                        game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.HIDE_DEF, board);
                        return;
                    }
                }
            }
            for (Card value : spellAndTrapZone) {
                if (value instanceof CallOfTheHaunted) {
                    ((CallOfTheHaunted) value).actionForAI(game, card);
                    return;
                } else if (value instanceof MonsterReborn && !board.isZoneFull(Board.Zone.MONSTER)) {
                    if (board.getGrave() != null) {
                        for (Card value1 : board.getGrave()) {
                            if (card == value1 && card instanceof Monster) {
                                game.removeCardFromZone(card, Board.Zone.GRAVE, 0, board);
                                game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
                            }
                            break;
                        }
                    } else if (boardRival.getGrave() != null) {
                        for (Card value2 : boardRival.getGrave()) {
                            if (card == value2 && card instanceof Monster) {
                                game.removeCardFromZone(card, Board.Zone.GRAVE, 0, board);
                                game.putCardInZone(card, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
                                break;
                            }
                        }
                    }
                    return;
                } else if (!board.isZoneFull(Board.Zone.SPELL_AND_TRAP)) {
                    card = value;
                    game.removeCardFromZone(card, Board.Zone.HAND, game.getSelectedZoneIndex(), board);// selectedZoneIndex hamun firstEmptyZoneIndexe?
                    game.putCardInZone(card, Board.Zone.SPELL_AND_TRAP, Board.CardPosition.HIDE_DEF, board);
                    return;
                }
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
           if(myHand[i] instanceof Spell){
              if(myHand[i] instanceof HarpiesFeatherDuster){
                  if(rivalSpellAndTrap != null && rivalSpellAndTrap.length > 2){
                      game.setSelectedCard(myHand[i]);
                      game.activeEffect();}
              }

              if(myHand[i] instanceof DarkHole){
                  int sumOfRivalATK = 0;
                  int sumOfMyATK = 0;
                  if(rivalMonsters != null){
                  for (int j = 0; j < rivalMonsters.length; j++) {
                     sumOfRivalATK += rivalMonsters[i].getATK();
                  }
                  for (int j = 0; j < myMonsters.length; j++) {
                      sumOfMyATK += myMonsters[i].getATK();
                  }
                  if(sumOfMyATK < sumOfRivalATK){
                      game.setSelectedCard(myHand[i]);
                      game.activeEffect();
                  }
                 }
              }
              if(myHand[i] instanceof ChangeOfHeart){
                  int rivalBiggestATK = 0;
                  int rivalsBiggestDEF = 0;
                  int myBiggestATK = 0;
                  int myBiggestDEF = 0;
                  int rivalIndexFotATK = 0;
                  int rivalIndexForDEF;
                  for (int j = 0; j < Objects.requireNonNull(rivalMonsters).length; j++) {
                    if  (rivalMonsters[i].getATK() > rivalBiggestATK) {
                        rivalBiggestATK = rivalMonsters[i].getATK();
                        rivalIndexFotATK = i;
                    }
                    if(rivalMonsters[i].getDEF() > rivalsBiggestDEF){
                        rivalsBiggestDEF = rivalMonsters[i].getDEF();
                        rivalIndexForDEF = i;
                    }
                  }
                  for (int j = 0; j < myMonsters.length; j++) {
                      if(myMonsters[i].getATK() > myBiggestATK)  myBiggestATK = myMonsters[i].getATK();
                      if(myMonsters[i].getDEF() > myBiggestDEF) myBiggestDEF = myMonsters[i].getDEF();
                  }
                  if(rivalBiggestATK > rivalsBiggestDEF && rivalBiggestATK > myBiggestATK){
                      Card monster = board.getCardByIndexAndZone(rivalIndexFotATK, Board.Zone.MONSTER);
                      game.setSelectedCard(myHand[i]);
                      game.activeEffect();
                  }
              }
              if(myHand[i] instanceof PotOfGreed){
                  if(myHand.length <= 2)
                  game.setSelectedCard(myHand[i]);
                  game.activeEffect();
              }
              if(myHand[i] instanceof  Raigeki){
                  if(rivalMonsters != null && rivalMonsters.length > myMonsters.length){
                      game.setSelectedCard(myHand[i]);
                      game.activeEffect();
                  }
              }
              if(myHand[i] instanceof RingOfDefense){
                  boolean use = false;
                  for (Card card: rivalSpellAndTrap) {
                      if(card instanceof MagicCylinder)
                          use = true;
                  }
                  if(use){
                      game.setSelectedCard(myHand[i]);
                      game.activeEffect();
                  }
              }
              if(myHand[i] instanceof SupplySquad){
                  game.setSelectedCard(myHand[i]);
                  game.activeEffect();
              }
           }
           if(myHand[i] instanceof SwordsOfRevealingLight) {
               int faceDownMonsters = 0;
               if (rivalMonsters != null) {

                   for (int j = 0; j < rivalMonsters.length; j++) {
                       if (board.getCardPositions()[0][i] == Board.CardPosition.HIDE_DEF) faceDownMonsters++;
                   }
                   if (faceDownMonsters > 2){
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
