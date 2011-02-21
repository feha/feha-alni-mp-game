/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import projektarbete.Coordinate;

/**
 *
 * @author felix.hallqvist
 */
public class MyRectangle2D extends MyPolygon {

    protected double width;
    protected double height;


    public MyRectangle2D(VisibleObject visibleObject) {

        super(visibleObject);

        System.out.println("Rectangle Initializing");

        initComponents();

        System.out.println("Rectangle Initialized");

    }

    private void initComponents() {
        width=12;
        height=12;

        points = new Coordinate[]{
            new Coordinate(-width, -height),
            new Coordinate(width, -height),
            new Coordinate(width, height),
            new Coordinate(-width, height)};
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        
        //g.fillPolygon(polygon);
    }

}
