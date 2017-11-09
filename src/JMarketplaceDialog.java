import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class JMarketplaceDialog extends JDialog implements ListSelectionListener,ActionListener
{
	
	JButton cancelButton=new JButton("Cancel");
	JLabel nameLabel;
	JTextField nameField;
	JTable marketPricesTable;
	JPanel buttonPanel;
	MultiWindowGameConsole callingObject;
	Container c;
	private int maxTableRows=0;
	private int maxTableCols=0;
	private String produceArray[][];
	
	JLabel marketBanner;
	protected ImageIcon marketBannerImg = new ImageIcon("marketBanner.gif");
	
	
	JMarketplaceDialog(MultiWindowGameConsole pCallingObject,String pProduceArray[][])
	{
		super((JFrame)pCallingObject,"Maketplace",true);
		callingObject=pCallingObject;
		produceArray=pProduceArray;
		if (pProduceArray!=null)
		{
			maxTableRows=pProduceArray.length;
			maxTableCols=pProduceArray[0].length;
		}
		
		c=getContentPane();
		c.setLayout(new BorderLayout());
		
		marketPricesTable=new JTable(maxTableRows,maxTableCols);
		marketPricesTable.setDragEnabled(false);
		marketPricesTable.setShowGrid(false);
		marketPricesTable.removeEditor();
		
		
		buttonPanel=new JPanel();
		buttonPanel.add(cancelButton);
		
		marketPricesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		populateMarketTable();
		
		//table selection: based on "Simple Table Selection Demo" at www.koders.com
		ListSelectionModel rowSM = marketPricesTable.getSelectionModel();
      rowSM.addListSelectionListener(this);


		cancelButton.addActionListener(this);
		
		initialiseMarketBanner();
		
		c.add(marketBanner,BorderLayout.NORTH);
		c.add(marketPricesTable,BorderLayout.CENTER);
		c.add(buttonPanel,BorderLayout.SOUTH);
		
		setSize(510,250); //future changes should make this dynamic
		setVisible(true);

	}
	
	public void initialiseMarketBanner()
	{
		marketBanner = new JLabel(marketBannerImg);
		
	}
	
	 /*	
	 * valueChanged(ListSelectionEvent e) : Handles event for table row selection
	 * Implementing ListSelectionListener
	 * table selection: based on "Simple Table Selection Demo" at www.koders.com
	 */

	public void valueChanged(ListSelectionEvent e) 
	{
	     //Ignore extra messages.
	     if (e.getValueIsAdjusting()) return;
	     
	     ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	     if (!lsm.isSelectionEmpty()) 
	     {
	         int selectedRow = lsm.getMinSelectionIndex();
	         callingObject.completeProducePurchase(selectedRow);//returns the row of the produce
	         System.out.println("Row " + selectedRow
	                            + " is now selected.");
	         dispose();                
	     }
	}
	
	public void populateMarketTable()
	{
		String cellContent=null;
		for(int xIndex=0; xIndex < produceArray.length; xIndex++)
		{
			for(int yIndex=0; yIndex< produceArray[xIndex].length ;yIndex++)
			{
				//produceArray[x][y]
				System.out.println("Cell Content = "+cellContent);
				cellContent = produceArray[xIndex][yIndex];
				marketPricesTable.setValueAt(cellContent,xIndex,yIndex);
			}
		}
		
	}

	public void actionPerformed(ActionEvent e) 
	{	
		System.out.println("e = "+e);	
		if (e.getSource() == cancelButton)
		{	
			dispose(); //dispose of this dialog without purchasing produce		
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
}