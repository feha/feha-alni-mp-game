/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import projektarbete.graphics.Painter;
import java.awt.Component;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author felix.hallqvist
 */
public class Menu extends JPanel {

    public Menu() {
        System.out.println("Menu Initializing");

        initComponents();
        initTesting();

        System.out.println("Menu Initialized");
    }

    private void initComponents() {

        Painter painter = Stage.getInstance().painter;

        painter.add(this);

    }

    private void initTesting() {



    }

    
    public void kill() {

        Painter painter = Stage.getInstance().painter;

        painter.remove(this);

    }

}
