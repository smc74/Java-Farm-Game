public abstract class Produce implements Schedule
{
	//Stock could be extended to have maturity and life span.  For this reason 
	//these constants and associated methods have been kept at the super class.
	
	final protected double PI = 3.1415927;
	
	final protected String AGE_MATURITY_REACHED = "AGE_MATURITY_REACHED";
	final protected String AGE_EXPECTANCY_REACHED = "AGE_EXPECTANCY_REACHED";
	final protected String AGE_EXPECTANCY_IN_ONE_MONTH = "AGE_EXPECTANCY_IN_ONE_MONTH";
	
	final static public String MATURE = "MATURE";
	final static public String IMMATURE = "IMMATURE";
	final static public String DEAD = "DEAD";
	final static public String MATURE_LAST_MONTH = "MATURE_LAST_MONTH";
	final static public String WARNING = MATURE_LAST_MONTH;
	
	
	protected String status=null;
	protected double preparationCost=0;
	protected double monthlyMaintenanceCost=0;
	protected double monthlyRevenue=0;
	protected Integer timeToMaturity=0;
	protected Integer timeToDie=0;
	public String PRODUCETYPE;
	protected Integer startDate=0;
	protected EventPriorityQueue eventQ;
	
	protected String produceDescription=null;
	protected ObservableCalendar yrlyCalendar=null;
	protected boolean bTimeIndicatorsSet=false;
	protected LandPlot hostLandPlot=null; //reference to host land plot
	
	Produce()
	{
		
	}
	
	Produce(Integer pStartDate,EventPriorityQueue pEvtQ, ObservableCalendar pCalendar)
	{
		
		eventQ = pEvtQ;
		startDate = pStartDate;
		yrlyCalendar = pCalendar;
		
		// set the following elements in extended classes
		
		//monthlyMaintenanceCost = 0;
		//preparationCost = 0;
		//monthlyRevenue = 0;
		//timeToMaturity = 0;
		//timeToDie=0;
		//produceDescription ="";
		//createEventEntries();
	}
	
	public double getPreparationCost()
	{
		return preparationCost;
	}
	
	public double getAge()
	{
		return ( yrlyCalendar.getDate() - startDate );
	}
	
	public double getMonthlyMaintenanceCost()
	{
		return monthlyMaintenanceCost;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public double getMonthlyRevenue()
	{
		return monthlyRevenue;
	}

	public double getCurrentSaleValue()
	{
		return 0;
	}
	
	//protected void setPreparationCost(double pPreparationCost)
	//{
	//	preparationCost=pPreparationCost;
	//}
	
	abstract public void createEventEntries();
		
	public boolean cleanUp()
	{
		//remove all entries of this object from the event queue
		return eventQ.delqueObj(this);	
	}
	
	abstract public boolean runScheduledActions();
		
	public String getProduceDescription()
	{
		return produceDescription;
	}
	
	public Produce getProduce()
	{
		return this;
	}
	public void setTimeIndicators(Integer pStartDate,EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
	}
	
	public boolean timeIndicatorsSet()
	{
		return bTimeIndicatorsSet;
	}

	private void setProduceAttributes()
	{
	}
	
	public void assignLandPlot(LandPlot pPlot)
	{
		hostLandPlot= pPlot;
	}
	
	public String getAddress()
	{
		return hostLandPlot.getAddress();	
	}
	
	public Integer getTimeToMaturity()
	{
		return timeToMaturity;
	}
	
	public Integer getMaturityDate()
	{
		Integer maturityDate=0;
		
		System.out.println("Start date:"+startDate);
		System.out.println("Period to maturity:"+getTimeToMaturity());
		
		if (this.getTimeToMaturity() > 0)
		{
			maturityDate = this.startDate + this.getTimeToMaturity();
		}
		return maturityDate;
	}

	public Integer getAgeExpectancy()
	{
		return timeToDie;
	}
	
	public Integer getAgeExpectancyDate()
	{
		//Produce cannot die immediately.  
		//If the produce doesn't have die 0 will be returned
		
		Integer ageExpectancyDate=0;
		
		if (this.getAgeExpectancy() != 0)
		{
			ageExpectancyDate = this.startDate + this.getAgeExpectancy();
		}
		return ageExpectancyDate;
	}

}