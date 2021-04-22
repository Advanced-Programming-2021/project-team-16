package model;

import model.card.Card;
import model.card.monster.Monster;
import model.card.trap.TorrentialTribute;
import model.person.Player;
import view.CommandProcessor;
import view.Show;

import java.util.ArrayList;


public class Game {
    private Card selectedCard;
    private Board.Zone selectedZone;
    private int selectedZoneIndex;
    private Player currentPlayer;
    private Player rival;
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
        if (round == 1) {
            run(player1, player2);
        }
    }

    private void run(Player me, Player rival) {
        //runCards
        Show.showGameMessage("its " + me.getUser().getNickname() + "’s turn");
        this.currentPlayer = me;
        this.rival = rival;
        setCurrentPhase(Phase.DRAW);
        Show.showGameMessage(drawCard());
        setCurrentPhase(Phase.STANDBY);
        //standbyCards
        setCurrentPhase(Phase.MAIN_1);
        CommandProcessor.game();
        setCurrentPhase(Phase.BATTLE);
        CommandProcessor.game();
        setCurrentPhase(Phase.MAIN_2);
        CommandProcessor.game();
        setCurrentPhase(Phase.END);
        run(rival, me);
    }


    public void selectCard(Board.Zone zone, int index) {
        this.selectedZone = zone;
        this.selectedZoneIndex = index;
    }

    public void deselect() {
        this.selectedZone = null;
        this.selectedZoneIndex = 0;

    }

    private String drawCard() {
        //Null pointer exception!!!!
    }

    private void endRound(Player surrounded) {

    }

    private void endRound() {

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
