package model.card.monster;

import controller.GameMenu;
import model.Board;
import model.Game;
import model.card.Activatable;
import model.card.Card;
import model.person.Player;
import view.CommandProcessor;

import java.util.ArrayList;

public class Scanner extends Monster implements Activatable {
    private int monsterZoneIndex;
    private Player player;
    private static final ArrayList<Scanner> activatedScanners = new ArrayList<>();

    public Scanner() {
        super("Scanner", "Once per turn, you can select 1 of your opponent's monsters that is removed " +
                        "from play. Until the End Phase, this card's name is treated as the selected monster's name, and this " +
                        "card has the same Attribute, Level, ATK, and DEF as the selected monster. If this card is removed " +
                        "from the field while this effect is applied, remove it from play.", 8000, MonsterType.MACHINE,
                1, 0, 0);
    }


    public String action() {
        Game game = GameMenu.getCurrentGame();
        Board board = game.getRival().getBoard();
        if (!board.doesGraveHaveMonster() || game.getCurrentPlayer().getBoard().isZoneFull(Board.Zone.MONSTER))
            return "there is no way you could special summon a monster";
        int monsterZoneIndex = game.getSelectedZoneIndex();
        int replacementIndex = CommandProcessor.getMonsterFromGrave(false);
        if (replacementIndex == -1) return "cancelled";
        Monster replacement = (Monster) board.getCardByIndexAndZone(replacementIndex, Board.Zone.GRAVE);
        this.player = game.getCurrentPlayer();
        this.monsterZoneIndex = monsterZoneIndex;
        game.getCurrentPlayer().getBoard().getMonsterZone()[monsterZoneIndex] = replacement;
        activatedScanners.add(this);
        return "Replaced successfully.";
    }

    public static void undo() {
        for (Scanner activatedScanner : activatedScanners) {
            Board board = activatedScanner.player.getBoard();
            int index = activatedScanner.monsterZoneIndex;
            Card fakeCard = new Scanner();
            fakeCard.setSide(true);
            fakeCard.setSizes(false);
            GameMenu.getCurrentGame().setOnMouseClickedSelect(fakeCard,index, Board.Zone.MONSTER,true);
            board.getMonsterZone()[index] = activatedScanner;
            board.getGameView().myMonsters.getChildren().set(Game.getGraphicalIndex(index,true),activatedScanner);
            activatedScanner.setSide(true);
            board.getRivalGameView().rivalMonsters.getChildren().set(Game.getGraphicalIndex(index,false),fakeCard);

        }
        activatedScanners.clear();
    }


}
