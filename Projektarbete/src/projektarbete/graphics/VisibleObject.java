/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import projektarbete.Coordinate;

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
    protected Coordinate anchor = new Coordinate(0,0);
    protected Color color = new Color(0,0,0);


    public VisibleObject() {

    }

    public VisibleObject(VisibleObject visibleObject) {


        parent = visibleObject;

        addToParent();


    }

    public void setParent(VisibleObject newParent) {
        parent = newParent;
    }
    
    public VisibleObject getParent() {
        return parent;
    }

    public final void addToParent(){

        //Tell the Stage that this instance wants to be in the display list.
        if (parent != null) {
            parent.addVisibleObject(this);
        }

    }

    public final void removeFromParent(){

        //Tell the Stage to remove this instance from the display list.
        if (parent != null) {
            parent.removeVisibleObject(this);
        }

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
            drawPos = drawPos.add(parent.drawPos);

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

    public void setColor(Color newColor) {
        color = newColor;
    }

    public Color getColor() {
        return color;
    }

    /*public void setImage(String file) {

    }*/

    public void setAnchor(Coordinate anchor) {
        this.anchor = anchor;
    }

    public void centerAnchor() {
        anchor = new Coordinate(0,0);
    }

    public void resetAnchor() {
        anchor = new Coordinate(0,0);
    }

    public VisibleObject copy() {
        VisibleObject copy = new VisibleObject();
        copy.set(this);
        return copy;
    }

    public void set(VisibleObject visibleObject) {
        anchor = new Coordinate(visibleObject.anchor);
        angle = visibleObject.angle;
        color = visibleObject.getColor();
        color = new Color(color.getRed(), color.getGreen(),
                color.getBlue(), color.getAlpha());
        drawPos = new Coordinate(visibleObject.drawPos);
        objectCount = visibleObject.objectCount;
        offset = new Coordinate(visibleObject.offset);
        parent = visibleObject.parent;
        addToParent();
        position = new Coordinate(visibleObject.position);
        scale = new Coordinate(visibleObject.scale);
        sizeScale = new Coordinate(visibleObject.sizeScale);
        totalAngle = visibleObject.totalAngle;
        visibleObjects = new LinkedList<VisibleObject>();
        for (VisibleObject element : visibleObject.visibleObjects) {
            visibleObjects.add(element.copy());
        }
    }

}