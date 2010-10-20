/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author felix.hallqvist
 */
public class VisibleObject {

    //Make parent be visibleobject, and have a special visibleobject subclass instead of stage.
    VisibleObject parent;
    boolean fixed;
    int x;
    int y;
    int offsetX;
    int offsetY;


    public VisibleObject() {

        System.out.println("VisibleObject (Camera) Initializing");

        //initComponents();

        System.out.println("VisibleObject (Camera) Initialized");

    }


    public VisibleObject(VisibleObject visibleObject) {

        System.out.println("VisibleObject Initializing");

        initComponents();

        System.out.println("VisibleObject Initialized");

    }


    private void initComponents() {

        parent = Camera.getInstance();
        addToParent();

    }

    public void addToParent(){

        //Tell the Stage that this instance wants to be in the display list.
        parent.addVisibleObject(this);

    }

    public void removeFromParent(){

        //Tell the Stage to remove this instance from the display list.
        parent.removeVisibleObject(this);

    }

    //Makes a list of visibleobjects, which is good because of its remove functions.
    LinkedList<VisibleObject> visibleObjects = new LinkedList<VisibleObject>();
    int objectCount = 0; //arrays start at 0 in java

    public void addVisibleObject( VisibleObject visibleObject ) {

        visibleObjects.add(visibleObject);

        objectCount++;

    }

    public void removeVisibleObject( VisibleObject visibleObject ) {

        while (visibleObjects.remove(visibleObject)) {}

        objectCount--;

    }

    public VisibleObject getVisibleObject( int index ) {
        return visibleObjects.get(index);

    }

    public int getVisibleObjectCount() {

        return objectCount;

    }

    public void draw(Graphics g){

        int count = getVisibleObjectCount();

        for (int i = 0; i < count; i++) {
            getVisibleObject(i).draw(g);
        }

    }

    public void offset(double x, double y) {

        offsetX = (int)x;
        offsetY = (int)y;

    }

}
