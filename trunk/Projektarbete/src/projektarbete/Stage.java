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
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import projektarbete.graphics.Painter;
import projektarbete.graphics.VisibleObject;
import projektarbete.graphics.Camera;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import projektarbete.physics.PhysicsObject;
import projektarbete.physics.Bouncer;
import projektarbete.physics.Floor;
import projektarbete.physics.ObjectData;
import projektarbete.physics.PhysicsEngine;
import projektarbete.physics.PhysicsUpdate;
import projektarbete.physics.TestUserObject;
import projektarbete.physics.ObjectUpdate;
import projektarbete.physics.PhysicsData;
import projektarbete.physics.UpdateTester;

public class Stage {
    PhysicsEngine engine;
    //Constructor
    public Stage () {

        System.out.println("Stage Initializing");
        //If I do not init globals before I init components I cause an infinite
        //loop, as components create a painter, which try to get the global
        //instance, which doesnt exist so it creates a new Stage.
        initGlobals();
        initComponents();
        /*initTesting();
        engine.start();*/

        System.out.println("Stage Initialized");

    }


    //Global variables
    private static Stage _instance;
    private static boolean server;
    private static boolean running;

    //Networked variables
    Painter painter;
    public JFrame jFrame;


    private void initGlobals() {

        _instance = this;

    }

    private void initComponents() {

        jFrame = new JFrame();

        jFrame.setSize(500, 500);
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        jFrame.setUndecorated(true);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        KeyListener k = new KeyListener() {

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                //System.out.println(e);
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        };

        MouseListener m = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                //System.out.println(e);
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        };

        jFrame.addKeyListener(k);
        jFrame.addMouseListener(m);

        //adds a frame to handle keyboard input. DONT add it after painter!
        
        //jFrame.add(input);

        //Adds a panel to handle painting. DONT add it before input!
        painter = new Painter();
        jFrame.getContentPane().add(painter);
        Input input = new Input(painter);

        engine = new PhysicsEngine();
        SocketListener.setListening(true);
        SocketListener.listen();
        

        //PhysicsEngine physicsEngine = new PhysicsEngine();

    }

    public static void start() {
        Camera camera = new Camera();
        camera.setScale(50);
        getInstance().initTesting();
        getInstance().engine.start();
        running = true;
    }

    private void initTesting() {

        //engine.addObject(new Floor(0, 0, 6));
        
        //engine.addObject(new Floor(3,-6));
        //engine.addObject(new Floor(1,-6));

        //engine.addObject(new Bouncer(5, -2, 0, 0, 1), (short)5);
        //engine.addObject(new Bouncer(6.5, -2, 0, 0, 1), (short)6);

        double radius = 11;
        for (double m = 0; m <= Math.PI*2; m += 1.0/radius) {
            engine.addObject(new Floor(radius*Math.sin(m),radius*Math.cos(m)));
            //engine.addObject(new Floor(radius*-Math.sin(t),radius*-Math.cos(t)));
        }
        /*radius = 6;
        for (double t = 0; t <= Math.PI*2+2.0/radius; t += 1.0/radius) {
            engine.addObject(new Floor(radius*Math.sin(t),radius*Math.cos(t)));
            //engine.addObject(new Floor(radius*-Math.sin(t),radius*-Math.cos(t)));
        }*/


        

        StartMenu m0 = new StartMenu();

        List<ObjectUpdate> updates = new ArrayList<ObjectUpdate>();

        updates.add(new ObjectUpdate(new PhysicsUpdate(5, -2, 0, 0, 0, 0), (short)5));
        updates.add(new ObjectUpdate(new PhysicsUpdate(5, -3.1, 0, 0, 0, 0), (short)6));
        List<ObjectData> dataz = new ArrayList<ObjectData>();
        dataz.add(new ObjectData(new PhysicsData(), (short)5));
        dataz.add(new ObjectData(new PhysicsData(), (short)6));


        UpdateTester resetter = new UpdateTester(updates, engine);
        UpdateTester resetter2 = new UpdateTester(dataz, engine, false);
        //resetter2.start(2000);
        //resetter.start(2000);
        //UpdateTester resetter = new UpdateTester(updates, engine);
        //resetter.start(2000);
        engine.addObject(new TestUserObject());

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

        while (visibleObjects.remove(visibleObject)) {
            objectCount--;
        }

    }

    public VisibleObject getVisibleObject( int index ) {
        return visibleObjects.get(index);

    }

    public int getVisibleObjectCount() {

        return objectCount;

    }

    public static boolean isServer() {
        return server;
    }

    public static void setServer(boolean server) {
        Stage.server = server;
    }

    public static boolean isRunning() {
        return running;
    }

    public void repaint() {
        painter.repaint();
    }

}
