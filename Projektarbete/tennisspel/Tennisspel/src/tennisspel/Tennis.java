

package Tennisspel;

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

public class Tennis extends JFrame {//Hela klassen Tennis sätts till lyssnare
    //Instansvariabler
private spelplan plan = new spelplan();//Ny instans av klassen spelplan
private JLabel poängl = new JLabel("0", JLabel.CENTER); //ny label poäng1,påskrift 0
private JLabel poäng2 = new JLabel("0", JLabel.CENTER); //ny label poäng2,påskrift 0
private JPanel pan = new JPanel();//ny instans av JPanel för att placera spelplan
private JButton[] b = new JButton[4];// en vektorinstans av JButton för att skapa kontrollknappar
private String[] s ={"Nytt spel","Paus","Fortsatt","Avsluta"};//en instansvektor av Datatypen String för att lagra knapptext

public Tennis() { 
    setTitle("Tennis"); //Titel på JFrame
    plan.setPreferredSize(new Dimension(350,250));//planens storlek 
    plan.setBackground(Color.white);//planens bakgrunsfärg

poängl.setFont(new Font("SansSerif", Font.BOLD, 24)); 
poäng2.setFont(new Font("SansSerif", Font.BOLD, 24)); 
pan.setLayout(new FlowLayout());//planens layout
for (int i=0; i<b.length; i++) { //Lägger ut knapparna på spelplanen
    b[i] = new JButton(); 
    b[i].setText(s[i]); 
    b[i].addActionListener(f1); 
    pan.add(b[i]);
}

Container c = getContentPane(); //container
c.add(plan, BorderLayout.CENTER); //planen läggs i mitten
c.add(poängl, BorderLayout.WEST); //poäng1label läggs till vänster
c.add(poäng2, BorderLayout.EAST); //poäng2label läggs till höger
c.add(pan, BorderLayout.SOUTH);// Knapparna läggs längst ner
pack() ;
plan.init(poängl, poäng2); // initierar spelplanen
setVisible(true);
}
ActionListener f1 = new ActionListener(){
public void actionPerformed(ActionEvent e){//Metod som kollar vilken knapp tryckt 
 plan.requestFocus();
if (e.getSource()== b[0]) 
    plan.nyttSpel();
else if (e.getSource() == b[1])
plan.stoppaSpel();		
else if (e.getSource() == b[2])
plan.startaSpel();		
else if (e.getSource() == b[4])
System.exit(0); 
}
};
public static void main(String[] arg) { //main metod som skapar instans av klassen Tennis
    Tennis s = new Tennis();
}
}
