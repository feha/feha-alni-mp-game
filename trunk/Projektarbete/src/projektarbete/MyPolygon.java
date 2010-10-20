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

    public Polygon polygon;

    
    public MyPolygon(VisibleObject visibleObject) {

        super(visibleObject);

        System.out.println("MyPolygon Initializing");

        initComponents();

        System.out.println("MyPolygon Initialized");

    }


    private void initComponents() {

        //TODO: Everything!!!
        polygon = new Polygon();

    }

    @Override
    public void draw(Graphics g) {

        g.drawPolygon(polygon);

    }

}
