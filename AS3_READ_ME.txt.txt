CPT23 - Assignment 3 - Farm Game

- The game fully works but the interface is still incomplete. I spent a fair bit 
of time trying to get the panel for the plots using gridBagLayout to refresh.  This still isn't working.

-I have written what I have time to do.

-The interface links into the game, the initial state (which I haven't progressed further) is from the game.


UPDATED

I still can get this to refresh.  I am moving in the direction of deleting everything and then rebuilding the screen
representation every time there is a change to the game environment.

Exception handling needs a tidy up

Produce needs it's own dialog for the choice of produce.


UPDATE

I am still having trouble getting this to refresh.  It will reresh for Cultivation and sale of a plot but the
display will not refresh for purchase of produce.

I have included the ability to add produce

The game information updates

The game works underneath, the only thing not working is the display of the plots for produce.


UPDATE 23_03_2007
Found what the issues was.  The decision logic was incorrect for updating the farm game console.  Not something more complicated
as I thought.  Refreshing now works properly.

Next thing will be to :
cleanup, 
comment briefly, 
write some explanation in a help section, 
maybe put in a bank statement section where all transactions can be viewed.


Brainstorming as to how this could be used:
Need to think what will be most impressive thing to do with this as this could be uploaded to a web site to gain work in Java.
Could form the basis for other java games. ie chess, Battle ships, reversi.  Be good to show polymorphism ie. try and develop 
a common engine that a number of games can be loaded into. Pac Man wouldn't be that hard either.

3D Battleships?  turn the 2D into 3D using Java?

Could also network the games so you can play another player ie. for battleships, chess, reversi.


Could also form the basis for a more complicated tool to simulate farm cycles ie as the basis of an analytical tool.


make it look more attractive
