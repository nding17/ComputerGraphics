/* Swing library */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

public class Face{
   
   double[][] vertices; // each face has four vertices for a cube
   // buffered image which texture should be put on
   BufferedImage bufferedImg; 
   TextureCube tex; // texture 
   
   public Face(double[][] v, BufferedImage bi, String tfn){
      
      /* face construction */   
      vertices = v;
      bufferedImg = bi;
      // construct a texture for the cube
      tex = new TextureCube(bufferedImg, vertices, tfn);
   }
   
   
   public void mapFace(){
      tex.mapTexture(); // map the texture on the face 
   }
   
}