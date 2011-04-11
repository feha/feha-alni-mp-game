/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import projektarbete.Coordinate;
import projektarbete.graphics.Camera;
import projektarbete.graphics.MyImage;

/**
 *
 * @author niclas.alexandersso
 */
public class Floor extends BasePhysics {

    public Floor() {
        this(5, -6);
    }
    public Floor(double x, double y) {
        position = new Coordinate(x, y);
        hitbox = new Hitbox();
        List<Point2D.Double> shape = new ArrayList<Point2D.Double>();
        shape.add(new Point2D.Double(1,0));
        shape.add(new Point2D.Double(0,0));
        shape.add(new Point2D.Double(1,1));
        hitbox.setShape(shape);
        hitbox.normalizeShape();
        size = 1;
        mass = 25;
        visibleObject = new MyImage(Camera.getInstance(), "TrollFace.png");
        visibleObject.centerAnchor();
        visibleObject.setScale(0.0043);
        //this.gravityFlag = true;
        this.gravity = 0.001;
    }
}
