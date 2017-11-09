public class LandPlot implements Schedule
{
	final private String CULTIVATE_GROUND="CULTIVATE_GROUND";
	final private String UNPOLLUTE_GROUND="UNPOLLUTE_GROUND";
	
	final private String MATURE = "MATURE";
	final private String IMMATURE = "IMMATURE";
	final protected String DEAD = "DEAD";
	final protected String MATURE_LAST_MONTH = "MATURE_LAST_MONTH";

	final private String POLLUTED = "POLLUTED";
	final private String UNCULTIVATED = "UNCULTIVATED";
	final private String CULTIVATED = "CULTIVATED";
	final private String CLEAR = "CLEAR";
	final private String OCCUPIED = "OCCUPIED";
	
	private EventPriorityQueue evtQ;
	private Produce plotProduce;
	private String plotAddress;
	private String plotStatus;
	private boolean groundCultivated;
	private boolean polluted;
	private String currentProduce;
	private double landValue;
	private LandLord landOwner;
	private double landPrice;
	private double recultivateCost;
	private double monthlyPlotMaintenanceCost;
	
	public LandPlot(EventPriorityQueue pEvtQ,LandLord pOwner,String pPlotAddress)
	{
		plotStatus = POLLUTED; //starts as uncultivated
	
		groundCultivated = false;
		polluted = false;
		landValue = 0;
		evtQ = pEvtQ;
		landOwner = pOwner;
		plotAddress=pPlotAddress;
		setPlotAttributes();
	}
	
	public LandPlot(EventPriorityQueue pEvtQ,LandLord pOwner,String pPlotAddress,boolean pGroundCultivated, double pLandValue)
	{
		if (pGroundCultivated)
		{
			plotStatus = CLEAR;
		}
		else
		{
			plotStatus = POLLUTED;
		}
	
		groundCultivated = pGroundCultivated;
		polluted = false;
		landValue = pLandValue;
		evtQ = pEvtQ;
		landOwner = pOwner;
		plotAddress=pPlotAddress;
		setPlotAttributes();
	}
	
	private void setPlotAttributes()
	{
		monthlyPlotMaintenanceCost=20;	
	}
	
	public boolean cultivateLand(LandLord pOwner) throws BankruptException
	{
		if ((groundCultivated == false) && (landOwner==pOwner))
		{
			if ( landOwner.pay(this.getRecultivateCost(),"Land Plot Cultivation:" + plotAddress) > 0 ) 
			{
				groundCultivated = true;
				polluted = false;  //cultivation reverses a polluted.
				plotStatus = CLEAR;
				//possible modification:
				//place an entry in the eventQueue with the correct time for activation.
				//EventData cultivateGroundEvt = new EventData(CULTIVATE_GROUND,this);
				//evtQ.PriorityEnque(yrlyCalender.getDate() + 1,1,1,cultivateGroundEvt);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	public void createEventEntries()
	{
		
	}
	public boolean cleanUp()
	{
		//remove all entries of this object from the event queue
		evtQ.delqueObj(this); 
		return true;
	}
	public boolean runScheduledActions()
	{
		//reads the event queue for the next event and runs the action
		//Events for Land Plots can be easily activated for more realistic gameplay.
		String strActionCall;
		EventData eventData = evtQ.deque();
		strActionCall=eventData.getActionCall();
		
		if (strActionCall.contentEquals(CULTIVATE_GROUND))
		{
			plotStatus = CLEAR;
			groundCultivated = true;
			polluted = false;
			return true;
		}
		else if(strActionCall.contentEquals(UNPOLLUTE_GROUND))
		{
			plotStatus = CLEAR; //no unpollute option at this stage
			polluted = false;	
			groundCultivated = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean sellLand(LandLord newOwner)
	{
		boolean sold;
		sold = landOwner.recievePayment(this.getSaleValue(),"Land Sale of " + this.plotAddress);
		if (sold == true)
		{
			//if produce exists and the land is sold the value of the produce is surrenderedand.

			groundCultivated = false;
			polluted = true;
			plotStatus = POLLUTED; 
			
			landOwner=newOwner;

		}
		return sold;
	}		
	public boolean buyLand(LandLord newOwner) throws BankruptException
	{
		double purchasedAmt;
		double saleValue;
		
		purchasedAmt = newOwner.pay(this.getSaleValue()," Land Purchase of " + this.plotAddress);
		if (purchasedAmt > 0)
		{
			landOwner=newOwner;
			return true;
		}
		else
		{
			return false;	
		}
	}
	
	public double getSaleValue()
	{
		//returns the current land sale value.
		return landValue; 
	}
	
	public Produce getProduce()
	{
		return plotProduce;
	}
	
	public void destroyPlotProduce()
	{
		produceEffectOnPlot(plotProduce.getStatus());
		plotProduce.cleanUp();
		plotProduce = null;	
	}
	
	private void produceEffectOnPlot(String sProduceStatus)
	{
		if (sProduceStatus == DEAD)
		{
			groundCultivated = false;
			polluted = true;
			plotStatus = POLLUTED; 		
		}
	}
	
	public double calculateThisMonthsCosts()
	{
		//Costs come from land maintenance and produce on the land.
		//Cultivation costs are excluded
		System.out.println("monthly cost="+monthlyPlotMaintenanceCost);
		
		double monthCost=0;
		if (plotProduce != null)
		{
			monthCost=plotProduce.getMonthlyMaintenanceCost();
		}
		
		//charge a fee even if the ground is polluted.
		monthCost=monthCost+monthlyPlotMaintenanceCost;
		
		return monthCost;
		
	}
	public double calculateThisMonthsProfits()
	{
		double monthsRevenue=0;
		if (plotProduce != null)
		{
			monthsRevenue=plotProduce.getMonthlyRevenue();
		}
		return monthsRevenue;
		
	}
	public String getPlotStatus()
	{
		return plotStatus;
	}
	
	public boolean sellProduce(LandLord pOwner)
	{
		boolean sold;
		sold = false;
		
		System.out.println("plotProduce = "+plotProduce.getStatus());
		if ( (landOwner == pOwner) && ((plotProduce.getStatus() == MATURE)||(plotProduce.getStatus() == MATURE_LAST_MONTH)) ) //produce ready for sale
		{
			System.out.println("before payment for produce is made");
			sold = pOwner.recievePayment(plotProduce.getCurrentSaleValue(),plotProduce.PRODUCETYPE+" "+plotProduce.getProduceDescription()+" sale from " + plotAddress);
			this.destroyPlotProduce();  //conceptually it is transferred to the buyer			
			//land plot doesn't need recultivation if mature crops are sold
		}
		else if ( (landOwner == pOwner)&&((plotProduce.getStatus() != MATURE)||(plotProduce.getStatus() != MATURE_LAST_MONTH))  ) //if selling before mature
		{
			sold = true; //no payment can be collected for immature crops
			//sale of immmature crop means land must be recultivated
			groundCultivated = false;
			polluted = true;
			plotStatus = POLLUTED; 
			this.destroyPlotProduce(); 
		}
		return sold;
	}

	public boolean buyProduce(LandLord pOwner,Produce newProduce) throws BankruptException
	{
		double purchaseAndPrepPrice;
		
		if ((landOwner == pOwner)&&(plotProduce == null ))//produce doesn't exist
		{
			//produce is bought for free although a preparation cost applies
			purchaseAndPrepPrice = pOwner.pay(newProduce.getPreparationCost(),
					newProduce.PRODUCETYPE+" "+newProduce.getProduceDescription()
					+" purchase of $0.00 and preparation of $"+newProduce.getPreparationCost()
					+" for " + plotAddress);
			newProduce.assignLandPlot(this);
			plotProduce = newProduce;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isDevelopable()
	{
		if ((groundCultivated==true) && (polluted==false))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public double getLandPrice()
	{
		return landPrice;
	}
	
	public double getRecultivateCost()
	{
		return recultivateCost;
	}
	
	public void setLandPrice(double pLandPrice)
	{
		landPrice= pLandPrice;
	}
	
	public void setRecultivateCost(double pRecultivateCost)
	{
		recultivateCost=pRecultivateCost;
	}
	
	public LandLord getOwner()
	{
		return landOwner;
	}
	
	public String getAddress()
	{
		return plotAddress;
	}
}