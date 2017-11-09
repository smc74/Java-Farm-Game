import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
//Notes: JGameCellButton
//Status:Unused
//I wanted to place this in the game board grid.  Each button when click would
//know the address in the game it represented.  The GridBagLayout doesn't
//refresh properly when I do this.  It only refreshes as I pass the mouse over
//the grid, probably it needs to be registered as a listener or something like that. 
public class JGameCellButton extends JButton
{
	int xCoord = 0;
	int yCoord = 0;
	
//	JGameCellButton(ImageIcon image,int pX,int pY)
//	{
//		super(image);
//		xCoord=pX;
//		yCoord=pY;
//	}

	public void setX(int pX)
	{
		xCoord=pX;
	}
	
	public void setY(int pY)
	{
		yCoord=pY;
	}
	
	public int getX()
	{
		return xCoord;
	}
	
	public int getY()
	{
		return yCoord;
	}
	
}