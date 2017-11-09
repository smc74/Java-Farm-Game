import java.lang.Math;
public class Wheat extends Crop
{
	Wheat()
	{
		super();
		setProduceAttributes();
		bTimeIndicatorsSet=false;
		//need to implement setTimeIndicators() after creating the object
	}
	
	Wheat(Integer pStartDate,EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		super(pStartDate,pEvtQ,pCalendar);
		setProduceAttributes();
		setSownDate(pStartDate);
		createEventEntries();
		bTimeIndicatorsSet=true;
	}
	
	private void setProduceAttributes()
	{
		monthlyMaintenanceCost = 30;
		preparationCost = 500;
		monthlyRevenue = 0;
		timeToMaturity = 3;
		timeToDie=6;
		produceDescription ="Wheat";
		status = IMMATURE;
				
	}
	
	public void setTimeIndicators(Integer pStartDate,EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		if(!bTimeIndicatorsSet)
		{
			startDate = pStartDate;
			eventQ = pEvtQ;
			yrlyCalendar = pCalendar;
			setSownDate(pStartDate);
			createEventEntries();
			bTimeIndicatorsSet=true;
		}
	}
	
	public double getCurrentSaleValue()
	{
		Integer month;
		//In this game this crop needs to be re-sown for every havest
		//Future changes may see crops such as apples are seasonal, their 
		//value seasonal, depending on the time of year.

		if ((yrlyCalendar.getDate()%12)==0)
		{
			month = 12;
		}
		else
		{
			month = (yrlyCalendar.getDate()%12);
		}

		if ( (status ==MATURE) || (status == MATURE_LAST_MONTH) )
		{
			return (400 + 150) * (Math.sin((PI/12)* month));
		}
		else
		{
			return 0;
		}	
	}
	
}