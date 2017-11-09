public class Player implements LandLord
{
	private String playerName;
	private BankAccount bank;
	private ObservableCalendar yrlyCalendar;
	private FarmLand land;  //list of owned farm land
	private double openingBankBalance;
	private double initialStartPlotQuota;
	private Integer plotsOwned=0;
	private Integer booksLastEntryDate = -1;	
	
	Player(String pPlayerName, ObservableCalendar pCalendar, FarmLand pLand ) throws BankruptException
	{
		land=pLand;		
		initialStartPlotQuota=4;
		openingBankBalance=2000;
		playerName = pPlayerName;
		bank = new BankAccount(pCalendar,openingBankBalance);
		yrlyCalendar = pCalendar;
		setStartingLandPlotQuota();
		
	}
	
	public void setStartingLandPlotQuota() throws BankruptException //purchase initial land
	{
		double landGrant;
		boolean landPurchased;
		
		landGrant=( (land.getLandValue(1,1))  * initialStartPlotQuota );
		bank.deposit(landGrant,"Intial Land Grant " + landGrant);
		
		// I know these plots will exist.This could be made random to select neighbouring plots
		landPurchased=this.buyLand(0,0);
		landPurchased=this.buyLand(0,1);
		landPurchased=this.buyLand(1,0);
		landPurchased=this.buyLand(1,1);
		
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public void changePlayerName(String newPlayerName)
	{
		playerName=newPlayerName;
	}
	public double getBankBalance()
	{
		return bank.getBalance();
	}
	
	public double pay(double amount, String description) throws BankruptException
	{
		double successfulPayAmt;
		successfulPayAmt=bank.pay(amount, description);
		System.out.println("Successfull Payment Amount ="+amount+"  Description"+description);
		return successfulPayAmt;
	}
	
	public boolean sellLand(Integer x,Integer y)
	{
		boolean landSold;
		if (landSold = land.sellLand(x,y,this) )
		{
			plotsOwned = plotsOwned - 1;
		}
		return landSold;
	}
	
	public boolean buyLand(Integer x, Integer y) throws BankruptException
	{
		boolean landPurchased;
		
		if (landPurchased=land.buyLand(x,y,this))
		{
			plotsOwned = plotsOwned + 1;
		}
		
		return landPurchased;
	}
	public boolean recievePayment(double amount,String pDescription)
	{
		return bank.deposit(amount,pDescription);
	}
	
	public boolean sellProduce(Integer x,Integer y)
	{
		return land.sellProduce(x,y,this);
	}
	
	public boolean buyProduce(Integer x,Integer y,Produce newProduce) throws BankruptException
	{
		return land.buyProduce(x,y,newProduce,this);
	}
	
	public boolean cultivateLand(Integer x,Integer y) throws BankruptException
	{
		return land.cultivateLand(x,y,this);
	}
	
	public Integer getPlotsOwned()
	{
		return plotsOwned;
	}
	public double getThisMonthsCosts()
	{
		return land.calculateThisMonthsCosts(this);
	}
	
	public double payMonthlyCosts() throws BankruptException
	{
		double monthsCosts;
		monthsCosts=pay(land.calculateThisMonthsCosts(this),"Farm land costs for date:"+yrlyCalendar.getDate()); 
		return monthsCosts;
	}
	
	public double getMonthlyRevenue()
	{
		return land.calculateThisMonthsProfits(this);
	}
	
	public boolean recieveMonthlyRevenue()
	{
		return recievePayment(land.calculateThisMonthsProfits(this),"Revenue from farm for date:" + yrlyCalendar.getDate());	
	}
	
	public boolean doMonthlyBookKeeping() throws BankruptException
	{
		//books can only be completed once each month
		if (booksLastEntryDate != yrlyCalendar.getDate() ) 
		{
			//revenue is collected first and costs paid later. (juggling the books)
			boolean bMonthlyRevenueRecieved=recieveMonthlyRevenue(); 
			double monthlyCosts=payMonthlyCosts();	
			booksLastEntryDate=yrlyCalendar.getDate();
			return true;
		}
		else
		{
			return false; //unsuccessful if books already completed for this month
		}
		
	}
	
	public void writeBankStatement(String sFilePath)
	{
		bank.writeStatement(sFilePath);
	}
	
}