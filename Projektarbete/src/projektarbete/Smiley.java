/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author felix.hallqvist
 */
public class Smiley extends VisibleObject {

    public Smiley(VisibleObject visibleObject) {

        super(visibleObject);

        System.out.println("Smiley Initializing");

        initComponents();

        System.out.println("Smiley Initialized");

    }

    VisibleObject face;
    VisibleObject eyeR;
    VisibleObject eyeL;
    VisibleObject mouth;

    private void initComponents() {

        face = new Hexagon(this);

        eyeR = new Hexagon(face);
        eyeR.setScale(0.25);
        eyeR.setPos(-5, -5);
        
        eyeL = new Hexagon(face);
        eyeL.setScale(0.25);
        eyeL.setPos(5, -5);

        mouth = new Hexagon(face);
        mouth.setScale(0.4);
        mouth.setPos(0, 5);

    }

}
