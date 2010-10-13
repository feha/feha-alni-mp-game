/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author felix.hallqvist
 */
public class VisibleObject {

    public VisibleObject() {

        System.out.println("VisibleObject Initializing");

        initComponents();

        System.out.println("VisibleObject Initialized");

    }

    private void initComponents() {

        addToStage();

    }

    public void addToStage(){

        //Tell the Stage that this instance wants to be in the display list.
        Stage.getInstance().addVisibleObject(this);

    }

    public void removeFromStage(){

        //Tell the Stage to remove this instance from the display list.
        Stage.getInstance().removeVisibleObject(this);

    }

}
