/* this is the controller class which connects all
 * the JFrame components with the drawing methods 
 * we set up in the SpiralPanel class. */


import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ControlPanel extends JPanel implements ChangeListener{

   // set up all the JPanel components so the information is
   // shared 
 	SpiralPanel sPanel;
   JSlider lambdaSlider; // slider component to control the spinning
   JButton spiralButton, patternButton; // buttons to activate graphics
   JTextField lambdaInput, squareNumInput, rowsInput, columnsInput;
   
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
    
 	public ControlPanel(SpiralPanel sp)
	{
		sPanel=sp;	
		// set the layout of control panel as
      // 3x1, 3 rows and 1 column
      // so everything should be lined up 
      // vertically
		setLayout(new GridLayout(3,1));
		
      // the panel for the spiral square section
      // this occupies 1/3 of the area of the 
      // overall control frame
      JPanel sb = new JPanel();
      // set up the spiral squares controller panel
      // as 6x1, so all the components there line
      // up vertically
		sb.setLayout(new GridLayout(6,1));
      
      // initialize all the components
      // buttons, textfields and labels 
      lambdaInput = new JTextField();
      spiralButton = new JButton("single");
      JLabel lambdaLabel = new JLabel("lambda:");
      squareNumInput = new JTextField();
      JLabel squareNumLabel = new JLabel("number:");
      
      // add all the components to the spiral square controller panel
      sb.add(lambdaLabel);
    	sb.add(lambdaInput);
      sb.add(squareNumLabel);
      sb.add(squareNumInput);
      sb.add(spiralButton);
            
		add(sb); // add spiral squares controller panel to the main controller panel
      
      // this is what happens when the button gets pressed
      // it is going to check what are inside of the textfields
      // that take lambda and the number of squares. It will
      // check if all the inputs are valid, then proceed to
      // change the corresponding fields in sPanel
      spiralButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(e.getSource() == spiralButton){
                  
                  int sqnum = 0;
                  double lamb = 0;
                  
                  // if the we get a valid input from the square number
                  // text field 
                  if(!squareNumInput.getText().isEmpty() && 
                           isNumeric(squareNumInput.getText())){
                           
                     sqnum = Integer.parseInt(squareNumInput.getText());
                  }
                  
                  // same thing to check if lambda is a valid value 
                  if(!lambdaInput.getText().isEmpty()&& 
                           isNumeric(lambdaInput.getText())){
                           
                     double lambdaFromText = Double.parseDouble(lambdaInput.getText());
                     // make sure lambda is from 0 to 1, values greater than 1 or less
                     // than 0 will be considered invalid 
                     if(lambdaFromText >= 0 && lambdaFromText <= 1){
                        lamb = lambdaFromText;
                     }
                  }
                  
                  // if all the input values are valid, and the spiral squares button
                  // is pressed. We update all the corresponding fields and repaint.
                  sPanel.lambda = lamb;
                  sPanel.squareNum = sqnum;
                  sPanel.isPattern = 0;
                  sPanel.repaint();
                }
            }
      });

      
      // basically now we are doing the same thing for the pattern squares 
      // controller except that different button is pressed and the text
      // inputs this time are the number of rows and columns of the grid
      
	   JPanel pb = new JPanel();
		pb.setLayout(new GridLayout(6,1));
      
      // similar set up process as the spiral square controller panel
      rowsInput = new JTextField();
      patternButton = new JButton("pattern");
      JLabel rowsLabel = new JLabel("rows:");
      columnsInput = new JTextField();
      JLabel columnsLabel = new JLabel("columns:");
      
      pb.add(rowsLabel);
    	pb.add(rowsInput);
      pb.add(columnsLabel);
      pb.add(columnsInput);
      pb.add(patternButton);		
      
      add(pb);
      
      // similar function to indicate what will happen when
      // the pattern squares button is pressed 
      patternButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(e.getSource() == patternButton){
                  
                  int r = 0, c = 0;
                  double lamb = 0.0;
                  
                  // check the number of rows input is valid
                  if(!rowsInput.getText().isEmpty() && 
                           isNumeric(rowsInput.getText())){
                           
                     r = Integer.parseInt(rowsInput.getText());
                  }
                  // check if the number of columns input is valid 
                  if(!columnsInput.getText().isEmpty()&& 
                           isNumeric(columnsInput.getText())){
                           
                     c = Integer.parseInt(columnsInput.getText());
                  }
                  
                  // same thing to check if lambda is a valid value 
                  // make sure when the pattern button is pressed. 
                  // it also knows to update the lambda value if the
                  // user has assigned a new one
                  if(!lambdaInput.getText().isEmpty()&& 
                           isNumeric(lambdaInput.getText())){
                           
                     double lambdaFromText = Double.parseDouble(lambdaInput.getText());
                     // make sure lambda is from 0 to 1, values greater than 1 or less
                     // than 0 will be considered invalid 
                     if(lambdaFromText >= 0 && lambdaFromText <= 1){
                        lamb = lambdaFromText;
                     }
                  }
                  
                  // update the sPanel if all the text inputs are valid
                  // and this time since we want a pattern, isPattern should
                  // be set as 1 to activate drawPatternSquares method inside
                  // paintComponent
                  sPanel.rows = r;
                  sPanel.columns = c;
                  sPanel.isPattern = 1;
                  sPanel.lambda = lamb;
                  sPanel.repaint();
                }
            }
      });
  
      // set up the slider to control lambda of the spiraling squares 
      JPanel sliderPanel = new JPanel();
      // the slider should be laid out vertically
      sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
      lambdaSlider = new JSlider(JSlider.VERTICAL, 0, 400, 0);
      // this is important, otherwise we won't know if the user has
      // ever scroll the slider
      lambdaSlider.addChangeListener(this); 
      lambdaSlider.setPaintLabels(true);
      JLabel sliderLabel = new JLabel("spinning");
      sliderPanel.add(sliderLabel);
      sliderPanel.add(lambdaSlider);
            
		add(sliderPanel);
          
   }//end contructor
   
   public void stateChanged(ChangeEvent ev){
  	  // constanly changing the values of lambda as
     // the user is scrolling the sliders 
     // since we set the range of the slider from 0-400
     // however, the value of lambda is from 0-1, so 
     // we have to scale the range of the sliders to 0-1
     sPanel.lambda = lambdaSlider.getValue() / 400.0;
     sPanel.repaint();
     
	}//end stateChanged
   
   
}