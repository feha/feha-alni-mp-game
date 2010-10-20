/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author felix.hallqvist
 */
public class BasePhysics {

    public BasePhysics () {

        System.out.println("BasePhysics Initializing");

        initComponents();

        System.out.println("BasePhysics Initialized");

    }

    private double xPos;
    private double yPos;
    private double[] pos;
    private double xVel;
    private double yVel;
    private double friction;

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

    }

    public void PhysicsSimulate() {

        xVel-= friction * xVel;
        yVel-= friction * yVel;

        xPos+= xVel;
        yPos+= yVel;

    }

    public void move(double x, double y) {

        xPos+= x;
        yPos+= y;

        pos[0]+= x;
        pos[1]+= y;

    }

    public void setPos(double x, double y) {

        xPos = x;
        yPos = y;

        pos[0] = x;
        pos[1] = y;

    }

    public double[] getPos() {

        return pos;

    }
    
}
