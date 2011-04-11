/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import projektarbete.Coordinate;

/**
 *
 * @author niclas.alexandersso
 */
public class UpdateTester {

    Timer timer = new Timer();
    TimerTask task;

    public UpdateTester(final List<Update> updates, final PhysicsEngine engine) {
        task = new TimerTask() {
            public void run() {
                for (Update update : updates) {
                    System.out.println("updating");
                    update.getUpdate().setPosition(
                            new Coordinate(Math.random()*6,Math.random()*-6));
                    update.getUpdate().setVelocity(
                            new Coordinate((Math.random()*2-1)*10,(Math.random()*2-1)*10));
                    update.getUpdate().setAngle(Math.random()*2*Math.PI);
                    engine.updateObject(update);
                }
            }
        };
    }

    public void start(long period) {
        timer.scheduleAtFixedRate(task, 20, period);
    }

}
