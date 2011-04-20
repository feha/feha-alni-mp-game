package projektarbete.physics;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import projektarbete.Coordinate;

public class PhysicsUpdate {

    protected Coordinate position;
    protected Coordinate velocity;
    protected double angle;
    protected double angularVelocity;

    public PhysicsUpdate() {
        this(0, 0, 0, 0, 0, 0);
    }

    public PhysicsUpdate(double x, double y,
                         double xVel, double yVel,
                         double angle, double angularVelocity) {
        this(new Coordinate(x, y), new Coordinate(xVel, yVel), angle,
                angularVelocity);
    }

    public PhysicsUpdate(Coordinate position,
                         Coordinate velocity,
                         double angle, double angularVelocity) {
        this.position = new Coordinate(position);
        this.velocity = new Coordinate(velocity);
        this.angle = angle;
        this.angularVelocity = angularVelocity;
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

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
}
