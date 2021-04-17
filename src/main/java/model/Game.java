package model;

import model.card.Card;
import model.card.monster.Monster;
import model.person.Player;

import java.util.ArrayList;

public class Game {
    private Card selectedCard;
    private Board.Zone selectedZone;
    private int selectedZoneIndex;
    private Player currentPlayer;
    private Player rival;
    private Phase currentPhase;


    public enum Phase {
        DRAW,
        STANDBY,
        MAIN_1,
        BATTLE,
        MAIN_2,
        END
    }

    public Game(Player player1, Player player2, int round) {

    }

    public void selectCard(Board.Zone zone, int index) {
        this.selectedZone = zone;
        this.selectedZoneIndex = index;
    }

    public void deselect() {
        this.selectedZone = null;
        this.selectedZoneIndex = 0;

    }


    private void drawCard() {

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
            case MONSTER -> board.getMonsterZone()[board.getFirstEmptyIndexOfZone(Board.Zone.MONSTER)] = (Monster) card;
            case FIELD_SPELL -> board.setFieldSpell(card);
            case SPELL_AND_TRAP -> board.getSpellAndTrapZone()[board.getFirstEmptyIndexOfZone(Board.Zone.SPELL_AND_TRAP)] = card;
        }
    }

    public void removeCardFromZone(Card card, Board.Zone zone, int zoneIndex, Board board) {
        if (zone == Board.Zone.MONSTER || zone == Board.Zone.FIELD_SPELL ||
                zone == Board.Zone.SPELL_AND_TRAP || zone == Board.Zone.HAND)
            putCardInZone(null, zone, null, board);
        else {
            ArrayList<Card> thisZone = (zone == Board.Zone.GRAVE) ? board.getGrave() : board.getDeck();
            for (int i = thisZone.size() - 1; i >= 0; i++)
                if (thisZone.get(i) == card) {
                    thisZone.remove(i);
                    break;
                }
        }
    }

}
