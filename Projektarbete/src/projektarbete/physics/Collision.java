/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.List;

/**
 *
 * @author niclas.alexandersso
 */
public class Collision {
    private PhysicsObject object1;
    private PhysicsObject object2;
    private double time;
    private boolean intersecting;

    public Collision() {

    }

    public Collision(PhysicsObject object1, PhysicsObject object2, double time,
            boolean intersecting) {
        this.object1 = object1;
        this.object2 = object2;
        this.time = time;
        this.intersecting = intersecting;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public PhysicsObject getObject2() {
        return object2;
    }


    public void setObject2(PhysicsObject object2) {
        this.object2 = object2;
    }


    public PhysicsObject getObject1() {
        return object1;
    }

    public void setObject1(PhysicsObject object1) {
        this.object1 = object1;
    }

    public void setIntersecting(boolean intersecting) {
        this.intersecting = intersecting;
    }


    public boolean isIntersecting() {
        return intersecting;
    }


    public static Collision getEarliest(List<Collision> collisions) {
        Collision earliest = null;
        for (Collision collision : collisions) {
            if (earliest == null) {
                earliest = collision;
            }
            if (earliest.getTime() > collision.getTime()) {
                earliest = collision;
            }
        }
        return earliest;
    }

/*public static Point2D.Double getLineIntersection(Line2D.Double line1, Line2D.Double line2) {

    if (line1.intersectsLine(line2)) {

        return new Point2D.Double(
                (((line1.x1*line1.y2)-(line1.y1*line1.x2) )*(line2.x1-line2.x2)
                -(line1.x1-line1.x2)*( (line2.x1*line2.y2)-(line2.y1*line2.x2)))
                /
                ( (line1.x1-line1.x2)*(line2.y1-line2.y2)
                - (line1.y1-line1.y2)*(line2.x1-line2.x2))
                ,
                (((line1.x1*line1.y2)-(line1.y1*line1.x2) )*(line2.y1-line2.y2)
                -(line1.y1-line1.y2)*( (line2.x1*line2.y2)-(line2.y1*line2.x2)))
                /
                ( (line1.x1-line1.x2)*(line2.y1-line2.y2)
                - (line1.y1-line1.y2)*(line2.x1-line2.x2))
                );

    }
    return null;
}

public static double getIntersectionTime(double t, Line2D.Double line1,
                                         Line2D.Double line2,
                                         Point2D.Double pos1,
                                         Point2D.Double pos2,
                                         Movement m1, Movement m2,
                                         double precision) {

    double t1 = 0;
    Line2D.Double testLine1 = new Line2D.Double();
    Line2D.Double testLine2 = new Line2D.Double();

    if (precision > 1/1000) {
        precision = 1/1000;
    }

    while (t1 <= t) {
        //line 1
        testLine1.setLine(m1.getPos((Double)line1.getP1(), t1),
                m1.getPos((Double)testLine1.getP2(), t1));
        testLine1.setLine(testLine1.x1+pos1.x, testLine1.y1+pos1.y,
                testLine1.x2+pos1.x, testLine1.y2+pos1.y);

        //line2
        testLine2.setLine(m2.getPos((Double)line2.getP1(), t1),
                m2.getPos((Double)testLine2.getP2(), t1));
        testLine2.setLine(testLine2.x1+pos2.x, testLine2.y1+pos2.y,
                testLine2.x2+pos2.x, testLine2.y2+pos2.y);

        if (getLineIntersection(testLine1, testLine2) != null) {
            return t1;
        }

        if (t1==t) {
            return -1;
        }
        if (t1 + precision >= t) {
            t1 = t;
        }
        else {
            t1 += precision;
        }
    }
    return -1;
}*/

}