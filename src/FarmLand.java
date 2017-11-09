public class FarmLand implements LandManagement
{
	private LandPlot[][] landPlotCollection;
	private EventPriorityQueue pEvtQ;
	
	FarmLand(Integer xMax,Integer yMax, EventPriorityQueue pEvtQ)
	{		
		double landPrice;
		double recultivateCost;
		double monthlyMaintenanceCost;
		boolean pGroundCultivated = false;
		landPlotCollection = new LandPlot[xMax][yMax];
		LandLord pOwner = null;
		landPrice=1000;
		recultivateCost=300;
		

		for (int xIndex=0; xIndex < landPlotCollection.length; xIndex++)
		{
			for (int yIndex=0; yIndex < landPlotCollection[xIndex].length; yIndex++)
			{

				landPlotCollection[xIndex][yIndex] = new LandPlot(pEvtQ,pOwner
				 ,xIndex + "," + yIndex,pGroundCultivated,landPrice);
				
				landPlotCollection[xIndex][yIndex].setLandPrice(landPrice);
				landPlotCollection[xIndex][yIndex].setRecultivateCost(recultivateCost);				
			}		
		}	
	}

	public String getPlotStatus(Integer x,Integer y)
	{
		return landPlotCollection[x][y].getPlotStatus();
	}
	
	public double getLandValue(Integer x, Integer y)
	{
		return landPlotCollection[x][y].getLandPrice();
	}
	
	public boolean sellLand(Integer xIndex, Integer yIndex, LandLord pOwner)
	{
		if ( pOwner==this.landPlotCollection[xIndex][yIndex].getOwner() )
		{
			return this.landPlotCollection[xIndex][yIndex].sellLand(null);
		}		
		else
		{
			return false;
		}		
	}
	
	public boolean buyLand(Integer xIndex,Integer yIndex,LandLord newOwner) throws BankruptException
	{
		boolean landBought;
		landBought = false;
		Integer plotsOwned;
		
		if ( newOwner!=this.landPlotCollection[xIndex][yIndex].getOwner() )
		{
			plotsOwned = newOwner.getPlotsOwned(); //allow intial land purchase without being a neighbouring plot
			if ((plotsOwned==0) ||(isNeighbour(xIndex, yIndex, newOwner)) )
			{
				System.out.println("BEFORE LANDBOUGHT");
				landBought=this.landPlotCollection[xIndex][yIndex].buyLand(newOwner);
		
			}
		}		
		return landBought;
		
	}
	
	public double calculateThisMonthsCosts(Integer x, Integer y, LandLord pOwner)
	{
		if ( this.landPlotCollection[x][y].getOwner() == pOwner )
		{
			return this.landPlotCollection[x][y].calculateThisMonthsCosts();
		}
		else
		{
			return 0;
		}
	}
	
	public double calculateThisMonthsCosts(LandLord pOwner)
	{
		double totalMonthsCost=0;
		
		for(int xIndex=0; xIndex < landPlotCollection.length; xIndex++)
		{
			for (int yIndex=0; yIndex < landPlotCollection[xIndex].length; yIndex++)
			{
				if( this.landPlotCollection[xIndex][yIndex].getOwner() == pOwner )
				{
					totalMonthsCost = totalMonthsCost + this.landPlotCollection[xIndex][yIndex].calculateThisMonthsCosts();
				}
			}
		}
		return totalMonthsCost;
	}

	public double calculateThisMonthsProfits(Integer x, Integer y, LandLord pOwner)
	{
		if ( this.landPlotCollection[x][y].getOwner() == pOwner )
		{
			return this.landPlotCollection[x][y].calculateThisMonthsProfits();
		}
		else
		{
			return 0;
		}

	}
	public double calculateThisMonthsProfits(LandLord pOwner)
	{
		double totalMonthsProfits=0;
		
		for(int xIndex=0; xIndex < landPlotCollection.length; xIndex++)
		{
			for(int yIndex=0; yIndex < landPlotCollection[xIndex].length; yIndex++)
			{
				if ( this.landPlotCollection[xIndex][yIndex].getOwner() == pOwner )
				{
					totalMonthsProfits = totalMonthsProfits + this.landPlotCollection[xIndex][yIndex].calculateThisMonthsProfits();
				}
			}
		}
		return totalMonthsProfits;
	}
	public boolean sellProduce(Integer x,Integer y,LandLord pOwner)
	{
		return this.landPlotCollection[x][y].sellProduce(pOwner);
	}
	public boolean buyProduce(Integer x,Integer y,Produce newProduce,LandLord pOwner) throws BankruptException
	{
		return this.landPlotCollection[x][y].buyProduce(pOwner,newProduce);
	}
	
	public boolean isNeighbour(Integer pX, Integer pY, LandLord pOwner)
	{
		boolean plotIsNeighbour;
		Integer maxX;
		Integer maxY;
		
		maxX=this.landPlotCollection.length;
		maxY=this.landPlotCollection[pX].length;		
		
		//check to see if the surrounding plots are owned by pOwner
		plotIsNeighbour = false;
		
		// check left side of plot
		if (  ((pX - 1) >= 0 )  && (!plotIsNeighbour) )// left of plot
		{
			if (this.landPlotCollection[pX-1][pY].getOwner() == pOwner)
			{
				plotIsNeighbour=true;
			}
		}
		
		if (  ((pX - 1) >= 0 ) && ( (pY-1) >= 0 ) && (!plotIsNeighbour) ) // left and up of plot
		{
			if (this.landPlotCollection[pX-1][pY-1].getOwner() == pOwner)
			{
				plotIsNeighbour=true;
			}
		}
		
		
		if (  ((pX - 1) >= 0 ) && ( (pY+1) > maxY ) && (!plotIsNeighbour) ) // left and down of plot
		{
			if (this.landPlotCollection[pX-1][pY+1].getOwner() == pOwner)
			{
				plotIsNeighbour=true;
			}
		}
		
		// check top side of plot
		if (  ((pY - 1) >= 0 )  && (!plotIsNeighbour)) // up of plot
		{
			if (this.landPlotCollection[pX][pY-1].getOwner() == pOwner)
			{
				plotIsNeighbour=true;
			}
		}
		
		// check the right side of plot
		if (  ((pX + 1) < maxX ) && (!plotIsNeighbour) ) // right of plot
		{
			if (this.landPlotCollection[pX+1][pY].getOwner() == pOwner)
			{
				plotIsNeighbour=true;
			}
		}
		
		if (  ((pX + 1) < maxX ) && ( (pY-1) >= 0 ) && (!plotIsNeighbour) ) // right and up of plot
		{
			if (this.landPlotCollection[pX+1][pY-1].getOwner() == pOwner)
			{
				plotIsNeighbour=true;
			}
		}
		
		
		if (  ((pX + 1) < maxX ) && ( (pY+1) < maxY ) && (!plotIsNeighbour) ) // right and down of plot
		{
			if (this.landPlotCollection[pX+1][pY+1].getOwner() == pOwner)
			{
				plotIsNeighbour=true;
			}
		}
		
		//check the down side of plot
		
		if (  ((pY + 1) < maxY ) && (!plotIsNeighbour) )// downside of plot
		{
			if (this.landPlotCollection[pX][pY+1].getOwner() == pOwner)
			{
				plotIsNeighbour=true;
			}
		}
		
		return plotIsNeighbour;
	}
	public boolean cultivateLand(Integer x, Integer y, LandLord pOwner) throws BankruptException
	{
		return this.landPlotCollection[x][y].cultivateLand(pOwner);
	}
	public Produce getProduce(Integer x, Integer y)
	{
		return landPlotCollection[x][y].getProduce();
	}
	public boolean isDevelopable(Integer x, Integer y)
	{
		return landPlotCollection[x][y].isDevelopable();
	}
	public boolean isOwned(Integer x,Integer y)
	{
		LandLord owner = landPlotCollection[x][y].getOwner();
		if (owner == null)
		{
			 return false;
		}
		else
		{
			return true;
		}
	}
	

}