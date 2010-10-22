/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author niclas.alexandersso
 */
public class Coordinate {
private double x;
private double y;

    public Coordinate(double xPos, double yPos) {
        x=xPos;
        y=yPos;
    }
    public double x() {
        return x;
    }
    public void x(double xPos) {
        x=xPos;
    }
    public double y() {
        return y;
    }
    public void y(double yPos) {
        y=yPos;
    }
    public double[] get() {
        return new double[] {x, y};
    }
    public void set(double xPos, double yPos) {
        x=xPos;
        y=yPos;
    }
    public void offset(double xOffset, double yOffset) {
        x+=xOffset;
        y+=yOffset;
    }
    public void xOffset(double xOffset) {
        x+=xOffset;
    }
    public void yOffset(double yOffset) {
        y+=yOffset;
    }
    public void add(Coordinate coordinate) {
        double[] xy = coordinate.get();
        x=x+xy[0];
        y=y+xy[1];
    }
    public void subtract(Coordinate coordinate) {
        double[] xy = coordinate.get();
        x=x-xy[0];
        y=y-xy[1];
    }
    public void multiply(Coordinate coordinate) {
        double[] xy = coordinate.get();
        x=x*xy[0];
        y=y*xy[1];
    }
    public void divide(Coordinate coordinate) {
        double[] xy = coordinate.get();
        x=x*xy[0];
        y=y*xy[1];
    }
}
