interface LandLord
{
	public double pay(double amount, String description) throws BankruptException;
	public boolean recievePayment(double amount,String pDescription);
	public boolean sellLand(Integer xIndex, Integer yIndex);
	public Integer getPlotsOwned();
}