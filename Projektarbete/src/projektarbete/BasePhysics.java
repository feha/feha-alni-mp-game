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

    protected double xPos;
    protected double yPos;
    protected double[] pos;
    protected double xVel;
    protected double yVel;
    protected double[] vel;
    protected double xAcc;
    protected double yAcc;
    protected double[] acc;
    protected double mass;
    protected double airFriction;
    protected double gravity;
    protected boolean gravityFlag;
    protected boolean frozenFlag;
    double deltaTime;

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
        vel = new double[2];
        vel[0] = xVel;
        vel[1] = yVel;

        xAcc = 0;
        yAcc = 0;
        acc = new double[2];
        acc[0] = xAcc;
        acc[1] = yAcc;

        mass = 100;
        airFriction = 0.25;
        gravity = 9.82;
        gravityFlag = true;
        frozenFlag = false;

        //If you skip converting each number to double it will first count as if its integers, then convert answer to a double.
        deltaTime = ((double)20)/((double)1000);

        physicsSimulate();
        
    }

    private void initTesting() {

        visibleObject = new Hexagon(Camera.getInstance());
        visibleObject.offset(250, 0);
        try {
            Hook.add(1, getClass().getMethod("hookTest", new Class[] {String.class}), this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Hook.call(1,"test");
    }

    public void hookTest(String test) {
        System.out.println(test);
    }

    public void updateGraphic() {

        if (visibleObject != null) {
            visibleObject.setPos(xPos, yPos);
        }

    }

    public void physicsSimulate() {

        if (!timerStarted) {
            timer.scheduleAtFixedRate(timerTask, 20, 20); //20 ms = 50 fps
            timerStarted = true;
        }

        if (!frozenFlag) {
            if (gravityFlag) {
                yAcc-= gravity * deltaTime;
            }

            //Velocity and accerelation
            xAcc-= (airFriction * xVel)/mass;
            yAcc-= (airFriction * yVel)/mass;

            xVel+= xAcc;
            yVel+= yAcc;
            vel[0] = xVel;
            vel[1] = yVel;

            xAcc = 0;
            yAcc = 0;
            acc[0] = xAcc;
            acc[1] = yAcc;

            //Position
            xPos+= xVel * deltaTime;
            yPos+= -yVel * deltaTime;
            pos[0]+= xPos;
            pos[1]+= yPos;
        }

        updateGraphic();

    }
    
    public void move(double x, double y) {

        xPos+= x;
        yPos+= y;

        pos[0]+= xPos;
        pos[1]+= yPos;

        updateGraphic();

    }

    //Position
    public void setPos(double x, double y) {

        xPos = x;
        yPos = y;

        pos[0] = xPos;
        pos[1] = yPos;

        updateGraphic();

    }

    public double[] getPos() {

        return pos;

    }

    //Velocity
    public void setVel(double x, double y) {

        xVel = x;
        yVel = y;

        vel[0] = xVel;
        vel[1] = yVel;
        
    }

    public double[] getVel() {

        return vel;

    }

    //Accerelation and force.
    public void applyForce(double x, double y) {

        xAcc+= x / mass;
        xAcc+= y / mass;

        acc[0] = xAcc;
        acc[1] = yAcc;

    }

    public void AddAcc(double x, double y) {

        xAcc+= x;
        xAcc+= y;

        acc[0] = xAcc;
        acc[1] = yAcc;

    }

    public void setAcc(double x, double y) {

        xAcc = x;
        xAcc = y;

        acc[0] = xAcc;
        acc[1] = yAcc;

    }

    public double[] getAcc() {

        return acc;

    }

}