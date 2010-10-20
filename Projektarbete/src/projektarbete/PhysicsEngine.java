/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;


/**
 *
 * @author niclas.alexandersso
 */
class PhysicsEngine  {
    static int svx = 0;
    static int svy = 0;
    static double vvx = 0;
    static double vvy = 0;
    static double xs = 0;
    static double ys = 0;
    static long oldTime = System.nanoTime();


    public static void physicsLoop (){
        long deltaTime;
        deltaTime = System.nanoTime()-oldTime;
        svx = 0;
        svy = 0;
        xs = xs + vvx/(deltaTime*10);
        ys = ys + vvy/(deltaTime*10);
        while (xs > 1) {
            xs--;
            svx++;
        }
        while (xs < 1) {
            xs++;
            svx--;
        }
        while (ys > 1) {
            ys--;
            svy++;
        }
        while (ys < 1) {
            ys++;
            svy--;
        }
        //Stage.getInstance().movePolygon(0, svx, svy);
        //Stage.repaint();
        System.out.println("Running loop");
        oldTime = System.nanoTime();
    }

}



