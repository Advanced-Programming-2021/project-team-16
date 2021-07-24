package server.modell.card.spell;

import controller.GameMenu;
import model.Board;
import server.controller.GameServer;
import server.modell.Game;
import server.modell.card.Card;
import server.modell.card.monster.RitualMonster;
import view.CommandProcessor;

import java.util.ArrayList;
import java.util.HashMap;

public class AdvancedRitualArt extends Spell {

    public AdvancedRitualArt() {
        super("AdvancedRitualArt", "Spell", SpellType.RITUAL
                , "This card can be used to Ritual Summon any 1 Ritual Monster. You must also send Normal Monsters from your Deck to the Graveyard whose total Levels equal the Level of that Ritual Monster.", "Unlimited", 3000);
    }

    public String action() {
        Game game = GameServer.getCurrentGame();
        Board board = game.getCurrentPlayer().getBoard();
        if (board.isZoneFull(Board.Zone.MONSTER)) return "activation cancelled (monster zone is full)";
        Card[] hand = board.getHand();
        ArrayList<Card> ritualMonsters = new ArrayList<>();
        HashMap<Card, Integer> ritualsWithIndex = new HashMap<>();
        for (int i = 0; i < hand.length; i++) {
            Card card = hand[i];
            if (card instanceof RitualMonster) {
                ritualMonsters.add(card);
                ritualsWithIndex.put(card, i);
            }
        }
        if (ritualMonsters.size() == 0)
            return "there is no way you could ritual summon a monster (no ritual monster in hand)";
        int ritualMonsterIndex = CommandProcessor.getIndexOfCardArray(ritualMonsters, "(ritual monster for ritual summon)");
        if (ritualMonsterIndex == -1) return "cancelled";
        RitualMonster ritualMonster = (RitualMonster) ritualMonsters.get(ritualMonsterIndex);
        int handIndex = ritualsWithIndex.get(ritualMonster);
        String errorInTribute = ritualMonster.getDeckIndexAndTribute();
        if (errorInTribute != null) return errorInTribute;
        Board.CardPosition position = CommandProcessor.yesNoQuestion("do you want your monster to be in attack position? \n" +
                "(\"no\" means you want it in defence position)") ? Board.CardPosition.ATK : Board.CardPosition.REVEAL_DEF;
        game.removeCardFromZone(ritualMonster, Board.Zone.HAND, handIndex, board);
        game.putCardInZone(ritualMonster, Board.Zone.MONSTER, position, board);
        return super.action() + "and ritual summoned " + ritualMonster.getName() + " successfully";
    }


}
