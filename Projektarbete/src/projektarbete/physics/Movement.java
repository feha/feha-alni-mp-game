/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Point2D;
import projektarbete.Coordinate;

/**
 *
 * @author niclas.alexandersso
 */
public class Movement {
    private Coordinate v;
    private double theta;

    public Movement() {
        v = new Coordinate(0,0);
        theta = 0;
    }

    public Movement(Coordinate velocity, double rotation) {
        v = velocity;
        theta = rotation;
    }

    public double getX(Point2D.Double pos, double time) {
        return v.x()*time
                + pos.x * Math.cos(theta*time) - pos.y * Math.sin(theta*time);
    }

    public double getY(Point2D.Double pos, double time) {
        return v.y()*time
                + pos.x * Math.sin(theta*time) + pos.y * Math.cos(theta*time);
    }

    public Point2D.Double getPos(Point2D.Double pos, double time) {
        return new Point2D.Double(getX(pos, time), getY(pos, time));
    }

    public Coordinate getVel() {
        return v;
    }

    public void setVel(Coordinate vel) {
        v = vel;
    }

    public double getRotation() {
        return theta;
    }

    public void setRotation(double rotation) {
        theta = rotation;
    }
}