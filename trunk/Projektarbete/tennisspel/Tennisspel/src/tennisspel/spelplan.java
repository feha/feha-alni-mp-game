

package Tennisspel;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

class spelplan extends JPanel implements ActionListener{
private	Timer tim = new Timer(100,this);
private	JLabel poäng1, poäng2; // för att visa poang
private	int p1, p2;	// aktuella poang
private	int xMax, yMax;	// hogsta x- och y-koordinat
private	int r, x0, y0;	// bollens radie och mittpunkt
private	int xSteg, ySteg;	// bollens steglangd
private	int v, v0 = 15;	// bollens hastighet
private	int rVä, rHö,	// rackets ovre kant
	rL, rSteg;	// rackets langd och steglangd

public void init(JLabel l1, JLabel l2){
poäng1 = l1; poäng2 =l2;  //variablerna poäng1 och poäng2 får sina initialvärden 
xMax= getSize().width-1; //högsta xkoordinaten ges värdet av Panelens bredd-1
yMax= getSize().height-1;//högsta ykoordinaten ges värdet av Panelens höjd-1
 r = yMax/20;// berakna bollens radie
 rL =3*r;// berakna rackets langd 3 ggr bollens radie
rSteg = r;	// berakna steglangd
 addKeyListener(k1); //lyssna pA tangentbordet och
 addComponentListener(c1);// andringar av planens storlek
 nollställ();
}

 
private void nollställ() {
p1 = p2 = 0; // nollstall poang 
poäng1.setText(" 0 ");
poäng2.setText(" 0 ");
xSteg = ySteg = v = v0 = 15; // utgAngshastighet
x0 = r + 1;// satt bollen i vansterkanten 
y0 = yMax/2;// i mitten av kortsidan
rVä = rHö = yMax/2-rL/2;// placera racketarna i mitten
} 

public void startaSpel() {
tim.start();
}
public void stoppaSpel() {
tim.stop();
}

public void nyttSpel() { 
    stoppaSpel(); 
    nollställ(); 
    startaSpel();
}

public void actionPerformed(ActionEvent e) {
    //Detta är den mest komplexa koden
// hit kommer man var 100:e millisekund, anropas av timern 
    if (x0-r <= 0) {	// är bollen i vansterkanten? 
        if (y0 < rVä || y0 > rVä+rL) { // miss? Bollens y-korr är mindre än nedre korrdinaten på vänsterracet eller större än övre korrdinaten på vänsterracket.
            Toolkit.getDefaultToolkit().beep();	// plinga 
            poäng2.setText(" " + String.valueOf(++p2) + " ");//Poäng uppdateras
            if (p2 == 10) 
                stoppaSpel();
            v = v0; // AtergA till utgAngshastighet
            
        }
        else	// traff
        v++;	// oka hastigheten
        xSteg = v; // flytta At h8ger nasta gAng
        
    }
else if (x0+r >= xMax){//	ar bollen i hogerkanten?
if (y0 < rHö || y0 > rHö+rL) { // miss? se ovan
    Toolkit.getDefaultToolkit().beep();	// plinga 
    poäng1.setText(" " + String.valueOf(++p1) + " ");
    if (p1 == 10) stoppaSpel();// Vi kör bara till tio
    v = v0; // dtergA till utgdngshastighet       
}
else	// träff
v++;	// oka hastigheten
xSteg = -v; // flytta At vanster nasta gAng
}
if (y0-r<=0 || y0+r>=yMax) // i över- eller underkanten? 
    ySteg = -ySteg;	// byt vertikal riktning 
    x0 += xSteg; // flytta bollen horisontellt
    y0 += ySteg; // flytta bollen vertikalt

 if (x0 < r)	// hamnade bollen for långt At vanster? 
     x0 = r;
 
else if (x0 > xMax-r) // hamnade bollen for långt At höger? 
    x0 = xMax-r+1;

if (y0 < r)	// hamnade bollen for 1ångt upp? 
    y0 = r;

else if (y0 > yMax-r) // hamnade bollen for lAngt ner? 
    y0 = yMax-r+1;

repaint();
}
public void paintComponent(Graphics g) {
super.paintComponent(g); 
g.setColor(Color.red);
g.fillOval(x0-r, y0-r, 2*r, 2*r); // rita bollen 
g.setColor(Color.black);
g.fillRect(0, rVä, 2, rL);	// rita vanster racket
g.fillRect(xMax-1, rHö, 2, rL);	// rita hoger racket

}
KeyListener k1 = new KeyAdapter(){
    
    public void keyPressed(KeyEvent e) {// någon tangent har tryckts ner 
        
        if (e.getKeyCode() == KeyEvent.VK_A) // vanster upp A-tangenten
            rVä = Math.max(0, rVä-rSteg);
        
        else if (e.getKeyCode() == KeyEvent.VK_Z) // vanster ner Z-tangenten
            rVä = Math.min(yMax-rL, rVä+rSteg); 
        
        if (e.getKeyCode() == KeyEvent.VK_UP) // höger upp pilup
            rHö = Math.max(0, rHö-rSteg);
        
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)// höger ner pilner
            rHö = Math.min(yMax-rL, rHö+rSteg);
        
    }

};


ComponentListener c1 = new ComponentAdapter(){
    public void componentResized(ComponentEvent e){
// spelplanens storlek har andrats
    
xMax = e.getComponent().getSize().width-1; 
yMax = e.getComponent().getSize().height-1; 
e.getComponent().requestFocus();
repaint();
    }
};

}
