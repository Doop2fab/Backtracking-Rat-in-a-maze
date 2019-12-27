import java.util.*;
/** 
 * This program finds ONE valid path, not necessarily the fastest, and prints it out.
 * If no paths are found, prints out "NO VALID PATH FOUND!"
 *@author Andrew Spores
 */

public class omnidirectional 
{
    static int N; // matrix size
    static int xend; //endpoint's x-value
    static int yend; //endpoint's y-value
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
            
            if ( y>0 && solution[x][y-1]!=1 ) 
            { //checking y-1 to see if its safe AND not part of the solution yet
                if(solveMaze(maze, solution, x, y-1))
                {
                    return true;
                }
            }
            if (y != N - 1 && solution[x][y+1]!=1&& solveMaze(maze, solution, x, y + 1)) 
            { //checking y+1 to see if its safe AND not part of the solution yet
                return true;
            }
            if (x != N - 1 && solution[x+1][y]!=1 && solveMaze(maze, solution, x + 1, y)) 
            {//checking x+1 to see if its safe AND not part of the solution yet
                return true;
            }
            if (x>0 && solution[x-1][y]!=1 ) 
            {//checking x-1 to see if its safe AND not part of the solution yet
                if(solveMaze(maze, solution, x - 1, y))
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
                for (int i = 0; i < N; i++) 
                { 
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

    static void printMaze(int maze[][])
    {
        for (int i = 0; i < N; i++) 
        { 
            for (int j = 0; j < N; j++)
            {
                System.out.print(" " + maze[i][j] + " "); 
                
            }
            System.out.println();
        }    
    }

    public static void main(String[] args) 
    {
        omnidirectional path = new omnidirectional();
        int maze[][] = { { 1, 1, 1, 0, 1 }, //maze matrix
                         { 1, 0, 1, 1, 1 },
                         { 1, 0, 1, 0, 1 }, 
                         { 1, 0, 1, 0, 1 }, 
                         { 1, 1, 1, 0, 1 } };

        N = maze.length;
        printMaze(maze); //prints out maze so user can see it
        Scanner cin=new Scanner(System.in); //scanner for input

        System.out.println("Please specify a startpoint (i.e. 2 3): ");
        int x=cin.nextInt();
        int y=cin.nextInt();

        System.out.println("Please specify an endpoint (i.e. 2 3): ");
        xend=cin.nextInt();
        yend=cin.nextInt();

        //starting point. End point is bottom right corner
        int solution[][] = new int[N][N]; //solution matrix

        boolean isPath = path.solveMaze(maze, solution, x, y);

        printSolution(solution, isPath);
    }
}