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
    protected VisibleObject parent;
    boolean fixed;
    protected int x;
    protected int y;
    protected Coordinate coordinate;
    protected int offsetX;
    protected int offsetY;
    protected Coordinate offset;


    public VisibleObject() {

        System.out.println("VisibleObject (Screen) Initializing");

        System.out.println("VisibleObject (Screen) Initialized");

    }


    public VisibleObject(VisibleObject visibleObject) {

        System.out.println("VisibleObject Initializing");

        parent = Camera.getInstance();
        initComponents();

        System.out.println("VisibleObject Initialized");

    }


    private void initComponents() {

        addToParent();

    }

    public void setParent(VisibleObject newParent) {
        parent = newParent;
    }
    
    public VisibleObject getParent() {
        return parent;
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

        while (visibleObjects.remove(visibleObject)) {
            objectCount--;
        }

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
    
    public void setPos(double xPos, double yPos) {
        setPos(new Coordinate(xPos, yPos));
    }

    public void setPos(Coordinate position) {

    }
    
    public void offset(double x, double y) {

        offset(new Coordinate(x,y));

    }

    public void offset(Coordinate position) {

        offset = position;

    }

}