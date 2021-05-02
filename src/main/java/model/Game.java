package model;

import model.card.Card;
import model.card.monster.*;
import model.card.trap.TimeSeal;
import model.card.trap.TorrentialTribute;
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
    private Player currentPlayer;
    private Player rival;
    private Player winner;
    private Player loser;
    private boolean surrendered;
    private Phase currentPhase;

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
                Show.showImportantGameMessage(winner.getUser().getNickname() + " won the round");
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
        setCurrentPhase(Phase.DRAW);
        selectedCard = null;
        Show.showGameMessage(drawCard());
        if (didSbWin()) return;
        setCurrentPhase(Phase.STANDBY);
        selectedCard = null;
        //standby_Effect Cards
        if (didSbWin()) return;
        setCurrentPhase(Phase.MAIN_1);
        selectedCard = null;
        CommandProcessor.game();
        if (didSbWin()) return;
        setCurrentPhase(Phase.BATTLE);
        selectedCard = null;
        CommandProcessor.game();
        if (didSbWin()) return;
        setCurrentPhase(Phase.MAIN_2);
        selectedCard = null;
        CommandProcessor.game();
        if (didSbWin()) return;
        setCurrentPhase(Phase.END);
        selectedCard = null;
    }

    private boolean didSbWin() {
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


    public void selectCard(Board.Zone zone, int index) {
        this.selectedZone = zone;
        this.selectedZoneIndex = index;
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

    public void deselect() {
        this.selectedZone = null;
        this.selectedZoneIndex = -1;

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
        return "drew " + drewCard.getName();
    }

    private void endRound() {


        //az field haye winner , loser estefade kon

    }

    private void endMatch(int maxLp) {

    }

    public String summon(String summonType) {

    }

    public String set(boolean isFromHand) {

    }


    public String flipSummon() {

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
                return "both you and your opponent monster cards are destroyed and no one receives damage"
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
                            ((TorrentialTribute) c).action();
                        return;
                    }
                }
                spellAndTrapZone = currentPlayer.getBoard().getSpellAndTrapZone();
                for (Card c : spellAndTrapZone) {
                    if (c instanceof TorrentialTribute) {
                        ((TorrentialTribute) c).action();
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
