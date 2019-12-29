import java.util.*;
import javax.swing.*; 
import java.awt.event.*;
import java.awt.*;
/** 
 * This program finds ONE valid path, not necessarily the fastest, and prints it out.
 * If no paths are found, prints out "NO VALID PATH FOUND!"
 *@author Andrew Spores
 */

public class swingMazeSolver extends JFrame implements ActionListener
{
    JButton submit, restart; JTextField x_input_start, y_input_start, x_input_end, y_input_end ;
    JLabel x_label_start, y_label_start, x_label_end, y_label_end;
    JTextArea mazeDisplay;
    //tile[][] tileSet;
    static int x, xend; //start and endpoint's x-value
    static int y, yend; //start and endpoint's y-value
    
    
    swingMazeSolver() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setTitle("Backtracking Maze Solver");  
        setLocation(250, 150); setSize(1000, 1000); setLayout(null); 

        addButtons(this); addTextFields(this); addLabels(this); addTiles(this);
        setVisible(true);
        
        
    }
    pictureclass[][] tileset = new pictureclass[N][N];
    public void addTiles(JFrame swingMazeSolver)
    {
        
        for(int i=0;i<N;i++)
        {
            for(int j=0; j<N;j++)
            {
                tileset[i][j]= new pictureclass(i, j);
                swingMazeSolver.add(tileset[i][j]); 
            }
            
        }
        
    }

    class pictureclass extends JPanel
	{ 
        int id1, id2;
		private static final long serialVersionUID = 1L;

		public pictureclass(int i, int j)
		{ 
            id1=(i);
            id2=(j);
            setBounds(400+(10*i), 30+(10*j), 10, 10);
            setBackground(Color.LIGHT_GRAY);
        
            this.addMouseListener(new MouseEventListener());

            
        }
        public int getid1(){return id1;}

        public int getid2(){return id2;}   
    }  


    public void addButtons(JFrame swingMazeSolver)
    {
        submit = new JButton("Submit");// create button
        submit.addActionListener(this);
        submit.setBounds(140,300, 100,40);
        swingMazeSolver.add(submit);// adding button on frame

        restart = new JButton("Restart");// create button
        restart.addActionListener(this);
        restart.setBounds(140,350, 100,40);
        swingMazeSolver.add(restart);// adding button on frame
    }

    public void addTextFields(JFrame swingMazeSolver)
    {
        x_input_start=new JTextField();  
        x_input_start.setBounds(300,50, 20,20);
        swingMazeSolver.add(x_input_start);
        
        y_input_start=new JTextField();
        y_input_start.setBounds(300,100, 20,20);
        swingMazeSolver.add(y_input_start);

        x_input_end=new JTextField();  
        x_input_end.setBounds(300,150, 20,20);
        swingMazeSolver.add(x_input_end);
        
        y_input_end=new JTextField();
        y_input_end.setBounds(300,200, 20,20);
        swingMazeSolver.add(y_input_end);
    }

    public void addLabels(JFrame swingMazeSolver)
    {
        x_label_start=new JLabel();
        x_label_start.setText("X-Coordinate of Starting Point: ");
        x_label_start.setBounds(50,50, 200,20);
        swingMazeSolver.add(x_label_start);

        y_label_start=new JLabel();
        y_label_start.setBounds(50,100, 200,20);
        y_label_start.setText("Y-Coordinate of Starting Point: ");
        swingMazeSolver.add(y_label_start);

        x_label_end=new JLabel();
        x_label_end.setBounds(50,150, 200,20);
        x_label_end.setText("X-Coordinate of Ending Point: ");
        swingMazeSolver.add(x_label_end);

        y_label_end=new JLabel();
        y_label_end.setBounds(50,200, 200,20);
        y_label_end.setText("Y-Coordinate of Ending Point: ");
        swingMazeSolver.add(y_label_end);
    }

    public void actionPerformed(ActionEvent e)
	{
        if(e.getSource()==submit)
        { 
            x = Integer.parseInt(x_input_start.getText()); //changes input to int from string
            y = Integer.parseInt(y_input_start.getText());
            xend = Integer.parseInt(x_input_end.getText());
            yend = Integer.parseInt(y_input_end.getText());

            boolean isPath = solveMaze(maze, solution, x, y);
            printSolution(solution, isPath); 
        }
        if(e.getSource()==restart)
        {
            super.dispose();
            new swingMazeSolver();
        }
		
    }
    
    public void addWall(pictureclass pressedButton)
    {
        maze[pressedButton.getid2()][pressedButton.getid1()] = 0;
    }
    
        static boolean mouseHeld = false;
        public class MouseEventListener implements MouseListener
        {
            int timesclicked = 0;
            
            public void mouseClicked(MouseEvent arg0)
            {
                
                
                
            }
            public void mouseEntered(MouseEvent arg0)
            {
                if(mouseHeld)
                {
                    timesclicked += 1;
                    System.out.println("Times activated: "+timesclicked);
                    pictureclass pressedButton = (pictureclass) arg0.getSource();
                    pressedButton.setBackground(Color.RED);
                    addWall(pressedButton);
                }
            }
            public void mouseExited(MouseEvent arg0)
            {
                
            }
            public void mousePressed(MouseEvent arg0)
            {
                mouseHeld = true;
            }
            public void mouseReleased(MouseEvent arg0)
            {
                mouseHeld = false;
            }
        }

        public void drawThoughts(int x, int y)
        {
            tileset[y][x].setBackground(Color.YELLOW);
            repaint();
        }
        
   
 //----------------------------------------------------------------------------------------------

    static int solution[][]; //solution matrix
    static int N; // matrix size
    static int maze[][];
    /**
     * Checks to see if the point in question is valid or invalid
     * @param maze Matrix filled with values of 1 and 0. 1 = valid move, 0 = invalid move 
     * @param x x-coordinate
     * @param y y-coordinate
     * @return  true if point is valid/safe, false if invalid
     */
    
    boolean safe(int maze[][], int x, int y) {
        if (x >= 0 && x < N && y >= 0 && y < N && maze[x][y] == 1)
        { //if point is within matrix and a valid space
            drawThoughts(x,y);
            return true;
        } 
        else 
        {
            return false;
        }
    }
    /**
     * Method to attempt to solve maze
     * @param maze Matrix filled with values of 1 and 0. 1 = valid move, 0 = invalid move 
     * @param solution Solution matrix filled with values of 1 and 0. 1 = valid move, 0 = invalid move 
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if valid solution found, false if no valid solution found
     */
    boolean solveMaze(int maze[][], int solution[][], int x, int y) 
    {
        if (x == xend && y == yend) 
        { //if x and y are equal to the endpoints
            solution[x][y] = 1;
            return true;
        }
        if (safe(maze, x, y) == true) 
        { //if coordinates are safe
            solution[x][y] = 1;

            if (x != N - 1 && solution[x+1][y]!=1 && solveMaze(maze, solution, x + 1, y)) 
            {//checking x+1 to see if its safe AND not part of the solution yet
                return true;
            }
             if (y != N - 1 && solution[x][y+1]!=1&& solveMaze(maze, solution, x, y + 1)) 
            { //checking y+1 to see if its safe AND not part of the solution yet
                return true;
            }
            if (x>0 && solution[x-1][y]!=1 ) 
            {//checking x-1 to see if its safe AND not part of the solution yet
                if(solveMaze(maze, solution, x - 1, y))
                {
                    return true;
                }
            } 
           
            if ( y>0 && solution[x][y-1]!=1 ) 
            { //checking y-1 to see if its safe AND not part of the solution yet
                if(solveMaze(maze, solution, x, y-1))
                {
                    return true;
                }
            }

        }
        solution[x][y] = 0;
        
        return false;
    }

       
    /**
     * Prints solution if valid path found, otherwise prints "NO VALID SOLUTION FOUND!"
     * @param solution matrix containing valid or invalid solution
     * @param isPath is the solution in question is valid or not
     */
    static void printSolution(int solution[][], boolean isPath) 
    {
        if (isPath == true) 
        {
            { 
                System.out.print("  x ");
                for(int i=0; i<N;i++)
                {
                    System.out.print(i+"  ");
                }
                System.out.println();
                System.out.print("y  ");
                for(int i=0;i<N;i++)
                {
                    System.out.print("___");
                }
                System.out.println();
                for (int i = 0; i < N; i++) 
                { 
                    System.out.print(i + "| ");
                    for (int j = 0; j < N; j++) 
                    {
                        System.out.print(" " + solution[i][j] + " "); 
                    } 
                    System.out.println(); 
                } 
            } 
        }
        else 
        {
            System.out.println("NO VALID PATH FOUND!");
        }
    }
    /**
     * Prints out maze for the user to visualize
     * @param maze Matrix filled with values of 1 and 0. 1 = valid move, 0 = invalid move 
     */
    static void printMaze(int maze[][])
    {
        System.out.print("  x ");
        for(int i=0; i<N;i++)
        {
            System.out.print(i+"  ");
        }
        System.out.println();
        System.out.print("y  ");
        for(int i=0;i<N;i++)
        {
            System.out.print("___");
        }
        System.out.println();
        for (int i = 0; i < N; i++) 
        { 
            System.out.print(i + "| ");
            for (int j = 0; j < N; j++)
            {
                System.out.print(" " + maze[i][j] + " "); 
                
            }
            System.out.println();
        }
          
    }

    

    public static void main(String[] args) throws InterruptedException
    {
        
        Scanner cin=new Scanner(System.in); //scanner for input
        System.out.println("Please specify a size: ");
        N=cin.nextInt();
        maze = new int[N][N];
        for (int i = 0; i < N; i++) 
                { 
                    for (int j = 0; j < N; j++) 
                    {
                        maze[i][j]=1;
                    } 
                } 
        solution = new int[N][N];
        new swingMazeSolver();
        
        printMaze(maze); //prints out maze so user can see it
        
         
        
    }
}