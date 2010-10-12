/*
 * spellyssnare.java
 *
 * Created on den 5 april 2007, 20:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Tennisspel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class spellyssnare implements ActionListener {
    public spelplan plan = new spelplan();
    public JButton[] b = new JButton[4];
    public void actionPerformed(ActionEvent e){//Metod som kollar vilken knapp tryckt 
 plan.requestFocus();
if (e.getSource()== b[0]) 
    plan.nyttSpel();
else if (e.getSource() == b[1])
plan.stoppaSpel();		
else if (e.getSource() == b[2])
plan.startaSpel();		
else if (e.getSource() == b[3])
System.exit(0);
}
    
}
