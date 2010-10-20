/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author felix.hallqvist
 */
public class BasePhysics {

    public BasePhysics () {

        System.out.println("BasePhysics Initializing");

        initComponents();
        initTesting();

        System.out.println("BasePhysics Initialized");

    }

    double xPos;
    double yPos;
    double[] pos;
    double xVel;
    double yVel;
    double friction;

    VisibleObject visibleObject;


    boolean timerStarted = false;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        public void run() {
            physicsSimulate();
        }
    };

    private void initComponents() {

        //Initializing variables
        xPos = 0;
        yPos = 0;
        pos = new double[2];
        pos[0] = xPos;
        pos[1] = yPos;

        xVel = 0;
        yVel = 0;

        friction = 0.75;

        physicsSimulate();

    }

    private void initTesting() {

        visibleObject = new Hexagon(Camera.getInstance());

    }

    public void updateGraphic() {

        visibleObject.x = (int)xPos;
        visibleObject.y = (int)yPos;
        
    }

    public void physicsSimulate() {

        if (!timerStarted) {
            timer.scheduleAtFixedRate(timerTask, 20, 20); //20 ms = 50 fps
            timerStarted = true;
        }

        xVel-= friction * xVel;
        yVel-= friction * yVel;

        xPos+= xVel;
        yPos+= yVel;

        updateGraphic();

    }

    public void move(double x, double y) {

        xPos+= x;
        yPos+= y;

        pos[0]+= x;
        pos[1]+= y;

        updateGraphic();

    }

    public void setPos(double x, double y) {

        xPos = x;
        yPos = y;

        pos[0] = x;
        pos[1] = y;

        updateGraphic();

    }

    public double[] getPos() {

        return pos;

    }
    
}
