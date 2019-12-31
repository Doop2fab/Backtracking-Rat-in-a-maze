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
    JButton submit, reset, presets, preset1, preset2, preset3, empty;
    JDialog pathError;
    JLabel messageToUser=new JLabel();
    JLabel mazeSolverDescription;
    JFrame presetWindow;
    Color background = new Color(50, 50, 63);
    static int x=0, xend; //start and endpoint's x-value
    static int y=0, yend; //start and endpoint's y-value
    static boolean mouseHeld = false;
    private static final long serialVersionUID = 1L;
    
    swingMazeSolver() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setTitle("Backtracking Maze Solver");  
        setLocation(0, 0); setSize(1080, 1920); setLayout(null);
        
        
        addButtons(this);
        addTiles(this);
        addTextArea(this);
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
        swingMazeSolver.add(new pictureclass(410, 20, 620, 620));
    }

    public void redrawTiles()
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0; j<N;j++)
            {
                if(maze[i][j]==0)
                {
                    tileset[j][i].setBackground(Color.DARK_GRAY);
                }
                else{tileset[j][i].setBackground(Color.LIGHT_GRAY);}
            }
        }
        tileset[0][0].setBackground(Color.MAGENTA);
        tileset[N-1][N-1].setBackground(Color.PINK);
        repaint();
    }

    class pictureclass extends JPanel
	{ 
        int id1, id2;
		private static final long serialVersionUID = 1L;

		public pictureclass(int i, int j)
		{ 
            id1=(i);id2=(j);
            setBounds(420+(60*i), 30+(60*j), 60, 60);
            if(maze[i][j]==0)
            {
                setBackground(Color.DARK_GRAY);
            }
            else{setBackground(Color.LIGHT_GRAY);}
            this.addMouseListener(new MouseEventListener());
        }
        pictureclass(int x, int y, int width, int height)
        {
            setBounds(x,y, width, height);
            setBackground(background);
        }

        public int getid1(){return id1;}
        public int getid2(){return id2;}   
    }  

    public void addButtons(JFrame swingMazeSolver)
    {
        submit = new JButton("Submit");// create button
        submit.addActionListener(this);
        submit.setBounds(50,20, 300,75);
        swingMazeSolver.add(submit);// adding button on frame

        reset = new JButton("Reset");// create button
        reset.addActionListener(this);
        reset.setBounds(50,100, 300,75);
        swingMazeSolver.add(reset);// adding button on frame

        presets = new JButton("Presets");// create button
        presets.addActionListener(this);
        presets.setBounds(50,180, 300,75);
        swingMazeSolver.add(presets);// adding button on frame
    }

    public void addPresetButtons(JFrame presetWindow)
    {
        
        preset1 = new JButton("Preset 1");// create button
        preset1.addActionListener(this);
        preset1.setBounds(50,20, 300,75);
        presetWindow.add(preset1);// adding button on frame
        
        preset2 = new JButton("Preset 2");// create button
        preset2.addActionListener(this);
        preset2.setBounds(50,100, 300,75);
        presetWindow.add(preset2);// adding button on frame
        
        preset3 = new JButton("Preset 3");// create button
        preset3.addActionListener(this);
        preset3.setBounds(50,180, 300,75);
        presetWindow.add(preset3);// adding button on frame

        empty = new JButton("Empty Maze");// create button
        empty.addActionListener(this);
        empty.setBounds(50,260, 300,75);
        presetWindow.add(empty);// adding button on frame
    }

    public void addLabels(JFrame swingMazeSolver, String messageToUserText)
    {
        
        messageToUser.setBounds(60,225, 300,100);
        messageToUser.setText(messageToUserText);
        messageToUser.setForeground(Color.RED);
        messageToUser.setFont(messageToUser.getFont().deriveFont(17.0f));
        swingMazeSolver.add(messageToUser);
        repaint();
        
    }

    public void addTextArea(JFrame swingMazeSolver)
    {
        String labelContent = 
        "<html><div style='text-align: center;'>Welcome to my Rat-in-a-Maze solver that implements backtracking using recursion!"+
        " Please click and drag where you would like to place walls."+
        " Also, ensure each space is either a <u>path</u> <font color='#D3D3D3'>(Light Gray)</font> or a <u>wall</u> <font color='#A9A9A9'>(Dark Gray)</font>,"+
        " and there are no 2x2 spots or larger of path. If you make a mistake,"+
        " click the Reset button.</html>";

        mazeSolverDescription=new JLabel();
        mazeSolverDescription.setBounds(50,310, 300,300);
        mazeSolverDescription.setBackground(new Color(17, 37, 64));
        mazeSolverDescription.setForeground(Color.WHITE);
        mazeSolverDescription.setOpaque(true); //why are JLabels transparent by default anyways?
        mazeSolverDescription.setText(labelContent);
        mazeSolverDescription.setFont(mazeSolverDescription.getFont().deriveFont(20.0f));
        swingMazeSolver.add(mazeSolverDescription); //info box
        swingMazeSolver.add(new pictureclass(40,300, 320,320)); //background of info box
        repaint();
    }
    
    public void actionPerformed(ActionEvent e)
	{
        if(e.getSource()==submit)
        {
            boolean isPath = solveMaze(maze, solution, x, y, "down");
            printSolution(isPath); 
        }
        if(e.getSource()==empty||e.getSource()==reset)
        {
            fillMaze(1);
            redrawTiles();
            messageToUser.setText("");
        }
        if(e.getSource()==presets)
        {
            presetWindow = new JFrame("Maze Presets");
            presetWindow.setSize(400, 600);
            presetWindow.setLayout(null);
            addPresetButtons(presetWindow);
            presetWindow.setVisible(true);
        }
        if(e.getSource()==preset1)
        {
            int newMaze[][]={{1,0,1,0,1,0,1,1,1,1},
                             {1,1,1,1,1,1,1,0,0,0},
                             {0,0,0,0,1,0,0,0,1,0},
                             {1,1,1,1,1,1,1,1,1,1},
                             {1,0,0,0,0,0,0,1,0,0},
                             {1,1,1,1,1,1,1,1,1,1},
                             {1,0,1,0,1,0,0,0,0,1},
                             {1,0,1,0,1,1,0,1,0,0},
                             {1,0,0,0,0,1,0,1,1,1},
                             {1,1,1,1,0,1,1,1,0,1}};
                maze=newMaze;
                redrawTiles();
        }	
        if(e.getSource()==preset2)
        {
            
        }	
        if(e.getSource()==preset3)
        {
            
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