/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author niclas.alexandersso
 */

public class TestUserObject /*extends BasePhysics*/ {

    boolean[] movementFlag = {false, false, false, false};
    String[] movementFlagName = {"up", "down", "left", "right"};

    public TestUserObject() {

    }

    public void setMovementFlag(boolean value, boolean[] flags) {
        for (int i = 0; i<movementFlag.length; i++){
            if (flags[i]) {
                System.out.println("Flag: "+movementFlag[i]);
                movementFlag[i] = value;
            }
        }
        System.out.println("Output: "+movementFlag[1]);
    }
    public boolean[] getMovementFlags() {
        return movementFlag;
    }
    public String[] getMovementFlagNames() {
        return movementFlagName;
    }
}
