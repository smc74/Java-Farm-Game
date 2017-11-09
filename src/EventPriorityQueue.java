import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

class EventPriorityQueue implements PriorityQueue
{
	private EventPriorityNode head;
	private EventPriorityNode tail;
	
	EventPriorityQueue()
	{
	}
	
	public void priorityEnqueue(Integer time, Integer objectOrder,Integer subObjectOrder, EventData data)
	{
		EventPriorityNode current = head;
		EventPriorityNode previous = null;
		EventPriorityNode newNode = new EventPriorityNode(time,objectOrder,subObjectOrder,data);
		EventPriorityNode oldCurrent=null;
		boolean itemPlaced = false;
		boolean fowardToNext = false;
		
		System.out.println("queue : isEmpty()  "+isEmpty());
		if (isEmpty()) //first entry
		{
			head=newNode;
			System.out.println("newNode ="+newNode+"  head="+head);
			itemPlaced=true;
		}
		while (!itemPlaced) 
		{
			fowardToNext=false;
			if (current.getPriorityTime() >= time ) 
			{
				fowardToNext=true;
			}	
			if ((current.getObjectOrderTime() >= objectOrder) && (current.getPriorityTime() == time ) )
			{
		  		fowardToNext=true;
		  	}
		  	if ((current.getSubObjectOrder() >= subObjectOrder) && ((current.getObjectOrderTime() == objectOrder) &&  (current.getPriorityTime() == time ) )  )
			{
				fowardToNext=true;
			}


			if ((fowardToNext) && (current.getNext() !=null)) //keep moving forward, otherwise place in current position
			{
				previous = current;
				current = current.getNext();
			}
			else //else place the item immediately after the current item
			{
				//Place the item after current position.
				//If all the priorities are the same then FIFO applies
				newNode.setNext(current.getNext());
				current.setNext(newNode);
				//place the new node as the current node				
				newNode.setPrevious(current);
				current=newNode;
				if(current.getNext()==null)
				{
					tail=current;	
				}
				itemPlaced=true;
				System.out.println("Previous="+current.getPrevious()+"  current="+current+"  next="+current.getNext());
			}

		}		
	}
	
	public boolean isEmpty()
	{
		return (head==null);
	}
	public EventData deque()
	{
		if (head==null)
			return null;
		else
		{
			EventData temp = head.getData();
			head = head.getNext();
			return temp;
		}
	}
	public EventData peek()
	{
		if (!isEmpty())
		{
			return head.getData();
		}
		else
		{
			return null;
		}
		
		
	}
	
	public boolean queueFull()
	{
		return false;
	}
	
	public boolean delqueObj(Schedule refObj) //deletes all queue entries of refObj
	{
		EventPriorityNode current = head;
		EventPriorityNode previous = null;
		
		EventPriorityNode newCurrent = null;
		
		EventData currentData = null;
		boolean itemPlaced = false;
		boolean deletedItem = false;
		boolean moveForward = false;
		Schedule currentObject=null;
		
		System.out.println("queue : isEmpty()  "+isEmpty());

		while ( (!this.isEmpty()) && (current != null) )
		{
			currentData=current.getData();
			currentObject=currentData.getReferenceToObject();
			
			if (currentObject == refObj ) 
			{
				System.out.println("Inside currentObject==refObj");
				
				moveForward = false; //removal of the item moves foward through the list. 
				deletedItem=true;
				//delete the item by orphaning it

				if (current==head)
				{
					head=current.getNext(); //returns next item and null if tail
				}
				
				if (current==tail)
				{
					tail=null;  //orphan the tail reference
				}
				
				//previous=current
				if (previous!=null)
				{
					previous.setNext(current.getNext());
				}
				
				newCurrent=current.getNext();
				if (newCurrent != null)
				{
					newCurrent.setPrevious(previous);  //=current.getNext();
				}
								
				current=newCurrent;
												
			}	
			else // move forward to the next record.
			{
				moveForward = true;
			}

			System.out.println("delete entries for object at "+ currentObject +"from the event shedule");
			if (moveForward)
			{				
				previous = current;
				if (current!=null)
				{
					current = current.getNext();
				}
			}

		}
		return deletedItem;
	}
	
	public boolean actionEvents(Integer date)
	{
		EventData waitingEvent;
		Schedule scheduledObject;
		boolean itemsFound=false;
		
		waitingEvent=this.peek();
		if (waitingEvent != null)
		{
			while ((!this.isEmpty()) && (waitingEvent.getActionTime() == date))
			{
				scheduledObject=waitingEvent.getReferenceToObject();
				scheduledObject.runScheduledActions();
				waitingEvent=this.peek(); //peek the next item in the shedule							
			}
			return true;
			
		}
		else
		{
			return false;
		}
	}
	
	public void writeQueue(String filePath)
	{
		//write
		EventPriorityNode current=null;
		EventData dataItem;
		Schedule scheduledObject=null;
		Integer queueIndex=0;			
		
		PrintWriter outputStream =null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream(filePath));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file "+filePath);
			System.exit(0);
		}
		
		current = head;
		while (current!=null) 
		{
			dataItem =	current.getData();
			scheduledObject=dataItem.getReferenceToObject();
			
			outputStream.println("Item No:"
			+queueIndex+" Priority:"+current.getPriorityTime()+" Object Order:"
			+current.getObjectOrderTime()+" Sub Object Order:"+current.getSubObjectOrder()
			+" Action call:"+dataItem.getActionCall()+" Instance of:"
			+scheduledObject.getClass());
						
			current=current.getNext();
			queueIndex = queueIndex + 1;
		}
		outputStream.close();
	}	
}