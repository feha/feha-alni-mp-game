/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author felix.hallqvist
 */

//import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

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

        //adds a frame to handle keyboard input. DONT add it after painter!
        Input input = new Input();
        jFrame.add(input);

        //Adds a panel to handle painting. DONT add it befor input!
        painter = new Painter();
        jFrame.getContentPane().add(painter);
        
        Camera camera = new Camera();

    }

    private void initTesting() {

        MyPolygon v0 = new MyPolygon(Camera.getInstance());

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

    //Makes a list of visibleobjects, which is good because of its remove functions.
    private LinkedList<VisibleObject> visibleObjects = new LinkedList<VisibleObject>();
    private int objectCount = 0; //arrays start at 0 in java

    public void addVisibleObject( VisibleObject visibleObject ) {
        
        visibleObjects.add(visibleObject);
        
        objectCount++;

    }

    public void removeVisibleObject( VisibleObject visibleObject ) {

        while (visibleObjects.remove(visibleObject)) {}

        objectCount--;

    }

    public VisibleObject getVisibleObject( int index ) {
        return visibleObjects.get(index);

    }

    public int getVisibleObjectCount() {

        return objectCount;

    }
    
}
