/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import javax.swing.*;

/**
 *
 * @author niclas.alexandersso
 */
public class InputSystem extends JFrame {

    public static void main(String[] args) {
        InputSystem is = new InputSystem();
        //random stuff for the JFrame
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //this is where the magic is made
        frame.add(new InputPanel());
    }

}