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
    protected Coordinate[] points;

    
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



    //Currently, scale affect both size and position of polygon.
    //Should it maybe start affect only size and sub polygons local coord system?

    //Scale affects the size of the polygon, and the child visible objects position
    public void updatePolygon() {
        polygon.reset();
        
        if (points != null) {
            for (int i=0; i < points.length; i++) {

                Coordinate pointPos = Coordinate.rotate( points[i], totalAngle );

                pointPos = drawPos.add(pointPos.mul(sizeScale)); //size poly with scale
                polygon.addPoint((int) pointPos.x(), (int) pointPos.y());
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        
        updatePolygon();
        g.drawPolygon(polygon);

    }

}
