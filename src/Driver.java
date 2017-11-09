/**
 * A console based driver class for the farm game.<p>
 *
 * For information about what the game board display means, look at the printBoard() documentation.<p>
 * 
 * @version 1.1
 * @author  Peter Tilmanis
 */
import java.io.*;

public class Driver
{
   static Controllable game;
   static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
   
   /**
    * The main execution method.
    */
   public static void main(String[] args) throws IOException, BankruptException
   {
      // get the player name, create the controller
      System.out.print("Enter the player name: ");
      String playerName = stdin.readLine();
      
      // create a controller instance. this will need to change to whatever is necessary for
      // your controller to be properly initialised.
      game = new Controller(playerName);
      
      // repeat the menu as long as necessary
      boolean quit = false;
      while ( !quit)
      {
         // display an information header
         System.out.println();
         System.out.println("===========================================================");
         //System.out.print("Date: " + GameCalendar.getDateString());
         System.out.print("Date: " + getDateString());
         System.out.println("\t\t" + game.getName() + "'s worth: $" + game.getWorth());
         System.out.println("===========================================================");
         System.out.println();
         
         // print the game board
         printBoard();
         
         // display the menu
         System.out.println("Menu");
         System.out.println("-------------------------------------------");
         System.out.println("1. Buy Land                 4. Buy Produce");
         System.out.println("2. Sell Land                5. Sell Produce");
         System.out.println("3. Recultivate Land         6. Next Month");
         System.out.println("                            7. Quit");
         System.out.println("										8. Dump Bank Statement");
         System.out.println("										9. Dump Priority Queue");
         System.out.println();
         
         // ask for an option
         System.out.print("Enter an option: ");
         int option = Integer.parseInt(stdin.readLine());
         
         try
         {
            // transfer control based on the option
            switch(option)
            {
               case 1:
                  buyLand();
                  break;
               case 2:
                  sellLand();
                  break;
               case 3:
                  recultivateLand();
                  break;
               case 4:
                  buyProduce();
                  break;
               case 5:
                  sellProduce();
                  break;
               case 6:
                  game.advanceCalendar();
                  break;
               case 7:
                  quit = true;
                  break;
               case 8:
               	Controller gameControl=(Controller)game;
               	gameControl.writeBankStatement("C:\\BankStatement.txt");
               	break;
               case 9:
               	Controller gameControl2=(Controller)game;
               	gameControl2.writeQueue("C:\\PriorityQueue.txt");
               	break;
            }
         }
         catch (BankruptException e)
         {
            System.out.println("You are bankrupt!");
            System.out.println("Reason: " + e.getMessage());
            quit = true;
         }
      }
      
      System.out.println("Thankyou for playing.");
   }

	public static String getDateString()
	{
		return "Today";
	}
   
   /**
    * A method to ask the user questions about buying produce.
    */
   public static void buyProduce() throws IOException, BankruptException
   {
      int x, y;
      char prodChar;
      Produce produce = null;
      
      // get info on where to buy
      System.out.print("Enter the x coordinate of land: ");
      x = Integer.parseInt(stdin.readLine());
      
      System.out.print("Enter the y coordinate of land: ");
      y = Integer.parseInt(stdin.readLine());

      // get info on what to buy
      System.out.print("Purchase (W)heat, (A)pples, (C)ows, (S)heep? ");
      prodChar = stdin.readLine().charAt(0);
      
      // create the produce object
      switch (prodChar)
      {
         case 'w':
         case 'W':
            produce = new Wheat();
            break;
         case 'a':
         case 'A':
            produce = new Apple();
            break;
         case 'c':
         case 'C':
            produce = new Cow();
            break;
         case 's':
         case 'S':
            produce = new Sheep();
            break;
         default:
            System.out.println("That is not a valid produce selection.");
            break;
      }
      
      if (produce != null)
      {
         // try to buy the produce
         if (game.buyProduce(x, y, produce))
            System.out.println("The produce at " + x + ", " + y + " was successfully bought.");
         else
            System.out.println("The produce at " + x + ", " + y + " could not be bought.");
      }         
   }
   
   /**
    * A method to ask the user questions about buying land.
    */
   public static void buyLand() throws IOException, BankruptException
   {
      int x, y;
      
      // get info on where to buy
      System.out.print("Enter the x coordinate of land: ");
      x = Integer.parseInt(stdin.readLine());
      
      System.out.print("Enter the y coordinate of land: ");
      y = Integer.parseInt(stdin.readLine());

      // try to buy land
      if (game.buyLand(x, y))
         System.out.println("The land at " + x + ", " + y + " was successfully bought.");
      else
         System.out.println("The land at " + x + ", " + y + " could not be bought.");
   }

   /**
    * A method to ask the user questions about recultivating land.
    */
   public static void recultivateLand() throws IOException, BankruptException
   {
      int x, y;
      
      // get info on where to recultivate
      System.out.print("Enter the x coordinate of land: ");
      x = Integer.parseInt(stdin.readLine());
      
      System.out.print("Enter the y coordinate of land: ");
      y = Integer.parseInt(stdin.readLine());

      // try to recultivate land
      if (game.recultivate(x, y))
         System.out.println("The land at " + x + ", " + y + " was successfully recultivated.");
      else
         System.out.println("The land at " + x + ", " + y + " could not be recultivated.");
   }

   /**
    * A method to ask the user questions about selling land.
    */
   public static void sellLand() throws IOException, BankruptException
   {
      int x, y;
      
      // get info on where to sell
      System.out.print("Enter the x coordinate of land: ");
      x = Integer.parseInt(stdin.readLine());
      
      System.out.print("Enter the y coordinate of land: ");
      y = Integer.parseInt(stdin.readLine());

      // try to sell land
      if (game.sellLand(x, y))
         System.out.println("The land at " + x + ", " + y + " was successfully sold.");
      else
         System.out.println("The land at " + x + ", " + y + " could not be sold.");
   }

   /**
    * A method to ask the user questions about selling produce.
    */
   public static void sellProduce() throws IOException, BankruptException
   {
      int x, y;
      
      // get info on where to sell
      System.out.print("Enter the x coordinate of land: ");
      x = Integer.parseInt(stdin.readLine());
      
      System.out.print("Enter the y coordinate of land: ");
      y = Integer.parseInt(stdin.readLine());

      // try to sell produce
      if (game.sellProduce(x, y))
         System.out.println("The produce at " + x + ", " + y + " was successfully sold.");
      else
         System.out.println("The produce at " + x + ", " + y + " could not be sold.");
   }

   /**
    * A method to print the game board.<p>
    *
    * Each land plot is made of three characters:<p>
    * <b>First Character: land status</b>
    * <blockquote>
    *    . -- vacant<br>
    *    L -- leased<br>
    *    O -- owned<br>
    * </blockquote>
    *
    * <b>Second Character: produce status</b>
    * <blockquote>
    *    . -- no produce<br>
    *    ! -- produce is about to die<br>
    *    C -- cows<br>
    *    S -- sheep<br>
    *    W -- wheat (lowercase for immature wheat)<br>
    *    A -- apples (lowercase for immature apples)<br>
    * </blockquote>
    *
    * <b>Third Character: development status</b>
    * <blockquote>
    *    . -- clean<br>
    *    X -- polluted<br>
    * </blockquote>
    *
    */
   public static void printBoard()
   {
      // draw the x-coordinate heading
      System.out.print("\t\t ");
      for(int i = 0; i < game.getXSize(); i++)
         System.out.print(i + "\t ");
      System.out.println();
      System.out.println();
      
      for (int y = 0; y < game.getYSize(); y++)
      {
         // draw the y-coordinate heading
         System.out.print("\t" + y + "\t");
         
         for (int x = 0; x < game.getXSize(); x++)
         {
            if (game.isOwned(x, y))
               System.out.print("O");
            else
               System.out.print(".");
            
            // display produce
            Produce produce = game.getProduce(x, y);
            
            if (produce == null)
               System.out.print(".");
            else if (produce instanceof Cow)
               System.out.print("C");
            else if (produce instanceof Sheep)
               System.out.print("S");
            else if (produce instanceof Wheat)
            {
               if ( ((Crop) produce).getStatus() == Crop.IMMATURE )
                  System.out.print("w");
               else if ( ((Crop) produce).getStatus() == Crop.WARNING )
                  System.out.print("!");
               else
                  System.out.print("W");
            }
            else if (produce instanceof Apple)
            {
               if ( ((Crop) produce).getStatus() == Crop.IMMATURE )
                  System.out.print("a");
               else if ( ((Crop) produce).getStatus() == Crop.WARNING )
                  System.out.print("!");
               else
                  System.out.print("A");
            }
            
            // display pollution
            if (game.isDevelopable(x, y))
               System.out.print(".\t");
            else
               System.out.print("X\t");
         }
         
         System.out.println();
         System.out.println();
      }
   }
   
}
