/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


/**
 *
 * @author niclas.alexandersso
 */
class PhysicsEngine  {

    public PhysicsEngine() {

        System.out.println("PhysicsEngine Initializing");

        initGlobals();
        initComponents();

        System.out.println("PhysicsEngine Initialized");

    }

    boolean timerStarted = false;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        public void run() {
            physicsLoop();
        }
    };

    LinkedList<BasePhysics> basePhysicsList = new LinkedList<BasePhysics>();
    int count = 0; //arrays start at 0 in java

    long oldTime = System.nanoTime();

    //Global variables
    private static PhysicsEngine _instance;


    private void initComponents() {
        
        physicsLoop();

    }
    

    private void initGlobals() {

        _instance = this;

    }

    public static PhysicsEngine getInstance() {

        //Double Check Locking pattern makes it only create new stage if it does
        //not exist already, and stops threading from beign able to mess it up.
        if (_instance == null) {
            synchronized(PhysicsEngine.class) {
                if (_instance == null) {
                    _instance = new PhysicsEngine();
                }
            }
        }

        return _instance;

    }

    public void addBasePhysics( BasePhysics basePhysics ) {

        basePhysicsList.add(basePhysics);

        count++;

    }

    public void removeBasePhysics( BasePhysics basePhysics ) {

        while (basePhysicsList.remove(basePhysics)) {
            count--;
        }

    }

    public BasePhysics getBasePhysics( int index ) {
        return basePhysicsList.get(index);

    }

    public int getBasePhysicsCount() {

        return count;

    }

    public void physicsLoop (){

        if (!timerStarted) {
            timer.scheduleAtFixedRate(timerTask, 20, 20); //20 ms = 50 fps
            timerStarted = true;
        }
        
        long deltaNanoTime;
        deltaNanoTime = (System.nanoTime()-oldTime);
        double deltaTime = (double)deltaNanoTime/1000000000;

        //code to manage delays between updating the picture
        for (int i = 0; i < getBasePhysicsCount(); i++) {
            getBasePhysics(i).physicsSimulate(deltaTime);
        }
        Stage.getInstance().repaint();
        oldTime = System.nanoTime();
    }

}



