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
public static TestUserObject tester = new TestUserObject();
    public Input() {
        
        System.out.println("Input started");


        Keybind[] keybinds = {new Keybind(new FlagAction("up pressed"), "UP"),
        new Keybind(new FlagAction("up released"), "released UP"),
        new Keybind(new FlagAction("down pressed"), "DOWN"),
        new Keybind(new FlagAction("down released"), "released DOWN"),
        new Keybind(new FlagAction("left pressed"), "LEFT"),
        new Keybind(new FlagAction("left released"), "released LEFT"),
        new Keybind(new FlagAction("right pressed"), "RIGHT"),
        new Keybind(new FlagAction("right released"), "released RIGHT")};

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

class FlagAction extends AbstractAction {
String name;

    public FlagAction(String n) {
        super(n);
        name = n;
    }

    public void actionPerformed(ActionEvent e) {
        String[] flag = name.split(" ");
        name = flag[0];
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
