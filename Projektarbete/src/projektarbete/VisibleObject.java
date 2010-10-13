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
    
    public VisibleObject(Stage stage) {
        
        System.out.println("VisibleObject Started");

        addThing(stage);
        
    }

    public void addThing(Stage stage){
        stage.addVisibleObject(this);
    }

    public void remove(Stage stage){
	
        stage.removeVisibleObject(this);
        
    }
    
    public static void removeObject( VisibleObject[] visibleObjects, VisibleObject visibleObject ) {

        int lastIndex = -1;

        for (int i = 0; i < visibleObjects.length; i++) {

            if (lastIndex != -1) {
                if (visibleObjects[i] != visibleObject | visibleObjects[i] != null) {
                    visibleObjects[lastIndex] = visibleObjects[i];
                    lastIndex++;
                }
            }

            if (visibleObjects[i] == visibleObject | visibleObjects[i] == null) {
                visibleObjects[i] = null;
                lastIndex = i;
            }

        }

    }

}
