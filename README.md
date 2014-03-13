# cs56-games-maze

A program that generates a solvable rectangular maze at random. 

Before W14:
* [Archive page](https://foo.cs.ucsb.edu/cs56/issues/0000769/)
* [Mantis page](https://foo.cs.ucsb.edu/56mantis/view.php?id=769)

After W14:
* [Javadoc page] (http://www.cs.ucsb.edu/~loganortega/cs56/W14/loganortega/cs56-games-maze/javadoc)

## User View

The game creates a maze slower than the actual maze-generation time, so that you can watch it grow. It is the user's job to then solve it. The algorithm used to generate the maze is based off of the recursive back-tracker algorithm. This algorithm is modified modified, however, to run first off of the newest added cell, then the oldest added cell in each iteration. This form of the algorithm generates a sort of branching effect from the point of origin. I also modified the algorithm to be able to randomly spawn new points of origin at a certain specified frequency (from command line arguments; jws uses default values) as the algorithm runs. This creates a sort of "bushy", more complex maze with more grequent path branching.

To see how the recursive backtracker works, visit http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap

Changes made to settings take effect on generation of a new maze.

## Playing the Game:

To launch the game simply use the command:
```
ant run
```
### Gameplay

![](http://i.imgur.com/eve3g50.png)

The player always starts in the top left corner, and must always traverse to the lower right corner (unless specified otherwise by the user). Use WASD keys to move the player.

In Progressive Reveal mode, only a small portion of the maze is revealed to the player at a time and all visited cells are retained as visible. 

At any time, mazes can be saved and resumed later, from the same position and with the same elapsed time.

### Sharing

Upon winning a maze, players can save the maze along with their name and time. If this file is then loaded by another player, they will be prompted to beat the previous best score and save their score to the file. 

### Possible Improvements


