/* Swing library */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

public class Cube{

   public final static double HEIGHT = 600.0; // height of the canvas
   public final static double WIDTH = 600.0; // width of the canvas 

   double[][] vertices; // vertices could be centered at the origin 
   Face[] f; // faces for a cube
   BufferedImage bufferedImg; // buffered image for each face
   String texFileName; // image file name 
   // determine which axis the cube should rotate around
   boolean isRx, isRy, isRz;  
   
   double z = 100.0; // z axis, which indicates the length of the cube 
   double e = z/(100.0/1200.0); // distance of the eye
   
   /* cube constructor */
   public Cube(BufferedImage bi, String tfn){
      
      bufferedImg = bi;
      texFileName = tfn;
               
      // set up the vertices 
      vertices = new double[][]{{-1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, -1.0, 1.0}, 
                 {-1.0, -1.0, 1.0}, {-1.0, -1.0, -1.0}, {1.0, -1.0, -1.0}, 
                 {1.0, 1.0, -1.0}, {-1.0, 1.0, -1.0}};
                 
      // scale the vertices to a visible amount 
      Matrix.scale(vertices, z);
                      
   }// end Cube
   
   // build up faces to form a cube
   public void buildFaces(double theta){
   
      for(int i = 0; i < vertices.length; i++){
         // if the rotation is around x axis 
         if(isRx){
            vertices[i] = Matrix.rotateX(vertices[i], 
                        Math.toRadians(theta));
         }
         // if the rotation is around y axis 
         if(isRy){
            vertices[i] = Matrix.rotateY(vertices[i], 
                        Math.toRadians(theta));
         }
         // if the rotation is around z axis 
         if(isRz){
            vertices[i] = Matrix.rotateZ(vertices[i], 
                        Math.toRadians(theta));
         }
      }// end for loop
      
      // make a copy of the vertices after rotation, we can leave the old vertices there
      // waiting for later rotation. Meanwhile, we will do perspective transform to the 
      // copied vertices, just so they could be appeared on a 2D screen.
      double[][] vt = new double[vertices.length][];
      
      // do perspective transformation
      for(int j = 0; j < vertices.length; j++){
         vt[j] = Matrix.perspectiveTransform(vertices[j], vertices[j][2], e);
      }
      
      vt = T(vt); // translate (300, 300)
               
      /* build up each face based on the vertices each face has */ 
      Face f1, f2, f3, f4, f5, f6;
      f1 = new Face(new double[][]{vt[2], vt[1], vt[0], vt[3]}, bufferedImg, texFileName);
      f2 = new Face(new double[][]{vt[5], vt[6], vt[1], vt[2]}, bufferedImg, texFileName);
      f3 = new Face(new double[][]{vt[5], vt[6], vt[7], vt[4]}, bufferedImg, texFileName);
      f4 = new Face(new double[][]{vt[4], vt[7], vt[0], vt[3]}, bufferedImg, texFileName); 
      f5 = new Face(new double[][]{vt[1], vt[6], vt[7], vt[0]}, bufferedImg, texFileName);
      f6 = new Face(new double[][]{vt[2], vt[5], vt[4], vt[3]}, bufferedImg, texFileName);
            
      f = new Face[]{f1, f2, f3, f4, f5, f6};
      
   }// end buildFaces
   
   // translate the coordinate just so the origin is
   // (300, 300)
   // since setRGB() doesn't deal with negative coordinates
   public double Tx(double a){
      return a + WIDTH/2; // x translation
   }
   
   // translate the coordinate just so the origin is
   // (300, 300)
   public double Ty(double b){
      return HEIGHT/2 - b; // y translation
   }
   
   // translate all the coordinates on the buffered image
   // all at one. 
   public double[][] T(double[][] v){
      
      double[][] tv = new double[v.length][v[0].length];
   
      for(int i = 0; i < v.length; i++){
         tv[i][0] = Tx(v[i][0]); // translate x 
         tv[i][1] = Ty(v[i][1]); // translate y
         tv[i][2] = v[i][2];     // z unchanged 
      }
      
      return tv;
   }// end T
   
   
   // implement a simple bubble sort algorithm to sort out faces 
   // based on their average z values (bubble sort)
   // however, if we have polygons with much more vertices, then
   // we have to consider mergesort or other more efficient sorting
   // algorithms 
   public Face[] bubbleSortFaces(Face[] f){
      
       /* implement painter's algorithm to keep track of average z */
       double[] faceZave = new double[6];
       Face[] faces = f; // faces to be sorted       
       
       // take the average z of the vertices for each face 
       for(int i = 0; i < faceZave.length; i++){
         faceZave[i] = ((faces[i].vertices[0][2])+(faces[i].vertices[1][2])+
                        (faces[i].vertices[2][2])+(faces[i].vertices[3][2]))/4.0;
       }
       
       /* sort out faces based on average z: implment bubble sorting algorithm */
       int n = faceZave.length;
       for (int i = 0; i < n-1; i++){
           for (int j = 0; j < n-i-1; j++){
               
               if (faceZave[j] > faceZave[j+1]){
                   // swap temp and faceZave[i]
                   double temp = faceZave[j];
                   faceZave[j] = faceZave[j+1];
                   faceZave[j+1] = temp;
                    
                   // swap tmpFace and faces[j]
                   // in the meantime 
                   Face tmpFace = faces[j];
                   faces[j] = faces[j+1];
                   faces[j+1] = tmpFace;
                }
            }
        }
        
        return faces;
      
    }// end bubbleSortFaces
   
   // main mapping function 
   // given the degree of rotation and a switch for
   // which axis to rotate around. Render the cube 
   // with given texture
   public void mapCube(double theta, boolean[] isRot){
      
      /* set up axis switch */
      isRx = isRot[0];
      isRy = isRot[1];
      isRz = isRot[2];
      
      // build up faces based on rotation degree
      buildFaces(theta); 
      // sort out faces 
      Face[] sortedFaces = bubbleSortFaces(f);   
      
      for(int i = 0; i < sortedFaces.length; i++){
         // map the texture of the face in the sorted
         // order 
         sortedFaces[i].mapFace();
      }
      
   }// end mapCube
   
}