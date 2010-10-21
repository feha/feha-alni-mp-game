/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author felix.hallqvist
 */
public class VisibleScreen extends VisibleObject {
    
    public VisibleScreen() {
        
        System.out.println("VisibleScreen Initializing");

        parent = Stage.getInstance();
        initComponents();

        System.out.println("VisibleScreen Initialized");
        
    }
    
    protected Stage parent;
   //Global variables
    private static VisibleScreen _instance;

    private void initGlobals() {

        _instance = this;

    }
    
    private void initComponents() {

        parent = Stage.getInstance();

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

}
