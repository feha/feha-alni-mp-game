package projektarbete.physics;

import projektarbete.Coordinate;

public class PlayerUpdate extends ObjectUpdate {

    private byte flags;
    private Coordinate aim = new Coordinate(0, 0);;

    public PlayerUpdate() {
        super();
    }

    public PlayerUpdate(PhysicsUpdate update, byte flags, Coordinate aim, short id) {
        super(update, id);
        this.flags = flags;
        this.aim = aim;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public Coordinate getAim() {
        return aim;
    }

    public void setAim(Coordinate aim) {
        this.aim = aim;
    }
    
}
