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
        coordinate = new Coordinate(0,0);

    }

    @Override
    public void setPos(Coordinate coordinate) {

        Coordinate delta = new Coordinate(position.x(),position.y());
        delta.subtract(coordinate);


        position = coordinate;

        
        polygon.translate((int)delta.x(),(int)delta.y());
        
    }

    @Override
    public void offset(double xPos, double yPos) {

        double deltaX = xPos-x;
        double deltaY = yPos-y;

        polygon.translate((int)deltaX, (int)deltaY);

        super.offset(xPos, yPos);

    }

    @Override
    public void draw(Graphics g) {

        int parentX = 0;
        int parentY = 0;

        polygon.translate((int)x-parentX, (int)y-parentY);

        g.drawPolygon(polygon);

    }

}
