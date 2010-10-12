/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author niclas.alexandersso
 */
public class InputPanel extends JPanel {

    public InputPanel() {
        //creating arrays for the actions and keystrokes connected to them
        Action[] actions = {new UpAction("up"),  new DownAction("down"),
        new LeftAction("left"), new RightAction("right")};
        String[] strokes = {"UP", "DOWN", "LEFT", "RIGHT"};

        //loops through the arrays
        for (int i=0; i<actions.length; i++) {
            //adds the keystrokes in the array to the
            getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(strokes[i]),
            actions[i].getValue(Action.NAME));
            //getActionMap().put(actions[i].getValue(Action.NAME), actions[i]);
        }
    }
}

class UpAction extends AbstractAction {

    public UpAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Up");
    }

}

class DownAction extends AbstractAction {

    public DownAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Down");
    }

}

class LeftAction extends AbstractAction {

    public LeftAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Left");
    }

}

class RightAction extends AbstractAction {

    public RightAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Right");
    }

}
