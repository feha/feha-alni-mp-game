/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import projektarbete.Coordinate;
import projektarbete.Flags;
import projektarbete.graphics.Camera;
import projektarbete.graphics.MyImage;

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

        position = new Coordinate(0,0);
        velocity = new Coordinate(0.5,0.10);
        force = new Coordinate(0,0);


        //mass = 100;
        gravityDir = new Coordinate(0,-1);
        gravity = 9.82;
        gravityFlag = true;
        airFrictionFlag = true;
        frozenFlag = false;
        scale = 1;

        hitbox = new Hitbox();
        List<Point2D.Double> shape = new ArrayList<Point2D.Double>();
        shape.add(new Point2D.Double(1,0));
        shape.add(new Point2D.Double(0,0));
        shape.add(new Point2D.Double(1,1));
        hitbox.setShape(shape);
        hitbox.normalizeShape();
        scale = 0.60;

    }

    private void initTesting() {

        visibleObject = new MyImage(Camera.getInstance());
        visibleObject.setImage("TrollFace.png");
        visibleObject.centerAnchor();
        visibleObject.setScale(0.0043);

    }

    @Override
    public void physicsForces() {

        if (Flags.getFlag("up")) {
            applyForce(0, 20 * mass /* deltaTime*/);
        }
        if (Flags.getFlag("down")) {
            applyForce(0, -5 * mass /* deltaTime*/);
        }
        if (Flags.getFlag("left")) {
            applyForce(-10 * mass /* deltaTime*/, 0);
        }
        if (Flags.getFlag("right")) {
            applyForce(20 * mass/* * deltaTime*/, 0);
        }

        angle = this.velocity.x()/10/*+Math.PI*/;
        
    }

}
