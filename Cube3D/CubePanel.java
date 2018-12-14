/* Swing library */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.Math;
import java.util.*;

public class CubePanel extends JPanel{
   
   double[][] vertices; // the vertices of the cube, will be updated constantly
   Face[] faces; // the six faces of the cube 
   double z = 100.0; // z axis, which indicates the length of the cube 
   double e = z/(100.0/1200.0); // distance of the eye
   boolean isRx, isRy, isRz, isArb; // switch to tell which rotation it is currently in 
   double theta; // rotational degree
   double[] a; // the vector representing an arbitrary axis 
   boolean[] isSolid; // switch to tell if the cube should be in wireframe or colored 
   
   // initiate the original cube on the panel
   public CubePanel(){
   
      setPreferredSize(new Dimension(600,600));
		setBackground(new Color(240,248,255)); // alice blue 
      
      /* initialization */
      theta = 0.0;
      isRx = false; isRy = false; isRz = false; isArb = false;
      
      isSolid = new boolean[6];
      for(int i = 0; i < isSolid.length; i++){
         isSolid[i] = false;
      }
      
      // set up the vertices 
      vertices = new double[][]{{-1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, -1.0, 1.0}, 
                 {-1.0, -1.0, 1.0}, {-1.0, -1.0, -1.0}, {1.0, -1.0, -1.0}, 
                 {1.0, 1.0, -1.0}, {-1.0, 1.0, -1.0}};
                           
      // scale the vertices to a visible amount 
      MatrixUtility.scale(vertices, z);
      
      // orient the arbitrary axis to the z axis 
      a = new double[]{0.0,0.0,1.0};
      
   }
   
   public void paintComponent(Graphics g){
   
		super.paintComponent(g);  //without this no background color set.
	
		Graphics2D g2d = (Graphics2D)g; //cast so we can use JAVA2D.
		g2d.translate(getWidth()/2,getHeight()/2);
		g2d.scale(1,-1);
      
      // invoke any of the rotation methods if called, based on theta, we know the cube
      // should be rotating how much amount in which direction
      for(int i = 0; i < vertices.length; i++){
         if(isRz){
            vertices[i] = MatrixUtility.rotateZ(vertices[i], Math.toRadians(theta));
         }
         
         if(isRx){
            vertices[i] = MatrixUtility.rotateX(vertices[i], Math.toRadians(theta));
         }
         
         if(isRy){
            vertices[i] = MatrixUtility.rotateY(vertices[i], Math.toRadians(theta));
         }
         
         if(isArb){
            vertices[i] = MatrixUtility.rotateArb(vertices[i], a, Math.toRadians(theta));
         }
         
      }
      
      // make a copy of the vertices after rotation, we can leave the old vertices there
      // waiting for later rotation. Meanwhile, we will do perspective transform to the 
      // copied vertices, just so they could be appeared on a 2D screen.
      double[][] vt = new double[vertices.length][];
      
      for(int j = 0; j < vertices.length; j++){
         vt[j] = MatrixUtility.perspectiveTransform(vertices[j], vertices[j][2], e);
      }

      /* build up each face based on the vertices each face has */ 
      Face f1, f2, f3, f4, f5, f6;
      f1 = new Face(new double[][]{vt[1], vt[0], vt[3], vt[2]}, 
                     new Color(255,99,71), isSolid[0]); // tomato
      f2 = new Face(new double[][]{vt[6], vt[1], vt[2], vt[5]}, 
                     new Color(255,215,0), isSolid[1]); // gold
      f3 = new Face(new double[][]{vt[7], vt[6], vt[5], vt[4]}, 
                     new Color(154,205,50), isSolid[2]); // yellow green
      f4 = new Face(new double[][]{vt[0], vt[7], vt[4], vt[3]}, 
                     new Color(135,206,235), isSolid[3]); // sky blue
      f5 = new Face(new double[][]{vt[6], vt[7], vt[0], vt[1]}, 
                     new Color(147,112,219), isSolid[4]); // medium purple
      f6 = new Face(new double[][]{vt[2], vt[3], vt[4], vt[5]}, 
                     new Color(255,192,203), isSolid[5]); // pink
            
      faces = new Face[]{f1, f2, f3, f4, f5, f6};
      
      // sort the faces based on the average z value of the vertices on each face
      // we want to figure out which face is the front face and which one is the
      // back face. 
      bubbleSortFaces(faces); 
      
      //after we sort out the order of the faces. we fill from face
      // with low average z to high average of z
      for(int i = 0; i < faces.length; i++){
         faces[i].drawFace(g2d);
      }
          
	 }

    // implement a simple bubble sort algorithm to sort out faces 
    // based on their average z values (bubble sort)
    // however, if we have polygons with much more vertices, then
    // we have to consider mergesort or other more efficient sorting
    // algorithms 
    public void bubbleSortFaces(Face[] faces){
      
       double[] faceZave = new double[6];       
       
       for(int i = 0; i < faceZave.length; i++){
         faceZave[i] = ((faces[i].vertices[0][2])+(faces[i].vertices[1][2])+
                        (faces[i].vertices[2][2])+(faces[i].vertices[3][2]))/4;
       }
       
       int n = faceZave.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (faceZave[j] > faceZave[j+1])
                {
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