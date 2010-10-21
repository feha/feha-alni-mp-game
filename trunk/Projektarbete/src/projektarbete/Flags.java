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
    private static int count = 0;

    public static boolean addFlag(String name, boolean value) {
        boolean exists = false;

        for (int i = 0; i < count; i++) {
            if (flags.get(i).getName().matches(name)) {
                exists = true;
            }
        }
        if (!exists) {
            flags.add(count, new Flag(name, value));
            count++;
        }

        return !exists;
    }
    public static void setFlag(String name, boolean value) {
        for (int i = 0; i < count; i++) {
            if (flags.get(i).getName().matches(name)) {
                flags.get(i).setValue(value);
            }
        }
    }
    public static void removeFlag(String name) {
        for (int i = 0; i < count; i++) {
            if (flags.get(i).getName().matches(name)) {
                flags.remove(i);
                count--;
            }
        }
    }
    public static boolean getFlag(String name) {
        for (int i = 0; i < count; i++) {
            if (flags.get(i).getName().matches(name)) {
                return flags.get(i).getValue();
            }
        }
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
