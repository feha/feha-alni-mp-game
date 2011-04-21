/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.graphics;

import projektarbete.Stage;

/**
 *
 * @author felix.hallqvist
 */
public class VisibleScreen extends VisibleObject {
    
    public VisibleScreen() {

        stage = Stage.getInstance();
        initGlobals();
        initComponents();
        this.addToStage();
        
    }
    
    protected Stage stage;
   //Global variables
    private static VisibleScreen _instance;

    private void initGlobals() {

        _instance = this;

    }
    
    private void initComponents() {

        stage = Stage.getInstance();

        addToParent();

    }

    public static VisibleScreen getInstance() {

        //Double Check Locking pattern makes it only create new stage if it does
        //not exist already, and stops threading from beign able to mess it up.
        if (_instance == null) {
            synchronized(VisibleScreen.class) {
                if (_instance == null) {
                    System.out.println(_instance);
                    _instance = new VisibleScreen();
                }
            }
        }

        return _instance;

    }

    private void addToStage() {
        stage.addVisibleObject(this);
    }

}
