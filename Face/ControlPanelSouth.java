/* ControPanelSouth is the class of the control
 * panel that allows you to select face parts and 
 * fill in colors
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.geom.*;
import java.lang.Math;

public class ControlPanelSouth extends JPanel implements ActionListener {

   FacePanel fPanel;
   JButton[] faceCompButtons;
   // string labels for all the face components
   String[] faceLabel = {"head", "left eye", "right eye", "nose", "mouth", "chin","ear"};
   
   JButton[] faceColorButtons;
   // string labels for all the colors, btw, to keep simplicity, the choice of colors
   // is fixed unfortunately
   String[] colorLabel = {"pink", "green", "blue", "yellow", "red", "hot pink", "dark gray"};
   
   public ControlPanelSouth(FacePanel fp){
      
      fPanel = fp;
      
      setLayout(new GridLayout(4,1));
      
      add(new JLabel(" FACE PARTS:"));
      
      // set up the face parts buttons
      faceCompButtons = new JButton[7];
      JPanel facePartsPanel = new JPanel();
      facePartsPanel.setLayout(new GridLayout(1,7));
      
      // set up the face color buttons
      faceColorButtons = new JButton[7];
      JPanel faceColorPanel = new JPanel();
      faceColorPanel.setLayout(new GridLayout(1,7));
      
      // now we just use the loop to create all the buttons
      // 7 face part buttons and 7 color buttons. 
      for(int i = 0; i < faceLabel.length; i++){
      
         faceCompButtons[i] = new JButton(faceLabel[i]);
         faceCompButtons[i].addActionListener(this);
         facePartsPanel.add(faceCompButtons[i]);
         
         faceColorButtons[i] = new JButton(colorLabel[i]);
         faceColorButtons[i].addActionListener(this);
         faceColorPanel.add(faceColorButtons[i]);
      }
      
      add(facePartsPanel); // add in face part panel
      
      add(new JLabel(" FILL COLOR:"));
      add(faceColorPanel); // add in face color panel
   }
   
   
   // what we want is that, when the user clicks on any of 
   // the face components, then those corresponding face parts
   // should be appearing on the screen. the tricky thing is 
   // what if the user chooses some parts of the face and he 
   // does some transformation, but later on he decides to add in 
   // other parts that he did not click previously. we want to
   // make the other parts of the face to be transformed as well
   // even though they did not appear on the panel. later on,
   // if the user clicks on that part, it should appear as being
   // transformed already. In order to do this, we have to make
   // a switch to let the program decide which parts to be drawn
   // however, all of the parts should be transformed already.
   public void actionPerformed(ActionEvent ev){
   
     for(int j = 0; j < faceLabel.length; j++){
         fPanel.faceTransIndex[j] = 0;
     }

     
     for(int i = 0; i < faceLabel.length; i++){
         if(ev.getSource() == faceCompButtons[i]){
            fPanel.faceIndex[i] = 1; // turn on the switch for face components
            fPanel.faceTransIndex[i] = 1;
         }
         
         if(ev.getSource() == faceColorButtons[i]){
            fPanel.colorIndex[i] = 1; // turn on the switch for face colors
         }
     }
     
     // this is important since the we already have the affine transform
     // and the matrix is stored in the FacePanel object
     // the identity matrix makes sure that any presses on the face parts 
     // buttons will not invoke unexpected transformations.
     fPanel.ax = new AffineTransform();
     fPanel.renderFace();
   }


}