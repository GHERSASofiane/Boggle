
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label; 
public class AppletExemple1 extends Applet 
{  
  String msg;
 
	 public void init() {
	  msg="Bonjour de java !";
	  System.out.println("salllllll");
	 }
 
	 public void paint(Graphics g) {
	  g.drawString(msg, 20, 20);
	  g.drawString(msg, 40, 40);
	 }
}