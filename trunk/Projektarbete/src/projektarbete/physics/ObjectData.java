package projektarbete.physics;

public class ObjectData {

     private PhysicsData data;
     private short id;

    public ObjectData() {
        this(new PhysicsData(), (short) 0);
    }

    public ObjectData(PhysicsData data, short id) {
        this.data = data;
        this.id = id;
    }

    public PhysicsData getData() {
        return data;
    }

    public short getId() {
        return id;
    }

    public void setData(PhysicsData data) {
        this.data = data;
    }

    public void setId(short id) {
        this.id = id;
    }
}
