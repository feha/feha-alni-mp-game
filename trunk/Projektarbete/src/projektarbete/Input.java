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
        new LeftAction("left"), new RightAction("right"), new UpReleasedAction("released up"),
        new DownReleasedAction("released down"), new LeftReleasedAction("released left"),
        new RightReleasedAction("released right")};
        String[] strokes = {"UP", "DOWN", "LEFT", "RIGHT", "released UP",
        "released DOWN", "released LEFT", "released RIGHT"};

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
        //Stage.getInstance().movePolygon(0, 0, -5);
        PhysicsEngine.svy = -5;
    }

}

class UpReleasedAction extends AbstractAction {

    public UpReleasedAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Up released");
        PhysicsEngine.svy = 0;
    }

}

class DownAction extends AbstractAction {

    public DownAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Down");
        //Stage.getInstance().movePolygon(0, 0, 5);
        PhysicsEngine.svy = 5;
    }

}

class DownReleasedAction extends AbstractAction {

    public DownReleasedAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Down released");
        PhysicsEngine.svy = 0;
    }

}

class LeftAction extends AbstractAction {

    public LeftAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Left");
        //Stage.getInstance().movePolygon(0, -5, 0);
        PhysicsEngine.svx = -5;
    }

}

class LeftReleasedAction extends AbstractAction {

    public LeftReleasedAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Left released");
        PhysicsEngine.svx = 0;
    }

}

class RightAction extends AbstractAction {

    public RightAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Right");
        //Stage.getInstance().movePolygon(0, 5, 0);
        PhysicsEngine.svx = 5;
    }

}

class RightReleasedAction extends AbstractAction {

    public RightReleasedAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Right released");
        PhysicsEngine.svx = 0;
    }

}