package projektarbete.physics;

public class PhysicsData {
    private short template;
    private double mass;
    private double size;
    private PhysicsUpdate update;

    public PhysicsData() {
        this((short) 0, 1, 1, new PhysicsUpdate());
    }

    public PhysicsData(short template, double mass, double size,
            PhysicsUpdate update) {
        this.template = template;
        this.mass = mass;
        this.size = size;
        this.update = update;
    }


    public double getMass() {
        return mass;
    }

    public double getSize() {
        return size;
    }

    public short getTemplate() {
        return template;
    }

    public PhysicsUpdate getUpdate() {
        return update;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setTemplate(short template) {
        this.template = template;
    }

    public void setUpdate(PhysicsUpdate update) {
        this.update = update;
    }

}
