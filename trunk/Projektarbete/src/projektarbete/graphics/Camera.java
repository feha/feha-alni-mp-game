/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.graphics;

import java.awt.Graphics;
import projektarbete.Coordinate;
import projektarbete.Stage;

/**
 *
 * @author felix.hallqvist
 */
public class Camera extends VisibleScreen {

    public Camera() {
        System.out.println("Camera Initializing");

        initGlobals();
        initComponents();

        //parent = Stage.getInstance();
        //System.out.println("Parent = " + parent);
        //addToParent();
        System.out.println("Camera Initialized");

    }

    CameraContainer container;
    
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

    @Override
    public void draw(Graphics g) {
        if (container != null) {
            angle = -container.getAng(); //Lets not use angle, almost made me dizzy
            scale = container.getScale();
            position = container.getPos();
        }
        
        super.draw(g);
    }

    public void setContainer(CameraContainer newContainer) {
        container = newContainer;
    }

}
