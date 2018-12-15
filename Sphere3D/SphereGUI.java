import java.awt.*;
import javax.swing.*;


// GUI launcher
public class SphereGUI{

   public static void main(String[] args){
		
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Shaded Sphere");
		myFrame.setSize(800,800);

		//Sets the window to close when upper right corner clicked.  
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      SpherePanel sPanel = new SpherePanel();
      ControlPanel controller = new ControlPanel(sPanel);
      
      Container cp = myFrame.getContentPane();
     
     
      // set up a border size just so canvas and controller 
      // will not touch each other
	   cp.setLayout(new BorderLayout(10, 10));
     
      // set the size of the panel to match up with the canvas
      sPanel.setPreferredSize(new Dimension(600, 600));
      //controller.setPreferredSize(new Dimension(150,600));
      
      cp.add(sPanel, BorderLayout.CENTER); // put the canvas in the center
      cp.add(controller,BorderLayout.EAST); // put the controller in the east
      
		//Must use getContentPane() with JFrame.
		myFrame.add(sPanel);
		myFrame.pack(); //resizes to preferred size for components.
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		
   }
}