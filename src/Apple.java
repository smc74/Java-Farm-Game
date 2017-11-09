import java.lang.Math;
public class Apple extends Crop
{
	Apple()
	{
		//super(0,null,null);
		//setProduceAttributes();
		//setSownDate(0);
		//bTimeIndicatorsSet=false;
		
		super();
		setProduceAttributes();
		bTimeIndicatorsSet=false;
		//need to implement setTimeIndicators() after creating the object
	}
	
	Apple(Integer pStartDate, EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		super(pStartDate,pEvtQ,pCalendar);
		setProduceAttributes();
		setSownDate(pStartDate);
		createEventEntries();
		bTimeIndicatorsSet=true;							
	}
	
	private void setProduceAttributes()
	{
		monthlyMaintenanceCost = 50;
		preparationCost = 800;
		monthlyRevenue = 0;
		timeToMaturity = 2; //this is changed from the assignment document
		timeToDie=5;		  //this is changed from the assignment document
		produceDescription ="Apples";
		status = IMMATURE;
				
	}
	
	public void setTimeIndicators(Integer pStartDate,EventPriorityQueue pEvtQ,ObservableCalendar pCalendar)
	{
		if (!(bTimeIndicatorsSet))
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
		
		if ( (status ==MATURE) || (status == MATURE_LAST_MONTH) )
		{
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
			return (750 + 100) * (Math.sin((PI/12) * month ));
		}
		else
		{
			return 0;
		}				
	}
}