/* this is the GUI class which takes control
 * of the whole program, you should compile and
 * run this */

import java.awt.*;
import javax.swing.*;

public class FaceGUI{

   public static void main(String[] args){
     // set up the main frame for the GUI 
     // that is supposed to add both the canvas
     // as well as the controller
     JFrame frame = new JFrame();
     frame.setTitle("Face Construction");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setResizable(false); // make the canvas fixed
	    
     FacePanel fPanel = new FacePanel();
     ControlPanelSouth controllerSouth = new ControlPanelSouth(fPanel);
     ControlPanelEast controllerEast = new ControlPanelEast(fPanel);
     Container cp = frame.getContentPane();
     
     // set up a border size just so canvas and controller 
     // will not touch each other
	  cp.setLayout(new BorderLayout(10, 10));
     
     // set the size of the panel to match up with the canvas
     fPanel.setPreferredSize(new Dimension(600, 600));
     controllerEast.setPreferredSize(new Dimension(160,600));
     controllerSouth.setPreferredSize(new Dimension(600,160));
    
     cp.add(fPanel, BorderLayout.CENTER); // put the canvas in the center
     cp.add(controllerEast,BorderLayout.EAST); // put the controller in the east
     cp.add(controllerSouth, BorderLayout.SOUTH); // put the controller in the south

     frame.pack();
     frame.setVisible(true);

   }

}