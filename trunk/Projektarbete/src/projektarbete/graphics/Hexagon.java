/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.graphics;

import projektarbete.Coordinate;

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
        points = new Coordinate[]{
            new Coordinate(-12, -5),
            new Coordinate(-12, 5),
            new Coordinate(-5, 12),
            new Coordinate(5, 12),
            new Coordinate(12, 5),
            new Coordinate(12, -5),
            new Coordinate(5, -12),
            new Coordinate(-5, -12)};
    }

}
