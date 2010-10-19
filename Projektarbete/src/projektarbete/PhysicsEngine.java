/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author niclas.alexandersso
 */
public class PhysicsEngine {
    static int svx = 0;
    static int svy = 0;

    public static void physicsLoop () throws InterruptedException {
        //Stage.getInstance().movePolygon(0, svx, svy);


        Thread.sleep(20);
        physicsLoop();
    }

}



