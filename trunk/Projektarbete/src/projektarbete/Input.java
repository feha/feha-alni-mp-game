/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author niclas.alexandersso
 */
public class Input extends JPanel {

    public Input() {
        System.out.println("Input started");
        //creating arrays for the actions and keystrokes connected to them
        Action[] actions = {new UpAction("up"),  new DownAction("down"),
        new LeftAction("left"), new RightAction("right")};
        String[] strokes = {"UP", "DOWN", "LEFT", "RIGHT"};

        //loops through the arrays
        for (int i=0; i<actions.length; i++) {
            //adds the keystrokes in the array to the input map
            getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(strokes[i]),
            actions[i].getValue(Action.NAME));
            //adds the actions in the array to the action map
            getActionMap().put(actions[i].getValue(Action.NAME), actions[i]);
        }
    }
}

/*all of these actions currently moves one of the polygons. This is purely for
 testing and will be replaced later. As of now, only one key can be pressed at a time.*/

class UpAction extends AbstractAction {

    public UpAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Up");
        Main.stage.movePolygon(0, 0, -5);
    }

}

class DownAction extends AbstractAction {

    public DownAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Down");
        Main.stage.movePolygon(0, 0, 5);
    }

}

class LeftAction extends AbstractAction {

    public LeftAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Left");
        Main.stage.movePolygon(0, -5, 0);
    }

}

class RightAction extends AbstractAction {

    public RightAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Right");
        Main.stage.movePolygon(0, 5, 0);
    }

}

