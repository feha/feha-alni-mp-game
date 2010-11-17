/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete;
import java.awt.geom.*;
/**
 *
 * @author niclas.alexandersso
 */
public class Hitbox {
private Point2D.Double position;
private double scale;
private double angle;
private Path2D.Double shape;

public Hitbox(){
    position = new Point2D.Double(0,0);
    scale = 1;
    angle = 0;
}

public Hitbox(Point2D.Double pos, double s, double a){
    position = pos;
    scale = s;
    angle = a;
}

public Hitbox(double x, double y, double s, double a){
    position = new Point2D.Double(x, y);
    scale = s;
    angle = a;
}

public Hitbox(Point2D.Double pos, double s){
    position = pos;
    scale = s;
    angle = 0;
}

public Hitbox(double x, double y, double s){
    position = new Point2D.Double(x, y);
    scale = s;
    angle = 0;
}

public Hitbox(Point2D.Double pos){
    position = pos;
    scale = 1;
    angle = 0;
}

public Hitbox(double x, double y){
    position = new Point2D.Double(x, y);
    scale = 1;
    angle = 0;
}

public void setPos(Point2D.Double pos) {
    position = pos;
}

public void setPos(double x, double y) {
    position.x = x;
    position.y = y;
}

public Point2D.Double getPos() {
    return position;
}

public Coordinate getCoordinate() {
    return new Coordinate(position.x, position.y);
}

public void setScale(double s) {
    scale = s;
}
public double getScale() {
    return scale;
}

public void setAngle(double a) {
    angle = a;
}
public double getAngle() {
    return angle;
}

public Rectangle2D getBounds() {
    //TODO: add code for finding out the bounding box
    //may need to be handled by subclasses
    return shape.getBounds2D();
}

public void setShape(PathIterator pi, boolean connect) {
    shape.reset();
    shape.append(pi, connect);
}

public void setShape(Path2D.Double s) {
    shape=s;
}

public AffineTransform getTransformation() {
    AffineTransform transformation = AffineTransform.getRotateInstance(angle);
    transformation.concatenate(AffineTransform.getScaleInstance(scale, scale));
    return transformation;
}

public void checkCollision() {
    
}

}
