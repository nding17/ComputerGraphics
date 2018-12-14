/* Swing library */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

public class Cylinder{
   // prevent going over coordinates
   public static final double EPSILON = 0.0005; 
   // default image is where texture image comes from
   // buffered image is where we draw the actual texture
   BufferedImage defaultImg, bufferedImg;
   
   public Cylinder(BufferedImage bi, String tfn, Dimension dim){
      // set up default image based on the dimension of the source image 
      defaultImg = new BufferedImage((int)dim.getWidth(), (int)dim.getHeight(), 
                     BufferedImage.TYPE_INT_RGB);
      bufferedImg = bi;
      
      /* try to read in the texture image file */
      try{
         defaultImg = ImageIO.read(new File(tfn));
      }catch(IOException e){
         e.printStackTrace();
      }
      
   }
   
   // translate (300,50)
   // translate x 
   public int Tx(int x){
      return x + 300;
   }
   
   // translate (300,50)
   // translate y 
   public int Ty(int y){
      return y + 50;
   }
   
   // main function for mapping the texture of 
   // the cylinder. Given the rotation degree,
   // you will be able to rotate the cylinder
   // to see how the texture looks like. Unlike
   // cube, which rotate in 3 axis, cylinder could
   // only do y axis 
   public void mapCylinder(double phi){
   
      // radius of the circle of a cylinder is constant
      // and the width of the image is the circumstance
      // of the circle 
      double r = defaultImg.getWidth() / (Math.PI*2);
      int maxY = defaultImg.getHeight(); // height of the cylinder
      
      // integrate over hight 
      for(int y = 0; y < maxY; y++){
         // integrate over length of the arc 
         for(int x = -(int)r; x < (int)r; x++){
            // angle between x and r 
            double theta = Math.PI - Math.acos(x/r);
            // rotation is happening here 
            theta += Math.toRadians(phi);
            
            // critical step here!
            // we have to make sure that theta 
            // is not more that 2*PI. Even that
            // is not enough, we have to technically
            // make sure theta is even less than
            // 2*PI-EPSILON
            while(theta >= (Math.PI*2-EPSILON)){
               theta -= Math.PI*2;
            }
            // length of the arc, which corresponds to
            // the x coordinate of the texture image 
            double d = r*theta; 
            // set up buffered image mapping
            bufferedImg.setRGB(Tx(x), Ty(y), defaultImg.getRGB((int)d, y));
         }
      }
   
   }
   
   

}