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
    protected Coordinate position = new Coordinate(0,0);
    protected Coordinate offset = new Coordinate(0,0);
    static Coordinate scale = new Coordinate(10.0,10.0);


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

    public void setPos(Coordinate coordinate) {

        //As position is a double, we need to make sure int still rounds well.
        position = /*Coordinate.round(*/coordinate/*.getMul(scale)*//*)*/;

    }

    public Coordinate getPos() {
        return position/*.getDiv(scale)*/;
    }


    public void offset(double x, double y) {

        offset(new Coordinate(x,y));

    }

    public void offset(Coordinate coordinate) {

        offset = coordinate/*.getMul(scale)*/;

    }

    public Coordinate getOffset() {
        return offset/*.getDiv(scale)*/;
    }

    public static void setScale(Coordinate newScale) {
        scale = newScale;
    }
    public static void setScale(double newScale) {
        scale.setPos(newScale, newScale);
    }

}