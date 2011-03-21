/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.*;
import java.awt.geom.Point2D.Double;

/**
 *
 * @author niclas.alexandersso
 */
public class Collision {

public static Point2D.Double getLineIntersection(Line2D.Double line1, Line2D.Double line2) {

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
}

}