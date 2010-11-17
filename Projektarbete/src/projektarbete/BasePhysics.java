/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;

/**
 *
 * @author felix.hallqvist
 */
public class BasePhysics {

    public BasePhysics () {

        System.out.println("BasePhysics Initializing");

        initComponents();
        initTesting();

        System.out.println("BasePhysics Initialized");
        
    }

    protected Coordinate coordinates;
    protected Coordinate velocity;
    protected Coordinate force;
    protected double mass;
    protected double airFriction;
    protected Coordinate gravityDir;
    protected double gravity;
    protected boolean gravityFlag;
    protected boolean airFrictionFlag;
    protected boolean frozenFlag;
    protected double deltaTime;
    protected double scale;

    VisibleObject visibleObject;
    VisibleObject smiley;
    

    private void initComponents() {

        PhysicsEngine.getInstance().addBasePhysics(this);

        //Initializing variables

        coordinates = new Coordinate(0,0);
        velocity = new Coordinate(0.5,0.10);
        force = new Coordinate(0,0);


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

        visibleObject = new MyRectangle2D(Camera.getInstance());
        visibleObject.setScale(0.05);
        smiley = new Smiley(visibleObject);
        smiley.setScale(1);
        try {
            Hook.add(1, getClass().getMethod("hookTest", new Class[] {String.class}), this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Hook.call(1,"test");
    }

    public void hookTest(String test) {
        System.out.println(test);
    }

    public void updateGraphic() {

        if (visibleObject != null) {
            visibleObject.setPos(coordinates.mul(1,-1));
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
    private void applyForces() {
        //these are forces which are always applied during the physics simulation

        simulateGravity();

        physicsForces();
    }

    private void simulateForce() {
        velocity.setPos(velocity.add(force.div(mass)));
        force.setPos(0, 0);
        physicsForce();
    }

    private void simulateFriction() {

        if (airFrictionFlag) {
            force.setPos(force.sub(force.add(velocity.mul(mass)).mul(airFriction)));
            physicsFriction();
        }

    }

    private void simulateVelocity() {
        coordinates.setPos(coordinates.add(velocity.mul(deltaTime).div(scale)));
        physicsVelocity();
    }

    private void simulateGravity() {
        if (gravityFlag) {
             applyForce(gravityDir.mul(gravity * mass * deltaTime));
             physicsGravity();
        }
    }
    
    public void move(double x, double y) {

        coordinates.setPos(coordinates.add(x, y));


        updateGraphic();

    }

    //Position
    public void setPos(Coordinate c) {

        coordinates.setPos(c);

        updateGraphic();

    }

    public void setPos(double x, double y) {

        setPos(new Coordinate(x,y));

    }

    public Coordinate getPos() {

        return coordinates;

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