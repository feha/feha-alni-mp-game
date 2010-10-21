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

    protected double x;
    protected double y;
    protected double[] coordinates;
    protected double horizontalVelocity;
    protected double verticalVelocity;
    protected double[] velocity;
    protected double horizontalAcceleration;
    protected double verticalAcceleration;
    protected double[] acceleration;
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
        x = 0;
        y = 0;
        coordinates = new double[] {x, y};
        horizontalVelocity = 0;
        verticalVelocity = 0;
        velocity = new double[] {horizontalVelocity, verticalVelocity};

        horizontalAcceleration = 0;
        verticalAcceleration = 0;
        acceleration = new double[] {horizontalAcceleration, verticalAcceleration};

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
            visibleObject.setPos(x, y);
        }

    }

    public void physicsSimulate(double deltaTime) {
        
        if (!frozenFlag) {
            if (gravityFlag) {
                applyForce(0, -gravity);
                //verticalAcceleration-= gravity * deltaTime;
            }
            System.out.println("Position: "+y+"Velocity: "+verticalVelocity);
            //Velocity and accerelation
            /*horizontalAcceleration-= (airFriction * horizontalVelocity);
            verticalAcceleration-= (airFriction * verticalVelocity);*/
            if (airFrictionFlag) {
                applyForce(-(airFriction*horizontalVelocity*mass), -(airFriction*verticalVelocity*mass));
            }
            System.out.println("Position: "+y+"Velocity: "+verticalVelocity);
            
            horizontalVelocity+= horizontalAcceleration;
            verticalVelocity+= verticalAcceleration;
            velocity[0] = horizontalVelocity;
            velocity[1] = verticalVelocity;
            horizontalAcceleration = 0;
            verticalAcceleration = 0;
            acceleration[0] = horizontalAcceleration;
            acceleration[1] = verticalAcceleration;

            //Position
            x+= horizontalVelocity * deltaTime;
            y+= -verticalVelocity * deltaTime;
            
            coordinates[0]+= x;
            coordinates[1]+= y;
        }

        updateGraphic();

    }
    
    public void move(double x, double y) {

        this.x+= x;
        this.y+= y;

        coordinates[0]+= this.x;
        coordinates[1]+= this.y;

        updateGraphic();

    }

    //Position
    public void setPos(double x, double y) {

        this.x = x;
        this.y = y;

        coordinates[0] = this.x;
        coordinates[1] = this.y;

        updateGraphic();

    }

    public double[] getPos() {

        return coordinates;

    }

    //Velocity
    public void setVel(double x, double y) {

        horizontalVelocity = x;
        verticalVelocity = y;

        velocity[0] = horizontalVelocity;
        velocity[1] = verticalVelocity;
        
    }

    public double[] getVel() {

        return velocity;

    }

    //Accerelation and force.
    public void applyForce(double horizontalForce, double verticalForce) {

        horizontalVelocity+= horizontalForce /* *  deltaTime */ / mass;
        verticalVelocity+= verticalForce /* * deltaTime */ / mass;

    }

    public void AddAcc(double x, double y) {

        horizontalAcceleration+= x;
        horizontalAcceleration+= y;

        acceleration[0] = horizontalAcceleration;
        acceleration[1] = verticalAcceleration;

    }

    public void setAcc(double x, double y) {

        horizontalAcceleration = x;
        horizontalAcceleration = y;

        acceleration[0] = horizontalAcceleration;
        acceleration[1] = verticalAcceleration;

    }

    public double[] getAcc() {

        return acceleration;

    }

}
