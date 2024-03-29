/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Painter.java
 *
 * Created on 2010-sep-28, 10:31:45
 */

package projektarbete.graphics;

import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import projektarbete.Stage;

/**
 *
 * @author felix.hallqvist
 */
public class Painter extends javax.swing.JPanel { //Extend so it is a subclass of jpanel
    //this makes me able to add it to the jframe.

    /** Creates new form Painter */
    public Painter() {

        System.out.println("Painter Initializing");
        
        initComponents();
        
        System.out.println("Painter Initialized");
        
    }
    
    private Stage stage = Stage.getInstance();
    private boolean timerStarted = false;
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        public void run() {
            repaint();
        }
    };
    
    @Override //This function clears the screen and then loops trough the polygons to paint them.
    public void paintComponent(Graphics g) {

        /*if (!timerStarted) {
            timer.scheduleAtFixedRate(timerTask, 20, 20); //20 ms = 50 fps
            timerStarted = true;
        }*/

        
        if (Stage.isRunning()) {
            super.paintComponent(g);

            int count = stage.getVisibleObjectCount();

            //code to manage delays between updating the picture
            for (int i = 0; i < count; i++) {
                stage.getVisibleObject(i).draw(g);
            }
        }


    }

    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
