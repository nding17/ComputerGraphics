import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

// main method logic of the shaded sphere
public class SpherePanel extends JPanel{

   BufferedImage sphereImg; // buffered image where we draw the sphere
   int width = 600; // width of the canvas
   int height = 600; // height of the canvas 
   double Ka = 0.02; // ambient coefficient 
   
   double Imax = 220.0; // max intensity (visible)
   double Imin = 50.0; // min intensity
   double R = 100.0; // radius of the sphere
   
   double[] L; // position of the light source
   double lx, ly, lz; // individual components of the light source 
      
   boolean isRed, isGreen, isBlue; // switch for changing colors 
   boolean isSpecular; // where or not put in specular mode 

   public SpherePanel(){
      setPreferredSize(new Dimension(600,600));
		sphereImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      setBlackScene(); // set the background scene to black
      
      /* initialize all the fields */
      isRed = false;
      isGreen = false;
      isBlue = false;
      isSpecular = false;
      lx = 0.0;
      ly = 100.0; // the light source is around the shoulder 
      lz = 450.0;
   } // end SpherePanel
   
   // get the ambient light, which is a constant 
   public double getIa(){
      return Ka*Imax;
   }
   
   // get the diffuse light 
   // Id = Ks*Imax*(n*l)
   public double getId(double[] N, double[] L){
      double Range = Imax - Imin;
      double Id = Range*dot(unit(N),unit(L)) + Imin;
      return Id; 
   } // end getId
   
   // vector subtraction
   // v = a - b
   public double[] sub(double[] a, double[] b){
      double[] v = new double[3];
      v[0] = a[0] - b[0];
      v[1] = a[1] - b[1];
      v[2] = a[2] - b[2];
      return v;
   }
   
   // vector multiplication
   // w = v * a
   // scale it by a factor of a 
   public double[] multi(double[] v, double a){
      double[] w = new double[3];
      w[0] = v[0]*a;
      w[1] = v[1]*a;
      w[2] = v[2]*a;
      return w;
   }
   
   // get the specular light
   public double getIs(double[] N, double[] L){
      
      double[] n = unit(N); // normalize the normal vector to the surface
      double[] l = unit(L); // normalize the light source vector 
      
      double c = 2*(dot(n,l));
      
      double[] R = sub(multi(n, c), l); // reflection vector
      double[] r = unit(R);  // normalize reflection vector
      double[] E = {0.0,50.0,200.0}; // position of the eye/camera
      double[] V = sub(E,N); // vector from the position of the surface to the eye
      double[] v = unit(V); // normalize camera vector 
            
      double Range = (Imax - Imin)/10; // set a reasonable range of addtional light 
      double cos_s = dot(r,v); // angle between reflection vector and camera vector 
      double cos_s_p = Math.pow(cos_s, 9); // make the reflection sharp
      
      return Range*cos_s_p;
   } // end getIs
   
   // this is the main method of getting the intensity 
   // of the light. The value we get should be in the range
   // of 0 to 255. Then this value we could use to make colors
   // do not assume unit vector for both vectors
   public int getI(double[] N, double[] L){
   
      double Ia = getIa(); // get ambient light intensity
      double Id = getId(N,L); // get diffused light intensity 
      double Is = getIs(N,L); // get specular light intensity 
      
      double Idouble = Ia+Id; // only consider ambient and diffused light
      
      if(isSpecular){
         Idouble = Idouble + Is; // add in the specular light 
      }
      
      int I = (int)Math.round(Idouble); // round the double to the nearest integer
      
      // when I is greater than 255, we know it's absolute white
      // otherwise, we will encounter out of bound issue when we 
      // try to make a color
      if(I >= 255){
         I = 255;
      }
      
      // same deal, prevent out of bound issue 
      if(I <= 0){
         I = 0;
      }
      
      return I;
   } // end getI
   
   // return a unit vector 
   public double[] unit(double[] v){
      return new double[]{v[0]/len(v), v[1]/len(v), v[2]/len(v)};
   }
   
   // return the length of a vector 
   public double len(double[] v){
      return Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
   }
   
   // return v.n, the dot product
   // the result should be a scalar
   public static double dot(double[] v, double[] n){
      
      // check if the length of two vectors match 
      if(v.length != n.length){
         throw new RuntimeException("length does not match");
      }
      
      double sum = 0.0;
      
      for(int i = 0; i < v.length; i++){
         sum += v[i]*n[i];
      }
      return sum;
   } // end dot 
   
   // translate the coordinate just so the origin is
   // (300, 300)
   // since setRGB() doesn't deal with negative coordinates
   public int Tx(int a){
      return a+300; // x translation
   }
   
   // translate the coordinate just so the origin is
   // (300, 300)
   public int Ty(int b){
      return 300-b; // y translation
   }
   
   // get the min and max of X
   public double getX(double r, double y){
      return Math.sqrt(r*r - y*y);
   }
   
   // given x, y and r, try to find z
   public double getZ(double r, double x, double y){
      return Math.sqrt(r*r - x*x - y*y);
   }
   
   // render a black background, set the RGB of every pixel to black
   public void setBlackScene(){
      for ( int rc = -height/2; rc < height/2; rc++ ) {
         for ( int cc = width/2; cc < -width/2; cc++ ) {
         
            // Set the pixel colour of the image n.b. x = cc, y = rc
            sphereImg.setRGB(Tx(cc), Ty(rc), Color.black.getRGB());
         }//for cols
      }//for rows
   } // end setBlackScene
   
   // render the every pixel of the sphere, set every pixel to its
   // right color to make a 3D effect 
   public void setSphere(){
   
    // start from bottom to top 
    // increment along with every pixel 
    for(int y = (int)(-R); y < (int)(R); y++){
         
         // get the max and min x value given y 
         double xmax = getX(R, (int)y);
         double xmin = -getX(R, (int)y);
         
         // now loop through all the x in the range we calculated based on a 
         // given y.
         // round double to the closest integer
         for(int x = (int)Math.round(xmin); x < (int)Math.round(xmax); x++){
            
            // given x, y, r 
            // according to the equation of a sphere, we can find z 
            double z = getZ(R, x, y);
            // normal of the pixel
            // since the sphere sits at the origin
            double[] N = new double[]{(double)x, (double)y, z};
            // get the intensity of every single pixel 
            int I = getI(N, sub(L,N));
            Color shadeColor = getColor(I);
            // set the corresponding pixel with the right intensity
            // here we should translate x, y since setColor() doesn't
            // deal with negative coordinates 
            sphereImg.setRGB(Tx(x), Ty(y), shadeColor.getRGB());
         }
         
      }
     
   }// end setSphere
   
   
   // see which color should be drawn 
   // on the sphere
   public Color getColor(int I){
   
      // default color: gray
      Color shadeColor = new Color(I,I,I);  
       
      if(isRed){
         // red
         shadeColor = new Color(I,0,0);
      }
      
      if(isGreen){
         // green
         shadeColor = new Color(0,I,0);
      }
      
      if(isBlue){
         // blue 
         shadeColor = new Color(0,0,I);
      }
      
      return shadeColor;
   } // end getColor
   
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D)g;
      L = new double[]{lx, ly, lz};
      setSphere(); // set the pixels of the sphere
      g2d.drawImage(sphereImg, 0, 0, this);
      g2d.dispose();
   } // end paintComponent
      
}