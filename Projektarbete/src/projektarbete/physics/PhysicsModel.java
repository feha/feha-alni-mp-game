
package projektarbete.physics;

import projektarbete.Coordinate;
import projektarbete.graphics.VisibleObject;


public class PhysicsModel {
    
    public static final double EARTH_GRAVITY = 9.831; //average gravitational
                                                      //acceleration on earth

    protected double mass;
    protected double airFriction;
    protected Coordinate gravityDir;
    protected double gravity;
    protected boolean gravityFlag;
    protected boolean airFrictionFlag;
    protected double size;
    protected double restitution; //0-1 where 1 is completely elastic
    protected Hitbox hitbox;
    VisibleObject visibleObject;

    public PhysicsModel() {
        mass = 1; // 1 kg
        gravityDir = new Coordinate(0, -1); // down
        gravity = EARTH_GRAVITY;
        size = 1;
        restitution = 1;
        hitbox = null;
        visibleObject = null;
    }

    public PhysicsModel(PhysicsModel model) {
        set(model);
    }

    public final void set(PhysicsModel model) {
        mass = model.getMass();
        airFriction = model.getAirFriction();
        gravityDir = model.getGravityDir();
        gravity = model.getGravity();
        gravityFlag = model.gravityFlag();
        airFrictionFlag = model.airFrictionFlag();
        size = model.getSize();
        restitution = model.getRestitution();
        hitbox = model.getHitbox();
        visibleObject = model.getVisibleObject();
    }

    public double getAirFriction() {
        return airFriction;
    }

    public double getGravity() {
        return gravity;
    }

    public Coordinate getGravityDir() {
        return new Coordinate(gravityDir);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public double getMass() {
        return mass;
    }

    public double getRestitution() {
        return restitution;
    }

    public double getSize() {
        return size;
    }

    public VisibleObject getVisibleObject() {
        return visibleObject;
    }

    public boolean airFrictionFlag() {
        return airFrictionFlag;
    }

    public boolean gravityFlag() {
        return gravityFlag;
    }

    public void setAirFriction(double airFriction) {
        this.airFriction = airFriction;
    }

    public void setAirFrictionFlag(boolean airFrictionFlag) {
        this.airFrictionFlag = airFrictionFlag;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setGravityDir(Coordinate gravityDir) {
        this.gravityDir = gravityDir;
    }

    public void setGravityFlag(boolean gravityFlag) {
        this.gravityFlag = gravityFlag;
    }

    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setVisibleObject(VisibleObject visibleObject) {
        this.visibleObject = visibleObject;
    }

}
