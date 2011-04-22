/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import projektarbete.Communication;
import projektarbete.Coordinate;
import projektarbete.Stage;
import projektarbete.UDPSocket;


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

    Map<Short, PhysicsObject> objects = new TreeMap<Short, PhysicsObject>();
    LinkedList<Collision> collisions = new LinkedList<Collision>();
    LinkedList<ObjectUpdate> objectUpdates = new LinkedList<ObjectUpdate>();
    List<Player> playersForUpdating = new LinkedList<Player>();

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
            addObject(object, currentKey);
            return currentKey;

    }

    public synchronized boolean addObject( PhysicsObject object, short key) {

            if (!this.objects.containsKey(key) && key != 0) {
                this.objects.put(key, object);
                object.setId(key);
                object.displayVisibleObject();
                if (Stage.isServer() && !(object instanceof Player)) {
                    Updates.create(object);
                }
                if (object instanceof TestUserObject) {
                    System.out.println("Test user object added to list");
                }
                return true;
            } else {
                return false;
            }

    }

    public synchronized void removeObject( PhysicsObject object ) {


        while (objects.values().remove(object)){} /*the conditional statement
                                                  itself takes care of the
                                                  actions intended to be carried
                                                  out by the loop 
                                                  (removing elements)*/
        object.clearVisibleObject();
        collisions.removeAll(object.collisions);
        if (Stage.isServer()) {
            Updates.remove(object);
        }

    }

    public synchronized void removeObject(short key) {
        if (objects.containsKey(key)) {
            PhysicsObject object = objects.remove(key);
            object.clearVisibleObject();
            collisions.removeAll(object.collisions);
            if (Stage.isServer()) {
                Updates.remove(object);
            }
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
        List<PhysicsObject> list = getPhysicsObjects();
        //list.addAll(Players.getAll());
        applyPlayerUpdates();
        applyUpdates();
        updateStart(deltaTime, list);
        force(deltaTime, list);
        movement(list);
        collisionDetection(deltaTime, list);
        collisionResponse(deltaTime);
        position(deltaTime, list);
        updateEnd(deltaTime, list);
        if (Stage.isServer()) {
            Updates.sync(list);
            List<Player> playerList = Players.getAll();
            for (Player player : playerList) {
                Updates.update(player);
            }
            for (Communication communication : Updates.getUpdates()) {
                for (Player player : playerList) {
                    communication.setAddress(player.getAddress());
                    UDPSocket.send(communication);
                }
            }
            for (Player player : playersForUpdating) {
                UDPSocket.send(new Communication(player.getAddress(),
                        Communication.writePlayerUpdate(
                        player.getPlayerUpdate())));
            }
            playersForUpdating.clear();
        } else {
            for (Player player : Players.getAll()) {
                    UDPSocket.send(new Communication(Stage.getConnection(),
                            Communication.writePlayerUpdate(
                            player.getPlayerUpdate())));
            }
        }
        graphics(list);
        graphics(new LinkedList<PhysicsObject>(Players.getAll()));

        oldTime = System.nanoTime();
        //}
    }

    protected synchronized List<PhysicsObject> getPhysicsObjects() {
        List<PhysicsObject> list = new LinkedList();
        for (PhysicsObject element : objects.values()) {
            list.add(element);
        }
        return list;
    }

    protected synchronized void updateStart(double deltaTime, List<PhysicsObject> objects) {
        for (PhysicsObject element : objects) {
            element.updateStart(deltaTime);
            element.physicsUpdate();
        }
    }

    protected synchronized void updateEnd(double deltaTime, List<PhysicsObject> objects) {
        for (PhysicsObject element : objects) {
            element.updateEnd(deltaTime);
        }
    }

    protected void update(PhysicsObject object, double deltaTime) {
        object.updateStart(deltaTime);
        object.physicsUpdate();
    }

    protected synchronized void force(double deltaTime, List<PhysicsObject> objects) {
        for (PhysicsObject element : objects) {
            element.applyForces(deltaTime);
            element.simulateFriction();
        }
    }

    protected void force(PhysicsObject object, double deltaTime) {
        object.applyForces(deltaTime);
        object.simulateFriction();
    }

    protected synchronized void movement(List<PhysicsObject> objects) {
        for (PhysicsObject element : objects) {
            element.simulateForce();
            element.simulateVelocity();
        }
    }

    protected void movement(PhysicsObject element) {
        element.simulateForce();
        element.simulateVelocity();
    }

    protected void collisionDetection(double deltaTime, List<PhysicsObject> group) {
        if (group.size()>7) {
            this.splitCollisionDomain(deltaTime, group);
        } else {
            this.detectInternalCollisions(deltaTime, group);
        }
    }

    protected void detectInternalCollisions(double deltaTime, List<PhysicsObject> group) {
            for (int i = 0; i < group.size()-1; i++) {
            PhysicsObject element = group.get(i);
            this.detectCollisions(element, group, 0, deltaTime, i);
            }
    }

    protected void splitCollisionDomain(double deltaTime, List<PhysicsObject> group) {
        Coordinate center = this.getCenter(group);
        List<PhysicsObject> upperLeft = new LinkedList<PhysicsObject>();
        List<PhysicsObject> upperRight = new LinkedList<PhysicsObject>();
        List<PhysicsObject> lowerLeft = new LinkedList<PhysicsObject>();
        List<PhysicsObject> lowerRight = new LinkedList<PhysicsObject>();

        double xMid = center.x();
        double yMid = center.y();

        double xMin;
        double yMin;

        double xMax;
        double yMax;
        
        for (PhysicsObject element : group) {
            double size = element.size;
            xMin = Math.min(
                    element.getPos().x(),
                    element.getPosAtTime(deltaTime).x())
                    -size;

            xMax = Math.max(
                    element.getPos().x(),
                    element.getPosAtTime(deltaTime).x())
                    +size;
            yMin = Math.min(
                    element.getPos().y(),
                    element.getPosAtTime(deltaTime).y())
                    -size;

            yMax = Math.max(
                    element.getPos().y(),
                    element.getPosAtTime(deltaTime).y())
                    +size;

            if (xMin<=xMid && yMax>=yMid) {
                upperLeft.add(element);
            }
            if (xMax>=xMid && yMax>=yMid) {
                upperRight.add(element);
            }
            if (xMin<=xMid && yMin<=yMid) {
                lowerLeft.add(element);
            }
            if (xMax>=xMid && yMin<=yMid) {
                lowerRight.add(element);
            }
        }
        if (upperLeft.size()<group.size()) {
            this.collisionDetection(deltaTime, upperLeft);
        } else {
            this.detectInternalCollisions(deltaTime, upperLeft);
        }
        if (upperRight.size()<group.size()) {
            this.collisionDetection(deltaTime, upperRight);
        } else {
            this.detectInternalCollisions(deltaTime, upperRight);
        }
        if (lowerLeft.size()<group.size()) {
            this.collisionDetection(deltaTime, lowerLeft);
        } else {
            this.detectInternalCollisions(deltaTime, lowerLeft);
        }
        if (lowerRight.size()<group.size()) {
            this.collisionDetection(deltaTime, lowerRight);
        } else {
            this.detectInternalCollisions(deltaTime, lowerRight);
        }
    }

    protected Coordinate getCenter(List<PhysicsObject> group) {
        double xMin = Double.POSITIVE_INFINITY;
        double xMax = Double.NEGATIVE_INFINITY;
        double xMid = 0;
        double yMin = Double.POSITIVE_INFINITY;
        double yMax = Double.NEGATIVE_INFINITY;
        double yMid = 0;
        for (int i = 0; i < group.size()-1; i++) {
            PhysicsObject element = group.get(i);
            double x = element.getPos().x();
            xMin = Math.min(xMin, x);
            xMax = Math.max(xMax, x);
            double y = element.getPos().y();
            yMin = Math.min(yMin, y);
            yMax = Math.max(yMax, y);
        }
        xMid = (xMin+xMax)/2;
        yMid = (yMin+yMax)/2;
        return new Coordinate(xMid, yMid);
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

                object1.physicsCollision(collision);
                object2.physicsCollision(collision);

                if (Stage.isServer()) {
                    Updates.update(object1);
                    Updates.update(object2);
                }

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

        Coordinate pos1 = object1.getPosAtTime(time);
        Coordinate pos2 = object2.getPosAtTime(time);
        
        Coordinate normal = Coordinate.normalized(pos1.sub(pos2));
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
        Coordinate pos1 = object1.getPosAtTime(time);
        Coordinate pos2 = object2.getPosAtTime(time);
        if (object1.collisionCount > 10 && object2.collisionCount > 10) {
            if (object1.collisionCount > 50 && object2.collisionCount > 50) {
                pos1 = object1.getPosAtTime(time);
                pos2 = object2.getPosAtTime(time);
                object1.velocity.setPos(0, 0);
                object2.velocity.setPos(0, 0);
                object1.position.setPos(pos1);
                object2.position.setPos(pos2);
            } else {
                object1.acceleration.setPos(0, 0);
                object1.position.setPos(pos1.sub(object1.velocity.mul(time)));
                object2.acceleration.setPos(0, 0);
                object2.position.setPos(pos2.sub(object2.velocity.mul(time)));
            }
        }
        if (approxCollisionTime(object1, object2, time, deltaTime) == time) {
            object1.acceleration.setPos(0, 0);
            object1.position.setPos(pos1.sub(object1.velocity.mul(time)));
            object2.acceleration.setPos(0, 0);
            object2.position.setPos(pos2.sub(object2.velocity.mul(time)));
        }
    }

    protected synchronized void position(double deltaTime, List<PhysicsObject> objects) {
        for (PhysicsObject element : objects) {
            element.updatePos(deltaTime);
        }
    }

    protected synchronized void graphics(List<PhysicsObject> objects) {
        for (PhysicsObject element : objects) {
            element.updateGraphic();
        }
        Stage.getInstance().repaint();
    }

    protected synchronized void applyUpdates() {
        if (!Stage.isServer()) {
            for (ObjectUpdate update : objectUpdates) {
                if (objects.containsKey(update.getId())) {
                    objects.get(update.getId()).applyUpdate(update.getUpdate());
                } else {
                    Updates.requestData(update.getId());
                }
            }
        } else {
            for (ObjectUpdate update : objectUpdates) {
                if (objects.containsKey(update.getId())) {
                    PhysicsObject object = objects.get(update.getId());
                    PhysicsUpdate physicsUpdate = update.getUpdate();
                    Coordinate oldPos = object.position;
                    Coordinate newPos = physicsUpdate.getPosition();
                    double size = object.size;
                    for (PhysicsObject element : objects.values()){
                        if (element != object && !isIntersecting(object, element, 0)) {
                            Coordinate pos = element.position;
                            if (Line2D.ptLineDistSq(oldPos.x(), oldPos.y(),
                                                newPos.x(), newPos.y(),
                                                pos.x(), pos.y())>=Math.pow(size+element.size, 2)) {
                                physicsUpdate.setPosition(oldPos);
                                if (Players.isPlayer(object)) {
                                    playersForUpdating.add((Player) object);
                                }
                                break;
                            }
                        }
                    }
                    Updates.update(object);
                    object.applyUpdate(physicsUpdate);
                }
                else {
                    PhysicsObject object = new PhysicsObject();
                    object.setId(update.getId());
                    Updates.remove(object);
                }
            }
        }
        objectUpdates.clear();
    }

    protected synchronized void applyPlayerUpdates() {
        for (Player player : Players.getAll()) {
            PlayerUpdate update = player.getNextUpdate();
            if (update != null) {
                objectUpdates.add(update);
                player.setFlags(update.getFlags());
                player.setAim(update.getAim());
                player.clearNextUpdate();
            }
        }
    }

    public void updateObject(PhysicsUpdate update, short id) {
        updateObject(new ObjectUpdate(update, id));
    }

    public synchronized void updateObject(ObjectUpdate update) {
        if (!Players.isPlayer(update.getId())) {
            objectUpdates.add(update);
        }
    }

    public synchronized void requestUpdate(short id) {
        Updates.update(objects.get(id));
    }

    public synchronized void createObject(ObjectData data) {
        addObject(new PhysicsObject(data.getData()), data.getId());
    }

    public synchronized short createObject(PhysicsData data) {
        return addObject(new PhysicsObject(data));
    }

    public synchronized void createPlayer(ObjectData data) {
        if (!Stage.isServer()) {
            /*TestUserObject player = new TestUserObject(data.getData());
            Players.addPlayer(player, data.getId());
            addObject(player, data.getId());*/
            TestUserObject tuo = new TestUserObject(new PhysicsData(Templates.TYPE_PLAYER_OBJECT, 60, 1,
                new PhysicsUpdate(-8, 0, 0, 0, 0, 0)));
            addObject(tuo, data.getId());
            Players.addPlayer(tuo, tuo.getId());
        }
    }

    public synchronized void createPlayer(String address) {
        if (Stage.isServer()) {
            Player player = new Player(address);
            addObject(player);
            Players.addPlayer(player, player.getId());
            UDPSocket.send(new Communication(address,
                    Communication.writePlayerData(
                    new ObjectData(player.getData(),
                    player.getId()))));
            for (PhysicsObject object :
                new LinkedList<PhysicsObject>(this.objects.values())) {
                UDPSocket.send(new Communication(address,
                        Communication.writeObjectData(
                        new ObjectData(object.getData(), object.getId()))));
            }
            UDPSocket.send(new Communication(player.getAddress(),
                    Communication.writePlayerUpdate(player.getPlayerUpdate())));
        }
    }

    public synchronized void updatePlayer(PlayerUpdate data) {
        Player player = Players.findPlayerByID(data.getId());
        if (player != null) {
            player.setNextUpdate(data);
        }
    }

    public synchronized void requestData(short id) {
        Updates.create(objects.get(id));
    }

}



