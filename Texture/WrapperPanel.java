/* Swing library */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

public class WrapperPanel extends JPanel{

   BufferedImage wrapperImg; // wrapper image 
   int width = 600; // width of the canvas
   int height = 600; // height of the canvas 
   TextureImage[] tis; // all the image information 
   int index; // chosen image index
   double phi; // rotation angle for cylinder and sphere
   double theta; // rotation angle for cube   
   boolean[] isRot; // x,y,z rotations for the cube
   Cube[] cubes; // cubes with different textures
   Cylinder[] cylinders; // cylinders with different textures
   Sphere[] spheres; // spheres with different textures 
         
   public WrapperPanel(){
   
      setPreferredSize(new Dimension(600,600));
      // reserve memory for putting the wrapper image
      wrapperImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      // refresh background 
      setWhiteScene(); 
      
      tis = createTextureImages(); // store all the images information
      index = 0; // texture that's being chosen
      phi = 0.0; // rotation angle for sphere and cylinder
      theta = 0.0; // rotation angle for cubes 
      isRot = new boolean[]{false, false, false};  
       
      /* cube initializations */ 
      cubes = new Cube[4]; 
      for(int i = 0; i < cubes.length; i++){
         cubes[i] = new Cube(wrapperImg, tis[i].fileName);
      }
      
      /* cylinder and sphere initializations */ 
      cylinders = new Cylinder[2];
      spheres = new Sphere[2];
      for(int j = 0; j < spheres.length; j++){
         cylinders[j] = new Cylinder(wrapperImg, tis[j+4].fileName, tis[j+4].dim); 
         spheres[j] = new Sphere(wrapperImg, tis[j+6].fileName, tis[j+6].dim); 
      }
      
   }
   
   // return an array of all the image information instances 
   // so that we can use it on the fly
   public TextureImage[] createTextureImages(){
   
      String prefix = "img/"; // folder of the images 
      
      // names of all images 
      String[] tfns = {"box.jpeg", "brick.png", "block.jpeg", "minecraft.jpeg",
                        "sodacan.png", "7up.jpeg", "jupiter.jpeg", "earth.jpeg"};
                        
      // dimensions of all images 
      Dimension[] dims = new Dimension[]{new Dimension(512,512), new Dimension(512,512),
                     new Dimension(512,512), new Dimension(512,512), new Dimension(1024,512),
                     new Dimension(900,488), new Dimension(1024,512), new Dimension(1024,512)};
                     
      TextureImage[] tis = new TextureImage[8]; // 8 images in total
      
      // store all the images information in an array
      for(int i = 0; i < tis.length; i++){
         tis[i] = new TextureImage(dims[i], prefix+tfns[i]);
      }
      return tis;
   }
   
   
   // render a white background, set the RGB of every pixel to white
   public void setWhiteScene(){
      for ( int rc = 0; rc < wrapperImg.getHeight(); rc++ ) {
         for ( int cc = 0; cc < wrapperImg.getWidth(); cc++ ) {
         
            // Set the pixel colour of the image n.b. x = cc, y = rc
            wrapperImg.setRGB(cc, rc, new Color(240,255,240).getRGB());
         }//for cols
      }//for rows
   } // end setBlackScene
     
   public void paintComponent(Graphics g){
     
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D)g;
      setWhiteScene(); // refresh the canvas everything when something is drawn

      // the first 4 images in the array are cubes
      if(index < 4){
         cubes[index].mapCube(theta, isRot); // map cube
      }
      
      // image 5 and 6 are for cylinders 
      if(index >= 4 && index < 6){
         cylinders[index-4].mapCylinder(phi); // map cylinder
      }
      
      // image 7 and 8 are for spheres 
      if(index >= 6 && index < 8){
         spheres[index-6].mapSphere(phi); // map sphere
      }  
      
      // draw the buffer image on screen
      g2d.drawImage(wrapperImg, 0, 0, this);
      g2d.dispose();
      
   }
   
}