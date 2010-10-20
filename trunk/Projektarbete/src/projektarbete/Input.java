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

        Keybind[] keybinds = {new Keybind(new MovementFlagAction("up", false), "UP"),
        new Keybind(new MovementFlagAction("up", true), "released UP"),
        new Keybind(new MovementFlagAction("down", false), "DOWN"),
        new Keybind(new MovementFlagAction("down", true), "released DOWN"),
        new Keybind(new MovementFlagAction("left", false), "LEFT"),
        new Keybind(new MovementFlagAction("left", true), "released LEFT"),
        new Keybind(new MovementFlagAction("right", false), "RIGHT"),
        new Keybind(new MovementFlagAction("right", true), "released RIGHT")};

        //loops through the arrays
//        for (int i=0; i<actions.length; i++) {
//            //adds the keystrokes in the array to the input map
//            getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(strokes[i]),
//            actions[i].getValue(Action.NAME));
//            //adds the actions in the array to the action map
//            getActionMap().put(actions[i].getValue(Action.NAME), actions[i]);
//        }
        for (int i=0; i<keybinds.length; i++) {
            //adds the keystrokes in the array to the input map
            getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keybinds[i].getStroke()),
            keybinds[i].getAction().getValue(Action.NAME));
            //adds the actions in the array to the action map
            getActionMap().put(keybinds[i].getAction().getValue(Action.NAME),
                    keybinds[i].getAction());
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
        PhysicsEngine.vvy = -10;
    }

}

class UpReleasedAction extends AbstractAction {

    public UpReleasedAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Up released");
        PhysicsEngine.vvy = 0;
    }

}

class DownAction extends AbstractAction {

    public DownAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Down");
        //Stage.getInstance().movePolygon(0, 0, 5);
        PhysicsEngine.vvy = 10;
    }

}

class DownReleasedAction extends AbstractAction {

    public DownReleasedAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Down released");
        PhysicsEngine.vvy = 0;
    }

}

class LeftAction extends AbstractAction {

    public LeftAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Left");
        //Stage.getInstance().movePolygon(0, -5, 0);
        PhysicsEngine.vvx = -10;
    }

}

class LeftReleasedAction extends AbstractAction {

    public LeftReleasedAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Left released");
        PhysicsEngine.vvx = 0;
    }

}

class RightAction extends AbstractAction {

    public RightAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Right");
        //Stage.getInstance().movePolygon(0, 5, 0);
        PhysicsEngine.vvx = 10;
    }

}

class RightReleasedAction extends AbstractAction {

    public RightReleasedAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Right released");
        PhysicsEngine.vvx = 0;
    }

}

class MovementFlagAction extends AbstractAction {
String name;
boolean released;
Direction[] directions = {new Direction("up", 0, 1), new Direction("down", 0, -1),
new Direction("left", -1, 0), new Direction("right", 1, 0)};

    public MovementFlagAction(String n, boolean r) {
        super(n);
        name = n;
        released = r;
    }

    public void actionPerformed(ActionEvent e) {
        for (int i=0; i < directions.length; i++) {
            if (directions[i].name.equalsIgnoreCase(name)) {
                PhysicsEngine.vvx += directions[i].xv;
                PhysicsEngine.vvy += directions[i].yv;
                System.out.println(PhysicsEngine.vvx+" "+PhysicsEngine.vvy);
            }
        }
    }

}

class Keybind {
    private Action action;
    private String stroke;

    public Keybind(Action a, String s) {
        action = a;
        stroke = s;
    }

    public Keybind(String s, Action a) {
        action = a;
        stroke = s;
    }

    public Action getAction() {
        return action;
    }

    public String getStroke() {
        return stroke;
    }
}

class Direction {
    public String name;
    public double xv;
    public double yv;

    public Direction(String n, double x, double y) {
        name = n;
        xv = x;
        yv = y;
    }
}