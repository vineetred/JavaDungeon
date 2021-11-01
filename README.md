# JavaDungeon

## Todo
* Driver run where a wrapping and non-wrapping dungeons are created
* Test cases to check if wrapping works as expected!
* Treasure that one method in its implementation!

IDEA - maybe count the number of arrows with the regular constructor to test differing interconnectedness.

# Overview
A turn-based RPG featuring two automated AI players along with a bunch of gear and weapons.

# Features
* Generate a battle arena with two players. The program automatically creates and assigns random
  weapons + gear to each player.
* A battle sequence is generated which allows contains all the sprite moves that were fulfilled
  during the battle, with the result being appended at the very end.
* We have a limit of 50 turns within which a battle must finish. If not, a stalemate is declared.
* The driver class basically controls all the classes to create a game. This game keeps asking
  the user for a new game until they do not want it anymore.
* All the output is parsed by the driver which is then printed to the console.


# Execution
## JAR
* Just run the JAR file like so ```java -jar battleDriver.jar``` and the program should go
  through a game, with an option for infinite rematches.

# Usage
* ```Battle``` - The constructor of the battle arena itself. Takes two Player objects as input
  to create a specialized arena for them to duke it out in.
* ```assignGearToPlayers``` - Randomly assigns gear to each of the players from a bag of
  equipment. Maintains all low level invariants regarding gear and weapons. For example, it
  ensures every player only every get at most one headgear.
* ```assignWeaponToPlayer``` - Randomly picks a weapon from the armory of the battle arena and
  assigns it to the players.
* ```startBattle``` - The actual method that begins a battle. It should only be every called
  once gear and weapons have been assigned to the players.
* ```reset``` - An easy method that allows us to reset the state of the battle arena to the
  initial state. Really useful when one needs re-playability. Also resets the players themselves
  so the weapons and gear have to be randomly assigned again.


# Example

Run 1 -- ExampleRun1.txt:
1. Creates some player objects and a battle arena that uses these objects.
2. The battle is allowed to begin. Since the battle sequence is a Arraylist of strings, we parse
   it and then output it so that the user can see the sprites played out.
3. Battle ends when one player wins or a stalemate is reached.
4. User given a Y/N option to replay.

# Model Changes
* Most major changes were essentially abstracting the functionality away from the user. A lot of
  the original plan revolved around classes communicating directly with other classes. Adding a
  layer of abstraction allows easier understand of the underlying code. This is exactly what I do.
* We also assign an ID field to every gear as it makes sorting much easier.

# Testing Plan
* Please refer to the testing source folder for more details.
*
# Assumptions
* Katanas always exist only in pairs -- making the damage dealt double every turn.
* A match ends in a stalemate after 50 turns.
* Rematch is only between the same players.
* Battle sequence is stored in an array list that must be parsed down the line.

# Limitations
* Need to create player objects if new player names are needed. No way to change it once
  instantiated.
* A battle object can only have two players. For new combinations, new objects of this type need
  to be created.

*
# Citations
* Oracle. (2021, April 23). *Java documentation*. Oracle. Retrieved October 3, 2021, from
  https://docs.oracle.com/en/java/. 