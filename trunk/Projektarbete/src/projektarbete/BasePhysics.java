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
    protected double gravity;
    protected boolean gravityFlag;
    protected boolean airFrictionFlag;
    protected boolean frozenFlag;
    double deltaTime;

    VisibleObject visibleObject;
    

    private void initComponents() {

        PhysicsEngine.getInstance().addBasePhysics(this);

        //Initializing variables

        coordinates = new Coordinate(0,0);
        velocity = new Coordinate(0,0);
        force = new Coordinate(0,0);



        mass = 100;
        airFriction = 0.10;
        gravity = 9.82;
        gravityFlag = true;
        airFrictionFlag = true;
        frozenFlag = false;
        
    }

    private void initTesting() {

        visibleObject = new Hexagon(Camera.getInstance());
        visibleObject.offset(250, 0);
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
            visibleObject.setPos(coordinates);
        }

    }

    public void physicsSimulate(double deltaTime) {
        
        if (!frozenFlag) {
            if (gravityFlag) {
                force.offset(0, -gravity*mass);
                //verticalAcceleration-= gravity * deltaTime;
            }
            System.out.println("Position: "+velocity.y()+" Velocity: "+velocity.y());
            //Velocity and accerelation
            /*horizontalAcceleration-= (airFriction * horizontalVelocity);
            verticalAcceleration-= (airFriction * verticalVelocity);*/


            //Position
            coordinates.offset(velocity.x() * deltaTime, -velocity.y() * deltaTime);
            
        }

        updateGraphic();

    }
    
    public void move(double x, double y) {

        this.coordinates.offset(x, y);


        updateGraphic();

    }

    //Position
    public void setPos(double x, double y) {

        this.coordinates.set(x, y);

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

        velocity.set(vel.x(), vel.y());

    }

    public Coordinate getVel() {

        return velocity;

    }

    //Accerelation and force.
    public void applyForce(double hF, double vF) {
        applyForce(new Coordinate(hF,vF));

    }
    public void applyForce(Coordinate F) {

        velocity.offset(F.x()  *  deltaTime  / mass,
            F.y()  * deltaTime  / mass);

    }


}
