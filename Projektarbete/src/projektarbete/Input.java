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
public class Input {
    JPanel window;

    public Input(JPanel window) {
        
        System.out.println("Input started");
        this.window = window;

        KeyListener k = new KeyListener() {

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        };

        window.addKeyListener(k);

        MouseListener m = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if (e.getButton()==MouseEvent.BUTTON1) {
                    Flags.setFlag("mouse1", true);
                }
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        };

        window.addMouseListener(m);

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
        new Keybind(new FlagAction("enter pressed"), "pressed ENTER"),
        new Keybind(new FlagAction("enter released"), "released ENTER")/*,
        new Keybind(new FlagAction("mouse1 pressed"), "BUTTON2"),
        new Keybind(new FlagAction("mouse1 released"), "released BUTTON2")*/};

        for (int i=0; i<keybinds.length; i++) {

            //adds the keystrokes in the keybind array to the input map
            window.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(keybinds[i].getStroke()),
            keybinds[i].getAction().getValue(Action.NAME));

            //adds the actions in the keybind array to the action map
            window.getActionMap().put(keybinds[i].getAction().getValue(Action.NAME),
                    keybinds[i].getAction());
        }
        AbstractAction exit = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Escape pressed");
                System.exit(0);
            }
        };
        window.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(
                KeyEvent.VK_ESCAPE, 0),
                exit);
        window.getActionMap().put(exit.getValue(Action.NAME), exit);
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
