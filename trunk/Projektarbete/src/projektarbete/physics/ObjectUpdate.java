package projektarbete.physics;

public class ObjectUpdate {

    private PhysicsUpdate update;
    private short id;

    public ObjectUpdate() {
        this(new PhysicsUpdate(), (short) 0);
    }

    public ObjectUpdate(PhysicsUpdate update, short id) {
        this.update = update;
        this.id = id;
    }

    public PhysicsUpdate getUpdate() {
        return update;
    }

    public void setUpdate(PhysicsUpdate update) {
        this.update = update;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

}
