/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.util.LinkedList;

/**
 *
 * @author niclas.alexandersso
 */
public class Flags {
    private static LinkedList<Flag> flags = new LinkedList<Flag>();

    public static boolean addFlag(String name, boolean value) {
        boolean exists = false;

        //checks if the name of the flag about to be added already exists
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i).getName().matches(name)) {
                exists = true;
                break;
            }
        }
        //if no flag with that name exists, a flag with that name is added
        if (!exists) {
            flags.add(flags.size(), new Flag(name, value));
        }

        //returns true if the flag was added
        return !exists;
    }
    public static void setFlag(String name, boolean value) {
        boolean exists = false;
        //loops until a flag with a name matiching the input string is found
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i).getName().matches(name)) {
                //sets the flag's value to the input value and breaks the loop
                flags.get(i).setValue(value);
                System.out.println(name+" "+value);
                exists = true;
                break;
            }
        }
        //if no flag with that name exists, a flag with that name is added
        if (!exists) {
            flags.add(flags.size(), new Flag(name, value));
        }
    }
    public static void removeFlag(String name) {

        //loops until a flag with a name matching the input string is found
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i).getName().matches(name)) {
                //removes that flag
                flags.remove(i);
                break;
            }
        }
    }
    public static boolean getFlag(String name) {

        //loops until a flag with a name matching the input string is found
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i).getName().matches(name)) {
                //returns the value of the flag
                return flags.get(i).getValue();
            }
        }
        //if no flag with a matching name is found, it returns false
        return false;
    }
}

class Flag {
    private String name;
    private boolean value;
    public Flag(String n, boolean v) {
        name = n;
        value = v;
    }
    public String getName() {
        return name;
    }
    public boolean getValue() {
        return value;
    }
    public void setName(String n) {
        name = n;
    }
    public void setValue(boolean v) {
        value = v;
    }
}
