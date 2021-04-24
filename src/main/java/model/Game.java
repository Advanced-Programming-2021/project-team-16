package model;

import model.card.Card;
import model.card.monster.HeraldOfCreation;
import model.card.monster.Monster;
import model.card.monster.Scanner;
import model.card.monster.Texchanger;
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


    public enum Phase {
        DRAW("draw phase"),
        STANDBY("standby phase"),
        MAIN_1("main phase 1"),
        BATTLE("battle phase"),
        MAIN_2("main phase 2"),
        END("end phase");

        Phase(String name) {
        }

        @Override
        public String toString() {
            return name();
        }
    }

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
            while (getMatchWinner(winnerAndLp) == null) {
                winner = null;
                player1.setLP(8000);
                player2.setLP(8000);
                while (winner == null) {
                    run(rival, currentPlayer);
                }
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
        if (currentPlayer.getBoard().getDeck().size() == 0 || currentPlayer.getBoard().isZoneFull(Board.Zone.HAND)) {
            Show.showGameMessage("You can't draw any card");
            winner = rival;
            return;
        } else {
            Show.showGameMessage(drawCard());
            setCurrentPhase(Phase.STANDBY);
            //standbyCards
            if (sbLost()) return;
            setCurrentPhase(Phase.MAIN_1);
            CommandProcessor.game();
            setCurrentPhase(Phase.BATTLE);
            CommandProcessor.game();
            setCurrentPhase(Phase.MAIN_2);
            CommandProcessor.game();
            setCurrentPhase(Phase.END);
        }
    }

    private boolean sbLost() {
        if (currentPlayer.getLP() == 0 && rival.getLP() == 0) endRound();
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
        this.selectedZoneIndex = 0;

    }

    private String drawCard(Game game) {
        //Null pointer exception!!!!
        Card[] spellAndTrapZone = rival.getBoard().getSpellAndTrapZone();
        for (int i = 0; i < spellAndTrapZone.length; i++) {
            Card card = spellAndTrapZone[i];
            if (card.getClass() == TimeSeal.class) {
                game.removeCardFromZone(card, Board.Zone.SPELL_AND_TRAP, i, game.rival.getBoard());
                return "You can't draw card because enemy has Time seal.";
            }
        }
        Card drewCard = null;
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

    }

    public String activeEffect() {

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
                board.getMonsterZone()[board.getFirstEmptyIndexOfZone(Board.Zone.MONSTER)] = (Monster) card;
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
            case SPELL_AND_TRAP -> board.getSpellAndTrapZone()[board.getFirstEmptyIndexOfZone(Board.Zone.SPELL_AND_TRAP)] = card;
        }
    }

    public void removeCardFromZone(Card card, Board.Zone zone, int zoneIndex, Board board) {
        if (zone == Board.Zone.MONSTER || zone == Board.Zone.FIELD_SPELL ||
                zone == Board.Zone.SPELL_AND_TRAP || zone == Board.Zone.HAND)
            putCardInZone(null, zone, null, board);                         //repeated cards!?
        else {
            ArrayList<Card> thisZone = (zone == Board.Zone.GRAVE) ? board.getGrave() : board.getDeck();
            for (int i = thisZone.size() - 1; i >= 0; i--)
                if (thisZone.get(i) == card) {
                    thisZone.remove(i);
                    break;
                }
        }

    }


    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
        Show.showPhase(currentPhase);
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
