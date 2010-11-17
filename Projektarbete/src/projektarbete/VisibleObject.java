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
    protected Coordinate drawPos;
    protected Coordinate sizeScale;
    protected double totalAngle;
    protected Coordinate position = new Coordinate(0,0);
    protected Coordinate offset = new Coordinate(0,0);
    protected Coordinate scale = new Coordinate(1.0,1.0);
    protected double angle = 0;


    public VisibleObject() {

        System.out.println("VisibleObject (Screen) Initializing");
        
        System.out.println("VisibleObject (Screen) Initialized");

    }


    public VisibleObject(VisibleObject visibleObject) {

        System.out.println("VisibleObject Initializing");

        parent = visibleObject;
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

    public void draw(Graphics g) {
        
        if (parent != null) {
            //put it at its right pos, within the parents coord systems size scale
            drawPos = (Coordinate.add(position, offset).mul(parent.sizeScale));
            //Rotate with the angle of the coord system (needs fixing)
            drawPos = drawPos.rotate(parent.totalAngle);
            
            //Add it to the parent pos, so it is drawn in a local coord system
            drawPos = drawPos.add(parent.drawPos).add(parent.offset);

            totalAngle = angle + parent.totalAngle;
            sizeScale = scale.mul(parent.sizeScale);
        } else {
            drawPos = (position.add(offset));

            totalAngle = angle;
            sizeScale = scale;
        }

        int count = getVisibleObjectCount();

        for (int i = 0; i < count; i++) {
            getVisibleObject(i).draw(g);
        }

    }
    
    public void setPos(double xPos, double yPos) {
        setPos(new Coordinate(xPos, yPos));
    }

    public void setPos(Coordinate coordinate) {
        
        position = coordinate;

    }

    public Coordinate getPos() {
        return position;
    }


    public void setAng(double newAngle) {
        angle = newAngle;
    }

    public double getAng() {
        return angle;
    }


    public void offset(double x, double y) {

        offset(new Coordinate(x,y));

    }

    public void offset(Coordinate coordinate) {

        offset = coordinate;

    }

    public Coordinate getOffset() {
        return offset;
    }


    public void setScale(Coordinate newScale) {
        scale = newScale;
    }
    public void setScale(double newScale) {
        scale.setPos(newScale, newScale);
    }

    public Coordinate getScale() {
        return scale;
    }

}