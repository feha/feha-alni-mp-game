/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projektarbete.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import projektarbete.Coordinate;

/**
 *
 * @author felix.hallqvist
 */
public class MyImage extends VisibleObject {

    protected Image image;
    protected Coordinate[] points;


    public MyImage(VisibleObject visibleObject) {

        super(visibleObject);

        System.out.println("MyImage Initializing");

        initComponents();

        System.out.println("MyImage Initialized");

    }


    private void initComponents() {

        //TODO: Everything!!!
        int width = 100;
        int height = 100;
        image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        position = new Coordinate(0,0);

    }

    @Override
    public void setImage(String file) {
        try {
            image = ImageIO.read(new File(file));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void centerAnchor() {
        anchor = new Coordinate(image.getWidth(null)/2,image.getHeight(null)/2);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        AffineTransform affine = new AffineTransform();
        //translate must be before rotate as rotate actually rotates, not just sets the angle
        affine.translate(drawPos.x(), drawPos.y());
        //scale must be after translate or it scales the position aswell as size
        affine.scale(sizeScale.x(), sizeScale.y());
        //the anchors is the local position that the image rotates around
        affine.rotate(totalAngle, anchor.x(), anchor.y());

        //g.drawImage(image, (int) drawPos.x(), (int) drawPos.y(), null);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(image, affine, null);

    }

}
