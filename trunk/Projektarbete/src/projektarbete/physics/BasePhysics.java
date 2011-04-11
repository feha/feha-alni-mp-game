/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;

import java.awt.geom.Rectangle2D;
import projektarbete.Coordinate;
import projektarbete.graphics.VisibleObject;

/**
 *
 * @author felix.hallqvist
 */
public class BasePhysics {

    protected Coordinate position;
    protected Coordinate velocity;
    protected Coordinate acceleration;
    protected Coordinate force;
    protected double angle;
    protected double mass;
    protected double airFriction;
    protected Coordinate gravityDir;
    protected double gravity;
    protected boolean gravityFlag;
    protected boolean airFrictionFlag;
    protected double size;
    protected double restitution;
    protected Hitbox hitbox;

    VisibleObject visibleObject;
    

    public BasePhysics() {

        //Initializing variables

        position = new Coordinate(0,0);
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
        
        
        //PhysicsEngine.getInstance().addBasePhysics(this);
        
    }

    public BasePhysics(PhysicsModel model) {
        
    }

    private void initTesting() {

        //visibleObject = new MyRectangle2D(Camera.getInstance());
        //visibleObject.setScale(0.05);
        /*
        try {
            Hook.add(1, getClass().getMethod("hookTest", new Class[] {String.class}), this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Hook.call(1,"test");
        */
    }

    public void hookTest(String test) {
        System.out.println(test);
    }


    public void updateGraphic() {

        if (visibleObject != null) {
            visibleObject.setPos(position.mul(1,-1));
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

    protected void update(double deltaTime) {
        velocity.setPos(this.getVel(deltaTime));
        acceleration.setPos(0, 0);
        force.setPos(0, 0);
        physicsUpdate();
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
        return new Coordinate(position.add(getVel().mul(time).add(acceleration.mul((time*time)/2))));
    }

    public void setPosAtTime(Coordinate position, double time) {
        this.position.setPos(position.sub(getVel(time).mul(time).sub(acceleration.mul((time*time)/2))));
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


        updateGraphic();

    }

    //Position
    public void setPos(Coordinate c) {

        position.setPos(c);

        updateGraphic();

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

        velocity.setPos(vel.sub(acceleration.mul(time)));

    }

    public Coordinate getVel() {

        return getVel(0);

    }

    public Coordinate getVel(double time) {

        return velocity.add(acceleration.mul(time));

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

    public void applyModel(PhysicsModel model) {
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

    public void applyUpdate(PhysicsUpdate update) {
        System.out.println("Applying update");
        position.setPos(update.getPosition());
        velocity.setPos(update.getVelocity());
        angle = update.getAngle();
    }

}