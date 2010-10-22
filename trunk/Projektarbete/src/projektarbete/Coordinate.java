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

    public Coordinate(double number) {
        x = number;
        y = number;
    }
    public Coordinate(double xPos, double yPos) {
        x = xPos;
        y = yPos;
    }
    public Coordinate(Coordinate coordinate) {
        x = coordinate.x();
        y = coordinate.y();
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

    
    public Coordinate getAdd(Coordinate coordinate) {
        double[] pos = coordinate.getPos();
        
        return new Coordinate( x+pos[0], y+pos[1] );
    }
    public Coordinate getAdd(double number) {
        return new Coordinate( x+number, y+number );
    }
    public void add(Coordinate coordinate) {
        double[] pos = coordinate.getPos();
        x= x+pos[0];
        y= y+pos[1];
    }
    public void add(double number) {
        x+= number;
        y+= number;
    }

    public Coordinate getSub(Coordinate coordinate) {
        double[] pos = coordinate.getPos();

        return new Coordinate( x-pos[0], y-pos[1] );
    }
    public Coordinate getSub(double number) {
        return new Coordinate( x-number, y-number );
    }
    public void subtract(Coordinate coordinate) {
        double[] pos = coordinate.getPos();
        x= x-pos[0];
        y= y-pos[1];
    }
    public void subtract(double number) {
        x-= number;
        y-= number;
    }

    public Coordinate getMul(Coordinate coordinate) {
        double[] pos = coordinate.getPos();

        return new Coordinate( x*pos[0], y*pos[1] );
    }
    public Coordinate getMul(double number) {
        return new Coordinate( x*number, y*number );
    }
    public void multiply(Coordinate coordinate) {
        double[] pos = coordinate.getPos();
        x*= pos[0];
        y*= pos[1];
    }
    public void multiply(double number) {
        
        x*= number;
        y*= number;
    }

    public Coordinate getDiv(Coordinate coordinate) {
        double[] pos = coordinate.getPos();

        return new Coordinate( x/pos[0], y/pos[1] );
    }
    public Coordinate getDiv(double number) {
        return new Coordinate( x/number, y/number );
    }
    public void divide(Coordinate coordinate) {
        double[] pos = coordinate.getPos();
        x= x*pos[0];
        y= y*pos[1];
    }
    public void divide(double number) {
        x/= number;
        y/= number;
    }
}
