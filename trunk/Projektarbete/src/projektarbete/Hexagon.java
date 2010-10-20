/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author felix.hallqvist
 */
public class Hexagon extends MyPolygon {

    public Hexagon(VisibleObject visibleObject) {

        super(visibleObject);

        System.out.println("Hexagon Initializing");

        initComponents();

        System.out.println("Hexagon Initialized");

    }

    private void initComponents() {
        polygon.addPoint(0, 35);
        polygon.addPoint(0, 85);
        polygon.addPoint(35, 120);
        polygon.addPoint(85, 120);
        polygon.addPoint(120, 85);
        polygon.addPoint(120, 35);
        polygon.addPoint(85, 0);
        polygon.addPoint(35, 0);
    }

}
