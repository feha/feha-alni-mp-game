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
    double deltaTime;
    protected double scale;

    VisibleObject visibleObject;
    

    private void initComponents() {

        PhysicsEngine.getInstance().addBasePhysics(this);

        //Initializing variables

        coordinates = new Coordinate(0,-100);
        velocity = new Coordinate(5,10);
        force = new Coordinate(0,0);



        mass = 100;
        airFriction = 0;
        gravityDir = new Coordinate(0,1);
        gravity = 9.82;
        gravityFlag = true;
        airFrictionFlag = true;
        frozenFlag = false;
        scale = 1;
        
    }

    private void initTesting() {

        visibleObject = new Hexagon(Camera.getInstance());
        visibleObject.offset(0, 0);
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
            visibleObject.setPos(coordinates.getMul(1,-1));
        }

    }

    public void physicsSimulate(double deltaTime) {
        
        if (!frozenFlag) {
            if (gravityFlag) {
                applyForce(0, -gravity * mass * deltaTime);
                //verticalAcceleration-= gravity * deltaTime;
            }
            System.out.println("Position: "+coordinates+" Force: "+force+" Velocity: "+velocity);


            //Position
            simulateForce();
            
            coordinates.add(velocity.getMul(deltaTime).getDiv(scale));
            
            System.out.println("Position: "+coordinates+" Force: "+force+" Velocity: "+velocity);
        }

        updateGraphic();

    }

    //Accerelation and forces
    private void simulateForce() {

        force.subtract(force.getAdd(velocity.getMul(mass)).getMul(airFriction));
        
        velocity.add(force.getDiv(mass));
        force.setPos(0, 0);
    }
    
    public void move(double x, double y) {

        this.coordinates.offset(x, y);


        updateGraphic();

    }

    //Position
    public void setPos(double x, double y) {

        this.coordinates.setPos(x, y);

        updateGraphic();

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

        force.add(F);

    }

    public void setGravityDir(Coordinate dir) {
        gravityDir = dir;
    }
    public void setGravity(double magnitude) {
        gravity = magnitude;
    }
    public void setGravity(Coordinate gravForce) {
        gravityDir = Coordinate.normalized(gravForce);
        gravity = Coordinate.length(gravForce);
    }
    public void enableGravity(boolean enabled) {
        gravityFlag = enabled;
    }

}
