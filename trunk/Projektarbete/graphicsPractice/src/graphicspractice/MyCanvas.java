/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphicspractice;

/**
 *
 * @author niclas.alexandersso
 */
import java.awt.*;
import javax.swing.*;
public class MyCanvas extends Canvas
{
    public MyCanvas()
    {
    }
    @Override
    public void paint(Graphics graphics)
    {
    }
    public static void main(String[] args)
    {
        MyCanvas canvas = new MyCanvas();
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
    }
}
