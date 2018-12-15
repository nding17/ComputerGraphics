import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.geom.*;
import java.lang.Math;


// control panel for user inputs 
public class ControlPanel extends JPanel implements ActionListener{

   SpherePanel sPanel; // canvas panel for drawing the sphere
   JButton[] colorButtons; // buttons for changing the sphere color
   String[] colorNames = {"RED", "GREEN", "BLUE", "GRAY"};
   
   JButton[] specButtons; // button to activate specular light
   String[] specNames = {"SPECULAR", "NON-SPECULAR"};
   
   JButton[] posButtons; // buttons to change the postions of the light 
   String[] posNames = {"x+50", "x-50", "y+50", "y-50", "RESET"};
      
   public ControlPanel(SpherePanel sp){
      sPanel = sp;
      setLayout(new GridLayout(3,1));
      
      // set up the color panel and add in
      // the corresponding buttons
      JPanel colorPanel = new JPanel();
      colorPanel.setLayout(new GridLayout(3,3));
      colorButtons = new JButton[4];
      for(int i = 0; i < colorNames.length; i++){
         colorButtons[i] = new JButton(colorNames[i]);
         colorButtons[i].addActionListener(this);
         colorPanel.add(colorButtons[i]);
      }
      
      // set up the specular light panel and add in 
      // corresponding buttons 
      JPanel specPanel = new JPanel();
      specButtons = new JButton[2];
      specPanel.setLayout(new GridLayout(3,1));
      
      for(int j = 0; j < specNames.length; j++){
         specButtons[j] = new JButton(specNames[j]);
         specButtons[j].addActionListener(this);
         specPanel.add(specButtons[j]);
      }
      
      // set up the panel for changing the position 
      // of the light source and add in the corresponding
      // buttons to change the location 
      // for simplicity, we can only change x and y coordinates
      // letting the distance to the sphere fixed 
      JPanel posPanel = new JPanel();
      posPanel.setLayout(new GridLayout(3,2));
      
      posButtons = new JButton[5];
      
      
      // add in all the buttons in order 
      for(int k = 0; k < posNames.length; k++){
         
         posButtons[k] = new JButton(posNames[k]);
         posButtons[k].addActionListener(this);
         posPanel.add(posButtons[k]);
      }
      
      
      // add in all the panels for control
      add(colorPanel);
      add(specPanel);
      add(posPanel);
   } // end ControlPanel
   
   public void actionPerformed(ActionEvent ev){
      
      // if the user indicates a red sphere
      if(ev.getSource() == colorButtons[0]){
         sPanel.isRed = true;
         sPanel.isGreen = false;
         sPanel.isBlue = false;
         sPanel.repaint();
      }
      
      // if the user indicates a green sphere
      if(ev.getSource() == colorButtons[1]){
         sPanel.isRed = false;
         sPanel.isGreen = true;
         sPanel.isBlue = false;
         sPanel.repaint();
      }
      
      // if the user indicates a blue sphere
      if(ev.getSource() == colorButtons[2]){
         sPanel.isRed = false;
         sPanel.isGreen = false;
         sPanel.isBlue = true;
         sPanel.repaint();
      }
      
      // default sphere, gray
      if(ev.getSource() == colorButtons[3]){
         sPanel.isRed = false;
         sPanel.isGreen = false;
         sPanel.isBlue = false;
         sPanel.repaint();
      }
      
      // if the user wants to have specular effect
      if(ev.getSource() == specButtons[0]){
         sPanel.isSpecular = true;
         sPanel.repaint();
      }
      
      // if the user only want ambient and diffused light effect
      if(ev.getSource() == specButtons[1]){
         sPanel.isSpecular = false;
         sPanel.repaint();
      }
      
      // move the light source to the right
      if(ev.getSource() == posButtons[0]){
         sPanel.lx += 50.0;
         sPanel.repaint();
      }
      
      // move the light source to the left
      if(ev.getSource() == posButtons[1]){
         sPanel.lx -= 50.0;
         sPanel.repaint();
      }
      
      // move the light source up
      if(ev.getSource() == posButtons[2]){
         sPanel.ly += 50.0;
         sPanel.repaint();
      }
      
      // move the light source down 
      if(ev.getSource() == posButtons[3]){
         sPanel.ly -= 50.0;
         sPanel.repaint();
      }
      
      // move the light source to the default position
      if(ev.getSource() == posButtons[4]){
         sPanel.lx = 0.0;
         sPanel.ly = 120.0;
         sPanel.repaint();
      }
      
   }// end actionPerformed 

}