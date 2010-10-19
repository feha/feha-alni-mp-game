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

    private Polygon polygon;

    
    public MyPolygon() {

        System.out.println("MyPolygon Initializing");

        initComponents();

        System.out.println("MyPolygon Initialized");

    }


    private void initComponents() {

        polygon = new Polygon();
            polygon.addPoint(0, 0);
            polygon.addPoint(0, 100);
            polygon.addPoint(100, 50);

    }

    @Override
    public void draw(Graphics g) {

        if (fixed) {
            for (int i = 0; i < polygon.npoints; i++) {
                System.out.println(polygon.xpoints[i] - parent.x);
                System.out.println(polygon.ypoints[i] - parent.y);
            }
        } else {
            for (int i = 0; i < polygon.npoints; i++) {
                System.out.println(polygon.xpoints);
                System.out.println(polygon.ypoints);
            }
        }

        g.drawPolygon(polygon);

    }

}
