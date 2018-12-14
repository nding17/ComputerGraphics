/* Swing library */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

// texture image to store the
// information of the texture image 
public class TextureImage{

   public Dimension dim; // dimension of the image 
   public String fileName; // file name of that image 
   
   /* texture image constructor */
   public TextureImage(Dimension d, String fn){
      dim = d;
      fileName = fn;
   }// end TextureImage

}