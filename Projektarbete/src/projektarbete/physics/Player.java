/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import projektarbete.Communication;
import projektarbete.Coordinate;
import projektarbete.Flags;
import projektarbete.Stage;
import projektarbete.UDPSocket;

/**
 *
 * @author felix.hallqvist
 */
public class Player extends PhysicsObject {

    public Player(String ip) {
        super(Templates.TYPE_PLAYER_OBJECT, 60, 1);

        //Initializing variables
        clientAddress = ip;
        position = new Coordinate(-8,0);

        //Add the player to the player list and then a localplayer on its client
        Players.addPlayer(this);
        
        ObjectData data = new ObjectData(this.getData(),this.getId());
        UDPSocket.send(new Communication(clientAddress, Communication.writePlayerData(data)));

    }

    String clientAddress = "";

    @Override
    public void physicsForces() {

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

}
