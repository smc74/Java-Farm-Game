import java.lang.Math;
public class Sheep extends Stock
{
	
	Sheep()
	{		
		super();
		setProduceAttributes();
		bTimeIndicatorsSet=false;
		//need to implement setTimeIndicators() after creating the object		
	}	

	Sheep(Integer pStartDate, EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		super(pStartDate,pEvtQ,pCalendar);
		setProduceAttributes();
		createEventEntries();
		bTimeIndicatorsSet=true;
				
	}
	
	private void setProduceAttributes()
	{
		monthlyMaintenanceCost = 150;
		preparationCost = 1300;
		monthlyRevenue = 200;
		timeToMaturity = 0;
		timeToDie=0;
		produceDescription ="Sheep";
		status=MATURE;
		
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

	
	public double getCurrentSaleValue()
	{
		Integer month;
		//In this game crop needs to be re-sown for every havest
		//Future changes may see crops such as apples are seasonal, their 
		//value seasonal, depending on the time of year.
		//Stock would not be be seasonal.
		
		return (1000 + 200) * (Math.cos((PI/12)* yrlyCalendar.getDate()));
	}	
	
}