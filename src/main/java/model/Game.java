package model;

import model.card.Activatable;
import model.card.Card;
import model.card.monster.*;
import model.card.spell.MessengerOfPeace;
import model.card.spell.Spell;
import model.card.spell.SupplySquad;
import model.card.spell.fieldspells.FieldSpell;
import model.card.trap.*;
import model.person.AI;
import model.person.Player;
import model.person.User;
import view.CommandProcessor;
import view.Show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Game {
    private final int round;
    private Card selectedCard;
    private Board.Zone selectedZone;
    private int selectedZoneIndex;
    private boolean isSelectedCardForRival = false;
    private Player currentPlayer;
    private Player rival;
    private Player winner;
    private Player loser;
    private Phase currentPhase;
    private boolean hasSummonedOrSet;
    private final ArrayList<Card> setInThisTurn = new ArrayList<>();
    private final ArrayList<Card> positionChangedInThisTurn = new ArrayList<>();
    private boolean hasAttackDirectInThisTurn;

    public Game(Player player1, Player player2, int round) {
        this.currentPlayer = player2;
        this.rival = player1;
        this.round = round;

    }

    public void play() {
        if (round == 1) {
            while (winner == null) {
                run(rival, currentPlayer);
            }
            Show.showGameMessage(endRound());

        } else {
            HashMap<Player, Integer> winnerAndLp = new HashMap<>();
            int currentRound = 0;
            while (getMatchWinner(winnerAndLp) == null) {
                currentRound++;
                Show.showImportantGameMessage("round " + currentRound);
                winner = null;
                currentPlayer.setLP(8000);
                rival.setLP(8000);
                while (winner == null) {
                    run(rival, currentPlayer);
                }
                Show.showImportantGameMessage(winner.getUser().getUsername() + " won the game and the score is: 1000-0");
                winner.increaseGameScore(1000);
                winnerAndLp.put(winner, winner.getLP());
            }
            winner = getMatchWinner(winnerAndLp);
            loser = (winner == currentPlayer) ? rival : currentPlayer;
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
        hasAttackDirectInThisTurn = false;
        setInThisTurn.clear();
        positionChangedInThisTurn.clear();
        hasSummonedOrSet = false;
        this.currentPlayer = me;
        this.rival = rival;
        Show.showGameMessage("its " + me.getUser().getNickname() + "’s turn");
        Show.showBoard();
        currentPlayer.getBoard().noMonsterAttacked();
        Monster[] monsters = currentPlayer.getBoard().getMonsterZone();
        //herald of creation
        for (Monster monster : monsters)
            if (monster instanceof HeraldOfCreation) ((HeraldOfCreation) monster).setUsed(false);
        //texchanger
        for (Monster monster : monsters) if (monster instanceof Texchanger) ((Texchanger) monster).setUsed(false);
        //scanner
        Scanner.undo();
        //Supply Squad
        for (Card card : currentPlayer.getBoard().getSpellAndTrapZone())
            if (card instanceof SupplySquad) ((SupplySquad) card).setUsedInThisTurn(false);
        for (Card card : rival.getBoard().getSpellAndTrapZone())
            if (card instanceof SupplySquad) ((SupplySquad) card).setUsedInThisTurn(false);
        setCurrentPhase(Phase.DRAW);
        Show.showGameMessage(drawCard());
        Show.showBoard();
        if (didSbWin()) return;
        setCurrentPhase(Phase.STANDBY);
        doStandByActions();
        Show.showBoard();
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
        Player player = isSelectedCardForRival ? rival : currentPlayer;
        selectedCard = player.getBoard().getCardByIndexAndZone(index, zone);
        if (selectedCard == null) return "no card found in the given position";
        if (!isSelectedCardForRival && ((selectedZone == Board.Zone.MONSTER && selectedCard instanceof Activatable &&
                (currentPhase == Phase.MAIN_1 || currentPhase == Phase.MAIN_2)) ||
                (selectedZone == Board.Zone.SPELL_AND_TRAP && selectedCard instanceof Trap && selectedCard instanceof Activatable
                        && !setInThisTurn.contains(selectedCard) && !MirageDragon.isOn(false))))
            if (CommandProcessor.yesNoQuestion("do you want to use " + selectedCard.getName() + " effect?"))
                Show.showGameMessage(((Activatable) selectedCard).action());
        return "card selected";

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
        if (!MirageDragon.isOn(true)) {
            for (int i = 0; i < spellAndTrapZone.length; i++) {
                Card card = spellAndTrapZone[i];
                if (card instanceof TimeSeal) {
                    removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, i, rival.getBoard());
                    putCardInZone(card, Board.Zone.GRAVE, null, rival.getBoard());
                    return "You can't draw card because enemy has Time seal.";
                }
            }
        }
        Card drewCard = currentPlayer.getBoard().getCardByIndexAndZone(currentPlayer.getBoard().getDeck().size() - 1, Board.Zone.DECK);
        removeCardFromZone(drewCard, Board.Zone.DECK, 0, currentPlayer.getBoard());
        putCardInZone(drewCard, Board.Zone.HAND, null, currentPlayer.getBoard());
        return "new card added to the hand : " + drewCard.getName();
    }

    private void doStandByActions() {
        Board board = currentPlayer.getBoard();
        Card[] cards = board.getSpellAndTrapZone();
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] instanceof MessengerOfPeace) {
                if (!(currentPlayer instanceof AI && currentPlayer.getLP() <= 900) &&
                        CommandProcessor.yesNoQuestion("Do you want to to pay 100 LP to keep Messenger of peace?"))
                    currentPlayer.decreaseLP(100);
                else {
                    putCardInZone(cards[i], Board.Zone.GRAVE, null, board);
                    removeCardFromZone(cards[i], Board.Zone.SPELL_AND_TRAP, i, board);
                }
            }
        }
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

        return winner.getUser().getUsername() + "won the whole match with score: " + winner.getGameScore() + "-" + loser.getGameScore();
    }

    public String summon() {
        if (selectedCard == null) return "no card is selected yet";
        if (isSelectedCardForRival) return "this action is not available on rival cards";
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
        if (selectedCard instanceof RitualMonster) return "you can't normally summon a ritual monster";
        int numberOfTributes = switch (((Monster) selectedCard).getLevel()) {
            case 5, 6 -> 1;
            case 7, 8, 9 -> 2;
            default -> 0;
        };
        if (numberOfTributes != 0) {
            if (currentPlayer.getBoard().getNumberOfMonsters() < numberOfTributes)
                return "there are not enough cards for tribute";
            int[] tributeIndexes = CommandProcessor.getTribute(numberOfTributes, true);
            if (tributeIndexes == null) return "summon cancelled";
            specialSummonable.tribute(tributeIndexes, this);
        }
        removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
        putCardInZone(selectedCard, Board.Zone.MONSTER, Board.CardPosition.ATK, currentPlayer.getBoard());
        hasSummonedOrSet = true;
        positionChangedInThisTurn.add(selectedCard);
        if (selectedCard instanceof Terratiger) {
            Show.showGameMessage("summoned successfully");
            return ((Terratiger) selectedCard).action();
        }
        if (checkForTraps("summon")) return "rival's trap activated";
        return "summoned successfully";
    }


    private String specialSummon() {
        if (selectedCard instanceof BeastKingBarbaros) {
            if (currentPlayer.getBoard().getNumberOfMonsters() < 3)
                return "there is no way you could special summon this monster";
            return ((BeastKingBarbaros) selectedCard).specialSummon(CommandProcessor.getTribute(3, true), selectedZoneIndex);
        } else if (selectedCard instanceof GateGuardian) {
            if (currentPlayer.getBoard().getNumberOfMonsters() < 3)
                return "there is no way you could special summon this monster";
            return ((GateGuardian) selectedCard).specialSummon(CommandProcessor.getTribute(3, true), selectedZoneIndex);
        } else if (selectedCard instanceof TheTricky) {
            int numberOfHandCards = 0;
            for (Card card : currentPlayer.getBoard().getHand())
                if (card != null && card != selectedCard) numberOfHandCards++;
            if (numberOfHandCards == 0) return "there is no way you could special summon this monster";
            else {
                int[] index;

                index = CommandProcessor.getTribute(1, false);
                if (index == null) return "special summon cancelled";
                return ((TheTricky) selectedCard).specialSummon(index[0], selectedZoneIndex);
            }
        } else return "this card can not be special summoned";
    }

    public String set() {
        if (selectedCard == null) return "no card is selected yet";
        if (isSelectedCardForRival) return "this action is not available on rival cards";
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
                    else if (CommandProcessor.yesNoQuestion("do you want to special summon it?"))
                        return specialSummon();
                    else return "set cancelled";
                } else if (CommandProcessor.yesNoQuestion("you can't normally set this. do you want to special summon?"))
                    return specialSummon();
            }
            if (selectedCard instanceof RitualMonster) return "you can't normally set a ritual monster";
            int numberOfTributes = switch (((Monster) selectedCard).getLevel()) {
                case 5, 6 -> 1;
                case 7, 8, 9 -> 2;
                default -> 0;
            };
            if (numberOfTributes != 0) {
                if (currentPlayer.getBoard().getNumberOfMonsters() < numberOfTributes)
                    return "there are not enough cards for tribute";
                int[] tributeIndexes = CommandProcessor.getTribute(numberOfTributes, true);
                if (tributeIndexes == null) return "set cancelled";
                specialSummonable.tribute(tributeIndexes, this);
            }
            removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
            putCardInZone(selectedCard, Board.Zone.MONSTER, Board.CardPosition.HIDE_DEF, currentPlayer.getBoard());
            hasSummonedOrSet = true;
            setInThisTurn.add(selectedCard);
            positionChangedInThisTurn.add(selectedCard);
        } else {
            if (selectedCard instanceof Spell) {
                switch (((Spell) selectedCard).getSpellType()) {
                    case RITUAL, NORMAL -> {
                        return "you can't set this. just activate it";
                    }
                }
            }
            if (selectedCard instanceof FieldSpell)
                putCardInZone(selectedCard, Board.Zone.FIELD_SPELL, Board.CardPosition.HIDE_DEF, currentPlayer.getBoard());
            else if (currentPlayer.getBoard().isZoneFull(Board.Zone.SPELL_AND_TRAP)) return "spell card zone is full";
            else
                putCardInZone(selectedCard, Board.Zone.SPELL_AND_TRAP, Board.CardPosition.HIDE_DEF, currentPlayer.getBoard());
            removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
            hasSummonedOrSet = true;
            setInThisTurn.add(selectedCard);
        }
        return "set successfully";
    }

    public String flipSummon() {
        if (selectedCard == null) return "no card is selected yet";
        if (isSelectedCardForRival) return "this action is not available on rival cards";
        if (selectedZone != Board.Zone.MONSTER) return "you can’t change this card position";
        if (getCurrentPhase() != Phase.MAIN_1 && getCurrentPhase() != Phase.MAIN_2)
            return "you can’t do this action in this phase";
        if (currentPlayer.getBoard().getCardPositions()[0][selectedZoneIndex] != Board.CardPosition.HIDE_DEF
                || setInThisTurn.contains(selectedCard))
            return "you can’t flip summon this card";
        currentPlayer.getBoard().getCardPositions()[0][selectedZoneIndex] = Board.CardPosition.ATK;
        positionChangedInThisTurn.add(selectedCard);
        if (selectedCard instanceof ManEaterBug) {
            Show.showGameMessage("flip summoned successfully");
            return ((ManEaterBug) selectedCard).action();
        }
        if (checkForTraps("summon")) return "rival's trap activated";
        return "flip summoned successfully";
    }

    public String setMonsterPosition(boolean isAttack) {
        if (selectedCard == null) return "no card is selected yet";
        if (selectedZone != Board.Zone.MONSTER) return "you can’t change this card position";
        if (currentPhase != Phase.MAIN_1 && currentPhase != Phase.MAIN_2)
            return "you can’t do this action in this phase";
        Board.CardPosition position = isAttack ? Board.CardPosition.ATK : Board.CardPosition.REVEAL_DEF;
        if (currentPlayer.getBoard().getCardPositions()[0][selectedZoneIndex] == position)
            return "this card is already in the wanted position";
        if (positionChangedInThisTurn.contains(selectedCard))
            return "you already changed this card position in this turn";
        currentPlayer.getBoard().getCardPositions()[0][selectedZoneIndex] = position;
        positionChangedInThisTurn.add(selectedCard);
        return "monster card position changed successfully";
    }

    public String attack(int monsterNumber) {
        if (selectedCard == null) return "no card is selected yet";
        if (isSelectedCardForRival) return "this action is not available on rival cards";
        if (selectedZone != Board.Zone.MONSTER) return "you can’t attack with this card";
        Monster attacking = (Monster) selectedCard;
        if (attacking instanceof TheCalculator) ((TheCalculator) attacking).action();
        if (currentPhase != Phase.BATTLE) return "you can’t do this action in this phase";
        if (currentPlayer.getBoard().getDidMonsterAttack()[selectedZoneIndex]) return "this card already attacked";
        boolean trapErrorHappened = checkForTraps("attack");
        if (trapErrorHappened) return "rival's trap was activated. attack is cancelled";
        if (MessengerOfPeace.canBeActivated()) return MessengerOfPeace.getActivationMessage();
        if (monsterNumber == -1) return attackDirectly();
        Monster attacked = rival.getBoard().getMonsterZone()[monsterNumber];
        if (attacked == null) return "there is no card to attack here";
        if (attacked instanceof CommandKnight && ((CommandKnight) attacked).hasDoneAction()) {
            for (Monster monster : rival.getBoard().getMonsterZone()) {
                if (!(monster instanceof CommandKnight) && monster != null)
                    return "You can't attack command knight while other monsters are available";
            }
        } else if (attacked instanceof Texchanger && !((Texchanger) attacked).isUsed()) {
            changeTurn();
            if (CommandProcessor.yesNoQuestion("texchanger is activated. do you want to special summon a cyberse monster?"))
                Show.showGameMessage(((Texchanger) attacked).doAction());
            changeTurn();
            return "attack was cancelled (you attacked texchanger)";
        }
        int deltaLP;
        Board.CardPosition attackedPosition = rival.getBoard().getCardPositions()[0][monsterNumber];
        currentPlayer.getBoard().getDidMonsterAttack()[selectedZoneIndex] = true;
        if (attacked instanceof Suijin && !((Suijin) attacked).isUsedUp() && attackedPosition != Board.CardPosition.HIDE_DEF) {
            changeTurn();
            String result = null;
            if (CommandProcessor.yesNoQuestion("do you want to activate suijin?"))
                result = ((Suijin) attacked).action(attackedPosition);
            changeTurn();
            if (result != null) return result;
        }
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

    private boolean checkForTraps(String state) {
        if (MirageDragon.isOn(true)) return false;
        boolean error = false;
        boolean trapExists;
        Card[] spellAndTrapZone = rival.getBoard().getSpellAndTrapZone();
        for (int i = 0; i < spellAndTrapZone.length; i++) {
            Card card = spellAndTrapZone[i];
            if (card instanceof Trap) {
                if (state.equals("attack"))
                    trapExists = card instanceof MagicCylinder || card instanceof NegateAttack || card instanceof MirrorForce;
                else if (state.equals("summon"))
                    trapExists = (card instanceof TrapHole && ((Monster) selectedCard).getATK() >= 1000) || card instanceof TorrentialTribute;
                else trapExists = card instanceof MagicJammer;
                if (trapExists) {
                    changeTurn();
                    if (CommandProcessor.yesNoQuestion("do you want to activate " + card.getName())) {
                        Show.showGameMessage(((Trap) card).action(i));
                        error = ((Trap) card).isActivated();
                    }
                    changeTurn();
                }
            }
        }
        return error;
    }

    public String attackDirectly() {
        hasAttackDirectInThisTurn = true;
        if (rival.getBoard().getNumberOfMonsters() != 0) return "you can’t attack the opponent directly";
        currentPlayer.getBoard().getDidMonsterAttack()[selectedZoneIndex] = true;
        int lp = ((Monster) selectedCard).getATK();
        rival.decreaseLP(lp);
        return "you opponent receives " + lp + " battle damage";
    }

    public String activeEffect() {
        if (selectedCard == null) return "no card is selected yet";
        if (!(selectedCard instanceof Spell)) return "activate effect is only for spell cards.";
        if (getCurrentPhase() != Phase.MAIN_1 && getCurrentPhase() != Phase.MAIN_2)
            return "you can’t activate an effect on this turn";
        if (((Spell) selectedCard).isActivated()) return "you have already activated this card";
        if (selectedCard instanceof FieldSpell) {
            if (checkForTraps("active-effect")) return "rival's trap activated and cancelled the activation";
            putCardInZone(selectedCard, Board.Zone.FIELD_SPELL, Board.CardPosition.ACTIVATED, currentPlayer.getBoard());
            return "field spell activated";
        }
        if (selectedZone == Board.Zone.HAND && currentPlayer.getBoard().isZoneFull(Board.Zone.SPELL_AND_TRAP) &&
                ((Spell) selectedCard).getSpellType() != Spell.SpellType.QUICK_PLAY)
            return "spell card zone is full";
        if (checkForTraps("active-effect")) return "rival's trap activated and cancelled the activation";
        return ((Spell) selectedCard).action();


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
                    if (!((CommandKnight) card).hasDoneAction()) ((CommandKnight) card).action(false);
            }
            case FIELD_SPELL -> {
                if (position == Board.CardPosition.ACTIVATED) {
                    ((FieldSpell) card).action(false);
                    if (rival.getBoard().getFieldSpell().isActivated())
                        removeCardFromZone(rival.getBoard().getFieldSpell(), Board.Zone.FIELD_SPELL, 0, rival.getBoard());
                }
                removeCardFromZone(currentPlayer.getBoard().getFieldSpell(), Board.Zone.FIELD_SPELL, 0, currentPlayer.getBoard());
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
                    if (((CommandKnight) card).hasDoneAction()) ((CommandKnight) card).action(true);
                for (Card spell : board.getSpellAndTrapZone())
                    if (spell instanceof SupplySquad && ((SupplySquad) spell).isActivated() && !((SupplySquad) spell).isUsedInThisTurn()) {
                        boolean needToChangeTurn = ((SupplySquad) spell).getOwner() != currentPlayer;
                        if (needToChangeTurn) changeTurn();
                        Show.showGameMessage(((SupplySquad) spell).draw());
                        if (needToChangeTurn) changeTurn();
                    }
            }
            case SPELL_AND_TRAP -> board.getSpellAndTrapZone()[zoneIndex] = null;
            case HAND -> {
                board.getHand()[zoneIndex] = null;
                board.refreshHand();
            }
            case FIELD_SPELL -> {
                if (card != null && ((FieldSpell) card).isActivated()) ((FieldSpell) card).action(true);
                board.setFieldSpell(null);
                putCardInZone(card, Board.Zone.GRAVE, null, board);
            }
            case GRAVE -> board.getGrave().remove(card);
            case DECK -> board.getDeck().remove(card);
        }
    }


    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
        Show.showPhase(currentPhase);
    }

    public void changeTurn() {
        Show.showImportantGameMessage("now it will be " + rival.getUser().getNickname() + "’s turn");
        Show.showBoard();
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

    public Board.Zone getSelectedZone() {
        return selectedZone;
    }

    public boolean hasSummonedOrSet() {
        return hasSummonedOrSet;
    }

    public void setHasSummonedOrSet(boolean hasSummonedOrSet) {
        this.hasSummonedOrSet = hasSummonedOrSet;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public boolean isHasAttackDirectInThisTurn() {
        return hasAttackDirectInThisTurn;
    }
}
