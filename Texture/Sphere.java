/* Swing library */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

public class Sphere{
   // prevent going over coordinates
   public static final double EPSILON = 0.0005;
   // default image is where texture image comes from
   // buffered image is where we draw the actual texture
   BufferedImage defaultImg, bufferedImg;
   
   public Sphere(BufferedImage bi, String tfn, Dimension dim){
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
   
   // calulate the max x coordinate given y and the
   // radius of the sphere
   public double maxX(double rho, double y){
      return Math.sqrt(rho*rho - y*y);
   }
   
   // calulate the min x coordinate given y and the
   // radius of the sphere, which is the negative
   // of maxX
   public double minX(double rho, double y){
      return -Math.sqrt(rho*rho - y*y);
   }
   
   // translate (300,300)
   // translate x
   public int Tx(int x){
      return x + 300;
   }
   
   // translate (300,300)
   // translate y
   public int Ty(int y){
      return 300 - y;
   }
   
   // main function for mapping the texture of 
   // the sphere. Given the rotation degree,
   // you will be able to rotate the sphere
   // to see how the texture looks like. Unlike
   // cube, which rotate in 3 axis, sphere could
   // only do y axis 
   public void mapSphere(double phi){
      // radius of the circle of the entire sphere,
      // which is constant, and the width of the image
      // is the circumstance of the circle
      double rho = defaultImg.getWidth() / (Math.PI*2);
      
      // integrate over y 
      for(int y = -(int)rho; y < (int)rho; y++){
      
         double xmax = maxX(rho, y);
         double xmin = minX(rho, y);
         // integrate over x 
         for(int x = (int)xmin; x < (int)xmax; x++){
            double r = Math.sqrt(rho*rho - y*y);
            // since each circle has a diffrent radius than others
            // in a sphere. We always want to map around the midpoint
            // of the texture map. So we have to cut the part that's
            // not supposed to be ending up on the sphere
            double start = (defaultImg.getWidth() - (2*Math.PI*r))/2;
            double theta = Math.PI - Math.acos(x/r);
            
            // critical step here!
            // we have to make sure that theta 
            // is not more that 2*PI. Even that
            // is not enough, we have to technically
            // make sure theta is even less than
            // 2*PI-EPSILON
            theta += Math.toRadians(phi);
            while(theta >= (Math.PI*2-EPSILON)){
               theta -= Math.PI*2;
            }
            // length of the arc, which corresponds to
            // the x coordinate of the texture image
            double d = start+r*theta;
            // set up buffered image mapping
            bufferedImg.setRGB(Tx(x), Ty(y), defaultImg.getRGB((int)d,(int)(rho-y)));
         }// end x loop
      }// end y loop
      
   }// end mapSphere
   
}