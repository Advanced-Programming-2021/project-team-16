package model.card.trap;


import model.Board;
import model.Game;
import model.card.Card;
import model.card.monster.Monster;
import model.person.AI;

public class CallOfTheHaunted extends Trap {
    public CallOfTheHaunted() {
        super("CallOfTheHaunted", "Trap", TrapType.CONTINUOUS, "Activate this card by targeting 1 monster in your GY;" +
                " Special Summon that target in Attack Position. " +
                "When this card leaves the field, destroy that monster. " +
                "When that monster is destroyed, destroy this card.", "Unlimited", 3500);
    }

    public String action(Game game, String monsterName) {// dar tabe attack takmil shavad.  va inke az grave random niyaze ya card ruyi bardashte mishe?

        Board board = game.getCurrentPlayer().getBoard();
        Card monster = getCardByName(monsterName);
        if (board.isZoneFull(Board.Zone.MONSTER)) {
            return "monsterZone is full!";
        } else if ((monster instanceof Monster)/* && (board.getGrave()!= null)*/) { //////?
            game.removeCardFromZone(monster, Board.Zone.GRAVE, game.getSelectedZoneIndex(), board);
            game.putCardInZone(monster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
        }

        return "summoned successfully!";
    }

    public void actionForAI(Game game, Card monster) {
        Board board = game.getCurrentPlayer().getBoard();
        if (game.getCurrentPlayer() instanceof AI) {
            if (board.getGrave() != null && !board.isZoneFull(Board.Zone.MONSTER)) {
                if (monster instanceof Monster) {
                    game.removeCardFromZone(monster, Board.Zone.GRAVE, game.getSelectedZoneIndex(), board);
                    game.putCardInZone(monster, Board.Zone.MONSTER, Board.CardPosition.ATK, board);
                }
            }
        }
    }
}