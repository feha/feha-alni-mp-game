/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author felix.hallqvist
 */

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Stage {
    
    //Networked variables
    private Painter painter;
    //private Reference visibleStuff;

    //Constructor
    public Stage () {
        System.out.println("Stage Started");


        JFrame jFrame = new JFrame();

        jFrame.setVisible(true);

        jFrame.setSize(500, 500);
        

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        painter = new Painter();

        Container container = jFrame.getContentPane();
        container.add(painter);
        

        Polygon poly1 = new Polygon();
            poly1.addPoint(0,0);
            poly1.addPoint(50,0);
            poly1.addPoint(25,50);

        Polygon poly2 = new Polygon();
            poly2.addPoint(75,75);
            poly2.addPoint(125,75);
            poly2.addPoint(100,125);

        createPolygon(poly1);
        createPolygon(poly2);
        
        movePolygon(0);
        movePolygon(1);

        
        
        VisibleObject v1 = new VisibleObject();
        /*VisibleObject v2 = new VisibleObject();
        VisibleObject v3 = new VisibleObject();
        VisibleObject v4 = new VisibleObject();
        VisibleObject v5 = new VisibleObject();
        VisibleObject v6 = new VisibleObject();
        VisibleObject v7 = new VisibleObject();
        VisibleObject v8 = new VisibleObject();
        VisibleObject v9 = new VisibleObject();
        VisibleObject v0 = new VisibleObject();*/

        for (int i = 0; i < visibleObjects.length; i++) {
            if (visibleObjects[i] == null) {
                break;
            }

            System.out.println(visibleObjects[i]);
        }

        //VisibleObject.removeObject(visibleObjects, v4);

        for (int i = 0; i < visibleObjects.length; i++) {
            if (visibleObjects[i] == null) {
                break;
            }

            System.out.println(visibleObjects[i]);
        }

    }

    VisibleObject[] visibleObjects = new VisibleObject[10^6]; //should check into making it have unlimited length
    int objectCount = 0; //arrays start at 0 in java

    public void addVisibleObject( VisibleObject visibleObject ) {
        System.out.println(visibleObject);
        visibleObjects[objectCount] = visibleObject;

        objectCount++;

    }

    public void removeVisibleObject( VisibleObject visibleObject ) {

        for (int i = 0; i < visibleObjects.length; i++) {
            if (visibleObjects[i] == visibleObject) {
                visibleObjects[i] = null;
                break;
            }
        }

        objectCount--;

    }

    Polygon[] polygons = new Polygon[10^6]; //should check into making it have unlimited length
    int count = 0; //arrays start at 0 in java

    public void createPolygon( Polygon poly ) {
        //debugging
        /*
        System.out.println(polygons);
        System.out.println(polygons.length);
        System.out.println(count);
        */

        //fill the array with new polys already created so we can create them in a class outside this
        polygons[count] = poly;

        count++;
    }

    public void movePolygon( int index ) {

        Polygon poly = polygons[index];

        //translate is a move function for polygons
        //They lack positioning, but this is same thing except local to the poly
        poly.translate(100, 100);

    }

    public Polygon getPolygon( int index ) {
        
        return polygons[index];

    }

    public int getPolygonCount() {

        return count;

    }

}
