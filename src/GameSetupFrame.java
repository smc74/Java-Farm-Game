import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


public class GameSetupFrame extends JDialog implements ActionListener, WindowListener
{
	JButton buttonOk;
	JLabel nameLabel;
	JTextField nameField;
	
	JPanel playerNamePanel;
	JPanel buttonPanel;
	
	MultiWindowGameConsole callingObject;
	Container c;
	int TEXT_FIELD_SIZE=20;
	
	GameSetupFrame(MultiWindowGameConsole pCallingObject)
	{
		
		super((JFrame)pCallingObject,"Game Setup",true);
		callingObject=pCallingObject;
		c=getContentPane();
		c.setLayout(new BorderLayout());
		
		nameLabel=new JLabel("Enter Player Name:");
		nameField=new JTextField(TEXT_FIELD_SIZE);
		buttonOk=new JButton("Ok");
		
		
		playerNamePanel = new JPanel();
		buttonPanel = new JPanel();
	
		playerNamePanel.setLayout(new BorderLayout());
	
		playerNamePanel.add(nameLabel,BorderLayout.WEST);
		playerNamePanel.add(nameField,BorderLayout.CENTER);
		
		buttonPanel.add(buttonOk);

		buttonOk.addActionListener(this);
		
		c.add(playerNamePanel,BorderLayout.CENTER);
		c.add(buttonPanel,BorderLayout.SOUTH);		
		
		setSize(450,100);
		setVisible(true);

	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == buttonOk)
		{
			
			if ((String.valueOf(nameField.getText())).length() > 0)
			{
				//String.valueOf(nameField.getText());
				callingObject.setPlayerName(nameField.getText());
				System.out.println("button pressed add player");
				try
				{
					callingObject.generateConsole();
				}
				catch(IOException ex)
				{						
					displayIoErrorMessage(ex);
				}									
				catch (BankruptException ex)
				{							
					displayBankruptMessage(ex);
				}
				
				dispose();
			}	
		}
	}
	
	public void displayBankruptMessage(BankruptException e)
	{
		String exceptionMsg[] = new String[2];
		exceptionMsg[0]="You are bankrupt!";
		exceptionMsg[1]="Reason: "+e.getMessage();
		JGameOverDialog gameOver=new JGameOverDialog(callingObject,exceptionMsg);
		System.out.println("You are bankrupt!");
		System.out.println("Reason: " + e.getMessage());
	}

	public void displayIoErrorMessage(IOException e)
	{
		System.out.println("IO Error!");
		System.out.println("Reason: " + e.getMessage());
	}
	
		public void windowOpened(WindowEvent e)
	{
	}	
	
	public void windowClosing(WindowEvent e)
	{
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