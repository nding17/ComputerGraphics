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

public class ControlPanel extends JPanel implements ActionListener {

   CubePanel cPanel;
   JButton[] rButtons; // rotation button
   JButton setButton; // button to take in vector inputs 
   JButton resetButton; // button to reset the cube to orient with z-axis 
   String[] bNames = {"X", "Y", "Z"}; // normal axis
   String[] arbText = {"x-comp: ", "y-comp: ", "z-comp: "}; // vector description
   JTextField[] vtext; // text field for vector inputs 
   
   JButton[] rDirButtons; // rotation buttons
   JButton[] colorButtons; // fill buttons
   
   String[] rDirNames = {"CW", "CCW"}; // rotation names
   String[] colorText = {"WIRE", "SOLID"}; // fill names
   
   public ControlPanel(CubePanel cp){
      cPanel = cp;
      setLayout(new GridLayout(4,1));
      
      // set up all the parts we have in the control panel
      // bNamePanel stores the button of all the normal axis rotations
      // rDirPanel is actually the rotate buttons
      // and colorPanel stores the buttons for wire and solid options 
      JPanel bNamePanel = new JPanel(), rDirPanel = new JPanel(), 
                        colorPanel = new JPanel();
      
      // set the layout of all various button panels 
      bNamePanel.setLayout(new GridLayout(3,1));
      rDirPanel.setLayout(new GridLayout(4,1));
      colorPanel.setLayout(new GridLayout(4,1));
      
      rButtons = new JButton[3];
      rDirButtons = new JButton[2];
      colorButtons = new JButton[2];
      vtext = new JTextField[3];
      
      for(int i = 0; i < vtext.length; i++){
         vtext[i] = new JTextField();
      }
      
      // organize the panel for axis buttons nicely 
      JPanel bNamePanelSub1 = new JPanel();
      JPanel bNamePanelSub2 = new JPanel();
      JPanel bNamePanelSub3 = new JPanel();
                   
      bNamePanelSub1.setLayout(new GridLayout(1,1));
      bNamePanelSub1.add(new JLabel("NORMAL AXIS"));
      bNamePanelSub2.setLayout(new GridLayout(1,3));
      bNamePanelSub3.setLayout(new GridLayout(1,1));
      resetButton = new JButton("RESET");
      resetButton.addActionListener(this);
      bNamePanelSub3.add(resetButton);
      
      JPanel colorPanelSub1 = new JPanel(),
             colorPanelSub2 = new JPanel(),
             colorPanelSub3 = new JPanel(),
             colorPanelSub4 = new JPanel();
      
      // put all the sub panels in a array so in the end you can
      // add all the sub panels bying using a loop
      JPanel[] colorPanelSubs = new JPanel[]{colorPanelSub4, colorPanelSub1, 
                                 colorPanelSub2, colorPanelSub3};
      
      // set up all the sub panels so they look nice 
      colorPanelSub1.setLayout(new GridLayout(1,1));
      colorPanelSub1.add(new JLabel("FILL"));
      colorPanelSub2.setLayout(new GridLayout(1,2));
      colorPanelSub3.setLayout(new GridLayout(1,1));
      
      // sub panels for the rotation panel
      // same trick as above
      JPanel rDirPanelSub1 = new JPanel(),
             rDirPanelSub2 = new JPanel(),
             rDirPanelSub3 = new JPanel(),
             rDirPanelSub4 = new JPanel();
      
      JPanel[] rDirPanelSubs = new JPanel[]{rDirPanelSub4, rDirPanelSub1, 
                                 rDirPanelSub2, rDirPanelSub3};
             
      rDirPanelSub1.setLayout(new GridLayout(1,1));
      rDirPanelSub1.add(new JLabel("ROTATION"));
      rDirPanelSub2.setLayout(new GridLayout(1,2));
      rDirPanelSub3.setLayout(new GridLayout(1,1));
      
      // this is the panel for tha arbitrary axis
      // including label and the textfields that take
      // in user inputs 
      JPanel arbPanel = new JPanel();
      arbPanel.setLayout(new GridLayout(2,1));
      JPanel arbPanelSub1 = new JPanel();
      arbPanelSub1.setLayout(new GridLayout(1,1));
      arbPanelSub1.add(new JLabel("ARBITRARY AXIS"));
      
      JPanel arbPanelSub2 = new JPanel();
      arbPanelSub2.setLayout(new GridLayout(4,2));
      setButton = new JButton("SET");
      setButton.addActionListener(this);
      
      arbPanel.add(arbPanelSub1);
      arbPanel.add(arbPanelSub2);
      
      // add the buttons all at once
      for(int i = 0; i < bNames.length; i++){
         rButtons[i] = new JButton(bNames[i]);
         rButtons[i].addActionListener(this);
         bNamePanelSub2.add(rButtons[i]);
                 
         arbPanelSub2.add(new JLabel(arbText[i]));
         arbPanelSub2.add(vtext[i]);
      }
      arbPanelSub2.add(new JLabel());
      arbPanelSub2.add(setButton);
      
      bNamePanel.add(bNamePanelSub1);
      bNamePanel.add(bNamePanelSub2); 
      bNamePanel.add(bNamePanelSub3);
      
      // add the buttons all at once 
      for(int j = 0; j < rDirNames.length; j++){
         rDirButtons[j] = new JButton(rDirNames[j]);
         rDirButtons[j].addActionListener(this);
         rDirPanelSub2.add(rDirButtons[j]);
         
         colorButtons[j] = new JButton(colorText[j]);
         colorButtons[j].addActionListener(this);
         colorPanelSub2.add(colorButtons[j]);
         
      }
      
      // add in all the sub panels all at once 
      for(int i = 0; i < colorPanelSubs.length; i++){
         colorPanel.add(colorPanelSubs[i]);
         rDirPanel.add(rDirPanelSubs[i]);
      }
      
      // add all the sub control panels to the 
      // real control panel
      add(bNamePanel);
      add(arbPanel);
      add(colorPanel);
      add(rDirPanel);
  }
   
   
  // check if the string gives a numerical value
  // try to convert the string text into a number
  // if failed, we will be careful not to take in 
  // that invalid result. We certainly don't want
  // to deal with random string inputs from users 
  public static boolean isNumeric(String str){  
    try{  
      double d = Double.parseDouble(str);  
    }catch(NumberFormatException nfe){  
      return false; 
    }  
    return true;  
  } // end isNumeric
   
   public void actionPerformed(ActionEvent ev){
   
      // if rotate around x-axis
      if(ev.getSource() == rButtons[0]){
         cPanel.isRx = true;
         cPanel.isRy = false;
         cPanel.isRz = false;
         cPanel.isArb = false;
         cPanel.theta = 0.0;
      }
      
      // if rotate around y-axis 
      if(ev.getSource() == rButtons[1]){
         cPanel.isRx = false;
         cPanel.isRy = true;
         cPanel.isRz = false;
         cPanel.isArb = false;
         cPanel.theta = 0.0;
      }
      
      // if rotate around z-axis 
      if(ev.getSource() == rButtons[2]){
         cPanel.isRx = false;
         cPanel.isRy = false;
         cPanel.isRz = true;
         cPanel.isArb = false;
         cPanel.theta = 0.0;
      }
      
      // if the user lost the orientation of the cube
      // he could reset the axis to the z-axis
      if(ev.getSource() == resetButton){
      
         // the vertices of how a cube is to be oriented with it is set
         // to align with the z-axis 
         cPanel.vertices = new double[][]{{-1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, 
                  {1.0, -1.0, 1.0}, {-1.0, -1.0, 1.0}, {-1.0, -1.0, -1.0}, 
                  {1.0, -1.0, -1.0}, {1.0, 1.0, -1.0}, {-1.0, 1.0, -1.0}};
         
         MatrixUtility.scale(cPanel.vertices, 100.0); // don't forget about the scale
         cPanel.theta = 0.0;
         
         cPanel.repaint();

      }
      
      // if the user has entered the vector that indicates 
      // an arbitrary axis. once setButton is clicked, all
      // text inputs will be converted into doubles. if the
      // convertion fails, nothing happens and the cube will
      // stuck there, unable to do anything unless a valid 
      // vector is entered 
      if(ev.getSource() == setButton){
         cPanel.isRx = false;
         cPanel.isRy = false;
         cPanel.isRz = false;
         cPanel.isArb = true;
         cPanel.theta = 0.0;
         
         double[] at = new double[3];
         
         // if validation check fails
         // doing nothing, disable rotations function
         for(int j = 0; j < vtext.length; j++){
            if(vtext[j].getText().isEmpty() || 
               !isNumeric(vtext[j].getText())){
               cPanel.isRx = false;
               cPanel.isRy = false;
               cPanel.isRz = false;
               cPanel.isArb = false;
               cPanel.theta = 0.0;

               return;
            }
         }
         // convet string into double 
         // validation check 
         for(int i = 0; i < vtext.length; i++){
            if(!(vtext[i].getText().isEmpty()) &&
                  isNumeric(vtext[i].getText())){
               at[i] = Double.parseDouble(vtext[i].getText());
            }
         }
         
         cPanel.a = at;
      }
      
      // rotate clock wise
      if(ev.getSource() == rDirButtons[0]){
         cPanel.theta = -15.0;
         cPanel.repaint();
      }
      
      // rotate counterclockwise 
      if(ev.getSource() == rDirButtons[1]){
         cPanel.theta = 15.0;
         cPanel.repaint();
      }
      
      // fill with wireframe
      if(ev.getSource() == colorButtons[0]){
         for(int i = 0; i < cPanel.isSolid.length; i++){
            cPanel.isSolid[i] = false;
            cPanel.theta = 0.0;
         }
         cPanel.repaint();
      }
      
      // fill with color 
      if(ev.getSource() == colorButtons[1]){
         for(int i = 0; i < cPanel.isSolid.length; i++){
            cPanel.isSolid[i] = true;
            cPanel.theta = 0.0;
         }
         cPanel.repaint();
      }
      
   } // end actionPerformed

}
