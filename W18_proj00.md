### Winter 2018
* Benson Chu
* Willy Tu

### Project Description:

The project is a maze game that provides users with a randomly generated maze to play with. The users can traverse through 
the maze by either using the WASD keys or the arrow keys on their keyboards. The goal is to reach the end of the maze, 
indicated by a colored square object located at the bottom right of the entire maze. The users are able to customize the 
maze via color themes and keyboard modes to have a unique way of playing the game. 

### Current User Stories:

* As a user, I can save my score for the maze run so that I can compare it to my previous score or to 
other players.

* As a user, I can check the solution of the maze from the start so that I know the way to get out of the maze.
	
* As a user, I can load up a saved game so that I can re-watch my personal run or another person's maze run

* As a user, I can look up the instructions of the game so that I know the controls for the maze game.
	
* As a user, I can change the color of the board to other default colors so that it looks better to play on.

* As a user, I can vary the dimensions of the maze in the settings option to either increase or decrease the maze's difficulty.
	
### Software:

  The program runs well with little to no functional bugs. The main issues of the program are the user interfaces, 
but other than that no large issues. The program randomly generates a maze of a given size that can be changed in 
the game settings. Users can use either WASD or the arrow keys to control the player's avatar to reach the end of the 
maze. The game provides a few different ways to play the game such as inverse control, random control, and memory mode.
	
### New User Stories:
* As a user, I can customize the board with any color so that I have full control of what the maze will look like.
* As a user, I can play against an AI enemy so that it increases the difficulties of the maze.
* As a user, I can select the difficulty of the maze so that I have simplified options to choose from.
* As a user. I can check the solution from my current location so that I know exactly how to get to the end of the maze.
	
### Current README.md

  The current README.md is well formatted, however, it is very outdated. The information for the Winter 2016 updates are no
where to be found in the readme. All the new features of the program should be listed, so it is easier for people to understand
the functionality of the program.

### build.xml
  The targets all have descriptions that describe what they do and are easy to understand.
	
### Current Issues
  There are enough issues to earn 1000 points. The instruction and expectation of the issues are clear and easy to understand.
	
### New Issues
* <https://github.com/ucsb-cs56-projects/cs56-games-maze/issues/68>

* <https://github.com/ucsb-cs56-projects/cs56-games-maze/issues/69>

* <https://github.com/ucsb-cs56-projects/cs56-games-maze/issues/70>

* <https://github.com/ucsb-cs56-projects/cs56-games-maze/issues/71>

* <https://github.com/ucsb-cs56-projects/cs56-games-maze/issues/72>

* <https://github.com/ucsb-cs56-projects/cs56-games-maze/issues/73>

* <https://github.com/ucsb-cs56-projects/cs56-games-maze/issues/74>

### Current Code

The current code is well organized and documented. Class names are coherent enough to understand 
because of the naming convention each has. It is obvious how all the classes relate to each other just by looking at 
each class's name. Each method's name is clear enough to understand the code's purpose and/or functionality. Classes 
are neatly divided for the most part, but the MazeGui.java is really large. The MazeGui.java is somewhat unclear because 
it mostly consists of setting up the gui. Within the MazeGui.java class is a Sound class that involves running music 
in the game as well as a KeyBoardAction class that handles the keyboard controls for the game. other than that all the 
other classes are clean and easy to understand.



### Test Coverage
There exists a minimum amount of test coverage only for the basic functionality of the maze such as the grids, 
player directions, and player movements. However, all the newer features do not have any test coverage at all.
master

