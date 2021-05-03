package model.card.trap;

public abstract class TrapHole extends Trap {
    public TrapHole() {
        super("TrapHole", "Trap", TrapType.NORMAL, "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that monster; destroy that target.", "Unlimited", 2000
        );
    }

    //boolean trapIsActivate = false;

    //public String action(Game game, int index) {   //(summon & flipSummon & activeEffect) method
    //    Board board = game.getRival().getBoard();
    //    Monster monster = board.getMonsterZone()[index];
    //        trapIsActivate = true;
    //        game.removeCardFromZone(monster, Board.Zone.MONSTER, index, board);
    //        game.putCardInZone(monster, Board.Zone.GRAVE, null, board);
    //    return "The monster was killed!";
    //}
}