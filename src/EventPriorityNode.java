class EventPriorityNode
{
	private Integer priorityTime;
	private Integer objectOrder;
	private Integer subObjectOrder;
	private EventPriorityNode previous=null;
	private EventPriorityNode next=null;
	//private EventPriorityNode current=null;
	private EventData eventEntry;
	
	public Integer getPriorityTime()
	{
		return priorityTime;
	}
	
	public Integer getObjectOrderTime()
	{
		return objectOrder;
	}
	
	public Integer getSubObjectOrder()
	{
		return subObjectOrder;
	}
	
	EventPriorityNode(Integer pTime,Integer pObjectOrder,Integer pSubObjectOrder,EventData pData)
	{
		priorityTime=pTime;
		objectOrder=pObjectOrder;
		subObjectOrder=pSubObjectOrder;
		eventEntry=pData;

	}
	
	public EventPriorityNode getNext()
	{
		return next;
	}
	
	//public EventPriorityNode getCurrent()
	//{
	//	return current;
	//}
	
	public EventPriorityNode getPrevious()
	{
		return previous;
	}
	
	public EventData getData()
	{
		return eventEntry;
	}
	
	public void setNext(EventPriorityNode aNode)
	{
		next=aNode;
	}
	
//	public void setCurrent(EventPriorityNode aNode)
//	{
//		current=aNode;
//	}
	
	public void setPrevious(EventPriorityNode aNode)
	{
		previous=aNode;
	}
}