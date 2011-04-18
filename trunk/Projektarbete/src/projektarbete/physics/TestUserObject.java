/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import projektarbete.Coordinate;
import projektarbete.Flags;
import projektarbete.Stage;
import projektarbete.graphics.Camera;
import projektarbete.graphics.CameraContainer;
import projektarbete.graphics.MyImage;

/**
 *
 * @author niclas.alexandersso
 */

public class TestUserObject extends BasePhysics {

    public TestUserObject() {

        //Initializing variables

        position = new Coordinate(0,0);
        velocity = new Coordinate(0.5,0.10);
        force = new Coordinate(0,0);


        mass = 1;
        gravityDir = new Coordinate(0,-1);
        gravity = 9.82;
        gravityFlag = true;
        airFrictionFlag = true;
        size = 1;

        hitbox = new Hitbox();
        List<Point2D.Double> shape = new ArrayList<Point2D.Double>();
        shape.add(new Point2D.Double(1,0));
        shape.add(new Point2D.Double(0,0));
        shape.add(new Point2D.Double(1,1));
        hitbox.setShape(shape);
        hitbox.normalizeShape();
        size = 0.60;
        visibleObject = new MyImage(Camera.getInstance(), "TrollFace.png");
        visibleObject.centerAnchor();
        visibleObject.setScale(0.0043);
        container = new CameraContainer();
        container.setParent(visibleObject);
        container.setScale(Camera.getInstance().getScale());
        container.offset = new Coordinate(-(5 - ((MyImage) visibleObject).getWidth()/2),-(5 - ((MyImage) visibleObject).getHeight()/2));
        Camera.getInstance().setContainer(container);

    }

    CameraContainer container;

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
        if (Flags.getFlag("enter")) {
            this.applyUpdate(new PhysicsUpdate(new Coordinate(0,0),
                    new Coordinate(0.5,0.10), 0));
        }

        angle = this.velocity.x()/10/*+Math.PI*/;

        Point mouse = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouse,Stage.getInstance().jFrame);
        container.offset = new Coordinate(mouse.getX()/400,mouse.getY()/400).add(new Coordinate(-(5 - ((MyImage) visibleObject).getWidth()/2),-(5 - ((MyImage) visibleObject).getHeight()/2)));

    }

}
