# JavaDungeon

# TODO
1. Create a JMenu that starts a game. So probably a wrapper around the params we give in the driver?

# Overview
JavaDungeon is a game that consists of a maze dungeon and a player who tries to navigate through 
this maze. There are many obstacles that are thrown the player's way; all of which the player 
must fight through.

The world for our game consists of a dungeon, a network of tunnels and caves that are interconnected 
so that player can explore the entire world by traveling from cave to cave through the tunnels 
that connect them.

The player must also fight Otyughs - a type of monster that is native to the maze dungeon! You must
kill them before you can win!


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
* We also have a controller that controls all the aspects of the game; providing an interface for the game.
* The driver allows the user to specify the dungeon parameters that they would like, which is then fed into the controller.
* Monsters such as Otyughs lurk the maze. A player will get killed if they meet a healthy Otyugh. 50% chance of survival
  if they meet an injured Otyugh.
* Arrows can be found throughout the maze, a tool used to slay Otyugh's.


# Execution
## JAR
* Just run the JAR file like so ```java -jar Dungeon.jar <WRAP STRING PARSED AS BOOL> <ROWS INT> 
  <COLS INT> 
  <INTERCONNECTIVITY INT> <TREASURE THRESHOLD INT> <NUMBER OF MONSTERS INT>``` and the program 
  should launch with the given dungeon params, allowing one to play.
* Example - ```java -jar Dungeon.jar true 7 7 1 55 3```.
* Keep in mind that the direction input is _always_ one of four symbols of the cardinal 
  direction, namely, N S E W.
* Can be safely exit-ed if the user wins, dies, or quits.

# Usage
## Model
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
* ```isMinor/MajorSmell``` - Tells if the given cave has some smell in it
* ```peekCaveCrookedArrows``` - Peek into the cave's arrow collection.
* ```expungeCaveCrookedArrows``` - Returns all the arrows that exists within a cave for it to be
  used by the player.
* ```shootCrookedArrow``` - Charts a path for a potential arrow and returns the result based on what happens.
* ```resetSmell()``` - Reset the smell throughout the dungeon.
* ```moveNorth``` - Move one cave over to the north.
* ```moveSouth``` - Move one cave over to the south.
* ```moveEast``` - Move one cave over to the east.
* ```moveWest``` - Move one cave over to the west.

## Controller
* ```buildDungeon``` - Builds a dungeon with the given parameters
* ```playGame``` - Plays a game with a given Dungeon object and player object

# Example

**NOTE** - This is applicable for all the following runs. The treasure/arrows generated in the driver run are pseudo-random without specifying any seed.
Hence, it most likely
will not match the treasure/arrows that you see in the example run text file. Please note that this is a parameter that can be specified to the default constructors of the Dungeon implementation.

Run 1 -- ExampleRun1.txt:
* We create a 5 x 6 (row x column) non-wrapping maze with an interconnectivity degree of 0, along with a treasure threshold
of 25%. Everything is generated randomly so these test runs might not be exactly the same each time
it is run! This run simulates a user navigating through the maze. We get to see what the game looks like,
along with the kinds of information displayed.

Run 2 -- ExampleRun2.txt:
SUMMARY: We create a 5 x 6 (row x column) non-wrapping maze with an interconnectivity degree of 0, along with a treasure threshold
of 55%. Everything is generated randomly so these test runs might not be exactly the same each time
it is run! This run simulates a user navigating through the maze and picking treasure/arrows as they
go around the maze. We also see how the user's vitals are printed after every move.


Run 3 -- ExampleRun3.txt:
* We create a 5 x 6 (row x column) non-wrapping maze with an interconnectivity degree of 0, along with a treasure threshold
of 25%. Everything is generated randomly so these test runs might not be exactly the same each time
it is run! This run simulates a user navigating the maze and getting eaten by an Otyugh! Chomp. Chomp.


Run 4 -- ExampleRun4.txt:
* We create a 5 x 6 (row x column) non-wrapping maze with an interconnectivity degree of 0, along with a treasure threshold
  of 25%. Everything is generated randomly so these test runs might not be exactly the same each time
  it is run! This run simulates a user navigating the maze and killing an Otyugh! We see how to shoot those arrows are slay an Otyugh.

Run 5 -- ExampleRun5.txt
* We create a 5 x 6 (row x column) non-wrapping maze with an interconnectivity degree of 0, along with a treasure threshold
  of 25%. Everything is generated randomly so these test runs might not be exactly the same each time
  it is run! This run simulates a user navigating the maze, killing the Otyugh at the end point, and
  finishing the game! We see how tshe user is greeted when the game ends.



# Model Changes
* Most major changes were essentially abstracting the functionality away from the user. A lot of
  the original plan revolved around classes communicating directly with other classes. Adding a
  layer of abstraction allows easier understand of the underlying code. This is exactly what I do.
* A lot of refactoring was around adding functionality; mainly the paradigm of shooting arrows and slaying monsters. 
  we could reuse a lot of the old logic needed to accommodate treasure in the maze.
* We now have a controller that takes user input and plays the game. The driver lets us manually specify the
  dungeon params.

# Testing Plan
* Please refer to the testing source folder for more details.

# Assumptions
* The model refers to the three public interfaces within the model. The entire game can only be 
  played when the Controller controls the methods within these interfaces. For the user, they only
  ever need to interact with the Controller.
* While there is a threshold for the number of caves that can get treasure, we don't enforce any 
  such invariants when dealing with the type of treasure itself. It is possible that a certain 
  dungeon is populated with the same type of treasure through its caves.
* There is no upper limit on the amount of loot a player can carry.
* We assume that once dead, an Otyugh _cannot_ contribute to the smell within the caves.
* Testing is done using a deterministic seed. We assume that it would work as expected when the 
  seed is not specified anymore. However, this is not true for the example runs as they are done fully random.
* We assume only one player can exist in a dungeon at any given time.

# Limitations
* Driver takes in command line arguments and the user must be well versed with knowing how to pass a CLI.
* Game must be restarted in order to be played once it is finished.
* As of now, we only accept the symbols of the four cardinal directions as the input for the 
  direction.


# Citations
* Oracle. (2021, October 23). *Java documentation*. Oracle. Retrieved October 3, 2021, from
  https://docs.oracle.com/en/java/. 
* Wikipedia. (2021, September 10). Breadth-first search. In Wikipedia, The Free Encyclopedia. Retrieved 17:44, 
  November 18, 2021
* Baeldung. (2021, Nov 2). *Java Command Line Arguments*. Baeldung. Retrieved November 2, 2021, from
  https://www.baeldung.com/java-command-line-arguments.
* CS5010 Class GitHub Repository.