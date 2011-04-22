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

    private String clientAddress = "";
    private byte flags = 0;
    public static final byte FLAG_UP = 1 << 0; //this is 0000 0001
    public static final byte FLAG_DOWN = 1 << 1; //above except 1 step to left
    public static final byte FLAG_LEFT = 1 << 2; //you know this by now... (4)
    public static final byte FLAG_RIGHT = 1 << 3; //8
    public static final byte FLAG_ENTER = 1 << 4; //16
    public static final byte FLAG_MOUSE1 = 1 << 5; //Oh come on...
    private PlayerUpdate nextUpdate;

    public Player(String address) {
        this(address, new PhysicsData(Templates.TYPE_PLAYER_OBJECT, 60, 1,
                new PhysicsUpdate(-8, 0, 0, 0, 0, 0)));
    }

    public Player(String address, PhysicsData data) {
        super(data);

        //Initializing variables
        clientAddress = address;
        //position = new Coordinate(-8,0);

        //Add the player to the player list and then a localplayer on its client
        //Players.addPlayer(this);
        
        //ObjectData data = new ObjectData(this.getData(),this.getId());
        //System.out.println(this.getId());
        //UDPSocket.send(new Communication(clientAddress, Communication.writePlayerData(data)));

    }


    @Override
    public void physicsForces() {

        if ((flags & FLAG_UP) != 0) {
            applyForce(0, 20 * mass /* deltaTime*/);
        }
        if ((flags & FLAG_DOWN) != 0) {
            applyForce(0, -5 * mass /* deltaTime*/);
        }
        if ((flags & FLAG_LEFT) != 0) {
            applyForce(-20 * mass /* deltaTime*/, 0);
        }
        if ((flags & FLAG_RIGHT) != 0) {
            applyForce(20 * mass /* deltaTime*/, 0);
        }
        if ((flags & FLAG_ENTER) != 0) {
            flags = (byte) (flags - FLAG_ENTER);
            this.applyUpdate(new PhysicsUpdate(new Coordinate(0,0),
                    new Coordinate(0.5,0.10), 0, 0));
        }
        if ((flags & FLAG_MOUSE1) != 0) {
            flags = (byte) (flags - FLAG_MOUSE1);

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

    public void setNextUpdate(PlayerUpdate update) {
        this.nextUpdate = update;
    }

    public PlayerUpdate getNextUpdate() {
        return nextUpdate;
    }

    public void clearNextUpdate() {
        nextUpdate = null;
    }

    public void applyPlayerUpdate(PlayerUpdate update) {
        applyUpdate(update.getUpdate());
        flags = update.getFlags();
    }

    public PlayerUpdate getPlayerUpdate() {
        return new PlayerUpdate(getUpdate(), flags, getId());
    }

    public void setAddress(String address) {
        clientAddress = address;
    }

    public String getAddress() {
        return clientAddress;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public byte getFlags() {
        return flags;
    }

}
