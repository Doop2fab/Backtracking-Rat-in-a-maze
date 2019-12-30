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
    JButton submit, reset;
    JLabel messageToUser;
    static int x=0, xend; //start and endpoint's x-value
    static int y=0, yend; //start and endpoint's y-value
    static boolean mouseHeld = false;
    
    
    swingMazeSolver() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setTitle("Backtracking Maze Solver");  
        setLocation(10, 10); setSize(1080, 1920); setLayout(null); 

        addButtons(this); 
        addTiles(this);
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
        tileset[0][0].setBackground(Color.MAGENTA);
        tileset[N-1][N-1].setBackground(Color.PINK);
    }

    class pictureclass extends JPanel
	{ 
        int id1, id2;
		private static final long serialVersionUID = 1L;

		public pictureclass(int i, int j)
		{ 
            id1=(i);id2=(j);
            setBounds(420+(60*i), 10+(60*j), 60, 60);
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

        reset = new JButton("Reset");// create button
        reset.addActionListener(this);
        reset.setBounds(140,350, 100,40);
        swingMazeSolver.add(reset);// adding button on frame
    }

    public void addLabels(JFrame swingMazeSolver, String messageToUserText)
    {
        messageToUser=new JLabel();
        messageToUser.setBounds(100,200, 200,20);
        messageToUser.setText(messageToUserText);
        swingMazeSolver.add(messageToUser);
        repaint();
    }

    public void actionPerformed(ActionEvent e)
	{
        if(e.getSource()==submit)
        {
            boolean isPath = solveMaze(maze, solution, x, y, "down");
            printSolution(isPath); 
        }
        if(e.getSource()==reset)
        {
            super.dispose();
            fillMaze(1);
            new swingMazeSolver();
        }	
    }
    
    public void addWall(pictureclass pressedButton)
    {
        maze[pressedButton.getid2()][pressedButton.getid1()] = 0;
    }
    
    public class MouseEventListener implements MouseListener
    {
        public void mouseClicked(MouseEvent arg0)
        {
            
        }
        public void mouseEntered(MouseEvent arg0)
        {
            if(mouseHeld)
            {
                pictureclass pressedButton = (pictureclass) arg0.getSource();
                pressedButton.setBackground(Color.DARK_GRAY);
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

    public void drawThoughts()
    {
        for(int i=0; i<N;i++)
        {
            for(int j=0; j<N; j++)
            {
                if(solution[i][j]==1)
                {
                    tileset[j][i].setBackground(Color.GRAY);
                    repaint();
                }
            }
        }   
    }

    static void fillMaze(int value)
    {
        solution = new int[N][N];
        maze = new int[N][N];
        for (int i = 0; i < N; i++) 
            { 
                for (int j = 0; j < N; j++) 
                {
                    maze[i][j]=value;
                } 
            } 
    }
 //----------------------------------------------------------------------------------------------

    static int solution[][]; //solution matrix
    static int visited[][]; //solution matrix
    static int N=10; // matrix size
    static int maze[][];
    static String direction;
    /**
     * Checks to see if the point in question is valid or invalid
     * @param maze Matrix filled with values of 1 and 0. 1 = valid move, 0 = invalid move 
     * @param x x-coordinate
     * @param y y-coordinate
     * @return  true if point is valid/safe, false if invalid
     */
    
    boolean safe(int maze[][], int x, int y) {
        if (x >= 0 && y >= 0 && x < N  && y < N && maze[x][y] != 0)
        { //if point is within matrix and a valid space
            
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
    boolean solveMaze(int maze[][], int solution[][], int x, int y, String direction) throws StackOverflowError
    {
        try
        {
            if (x == N-1 && y == N-1&&safe(maze, x, y)) 
            { //if x and y are equal to the endpoints
                solution[x][y] = 1;
                return true;
            }
            if (safe(maze, x, y) == true) 
            { //if coordinates are safe
                solution[x][y] = 1;
    
                if (direction!="up" && solveMaze(maze, solution, x + 1, y, "down")) 
                {//checking x+1 to see if its safe AND not part of the solution yet
                    
                    return true;
                }
                 if (direction!="left"&& solveMaze(maze, solution, x, y + 1, "right")) 
                { //checking y+1 to see if its safe AND not part of the solution yet
                    
                    return true;
                }
                if (direction!="down" && solveMaze(maze, solution, x - 1, y, "up" )) 
                {//checking x-1 to see if its safe AND not part of the solution yet
                    
                    return true;
                } 
                if (direction!="right" && solveMaze(maze, solution, x, y-1, "left")) 
                { //checking y-1 to see if its safe AND not part of the solution yet
                    
                    return true;
                } 
                solution[x][y] = 0;
                return false; 
            }
            return false;
        }
        catch(StackOverflowError e)
        {
            fillMaze(0);
            addLabels(this,"NO PATH FOUND, PLEASE RESET!" );
            return true;
        }
    }

    /**
     * Prints solution if valid path found, otherwise prints "NO VALID SOLUTION FOUND!"
     * @param solution matrix containing valid or invalid solution
     * @param isPath is the solution in question is valid or not
     */
    void printSolution(boolean isPath) 
    {
        if (isPath == true) 
        {
            drawThoughts();     
        }
        else 
        {
            addLabels(this,"START BLOCKED, PLEASE RESET!" );
        }
    }
    /**
     * Prints out maze for the user to visualize
     * @param maze Matrix filled with values of 1 and 0. 1 = valid move, 0 = invalid move 
     */
    
    public static void main(String[] args) throws InterruptedException
    {
        fillMaze(1);
        new swingMazeSolver();
    }
}