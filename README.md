------
README
------

Byron is a multi-game Tic-Tac-Toe, with a running score and a race against  
the clock. The object is to remain alive while as many points as possible.  

A new game begins with five lives and zero points. You're X and Byron is O.
Byron starts first. Each Tic-Tac-Toe is timed. The timer starts at ten and 
counts down to zero. If you win the game, you get one point for every second 
left on the clock. If you lose, a "life" is taken from you. Byron is over 
when no lives remain. You get a new "life" for every 50 points.

Artificial Intelligence.

Byron uses four AI engines, M.E.N.A.C.E., Minimax, Monte Carlo and Reinforced  
Learning.  The first one is based on Donald Michie's Machine Educable Noughts 
And Crosses Engine (MENACE). That was a "computer" constructed of match boxes  
which was capable of "learning" Tic Tac Toe.  

For more info on M.E.N.A.C.E. see http://en.wikipedia.org/wiki/Donald_Michie  

The second engine is based on Minimax which is a decision theory concept designed  
to MINImize a possible loss for a MAXimum loss scenario.  

For more information on MiniMax see http://en.wikipedia.org/wiki/Minimax  

Minimax consistently plays smarter Tic Tac Toe but it's incapable of "learning."  
It plays its first game as well as its last. M.E.N.A.C.E., on the other hand, starts  
dumb and gets smarter. It's rewarded for winning behavior and punished for losing  
behavior.  

Monte Carlo method looks down the search try and tries to find the best possible  
outcomes for itself. In order to be competitive, we limited the amount of time it  
has to search. Monte Carlo is pretty competitve but its limits will give you
opportunities to beat it.

For more information on Monte Carlo see https://en.wikipedia.org/wiki/Monte_Carlo_method

Reinforced learning was trained from scratch. It picked moves at random and then  
we rewarded it for wins and punished it for losses. The engine played 100,000 games  
against MiniMax to "learn" how to play better. The educated enging always picks the  
middle square. To make it more interesting, we have it pick its first square at random  
then it uses its "brain."

For more information on Reinforced Learning see https://en.wikipedia.org/wiki/Reinforcement_learning

M.E.N.A.C.E. generally provides a more enjoyable byron experience. When you place  
against M.E.N.A.C.E. you're playing beat the clock. You try to score as many points  
as possible before it gets too smart. 


Requirements 

Byron requires at least java 1.6. The game itself is bundled in byron.jar.  
On most platforms, you should be able to double-click the file in order to  
play the game. If java is not associated with jar files on your computer, you 
can play the game with the following command:  java -jar byron-1.0.5.jar





