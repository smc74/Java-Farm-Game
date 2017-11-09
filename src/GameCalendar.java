public class GameCalendar implements ObservableCalendar
{
	protected Integer date;
	GameCalendar()
	{
		date = 0;
	}
	public void incrementMonth()
	{
		date = date + 1;
	}
	public Integer getDate()
	{
		return date;
	}
	
	public String getDateString()
	{
		int month=0;
		int year=0;
		String sMonth=null;
		
		month=(date%12) + 1;
		year=2000 + ((int)(date/12) );
		
		switch(month)
		{
			case(1):
				sMonth="January";
				break;
			case(2):
				sMonth="February";
				break;
			case(3):
				sMonth="March";
				break;
			case(4):
				sMonth="April";
				break;
			case(5):
				sMonth="May";
				break;
			case(6):
				sMonth="June";
				break;
			case(7):
				sMonth="July";
				break;
			case(8):
				sMonth="August";
				break;
			case(9):
				sMonth="September";
				break;
			case(10):
				sMonth="October";
				break;
			case(11):
				sMonth="November";
				break;
			case(12):
				sMonth="December";
				break;			
		}
		
		return String.valueOf(sMonth+", "+year);
	}

}