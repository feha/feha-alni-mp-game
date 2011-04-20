/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import projektarbete.Communication;
import projektarbete.Coordinate;
import projektarbete.UDPSocket;

/**
 *
 * @author niclas.alexandersso
 */
public class UpdateTester {

    Timer timer = new Timer();
    TimerTask task;
    int counter;
    private static String ip = "127.0.0.1";

    public UpdateTester(final List<ObjectUpdate> updates, final PhysicsEngine engine) {
        task = new TimerTask() {
            public void run() {
                for (ObjectUpdate update : updates) {
                    update.getUpdate().setPosition(
                            new Coordinate(Math.random()*6,Math.random()*-6));
                    update.getUpdate().setVelocity(
                            new Coordinate((Math.random()*2-1)*10,(Math.random()*2-1)*10));
                    update.getUpdate().setAngle(Math.random()*2*Math.PI);
                    //engine.updateObject(update);
                    UDPSocket.send(new Communication(ip, Communication.writeUpdate(update)));
                }
            }
        };
    }

    public UpdateTester(final List<ObjectData> data, final PhysicsEngine engine, boolean ignored) {
        task = new TimerTask() {
            public void run() {
                for (ObjectData element : data) {
                    switch (counter) {
                        case 0:
                            element.getData().setTemplate(
                                    Templates.TYPE_ELASTIC_OBSTACLE);

                            counter++; break;

                        case 1:
                            element.getData().setTemplate(
                                    Templates.TYPE_INELASTIC_OBSTACLE);

                            counter++; break;
                        case 2:
                            element.getData().setTemplate(
                                    Templates.TYPE_PLAYER_OBJECT);

                            counter=0; break;
                    }
                    //engine.updateObject(update);
                    /*System.out.println("Synchronized block");
                    synchronized (engine) {
                    System.out.println("Removing object "+ element.getId());
                    engine.removeObject(element.getId());
                    System.out.println("Creating object "+ element.getId());
                    engine.createObject(element);
                    System.out.println("Loop done");
                    }*/
                    UDPSocket.send(new Communication(ip, Communication.writeRemoveObject(element.getId())));
                    UDPSocket.send(new Communication(ip, Communication.writeObjectData(element)));
                }
            }
        };
    }

    public void start(long period) {
        timer.scheduleAtFixedRate(task, period, period);
    }

}
