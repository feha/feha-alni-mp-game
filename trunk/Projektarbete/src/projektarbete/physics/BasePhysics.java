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

    public BasePhysics () {

        System.out.println("BasePhysics Initializing");

        initComponents();
        //initTesting();

        System.out.println("BasePhysics Initialized");
        
    }

    protected Coordinate position;
    protected Coordinate futurePosition;
    protected Coordinate velocity;
    protected Coordinate force;
    protected double angle;
    protected double mass;
    protected double airFriction;
    protected Coordinate gravityDir;
    protected double gravity;
    protected boolean gravityFlag;
    protected boolean airFrictionFlag;
    protected boolean frozenFlag;
    protected double deltaTime;
    protected double time;
    protected double scale;
    protected Hitbox hitbox;

    VisibleObject visibleObject;
    

    private void initComponents() {

        PhysicsEngine.getInstance().addBasePhysics(this);

        //Initializing variables

        position = new Coordinate(0,0);
        futurePosition = new Coordinate(0,0);
        velocity = new Coordinate(0,0);
        force = new Coordinate(0,0);
        angle = 0;
        //hitbox = new Hitbox();


        mass = 100;
        airFriction = 0.02;
        gravityDir = new Coordinate(0,-1);
        gravity = 9.82;
        gravityFlag = false;
        airFrictionFlag = true;
        frozenFlag = false;
        scale = 1;
        
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

    public Rectangle2D.Double getMovementBounds() {
        double xMin = Math.min(position.x(), futurePosition.x());
        double yMin = Math.min(position.y(), futurePosition.y());
        double xMax = Math.max(position.x(), futurePosition.x());
        double yMax = Math.max(position.y(), futurePosition.y());

        return new Rectangle2D.Double(
                xMin -(scale*hitbox.getSize()),
                yMin -(scale*hitbox.getSize()),
                xMax - xMin + 2 * (scale*hitbox.getSize()),
                yMax - yMin + 2 * (scale*hitbox.getSize()));
    }

    public void updateGraphic() {

        if (visibleObject != null) {
            visibleObject.setPos(position.mul(1,-1));
            visibleObject.setAng(angle);
        }

    }

    public void physicsSimulate(double dt) {

        setDeltaTime(dt);
        
        if (!frozenFlag) {

            
            physicsUpdate();

            //Forces
            applyForces();
            simulateFriction();

            //Position
            simulateForce();
            simulateVelocity();

            physicsOnSimulate();
        }

        updateGraphic();

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

    //Accerelation and forces
    public void applyForces() {
        //these are forces which are always applied during the physics simulation

        simulateGravity();

        physicsForces();
    }

    public void simulateForce() {
        velocity.setPos(velocity.add(force.div(mass)));
        force.setPos(0, 0);
        
        physicsForce();
    }

    public void simulateFriction() {

        if (airFrictionFlag) {
            force.setPos(force.sub(force.add(velocity.mul(mass)).mul(airFriction)));
            physicsFriction();
        }

    }

    public void simulateVelocity() {
        //position.setPos(position.add(velocity.mul(deltaTime).div(scale)));
        futurePosition.setPos(getPosAtTime(deltaTime));
        physicsVelocity();
    }

    public Coordinate getPosAtTime(double time) {
        return new Coordinate(position.add(velocity.mul(time)));
    }

    public void updatePos() {
        setPos(futurePosition);
    }

    public void simulateGravity() {
        if (gravityFlag) {
             applyForce(gravityDir.mul(gravity * mass * deltaTime));
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

        setVel(new Coordinate(x,y));

    }
    public void setVel(Coordinate vel) {

        velocity.setPos(vel.x(), vel.y());

    }

    public Coordinate getVel() {

        return velocity;

    }

    public void applyForce(double hF, double vF) {
        applyForce(new Coordinate(hF,vF));

    }
    public void applyForce(Coordinate F) {

        force.setPos(force.add(F));

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

    //time
    public void setDeltaTime(double dt) {
        deltaTime=dt;
    }
    public double getDeltaTime() {
        return deltaTime;
    }

}