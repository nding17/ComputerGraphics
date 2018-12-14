/* this is the GUI class which takes control
 * of the whole program, you should compile and
 * run this */

import java.awt.*;
import javax.swing.*;

public class SpiralGUI{

  public static void main(String[] args){
    // set up the main frame for the GUI 
    // that is supposed to add both the canvas
    // as well as the controller
    JFrame frame = new JFrame();
    frame.setTitle("Spiraling Squares");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false); // make the canvas fixed
	 
    SpiralPanel sPanel = new SpiralPanel();
    ControlPanel controllerEast = new ControlPanel(sPanel);
	 Container cp = frame.getContentPane();
    
    // set up a border size just so canvas and controller 
    // will not touch each other
	 cp.setLayout(new BorderLayout(10, 10));
    
    // set the size of the panel to match up with the canvas
    sPanel.setPreferredSize(new Dimension(600, 600));
    controllerEast.setPreferredSize(new Dimension(120,600));
    
    cp.add(sPanel, BorderLayout.CENTER); // put the canvas in the center
    cp.add(controllerEast,BorderLayout.EAST); // put the controller in the east

    frame.pack();
    frame.setVisible(true);
  } //end main
  
}
  
  
 