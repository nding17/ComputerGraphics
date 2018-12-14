import java.awt.*;
import javax.swing.*;


// GUI launcher
public class WrapperGUI{

   public static void main(String[] args){
		
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Wrapper");
		myFrame.setSize(600,600);

		//Sets the window to close when upper right corner clicked.  
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      WrapperPanel wPanel = new WrapperPanel();
      ControlPanel controller = new ControlPanel(wPanel);
      
      Container cp = myFrame.getContentPane();
     
      // set up a border size just so canvas and controller 
      // will not touch each other
	   cp.setLayout(new BorderLayout(10, 10));
     
      // set the size of the panel to match up swith the canvas
      wPanel.setPreferredSize(new Dimension(600, 600));
      controller.setPreferredSize(new Dimension(300,600));
      
      cp.add(wPanel, BorderLayout.CENTER); // put the canvas in the center
      cp.add(controller, BorderLayout.EAST);
      
		//Must use getContentPane() with JFrame.
		myFrame.add(wPanel);
		myFrame.pack(); //resizes to preferred size for components.
		myFrame.setResizable(false);
		myFrame.setVisible(true);
   }
}