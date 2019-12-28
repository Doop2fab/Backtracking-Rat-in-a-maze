import javax.swing.*; 
import java.awt.event.*;
import java.awt.*;
/** 
 * This program finds ONE valid path, not necessarily the fastest, and prints it out.
 * If no paths are found, prints out "NO VALID PATH FOUND!"
 *@author Andrew Spores
 */

public class mazeViewer extends JFrame 
{

    Color openSpace = Color.LIGHT_GRAY;
    Color closedSpace = Color.DARK_GRAY;
    //tile[][] spaces;

    mazeViewer() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setTitle("Maze");  
        setLocation(150, 150); setSize(1000, 1000); setLayout(null); 

        addTiles(this);

        setVisible(true);

        
    }
    public void addTiles(JFrame mazeViewer)
    {
        pictureclass tileSet = new pictureclass();
        mazeViewer.add(tileSet);
        repaint();
    }
    
    class pictureclass extends JPanel implements ActionListener
	{
        

		public pictureclass()
		{ setSize(2000,2000); }
		
		public void paintComponent(Graphics g)
		{
            JButton[][] tiles = new JButton[swingMazeSolver.N][swingMazeSolver.N];
			g.setColor(Color.cyan);
            g.fillRect(0,0, 2000,2000);

            for(int i=0; i<swingMazeSolver.N;i++)
            {
                for(int j=0; j<swingMazeSolver.N;j++)
                {
                    tiles[i][j]= new JButton();
                    tiles[i][j].addActionListener(this);
                    if(swingMazeSolver.maze[j][i] == 1)
                    {
                        tiles[i][j].setBackground(Color.GREEN);
                    }
                    else if(swingMazeSolver.maze[j][i] == 0)
                    {
                        tiles[i][j].setBackground(Color.RED);
                    }
                    tiles[i][j].setBounds(100*i, 100*j, 100, 100);
                    tiles[i][j].setText(""+i+" "+j);
                    add(tiles[i][j]);
                    repaint();
                }
            }
        }

        public void actionPerformed(ActionEvent e)
	    {
            JButton pressedButton = (JButton) e.getSource();
            
            String buttonID = pressedButton.getText();
            String[] coordinates = buttonID.split(" "); // splitting string by spaces
            int xButton = Integer.parseInt(coordinates[0]);
            int yButton = Integer.parseInt(coordinates[1]);
            if(swingMazeSolver.maze[yButton][xButton] == 0)
            {
                swingMazeSolver.maze[yButton][xButton] = 1;
                pressedButton.setBackground(Color.GREEN);
            }
            else if(swingMazeSolver.maze[yButton][xButton] == 1)
            {
                swingMazeSolver.maze[yButton][xButton] = 0;
                pressedButton.setBackground(Color.RED);
            }
           
        }
	}
}