/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.graphics;

import java.awt.Graphics;

/**
 *
 * @author felix.hallqvist
 */
public class Camera extends VisibleScreen {

    public Camera() {

        System.out.println("Camera Initializing");

        initGlobals();
        initComponents();

        System.out.println("Camera Initialized");

    }

    
    //Global variables
    private static Camera _instance;

    private void initGlobals() {

        _instance = this;

    }
    
    private void initComponents() {
        /*
        parent = Stage.getInstance();
        
        addToParent();
        */
    }


    public static Camera getInstance() {

        /*
        //Double Check Locking pattern makes it only create new stage if it does
        //not exist already, and stops threading from beign able to mess it up.
        if (_instance == null) {
            synchronized(Camera.class) {
                if (_instance == null) {
                    System.out.println(_instance);
                    _instance = new Camera();
                    System.out.println(_instance);
                }
            }
        }
        */
        
        return _instance;

    }

}
