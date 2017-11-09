interface PriorityQueue
{
	public void priorityEnqueue(Integer time, Integer objectOrder,Integer subObjectOrder, EventData data);
	public boolean isEmpty();
	public EventData deque();
	public EventData peek();
	public boolean queueFull();
	public boolean delqueObj(Schedule refObj); //deletes all queue entries of refObj
}