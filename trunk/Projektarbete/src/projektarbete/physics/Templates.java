package projektarbete.physics;

import projektarbete.graphics.Camera;
import projektarbete.graphics.MyImage;
import projektarbete.graphics.VisibleObject;

public class Templates {

    public static final short TYPE_PLAYER_OBJECT = 1;
    public static final short TYPE_ELASTIC_OBSTACLE = 2;
    public static final short TYPE_INELASTIC_OBSTACLE = 3;

    public static final String PLAYER_IMAGE_FILE = "stickman.png";
    public static final String OBSTACLE_IMAGE_FILE = "empty.png";

    public static PhysicsModel getTemplate(short type) {
        return getTemplate(type, 1, 1);
    }

    public static PhysicsModel getTemplate(short type, double mass, double size) {
        PhysicsModel template = new PhysicsModel();
        template.setVisibleObject(new VisibleObject());
        template.setSize(size);
        template.setMass(mass);

        switch (type) {
            case (TYPE_PLAYER_OBJECT):
                template = playerObject(template); break;

            case (TYPE_ELASTIC_OBSTACLE):
                template = elasticObstacle(template); break;

            case (TYPE_INELASTIC_OBSTACLE):
                template = inelasticObstacle(template); break;
        }

        return template;
    }

    /*Types*/

    private static PhysicsModel playerObject(PhysicsModel model) {
        newtonianPhysics(model, 0.2, 0.5);
        image(model, PLAYER_IMAGE_FILE, 423, 423);
        return model;
    }

    private static PhysicsModel elasticObstacle(PhysicsModel model) {
        obstacle(model);
        return model;
    }

    private static PhysicsModel inelasticObstacle(PhysicsModel model) {
        obstacle(model);
        model.setRestitution(0);
        return model;
    }

    private static PhysicsModel obstacle(PhysicsModel model) {
        image(model, OBSTACLE_IMAGE_FILE, 423, 423);
        model.setAirFrictionFlag(true);
        model.setAirFriction(1);
        return model;
    }


    /*end of types*/

    private static VisibleObject getImage(String file, int width, int height) {
        VisibleObject image = new MyImage(Camera.getInstance(), file);
        image.centerAnchor();
        image.setScale(2.0/Math.max(width, height));
        return image;
    }

    private static PhysicsModel image(PhysicsModel model, String imageFile,
                                      int width, int height) {
        VisibleObject image = getImage(imageFile, width, height);
        image.setScale(image.getScale().mul(model.getSize()));
        model.setVisibleObject(image);
        return model;
    }

    private static PhysicsModel newtonianPhysics(PhysicsModel model,
                                                 double airFriction,
                                                 double restitution) {

        model.setAirFriction(airFriction);
        model.setAirFrictionFlag(true);
        model.setGravityFlag(true);
        model.setRestitution(restitution);
        return model;
    }
}
