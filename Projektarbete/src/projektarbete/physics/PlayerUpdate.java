package projektarbete.physics;

import projektarbete.Coordinate;

public class PlayerUpdate extends ObjectUpdate {

    private byte flags;

    public PlayerUpdate() {
        super();
    }

    public PlayerUpdate(PhysicsUpdate update, byte flags, short id) {
        super(update, id);
        this.flags = flags;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }
}
