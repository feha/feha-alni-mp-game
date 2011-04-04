
package projektarbete.physics;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GeomUtils {

    public static double lineSegDistSq(Line2D.Double line1, 
            Line2D.Double line2) {

        double distance;
        boolean l1NonZero = !line1.getP1().equals(line1.getP2());
        boolean l2NonZero = !line2.getP1().equals(line2.getP2());
        if (!l1NonZero && !l2NonZero) {
            distance = line1.getP1().distanceSq(line2.getP1());
        } else {
            if (line1.intersectsLine(line2)) {
                distance = 0;
            } else {
                double tempDist;
                distance = Double.POSITIVE_INFINITY;
                tempDist = line1.ptSegDistSq(line2.getP1());
                if (tempDist < distance) {
                    distance = tempDist;
                }
                tempDist = line1.ptSegDistSq(line2.getP2());
                if (tempDist < distance) {
                    distance = tempDist;
                }
                tempDist = line2.ptSegDistSq(line1.getP1());
                if (tempDist < distance) {
                    distance = tempDist;
                }
                tempDist = line2.ptSegDistSq(line1.getP2());
                if (tempDist < distance) {
                    distance = tempDist;
                }
            }
        }
        return distance;
    }

    public static double lineSegDist(Line2D.Double line1, Line2D.Double line2) {
        return Math.sqrt(lineSegDistSq(line1, line2));
    }

    public static Point2D.Double pointDiv(Point2D.Double point, double divisor) {
        return new Point2D.Double(point.x/divisor, point.y/divisor);
    }

    public static Point2D.Double pointDiv(Point2D.Double point1, Point2D.Double point2) {
        return new Point2D.Double(point1.x/point2.x, point1.y/point2.y);
    }

    public static Point2D.Double pointMul(Point2D.Double point, double factor) {
        return new Point2D.Double(point.x*factor, point.y*factor);
    }

    public static Point2D.Double pointMul(Point2D.Double point1, Point2D.Double point2) {
        return new Point2D.Double(point1.x*point2.x, point1.y*point2.y);
    }

    public static Point2D.Double pointAdd(Point2D.Double point, double term) {
        return new Point2D.Double(point.x+term, point.y+term);
    }

    public static Point2D.Double pointAdd(Point2D.Double point1, Point2D.Double point2) {
        return new Point2D.Double(point1.x+point2.x, point1.y+point2.y);
    }

    public static Point2D.Double pointSub(Point2D.Double point, double term) {
        return new Point2D.Double(point.x-term, point.y-term);
    }

    public static Point2D.Double pointSub(Point2D.Double point1, Point2D.Double point2) {
        return new Point2D.Double(point1.x-point2.x, point1.y-point2.y);
    }

    public static Point2D.Double pointRotate(Point2D.Double point, double rads) {
        return new Point2D.Double(point.x*Math.cos(rads)-point.y*Math.sin(rads),
                point.x*Math.sin(rads)+point.y*Math.cos(rads));
    }

    public static Point2D.Double normalize(Point2D.Double point) {
        double magnitude = vectorMagnitude(point);
        
        return new Point2D.Double(point.getX()/magnitude,
                point.getY()/magnitude);
    }

    public static double vectorMagnitude(Point2D.Double point) {
        return Math.sqrt((point.x*point.x)+(point.y*point.y));
    }

}
