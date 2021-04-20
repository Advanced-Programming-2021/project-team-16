package model.card.spell;

public class ChangeOfHeart extends Spell {
    public ChangeOfHeart() {
        super("Change of Heart", "Spell", SpellType.NORMAL
                , "Target 1 monster your opponent controls; take control of it until the End Phase.", "Limited", 2500);
    }

//    public void action(Monster monster, int index) {
//        Game game = Game.getInstance();
//        Board board = game.getRival().getBoard();
//        Monster[] monsters = board.getMonsterZone();
//        for (int i = 0 ; i < monsters.length; i++){
//            if (monsters[index] == monster) {
//
//            }
//    }
}
