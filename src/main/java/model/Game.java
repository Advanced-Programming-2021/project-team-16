package model;

import controller.GameMenu;
import model.card.Card;
import model.card.monster.*;
import model.card.spell.MessengerOfPeace;
import model.card.spell.Spell;
import model.card.spell.SupplySquad;
import model.card.trap.*;
import model.person.Player;
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
    private boolean surrendered;
    private Phase currentPhase;
    private int gameScore;

    public Game(Player player1, Player player2, int round) {
        this.currentPlayer = player2;
        this.rival = player1;
        if (round == 1) {
            while (winner == null) {
                run(rival, currentPlayer);
            }
            endRound();
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
                winnerAndLp.put(winner, winner.getLP());
            }
            winner = getMatchWinner(winnerAndLp);
            loser = (winner == player1) ? player2 : player1;
            int maxLp = 0;
            for (Map.Entry<Player, Integer> player : winnerAndLp.entrySet()) {
                if (player.getKey() == winner && maxLp < player.getValue()) maxLp = player.getValue();
            }
            endMatch(maxLp);
        }
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

        Show.showGameMessage("its " + me.getUser().getNickname() + "’s turn");
        this.currentPlayer = me;
        this.rival = rival;
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
                if (CommandProcessor.yesNoQuestion("Do you want to to pay 100 LP to keep Messenger of peace?"))
                    currentPlayer.decreaseLP(100);
                else {
                    putCardInZone(cards[i], Board.Zone.GRAVE, null, currBoard);
                    removeCardFromZone(cards[i], Board.Zone.SPELL_AND_TRAP, i, currBoard);
                }
            }
        }
        setCurrentPhase(Phase.MAIN_1);
        CommandProcessor.game();
        if (didSbWin()) return;
        setCurrentPhase(Phase.BATTLE);
        CommandProcessor.game();
        if (didSbWin()) return;
        setCurrentPhase(Phase.MAIN_2);
        CommandProcessor.game();
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
                    Show.showGameMessage("Enter the hand index of the card witch you want to tribute");
                    Show.showGameMessage(heraldOfCreation.action(CommandProcessor.getCardIndex(), CommandProcessor.getCardName()));
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
//      winner.getUser().increaseGameScore(1000);
        winner.getUser().increaseMoney(1000 + winner.getLP());
        loser.getUser().increaseMoney(100);
//      int winnerGameScore = winner.getUser().getGameScore();
//      int loserGameScore = loser.getUser().getGameScore();
//      if(winnerGameScore == 2000 && loserGameScore == 0) {
//          endMatch()
//      }
        return winner.getUser().getUsername() + " won the game and the score is: 1000-0";


    }

    private String endMatch(int maxLp) {
        winner.getUser().increaseScore(3000);
        winner.getUser().increaseMoney(3000 + (3 * maxLp));
        loser.getUser().increaseMoney(300);

        return winner.getUser().getUsername() + "won the whole match with score: " + winner.getUser().getGameScore() + "-" + loser.getUser().getGameScore();
    }

    public String summon(String summonType) {
        ArrayList<Monster> monsters = new ArrayList<>();
        Card[] spellAndTrapZone = currentPlayer.getBoard().getSpellAndTrapZone();
        Card[] spellAndTrapZoner = rival.getBoard().getSpellAndTrapZone();
        Monster CMonster = (Monster) currentPlayer.getBoard().getHand()[selectedZoneIndex];
        Board board = currentPlayer.getBoard();
        Monster monster = board.getMonsterZone()[CommandProcessor.getCardIndex()];
        if (selectedCard == null) {
            return "no card is selected yet";
        }
        if ((selectedCard instanceof GateGuardian)
                || (selectedCard instanceof TheTricky)
                || !(selectedCard instanceof Monster)
                || selectedCard != currentPlayer.getBoard().getHand()[selectedZoneIndex]) {////////////////
            return "you can't summon this card";
        }
        if (currentPhase != Phase.MAIN_1 && currentPhase != Phase.MAIN_2) {
            return "action not allowed in this phase";
        }
        if (getCurrentPlayer().getBoard().isZoneFull(Board.Zone.MONSTER)) {
            return "RMonster card zone is full";
        }
        /*if ghablan summon ya set karde bashe va dg natune summon ya set kone*/


        if (((Monster) selectedCard).getLevel() <= 4) {
            removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, board);
            putCardInZone(selectedCard,/*board.getFirstEmptyIndexOfZone() */ selectedZone, Board.CardPosition.ATK, board);/////////
            return "summoned successfully";
        }
        if (((Monster) selectedCard).getLevel() == 5 || ((Monster) selectedCard).getLevel() == 6) {
            if (board.howManyMonsters() == 0) {
                return "there are not enough cards for tribute";
            } else {
                if (monster == null) {
                    return "there no monsters one this address";
                }
                removeCardFromZone(selectedCard, Board.Zone.MONSTER, CommandProcessor.getCardIndex(), board);
                putCardInZone(selectedCard, Board.Zone.GRAVE, null, board);

                removeCardFromZone(CMonster, Board.Zone.HAND, selectedZoneIndex, board);

                putCardInZone(CMonster,/*board.getFirstEmptyIndexOfZone() */ Board.Zone.MONSTER, Board.CardPosition.ATK, board);/////////
                return "summoned successfully";
            }
        }
        if (((Monster) selectedCard).getLevel() == 7 || ((Monster) selectedCard).getLevel() == 8) {
            if (!(selectedCard instanceof GateGuardian)) {
                if (board.howManyMonsters() < 2) {
                    return "there are not enough cards for tribute";
                }
                for (int i = 0; i < 2; i++) {
                    monsters.add(monster);
                }
                if (monsters.size() == 2) {
                    for (int i = 0; i < 2; i++) {
                        removeCardFromZone(monster, Board.Zone.MONSTER, CommandProcessor.getCardIndex(), board);
                        putCardInZone(monster, Board.Zone.GRAVE, null, board);
                    }
                } else {
                    return "there is no monster on one of these addresses";
                }
                removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, board);
                putCardInZone(selectedCard,/*board.getFirstEmptyIndexOfZone() */ selectedZone, Board.CardPosition.ATK, board);/////////
                return "summoned successfully";
            }
        }
        for (Card card : spellAndTrapZone) {
            if (card instanceof CallOfTheHaunted) {
                ((CallOfTheHaunted) card).action(this, summonType);
                if (((CallOfTheHaunted) card).action(this, summonType).equals("monsterZone is full!")) {
                    return "monsterZone is full, You can't summon!";
                } else if (((CallOfTheHaunted) card).action(this, summonType).equals("summoned successfully!")) {
                    return "summoned successfully!";
                }
            }
        }
        if (CMonster.getATK() >= 1000) {////////////////////////
            if (selectedCard == CMonster) {
                if (!(currentPlayer.getBoard().isZoneFull(Board.Zone.MONSTER))) {
                    removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, rival.getBoard());
                    putCardInZone(selectedCard, Board.Zone.MONSTER, Board.CardPosition.ATK, getRival().getBoard());
                    for (Card card : spellAndTrapZoner) {
                        if (card instanceof TrapHole) {
                            ((TrapHole) card).action(selectedCard, CommandProcessor.getCardIndex(), this);
                            if (((TrapHole) card).action(selectedCard, CommandProcessor.getCardIndex(), this).equals("The monster is killed successfully!")) {
                                return "remove the monster by TrapHole is successfully!";
                            }
                        }
                    }
                }
            }
        }
        for (Card card : spellAndTrapZone) {                 ///////////////////
            if (card instanceof TorrentialTribute) {
                if (selectedCard == CMonster && !currentPlayer.getBoard().isZoneFull(Board.Zone.MONSTER)) {
                    removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
                    putCardInZone(selectedCard, Board.Zone.MONSTER, Board.CardPosition.ATK, currentPlayer.getBoard());
                }
                ((TorrentialTribute) card).action(this);
            }
        }
        if (selectedCard instanceof TerratigerTheEmpoweredWarrior) {
            for (Monster monster1 : getCurrentPlayer().getBoard().getMonsterZone())
                ((TerratigerTheEmpoweredWarrior) monster1).action(selectedZoneIndex, this);
        }
        //    for (Card card : spellAndTrapZone) {
        //        if (card instanceof TerratigerTheEmpoweredWarrior) {
        //            if (selectedCard == CMonster && !currentPlayer.getBoard().isZoneFull(Board.Zone.MONSTER)) {
        //                removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
        //                putCardInZone(selectedCard, Board.Zone.MONSTER, Board.CardPosition.ATK, currentPlayer.getBoard());}
        //            ((TerratigerTheEmpoweredWarrior) card).action(selectedZoneIndex, this);}}

        //     for (Card card : spellAndTrapZoner) {              ///////////special summon
        //         if (card instanceof SolemnWarning) {
        //             if (selectedCard == CMonster) {
        //                 ((SolemnWarning) card).action(this, selectedZoneIndex);
        //                 if (((SolemnWarning) card).action(this, selectedZoneIndex).equals("Stop summon!")) {
        //                     return "summoned successfully!";}}}}
        return summonType;
    }

    public String set() {
        boolean isFromHand = selectedZone == Board.Zone.HAND;
        if (selectedCard == null) return "no card is selected yet";
        for (Card card : currentPlayer.getBoard().getHand()) {
            if (card instanceof Monster) {
                if (selectedCard != card) {
                    return "you can’t set this card";
                } else if (currentPhase != Phase.MAIN_1 && currentPhase != Phase.MAIN_2) {
                    return "action not allowed in this phase";
                }
                if (currentPlayer.getBoard().isZoneFull(Board.Zone.MONSTER)) {
                    return "monster card zone is full";
                }
                if (!isFromHand) {//kafie?
                    return "you already summoned/set on this turn";
                }
                if (!currentPlayer.getBoard().isZoneFull(selectedZone)) {
                    removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());// selectedZoneIndex hamun firstEmptyZoneIndexe?
                    putCardInZone(selectedCard, Board.Zone.MONSTER, Board.CardPosition.HIDE_DEF, currentPlayer.getBoard());
                }

            }
        }
        for (Card card : currentPlayer.getBoard().getHand()) {
            if (card instanceof Spell) {
                if (selectedCard == card) {
                    // if (selectedCard == null) {
                    //     return "no card is selected yet";
                    // }
                    if (selectedCard != currentPlayer.getBoard().getHand()[selectedZoneIndex]) {
                        return "you can’t set this card";
                    }
                    if (currentPhase != Phase.MAIN_1 && currentPhase != Phase.MAIN_2) {
                        return "action not allowed in this phase";
                    }
                    //  if (rival.getBoard().isZoneFull(Board.Zone.MONSTER)) {///////////////////
                    //      return "spell card zone is full";
                    //  }
                    if (currentPlayer.getBoard().isZoneFull(Board.Zone.SPELL_AND_TRAP)) {
                        return "spell card zone is full";
                    }
                    if (!isFromHand) {                                                 //kafie?
                        return "you already summoned/set on this turn";
                    }
                    if (!currentPlayer.getBoard().isZoneFull(selectedZone)) {
                        removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
                        putCardInZone(selectedCard, Board.Zone.SPELL_AND_TRAP, Board.CardPosition.HIDE_DEF, currentPlayer.getBoard());
                    }
                }
            }
        }
        for (Card card : currentPlayer.getBoard().getHand()) {
            if (card instanceof Trap) {
                if (selectedCard == card) {
                    if (selectedCard != currentPlayer.getBoard().getHand()[selectedZoneIndex]) {
                        return "you can’t set this card";
                    }
                    if (currentPhase != Phase.MAIN_1 && currentPhase != Phase.MAIN_2) {
                        return "action not allowed in this phase";
                    }
                    if (currentPlayer.getBoard().isZoneFull(Board.Zone.SPELL_AND_TRAP)) {
                        return "trap card zone is full";
                    }
                    if (!isFromHand) {//kafie?
                        return "you already summoned/set on this turn";
                    }
                    if (!currentPlayer.getBoard().isZoneFull(selectedZone)) {
                        removeCardFromZone(selectedCard, Board.Zone.HAND, selectedZoneIndex, currentPlayer.getBoard());
                        putCardInZone(selectedCard, Board.Zone.SPELL_AND_TRAP, Board.CardPosition.HIDE_DEF, currentPlayer.getBoard());
                    }

                }
            }
        }
        return "set successfully";
    }
    public String flipSummon() {

        boolean isInZone = false;
        Game game = GameMenu.getCurrentGame();
        if (selectedCard == null) return "no card is selected yet";
        for (Monster monster : game.getCurrentPlayer().getBoard().getMonsterZone()) {
            if (monster == selectedCard) {
                isInZone = true;
                break;
            }
        }
        if (!isInZone) return "you can’t change this card position";
        if (getCurrentPhase() != Phase.MAIN_1 && getCurrentPhase() != Phase.MAIN_2)
            return "you can’t do this action in this phase";
        Monster[] monsterZone = game.getCurrentPlayer().getBoard().getMonsterZone();
        Board board = game.getCurrentPlayer().getBoard();
        for (int i = 0; i < monsterZone.length; i++) {
            if (monsterZone[i] == selectedCard) {
                if (board.getCardPositions()[0][i] != Board.CardPosition.HIDE_DEF) {
                    return "you can’t flip summon this card";                                //TODO: once per turn.
                } else {
                    board.getCardPositions()[0][i] = Board.CardPosition.ATK;

                    if (((Monster) selectedCard).getATK() >= 1000) {
                        for (Card card : getRival().getBoard().getMonsterZone()) {
                            if (card instanceof TrapHole)
                                putCardInZone(selectedCard, Board.Zone.GRAVE, null, getCurrentPlayer().getBoard());
                            removeCardFromZone(selectedCard, Board.Zone.MONSTER, i, getCurrentPlayer().getBoard());
                            return "card was destroyed due to Trap hole activation";
                        }
                    }
                }
                //needs some changes
            }

        }
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
            Show.showGameMessage("choose zone for the cyberse monster that you want to tribute (deck, graveyard or hand)");
            Board.Zone zone = CommandProcessor.getZone();
            int cardIndex = -1;
            Card card;
            if (zone == Board.Zone.HAND) {
                Show.showGameMessage("choose hand index");
                cardIndex = CommandProcessor.getCardIndex();
                card = currentPlayer.getBoard().getHand()[cardIndex];
            } else if (zone == Board.Zone.DECK || zone == Board.Zone.GRAVE) {
                Show.showGameMessage("Enter the card's name");
                cardIndex = currentPlayer.getBoard().getIndexOfCard(CommandProcessor.getCardName(), zone);
                card = currentPlayer.getBoard().getCardByIndexAndZone(cardIndex, zone);
            } else return "zone is not valid";
            String result = ((Texchanger) attacked).specialSummonACyberseMonster(card, zone, cardIndex);
            changeTurn();
            return result;
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
        if (((Spell) selectedCard).getSpellType() == Spell.SpellType.FIELD) {
            if (currentPlayer.getBoard().isZoneFull(Board.Zone.FIELD_SPELL)) {
                putCardInZone(currentPlayer.getBoard().getFieldSpell(), Board.Zone.GRAVE, null, currentPlayer.getBoard());
                removeCardFromZone(currentPlayer.getBoard().getFieldSpell(), Board.Zone.FIELD_SPELL, 0, currentPlayer.getBoard());
            }
            putCardInZone(selectedCard, Board.Zone.FIELD_SPELL, Board.CardPosition.ACTIVATED, currentPlayer.getBoard());
            return "spell activated";
        }
        putCardInZone(selectedCard, Board.Zone.SPELL_AND_TRAP, Board.CardPosition.ACTIVATED, getCurrentPlayer().getBoard());
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
            case FIELD_SPELL -> board.setFieldSpell(card);
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
                for (Card spell : board.getSpellAndTrapZone()) {
                    if (spell instanceof SupplySquad) {
                        SupplySquad supplySquad = new SupplySquad();
                        supplySquad.action();
                    }
                }
            }
            case SPELL_AND_TRAP -> board.getSpellAndTrapZone()[zoneIndex] = null;
            case HAND -> board.getHand()[zoneIndex] = null;
            case FIELD_SPELL -> board.setFieldSpell(null);
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
