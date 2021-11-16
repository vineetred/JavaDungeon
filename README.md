# JavaDungeon

## Todo
1. Add Otyugh
   1. Might want to create a class and maybe an interface too
   2. Pop one at the end
   3. See how we do random treasure, do same as that since both require caves as the only place where stuff can be held
   4. BFS situation - Drop smell units in the caves away from where step 3 happens. 
   5. Maintain health also of the Otyugh!
2. Add arrow functionality!
   1. Randomly spread arrows throughout the maze; read monster and treasure addition for this
3. Need to take number of monster as a parameter!
4. Tests for smell :(.

# Overview
JavaDungeon is a game that consists of a maze dungeon and a player who tries to navigate through 
this maze.

The world for our game consists of a dungeon, a network of tunnels and caves that are interconnected 
so that player can explore the entire world by traveling from cave to cave through the tunnels 
that connect them.


# Features
* Generate a dungeon via different ways. One can generate a non-random deterministic 5 x 6 
  maze or by specifying commands through user input.
* A wrapping or non-wrapping dungeon with varying interconnectivity can be generated based on 
  the user's wishes.
* A player can also then be created by having a dungeon assigned to them. This means that only 
  one player can ever occupy a given dungeon.
* We also automatically find start and end points via a simple graph search, all the while 
  ensuring that it would take a player at least 5 moves to successfully reach the end point from 
  the start.
* The driver class basically controls all the classes to create dungeons and players. It is also 
  responsible for checking if the game is over.
* A Point2D interface allows us to easily interact with the model when passing/receiving 
  location data of a certain object within the maze.
* All the output is parsed by the driver which is then printed to the console.


# Execution
## JAR
* Just run the JAR file like so ```java -jar Dungeon.jar``` and the program should several 
  demo runs that show the different styles of this game.
*  It ends with a run where the user must provide it with dungeon parameters to generate a new 
   dungeon. It tries to make a move North and sees what happens, before the simulation ends.

# Usage
* ```DungeonImpl``` - The constructor of the dungeon arena itself. Many ways to achieve this but 
  the easiest way to do it is specify all the different parameters in the order mentioned 
  in the method signature.
* ```getStartPoint``` - Get the randomly assigned start point in a Point2D object.
* ```getEndPoint``` - Get the randomly assigned end point in a Point2D object.
* ```getMovesAtCaveIndex``` - Gets the possible moves at the specified point 2D object before 
  returning a List of directions that one can take from the given location.
* ```peekCaveTreasure``` - Peek into the cave's treasure. 
* ```expungeCaveTreasure``` - Returns all the treasure that exists within a cave for it to be 
  used by the player.
* ```pickUpTreasure``` - Takes a List of treasure and assigns its contents to the player.
* ```getPlayerTreasure``` - Returns a copy of the treasure that is held by the player.
* ```getPlayerLocation``` - Returns a Point2D object that specifies the current location of the 
  player within the maze.
* ```moveNorth``` - Move one cave over to the north.
* ```moveSouth``` - Move one cave over to the south.
* ```moveEast``` - Move one cave over to the east.
* ```moveWest``` - Move one cave over to the west.

# Example

Run 1 -- ExampleRun1.txt:
* We create a 5 x 6 (row x column) non-wrapping maze with an interconnectivity degree of 0, along 
with a treasure threshold
of 20%. Since the graph used is a predetermined non-random graph generated using a seed, we know the shortest path
that the user can take, which is what we simulate. We also check if there is treasure at every place and try to pick it
up if it exists. We also print the possible moves, player treasure that they have hoarded at every move. We also reach
the very end of this maze, checking if the game has ended after every move. This run also prints the location and
player details every single move.
* The treasure generated in the driver run is pseudo-random without specifying any seed. Hence, it most likely
  will not match the treasure that you see in the example run text file. Please note that this is a parameter that can
  be specified to the default constructors.

Run 2 -- ExampleRun2.txt:
* We create a 5 x 6 (row x column) a wrapping and non-wrapping maze with an interconnectivity degree of 0, along with a treasure threshold
  of 20%. Since the graph used is a predetermined non-random graph generated using a seed, we know the shortest path
  that the user can take. We simply have this test run to see if a user can create a non-wrapping as well as wrapping
  dungeon.
* The treasure generated in the driver run is pseudo-random without specifying any seed. Hence, it most likely
  will not match the treasure that you see in the example run text file. Please note that this is a parameter that can
  be specified to the default constructors.
* I have a welcome message at the start of the driver code, which gets chopped of otherwise. I simply appended it
  here.

Run 3 -- ExampleRun3.txt:
* We create a 4 x 3 (row x column) non-wrapping maze with an interconnectivity degree of 0, along with a
  treasure threshold of 20%. Since the graph used is a predetermined non-random graph generated using a seed,
  we know the shortest path that the user can take, but, our goal is to navigate all the locations. Hence, we visit every
  node within this run.
* The treasure generated in the driver run is pseudo-random without specifying any seed. Hence, it most likely
  will not match the treasure that you see in the example run text file. Please note that this is a parameter that can
  be specified to the default constructors.


Run 4 -- ExampleRun4.txt:
* We create a 9 * 9 dungeon with some parameters. This run just demonstrates that a user can
  create a dungeon via user input.
* The treasure generated in the driver run is pseudo-random without specifying any seed. Hence, it most likely
  will not match the treasure that you see in the example run text file. Please note that this is a parameter that can
  be specified to the default constructors.



# Model Changes
* Most major changes were essentially abstracting the functionality away from the user. A lot of
  the original plan revolved around classes communicating directly with other classes. Adding a
  layer of abstraction allows easier understand of the underlying code. This is exactly what I do.
* A major change was that I took away the idea of differentiating tunnels and caves as their only 
  invariant is the ability to hold treasure!
* I also abstracted the coordinates from within the cave and edge objects into a new class of 
  their own, along with their interface.
* Treasure is now an ENUM with an interface. This allows us to have unlimited number of treasure 
  things without having to worry about types.

# Testing Plan
* Please refer to the testing source folder for more details.

# Assumptions
* The model refers to the three public interfaces within the model. The entire game can only be 
  played when the Driver controls the methods within these interfaces.
* While there is a threshold for the number of caves that can get treasure, we don't enforce any 
  such invariants when dealing with the type of treasure itself. It is possible that a certain 
  dungeon is populated with the same type of treasure through its caves.
* Testing is done using a deterministic seed. We assume that it would work as expeceted when the 
  seed is not specified anymore.

# Limitations
* To move, the dungeon can only provide the possible moves from a given location. It is the 
  prerogative of the player to initiate it from within its own public class.
* Better error handling when invariants with respect to the dungeon parameters such that we ask 
  the user for new parameters if the old ones are not valid.

# Citations
* Oracle. (2021, October 23). *Java documentation*. Oracle. Retrieved October 3, 2021, from
  https://docs.oracle.com/en/java/. 