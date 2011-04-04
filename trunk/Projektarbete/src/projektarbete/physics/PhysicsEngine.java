/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import projektarbete.Coordinate;
import projektarbete.Stage;


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
    LinkedList<Collision> collisions = new LinkedList<Collision>();
    int count = 0; //arrays start at 0 in java

    long oldTime = System.nanoTime();

    //Global variables
    private static PhysicsEngine _instance;


    private void initComponents() {

        //System.out.println("GeomUtils test: "+GeomUtils.lineSegDist(new Line2D.Double(-1, -1, -2, -2), new Line2D.Double(1, 1, 2, 2)));
        //physicsLoop();

    }

    public void start() {
        //physicsLoop();
        timer.scheduleAtFixedRate(timerTask, 20, 20); //20 ms = 50 fps
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

        /*if (!timerStarted) {
            timer.scheduleAtFixedRate(timerTask, 20, 20); //20 ms = 50 fps
            timerStarted = true;
        }*/
        
        long deltaNanoTime;
        deltaNanoTime = (System.nanoTime()-oldTime);
        double deltaTime = (double)deltaNanoTime/1000000000;

        //code to manage delays between updating the picture
        /*for (int i = 0; i < getBasePhysicsCount(); i++) {
            getBasePhysics(i).physicsSimulate(deltaTime);
        }*/
        update(deltaTime);
        force(deltaTime);
        movement();
        collisionDetection(deltaTime);
        collisionResponse(deltaTime);
        position(deltaTime);
        graphics();

        oldTime = System.nanoTime();
    }

    protected void update(double deltaTime) {
        for (BasePhysics element : basePhysicsList) {
            element.update(deltaTime);
            element.physicsUpdate();
        }
    }

    protected void update(BasePhysics object, double deltaTime) {
        object.update(deltaTime);
        object.physicsUpdate();
    }

    protected void force(double deltaTime) {
        for (BasePhysics element : basePhysicsList) {
            element.applyForces(deltaTime);
            element.simulateFriction();
        }
    }

    protected void force(BasePhysics object, double deltaTime) {
        object.applyForces(deltaTime);
        object.simulateFriction();
    }

    protected void movement() {
        for (BasePhysics element : basePhysicsList) {
            element.simulateForce();
            element.simulateVelocity();
        }
    }

    protected void movement(BasePhysics element) {
        element.simulateForce();
        element.simulateVelocity();
    }

    protected void collisionDetection(double deltaTime) {
        for (int i = 0; i < basePhysicsList.size()-1; i++) {
            BasePhysics element = basePhysicsList.get(i);
            this.detectCollisions(element, basePhysicsList, 0, deltaTime, i);
        }
    }

    protected void detectCollisions(BasePhysics basePhysics,
            List<BasePhysics> group, double time0, double time) {
        
        this.detectCollisions(basePhysics, group, time0, time, 0);
    }

    protected void detectCollisions(BasePhysics basePhysics,
            List<BasePhysics> group, double time0, double time,
            int startIndex) {

        for(int i = startIndex; i < group.size(); i++) {
            BasePhysics element = group.get(i);
            if (group.get(i) != basePhysics) {
                if (isColliding(basePhysics, element, time0, time)) {
                    double t = this.approxCollisionTime(basePhysics, element, time0, time);
                    if (t >= time0 && t <= time) {
                        collisions.add(new Collision(basePhysics, element, t, false));
                    }
                }
            }
        }
    }

    protected double approxCollisionTime(BasePhysics object1, BasePhysics object2,
            double time0, double time) {
        double t = time0;
        double divisor;
        if (!isIntersecting(object1, object2, time0)
                && isColliding(object1, object2, time0, time)
                && collisionIsPossible(object1, object2, time0, time)) {
            for (int i = 0; i < 10; i++) {
                divisor = Math.pow(2, i+1);
                if (!isColliding(object1, object2, t, t+(time-t)/divisor)) {
                    t += (time-t)/divisor;
                    i--;
                    if (t > time || !isColliding(object1, object2, t, time)) {
                        return time0-1;
                    }
                }
            }
            if (isColliding(object1, object2, t, time)) {
                return t;
            }
        } else {
        }
        return time0-1;
    }

    protected boolean collisionIsPossible(BasePhysics object1,
            BasePhysics object2, double time0, double time) {
        Coordinate pos1 = object1.getPosAtTime(time);
        Coordinate pos2 = object2.getPosAtTime(time);
        Coordinate v1 = object1.getVel(time);
        Coordinate v2 = object2.getVel(time);
        Coordinate a1 = object1.acceleration;
        Coordinate a2 = object1.acceleration;
        boolean x = ((pos1.x() > pos2.x() && v1.x() >= v2.x() && a1.x() >= a2.x())
                ||(pos2.x() > pos1.x() && v2.x() >= v1.x() && a2.x() >= a1.x()));
        boolean y = ((pos1.y() > pos2.y() && v1.y() >= v2.y() && a1.y() >= a2.y())
                ||(pos2.y() > pos1.y() && v2.y() >= v1.y() && a2.y() >= a1.y()));
        return (!x||!y);
        //return true;
    }

    protected boolean isColliding(BasePhysics object1, BasePhysics object2,
            double time0, double time) {
        return (GeomUtils.lineSegDistSq(
                            new Line2D.Double(
                                object1.getPosAtTime(time0).toPoint2D(),
                                object1.getPosAtTime(time).toPoint2D()),
                            new Line2D.Double(
                                object2.getPosAtTime(time0).toPoint2D(),
                                object2.getPosAtTime(time).toPoint2D())) <= (
                            Math.pow(object1.scale+object2.scale, 2)));
    }

    protected boolean isIntersecting(BasePhysics object1, BasePhysics object2,
            double time) {
        return (object1.getPosAtTime(time).toPoint2D().distanceSq(
                object2.getPosAtTime(time).toPoint2D())
                <=
                Math.pow(object1.scale+object2.scale, 2));
    }

    protected void collisionResponse(double deltaTime) {
        while (!collisions.isEmpty()) {
            Collision collision = Collision.getEarliest(collisions);
            collisions.remove(collision);
            if (collision != null) {
                System.out.println("Collision");
                double time = collision.getTime();
                BasePhysics object1 = collision.getObject1();
                BasePhysics object2 = collision.getObject2();
                Coordinate object1Pos = object1.getPosAtTime(time);
                Coordinate object2Pos = object2.getPosAtTime(time);
                Coordinate normal = Coordinate.normalized(object1Pos.sub(object2Pos));
                Coordinate tangent = new Coordinate(-normal.y(), normal.x());
                double vn1 = Coordinate.dot(normal, object1.getVel(time));
                double vn2 = Coordinate.dot(normal, object2.getVel(time));
                double vt1 = Coordinate.dot(tangent, object1.getVel(time));
                double vt2 = Coordinate.dot(tangent, object2.getVel(time));
                double m1 = object1.mass;
                double m2 = object2.mass;
                double cr = 1;
                double un1 = (cr*m2*(vn2-vn1)+m1*vn1+m2*vn2)/(m1+m2);
                double un2 = (cr*m1*(vn1-vn2)+m1*vn1+m2*vn2)/(m1+m2);
                object1.setVel(normal.mul(un1).add(tangent.mul(vt1)), time);
                object2.setVel(normal.mul(un2).add(tangent.mul(vt2)), time);
                movement(object1);
                movement(object2);
                object1.setPosAtTime(object1Pos, time);
                object2.setPosAtTime(object2Pos, time);
                if (approxCollisionTime(object1, object2, time, deltaTime) != time) {
                    System.out.println(approxCollisionTime(object1, object2, time, deltaTime) - time);
                    detectCollisions(object2, basePhysicsList, time, deltaTime);
                    detectCollisions(object1, basePhysicsList, time, deltaTime);
                    System.out.println("Rechecking collisions");
                } else {
                    Coordinate acceleration1 = object1.acceleration.mul(normal);
                    Coordinate acceleration2 = object2.acceleration.mul(normal);
                    Coordinate combined = (acceleration1.mul(m1).add(acceleration2.mul(m2).div(m1+m2)));
                    System.out.println("Closest distance reached");
                    /*object1.acceleration = object1.acceleration.add(combined).sub(acceleration1);
                    object2.acceleration = object2.acceleration.add(combined).sub(acceleration2);
                    detectCollisions(object2, basePhysicsList, time, deltaTime);
                    detectCollisions(object1, basePhysicsList, time, deltaTime);*/
                }
            }
        }

    }

    protected void position(double deltaTime) {
        for (BasePhysics element : basePhysicsList) {
            element.updatePos(deltaTime);
        }
    }

    protected void graphics() {
        for (BasePhysics element : basePhysicsList) {
            element.updateGraphic();
        }
    }

}



