
package projektarbete.physics;

import projektarbete.Coordinate;


public class Bullet extends PhysicsObject {

    int hitCount;

    public Bullet() {
        this(new Coordinate(0, 0), new Coordinate(0, 0), 1, 1);
    }

    public Bullet(Coordinate pos, Coordinate vel, double mass, double size) {
        this(new PhysicsUpdate(pos, vel, 0, 0), mass, size);
    }

    public Bullet(PhysicsUpdate update, double mass, double size) {
        super(Templates.TYPE_BASE_BULLET, mass, size, update);
    }

    @Override
    public void physicsCollision(Collision collision) {
        hitCount++;
        if (hitCount > 3) {
            PhysicsEngine.getInstance().removeObject(this);
        }
        if (Coordinate.length(velocity) < 30) {
            PhysicsEngine.getInstance().removeObject(this);
        }
    }



}
