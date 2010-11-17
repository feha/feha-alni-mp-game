/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author niclas.alexandersso
 */

public class TestUserObject extends BasePhysics {

    public TestUserObject () {

        System.out.println("TestUserObject Initializing");

        initComponents();
        initTesting();

        System.out.println("TestUserObject Initialized");

    }


    private void initComponents() {

        //Initializing variables

        coordinates = new Coordinate(0,0);
        velocity = new Coordinate(0.5,0.10);
        force = new Coordinate(0,0);


        mass = 100;
        airFriction = 0.02;
        gravityDir = new Coordinate(0,-1);
        gravity = 9.82;
        gravityFlag = false;
        airFrictionFlag = true;
        frozenFlag = false;
        scale = 1;

    }

    private void initTesting() {

        visibleObject = new MyRectangle2D(Camera.getInstance());
        visibleObject.setScale(0.05);

    }

    @Override
    public void physicsForces() {

        if (Flags.getFlag("up")) {
            applyForce(0, 20 * mass * deltaTime);
        }
        if (Flags.getFlag("down")) {
            applyForce(0, -20 * mass * deltaTime);
        }
        if (Flags.getFlag("left")) {
            applyForce(-20 * mass * deltaTime, 0);
        }
        if (Flags.getFlag("right")) {
            applyForce(20 * mass * deltaTime, 0);
        }

        angle+= Coordinate.length(velocity)*deltaTime;
        
    }

}
