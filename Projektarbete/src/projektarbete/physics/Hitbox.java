/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.physics;
import java.awt.geom.*;
//import java.awt.geom.Rectangle2D.Double;
import java.util.*;
import projektarbete.Coordinate;
/**
 *
 * @author niclas.alexandersso
 */
public class Hitbox {
    private List<Point2D.Double> shape;
    private Point2D.Double position;
    private double scale;
    private double angle;
    private double size;

    public Hitbox() {
        this(new Coordinate(0, 0));
    }

    public Hitbox(Coordinate position) {
        shape = new ArrayList<Point2D.Double>();
        this.position = position.toPoint2D();
        scale = 1;
        updateShapeSize();
    }

    public Hitbox(Hitbox hitbox) {
        shape = cloneShape(hitbox.getShape());
        position = hitbox.getPos();
        position = new Point2D.Double(position.x, position.y);
        scale = hitbox.getScale();
        angle = hitbox.getAngle();
        size = hitbox.getSize();
    }

    public void set(Hitbox hitbox) {
        shape = cloneShape(hitbox.getShape());
        position = hitbox.getPos();
        position = new Point2D.Double(position.x, position.y);
        scale = hitbox.getScale();
        angle = hitbox.getAngle();
        size = hitbox.getSize();
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Point2D.Double getPos() {
        return position;
    }

    public void setPos(Point2D.Double position) {
        this.position = position;
    }

    public List<Point2D.Double> getShape() {
        return shape;
    }

    public void setShape(List<Point2D.Double> shape) {
        this.shape = new ArrayList(shape);
        updateShapeSize();
    }

    public void normalizeShape() {
        updateShapeSize();
        for (Point2D.Double point : shape) {
            point.setLocation(GeomUtils.pointDiv(point, size));
        }
        updateShapeSize();
    }

    public boolean shapeIsNormalized() {
        updateShapeSize();
        return size == 1;
    }

    private void updateShapeSize() {
        double magnitude = 0;
        for (Point2D.Double point : shape) {
            magnitude = Math.max(magnitude, GeomUtils.vectorMagnitude(point));
        }
        size = magnitude;
    }

    public double getSize() {
        return size;
    }

    public Point2D.Double getPointLocation(Point2D.Double point) {
        Point2D.Double newPoint = new Point2D.Double();
        newPoint.setLocation(GeomUtils.pointAdd(GeomUtils.pointRotate(
                GeomUtils.pointMul(point, scale), angle), position));
        return newPoint;
    }
    
    public Point2D.Double getPointLocation(int index) {
        return getPointLocation(shape.get(index));
    }

    public List<Point2D.Double> getHitbox() {
        List<Point2D.Double> hitbox = new LinkedList<Point2D.Double>();
        for (Point2D.Double point : shape) {
            hitbox.add(getPointLocation(point));
        }
        return hitbox;
    }

    public final List<Point2D.Double> cloneShape(List<Point2D.Double> shape) {
        List<Point2D.Double> clone = new ArrayList();
        for (Point2D.Double point : shape) {
            clone.add(new Point2D.Double(point.x, point.y));
        }
        return clone;
    }

/*    public List<Integer> indicesByDistance() {
        HashMap<Integer, Double> map = new HashMap<Integer, Double>();
        for (int i = 0; i < shape.size(); i++) {

            map.put(i, shape.get(i).distance(0, 0));
        }
    }*/
    
/*private Point2D.Double position;
private double scale;
private double angle;
private Path2D.Double shape;

public Hitbox(){
    position = new Point2D.Double(0,0);
    scale = 1;
    angle = 0;
    TriangulatedPolygon p = new TriangulatedPolygon(new Point2D.Double(2, 2),
            new Point2D.Double(2, 0),new Point2D.Double(0, 0));
    p.addPoint(new Point2D.Double(0, 2));
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

class TriangulatedPolygon {

    private LinkedList<Point2D.Double> points;
    private LinkedList<PolygonTriangle> triangles;

    public TriangulatedPolygon() {
        points = new LinkedList<Point2D.Double>();
        triangles = new LinkedList<PolygonTriangle>();
    }
    public TriangulatedPolygon(Point2D.Double p1, Point2D.Double p2,
                               Point2D.Double p3) {

        points = new LinkedList<Point2D.Double>();
        triangles = new LinkedList<PolygonTriangle>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        triangles.add(new PolygonTriangle(p1, p2, p3));
    }

    public void addPoint(Point2D.Double point) {
        points.add(point);
        triangulate();
    }
    public void addPoint(int index, Point2D.Double point) {
        points.add(index,point);
        triangulate();
    }
    public void removePoint(int index) {
        points.remove(index);
        triangulate();
    }
    public void removePoint(Point2D.Double point) {
        points.remove(point);
        triangulate();
    }
    public Point2D.Double getPoint(int index) {
        return getPoint(index, points);
    }
    public static Point2D.Double getPoint(int index, 
                                          LinkedList<Point2D.Double> p) {

        int highestIndex = p.size();
        if (index > highestIndex-1) {
            index -= Math.floor((double)index/(double)highestIndex)
                    *highestIndex;
        }
        if (index < 0) {
            index -= Math.floor((double)index/(double)highestIndex+1)
                    *highestIndex-highestIndex;
        }
        return p.get(index);
    }
    public PolygonTriangle getTriangle(int index) {
        return getTriangle(index, points);
    }
    public Line2D.Double getLine(int index) {
        return getLine(index, points);
    }
    public Line2D.Double getLine(int index1, int index2) {
        return getLine(index1, index2, points);
    }
    public Rectangle2D.Double getBounds(){
        return getBounds(points);
    }
    public int getLineIntersections(Line2D.Double l){
        return getLineIntersections(l, points);
    }
    public void triangulate() {
        triangles = getTriangulation(points);
    }

    public static LinkedList<PolygonTriangle> getTriangulation(LinkedList<Point2D.Double> p) {

        LinkedList<Point2D.Double> remainingPoints =
                (LinkedList<Point2D.Double>)p.clone();
        PolygonTriangle current = new PolygonTriangle();
        LinkedList<PolygonTriangle> t = new LinkedList<PolygonTriangle>();

        //triangulation is handled with the ear-clipping method
        //if there is more than 3 points, there are at least two remaining ears
        while (remainingPoints.size()>3) {

            //loops through the list of points
            for (int i = 0; i < remainingPoints.size(); i++) {

                //sets points in the the test triangle to the current point and
                //the points directly connected to it
                current=getTriangle(i, remainingPoints);

                //checks if this triangle is inside the polygon
                if (inside(current.getCentroid(), remainingPoints)) {

                    //makes sure there are no points inside the triangle
                    if (getLineIntersections(current.getSideA(),
                            remainingPoints)==0) {

                        //adds the current triangle to the list of triangles
                        t.add(current);
                        //removes the current point from the list of points
                        remainingPoints.remove(i);
                        //breaks the loop to start over from the beginning
                        break;
                    }
                }
            }
        }
        //if there is exactly one triangle remaining
        if (remainingPoints.size()==3) {
            //the triangle is added to the list of triangles
            t.add(getTriangle(0, remainingPoints));
        }
        return t;
    }
    public static PolygonTriangle getTriangle(int index,
                                              LinkedList<Point2D.Double> p){

        //returns a triangle formed by the point at the specified index in the
        //list, and the points right before and right after it
        return new PolygonTriangle(getPoint(index, p),
                getPoint(index+1, p), getPoint(index-1, p));
    }
    public static Line2D.Double getLine(int index, 
                                        LinkedList<Point2D.Double> p) {

        //returns a line between the point at the specified index in the list
        //and the point right after it
        return new Line2D.Double(getPoint(index, p), getPoint(index+1, p));
    }
    public static Line2D.Double getLine(int index1, int index2, 
                                        LinkedList<Point2D.Double> p) {

        //returns a line between the points at the specified indexes in the list
        return new Line2D.Double(getPoint(index1, p), getPoint(index2, p));
    }
    public static Rectangle2D.Double getBounds(LinkedList<Point2D.Double> p) {

        //creates a bounding box by combining the bounding boxes of
        Rectangle2D.Double bounds = (Double) getLine(0, p).getBounds2D();
        int size = p.size();
        for (int i = 1; i < size; i++) {
            bounds.createUnion(getLine(i, p).getBounds2D());
        }
        return bounds;
    }
    public static int getLineIntersections(Line2D.Double l,
                                            LinkedList<Point2D.Double> p) {
        
        int intersections = 0;
        int size = p.size();
        Line2D.Double currentLine;

        //loops through the list to get lines to check intersection with
        for (int i = 0; i < size; i++) {

            currentLine = getLine(i, p);

            //makes sure the lines do not share any endpoints
            //and that an intersection isn't counted twice due
            //to passing through a point
            if (!currentLine.getP2().equals(l.getP1()) &&
                    !currentLine.getP2().equals(l.getP2()) && 
                    !(l.ptSegDist(currentLine.getP1())==0)) {

                //if the lines intersect,
                //the number of intersections is increased by one
                if (currentLine.intersectsLine(l)) {
                    intersections++;
                }

            }
        }
        return intersections;
    }
    public static boolean inside(Point2D.Double point,
                                 LinkedList<Point2D.Double> p){

        //draws a line between the given point and a point right outside
        //the bounding box of this polygon
        Rectangle2D.Double bounds = getBounds(p);
        Line2D.Double intersectionChecker = new Line2D.Double(point,
                new Point2D.Double(bounds.getMinX()-1, bounds.getMaxY()+1));

        //for simple polygons, if the number of intersections is not even, the
        //point is inside the polygon
        if (getLineIntersections(intersectionChecker, p)%2!=0) {
            return true;
        }
        return false;
    }
}

class PolygonTriangle {
    Point2D.Double A;
    Point2D.Double B;
    Point2D.Double C;

    public PolygonTriangle() {
        A = new Point2D.Double(0, 0);
        B = new Point2D.Double(0, 0);
        C = new Point2D.Double(0, 0);
    }

    public PolygonTriangle(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
        A=a;
        B=b;
        C=c;
    }

    //sides
    public Line2D.Double getSideA() {
        return new Line2D.Double(B, C);
    }
    public Line2D.Double getSideB() {
        return new Line2D.Double(C, A);
    }
    public Line2D.Double getSideC() {
        return new Line2D.Double(A, B);
    }
    //other
    public Point2D.Double getCentroid() {
        return new Point2D.Double((A.x+B.x+C.x)/3, (A.y+B.y+C.y)/3);
    }
    public Rectangle2D.Double getBounds() {
        return (Double)getSideA().getBounds2D()
                .createUnion(getSideB().getBounds2D())
                .createUnion(getSideC().getBounds2D());
    }*/
}