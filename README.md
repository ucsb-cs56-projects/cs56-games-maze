# cs56-games-maze

A program that generates a solvable rectangular maze at random. 

Before W14:
* [Archive page](https://foo.cs.ucsb.edu/cs56/issues/0000769/)
* [Mantis page](https://foo.cs.ucsb.edu/56mantis/view.php?id=769)

After W14:
* [Javadoc page] (http://www.cs.ucsb.edu/~loganortega/cs56/W14/loganortega/cs56-games-maze/javadoc)

## User View

### Maze Generation
The game creates a maze slower than the actual maze-generation time, so that you can watch it grow. It is the user's job to then solve it. The algorithm used to generate the maze is based off of the recursive back-tracker algorithm.  If the user changes maze settings, the generation of the maze responds appropriately.

### Saving, Loading and Sharing
The user has the option to save an in-progress game at any point during gameplay and has the option to load any previously saved game with the same position and time as when saved. Upon winning a maze, players can save the maze, along with their name and time. If this file is then loaded by another player, they will be prompted to beat the previous best time and save their name and time to the maze. 

### Gameplay
To launch the game simply use the command:
```
ant run
```
#### Navigation
The player always starts in the top left corner, and must always traverse to the lower right corner, unless specified otherwise by the user, by using WASD keys to move the player marker (bold square).

#### Progressive Reveal
In Progressive Reveal mode, only a small portion of the maze is revealed to the player at a time and all visited cells are retained as visible. 

#### New Button
The user can abandon a maze in progress and start a new maze by pressing the New Button. 

#### Solve Button
If at any time the player is stumped and is interested in the maze's solution, the player has the option to click the solve button to see a highlighted path of solution. 

#### How to Button
The user has the option 
![](http://i.imgur.com/eve3g50.png)


## Developers View

The maze generating algorithm is modified from the recursive back-tracker algorithm. It is set to run first off of the newest added cell, then the oldest added cell in each iteration. This form of the algorithm generates a sort of branching effect from the point of origin. In addition, the modified algorithm has the ability to randomly spawn new points of origin at a certain specified frequency, either from command line arguments or default values, as the algorithm runs. This creates a sort of "bushy", more complex maze with more frequent path branching.

* [More Algorithm Info] (http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap)






