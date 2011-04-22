/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import projektarbete.Coordinate;
import projektarbete.graphics.VisibleObject;

/**
 *
 * @author felix.hallqvist
 */
public class PhysicsObject {

    protected Coordinate position = new Coordinate(0, 0);
    protected Coordinate velocity = new Coordinate(0, 0);
    protected Coordinate acceleration = new Coordinate(0, 0);
    protected Coordinate force = new Coordinate(0, 0);
    protected double angle;
    protected double angularVelocity;
    protected double mass;
    protected double airFriction;
    protected Coordinate gravityDir = new Coordinate(0, 0);
    protected double gravity;
    protected boolean gravityFlag;
    protected boolean airFrictionFlag;
    protected double size;
    protected double restitution;
    protected Hitbox hitbox;
    protected short template;
    private short id;

    VisibleObject visibleObject;

    protected int collisionCount;
    protected List<Collision> collisions = new ArrayList<Collision>();
    

    public PhysicsObject() {

        this((short) 0);
        //Initializing variables

        /*position = new Coordinate(0,0);
        velocity = new Coordinate(0,0);
        acceleration = new Coordinate(0,0);
        force = new Coordinate(0,0);
        angle = 0;
        //hitbox = new Hitbox();


        mass = 1;
        airFriction = 0.2;
        gravityDir = new Coordinate(0,-1);
        gravity = 9.82;
        gravityFlag = false;
        airFrictionFlag = true;
        size = 1;
        restitution = 1;
        template = 0;*/
        
        
        //PhysicsEngine.getInstance().addBasePhysics(this);
        
    }

    public PhysicsObject(short template) {
        this(template, 1, 1, new PhysicsUpdate(0, 0, 0, 0, 0, 0));
    }

    public PhysicsObject(short template, double mass, double size) {
        this(template, mass, size, new PhysicsUpdate(0, 0, 0, 0, 0, 0));
    }

    public PhysicsObject(PhysicsData data) {
        this(data.getTemplate(), data.getMass(), data.getSize(), data.getUpdate());
    }

    public PhysicsObject(short template, double mass, double size,
                       PhysicsUpdate update) {
        this.template = template;
        PhysicsModel model = Templates.getTemplate(template, mass, size);
        this.applyModel(model);
        this.applyUpdate(update);
        visibleObject.removeFromParent();
    }

    public void hookTest(String test) {
        System.out.println(test);
    }


    public void updateGraphic() {

        if (visibleObject != null) {
            visibleObject.setPos(position.sub(size, -size).mul(1,-1));
            visibleObject.setAng(angle);
        }

    }

    public void physicsUpdate() {
        /*this method only exists to be overridden by subclasses of base physics
        so that subclasses can add their own code to the physics simulation
         
         
         this code is run at the very beginning of the simulation*/
    }
    
    public void physicsOnSimulate() {
        /*this method only exists to be overridden by subclasses of base physics
        so that subclasses can add their own code to the physics simulation
         
         
         this code is run at the end of the simulation*/
    }

    public void physicsForces() {
        /*this method only exists to be overridden by subclasses of base physics
        so that subclasses can add their own code to the physics simulation


         this code is run after forces are applied*/
    }

    public void physicsGravity() {
        /*this method only exists to be overridden by subclasses of base physics
        so that subclasses can add their own code to the physics simulation


         this code is run after gravity is simulated*/
    }

    public void physicsForce() {
        /*this method only exists to be overridden by subclasses of base physics
        so that subclasses can add their own code to the physics simulation


         this code is run after force is simulated*/
    }

    public void physicsFriction() {
        /*this method only exists to be overridden by subclasses of base physics
        so that subclasses can add their own code to the physics simulation


         this code is run after friction is simulated*/
    }

    public void physicsVelocity() {
        /*this method only exists to be overridden by subclasses of base physics
        so that subclasses can add their own code to the physics simulation
         
         
         this code is run after velocity is simulated*/
    }

    public void physicsCollision(Collision collision) {
    }

    protected void updateStart(double deltaTime) {
        physicsUpdate();
    }

    protected void updateEnd(double deltaTime) {
        collisions.clear();
        collisionCount = 0;
        velocity.setPos(this.getVel(deltaTime));
        acceleration.setPos(0, 0);
        force.setPos(0, 0);
    }

    //Accerelation and forces
    public void applyForces(double deltaTime) {
        //these are forces which are always applied during the physics simulation

        simulateGravity(deltaTime);


        physicsForces();
    }

    public void simulateForce() {
        acceleration.setPos(acceleration.add(force.div(mass)));
        //System.out.println(this+" acceleration: "+acceleration);
        force.setPos(0, 0);
        
        physicsForce();
    }

    public void simulateFriction() {

        if (airFrictionFlag) {
            //System.out.println(this+" force before friction: "+force);
            //force.setPos(force.sub(force.add(velocity.mul(mass)).mul(airFriction)));
            force.setPos(force.sub(force.mul(airFriction)).sub(velocity.mul(mass).mul(airFriction)));
            //force.setPos(force.sub(velocity.mul(velocity.div(2)).mul(airFriction)));
            //System.out.println(this+" force after friction: "+force);
            physicsFriction();
        }

    }

    public void simulateVelocity() {
        //position.setPos(position.add(velocity.mul(deltaTime).div(scale)));
        /*if (Math.abs(velocity.x())<1/1000) {
            velocity.x(0);
        }
        if (Math.abs(velocity.y())<1/1000) {
            velocity.y(0);
        }*/
        //futurePosition.setPos(getPosAtTime(deltaTime));
        physicsVelocity();
    }

    public Coordinate getPosAtTime(double time) {
        double x0 = position.x();
        double xVel = velocity.x();
        double xAcc = acceleration.x();
        double x = x0 + xVel*time + (xAcc*time*time)/2;
        
        double y0 = position.y();
        double yVel = velocity.y();
        double yAcc = acceleration.y();
        double y = y0 + yVel*time + (yAcc*time*time)/2;

        return new Coordinate(x, y);
        //return new Coordinate(position.add(getVel().mul(time).add(acceleration.mul((time*time)/2))));
    }

    public void setPosAtTime(Coordinate position, double time) {
        double x = position.x();
        double xVel = velocity.x();
        double xAcc = acceleration.x();
        double x0 = x - xVel*time - (xAcc*time*time)/2;

        double y = position.y();
        double yVel = velocity.y();
        double yAcc = acceleration.y();
        double y0 = y - yVel*time - (yAcc*time*time)/2;



        //this.position.setPos(position.sub(getVel().mul(time).sub(acceleration.mul((time*time)/2))));
        this.position.setPos(x0, y0);
    }

    public void updatePos(double deltaTime) {
        //setPos(futurePosition);
        //    System.out.println(this+" velocity: "+velocity);
        //System.out.println(this+" acceleration: "+acceleration);
        setPos(this.getPosAtTime(deltaTime));
    }

    public void simulateGravity(double deltaTime) {
        if (gravityFlag) {
             //applyForce(gravityDir.mul(gravity * mass * deltaTime));
            //acceleration.setPos(acceleration.add(gravityDir.mul(gravity)));
            this.applyForce(gravityDir.mul(gravity * mass));
            physicsGravity();
        }
    }
    
    public void move(double x, double y) {

        position.setPos(position.add(x, y));


        //updateGraphic();

    }

    //Position
    public void setPos(Coordinate c) {

        position.setPos(c);

        //updateGraphic();

    }

    public void setPos(double x, double y) {

        setPos(new Coordinate(x,y));

    }

    public Coordinate getPos() {

        return position;

    }

    //Velocity
    public void setVel(double x, double y) {

        setVel(x, y, 0);

    }
    public void setVel(double x, double y, double time) {

        setVel(new Coordinate(x,y));

    }
    
    public void setVel(Coordinate vel) {

        setVel(vel, 0);

    }

    public void setVel(Coordinate vel, double time) {
        Coordinate oldPos = getPosAtTime(time);
        double xVel = vel.x();
        double xAcc = acceleration.x();
        double xVel0 = xVel - xAcc*time;

        double yVel = vel.y();
        double yAcc = acceleration.y();
        double yVel0 = yVel - yAcc*time;
        velocity = new Coordinate(xVel0, yVel0);
        setPosAtTime(oldPos, time);

    }

    public Coordinate getVel() {

        return getVel(0);

    }

    public Coordinate getVel(double time) {

        double xVel0 = velocity.x();
        double xAcc = acceleration.x();
        double xVel = xVel0 + xAcc*time;

        double yVel0 = velocity.y();
        double yAcc = acceleration.y();
        double yVel = yVel0 + yAcc*time;

        return new Coordinate(xVel, yVel);

    }

    public void setAcceleration(Coordinate acceleration, double time) {
        Coordinate oldPos = getPosAtTime(time);
        this.acceleration.setPos(acceleration);
        setPosAtTime(oldPos, time);
    }

    public void setAcceleration(double xAcc, double yAcc, double time) {
        setAcceleration(new Coordinate(xAcc, yAcc), time);
    }

    public void setAcceleration(double xAcc, double yAcc) {
        setAcceleration(new Coordinate(xAcc, yAcc), 0);
    }

    public void setAcceleration(Coordinate acceleration) {
        setAcceleration(acceleration, 0);
    }

    public Coordinate getAcceleration() {
        return acceleration;
    }

    public Coordinate getMomentum() {
        return getMomentum(0);
    }

    public Coordinate getMomentum(double time) {
        return getVel(time).mul(mass);
    }

    public void applyForce(double hF, double vF) {
        applyForce(new Coordinate(hF,vF));

    }

    public void applyForce(Coordinate force) {
        this.force.setPos(this.force.add(force));
    }

    public void applyImpulse(double hF, double vF) {
        applyImpulse(new Coordinate(hF,vF));

    }
    public void applyImpulse(Coordinate force) {

        this.velocity.setPos(this.velocity.add(force.div(mass)));

    }

    public void setGravityDir(Coordinate dir) {
        gravityDir =  Coordinate.normalized(dir);
    }
    public void setGravity(double magnitude) {
        gravity = magnitude;
    }
    public void setGravity(Coordinate gravForce) {
        setGravityDir(gravForce);
        gravity = Coordinate.length(gravForce);
    }
    public void enableGravity(boolean enabled) {
        gravityFlag = enabled;
    }

    public PhysicsModel getModel() {
        return Templates.getTemplate(template, mass, size);
    }

    public final void applyModel(PhysicsModel model) {
        airFrictionFlag = model.airFrictionFlag();
        airFriction = model.getAirFriction();
        gravity = model.getGravity();
        gravityDir = model.getGravityDir();
        gravityFlag = model.gravityFlag();
        hitbox = model.getHitbox();
        mass = model.getMass();
        restitution = model.getRestitution();
        size = model.getSize();
        visibleObject = model.getVisibleObject();
    }

    public void setTemplate(short template) {
        this.template = template;
        applyTemplate();
    }

    public void applyTemplate() {
        clearVisibleObject();
        applyModel(getModel());
    }

    public short getTemplate() {
        return template;
    }

    public final void applyUpdate(PhysicsUpdate update) {
        position.setPos(update.getPosition());
        velocity.setPos(update.getVelocity());
        angle = update.getAngle();
        angularVelocity = update.getAngularVelocity();
    }

    public PhysicsUpdate getUpdate() {
        return new PhysicsUpdate(position.x(), position.y(),
                                 velocity.x(), velocity.y(),
                                 angle, angularVelocity);
    }

    public void applyData(PhysicsData data) {
        mass = data.getMass();
        size = data.getSize();
        setTemplate(data.getTemplate());
        applyUpdate(data.getUpdate());
    }

    public PhysicsData getData() {
        return new PhysicsData(template, mass, size, getUpdate());
    }

    public void clearVisibleObject() {
        visibleObject.removeFromParent();
    }

    public void displayVisibleObject() {
        visibleObject.addToParent();
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    /*public void showVisibleObject() {
        visibleObject.addToParent();
    }*/

}