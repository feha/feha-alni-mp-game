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

        //adds flags for later use. Allows identifying boolean valyes with a string
        Flags.addFlag("pressed", true);
        Flags.addFlag("released", false);

        //creates an array of keybinds
        Keybind[] keybinds = {new Keybind(new FlagAction("up pressed"), "UP"),
        new Keybind(new FlagAction("up released"), "released UP"),
        new Keybind(new FlagAction("down pressed"), "DOWN"),
        new Keybind(new FlagAction("down released"), "released DOWN"),
        new Keybind(new FlagAction("left pressed"), "LEFT"),
        new Keybind(new FlagAction("left released"), "released LEFT"),
        new Keybind(new FlagAction("right pressed"), "RIGHT"),
        new Keybind(new FlagAction("right released"), "released RIGHT"),
        new Keybind(new FlagAction("enter pressed"), "ENTER"),
        new Keybind(new FlagAction("enter released"), "released ENTER")};

        for (int i=0; i<keybinds.length; i++) {

            //adds the keystrokes in the keybind array to the input map
            getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keybinds[i].getStroke()),
            keybinds[i].getAction().getValue(Action.NAME));

            //adds the actions in the keybind array to the action map
            getActionMap().put(keybinds[i].getAction().getValue(Action.NAME),
                    keybinds[i].getAction());
        }
    }
}

class FlagAction extends AbstractAction {
String input;
String name;
boolean value;

    public FlagAction(String n) {
        super(n);
        input = n;
    }

    public void actionPerformed(ActionEvent e) {

        //splits the input string into an array
        String[] flag = input.split(" ");

        //the first element of the array is the name
        name = flag[0];

        //the second element of the array is a boolean value, identified by a flag
        value = Flags.getFlag(flag[1]);
        
        //the flag for this key is set to the value of the input string
        Flags.setFlag(name, value);
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
