/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author felix.hallqvist
 */
public class MyPolygon extends VisibleObject {

    protected Polygon polygon;

    
    public MyPolygon(VisibleObject visibleObject) {

        super(visibleObject);

        System.out.println("MyPolygon Initializing");

        initComponents();

        System.out.println("MyPolygon Initialized");

    }


    private void initComponents() {

        //TODO: Everything!!!
        polygon = new Polygon();
        position = new Coordinate(0,0);

    }

    //Add a new point in the polygon
     public void addPoint(double xPos, double yPos) {

        polygon.addPoint( (int)xPos, (int)yPos );

    }

    public void addPoint(Coordinate coordinate) {

        addPoint( coordinate.x(), coordinate.y() );

    }


    @Override
    public void setPos(Coordinate coordinate) {

        Coordinate delta = new Coordinate(position);
        delta.subtract(coordinate);
        
        position = coordinate;

        polygon.translate((int)delta.x(),(int)delta.y());
        
    }

    @Override
    public void offset(double xPos, double yPos) {

        double deltaX = xPos-position.x();
        double deltaY = yPos-position.y();

        polygon.translate((int)deltaX, (int)deltaY);

        super.offset(xPos, yPos);

    }

    @Override
    public void draw(Graphics g) {
        
        polygon.translate((int)(position.x()-parent.getPos().x()), (int)(position.y()-parent.getPos().y()));

        g.drawPolygon(polygon);

    }

}
