import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Washer extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		System.out.println("Hasta la Vista, baby!");
		System.exit(0);
	}
}


public class gui7 extends JFrame implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JButton change, swim;
	JTextArea input;
	pictureclass tank;
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==swim)
		{
			for(int i=0; i<school.length; i++)
				school[i].swim();
		}
		if(e.getSource()==change)
		{
			int fishid
			  =Integer.parseInt(input.getText());
			if(0<=fishid && fishid<school.length)
				school[fishid].change();
		}
		repaint();
	}
	
	private static int random(int low, int hi)
	{
		return low+(int)(Math.random()*(hi-low));
	}
	
	class fish
	{
		int x, y;
		int length;
		int height;
		Color color;
		
		public void swim()
		{
			x=x+2+length/10;
			if(x+length>700) x= -length;
		}
		
		public fish(int xin, int yin, int l, int h, Color c)
		{
			x=xin; y=yin; length=l; height=h;
			color=c;
		}
		public fish() // random fish
		{
			x=random(0,650);
			y=random(0,450);
			length=random(10,100);
			height=length/2;
			color=new Color( random(0,255),
			  random(0,255), random(0,255) );
		}
		
		void change() // assigns new random color
		{
			color=new Color( random(0,255),
			  random(0,255), random(0,255) );
		}
		
		void draw(Graphics g)
		{
			g.setColor(color);
			g.fillOval(x+length/4,y, 3*length/4,height);
			int [] xs={x, x+2*length/5, x};
			int [] ys={y, y+height/2, y+height};
			g.fillPolygon(xs, ys, 3);
			
			g.setColor(Color.white);
			g.fillOval(x+3*length/4,y+height/4,
			  height/4,height/4);
			g.setColor(Color.black);
			int ew=2;
			g.fillOval(x+3*length/4+ew+2,y+height/4+ew,
			  height/4-2*ew,height/4-2*ew);
		}
	}
	
	fish [] school;
	
	class pictureclass extends JPanel
	{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public pictureclass()
		{ setSize(700,500); }
		
		public void paintComponent(Graphics g)
		{
			g.setColor(Color.cyan);
			g.fillRect(0,0, 700,500);
			for(int i=0; i<school.length; i++)
				school[i].draw(g);
		}
	}
	
	public gui7()
	{
		addWindowListener( new Washer() );
		setTitle("The Ocean");
		setSize(900,700); // in pixels
		
		school=new fish[24];
		for(int i=0; i<school.length; i++)
			school[i]=new fish();
		
		Container malcolm=getContentPane();
		malcolm.setLayout( new BorderLayout() );
		
		tank=new pictureclass();
		malcolm.add( tank,"Center");
		
		change=new JButton("Change");
		change.addActionListener(this);
		swim=new JButton("Swim");
		swim.addActionListener(this);
		
		input=new JTextArea("0");
		malcolm.add( swim,"East");
		malcolm.add( change, "West");
		malcolm.add( input, "South");
		
		setVisible(true);
	}
	
	public static void main(String [] args)
	{
		gui7 peter=new gui7();	
	}
}
