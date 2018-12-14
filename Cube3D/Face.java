/* Swing library */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.Math;
import java.util.*;

public class Face{

   double[][] vertices; // each face has four vertices for a cube
   Color faceColor; // the color of the individual face 
   boolean isSolid; // a switch to tell whether or not the color should be filled 

   public Face(double[][] v, Color c, boolean s){
      
      /* face construction */   
      vertices = v;
      faceColor = c;
      isSolid = s;
   }
   
   public void drawFace(Graphics2D g2d){
   
      // connect all the vertices in order
      // we want the squares to be connected
      // with edges 
      Path2D.Double face = new Path2D.Double();
      face.moveTo(vertices[0][0], vertices[0][1]);
      
      for(int i = 1; i < vertices.length; i++){
         face.lineTo(vertices[i][0], vertices[i][1]);
      }   
      
      face.closePath();
      
      // fill the color if the user clicked the 
      // corresponding button
      if(isSolid){
         g2d.setColor(faceColor);
         g2d.fill(face);
      }
      
      g2d.draw(face);      
   } // end drawFace
}