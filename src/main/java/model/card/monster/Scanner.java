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


    public static void undo() {
        for (Scanner activatedScanner : activatedScanners) {
            Board board = activatedScanner.player.getBoard();
            int index = activatedScanner.monsterZoneIndex;
            Card fakeCard = new Scanner();
            fakeCard.setSide(true);
            fakeCard.setSizes(false);
            fakeCard.setAttackedOnDraggedOver(index);
            GameMenu.getCurrentGame().setOnMouseClickedSelect(fakeCard,index, Board.Zone.MONSTER,true);
            board.getMonsterZone()[index] = activatedScanner;
            board.getGameView().myMonsters.getChildren().set(Game.getGraphicalIndex(index,true),activatedScanner);
            activatedScanner.setSide(true);
            board.getRivalGameView().rivalMonsters.getChildren().set(Game.getGraphicalIndex(index,false),fakeCard);

        }
        activatedScanners.clear();
    }

    public String action() {
        Game game = GameMenu.getCurrentGame();
        Board rivalBoard = game.getRival().getBoard();
        if (!rivalBoard.doesGraveHaveMonster() || game.getCurrentPlayer().getBoard().isZoneFull(Board.Zone.MONSTER))
            return "there is no way you could special summon a monster";
        int monsterZoneIndex = game.getSelectedZoneIndex();
        int replacementIndex = CommandProcessor.getMonsterFromGrave(false);
        if (replacementIndex == -1) return "cancelled";
        Monster replacement = (Monster) rivalBoard.getCardByIndexAndZone(replacementIndex, Board.Zone.GRAVE);
        this.player = game.getCurrentPlayer();
        this.monsterZoneIndex = monsterZoneIndex;
        game.getCurrentPlayer().getBoard().getMonsterZone()[monsterZoneIndex] = replacement;
        game.getCurrentPlayer().getBoard().getCardPositions()[0][monsterZoneIndex] = Board.CardPosition.ATK;
        Card fakeCard = Card.make(replacement.getName());
        fakeCard.setSide(true);
        fakeCard.setSizes(false);
        replacement.setSizes(false);
        replacement.setSide(true);
        GameMenu.getCurrentGame().setOnMouseClickedSelect(fakeCard,monsterZoneIndex, Board.Zone.MONSTER,true);
        GameMenu.getCurrentGame().setOnMouseClickedSelect(replacement,monsterZoneIndex, Board.Zone.MONSTER,false);
        player.getGameView().myMonsters.getChildren().set(Game.getGraphicalIndex(monsterZoneIndex,true),replacement);
        player.getBoard().getRivalGameView().rivalMonsters.getChildren().set(Game.getGraphicalIndex(monsterZoneIndex,false),fakeCard);
        replacement.setAttackingOnDragged(replacementIndex,player);
        fakeCard.setAttackedOnDraggedOver(replacementIndex);
        activatedScanners.add(this);
        return "Replaced successfully.";
    }


}
