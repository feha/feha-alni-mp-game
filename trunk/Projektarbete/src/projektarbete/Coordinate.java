/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.awt.geom.Point2D;

/**
 *
 * @author niclas.alexandersso
 */
public class Coordinate {
private double x;
private double y;

    public Coordinate(double number) {
        x = number;
        y = number;
    }
    public Coordinate(double xPos, double yPos) {
        x = xPos;
        y = yPos;
    }
    public Coordinate(double[] pos) {
        x = pos[0];
        y = pos[1];
    }
    public Coordinate(Coordinate coordinate) {
        x = coordinate.x();
        y = coordinate.y();
    }
    public Coordinate(Point2D.Double point) {
        x = point.x;
        y = point.y;
    }

    //This lets us print this datatype.
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    public Point2D.Double toPoint2D() {
        return new Point2D.Double(x, y);
    }

    public double x() {
        return x;
    }
    public void x(double xPos) {
        x= xPos;
    }
    public double y() {
        return y;
    }
    public void y(double yPos) {
        y= yPos;
    }
    public double[] getPos() {
        return new double[] {x, y};
    }
    public void setPos(double xPos, double yPos) {
        x= xPos;
        y =yPos;
    }
    public void setPos(Coordinate coordinate) {
        x=coordinate.x();
        y=coordinate.y();
    }

    //mathematical operations

    //static methods

    //addition
    public static Coordinate add(Coordinate c1, Coordinate c2) {
        return new Coordinate(c1.x()+c2.x(), c1.y()+c2.y());
    }
    public static Coordinate add(Coordinate c, double x, double y) {
        return Coordinate.add(c, new Coordinate(x, y));
    }
    public static Coordinate add(double x, double y, Coordinate c) {
        return Coordinate.add(new Coordinate(x, y), c);
    }
    public static Coordinate add(double x1, double y1, double x2, double y2) {
        return Coordinate.add(new Coordinate(x1, y1), new Coordinate(x1, y1));
    }
    public static Coordinate add(Coordinate c, double[] pos) {
        return Coordinate.add(c, new Coordinate(pos));
    }
    public static Coordinate add(double[] pos, Coordinate c) {
        return Coordinate.add(new Coordinate(pos), c);
    }
    public static Coordinate add(double[] pos1, double[] pos2) {
        return Coordinate.add(new Coordinate(pos1), new Coordinate(pos2));
    }
    public static Coordinate add(Coordinate c, double number) {
        return Coordinate.add(c, new Coordinate(number));
    }

    //subtraction
    public static Coordinate sub(Coordinate c1, Coordinate c2) {
        return new Coordinate(c1.x()-c2.x(), c1.y()-c2.y());
    }
    public static Coordinate sub(Coordinate c, double x, double y) {
        return Coordinate.sub(c, new Coordinate(x, y));
    }
    public static Coordinate sub(double x, double y, Coordinate c) {
        return Coordinate.sub(new Coordinate(x, y), c);
    }
    public static Coordinate sub(double x1, double y1, double x2, double y2) {
        return Coordinate.sub(new Coordinate(x1, y1), new Coordinate(x1, y1));
    }
    public static Coordinate sub(Coordinate c, double[] pos) {
        return Coordinate.sub(c, new Coordinate(pos));
    }
    public static Coordinate sub(double[] pos, Coordinate c) {
        return Coordinate.sub(new Coordinate(pos), c);
    }
    public static Coordinate sub(double[] pos1, double[] pos2) {
        return Coordinate.sub(new Coordinate(pos1), new Coordinate(pos2));
    }
    public static Coordinate sub(Coordinate c, double number) {
        return Coordinate.sub(c, new Coordinate(number));
    }

    //multiplication
    public static Coordinate mul(Coordinate c1, Coordinate c2) {
        return new Coordinate(c1.x()*c2.x(), c1.y()*c2.y());
    }
    public static Coordinate mul(Coordinate c, double x, double y) {
        return Coordinate.mul(c, new Coordinate(x, y));
    }
    public static Coordinate mul(double x, double y, Coordinate c) {
        return Coordinate.mul(new Coordinate(x, y), c);
    }
    public static Coordinate mul(double x1, double y1, double x2, double y2) {
        return Coordinate.mul(new Coordinate(x1, y1), new Coordinate(x1, y1));
    }
    public static Coordinate mul(Coordinate c, double[] pos) {
        return Coordinate.mul(c, new Coordinate(pos));
    }
    public static Coordinate mul(double[] pos, Coordinate c) {
        return Coordinate.mul(new Coordinate(pos), c);
    }
    public static Coordinate mul(double[] pos1, double[] pos2) {
        return Coordinate.mul(new Coordinate(pos1), new Coordinate(pos2));
    }
    public static Coordinate mul(Coordinate c, double number) {
        return Coordinate.mul(c, new Coordinate(number));
    }

    //division
    public static Coordinate div(Coordinate c1, Coordinate c2) {
        return new Coordinate(c1.x()/c2.x(), c1.y()/c2.y());
    }
    public static Coordinate div(Coordinate c, double x, double y) {
        return Coordinate.div(c, new Coordinate(x, y));
    }
    public static Coordinate div(double x, double y, Coordinate c) {
        return Coordinate.div(new Coordinate(x, y), c);
    }
    public static Coordinate div(double x1, double y1, double x2, double y2) {
        return Coordinate.div(new Coordinate(x1, y1), new Coordinate(x1, y1));
    }
    public static Coordinate div(Coordinate c, double[] pos) {
        return Coordinate.div(c, new Coordinate(pos));
    }
    public static Coordinate div(double[] pos, Coordinate c) {
        return Coordinate.div(new Coordinate(pos), c);
    }
    public static Coordinate div(double[] pos1, double[] pos2) {
        return Coordinate.div(new Coordinate(pos1), new Coordinate(pos2));
    }
    public static Coordinate div(Coordinate c, double number) {
        return Coordinate.div(c, new Coordinate(number));
    }

    //other
    public static Coordinate rotate(Coordinate coordinate, double angle) {
        return new Coordinate(
            coordinate.x()*Math.cos(angle)-coordinate.y()*Math.sin(angle),
            coordinate.x()*Math.sin(angle)+coordinate.y()*Math.cos(angle)
        );
    }

    public static Coordinate normalized(Coordinate coordinate) {
        return Coordinate.div(coordinate, Coordinate.length(coordinate));
    }

    public static Coordinate round(Coordinate coordinate) {
        Coordinate returnCoord = new Coordinate(coordinate);

        returnCoord.setPos(Math.round(returnCoord.x()), Math.round(returnCoord.y()));

        return returnCoord;
    }

    public static Coordinate floor(Coordinate coordinate) {
        Coordinate returnCoord = new Coordinate(coordinate);

        returnCoord.setPos(Math.floor(returnCoord.x()), Math.floor(returnCoord.y()));

        return returnCoord;
    }

    public static double length2(Coordinate coordinate) {
        double length2 = Math.pow(coordinate.x(), 2) + Math.pow(coordinate.y(), 2);

        return length2;
    }

    public static double length(Coordinate coordinate) {
        double length = Math.sqrt(Coordinate.length2(coordinate));

        return length;
    }

    //non-static methods

    //addition
    public Coordinate add(Coordinate c) {
        return Coordinate.add(this, c);
    }
    public Coordinate add(double x, double y) {
        return add(new Coordinate(x,y));
    }
    public Coordinate add(double number) {
        return add(new Coordinate(number));
    }
    public Coordinate add(double[] pos) {
        return add(new Coordinate(pos));
    }

    //subtraction
    public Coordinate sub(Coordinate c) {
        return Coordinate.sub(this, c);
    }
    public Coordinate sub(double x, double y) {
        return sub(new Coordinate(x,y));
    }
    public Coordinate sub(double number) {
        return sub(new Coordinate(number));
    }
    public Coordinate sub(double[] pos) {
        return sub(new Coordinate(pos));
    }

    //multiplication
    public Coordinate mul(Coordinate c) {
        return Coordinate.mul(this, c);
    }
    public Coordinate mul(double x, double y) {
        return mul(new Coordinate(x,y));
    }
    public Coordinate mul(double number) {
        return mul(new Coordinate(number));
    }
    public Coordinate mul(double[] pos) {
        return mul(new Coordinate(pos));
    }

    //division
    public Coordinate div(Coordinate c) {
        return Coordinate.div(this, c);
    }
    public Coordinate div(double x, double y) {
        return div(new Coordinate(x,y));
    }
    public Coordinate div(double number) {
        return div(new Coordinate(number));
    }
    public Coordinate div(double[] pos) {
        return div(new Coordinate(pos));
    }

    //other
    public Coordinate rotate(double angle) {
        return Coordinate.rotate(this, angle);
    }
}
