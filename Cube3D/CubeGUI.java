import java.awt.*;
import javax.swing.*;

public class CubeGUI{

   public static void main(String[] args){
		
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Cube Renderer");
		myFrame.setSize(800,800);

		//Sets the window to close when upper right corner clicked.  
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      CubePanel cPanel = new CubePanel();
      ControlPanel controller = new ControlPanel(cPanel);
      Container cp = myFrame.getContentPane();
     
      // set up a border size just so canvas and controller 
      // will not touch each other
	   cp.setLayout(new BorderLayout(10, 10));
     
      // set the size of the panel to match up with the canvas
      cPanel.setPreferredSize(new Dimension(600, 600));
      controller.setPreferredSize(new Dimension(160,600));
      
      cp.add(cPanel, BorderLayout.CENTER); // put the canvas in the center
      cp.add(controller,BorderLayout.EAST); // put the controller in the east

		//Must use getContentPane() with JFrame.
		myFrame.add(cPanel);
		myFrame.pack(); //resizes to preferred size for components.
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		
   }


}