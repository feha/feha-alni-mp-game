/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.awt.Graphics;

/**
 *
 * @author felix.hallqvist
 */
public class Camera extends VisibleObject {

    public Camera() {

        System.out.println("Camera Initializing");

        initGlobals();
        initComponents();

        System.out.println("Camera Initialized");

    }

    
    //Global variables
    private static Camera _instance;

    //Networked variables
    Stage parent;

    private void initGlobals() {

        _instance = this;

    }
    
    private void initComponents() {

        parent = Stage.getInstance();
        
        addToParent();

        x = 100;
        y = 100;

    }


    public static Camera getInstance() {

        //Double Check Locking pattern makes it only create new stage if it does
        //not exist already, and stops threading from beign able to mess it up.
        if (_instance == null) {
            synchronized(Camera.class) {
                if (_instance == null) {
                    System.out.println(_instance);
                    _instance = new Camera();
                }
            }
        }

        return _instance;

    }

    @Override
    public void addToParent(){
        
        //Tell the Stage that this instance wants to be in the display list.
        parent.addVisibleObject(this);

    }

    @Override
    public void removeFromParent(){

        //Tell the Stage to remove this instance from the display list.
        parent.removeVisibleObject(this);

    }

}
