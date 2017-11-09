public class Controller implements Controllable
{
	private Player[] arrayOfPlayers;  //list of players
	private FarmLand farmGameBoard;
	private Integer xBoardWidth;
	private Integer yBoardHeight;
	private EventPriorityQueue scheduledEvents;
	private GameCalendar calendar;
	private Integer numberOfPlayers;
	private Integer playersTurn=0;
	
	Controller(String playerName) throws BankruptException
	{
	  xBoardWidth=8;
	  yBoardHeight=6;
	  numberOfPlayers=1;
	  farmGameBoard=new FarmLand(xBoardWidth,yBoardHeight,scheduledEvents);
	  scheduledEvents=new EventPriorityQueue();
	  calendar= new GameCalendar();
	  arrayOfPlayers=new Player[numberOfPlayers]; //future multiple player support
	  
	  //String playerName = "Player1";
	  
	  // set up all game players in array
	  for(int playerIndex=0; playerIndex < numberOfPlayers; playerIndex++)
	  {
	  		arrayOfPlayers[playerIndex]= new Player(playerName, calendar, farmGameBoard);
	  }	  	
	  
	}
	
	public String getName()
	{
		return arrayOfPlayers[playersTurn].getPlayerName();
	}
	
	public double getWorth()
	{
		return arrayOfPlayers[playersTurn].getBankBalance();
	}
	
	public int getXSize()
	{
		return xBoardWidth;
	}
	
	public int getYSize()
	{
		return yBoardHeight;
	}
	
	public void advanceCalendar() throws BankruptException
	{
		boolean bMonthlyBooks;
		calendar.incrementMonth();
		scheduledEvents.actionEvents(calendar.getDate());
		bMonthlyBooks = arrayOfPlayers[playersTurn].doMonthlyBookKeeping();
	}
	
	public Produce getProduce(int x, int y)
	{
		return farmGameBoard.getProduce(x,y);
	}
	
	public boolean isDevelopable(int x, int y)
	{
		return farmGameBoard.isDevelopable(x,y);
	}
	
	public boolean isOwned(int x, int y)
	{
		return farmGameBoard.isOwned(x,y);
	}
	
	public boolean buyLand(int x, int y) throws BankruptException
	{
		return arrayOfPlayers[playersTurn].buyLand(x,y);
	}
	
	public boolean recultivate(int x, int y) throws BankruptException
	{
		return arrayOfPlayers[playersTurn].cultivateLand(x,y);
	}
	
	public boolean sellLand(int x, int y)
	{
		return arrayOfPlayers[playersTurn].sellLand(x,y);
	}
	
	public boolean buyProduce(int x, int y, Produce newProduce) throws BankruptException
	{
		if( !newProduce.timeIndicatorsSet() )
		{
			System.out.println("BEFORE SET TIME INDICATORS in control");		
			newProduce.setTimeIndicators(calendar.getDate(),scheduledEvents,calendar);	
		}
		
		return arrayOfPlayers[playersTurn].buyProduce(x,y,newProduce);	
	}
	
	public boolean sellProduce(int x, int y)
	{
		return arrayOfPlayers[playersTurn].sellProduce(x,y);
	}
	
	public String getDateString()
	{
		return calendar.getDateString();
	}
	
	public void writeBankStatement(String sFilePath)
	{
		arrayOfPlayers[playersTurn].writeBankStatement(sFilePath);
	}	
	
	public void writeQueue(String sFilePath)
	{
		scheduledEvents.writeQueue(sFilePath);
	}
}