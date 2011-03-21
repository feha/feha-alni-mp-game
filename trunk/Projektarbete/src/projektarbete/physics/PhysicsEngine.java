/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import projektarbete.Coordinate;


/**
 *
 * @author niclas.alexandersso
 */
public class PhysicsEngine  {

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
        /*for (int i = 0; i < getBasePhysicsCount(); i++) {
            getBasePhysics(i).physicsSimulate(deltaTime);
        }*/
        update(deltaTime);
        force();
        movement();
        collision();
        position();
        graphics();

        oldTime = System.nanoTime();
    }

    protected void simulate(double deltaTime) {
        for (BasePhysics element : basePhysicsList) {
            element.physicsSimulate(deltaTime);
        }
    }
    protected void update(double deltaTime) {
        for (BasePhysics element : basePhysicsList) {
            element.setDeltaTime(deltaTime);
            element.physicsUpdate();
        }
    }

    protected void force() {
        for (BasePhysics element : basePhysicsList) {
            element.applyForces();
            element.simulateFriction();
        }
    }

    protected void movement() {
        for (BasePhysics element : basePhysicsList) {
            element.simulateForce();
            element.simulateVelocity();
        }
    }

    protected void collision() {
        for (int i = 0; i < basePhysicsList.size()-1; i++) {
            BasePhysics element = basePhysicsList.get(i);
            Rectangle2D.Double bounds = element.getMovementBounds();
            for (int n = i+1; n < basePhysicsList.size(); n++) {
                BasePhysics comparison = basePhysicsList.get(n);
                if (bounds.intersects(comparison.getMovementBounds())) {
                    if (GeomUtils.lineSegDistSq(new Line2D.Double(
                            element.position.toPoint2D(),
                            element.futurePosition.toPoint2D()), new Line2D.Double(
                            comparison.position.toPoint2D(),
                            comparison.futurePosition.toPoint2D()))<
                            (Math.pow(element.scale+comparison.scale, 2))) {
                        element.futurePosition = new Coordinate(element.position);
                        element.setVel(element.getVel().mul(-1));
                        comparison.futurePosition =
                                new Coordinate(comparison.position);
                        comparison.setVel(comparison.getVel().mul(-1));
                    }
                }
            }
        }
    }

    protected void position() {
        for (BasePhysics element : basePhysicsList) {
            element.updatePos();
        }
    }

    protected void graphics() {
        for (BasePhysics element : basePhysicsList) {
            element.updateGraphic();
        }
    }

}



