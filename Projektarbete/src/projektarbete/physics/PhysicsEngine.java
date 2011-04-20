/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
            if (Stage.isRunning()) {
                physicsLoop();
            }
        }
    };

    Map<Short, PhysicsObject> objects = new HashMap<Short, PhysicsObject>();
    LinkedList<Collision> collisions = new LinkedList<Collision>();
    LinkedList<ObjectUpdate> updates = new LinkedList<ObjectUpdate>();
    int count = 0;

    long oldTime = 0;//System.nanoTime();

    short currentKey = 1;

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

    public synchronized short addObject( PhysicsObject object ) {

            while (this.objects.containsKey(currentKey)) {
                currentKey++;
            }
            this.objects.put(currentKey, object);
            return currentKey;

    }

    public synchronized boolean addObject( PhysicsObject object, short key) {

            if (!this.objects.containsKey(key)) {
                this.objects.put(key, object);
                return true;
            } else {
                return false;
            }

    }

    public synchronized void removeObject( PhysicsObject object ) {


        while (this.objects.values().remove(object));
        object.clearVisibleObject();
        collisions.removeAll(object.collisions);

    }

    public synchronized void removeObject(short key) {
        if (objects.containsKey(key)) {
            objects.remove(key).clearVisibleObject();
        }

    }

    public synchronized int getObjectCount() {

        return objects.size();

    }

    public synchronized void physicsLoop (){
        //synchronized(this) {
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
        applyUpdates();
        update(deltaTime);
        force(deltaTime);
        movement();
        collisionDetection(deltaTime);
        collisionResponse(deltaTime);
        position(deltaTime);
        graphics();

        oldTime = System.nanoTime();
        //}
    }

    protected void update(double deltaTime) {
        for (PhysicsObject element : objects.values()) {
            element.update(deltaTime);
            element.physicsUpdate();
        }
    }

    protected void update(PhysicsObject object, double deltaTime) {
        object.update(deltaTime);
        object.physicsUpdate();
    }

    protected void force(double deltaTime) {
        for (PhysicsObject element : objects.values()) {
            element.applyForces(deltaTime);
            element.simulateFriction();
        }
    }

    protected void force(PhysicsObject object, double deltaTime) {
        object.applyForces(deltaTime);
        object.simulateFriction();
    }

    protected void movement() {
        for (PhysicsObject element : objects.values()) {
            element.simulateForce();
            element.simulateVelocity();
        }
    }

    protected void movement(PhysicsObject element) {
        element.simulateForce();
        element.simulateVelocity();
    }

    protected void collisionDetection(double deltaTime) {
        List<PhysicsObject> list = new LinkedList(objects.values());
        for (int i = 0; i < list.size()-1; i++) {
            PhysicsObject element = list.get(i);
            this.detectCollisions(element, list, 0, deltaTime, i);
        }
    }

    protected void detectCollisions(PhysicsObject object,
            List<PhysicsObject> group, double time0, double time) {
        
        this.detectCollisions(object, group, time0, time, 0);
    }

    protected void detectCollisions(PhysicsObject object,
            List<PhysicsObject> group, double time0, double time,
            int startIndex) {

        for(int i = startIndex; i < group.size(); i++) {
            PhysicsObject element = group.get(i);
            if (group.get(i) != object) {
                if (isColliding(object, element, time0, time)) {
                    double t = this.approxCollisionTime(object, element, time0, time);
                    if (t >= time0 && t <= time) {
                        Collision collision = new Collision(object, element, t, false);
                        collisions.add(collision);
                        object.collisions.add(collision);
                        element.collisions.add(collision);
                    }
                }
            }
        }
    }

    protected double approxCollisionTime(PhysicsObject object1, PhysicsObject object2,
            double time0, double time) {
        double t = time0;
        double divisor;
        if (!isIntersecting(object1, object2, time0)
                && (isIntersecting(object1, object2, time)
                ||(isColliding(object1, object2, time0, time)
                && collisionIsPossible(object1, object2, time0)))) {
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

    protected boolean collisionIsPossible(PhysicsObject object1,
            PhysicsObject object2, double time0) {

        Coordinate pos1 = object1.getPosAtTime(time0);
        Coordinate pos2 = object2.getPosAtTime(time0);
        Coordinate normal = Coordinate.normalized(pos1.sub(pos2));
        Coordinate v1 = object1.getVel(time0);
        Coordinate v2 = object2.getVel(time0);
        Coordinate a1 = object1.acceleration;
        Coordinate a2 = object2.acceleration;
        double yV1 = Coordinate.dot(normal, v1);
        double yV2 = Coordinate.dot(normal, v2);
        double yA1 = Coordinate.dot(normal, a1);
        double yA2 = Coordinate.dot(normal, a2);
        
        return (yV2>yV1||yA2>yA1);
    }

    protected boolean isColliding(PhysicsObject object1, PhysicsObject object2,
            double time0, double time) {
        return (GeomUtils.lineSegDistSq(
                            new Line2D.Double(
                                object1.getPosAtTime(time0).toPoint2D(),
                                object1.getPosAtTime(time).toPoint2D()),
                            new Line2D.Double(
                                object2.getPosAtTime(time0).toPoint2D(),
                                object2.getPosAtTime(time).toPoint2D())) <= (
                            Math.pow(object1.size+object2.size, 2)));
    }

    protected boolean isIntersecting(PhysicsObject object1, PhysicsObject object2,
            double time) {
        return (object1.getPosAtTime(time).toPoint2D().distanceSq(
                object2.getPosAtTime(time).toPoint2D())
                <=
                Math.pow(object1.size+object2.size, 2));
    }

    protected void collisionResponse(double deltaTime) {
        while (!collisions.isEmpty()) {
            Collision collision = Collision.getEarliest(collisions);
            collisions.remove(collision);
            if (collision != null) {
                double time = collision.getTime();
                PhysicsObject object1 = collision.getObject1();
                PhysicsObject object2 = collision.getObject2();

                setPostCollisionVelocities(object1, object2, time);

                manageRepeatedCollisions(object1, object2, time, deltaTime);
                
                object1.collisionCount++;
                object2.collisionCount++;

                collisions.removeAll(object1.collisions);
                collisions.removeAll(object2.collisions);
                
                object1.collisions.clear();
                object2.collisions.clear();

                detectCollisions(object2,
                        new LinkedList<PhysicsObject>(objects.values()),
                        time, deltaTime);


                detectCollisions(object1,
                        new LinkedList<PhysicsObject>(objects.values()),
                        time, deltaTime);

            }
        }

    }

    private void setPostCollisionVelocities(PhysicsObject object1,
                                            PhysicsObject object2,
                                            double time) {

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

        double cr = object1.restitution*object2.restitution;

        double un1 = (cr*m2*(vn2-vn1)+m1*vn1+m2*vn2)/(m1+m2);
        double un2 = (cr*m1*(vn1-vn2)+m1*vn1+m2*vn2)/(m1+m2);

        object1.setVel(normal.mul(un1).add(tangent.mul(vt1)), time);
        object2.setVel(normal.mul(un2).add(tangent.mul(vt2)), time);
    }

    private void manageRepeatedCollisions(PhysicsObject object1,
                                          PhysicsObject object2,
                                          double time, double deltaTime) {
        
        if (object1.collisionCount > 10 && object2.collisionCount > 10) {
            if (object1.collisionCount > 50 && object2.collisionCount > 50) {
                /*System.out.println("50+ collisions between "+ object1+
                        " and "+object2);
                Coordinate vel1 = new Coordinate(object1.getVel(time));
                double m1 = object1.mass;
                Coordinate vel2 = new Coordinate(object2.getVel(time));
                double m2 = object2.mass;
                Coordinate totalMomentum = vel1.mul(m1).add(vel2.mul(m2));
                object1.setVel(totalMomentum.div(2).div(m1), time);
                object2.setVel(totalMomentum.div(2).div(m2), time);*/
                object1.setVel(0, 0, time);
                object2.setVel(0, 0, time);

            } else {
                object1.setAcceleration(0, 0, time);
                object2.setAcceleration(0, 0, time);
            }
        }
        if (approxCollisionTime(object1, object2, time, deltaTime) == time) {
            object1.setAcceleration(0, 0, time);
            object2.setAcceleration(0, 0, time);
        }
    }

    protected void position(double deltaTime) {
        for (PhysicsObject element : objects.values()) {
            element.updatePos(deltaTime);
        }
    }

    protected void graphics() {
        for (PhysicsObject element : objects.values()) {
            element.updateGraphic();
        }
    }

    protected void applyUpdates() {
            for (ObjectUpdate update : updates) {
                if (objects.containsKey(update.getId())) {
                    objects.get(update.getId()).applyUpdate(update.getUpdate());
                }
            }
            updates.clear();
    }

    public void updateObject(PhysicsUpdate update, short id) {
        updateObject(new ObjectUpdate(update, id));
    }

    public synchronized void updateObject(ObjectUpdate update) {
        updates.add(update);
    }

    public synchronized PhysicsUpdate getUpdate(short id) {
        return objects.get(id).getUpdate();
    }

    public synchronized void createObject(ObjectData data) {
        addObject(new PhysicsObject(data.getData()), data.getId());
    }

    public synchronized short createObject(PhysicsData data) {
        return addObject(new PhysicsObject(data));
    }

    public synchronized PhysicsData getData(short id) {
        return objects.get(id).getData();
    }

}



