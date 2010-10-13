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

    //Constructor
    public Stage () {

        System.out.println("Stage Initializing");
        //If I do not init globals before I init components I cause an infinite
        //loop, as components create a painter, which try to get the global
        //instance, which doesnt exist so it creates a new Stage.
        initGlobals();
        initComponents();
        initTesting();

        System.out.println("Stage Initialized");

    }


    //Global variables
    private static Stage _instance;

    //Networked variables
    private Painter painter;
    

    private void initGlobals() {

        _instance = this;

    }

    private void initComponents() {

        JFrame jFrame = new JFrame();

        jFrame.setVisible(true);
        jFrame.setSize(500, 500);
        jFrame.setResizable(false);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        painter = new Painter();

        jFrame.getContentPane().add(painter);

        //adds a frame to handle keyboard input
        jFrame.add(new Input());

    }

    private void initTesting() {

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

        movePolygon(0, 100, 100);
        movePolygon(1, 100, 100);



        VisibleObject v0 = new VisibleObject();
        VisibleObject v1 = new VisibleObject();
        VisibleObject v2 = new VisibleObject();
        VisibleObject v3 = new VisibleObject();
        VisibleObject v4 = new VisibleObject();
        v4.addToStage();
        VisibleObject v5 = new VisibleObject();
        VisibleObject v6 = new VisibleObject();
        VisibleObject v7 = new VisibleObject();
        VisibleObject v8 = new VisibleObject();
        v4.addToStage();
        VisibleObject v9 = new VisibleObject();

        for (int i = 0; i < visibleObjects.length; i++) {
            if (visibleObjects[i] == null) {
                break;
            }

            System.out.println("[" + i + "] " + visibleObjects[i]);
        }

        System.out.println("Removing");
        VisibleObject.removeVObjectFromTable(visibleObjects, v4);
        System.out.println("Removed");

        for (int i = 0; i < visibleObjects.length; i++) {
            if (visibleObjects[i] == null) {
                break;
            }

            System.out.println("[" + i + "] " + visibleObjects[i]);
        }

    }

    public static Stage getInstance() {

        //Double Check Locking pattern makes it only create new stage if it does
        //not exist already, and stops threading from beign able to mess it up.
        if (_instance == null) {
            synchronized(Stage.class) {
                if (_instance == null) {
                    _instance = new Stage();
                }
            }
        }

        return _instance;

    }

    //Something like this, will make it work soon
    LinkedList<VisibleObject> visibleObjects = new LinkedList<VisibleObject>(); //should check into making it have unlimited length
    int objectCount = 0; //arrays start at 0 in java

    public void addVisibleObject( VisibleObject visibleObject ) {
        
        visibleObjects.add(visibleObject);
        
        objectCount++;

    }

    public void removeVisibleObject( VisibleObject visibleObject ) {

        while (visibleObjects.remove(visibleObject)) {}

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

    public void movePolygon( int index, int x, int y) {

        Polygon poly = polygons[index];

        //translate is a move function for polygons
        //They lack positioning, but this is same thing except local to the poly
        poly.translate(x, y);

    }

    public Polygon getPolygon( int index ) {
        return polygons[index];

    }

    public int getPolygonCount() {

        return count;

    }

}
