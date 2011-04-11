package projektarbete.physics;

import projektarbete.Coordinate;

public class PhysicsUpdate {

    protected Coordinate position;
    protected Coordinate velocity;
    protected double angle;

    public PhysicsUpdate() {
        this(0, 0, 0, 0, 0);
    }

    public PhysicsUpdate(double x, double y,
                         double xVel, double yVel,
                         double angle) {
        this(new Coordinate(x, y), new Coordinate(xVel, yVel), angle);
    }

    public PhysicsUpdate(Coordinate position,
                         Coordinate velocity,
                         double angle) {
        this.position = position;
        this.velocity = velocity;
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public Coordinate getVelocity() {
        return velocity;
    }

    public void setVelocity(Coordinate velocity) {
        this.velocity = velocity;
    }
}
