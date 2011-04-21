/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.graphics;

import projektarbete.Coordinate;

/**
 *
 * @author felix.hallqvist
 */
public class CameraContainer {

    public CameraContainer() {

    }

    VisibleObject parent;
    Coordinate position = new Coordinate(0,0);
    public Coordinate offset = new Coordinate(0,0);
    double angle;
    Coordinate scale;

    public void setParent(VisibleObject newParent) {
        parent = newParent;
    }

    public void setPos(Coordinate newPos) {
        position = newPos;
    }

    public void setAng(double newAng) {
        angle = newAng;
    }

    public void setScale(Coordinate newScale) {
        scale = newScale;
    }


    public VisibleObject getParent() {
        return parent;
    }

    public Coordinate getPos() {
        return parent != null ? parent.position.add(offset) : position.add(offset);
    }

    public double getAng() {
        return parent != null ? parent.angle : angle;
    }

    public Coordinate getScale() {
        return scale;
    }

}
