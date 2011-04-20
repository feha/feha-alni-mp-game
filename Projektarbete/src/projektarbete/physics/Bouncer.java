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
public class Bouncer extends PhysicsObject {

    public Bouncer(){
        this(0,0,0,0,1);
    }

    public Bouncer(double x, double y) {
        this(x,y,0,0,1);
    }

    public Bouncer(double x, double y, double xVel, double yVel, double mass) {
        super(Templates.TYPE_ELASTIC_OBSTACLE, mass, 1,
                new PhysicsUpdate(x, y, xVel, yVel, 0, 0));
        /*this.position = new Coordinate(x,y);
        //this.gravityFlag = true;
        //this.gravity = 1;
        //this.gravityDir = new Coordinate(1,0);
        this.velocity = new Coordinate(velX, velY);
        hitbox = new Hitbox();
        List<Point2D.Double> shape = new ArrayList<Point2D.Double>();
        shape.add(new Point2D.Double(1,0));
        shape.add(new Point2D.Double(0,0));
        shape.add(new Point2D.Double(1,1));
        hitbox.setShape(shape);
        hitbox.normalizeShape();
        size = 1;
        visibleObject = new MyImage(Camera.getInstance(), "empty.png");
        visibleObject.centerAnchor();
        visibleObject.setScale(0.002364*size*2);
        this.mass = mass;
        //this.restitution = 0;*/
    }
}
