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
import javax.swing.JFrame;
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

public class TestUserObject extends PhysicsObject {

    public TestUserObject() {
        this(new PhysicsData(Templates.TYPE_PLAYER_OBJECT, 60, 1, new PhysicsUpdate()));

        //Initializing variables


    }

    public TestUserObject(PhysicsData data) {
        super(data);
        container = new CameraContainer();
        container.setScale(Camera.getInstance().getScale());
        container.offset = new Coordinate(-(5 - ((MyImage) visibleObject).getWidth()/2),-(5 - ((MyImage) visibleObject).getHeight()/2));
        Camera.getInstance().setContainer(container);
    }

    CameraContainer container;

    @Override
    public void physicsForces() {
        Updates.update(this);

        //Coordinate offset = new Coordinate(mouse.getX()/400,mouse.getY()/400).add(new Coordinate(-(5 - ((MyImage) visibleObject).getWidth()/2),-(5 - ((MyImage) visibleObject).getHeight()/2)));
        //container.offset.setPos(monitorSize.div(-100).add(size).add(mousePos.add(monitorSize.div(2)).div(100)));
        //container.offset.setPos(offset);

        if (Flags.getFlag("up")) {
            applyForce(0, 20 * mass /* deltaTime*/);
        }
        if (Flags.getFlag("down")) {
            applyForce(0, -5 * mass /* deltaTime*/);
        }
        if (Flags.getFlag("left")) {
            applyForce(-20 * mass /* deltaTime*/, 0);
        }
        if (Flags.getFlag("right")) {
            applyForce(20 * mass/* * deltaTime*/, 0);
        }
        if (Flags.getFlag("enter")) {
            Flags.setFlag("enter", false);
            this.applyUpdate(new PhysicsUpdate(new Coordinate(0,0),
                    new Coordinate(0.5,0.10), 0, 0));
        }
        if (Flags.getFlag("mouse1")) {
            Flags.setFlag("mouse1", false);
            
            JFrame jFrame = Stage.getInstance().jFrame;
            Point mouse = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(mouse,Stage.getInstance().jFrame);
            Coordinate mousePos = new Coordinate(mouse.getX(), mouse.getY());
            mousePos = mousePos.mul(new Coordinate(1, -1));
            Coordinate monitorSize = new Coordinate(jFrame.getWidth(), jFrame.getHeight());
            monitorSize = monitorSize.mul(new Coordinate(1, -1));
            mousePos = mousePos.sub(monitorSize.div(2));
            mousePos = Coordinate.normalized(mousePos);
            PhysicsUpdate update = this.getUpdate();
            update.setVelocity(new Coordinate(mousePos.mul(60)));
            PhysicsEngine.getInstance().addObject(new Bullet(update, 0.1, 0.1));
            this.applyImpulse(mousePos.mul(60).mul(0.1).mul(-1));
        }

        angle = (angle*3+this.velocity.x()/20)/4/*+Math.PI*/;

    }

    @Override
    public void updateGraphic() {
        super.updateGraphic();

        JFrame jFrame = Stage.getInstance().jFrame;
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouse,Stage.getInstance().jFrame);
        Coordinate mousePos = new Coordinate(mouse.getX(), mouse.getY());
        mousePos.setPos(mousePos.mul(new Coordinate(1, 1)));
        Coordinate monitorSize = new Coordinate(jFrame.getWidth(), jFrame.getHeight());
        //mousePos.setPos(mousePos.sub(monitorSize.div(1)));

        
        Coordinate center = new Coordinate(
                ((this.position.x()))*-50+(monitorSize.x()/2),
                ((this.position.y()))*50+(monitorSize.y()/2));
        Coordinate mouseCenterDistance = monitorSize.div(2).sub(mousePos);
        container.setPos(center.add(mouseCenterDistance.mul(0.5)));
    }


}
