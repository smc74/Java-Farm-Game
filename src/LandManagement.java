interface LandManagement
{
	public String getPlotStatus(Integer x,Integer y);
	public double getLandValue(Integer x,Integer y);
	public boolean sellLand(Integer xIndex,Integer yIndex,LandLord pOwner);
	public boolean buyLand(Integer xIndex,Integer yIndex,LandLord pOwner) throws BankruptException;

}