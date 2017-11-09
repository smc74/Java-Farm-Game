//Name:FarmGameConsole
//Portions of this class have been extracted from the Driver class 
// developed by Peter Tilmanis.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;

public class FarmGameConsole extends JFrame implements ActionListener,MultiWindowGameConsole
{
	static Controllable game;
	protected JButton buyProduceButton = new JButton("Buy Produce");
	protected JButton sellProduceButton = new JButton("Sell Produce");
	protected JButton buyLandButton = new JButton("Buy Land");
	protected JButton sellLandButton = new JButton("Sell Land");
	protected JButton nextMonthButton = new JButton("Next Month");
	protected JButton cultivateLandButton = new JButton("Cultivate Land");
	
	protected JLabel bannerTitle;	

	protected JButton gameBoardButtons[][];
	
	GridBagConstraints gbcGameBoardPanel;
	GridBagConstraints gbcGameInfoPanel;
	
	JPanel buttonPanel = new JPanel();
	JPanel gameboardPanel = new JPanel();
	JPanel gameInfoContainerPanel = new JPanel();
	JPanel gameInfoPanel = new JPanel();
	JPanel gameTitleBanner = new JPanel();
	
	Container gameContentPane;
	private String playerName;	
	
	protected ImageIcon wheatImg = new ImageIcon("wheat.gif");
	protected ImageIcon cowImg = new ImageIcon("cow.gif");
	protected ImageIcon sheepImg = new ImageIcon("sheep.gif");
	protected ImageIcon cultivatedImg = new ImageIcon("cultivated.gif");
	protected ImageIcon uncultivatedImg = new ImageIcon("uncultivated.gif");
	protected ImageIcon pollutedImg = new ImageIcon("polluted.gif");
	protected ImageIcon appleImg = new ImageIcon("apple.gif");
	protected ImageIcon unownedImg = new ImageIcon("unowned.gif");
	
	protected ImageIcon farmYardImg = new ImageIcon("farmYard.gif");
	
	protected ImageIcon immatureAppleImg = new ImageIcon("immatureApple.gif");
	protected ImageIcon immatureWheatImg = new ImageIcon("immatureWheat.gif");
	
	protected ImageIcon warningWheatImg = new ImageIcon("warningWheat.gif");
	protected ImageIcon warningAppleImg = new ImageIcon("warningApple.gif");
	
	protected int gameBoardFocusX=-1;
	protected int gameBoardFocusY=-1;
	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
	private String produceArray[][];
	private int numberOfDifferentProduce;
	final String PRODUCE_APPLE="Apples";
	final String PRODUCE_WHEAT="Wheat";
	final String PRODUCE_COWS="Cows";
	final String PRODUCE_SHEEP="Sheep";
	final String PRODUCE_CROP="Crop";
	final String PRODUCE_STOCK="Stock";
	
	
	GameSetupFrame dialogGetPlayerDetails;
	JLabel labelPlayerNameData;
	DecimalFormat currency;
	JLabel labelPlayerValueData;
	JLabel labelDateData;
	JLabel labelPlayerNameTag;
	JLabel labelPlayerValueTag;
	JLabel labelCurrentDateTag;
	JLabel labelSelectedCoOrdTag;
	JLabel labelSelectedCoOrdData;

	
	FarmGameConsole() throws IOException, BankruptException
	{
		//intitialise game
		super("Farm Game");
		dialogGetPlayerDetails = new GameSetupFrame(this);
		//playerName="TEMPPLAYER";
		
		
	}
		
	
	public void generateConsole() throws IOException, BankruptException
	{	
		if (game == null)
		{
			game = new Controller(playerName);
			initialiseProduceList();
		}
			
		gameContentPane=getContentPane();
		gameContentPane.setLayout(new BorderLayout());
		buttonPanel.setLayout(new GridLayout(6,1));
		gameboardPanel.setLayout(new GridBagLayout());
		gameInfoContainerPanel.setLayout(new BorderLayout());
		gameInfoPanel.setLayout(new GridBagLayout());


		//GridBagConstraints 
		gbcGameBoardPanel=new GridBagConstraints();
		gbcGameBoardPanel.fill = GridBagConstraints.HORIZONTAL;
		
		gbcGameInfoPanel=new GridBagConstraints();
		gbcGameInfoPanel.fill = GridBagConstraints.HORIZONTAL;
	
		//create the game board buttons
		createGameBoardButtons(game.getXSize(), game.getYSize());
		
		//initialise uses gameBoardPanel,gbc
		intialiseGameBoard(game.getXSize(), game.getYSize());
		
		
		initialiseBannerTitle();
		
		//set action listener on the control buttons
		sellProduceButton.addActionListener(this);
		buyLandButton.addActionListener(this);
		cultivateLandButton.addActionListener(this);
		sellLandButton.addActionListener(this);
		buyProduceButton.addActionListener(this);
		nextMonthButton.addActionListener(this);
		
		//control buttons
		buttonPanel.add(sellProduceButton);		
		buttonPanel.add(buyLandButton);
		buttonPanel.add(cultivateLandButton);
		buttonPanel.add(sellLandButton);
		buttonPanel.add(buyProduceButton);
		buttonPanel.add(nextMonthButton);
		
		//game info panel
		intialiseGameInfoPanel();
	
		//for positioning add the game info panel to game info container panel
		gameInfoContainerPanel.add(gameInfoPanel,BorderLayout.WEST);
		
		//update the boards status
		//updateBoardStatus();
		
		
		//add panels to the swing content pane
		gameContentPane.add(buttonPanel,BorderLayout.EAST);
		gameContentPane.add(gameboardPanel,BorderLayout.CENTER);
		gameContentPane.add(gameInfoContainerPanel,BorderLayout.SOUTH);
		gameContentPane.add(gameTitleBanner,BorderLayout.NORTH);
				
		setSize(900,670);
		setVisible(true);
		

		//refreshGameConsole();
		
		
		//gameboardPanel.removeAll();	
		
	}
	
	
//	public void paintComponent(Graphics g,Component c,Container p,Rectangle r)
//	{
//		super.paintComponent(g,c,p,r);
//		gameContentPane.update(g);		
//	}
	public void refreshGameConsole()
	{
		//destroy everything in the gamebordPannel and then redraw it???
		
		//Reset the gameboardPanel
		gameboardPanel.setVisible(false);	
		
		destroyGameBoardButtons(game.getXSize(), game.getYSize());
		gameboardPanel.removeAll();  //clear the gameboard panel
		createGameBoardButtons(game.getXSize(), game.getYSize()); //create new gameboard buttons
		intialiseGameBoard(game.getXSize(), game.getYSize());//initialise the gameboard panel with the new buttons

		gameboardPanel.setVisible(true);
		
		//reset the game info pannel
		labelPlayerValueData.setText("$"+currency.format( game.getWorth()) );
		labelDateData.setText(((Controller)game).getDateString());
		refreshConsoleCoordinateIndicator();
		
	}

	/*refreshConsoleCoordinateIndicator()
	 *
	 *Refresh coordinate indicator is run everytime a game board cell is clicked
	 *less overhead than refreshing the content of the entire game board
	 */
	public void refreshConsoleCoordinateIndicator()
	{
		if ( (gameBoardFocusX>=0) && (gameBoardFocusY>=0) )
		{ 	
			labelSelectedCoOrdData.setText(gameBoardFocusX+","+gameBoardFocusY);
		}
		else
		{
			labelSelectedCoOrdData.setText("Plot not selected");
		}
		
	}
	
	
	public void initialiseBannerTitle()
	{
		JLabel bannerTitle = new JLabel(farmYardImg);
		gameTitleBanner.add(bannerTitle);
	}

	public void intialiseGameInfoPanel()
	{
		labelPlayerNameData = new JLabel(game.getName());
		currency=new DecimalFormat("#0.00"); 
		labelPlayerValueData= new JLabel("$"+currency.format( game.getWorth()) );
		labelDateData = new JLabel(((Controller)game).getDateString());
		
		if ( (gameBoardFocusX>=0) && (gameBoardFocusY>=0) )
		{ 	
			labelSelectedCoOrdData = new JLabel(gameBoardFocusX+","+gameBoardFocusY);
		}
		else
		{
			labelSelectedCoOrdData = new JLabel("Plot not selected");
		}
		
		labelPlayerNameTag =new JLabel("Player Name:");
		labelPlayerValueTag = new JLabel("Player Value:");
		labelCurrentDateTag = new JLabel("Current Date:");
		labelSelectedCoOrdTag = new JLabel("Selected Plot:");
		
		gbcGameInfoPanel.weightx=0.5;
		gbcGameInfoPanel.gridx=0;
		gbcGameInfoPanel.gridy=0;
		gbcGameInfoPanel.gridwidth=1;
		gbcGameInfoPanel.insets = new Insets(0,0,0,0);

		//labels
		
		gbcGameInfoPanel.gridy=0;
		gameInfoPanel.add(labelPlayerNameTag,gbcGameInfoPanel);
		gbcGameInfoPanel.gridy=1;
		gameInfoPanel.add(labelPlayerValueTag,gbcGameInfoPanel);
		gbcGameInfoPanel.gridy=2;
		gameInfoPanel.add(labelCurrentDateTag,gbcGameInfoPanel);
		
		
		gbcGameInfoPanel.gridx=1;
		gbcGameInfoPanel.insets = new Insets(0,10,0,0);
		//data
		gbcGameInfoPanel.gridy=0;
		gameInfoPanel.add(labelPlayerNameData,gbcGameInfoPanel);
		gbcGameInfoPanel.gridy=1;
		gameInfoPanel.add(labelPlayerValueData,gbcGameInfoPanel);
		gbcGameInfoPanel.gridy=2;
		gameInfoPanel.add(labelDateData,gbcGameInfoPanel);
		
		//Coordinates of selected plot
		gbcGameInfoPanel.insets = new Insets(0,30,0,0);
		gbcGameInfoPanel.gridx=3;
		gbcGameInfoPanel.gridy=1;
		gameInfoPanel.add(labelSelectedCoOrdTag,gbcGameInfoPanel);
		
		gbcGameInfoPanel.insets = new Insets(0,10,0,0);
		gbcGameInfoPanel.gridx=4;
		gbcGameInfoPanel.gridy=1;
		gameInfoPanel.add(labelSelectedCoOrdData,gbcGameInfoPanel);
	}
	
	public void setPlayerName(String pName)
	{
		playerName=pName;
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		
		System.out.println("ae.getSource ="+ae.getSource());

			if (ae.getSource() == sellLandButton )
			{					
				if ( (gameBoardFocusX!=-1)&&(gameBoardFocusY!=-1) )
				{		
					try
					{
						this.sellLand();
					}
					catch(IOException e)
					{
						displayIoErrorMessage(e);
					}
					catch(BankruptException e)
					{
						displayBankruptMessage(e);
					}
					this.resetGameBoardFocus();
					this.refreshGameConsole();
				}
			}
			
			if (ae.getSource() == buyLandButton )
			{
		
				if ( (gameBoardFocusX!=-1)&&(gameBoardFocusY!=-1) )
				{
					try
					{
						this.buyLand();
					}
					catch(IOException e)
					{						
						displayIoErrorMessage(e);
					}
					catch (BankruptException e)
					{
						displayBankruptMessage(e);					
					}
					this.resetGameBoardFocus();
					this.refreshGameConsole();
				}
				
			}
			else if (ae.getSource() == sellProduceButton )
			{
				
				if ( (gameBoardFocusX!=-1)&&(gameBoardFocusY!=-1) )
				{
					try
					{
						this.sellProduce();
					}
					catch(IOException e)  // in the future all transactions may have a fee
					{	
						displayIoErrorMessage(e);						
					}
					catch (BankruptException e)
					{
						displayBankruptMessage(e);
					}
					this.resetGameBoardFocus();
					this.refreshGameConsole();
					
				}
		
			}
			else if (ae.getSource() == buyProduceButton )
			{
				
				if ( (gameBoardFocusX!=-1)&&(gameBoardFocusY!=-1) )
				{
					try
					{
						if (game.isDevelopable(gameBoardFocusX,gameBoardFocusY))
						{
							this.buyProduce();
							this.resetGameBoardFocus();
							this.refreshGameConsole();	
						}
					}
					catch(IOException e)
					{	
						displayIoErrorMessage(e);						
					}
					catch (BankruptException e)
					{
						displayBankruptMessage(e);
					}
					
				}
		
			}			
			
			else if (ae.getSource() == buyLandButton )
			{
				if ( (gameBoardFocusX!=-1)&&(gameBoardFocusY!=-1) )
				{
					try
					{
						this.buyLand();
					}
					catch(IOException e)
					{	
						displayIoErrorMessage(e);
					}
					catch (BankruptException e)
					{
						displayBankruptMessage(e);
					}
					this.resetGameBoardFocus();
					this.refreshGameConsole();
				}
		
			}
			else if (ae.getSource() == nextMonthButton )
			{
				
				try
				{
					game.advanceCalendar();					
				}
				catch (BankruptException e)
				{
					displayBankruptMessage(e);				
				}
				this.resetGameBoardFocus();
				this.refreshGameConsole();
			}
			else if (ae.getSource() == cultivateLandButton )
			{
				if ( (gameBoardFocusX!=-1)&&(gameBoardFocusY!=-1) )
				{
					try
					{
						this.recultivateLand();
						this.resetGameBoardFocus();
						this.refreshGameConsole();	
					}
					catch(IOException e)
					{						
						displayIoErrorMessage(e);
					}									
					catch (BankruptException e)
					{							
						displayBankruptMessage(e);
					}

				}
				
			}
			else // iterate through looking for the button that matches
			{
				for(int indexX=0;indexX<gameBoardButtons.length; indexX++)			
				{
					for(int indexY=0; indexY<gameBoardButtons[indexX].length;indexY++)
					{
						if ( gameBoardButtons[indexX][indexY] == ae.getSource() )
						{
							gameBoardFocusX=indexX; //set the gameboard focus
							gameBoardFocusY=indexY;
							refreshConsoleCoordinateIndicator();
							System.out.println("index X = "+indexX+"  indexY ="+indexY);
						}
					}
				}
			}

	}
	
	public void displayBankruptMessage(BankruptException e)
	{
		String exceptionMsg[] = new String[2];
		exceptionMsg[0]="You are bankrupt!";
		exceptionMsg[1]="Reason: "+e.getMessage();
		JGameOverDialog gameOver=new JGameOverDialog(this,exceptionMsg);
		System.out.println("You are bankrupt!");
		System.out.println("Reason: " + e.getMessage());
	}

	public void displayIoErrorMessage(IOException e)
	{
		System.out.println("IO Error!");
		System.out.println("Reason: " + e.getMessage());
	}

	
	public void resetGameBoardFocus()
	{
		gameBoardFocusX=-1;
		gameBoardFocusY=-1;
	}
	
	/* Removes all instances of every game button used from action listener
	 *	
	 */
	
	public void destroyGameBoardButtons(int pMaxX, int pMaxY)
	{
		
		for(int indexX=0; indexX<pMaxX; indexX++)
		{
			for(int indexY=0; indexY<pMaxY; indexY++)
			{
				//gameBoardButtons[indexX][indexY]=createGameBoardCell(indexX,indexY);
				gameBoardButtons[indexX][indexY].removeActionListener(this);
				gameBoardButtons[indexX][indexY]=null;
			}			
			
		}		
		
	}
	
	public void createGameBoardButtons(int pMaxX, int pMaxY)
	{
		gameBoardButtons=new JButton[pMaxX][pMaxY];
		
		for(int indexX=0; indexX<pMaxX; indexX++)
		{
			for(int indexY=0; indexY<pMaxY; indexY++)
			{
				gameBoardButtons[indexX][indexY]=createGameBoardCell(indexX,indexY);
				gameBoardButtons[indexX][indexY].addActionListener(this);
			}			
			
		}		
		
	}
	
	//public JButton createGameBoardCell(int pX, int pY)
	
	public JButton createGameBoardCell(int pX, int pY)
	{
		//State tests based on "Driver.java" developed by Peter Tilmanis
		//JButton createdButton=null;
		JButton createdButton = null;
		
		//start highest order precedence, ie look for most development
		//and populate the cell accordingly.ie polluted,produce,owned

	
       if ( game.isOwned(pX,pY) ) // only if owned check the state
       {
       
           // display produce
	       Produce produce = game.getProduce(pX, pY);
	        System.out.println("Create Game Board Cell - Produce ="+produce);
	       if (produce instanceof Cow)
	       {
	       	System.out.println("make a cow button");
	       	createdButton= new JButton(cowImg);   //gameBoardButtons[x][y].setSelectedIcon(cowImg); //System.out.print("C");
	       }
	       else if (produce instanceof Sheep)
	       {
	        	createdButton=new JButton(sheepImg);//System.out.print("S");
	       }
	       else if (produce instanceof Wheat)
	       {
	           if ( ((Crop) produce).getStatus() == Crop.IMMATURE )
	           {
	           		createdButton=new JButton(immatureWheatImg); //System.out.print("w");
	           }
	           else if ( ((Crop) produce).getStatus() == Crop.WARNING )
	           {
	           
	               createdButton=new JButton(warningWheatImg); //System.out.print("!");
	           }
	           	else
	         	{
	           	
	            	createdButton=new JButton(wheatImg); //System.out.print("W");
	            }
	       }
	       else if (produce instanceof Apple)
	       {
	            if ( ((Crop) produce).getStatus() == Crop.IMMATURE )
	            {
	               createdButton=new JButton(immatureAppleImg); //System.out.print("a");
	            }
	            else if ( ((Crop) produce).getStatus() == Crop.WARNING )
	            {
	               createdButton=new JButton(warningAppleImg);//System.out.print("!");
	            }
	            else
	            {
	               createdButton=new JButton(appleImg);//System.out.print("A");
	            }
	       }
	       if (createdButton == null)  // if produce has not been assigned to the button 
	       {   
		       if (!game.isDevelopable(pX, pY))  // display pollution
		       {
				 		createdButton=new JButton(pollutedImg); //System.out.print(".\t");
		  		 }
		  		 else
		  		 {
		  		 		createdButton=new JButton(cultivatedImg);
		  		 }
		   } 
		}

		// test for owned
		if (createdButton == null)
		{		
			if (!game.isOwned(pX, pY))
			{
	      		createdButton= new JButton(unownedImg); //System.out.print(".");			
			}
		}
         
		return createdButton;		
			
	}
	
	public void intialiseGameBoard(int pMaxX, int pMaxY)
	{
		gbcGameBoardPanel.weightx=0.5;
		gbcGameBoardPanel.gridx=0;
		gbcGameBoardPanel.gridy=0;
		gbcGameBoardPanel.gridwidth=1;
		gbcGameBoardPanel.insets = new Insets(0,0,0,0);

		for(int indexX=0; indexX<pMaxX; indexX++)
		{
			for(int indexY=0; indexY<pMaxY; indexY++)
			{
				gbcGameBoardPanel.gridx=(indexX); 
				gbcGameBoardPanel.gridy=(indexY);
				gameboardPanel.add(gameBoardButtons[indexX][indexY],gbcGameBoardPanel);
			}			
			
		}		
		
	}
	
	/****
	/*
	/*/
	 
	 public void initialiseProduceList()
	 {
		numberOfDifferentProduce=4; 
		
		produceArray = new String[numberOfDifferentProduce][2];
		
		produceArray[0][0]=PRODUCE_CROP;
		produceArray[0][1]=PRODUCE_APPLE;
			
		produceArray[1][0]=PRODUCE_CROP;
		produceArray[1][1]=PRODUCE_WHEAT;
			
		produceArray[2][0]=PRODUCE_STOCK;
		produceArray[2][1]=PRODUCE_SHEEP;

		produceArray[3][0]=PRODUCE_STOCK;
		produceArray[3][1]=PRODUCE_COWS;
			 		 	
	 }


   /**
    * A method to ask the user questions about buying produce.
    *  based on "Driver.java" developed by Peter Tilmanis
    */
   public void buyProduce() throws IOException, BankruptException
   {
      JMarketplaceDialog marketplaceDialog;
		marketplaceDialog = new JMarketplaceDialog(this,produceArray); 
   }
   
   
	public void completeProducePurchase(int pSelectedRow)   
	{
		String produceDescription=null;
		Produce selectedProduce=null;
		
		int x=gameBoardFocusX;
		int y=gameBoardFocusY;
		
		produceDescription=produceArray[pSelectedRow][1];
		
		if (produceDescription == PRODUCE_APPLE)
		{
			selectedProduce = new Apple();
		}
		else if (produceDescription == PRODUCE_WHEAT)
		{
			selectedProduce = new Wheat();
		}
		else if (produceDescription == PRODUCE_SHEEP)
		{
			selectedProduce = new Sheep();
		}
		else if (produceDescription == PRODUCE_COWS)
		{
			selectedProduce = new Cow();
			
		}
		
      if (selectedProduce != null)
      {
         // try to buy the produce
         try
         {
         
         	if (game.buyProduce(x, y, selectedProduce))
         	{
         		
            	System.out.println("The produce at " + x + ", " + y + " was successfully bought.");
            }
         	else
            	System.out.println("The produce at " + x + ", " + y + " could not be bought.");
         }
         catch(BankruptException e)
         {
         	displayBankruptMessage(e);
         }
         
      }         

		
	}
   
   
   
   /**
    * A method to ask the user questions about selling produce.
    *  based on "Driver.java" developed by Peter Tilmanis
    */
   public void sellProduce() throws IOException, BankruptException
   {
      // try to sell produce
      if (game.sellProduce(gameBoardFocusX, gameBoardFocusY))
         System.out.println("The produce at " + gameBoardFocusX + ", " + gameBoardFocusY + " was successfully sold.");
      else
         System.out.println("The produce at " + gameBoardFocusX + ", " + gameBoardFocusY + " could not be sold.");
   }

   /**
    * A method to ask the user questions about buying land.
    *  based on "Driver.java" developed by Peter Tilmanis
    */
   public void buyLand() throws IOException, BankruptException
   {
      int x;
      int y;
      // get info on where to buy
  //    System.out.print("Enter the x coordinate of land: ");
  //    x = Integer.parseInt(stdin.readLine());
  //    
  //    System.out.print("Enter the y coordinate of land: ");
  //    y = Integer.parseInt(stdin.readLine());

      // try to buy land
      x=gameBoardFocusX;
      y=gameBoardFocusY;
      
      if (game.buyLand(gameBoardFocusX, gameBoardFocusY))
         System.out.println("The land at " + x + ", " + y + " was successfully bought.");
      else
         System.out.println("The land at " + x + ", " + y + " could not be bought.");
   }


   /**
    * A method to ask the user questions about recultivating land.
    *     based on "Driver.java" developed by Peter Tilmanis
    */
   public void recultivateLand() throws IOException, BankruptException
   {
      int x, y;
      
      // get info on where to recultivate
      
      x = gameBoardFocusX;
      y = gameBoardFocusY;

      // try to recultivate land
      if (game.recultivate(x, y))
         System.out.println("The land at " + x + ", " + y + " was successfully recultivated.");
      else
         System.out.println("The land at " + x + ", " + y + " could not be recultivated.");
   }

	 /**
    * A method to ask the user questions about selling land.
    *	  based on "Driver.java" developed by Peter Tilmanis
    */
   public void sellLand() throws IOException, BankruptException
   {
      int x, y;
      
      // get info on where to sell
      x = gameBoardFocusX;
      y = gameBoardFocusY;

      // try to sell land
      if (game.sellLand(x, y))
         System.out.println("The land at " + x + ", " + y + " was successfully sold.");
      else
         System.out.println("The land at " + x + ", " + y + " could not be sold.");
   }
	
	public void exitGame()
	{
		dispose();
	}
	public void resetGame() 
	{
		
		//reset every class attribute to it's original default.
		destroyGameBoardButtons(game.getXSize(), game.getYSize());
		Controllable game=null;
		
		bannerTitle=null;	
		
		
		JButton gameBoardButtons[][]=null;
		
		gbcGameBoardPanel=null;
		gbcGameInfoPanel=null;
		
		gameContentPane.removeAll();
		gameContentPane=null;
		//private String playerName; // keep the player name	
	
		gameBoardFocusX=-1;
		gameBoardFocusY=-1;
		produceArray=null;
		numberOfDifferentProduce=-1;
		labelPlayerNameData=null;
		currency=null;
		labelPlayerValueData=null;
		labelDateData=null;
		labelPlayerNameTag=null;
		labelPlayerValueTag=null;
		labelCurrentDateTag=null;
	
		try
		{
			this.generateConsole();
		}
		catch(BankruptException e)
		{
			displayBankruptMessage(e);
		}
		catch(IOException e)
		{	
			displayIoErrorMessage(e);
		}
	}

}