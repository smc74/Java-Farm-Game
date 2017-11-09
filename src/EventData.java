class EventData
{
	private String strActionCall;
	private Schedule refToObject;
	private Integer actionTime;
	
	EventData(String pStrActionCall, Schedule pRefToObject,Integer pActionTime)
	{
		strActionCall=pStrActionCall;
		refToObject=pRefToObject;
		actionTime=pActionTime;
	}
	
	public String getActionCall()
	{
		return strActionCall;
	}
	
	public Integer getActionTime()
	{
		return actionTime;
	}

	public Schedule getReferenceToObject()
	{
		return refToObject;
	}
}