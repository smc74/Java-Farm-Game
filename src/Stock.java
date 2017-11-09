public class Stock extends Produce
{
	final public String PRODUCETYPE="STOCK";
	final public String ALIVE = "ALIVE";
	
	
	Stock()
	{
		super();
	}
	
	Stock(Integer pStartDate, EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		super(pStartDate,pEvtQ,pCalendar);
		setProduceAttributes();
		createEventEntries();
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
	
	public void setTimeIndicators(Integer pStartDate,EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		if(!bTimeIndicatorsSet)
		{
			startDate = pStartDate;
			eventQ = pEvtQ;
			yrlyCalendar = pCalendar;				
			createEventEntries();
			bTimeIndicatorsSet=true;	
		}
	}	

	public void createEventEntries()
	{
	}
	

	public boolean runScheduledActions()
	{
		return false;
	}
	

}
