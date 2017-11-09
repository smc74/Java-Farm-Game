import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class JGameOverDialog extends JDialog implements ActionListener, WindowListener
{
	JButton buttonQuit;
	JButton buttonNewGame;
	JPanel buttonPanel;
	JLabel messageLabel;
	JLabel gameOverLabel;
	JPanel messagePanel;
	JPanel gameOverPanel;
	String message[];
	GridBagConstraints gbcMessagePanel;
	MultiWindowGameConsole callingObject;
	Container c;
	
	
	JGameOverDialog(MultiWindowGameConsole pCallingObject,String pMessage[])
	{
		
		super((JFrame)pCallingObject,"Game Over",true);
		callingObject=pCallingObject;
		c=getContentPane();
		c.setLayout(new BorderLayout());
		message=pMessage;
		
		messagePanel = new JPanel();
		messagePanel.setLayout(new GridBagLayout());
		
		gbcMessagePanel=new GridBagConstraints();
		gbcMessagePanel.fill = GridBagConstraints.HORIZONTAL;
		
		
		buttonQuit=new JButton("Quit");
		buttonNewGame=new JButton("New Game");
		
		
		gameOverPanel=new JPanel();
		
		buttonPanel = new JPanel();
		buttonPanel.add(buttonQuit);
		//buttonPanel.add(buttonNewGame);  //needs repair
				
		buttonQuit.addActionListener(this);
		buttonNewGame.addActionListener(this);
		
		addWindowListener(this);
		
		gameOverLabel=new JLabel("GAME OVER");
		gameOverPanel.add(gameOverLabel);
		//load the multiline message to the message panel
		//gbcMessagePanel.weightx=0.5;
		gbcMessagePanel.gridx=0;
		gbcMessagePanel.gridy=0;
		gbcMessagePanel.gridwidth=1;
		gbcMessagePanel.insets = new Insets(0,0,0,0);
		for (int msgIndex=0; msgIndex<message.length; msgIndex++   )
		{
			gbcMessagePanel.gridy=msgIndex;
			messagePanel.add(new JLabel(message[msgIndex]),gbcMessagePanel);
		}
		c.add(gameOverPanel,BorderLayout.NORTH);
		c.add(messagePanel,BorderLayout.CENTER);		
		c.add(buttonPanel,BorderLayout.SOUTH);
		
		setSize(700,250);
		setVisible(true);

	}
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==buttonQuit )
		{
			callingObject.exitGame();	
		}
		
		if (e.getSource()==buttonNewGame )
		{
			callingObject.resetGame();	
		}
		
	}
	
	public void windowOpened(WindowEvent e)
	{
	}	
	
	public void windowClosing(WindowEvent e)
	{
		callingObject.exitGame();
		dispose();	
	}	

	public void windowClosed(WindowEvent e)
	{	
	}	

	public void windowIconified(WindowEvent e)
	{		
	}	
	
	public void windowDeiconified(WindowEvent e)
	{		
	}	

	public void windowActivated(WindowEvent e)
	{		
	}	

	public void windowDeactivated(WindowEvent e)
	{	
	}	
	
}