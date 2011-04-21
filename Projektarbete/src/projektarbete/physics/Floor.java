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
public class Floor extends PhysicsObject {

    public Floor() {
        this(0, 0);
    }
    public Floor(double x, double y) {
        this(x, y, 0.5);
        /*position = new Coordinate(x, y);
        hitbox = new Hitbox();
        List<Point2D.Double> shape = new ArrayList<Point2D.Double>();
        shape.add(new Point2D.Double(1,0));
        shape.add(new Point2D.Double(0,0));
        shape.add(new Point2D.Double(1,1));
        hitbox.setShape(shape);
        hitbox.normalizeShape();
        size = 0.5;
        mass = 100000;
        visibleObject = new MyImage(Camera.getInstance(), "empty.png");
        visibleObject.centerAnchor();
        visibleObject.setScale(/*0.002364* /(1.0/423.0)*size*2);
        //this.gravityFlag = true;
        this.gravity = 0.001;*/
    }
    public Floor(double x, double y, double radius) {
        super(Templates.TYPE_ELASTIC_OBSTACLE, 10000000, radius,
                new PhysicsUpdate(x, y, 0, 0, 0, 0));
    }
}
