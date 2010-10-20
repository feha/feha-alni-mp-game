/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author felix.hallqvist
 */
public class Main {

    //public static Stage stage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("Main Initializing");

        Runnable physics = new Runnable() {
            public void run() {
                //PhysicsEngine pe = new PhysicsEngine();
                try {
                    PhysicsEngine.physicsLoop();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        physics.run();

        Stage stage = new Stage();


        System.out.println("Main Initialized");

    }

}
