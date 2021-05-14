package model;

import model.card.Card;
import model.card.monster.*;
import model.card.spell.*;
import model.card.spell.fieldspells.FieldSpell;
import model.card.trap.TimeSeal;
import model.card.trap.TorrentialTribute;
import model.card.trap.Trap;
import model.person.AI;
import model.person.Player;
import model.person.User;
import view.CommandProcessor;
import view.Show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Game {
    private Card selectedCard;
    private Board.Zone selectedZone;
    private int selectedZoneIndex;
    private boolean isSelectedCardForRival = false; //TODO : tasir dadane in tooye tavabe
    private Player currentPlayer;
    private Player rival;
    private Player winner;
    private Player loser;
    private Phase currentPhase;
    private boolean hasSummonedOrSet; //TODO : tasir dadane in tooye tavabe
    private ArrayList<Card> setInThisPhase = new ArrayList<>();

    public Game(Player player1, Player player2, int round) {
        this.currentPlayer = player2;
        this.rival = player1;
        if (round == 1) {
            while (winner == null) {
                run(rival, currentPlayer);
            }
            Show.showGameMessage(endRound());

        } else {
            HashMap<Player, Integer> winnerAndLp = new HashMap<>();
            this.currentPlayer = player2;
            this.rival = player1;
            player1.getUser().setGameScore(0);
            player2.getUser().setGameScore(0);

            int currentRound = 0;
            while (getMatchWinner(winnerAndLp) == null) {
                currentRound++;
                Show.showImportantGameMessage("round " + currentRound);
                winner = null;
                player1.setLP(8000);
                player2.setLP(8000);
                while (winner == null) {
                    run(rival, currentPlayer);
                }
                Show.showImportantGameMessage(winner.getUser().getUsername() + " won the game and the score is: 1000-0");
                winner.getUser().increaseGameScore(1000);
                int winnerGameScore = winner.getUser().getGameScore();
                int loserGameScore = loser.getUser().getGameScore();
                if(winnerGameScore == 2000 && loserGameScore == 0) {
                    int maxLp = 0;
                    for (Map.Entry<Player, Integer> player : winnerAndLp.entrySet()) {
                        if (player.getKey() == winner && maxLp < player.getValue()) maxLp = player.getValue();
                    }
                    Show.showGameMessage(endMatch(maxLp)); }
                winnerAndLp.put(winner, winner.getLP());
            }
            winner = getMatchWinner(winnerAndLp);
            loser = (winner == player1) ? player2 : player1;
            int maxLp = 0;
            for (Map.Entry<Player, Integer> player : winnerAndLp.entrySet()) {
                if (player.getKey() == winner && maxLp < player.getValue()) maxLp = player.getValue();
            }
            Show.showGameMessage(endMatch(maxLp));
        }
        User.getAllUsers().remove(User.getUserByUsername("AI"));
    }

    private Player getMatchWinner(HashMap<Player, Integer> winnerAndLp) {
        int count1 = 0, count2 = 0;
        for (Map.Entry<Player, Integer> winner : winnerAndLp.entrySet()) {
            if (winner.getKey() == currentPlayer) count1++;
            else count2++;
        }
        if (count1 == 2) return currentPlayer;
        if (count2 == 2) return rival;
        return null;
    }

    private void run(Player me, Player rival) {
        setInThisPhase.clear();
        hasSummonedOrSet = false;
        this.currentPlayer = me;
        this.rival = rival;
        Show.showGameMessage("its " + me.getUser().getNickname() + "’s turn");
        currentPlayer.getBoard().noMonsterAttacked();
        Monster[] monsters = currentPlayer.getBoard().getMonsterZone();
        //herald of creation
        for (Monster monster : monsters)
            if (monster instanceof HeraldOfCreation) ((HeraldOfCreation) monster).setUsed(false);
        //texchanger
        for (Monster monster : monsters) if (monster instanceof Texchanger) ((Texchanger) monster).setAttacked(false);
        //spell absorbtion
        //ring of defence
        //negate attack
        selectedCard = null;
        setCurrentPhase(Phase.DRAW);
        Show.showGameMessage(drawCard());
        if (didSbWin()) return;
        setCurrentPhase(Phase.STANDBY);
        Board currBoard = currentPlayer.getBoard();
        Card[] cards = currBoard.getSpellAndTrapZone();
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] instanceof MessengerOfPeace) {
                if (currentPlayer instanceof AI) currentPlayer.decreaseLP(100);
                else if (CommandProcessor.yesNoQuestion("Do you want to to pay 100 LP to keep Messenger of peace?"))
                    currentPlayer.decreaseLP(100);
                else {
                    putCardInZone(cards[i], Board.Zone.GRAVE, null, currBoard);
                    removeCardFromZone(cards[i], Board.Zone.SPELL_AND_TRAP, i, currBoard);
                }
            }
        }
        setCurrentPhase(Phase.MAIN_1);
        if (currentPlayer instanceof AI) ((AI) currentPlayer).playMainPhase();
        else CommandProcessor.game();
        if (didSbWin()) return;
        setCurrentPhase(Phase.BATTLE);
        if (currentPlayer instanceof AI) ((AI) currentPlayer).playBattlePhase();
        else CommandProcessor.game();
        if (didSbWin()) return;
        setCurrentPhase(Phase.MAIN_2);
        if (currentPlayer instanceof AI) ((AI) currentPlayer).playMainPhase();
        else CommandProcessor.game();
        if (didSbWin()) return;
        setCurrentPhase(Phase.END);
    }

    public boolean didSbWin() {
        if (winner != null) {
            loser = (winner == currentPlayer) ? rival : currentPlayer;
            return true;
        }
        if (currentPlayer.getLP() == 0) {
            winner = rival;
            loser = currentPlayer;
            return true;
        }
        if (rival.getLP() == 0) {
            winner = currentPlayer;
            loser = rival;
            return true;
        }
        return false;
    }


    public String selectCard(Board.Zone zone, int index, boolean isSelectedCardForRival) {
        this.isSelectedCardForRival = isSelectedCardForRival;
        this.selectedZone = zone;
        this.selectedZoneIndex = index;
        if (isSelectedCardForRival) this.selectedCard = rival.getBoard().getCardByIndexAndZone(index, zone);
        else {
            this.selectedCard = currentPlayer.getBoard().getCardByIndexAndZone(index, zone);
            if (selectedCard instanceof HeraldOfCreation)
                if (CommandProcessor.yesNoQuestion("Do you want to use Herald of Creation?")) {
                    HeraldOfCreation heraldOfCreation = (HeraldOfCreation) selectedCard;
                    int[] tributes = CommandProcessor.getTribute(1, false);
                    if (tributes == null) return "cancelled";
                    else
                        Show.showGameMessage(heraldOfCreation.action(tributes[0], ), CommandProcessor.getCardName()));
                }
            if (selectedCard instanceof Scanner)
                if (CommandProcessor.yesNoQuestion("Do you want to use Scanner?")) {
                    Scanner scanner = (Scanner) selectedCard;
                    Show.showGameMessage("Enter the name of the monster witch you want to replace with Scanner");
                    Show.showGameMessage(scanner.setReplacement(CommandProcessor.getCardName(), index));
                }
        }
        if (selectedCard == null) return "no card found in the given position";
        else return "card selected";

    }

    public String deselect() {
        if (selectedCard == null) return "no card is selected yet";
        selectedCard = null;
        this.selectedZone = null;
        this.selectedZoneIndex = -1;
        return "card deselected";
    }

    private String drawCard() {
        if (currentPlayer.getBoard().getDeck().size() == 0 || currentPlayer.getBoard().isZoneFull(Board.Zone.HAND)) {
            winner = rival;
            return "You can't draw any card";
        }
        Card[] spellAndTrapZone = rival.getBoard().getSpellAndTrapZone();
        for (int i = 0; i < spellAndTrapZone.length; i++) {
            Card card = spellAndTrapZone[i];
            if (card instanceof TimeSeal) {
                removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, i, rival.getBoard());
                return "You can't draw card because enemy has Time seal.";
            }
        }
        Card drewCard = currentPlayer.getBoard().getCardByIndexAndZone(0, Board.Zone.DECK);
        removeCardFromZone(drewCard, Board.Zone.DECK, 0, currentPlayer.getBoard());
        putCardInZone(drewCard, Board.Zone.HAND, null, currentPlayer.getBoard());
        return "new card added to the hand : " + drewCard.getName();
    }

    private String endRound() {

        winner.getUser().increaseScore(1000);
        winner.getUser().increaseMoney(1000 + winner.getLP());
        loser.getUser().increaseMoney(100);

        return winner.getUser().getUsername() + " won the game and the score is: 1000-0";


    }

    private String endMatch(int maxLp) {
        winner.getUser().increaseScore(3000);
        winner.getUser().increaseMoney(3000 + (3 * maxLp));
        loser.getUser().increaseMoney(300);

        return winner.getUser().getUsername() + "won the whole match with score: " + winner.getUser().getGameScore() + "-" + loser.getUser().getGameScore();
    }

    public String summon() {
        if (selectedCard == null) return "no card is selected yet";
        if (selectedZone != Board.Zone.HAND || !(selectedCard instanceof Monster)) return "you can’t summon this card";
        if (currentPhase != Phase.MAIN_1 && currentPhase != Phase.MAIN_2) return "action not allowed in this phase";
        if (getCurrentPlayer().getBoard().isZoneFull(Board.Zone.MONSTER)) return "monster card zone is full";
        if (hasSummonedOrSet) return "you already summoned/set on this turn";
        if (selectedCard instanceof specialSummonable) {
            if (selectedCard instanceof BeastKingBarbaros) {
                if (CommandProcessor.yesNoQuestion("do you want to normally summon" + selectedCard.getName() + "? (this will make it's ATK 1900)"))
                    ((BeastKingBarbaros) selectedCard).normalSummonOrSet();
                else if (CommandProcessor.yesNoQuestion("do tou want to special summon it?"))
                    return specialSummon();
                else return "summon cancelled";
            } else if (CommandProcessor.yesNoQuestion("you can't normally set this. do you want to special summon it?"))
                return specialSummon();
        }
        int numberOfTributes = switch (((Monster) selectedCard).getLevel()) {
            case 5, 6 -> 1;
            case 7, 8, 9 -> 2;
            default -> 0;
        };
        if (numberOfTributes != 0) {
            if (currentPlayer.getBoard().getNumberOfMonstersInMonsterZone() < numberOfTributes)
                return "there are not enough cards for tribute";
            int[] tributeIndexes = CommandProcessor.getTribute(numberOfTributes, true);
            if (tributeIndexes == null) return "summon cancelled";
            specialSummonable.tribute(tributeIndexes, this);
        }
        removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
        putCardInZone(selectedCard, Board.Zone.MONSTER, Board.CardPosition.ATK, currentPlayer.getBoard());
        hasSummonedOrSet = true;
        return "summoned successfully";
    }

    private String specialSummon() {
        if (selectedCard instanceof BeastKingBarbaros) {
            if (currentPlayer.getBoard().getNumberOfMonstersInMonsterZone() < 3)
                return "there is no way you could special summon this monster";
            return ((BeastKingBarbaros) selectedCard).specialSummon(CommandProcessor.getTribute(3, true), selectedZoneIndex);
        } else if (selectedCard instanceof GateGuardian) {
            if (currentPlayer.getBoard().getNumberOfMonstersInMonsterZone() < 3)
                return "there is no way you could special summon this monster";
            return ((GateGuardian) selectedCard).specialSummon(CommandProcessor.getTribute(3, true), selectedZoneIndex);
        } else if (selectedCard instanceof TheTricky) {
            int numberOfHandCards = 0;
            for (Card card : currentPlayer.getBoard().getHand())
                if (card != null && card != selectedCard) numberOfHandCards++;
            if (numberOfHandCards == 0) return "there is no way you could special summon this monster";
            else {
                int[] index;
                if (currentPlayer instanceof AI) {
                    Card[] cards = currentPlayer.getBoard().getHand();
                    for (int i = 0; i < cards.length; i++) if (cards[i] != null) index = new int[]{i};
                }
                index = CommandProcessor.getTribute(1, false);
                if (index == null) return "special summon cancelled";
                return ((TheTricky) selectedCard).specialSummon(index[0], selectedZoneIndex);
            }
        } else return "this card can not be special summoned";
    }

    private String ritualSummon() {
        //TODO
        return "this card can not be ritual summoned";
    }

    public String set() {
        if (selectedCard == null) return "no card is selected yet";
        if (selectedZone != Board.Zone.HAND) return "you can’t set this card";
        if (currentPhase != Phase.MAIN_1 && currentPhase != Phase.MAIN_2)
            return "you can’t do this action in this phase";
        if (selectedCard instanceof Monster) {
            if (currentPlayer.getBoard().isZoneFull(Board.Zone.MONSTER)) return "monster card zone is full";
            if (hasSummonedOrSet) return "you already summoned/set on this turn";
            if (selectedCard instanceof specialSummonable) {
                if (selectedCard instanceof BeastKingBarbaros) {
                    if (CommandProcessor.yesNoQuestion("do you want to normally set" + selectedCard.getName() + "? (this will make it's ATK 1900)"))
                        ((BeastKingBarbaros) selectedCard).normalSummonOrSet();
                    else if (CommandProcessor.yesNoQuestion("do tou want to special summon it?"))
                        return specialSummon();
                    else return "set cancelled";
                } else if (CommandProcessor.yesNoQuestion("you can't normally set this. do you want to special summon?"))
                    return specialSummon();
            }
            int numberOfTributes = switch (((Monster) selectedCard).getLevel()) {
                case 5, 6 -> 1;
                case 7, 8, 9 -> 2;
                default -> 0;
            };
            if (numberOfTributes != 0) {
                if (currentPlayer.getBoard().getNumberOfMonstersInMonsterZone() < numberOfTributes)
                    return "there are not enough cards for tribute";
                int[] tributeIndexes = CommandProcessor.getTribute(numberOfTributes, true);
                if (tributeIndexes == null) return "set cancelled";
                specialSummonable.tribute(tributeIndexes, this);
            }
            removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
            putCardInZone(selectedCard, Board.Zone.MONSTER, Board.CardPosition.HIDE_DEF, currentPlayer.getBoard());
            hasSummonedOrSet = true;
            setInThisPhase.add(selectedCard);
            return "set successfully";
        }
        if (selectedCard instanceof Spell || selectedCard instanceof Trap) {
            if (currentPlayer.getBoard().isZoneFull(Board.Zone.SPELL_AND_TRAP))
                return "spell card zone is full";
            removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
            putCardInZone(selectedCard, Board.Zone.SPELL_AND_TRAP, Board.CardPosition.HIDE_DEF, currentPlayer.getBoard());
            hasSummonedOrSet = true;
            return "set successfully";
        }
        return "not successful";
    }

    public String flipSummon() {
        if (selectedCard == null) return "no card is selected yet";
        if (selectedZone != Board.Zone.MONSTER) return "you can’t change this card position";
        if (getCurrentPhase() != Phase.MAIN_1 && getCurrentPhase() != Phase.MAIN_2)
            return "you can’t do this action in this phase";
        if (currentPlayer.getBoard().getCardPositions()[0][selectedZoneIndex] != Board.CardPosition.HIDE_DEF
                || setInThisPhase.contains(selectedCard))
            return "you can’t flip summon this card";
        currentPlayer.getBoard().getCardPositions()[0][selectedZoneIndex] = Board.CardPosition.ATK;
        return "flip summoned successfully";
    }

    public String attack(int monsterNumber) {
        if (selectedCard == null) return "no card is selected yet";
        if (selectedZone != Board.Zone.MONSTER) return "you can’t attack with this card";
        Monster attacking = (Monster) selectedCard;
        if (attacking instanceof TheCalculator) ((TheCalculator) attacking).action();
        if (currentPhase != Phase.BATTLE) return "you can’t do this action in this phase";
        if (currentPlayer.getBoard().getDidMonsterAttack()[selectedZoneIndex]) return "this card already attacked";
        if (monsterNumber == -1) return attackDirectly();
        Monster attacked = rival.getBoard().getMonsterZone()[monsterNumber];
        if (attacked instanceof Texchanger && !((Texchanger) attacked).isAttacked()) {
            changeTurn();
            if (currentPlayer instanceof AI) {
                for (Card card : currentPlayer.getBoard().getDeck())
                    if (card instanceof Monster && ((Monster) card).getMonsterType() == Monster.MonsterType.CYBERSE) {
                        Show.showGameMessage(((Texchanger) attacked).specialSummonACyberseMonster(card, Board.Zone.DECK, 0));
                        break;
                    }
                changeTurn();
                return "texchanger cancelled the attack";
            }
            Show.showGameMessage("choose zone for the cyberse monster that you want to tribute (deck, graveyard or hand)");
            Board.Zone zone = CommandProcessor.getZone();
            int cardIndex = -1;
            Card card = null;
            if (zone == Board.Zone.HAND) {
                Show.showGameMessage("choose hand index");
                cardIndex = CommandProcessor.getCardIndex();
                card = currentPlayer.getBoard().getHand()[cardIndex];
            } else if (zone == Board.Zone.DECK || zone == Board.Zone.GRAVE) {
                Show.showGameMessage("Enter the card's name");
                cardIndex = currentPlayer.getBoard().getIndexOfCard(CommandProcessor.getCardName(), zone);
                card = currentPlayer.getBoard().getCardByIndexAndZone(cardIndex, zone);
            } else Show.showGameMessage("zone is not valid");
            if (card != null)
                Show.showGameMessage(((Texchanger) attacked).specialSummonACyberseMonster(card, zone, cardIndex));
            changeTurn();
            return "texchanger cancelled the attack";
        }
        if (attacked == null) return "there is no card to attack here";
        if (attacked instanceof CommandKnight && ((CommandKnight) attacked).hasDoneAction()) {
            for (Monster monster : rival.getBoard().getMonsterZone()) {
                if (!(monster instanceof CommandKnight) && monster != null)
                    return "You can't attack command knight while other monsters are available";
            }
        }
        int deltaLP;
        Board.CardPosition attackedPosition = rival.getBoard().getCardPositions()[0][monsterNumber];
        currentPlayer.getBoard().getDidMonsterAttack()[selectedZoneIndex] = true;
        if (attacked instanceof Suijin && !((Suijin) attacked).isUsedUp() && attackedPosition != Board.CardPosition.HIDE_DEF)
            return ((Suijin) attacked).action(attackedPosition);
        if (attackedPosition == Board.CardPosition.ATK) {
            deltaLP = attacking.getATK() - attacked.getATK();
            if (deltaLP > 0) {
                if (!(attacked instanceof Marshmallon)) {
                    removeCardFromZone(attacked, Board.Zone.MONSTER, monsterNumber, rival.getBoard());
                    putCardInZone(attacked, Board.Zone.GRAVE, null, rival.getBoard());
                    if (attacked instanceof GraveYardEffectMonster)
                        return ((GraveYardEffectMonster) attacked).action(monsterNumber);
                    rival.decreaseLP(deltaLP);
                    return "your opponent’s monster is destroyed and your opponent receives" + deltaLP + " battle damage";
                } else {
                    rival.decreaseLP(deltaLP);
                    return "you can't destroy marshmallon and your opponent receives" + deltaLP + " battle damage";
                }
            } else if (deltaLP == 0) {
                removeCardFromZone(attacking, Board.Zone.MONSTER, selectedZoneIndex, currentPlayer.getBoard());
                putCardInZone(attacking, Board.Zone.GRAVE, null, currentPlayer.getBoard());
                if (attacked instanceof Marshmallon)
                    return "Your monster card is destroyed. you can't destroy marshmallon";
                removeCardFromZone(attacked, Board.Zone.MONSTER, monsterNumber, rival.getBoard());
                putCardInZone(attacked, Board.Zone.GRAVE, null, rival.getBoard());
                return "both you and your opponent monster cards are destroyed and no one receives damage";
            } else {
                deltaLP *= -1;
                currentPlayer.decreaseLP(deltaLP);
                removeCardFromZone(attacking, Board.Zone.MONSTER, selectedZoneIndex, currentPlayer.getBoard());
                putCardInZone(attacking, Board.Zone.GRAVE, null, currentPlayer.getBoard());
                return "Your monster card is destroyed and you received " + deltaLP + " battle damage";
            }
        } else {
            deltaLP = attacking.getATK() - attacked.getDEF();
            String result;
            if (deltaLP > 0) {
                if (!(attacked instanceof Marshmallon)) {
                    removeCardFromZone(attacked, Board.Zone.MONSTER, monsterNumber, rival.getBoard());
                    putCardInZone(attacked, Board.Zone.GRAVE, null, rival.getBoard());
                    if (attacked instanceof GraveYardEffectMonster)
                        return ((GraveYardEffectMonster) attacked).action(monsterNumber);
                    result = "the defense position monster is destroyed";
                } else result = "you can't destroy marshmallon";
            } else if (deltaLP == 0) result = "no card is destroyed";
            else {
                deltaLP *= -1;
                currentPlayer.decreaseLP(deltaLP);
                result = "no card is destroyed and you received " + deltaLP + " battle damage";
            }
            if (attackedPosition == Board.CardPosition.HIDE_DEF) {
                result = "opponent’s monster card was " + attacked.getName() + " and " + result;
                if (attacked instanceof Marshmallon) return ((Marshmallon) attacked).action(monsterNumber);
            }

            return result;
        }
    }

    public String attackDirectly() {
        for (Monster monster : rival.getBoard().getMonsterZone()) {
            //what other reasons can there be?
            if (monster != null) return "you can’t attack the opponent directly";
        }
        int lp = ((Monster) selectedCard).getATK();
        rival.decreaseLP(lp);
        return "you opponent receives " + lp + " battle damage";
    }

    public String activeEffect() {
        if (selectedCard == null) return "no card is selected yet";
        if (!(selectedCard instanceof Spell)) return "activate effect is only for spell cards.";
        if (getCurrentPhase() != Phase.MAIN_1 && getCurrentPhase() != Phase.MAIN_2)
            return "you can’t activate an effect on this turn";
        if (((Spell) selectedCard).getCondition() == Spell.Conditions.ACTIVATED)
            return "you have already activated this card";
        if (selectedZone == Board.Zone.HAND && currentPlayer.getBoard().isZoneFull(Board.Zone.SPELL_AND_TRAP)
                && ((Spell) selectedCard).getSpellType() != Spell.SpellType.FIELD)
            return "spell card zone is full";
        //TODO: preparation stuff
        if(selectedCard instanceof HarpiesFeatherDuster) ((HarpiesFeatherDuster) selectedCard).action();
        if(selectedCard instanceof DarkHole) ((DarkHole) selectedCard).action();
        if(selectedCard instanceof PotOfGreed) ((PotOfGreed) selectedCard).action();
        if(selectedCard instanceof  Raigeki) ((Raigeki) selectedCard).action();
        if(selectedCard instanceof RingOfDefense) ((RingOfDefense) selectedCard).action();
        if(selectedCard instanceof SupplySquad) ((SupplySquad) selectedCard).action();
        if(selectedCard instanceof SwordsOfRevealingLight) ((SwordsOfRevealingLight) selectedCard).action();
        //if(selectedCard instanceof ChangeOfHeart) ((ChangeOfHeart) selectedCard).action()
        if (((Spell) selectedCard).getSpellType() == Spell.SpellType.FIELD) {
            if (currentPlayer.getBoard().isZoneFull(Board.Zone.FIELD_SPELL)) {
                putCardInZone(currentPlayer.getBoard().getFieldSpell(), Board.Zone.GRAVE, null, currentPlayer.getBoard());
                removeCardFromZone(currentPlayer.getBoard().getFieldSpell(), Board.Zone.FIELD_SPELL, 0, currentPlayer.getBoard());
            }
            putCardInZone(selectedCard, Board.Zone.FIELD_SPELL, Board.CardPosition.ACTIVATED, currentPlayer.getBoard());
            removeCardFromZone(selectedCard, Board.Zone.HAND,selectedZoneIndex,getCurrentPlayer().getBoard());
            return "spell activated";
        }
        putCardInZone(selectedCard, Board.Zone.SPELL_AND_TRAP, Board.CardPosition.ACTIVATED, getCurrentPlayer().getBoard());
        removeCardFromZone(selectedCard, Board.Zone.HAND,selectedZoneIndex,getCurrentPlayer().getBoard());
        return "spell activated";

    }

    public void surrendered() {
        winner = rival;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getRival() {
        return rival;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public Board.Zone getSelectedZone() {
        return selectedZone;
    }

    public int getSelectedZoneIndex() {
        return selectedZoneIndex;
    }

    public void putCardInZone(Card card, Board.Zone zone, Board.CardPosition position, Board board) {
        switch (zone) {
            case HAND -> board.getHand()[board.getFirstEmptyIndexOfZone(Board.Zone.HAND)] = card;
            case GRAVE -> board.getGrave().add(card);
            case DECK -> board.getDeck().add(card);
            case MONSTER -> {
                int index = board.getFirstEmptyIndexOfZone(Board.Zone.MONSTER);
                board.getMonsterZone()[index] = (Monster) card;
                board.getCardPositions()[0][index] = position;
                if (position != Board.CardPosition.HIDE_DEF && card instanceof CommandKnight)
                    if (!((CommandKnight) card).hasDoneAction()) ((CommandKnight) card).action();
                Card[] spellAndTrapZone = rival.getBoard().getSpellAndTrapZone();
                for (Card c : spellAndTrapZone) {
                    if (c instanceof TorrentialTribute) {
                        if (rival.askPlayerToActive(c))
                            ((TorrentialTribute) c).action(this);
                        return;
                    }
                }
                spellAndTrapZone = currentPlayer.getBoard().getSpellAndTrapZone();
                for (Card c : spellAndTrapZone) {
                    if (c instanceof TorrentialTribute) {
                        ((TorrentialTribute) c).action(this);
                        return;
                    }
                }
            }
            case FIELD_SPELL -> {
                if (position == Board.CardPosition.ACTIVATED)
                    ((FieldSpell) card).action(false);
                removeCardFromZone(currentPlayer.getBoard().getFieldSpell(), Board.Zone.FIELD_SPELL, 0, currentPlayer.getBoard());
                removeCardFromZone(rival.getBoard().getFieldSpell(), Board.Zone.FIELD_SPELL, 0, rival.getBoard());
                board.setFieldSpell(((FieldSpell) card));
            }
            case SPELL_AND_TRAP -> {
                int index = board.getFirstEmptyIndexOfZone(Board.Zone.SPELL_AND_TRAP);
                board.getSpellAndTrapZone()[index] = card;
                board.getCardPositions()[1][index] = position;
            }
        }
    }

    public void removeCardFromZone(Card card, Board.Zone zone, int zoneIndex, Board board) {
        switch (zone) {
            case MONSTER -> {
                board.getMonsterZone()[zoneIndex] = null;
                if (card instanceof CommandKnight)
                    if (((CommandKnight) card).hasDoneAction()) ((CommandKnight) card).undoAction();
                for (Card spell : board.getSpellAndTrapZone())
                    if (spell instanceof SupplySquad)
                        ((SupplySquad) spell).action();
            }
            case SPELL_AND_TRAP -> board.getSpellAndTrapZone()[zoneIndex] = null;
            case HAND -> board.getHand()[zoneIndex] = null;
            case FIELD_SPELL -> {
                if (card != null && ((FieldSpell) card).isActivated()) ((FieldSpell) card).action(true);
                board.setFieldSpell(null);
            }
            default -> {
                ArrayList<Card> thisZone = (zone == Board.Zone.GRAVE) ? board.getGrave() : board.getDeck();
                for (int i = thisZone.size() - 1; i >= 0; i--)
                    if (thisZone.get(i) == card) {
                        thisZone.remove(i);
                        break;
                    }
            }
        }
    }


    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
        Show.showPhase(currentPhase);
    }

    public void changeTurn() {
        Show.showImportantGameMessage("it's " + rival.getUser().getNickname() + "’s turn");
        Player temp = currentPlayer;
        currentPlayer = rival;
        rival = temp;
    }

    public String showSelectedCard() {
        if (selectedCard == null) return "no card is selected yet";
        if (isSelectedCardForRival) {
            if ((selectedZone == Board.Zone.MONSTER && currentPlayer.getBoard().getCardPositions()[0][selectedZoneIndex] == Board.CardPosition.HIDE_DEF) ||
                    (selectedZone == Board.Zone.SPELL_AND_TRAP && currentPlayer.getBoard().getCardPositions()[1][selectedZoneIndex] == Board.CardPosition.HIDE_DEF))
                return "card is not visible";
        }
        Show.showSingleCard(selectedCard.getName());
        return "";
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setSelectedZone(Board.Zone selectedZone) {
        this.selectedZone = selectedZone;
    }

    public void setSelectedZoneIndex(int selectedZoneIndex) {
        this.selectedZoneIndex = selectedZoneIndex;
    }


}
