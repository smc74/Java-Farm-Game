import java.io.*;
public interface MultiWindowGameConsole
{	
	public void setPlayerName(String pName);
	public void generateConsole()throws IOException, BankruptException;
	public void completeProducePurchase(int selectedRow);
	public void exitGame();
	public void resetGame();
}