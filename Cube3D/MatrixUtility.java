// matrix utility class

import java.util.*;

public class MatrixUtility{

   // return x.y the result should be a scalar
   public static double dot(double[] x, double[] y){
      
      // check if the length of two vectors match 
      if(x.length != y.length){
         throw new RuntimeException("length does not match");
      }
      
      double result = 0.0;
      
      for(int i = 0; i < x.length; i++){
         result += x[i]*y[i];
      }
      return result;
   }
   
   
   // return a transformed vector after multiplying
   // a corresponding matrix 
   // v2 = m*v1
   public static double[] multiply(double[][] m, double[] v1){
      
      int nr_m = m.length; // number of rows of the matrix
      int nc_m = m[0].length; // number of columns of the matrix
      
      int len_v1 = v1.length;
      
      // check if dimension actually matches up
      // if the length of the vector is not equal to the number of 
      // rows of the matrix, then something goes wrong
      if(len_v1 != nc_m){
         throw new RuntimeException("dimensions don't match");
      }
      
      // the transformed vector 
      // the length should be equal to the number of rows
      // of the matrix
      double[] v2 = new double[nr_m];
      
      for(int i = 0; i < nr_m; i++){
         for(int j = 0; j < nc_m; j++){
            v2[i] += m[i][j]*v1[j];
         }
      }
      
      return v2;
   } // end multiply
   
   
   public static double[] unit(double[] v){
      return new double[]{v[0]/len(v), v[1]/len(v), v[2]/len(v)};
   }
   
   // find the length of a given vector
   // only deal with 3D vectors 
   public static double len(double[] v){
      return Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
   }// len
   
   // try to find the cos(theta) between two given vectors
   public static double getCosTheta(double[] v, double[] w){
      return dot(v,w)/(len(v)*len(w));
   }// end getCosTheta
   
   // rotate around x-axis
   public static double[] rotateX(double[] v, double theta){
      // the matrix of rotation around x-axis 
      // 3x3 cartesian matrix 
      double[][] Rx = {{1.0, 0.0, 0.0}, {0.0, Math.cos(theta), -Math.sin(theta)}, 
                     {0.0, Math.sin(theta), Math.cos(theta)}};
      double[] vt = multiply(Rx, v);
      
      return vt;
      
      //v = vt;
   } // rotateX
   
   // this is another way to rotateX given the vector that we could 
   // work out the cos and sin based on that vector. 
   // sign indicates whether the rotation is clockwise or counterclockwise
   // the idea is we will have it for the arbitrary axis 
   public static double[] rotateX(double[] v, double[] a, int sign){
   
     double[] au = unit(a); // normalize the arbitrary axis 
     
     double g = Math.sqrt(au[1]*au[1] + au[2]*au[2]);
     
     // implement the rotation around x matrix 
     // this time we directly plug in cos and sin of theta 
     double[][] Rx = {{1.0, 0.0, 0.0}, {0, au[2]/g, (-au[1]/g)*sign}, 
                     {0, (au[1]/g)*sign, au[2]/g}};
     
     double[] vt = multiply(Rx, v);
    
     return vt; 
   } // end rotateX
   
   // rotate around y-axis
   public static double[] rotateY(double[] v, double theta){
   
      double[][] Ry = {{Math.cos(theta), 0.0, Math.sin(theta)}, {0.0, 1.0, 0.0}, 
                     {-Math.sin(theta), 0.0, Math.cos(theta)}};
      
      double[] vt = multiply(Ry, v);
      
      return vt;
   } // end rotateY
   
   // this is another way to rotate around y axis
   // the format and idea are similar to rotate around x axis 
   public static double[] rotateY(double[] v, double[] a, int sign){
   
     double[] au = unit(a);
     
     double g = Math.sqrt(au[1]*au[1] + au[2]*au[2]);
     
     // implement the rotation around y matrix 
     // this time we directly plug in cos and sin of theta 
     double[][] Ry = {{g, 0.0, (-au[0])*sign}, {0, 1.0, 0.0}, {(au[0])*sign, 0.0, g}};
     
     double[] vt = multiply(Ry, v);
    
     return vt; 
   } // end rotateY

   
   // rotate around z-axis
   public static double[] rotateZ(double[] v, double theta){
   
      double[][] Rz = {{Math.cos(theta), -Math.sin(theta), 0.0}, 
                     {Math.sin(theta), Math.cos(theta), 0.0}, {0.0, 0.0, 1.0}};
                     
      double[] vt = multiply(Rz, v);
   
      return vt;
   } // end rotateZ
   
   // arbitrary rotation
   // to be developed 
   public static double[] rotateArb(double[] v, double[] a, double theta){
      
      double[] vt;
      
      // we want to bring the arbitrary axis to the z-axis
      // after that we could rotate it around the z-axis
      // when the rotation around z-axis is done, we rotate
      // the point back  
      vt = rotateX(v, a, 1); // rotate around x based on vector a 
      vt = rotateY(vt, a, 1); // rotate around y based on vector a 
      vt = rotateZ(vt, theta); // this is the rotation we want 
      vt = rotateY(vt, a, -1); // rotate around y back
      vt = rotateX(vt, a, -1); // rotate around x back
      
      return vt;
   }
   
   // transform x and y coordinates for a 3D object to appear on the screen
   public static double[] perspectiveTransform(double[] v, double z, double e){
   
      double[] vt = new double[v.length];
      
      for(int i = 0; i < v.length; i++){
         vt[i] = v[i] / (1-z/e);         
      }
      
      return vt;
   }
   
   // scale all the vertices to a visible amount
   public static void scale(double[][] v, double scaleFactor){
      for(int i = 0; i < v.length; i++){
         for(int j = 0; j < v[0].length; j++){
            v[i][j] = v[i][j] * scaleFactor;
         }
      }
   }   
   
}