/* FacePanel class consists of all the face components like head, eye, ear ect. 
 * it stores the cumulative affine transformation matrix, which will be updated
 * each time an Action event is called 
 */

/* Swing library */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.Math;
import java.util.*;

public class FacePanel extends JPanel{
   
   Shape[] faceComps = new Shape[7]; // all the face parts 
   int[] faceIndex = new int[7]; // index to tell if the parts should be drawn
   int[] faceTransIndex = new int[7];
   int[] colorIndex = new int[7]; // index to tell if the color should be filled 
    
   AffineTransform ax; // the overall affine transform matrix, which is cumulative
   double h,w; // the height and width of the canvas 
   
   // check if there is click on any of the face parts.
   // prevent accidental clicks on the transformations
   // methods 
   int noClickOnFaceParts;  
   
   // all the colors that are to be filled in individual 
   // face part and they are fixed
   Color[] colors = {new Color(255,192,203), new Color(0,255,0), 
      new Color(0,191,255), new Color(255,228,181), new Color(255,0,0), 
      new Color(255,105,180), new Color(169,169,169)};
   
   // face panel constructor 
   public FacePanel(){
      setBackground(Color.white);
      setOpaque(true);
      setDoubleBuffered(true);
      // the size of the head
      h = 200.0;
      w = 200.0;
      
      // head
      faceComps[0] = getHead();
      // left eye      
      faceComps[1] = getLeftEye();
      // right eye
      faceComps[2] = getRightEye();
      // nose
      faceComps[3] = getNose();
      // mouse
      faceComps[4] = getMouth();
      // chin
      faceComps[5] = getChin();
      // ear
      faceComps[6] = getEar();

            
      // initialize all the face indices and color indices to zero, just so
      // the user could choose the face parts as well as their fill color as 
      // they like
      for(int i = 0; i < faceIndex.length; i++){
         faceIndex[i] = 0;
         colorIndex[i] = 0;
         faceTransIndex[i] = 0;
      }
      
      // initialize the cumulative affine transformation matrix to an identity 
      // matrix 
      ax = new AffineTransform();
      // keep track if any of the parts have been clicked to avoid any accidental
      // transformations
      noClickOnFaceParts = 1; 
   }
   
   // return the shape of a head
   public Shape getHead(){
      return new Ellipse2D.Double(-100.0, -100.0, h, w);
   }
   
   // return the shape the left eye
   public Shape getLeftEye(){
      return new Ellipse2D.Double(-50, 0, h/10, w/10);   
   }
   
   // return the shape of the right eye
   public Shape getRightEye(){   
      return new Ellipse2D.Double(45, 0, h/10, w/10);   
   }
   
   // return the shape of the mouth
   public Shape getMouth(){
      return new Arc2D.Double(-5, -65, w/6, h/10, 180, -180, Arc2D.PIE);
   }
   
   // return the shape of the nose 
   public Shape getNose(){
      return new Rectangle2D.Double(0+5,0-35,w/15, h/8);
   }
   
   // return the shape of the chin 
   public Shape getChin(){
       return new Arc2D.Double(-14, -90, w/4, h/12, 180, -180, Arc2D.OPEN);
   }
   
   // return the shape of the ear 
   public Shape getEar(){
       return new Arc2D.Double(-85, -20, w/8, h/5, 90, 180, Arc2D.PIE);
   }
   
   // render the face painting
   public void renderFace(){
      repaint();
   }
   
   public void paintComponent(Graphics g){
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(Color.white);
      g2d.fillRect(0,0,getWidth(),getHeight());
      g2d.setColor(Color.black);
      
      // set the origin of the grid in the center
      // which is (0, 0). This point is fixed, it 
      // brings the convenience when we do affine
      // transform on any given objects 
      g2d.translate(getWidth()/2, getHeight()/2);
      g2d.scale(1,-1);
      
      
      // check if any of the face parts buttons 
      // have been clicked yet
      for(int i = 0; i < faceComps.length; i++){
         if(faceIndex[i] == 1){
            noClickOnFaceParts = 0;
         }
      }
      
      for(int i = 0; i < faceComps.length; i++){
         
         // if the user has clicked any of the face buttons
         // this means that the transformations would be valid 
         // otherwise ax will be an identity matrix which will
         // let nothing happen
         if(noClickOnFaceParts == 0 && faceTransIndex[i] == 1){
            // create a new shape based on the affine transformation
            // matrix
            faceComps[i] = ax.createTransformedShape(faceComps[i]);
         }
         
         // both the face parts and the color buttons are clicked
         // then we can fill in color to the face components the 
         // user intends to fill in color 
         if(faceIndex[i] == 1 && colorIndex[i] == 1){
            g2d.setColor(colors[i]);
            g2d.fill(faceComps[i]);
         }
         
         // if the user does not click any color buttons, then just
         // draw the outline of a face part, otherwise, the whole
         // will be filed with given color
         if(faceIndex[i] == 1 && colorIndex[i] == 0){
            g2d.setColor(Color.black);
            g2d.draw(faceComps[i]);
         }
         
      }
      
   }

}