/* ControlPanelEast is the panel that appears on the east
 * side of the box border, this is the main controller class
 * which gives user the ability to do transformations methods
 * such as translation, scale, rotation and shear
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

public class ControlPanelEast extends JPanel implements ActionListener {
   
   FacePanel fPanel;
   JButton[] translateButtons;
   // string label for all the translation methods
   String[] translateText = {"UP", "DOWN", "LEFT", "RIGHT"};
   
   // string label for all the scale methods
   JButton[] scaleButtons;
   String[] scaleText = {"+", "-"};
   
   // string label for the shear method 
   JButton[] shearButtons;
   String[] shearText = {"SHEAR"};
   
   // string label for the rotation methods
   JButton[] rotateButtons;
   String[] rotateText = {"CW", "CCW"};
   
   // the current coordinates of the affine transform
   // this is crucial variable to keep track of where
   // the face is. Also, it will be updated as any of
   // the translation methods are invoked, since those
   // are the only methods that will chanege the coordinates
   // of the face 
   double[] currX = new double[7]; double[] currY = new double[7];
   
   public ControlPanelEast(FacePanel fp){
   
      // initialize the current coordinates of the face
      // to the origin
            
      currX = new double[]{0.0, -50.0+200/20, 45+200/20, 5+200.0/30.0, -5+200.0/12, -14+200.0/8, -85+200.0/16};
      currY = new double[]{0.0, 0+200/20, 0+200/20, -35+200.0/16, -65+200.0/20, -90+200.0/24, -20+200.0/10};
        
      fPanel = fp;
      setLayout(new GridLayout(4,1));
      
      // translation panel
      JPanel movePanel = new JPanel();
      movePanel.setLayout(new GridLayout(3,2));
      
      translateButtons = new JButton[4];
      
      movePanel.add(new JLabel("translation:"));
      movePanel.add(new JLabel());
      
      // set up all the translation buttons: up, down, left and right
      for(int i = 0; i < translateText.length; i++){
         translateButtons[i] = new JButton(translateText[i]);
         translateButtons[i].addActionListener(this);
         movePanel.add(translateButtons[i]);
      }
          
      // scale panel
      JPanel scalePanel = new JPanel();
      scalePanel.setLayout(new GridLayout(3,2));
      
      scaleButtons = new JButton[2];
      
      scalePanel.add(new JLabel("scale:"));
      scalePanel.add(new JLabel());
      
      // set up all the scale buttons: enlarge and shrink
      for(int j = 0; j < scaleText.length; j++){
         scaleButtons[j] = new JButton(scaleText[j]);
         scaleButtons[j].addActionListener(this);
         scalePanel.add(scaleButtons[j]);
      }
          
      // shear panel
      JPanel shearPanel = new JPanel();
      shearPanel.setLayout(new GridLayout(3,2));
      
      shearButtons = new JButton[1];
      
      // set up the shear button
      for(int i = 0; i < shearText.length; i++){
         shearButtons[i] = new JButton(shearText[i]);
         shearButtons[i].addActionListener(this);
         shearPanel.add(shearButtons[i]);
      }

      // rotate panel
      JPanel rotatePanel = new JPanel();
      rotatePanel.setLayout(new GridLayout(3,2));
      
      rotateButtons = new JButton[2];
      
      rotatePanel.add(new JLabel("rotate:"));
      rotatePanel.add(new JLabel());
      
      // set up the rotation buttons
      // we can either rotate clockwise or counterclockwise 
      for(int j = 0; j < rotateText.length; j++){
         rotateButtons[j] = new JButton(rotateText[j]);
         rotateButtons[j].addActionListener(this);
         rotatePanel.add(rotateButtons[j]);
      }
          
          
      // add all the panels to the controller panel    
      add(movePanel);
      add(scalePanel);
      add(rotatePanel);
      add(shearPanel);
      
   }
   
   // this is the main method that user uses to move the face
   // it invokes getTranslateInstance to create a matrix that
   // will transform the original coordinates to the destination
   // coordinates. In the mean time, the current coordinates of 
   // the face will be updated for later use.
   public void faceTranslate(ActionEvent ev){
   
      AffineTransform tx; // the transform matrix
      double increments = 20.0; // the increment of each translation
      
      int currPart = 0;
      
      for(int i = 0; i < currX.length; i++){
         
         if(fPanel.faceTransIndex[i] == 1){
             currPart = i;
         }
      
      }
      
      // going up
      if(ev.getSource() == translateButtons[0]){
         currY[currPart] += increments; // update the current Y coordinate
         tx = AffineTransform.getTranslateInstance(0.0, increments);
         fPanel.ax = tx; // update the affine transform matrix
         fPanel.renderFace(); // repaint
      }
      
      // going down
      if(ev.getSource() == translateButtons[1]){
         currY[currPart] -= increments; // update the current Y coordinate
         tx = AffineTransform.getTranslateInstance(0.0, -increments);
         fPanel.ax = tx; // update the affine transform matrix
         fPanel.renderFace(); // repaint
      }
      
      // going left
      if(ev.getSource() == translateButtons[2]){
         currX[currPart] -= increments; // update current X coordinate
         tx = AffineTransform.getTranslateInstance(-increments, 0.0);
         fPanel.ax = tx;
         fPanel.renderFace();
      }
      
      // going right
      if(ev.getSource() == translateButtons[3]){
         currX[currPart] += increments; // update current X coordinate
         tx = AffineTransform.getTranslateInstance(increments, 0.0);
         fPanel.ax = tx;
         fPanel.renderFace();
      }
      
      
   }
   
   // this is a critical step in the transformation as it could 
   // keep the face in the correct position around the proper place
   // if not, for example, if you rotate, the face will not be
   // rotating around its center, instead, some previous coordinates
   // it once stays
   public AffineTransform multiPreConcatenate(AffineTransform tx){
   
      // destTx is the matrix where you want to send the face to 
      // originTx is the matrix where you want your face to be 
      // rotating around. aka the origin of the grid 
      AffineTransform destTx = new AffineTransform(); 
      AffineTransform originTx = new AffineTransform();
      
      // since we know where the face is, it is easy to translate it
      // to any position
      
      int currPart = 0;
      for(int i = 0; i < currX.length; i++){
        
        if(fPanel.faceTransIndex[i] == 1){
         currPart = i;
        }
      
      }    
      
      destTx = AffineTransform.getTranslateInstance(currX[currPart], currY[currPart]);
      originTx = AffineTransform.getTranslateInstance(-currX[currPart], -currY[currPart]);
      
      // so translate the shape back to the origin of the grid
      AffineTransform newTx = new AffineTransform(originTx);
      // do the given transform operation
      newTx.preConcatenate(tx);
      // translate the shape to where it should be 
      newTx.preConcatenate(destTx);
      
      return newTx;
   }
   
   
   // this is the method that you either enlarge or shrink
   // the face. this will invoke multiPreConcatenate to keep
   // the coordinates consistantly
   public void faceScale(ActionEvent ev){
      
      AffineTransform tx;
      
      // to enlarge the face
      if(ev.getSource() == scaleButtons[0]){
         
         // each time the face is enlarged by 20%
         tx = AffineTransform.getScaleInstance(1.2, 1.2);
         tx = multiPreConcatenate(tx); // keep the coordinates consistant
         // send the whole matrix to the affine transform matrix
         fPanel.ax = tx;
         fPanel.renderFace();
      }
      
      // to shrink the face 
      if(ev.getSource() == scaleButtons[1]){
         // each time the face is shrunk by 20%
         tx = AffineTransform.getScaleInstance(0.8, 0.8);
         tx = multiPreConcatenate(tx); // same business
         fPanel.ax = tx;
         fPanel.renderFace();
      }
   }
   
   // this is the method that you shear the face 
   public void faceShear(ActionEvent ev){
   
      AffineTransform tx; // the actual transformation
      
      // if the shear button is being clicked
      if(ev.getSource() == shearButtons[0]){
         // we shear both x and y coordinates by 20%
         // then after the shearing, the orientation
         // of the face would be pointing to the top
         // right corner 
         tx = AffineTransform.getShearInstance(0.2, 0.2);
         tx = multiPreConcatenate(tx);
         fPanel.ax = tx;
         fPanel.renderFace();
      }
                  
   }
   
   // this is the main method that you rotate the face 
   // the main is the same as previous methods such as
   // scaling and shearing. basically, we bring the shape
   // back to the origin and rotate it. after the rotation
   // is done, we bring the shape back to its correct coordinates
   public void faceRotate(ActionEvent ev){
   
      AffineTransform tx;
      
      // if the rotate clockwise button is clicked 
      if(ev.getSource() == rotateButtons[0]){
         // change from degrees to radians 
         tx = AffineTransform.getRotateInstance(Math.toRadians(-5));
         tx = multiPreConcatenate(tx);
         fPanel.ax = tx;
         fPanel.renderFace();
         
      }
      
      // if the rotate counterclockwise button is clicked 
      if(ev.getSource() == rotateButtons[1]){
         tx = AffineTransform.getRotateInstance(Math.toRadians(5));
         tx = multiPreConcatenate(tx);
         fPanel.ax = tx;
         fPanel.renderFace();

      }
   }
   
   
   // we have built all the transformation methods
   // we just need to integrate all these methods 
   // into the actionPerformed since all of them
   // requires button components. Everything is 
   // set up at this point
   public void actionPerformed(ActionEvent ev){
      faceTranslate(ev);
      faceScale(ev);
      faceShear(ev);
      faceRotate(ev);
   }
   
 }