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
    double[] vel;
    double xAcc;
    double yAcc;
    double[] acc;
    double mass;
    double airFriction;
    double gravity;
    boolean gravityFlag;
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
        airFriction = 0.5;
        gravity = 9.82;
        gravityFlag = true;

        deltaTime = ((double)20)/((double)1000);

        physicsSimulate();
        
    }

    private void initTesting() {

        visibleObject = new Hexagon(Camera.getInstance());

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
        System.out.println(yVel);
        
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
