/*
 * SpiralPanel.java
 * this is the canvas for all the drawings
 * including two options:
 * spiraling squares (single): one big square in the outside
 * and smaller squares spiraling within that big square
 * 
 * spiraling squares (pattern): a grid of squares with half
 * of them rotating clockwise and the other half rotating
 * counterclockwise
*/

/* Swing library */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.Math;

public class SpiralPanel extends JPanel{

   /* values that will be inputed from spiraling squares(single) */
   
   // lambda is the value that controls
   // the spiraling angles
   public double lambda;  
   // number of squares for the single spiraling squares
   public int squareNum;
   
   /* values that will be inputed from spiraling squares(pattern) */

   // the number of rows and columns for grid that you are putting
   // the squares in 
   public int rows, columns;
   // make sure which button the user is pressing
   public int isPattern; 
   
   // the coordinates of the square
   // note: the coordinates will be kept updated as you 
   // draw more squares. 
   // both "single" and "pattern" will utilize the coordinates
   public Point2D.Double[] coordinates;
   
   final int HEIGHT = 600; // height of the drawing canvas
   final int WIDTH = 600; // width of the drawing canvas 

   public SpiralPanel (){
   
	   //The following is another way to guarantee correct size.
	   setPreferredSize(new Dimension(HEIGHT, WIDTH));		
      setBackground(Color.white); 
         
      // the default values for all the text inputs
      // to prevent breaking the program 
      lambda = 0;
      squareNum = 0;
      
      rows = 0;
      columns = 0;
      
      // give a riculous value just so when buttons are
      // not clicked, nothing is going to happen
      isPattern = 999;
              
   } //end SpiralPanel
   
         
   // vector addition: V = V1 + V2
   public Point2D.Double add(Point2D.Double v1, Point2D.Double v2){
      return new Point2D.Double(v1.getX()+v2.getX(), v1.getY()+v2.getY());
   }
   
   // vector subtraction: V = V1 - V2
   public Point2D.Double subtract(Point2D.Double v1, Point2D.Double v2){
      return new Point2D.Double(v1.getX()-v2.getX(), v1.getY()-v2.getY());
   }
   
   // vector multiplication: V = lambda * V1
   public Point2D.Double multiply(Point2D.Double v, double lamb){
      return new Point2D.Double(lamb*v.getX(), lamb*v.getY());
   }
   
   /* this function is how we draw a general square, we are given the
    * coordinates of the four verices of a square, as Path2D object. 
    * then we basically connect those four coordiates to make it a 
    * square. We assume that all the given coordinates are right 
    * representations of a square's verices  */
   public void drawSquare(Graphics2D g2d, Point2D.Double[] coordinates){
   
      Path2D.Double squares = new Path2D.Double();
      squares.moveTo(coordinates[0].getX(), coordinates[0].getY());
      squares.lineTo(coordinates[1].getX(), coordinates[1].getY());
      squares.lineTo(coordinates[2].getX(), coordinates[2].getY());
      squares.lineTo(coordinates[3].getX(), coordinates[3].getY());
      squares.closePath();
      
      // paint the square top to bottom from white to black to add some 
      // artistic effects 
      GradientPaint squarePaint = new GradientPaint((float)coordinates[0].getX(), 
         (float)coordinates[0].getY(), Color.white, (float)coordinates[3].getX(), 
         (float)coordinates[3].getY(), Color.black);
      
      g2d.setPaint(squarePaint);
      g2d.fill(squares);
      g2d.draw(squares);

   } // end drawSquare
   
   
   /* this function is pretty useful when we are doing vection operations
    * basically we are getting the four vectors from the four vertices
    * of the square. As we obtain this vection, we are free to get any
    * point along the line P0 + tV */
   public Point2D.Double[] get4Vectors(Point2D.Double[] coordinates){
   
      Point2D.Double v1, v2, v3, v4;
      
      // first side of the square (p2 - p1): top
      v1 = subtract(coordinates[1], coordinates[0]);
      // second side of the square (p3 - p2): right
      v2 = subtract(coordinates[2], coordinates[1]);
      // third side of the square (p4 - p3): bottom
      v3 = subtract(coordinates[3], coordinates[2]);
      // forth side of the square (p1 - p4): left
      v4 = subtract(coordinates[0], coordinates[3]);
      
      return new Point2D.Double[]{v1, v2, v3, v4};
   } // end get4Vector
   
   /* this function is how to twist the coordinates as we want to 
    * make spiraling squares. What it does is it gets the current
    * coordinates of the square, as well as the lambda factor, which
    * essentially decides the angle that the spiraling square should
    * rotate. Then by using the line formula P0 + lambda*V, it computes
    * the coordinates of the new square. Finally it update the new
    * coordiantes */
   public void twistCoordinates(Point2D.Double[] coordinates, 
      Point2D.Double[] V, double lambda){
      
      // now we should update our coordinates based on lambda
      // use the affine formula: P = (1-lambda)*P0 + lambda*P1
      coordinates[0] = add(coordinates[0], multiply(V[0], lambda));
      coordinates[1] = add(coordinates[1], multiply(V[1], lambda));
      coordinates[2] = add(coordinates[2], multiply(V[2], lambda));
      coordinates[3] = add(coordinates[3], multiply(V[3], lambda));
   
   } // end twistCoordinates
                
   /* main method for drawing the nested spiraling squares
    * now we all the functions that are needed in order to 
    * draw nested squares. We just use a for-loop to draw
    * whatever we have in the coordinates field, given the
    * specific number of squares user determines. */ 
   public void drawNestedSquares(Graphics2D g2d, Point2D.Double[] coordinates){
            
      for (int i = 0; i < squareNum; i++){
                  
         // draw whatever is there in the coordinates field as
         // a square.
         drawSquare(g2d, coordinates); 
         // get the four vectors from the present coordinates         
         Point2D.Double[] V = get4Vectors(coordinates);
         // twist the next square based on lambda as the line
         // formula P0 + tV indicates. Now the coordinates should
         // be fully updated. The next twisted square should be 
         // ready to set up.
         twistCoordinates(coordinates, V, lambda);
                           
      }
      
   } // end drawNestedSquares
   
   /* given the coordinate of the top left vertex of a square and given length
    * of the side, return the full coordiantes of all the vertices of the square */
   public Point2D.Double[] produceCoordinates(Point2D.Double p0, double len){
      
      Point2D.Double p1 = new Point2D.Double(p0.getX()+len, p0.getY());
      Point2D.Double p2 = new Point2D.Double(p0.getX()+len, p0.getY()+len);
      Point2D.Double p3 = new Point2D.Double(p0.getX(), p0.getY()+len);
    
      return new Point2D.Double[]{p0, p1, p2, p3};  
   } // end produceCoordinates
   
   /* this is the second drawing method we use to draw a grid of squares.
    * with the one corresponding to black in the checkerboard rotating
    * clockwise and the one corresponding to red rotating counterclockwise.
    * based on the given number of rows and columns, it should be able to
    * automatically decide the orientation of the grid.  */
   public void drawPatternSquares(Graphics2D g2d){
   
      // the current coordinate of the top left corner of the square
      double x, y; 
      // the length of individual square
      double len;
      
      // this decide the orientation of the grid
      // since the dimension of the grid is fixed
      // the length will be determined by whichever
      // has a greater amount between rows and columns
      if(rows >= columns){
         len = HEIGHT*1.0 / rows;
      }else{
         len = HEIGHT*1.0 / columns;
      }
      
      for(int r = 0; r < rows; r++){
         for(int c = 0; c < columns; c++){
            
            // update the coordinates of the top left corners
            x = r*len;
            y = c*len;
            
            Point2D.Double p = new Point2D.Double(x, y);
            // since we have the top left coordinate as well as the length
            // we get the full coordinates of all vertices 
            Point2D.Double[] gridCoordinates = produceCoordinates(p, len);
            // since we have the full coordinates, we will be able to get 
            // all the side vectors in order to produce the coordinates of
            // the twisted square
            Point2D.Double[] v = get4Vectors(gridCoordinates);
            // draw the grid 
            drawSquare(g2d, gridCoordinates);
            
            if((r%2) == (c%2)){
               // update the twisted square clockwise
               twistCoordinates(gridCoordinates, v, lambda);
            }else{
               // update the twisted square counterclockwise
               twistCoordinates(gridCoordinates, v,  1-lambda);
            }
            // draw the twisted square 
            drawNestedSquares(g2d, gridCoordinates); 
         }
      }
   
   } // end drawPatternSquares  
  
          
   public void paintComponent(Graphics g){
    
	   super.paintComponent(g);  //without this no background color set.
		Graphics2D g2d = (Graphics2D)g; //cast so we can use JAVA2D.
      
      if(isPattern == 0){
         /* if user clicks the spiral square button */
         g2d.translate(getWidth()/2,getHeight()/2);
         g2d.scale(1,-1);      
         /* set up the initial coordinates given by the dimension of the canvas */ 
         coordinates = new Point2D.Double[] {new Point2D.Double(-WIDTH/2,HEIGHT/2),
            new Point2D.Double(WIDTH/2, HEIGHT/2), new Point2D.Double(WIDTH/2, -HEIGHT/2), 
            new Point2D.Double(-WIDTH/2, -HEIGHT/2)};
         // draw nested squares
         drawNestedSquares(g2d, coordinates);
         
      }else if(isPattern == 1){
         /* if user clicks the pattern squares button */
         drawPatternSquares(g2d);
         
      }
     
   } // end paintComponent


}