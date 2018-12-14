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

public class ControlPanel extends JPanel implements ActionListener{

   WrapperPanel wPanel;
   
   // cube rotate axes buttons
   JButton[] cubeAxisBtns;
   String[] cubeAxisBtnsNames = {"x-axis", "y-axis", "z-axis"};
   // cube textures buttons
   JButton[] cubeTextBtns;
   String[] cubeTextBtnsNames = {"box", "brick", "block", "minecraft"};
   
   // cylinder textures buttons
   JButton[] cylinderTextBtns;
   String[] cylinderTextBtnsNames = {"soda can", "7 UP"};
   
   // sphere textures buttons
   JButton[] sphereTextBtns;
   String[] sphereTextBtnsNames = {"jupiter", "earth"};
   
   // rotation button for cylinder and sphere only
   JButton rotateBtn;
   String rotateBtnName = "ROTATE";
   
   // rotation button for cubes only 
   JButton rotateAxisBtn;
   
   // reset button that reset everything at once 
   JButton resetBtn;
   
   double phi; // rotation for cylinder and sphere

   public ControlPanel(WrapperPanel wp){
   
      wPanel = wp;
      setLayout(new GridLayout(5,1)); // integrated control panel layout 
      
      // initialize all the sub panels for the buttons 
      JPanel cubePanel, rotateAxisPanel, cylinderPanel, spherePanel, rotatePanel;
      
      // begin cubePanel setup
      cubePanel = new JPanel();
      // cubePanel layout
      cubePanel.setLayout(new GridLayout(3,1));
      
      JPanel cubePanelSub1 = new JPanel();
      cubePanelSub1.setLayout(new GridLayout(1,1));
      cubePanelSub1.add(new JLabel("Cube"));
      
      JPanel cubePanelSub2 = new JPanel();
      cubePanelSub2.setLayout(new GridLayout(1,3));
      cubeAxisBtns = new JButton[3];
      
      // add in buttons for cube rotation axis
      for(int i1 = 0; i1 < cubeAxisBtns.length; i1++){
         cubeAxisBtns[i1] = new JButton(cubeAxisBtnsNames[i1]);
         cubeAxisBtns[i1].addActionListener(this);
         cubePanelSub2.add(cubeAxisBtns[i1]);
      }
      
      JPanel cubePanelSub3 = new JPanel();
      cubePanelSub3.setLayout(new GridLayout(1,4));
      cubeTextBtns = new JButton[4];
      
      // add in buttons for cube textures 
      for(int i2 = 0; i2 < cubeTextBtns.length; i2++){
         cubeTextBtns[i2] = new JButton(cubeTextBtnsNames[i2]);
         cubeTextBtns[i2].addActionListener(this);
         cubePanelSub3.add(cubeTextBtns[i2]);
      }
      
      // add in all the sub panels for cubePanel
      cubePanel.add(cubePanelSub1);
      cubePanel.add(cubePanelSub3);
      cubePanel.add(cubePanelSub2);      
      add(cubePanel); // end cubePanel setup
      
      // begin rotateAxisPanel setup
      rotateAxisPanel = new JPanel();
      // rotateAxisPanel layout
      rotateAxisPanel.setLayout(new GridLayout(2,1));
      rotateAxisBtn = new JButton("ROTATE CUBE");
      rotateAxisBtn.addActionListener(this);
      rotateAxisPanel.add(rotateAxisBtn);
      rotateAxisPanel.add(new JLabel());
      add(rotateAxisPanel); // end rotateAxisPanel setup
      
      // begin cylinderPanel setup
      cylinderPanel = new JPanel();
      // cylinderPanel layout
      cylinderPanel.setLayout(new GridLayout(2,1));
      
      JPanel cylinderPanelSub1 = new JPanel();
      cylinderPanelSub1.setLayout(new GridLayout(1,1));
      cylinderPanelSub1.add(new JLabel("Cylinder"));
      
      JPanel cylinderPanelSub2 = new JPanel();
      cylinderPanelSub2.setLayout(new GridLayout(1,2));
      
      cylinderTextBtns = new JButton[2];
      
      // add in cylinder textures buttons 
      for(int j = 0; j < cylinderTextBtns.length; j++){
         cylinderTextBtns[j] = new JButton(cylinderTextBtnsNames[j]);
         cylinderTextBtns[j].addActionListener(this);
         cylinderPanelSub2.add(cylinderTextBtns[j]);
      }
      
      // add in all the sub panels for cylinderPanel
      cylinderPanel.add(cylinderPanelSub1);
      cylinderPanel.add(cylinderPanelSub2);
      
      add(cylinderPanel); // end cylinderPanel setup
      
      // begin spherePanel setup
      spherePanel = new JPanel();
      // spherePanel layout
      spherePanel.setLayout(new GridLayout(2,1));
      
      JPanel spherePanelSub1 = new JPanel();
      spherePanelSub1.setLayout(new GridLayout(1,1));
      spherePanelSub1.add(new JLabel("Sphere"));
      
      JPanel spherePanelSub2 = new JPanel();
      spherePanelSub2.setLayout(new GridLayout(1,2));
      
      sphereTextBtns = new JButton[2];
      
      // add in all the buttons for sphere textures 
      for(int k = 0; k < sphereTextBtns.length; k++){
         sphereTextBtns[k] = new JButton(sphereTextBtnsNames[k]);
         sphereTextBtns[k].addActionListener(this);
         spherePanelSub2.add(sphereTextBtns[k]);
      }
      
      // add in all the sub panels for spherePanel
      spherePanel.add(spherePanelSub1);
      spherePanel.add(spherePanelSub2);
      
      add(spherePanel); // end cylinderPanel setup
      
      // begin rotatePanel setup
      rotatePanel = new JPanel();
      rotatePanel.setLayout(new GridLayout(2,2));
      rotatePanel.add(new JLabel());
      rotatePanel.add(new JLabel());
      rotateBtn = new JButton(rotateBtnName);      
      rotateBtn.addActionListener(this);
      rotatePanel.add(rotateBtn);
      
      // reset button setup
      resetBtn = new JButton("RESET");
      resetBtn.addActionListener(this);
      rotatePanel.add(resetBtn);
      
      add(rotatePanel); // end rotatePanel setup
      
      phi = 0.0; // default rotation angle for cylinders and spheres 
   }
   
   public void actionPerformed(ActionEvent e){
      
      // loop through cube textures
      for(int i = 0; i < cubeTextBtns.length; i++){
         if(e.getSource() == cubeTextBtns[i]){
            wPanel.index = i;
            wPanel.repaint();
         }
      }// end loop
      
      // loop through cylinder textures
      for(int j = 0; j < cylinderTextBtns.length; j++){
         if(e.getSource() == cylinderTextBtns[j]){
            wPanel.index = j+4;
            wPanel.repaint();
         }
      }// end loop
      
      // loop through sphere textures
      for(int k = 0; k < sphereTextBtns.length; k++){
         if(e.getSource() == sphereTextBtns[k]){
            wPanel.index = k+6;
            wPanel.repaint();
         }
      }// end loop
      
      // the rotations for cylinder and sphere 
      // and the angle is accumulative 
      if(e.getSource() == rotateBtn){
         phi += 20.0;
         wPanel.phi = phi;
         wPanel.theta = 0.0;
         wPanel.repaint();
      }
      
      // reset button
      if(e.getSource() == resetBtn){
      
         for(int i = 0; i < wPanel.cubes.length; i++){
            // the vertices of how a cube is to be oriented with it is set
            // to align with the z-axis
            wPanel.cubes[i].vertices = new double[][]{{-1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, 
                  {1.0, -1.0, 1.0}, {-1.0, -1.0, 1.0}, {-1.0, -1.0, -1.0}, 
                  {1.0, -1.0, -1.0}, {1.0, 1.0, -1.0}, {-1.0, 1.0, -1.0}};
            Matrix.scale(wPanel.cubes[i].vertices, 100.0); // don't forget about the scale
         }
         wPanel.theta = 0.0; // no rotation for cubes 
         
         phi = 0.0; // no rotation for cylinders and spheres 
         wPanel.phi = phi;
         wPanel.repaint();
      }
      
      // rotation axis x
      if(e.getSource() == cubeAxisBtns[0]){
         // rotation switch: x swich up
         wPanel.isRot = new boolean[]{true, false, false};
         wPanel.theta = 0.0;
      }
      
      // rotation axis y
      if(e.getSource() == cubeAxisBtns[1]){
         // rotation switch: y swich up
         wPanel.isRot = new boolean[]{false, true, false};
         wPanel.theta = 0.0;
      }
      
      // rotation axis z
      if(e.getSource() == cubeAxisBtns[2]){
         // rotation switch: z swich up
         wPanel.isRot = new boolean[]{false, false, true};
         wPanel.theta = 0.0;
      }
      
      // rotation for the cube 
      if(e.getSource() == rotateAxisBtn){
         // rotation 20 degrees for every click
         wPanel.theta = 15.0; 
         wPanel.repaint();
      }

      
   }
   
}