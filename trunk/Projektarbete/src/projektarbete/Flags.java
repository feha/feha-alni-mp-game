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
    LinkedList<Flag> flags = new LinkedList<Flag>();
    int count = 0;
    public boolean addFlag(String name, boolean value) {
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
