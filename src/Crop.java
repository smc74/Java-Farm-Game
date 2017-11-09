public class Crop extends Produce
{
	final public String PRODUCETYPE = "CROP";
	protected Integer sownDate;
		
	Crop()
	{
		super();
		
	}
	
	Crop(Integer currentDate, EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		super(currentDate,pEvtQ,pCalendar);
		setSownDate(currentDate);
		createEventEntries();
	}
	protected void setSownDate(Integer date)
	{
		sownDate = date;
	}
	public void createEventEntries()
	{
		//write event to determine when age maturity has been reached
		EventData ageMaturityEvt = new EventData(AGE_MATURITY_REACHED,this,this.getMaturityDate());
		eventQ.priorityEnqueue(this.getMaturityDate(),1,1,ageMaturityEvt);
		
		//write event to determine when age expectancy has been reached
		EventData ageExpectancyEvt = new EventData(AGE_EXPECTANCY_REACHED,this,this.getAgeExpectancyDate());
		eventQ.priorityEnqueue(this.getAgeExpectancyDate(),1,1,ageExpectancyEvt);
		
		//write event to determine when to give the month warning of crop age expectancy
		EventData ageExpectancyWarningEvt = new EventData(AGE_EXPECTANCY_IN_ONE_MONTH,this,this.getAgeExpectancyDate()-1);
		eventQ.priorityEnqueue(this.getAgeExpectancyDate()-1,1,1,ageExpectancyWarningEvt);		

	}
	
	public void setTimeIndicators(Integer pStartDate,EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		
	}
	
	public Integer getSownDate()
	{
		return sownDate;
	}	
	public double getCurrentSaleValue(Integer date)
	{
		return 0;	
	}
	public double sellProduce()
	{
		return 0;
	}
	public boolean runScheduledActions()
	{
		String strActionCall;
		boolean schedActionFound=false;
		
		//reads the event queue for the next event and runs the action
		EventData currentEventData = eventQ.deque();
		strActionCall=currentEventData.getActionCall();
		
		if ( strActionCall.contentEquals(AGE_MATURITY_REACHED))
		{
			status=MATURE;
			schedActionFound=true;
		}
		else if (strActionCall.contentEquals(AGE_EXPECTANCY_REACHED))
		{
			status=DEAD; //referenced by land plot to determine it's own status
			hostLandPlot.destroyPlotProduce();
			this.cleanUp();
			schedActionFound=true;
		}
		else if (strActionCall.contentEquals(AGE_EXPECTANCY_IN_ONE_MONTH))
		{
			status=MATURE_LAST_MONTH;	
			schedActionFound=true;
		}
		
		return schedActionFound;
	}
	private void setProduceAttributes()
	{
		monthlyMaintenanceCost = 0;
		preparationCost = 0;
		monthlyRevenue = 0;
		timeToMaturity = 0;
		timeToDie=0;
		produceDescription ="";	
		status=null;
	}
	
}