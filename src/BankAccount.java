import java.util.Vector;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class BankAccount
{
	private double balance;
	private Vector statement;
	ObservableCalendar clock;
	
	BankAccount()
	{		 
		statement = new Vector(3,3); //inital size 3, increases in 3's
		balance = 0.00;
	}
	
	BankAccount(ObservableCalendar pClock, double openingBalance)
	{
		statement = new Vector(3,3); //inital size 3, increases in 3's
		balance = openingBalance;
		clock=pClock;
	}
	
	public boolean withdraw(double amount,String description) throws BankruptException
	{	
		if (balance >= amount)
		{
			balance = balance - amount;
			statement.add("Date:" + clock.getDate() + "Withdrawal Amount:$"+ amount+" Description: "+description);
			return true;
		}
		else
		{
			throw new BankruptException("Bankrupt: Attempted withdraw of $"+amount+"Description:"+description);
		}
	}
	
	public boolean deposit(double amount, String description)
	{
		if (amount > 0)
		{
			balance = balance + amount;
			statement.add("Date:" + clock.getDate() + "Trans:Deposit Amount:$"+amount+" Description: " + description);
			return true;
		}
		else
		{
			return false;
		}
	}

	public double getBalance()
	{
		return balance;
	}

	public double pay(double amount, String description) throws BankruptException
	{
		if (balance >= amount)
		{
			balance=balance - amount;
			statement.add("Date:" + clock.getDate() + "Trans:Pay Amount:$"+ amount+" Description: "+description);
			return amount;
		}
		else
		{
			throw new BankruptException("Bankrupt: Attempted Payment of $"+amount+" Description:"+description);
		}	
	}
	
	public void writeStatement(String filePath)
	{ 
		//write
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
		for(int index=0; index < statement.size(); index++)		
		{
			outputStream.println("Item No:"+index+" : "+statement.elementAt(index));
		}
		outputStream.close();
	}
}