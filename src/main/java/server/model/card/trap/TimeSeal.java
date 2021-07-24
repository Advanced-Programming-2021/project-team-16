package server.model.card.trap;


public class TimeSeal extends Trap {
    public TimeSeal() {
        super("TimeSeal", "Trap", TrapType.NORMAL, "Skip the Draw Phase of your opponent's next turn.", "Limited", 2000);
    }

}