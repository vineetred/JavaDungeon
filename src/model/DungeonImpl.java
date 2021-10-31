package model;

import java.util.ArrayList;
import java.util.Random;
import random.RandomNumberGenerator;

/**
 * The implementation of the Dungeon interface.
 */
public class DungeonImpl implements Dungeon {

  private final boolean wraps;
  private final int rows;
  private final int columns;
  private final int interconnectivity;
  private final int treasure;
  private final int startPoint;
  private final int endPoint;
  private final Cave[][] Gameboard;
  private ArrayList<Edge> potentialEdges;
  private ArrayList<Edge> leftOverEdges;
  private ArrayList<Edge> finalEdges;

  /**This creates a dungeon that requires the specification of whether the dungeon should wrap or not. How many rows and columns there should be specified as integers. The degree of interconnectivity(default is 0) or how many paths between nodes should there be. An interconnectivity of 0 means that there is exactly 1 path between all nodes. Each degree above that is an additional edge/connection added to the map. Finally, what percentage of caves should have treasure in it. The default is 20%. Caves are defined as having 1, 3, or 4 entrances. Tunnels only have 2 entrances and do not have treasure.
          Params:
  wraps – A boolean which determines if a dungeon wraps its edges around to the other side.
  rows – The number of rows in the dungeon as an integer.
  columns – The number of columns in the dungeon as an integer.
  interconnect – The level of interconnectivity expressed as an integer. Default is 0.
  treasure – The percentage of caves with treasure expressed as an integer. Default is 20.
  Returns:
  The dungeon built to specification represented as a 2 dimensional array.**/
  public DungeonImpl(boolean wraps, int rows, int columns, int interconnect, int treasure) {
    //possible case for the builder pattern for this constructor using the make dungeon method
    // to abstract it
    //Cave = cave;
    //Location = location;
    Cave[][] Gameboard = new Cave[rows][columns];

    this.wraps = wraps;
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnect;
    this.treasure = treasure;
    this.Gameboard = Gameboard;
    this.potentialEdges = new ArrayList<Edge>();
    this.leftOverEdges = new ArrayList<Edge>();;
    this.finalEdges = new ArrayList<Edge>();;

    // Check the dungeon invariants!
    checkDungeonInvariants(wraps, rows, columns, interconnect, treasure);
    // Generate a filled graph
    createConnectedGraph();
    // Run the algorithm
    runKruskalAlgorithm();
    // Fill the caves with treasure
    fillCavesWithTreasure(getCavesIndexArrayList());
    // Initialize the start and end points
    this.startPoint = findStartPoint(getCavesIndexArrayList());
    this.endPoint = findEndPoint(this.startPoint);

  }

  private void checkDungeonInvariants(boolean wraps, int rows, int columns, int interconnect,
                                         int treasure) {
    // A lot of exception handling to ensure that the dungeon is never
    // created with invalid params
    if (rows < 1 || columns < 1) {
      throw new IllegalArgumentException("Rows or Columns cannot be less than 1.");
    } else if (rows == 1 && columns < 6 || rows < 6 && columns == 1) {
      throw new IllegalArgumentException("You must have at least 6 rows or columns if the other "
          + "is 1.");
    } else if (rows == 2 && columns < 3) {
      throw new IllegalArgumentException("You must have at least 6 nodes in the graph.");
    }

    if (treasure < 20) {
      throw new IllegalArgumentException("You must have at least 20% treasure.");
    }

    if (interconnect < 0) {
      throw new IllegalArgumentException("The interconnectivity cannot be less than 0");
    }

    if (interconnect > 0 && !wraps) {

      int maxEdges = 2 * rows * columns - rows - columns;
      if (interconnect > maxEdges -  (rows  * columns - 1)) {
        throw new IllegalArgumentException("Interconnectivity too high, beyond number of edges in"
            + " graph.");
      }
    } else if (interconnect > 0 && wraps) {

      int maxEdges = 2 * rows * columns;
      if (interconnect > maxEdges) {
        throw new IllegalArgumentException("Interconnectivity too high, beyond number of edges in"
            + " graph.");
      }
    }
  }

  private int findStartPoint(ArrayList<Integer> caves) {

    RandomNumberGenerator rand = new RandomNumberGenerator(0, caves.size() - 1, 0,
            1);
    return caves.get(rand.getRandomNumber());
  }

  private int findEndPoint(int startIndex) {
    // Conduct a DFS from the start point
    int depthFirstSearchOutput = this.depthFirstSearchDistance(startIndex, 5);

    // Check if a node at least 5 units away from the start index can be reached
    if (depthFirstSearchOutput != -1) {
        return depthFirstSearchOutput;
      }
    // Else, throw an exception
    throw new IllegalStateException("Cannot find any viable endpoint!");
  }

  // Will return -1 to indicate that the searchDistance cannot be reached
  private int depthFirstSearchDistance(int caveIndex, int searchDistance) {
    // Check for the base case
    if (searchDistance == 0) {
      return caveIndex;
    }

    // Find the cave using the caveIndex
    Cave caveObject = this.findCaveByIndex(caveIndex);

    // Iterate through the neighbours
    for (int index = 0; index < caveObject.getNeighbors().size(); index++) {

      // Recursively call DFS on all neighbours
      // notice that the search distance is decremented by 1
      // in each successive call
      int depthFirstSearchAccumulator =
          depthFirstSearchDistance((Integer) caveObject.getNeighbors().get(index),
          searchDistance - 1);

      // Disregard if output is -1 or if index returned is not a cave
      // by returning -1
      if (depthFirstSearchAccumulator != -1
          && this.getCavesIndexArrayList().contains(depthFirstSearchAccumulator)) {
        return depthFirstSearchAccumulator;
      }
    }

    return -1;
  }

  private void createConnectedGraph() {
    // Construct the actual cave nodes
    int index = 0;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {

        // All empty
        ArrayList entrances = new ArrayList();
        ArrayList neighborList = new ArrayList();
        ArrayList treasureList = new ArrayList();
        Cave cave = new Cave(r, c, entrances, neighborList, treasureList, index, index);
        Gameboard[r][c] = cave;

        index++;
      }
    }

    // Build the edges based on the wraps command!
    if (!wraps) {
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < columns; c++) {
          //case for nodes that aren't on far edge
          if (c < columns - 1 && r < rows - 1) {
            Edge edge = new Edge(Gameboard[r][c], Gameboard[r + 1][c]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(Gameboard[r][c], Gameboard[r][c + 1]);
            potentialEdges.add(edge2);

            // bottom right-hand corner, opposite origin
          } else if (c == columns - 1 && r == rows - 1) {
          } else if (c == columns - 1 && r <= rows - 1) {
            Edge edge = new Edge(Gameboard[r][c], Gameboard[r + 1][c]);
            potentialEdges.add(edge);
          } else {
            Edge edge = new Edge(Gameboard[r][c], Gameboard[r][c + 1]);
            potentialEdges.add(edge);
          }
        }
      }
    } else {
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < columns; c++) {

          // TODO: Add some more wrapping cases!
          // CASE: not an edge node so we add as usual
          if (c < columns - 1 && r < rows - 1) {
            Edge edge = new Edge(Gameboard[r][c], Gameboard[r + 1][c]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(Gameboard[r][c], Gameboard[r][c + 1]);
            potentialEdges.add(edge2);
          }

          // CASE: bottom right edge, wrap right, wrap down
          else if (c == columns - 1 && r == rows - 1) {
            Edge edge = new Edge(Gameboard[r][c], Gameboard[0][c]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(Gameboard[r][c], Gameboard[r][0]);
            potentialEdges.add(edge2);
          }

          // CASE: Last column where everything wraps right
          else if (c == columns - 1 && r <= rows - 1) {
            Edge edge = new Edge(Gameboard[r][c], Gameboard[r + 1][c]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(Gameboard[r][c], Gameboard[r][0]);
            potentialEdges.add(edge2);
          }

          else {
            Edge edge = new Edge(Gameboard[r][c], Gameboard[r][c + 1]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(Gameboard[r][c], Gameboard[0][c]);
            potentialEdges.add(edge2);
          }
        }
      }
    }
  }

  private void fillCavesWithTreasure(ArrayList<Integer> caves) {
    // Make sure that the treasure amount is NOT 0.

    if (this.treasure != 0) {
      int treasCaveNum = (int) Math.ceil((caves.size() * treasure) / 100);
      RandomNumberGenerator rand =
          new RandomNumberGenerator(0, caves.size() - 1, 0, 1);
      RandomNumberGenerator rand2 = new RandomNumberGenerator(0, 2, 0, 1);
      TreasureImpl.TreasureFactory treasureFactory = new TreasureImpl.TreasureFactory();
      for (int t = 0; t < treasCaveNum; t++) {
        int treasureRand = rand2.getRandomNumber();
        if (treasureRand == 0 ) {
          for(int r = 0; r <= treasureRand + 1; r++) {
            findCaveByIndex(caves.get(rand.getRandomNumber()))
                .addTreasure(TreasureImpl.TreasureFactory
                    .getTreasureFromEnum(TreasureImpl.TreasureType.RUBY));
          }
        } else if (treasureRand == 1 ) {
          for (int r = 0; r <= treasureRand + 1; r++) {
            findCaveByIndex(caves.get(rand.getRandomNumber()))
                .addTreasure(TreasureImpl.TreasureFactory
                    .getTreasureFromEnum(TreasureImpl.TreasureType.DIAMOND));
          }
        } else {
          for (int r = 0; r <= treasureRand + 1; r++) {
            findCaveByIndex(caves.get(rand.getRandomNumber()))
                .addTreasure(TreasureImpl.TreasureFactory
                    .getTreasureFromEnum(TreasureImpl.TreasureType.SAPPHIRE));
          }
        }
      }
    }

  }

  private Cave findCaveByIndex(int index) {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        if (Gameboard[r][c].getIndex() == index) {
          return Gameboard[r][c];
        }
      }
    }
    throw new IllegalArgumentException("couldn't find cave index of " + index);
  }

  private ArrayList<Edge> getPotentialEdges() {
    return this.potentialEdges;
  }

  private ArrayList<Integer> getCavesIndexArrayList() {
    ArrayList<Integer> caves = new ArrayList<>();
    //make list of caves, exclude tunnels
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        if (Gameboard[r][c].getNeighbors().size() != 0 && Gameboard[r][c].getNeighbors().size() != 2) {
          caves.add(Gameboard[r][c].getIndex());
        }

      }
    }
    return caves;
  }

  private void runKruskalAlgorithm() {
    //start condition - every cave in own set
    RandomNumberGenerator rand = new RandomNumberGenerator(0, this.getPotentialEdges().size(), 0, 1);
    Random randGen = new Random(rand.getRandomNumber());
    boolean exitInvariant = false;
    ArrayList<Integer> setList = new ArrayList<>();

    // Adding every node to the set list
    // Every node is its own set at the start
    for (int s = 0; s < rows * columns; s++) {
      setList.add(s);
    }

    // TODO: Handle this exception at some point.
    if (setList.size() - 1 != Gameboard[rows - 1][columns - 1].getIndex()) {
      throw new IllegalArgumentException("the set list doesn't match the number of elements");
    }
    while (!exitInvariant) {

      // Get some random integer between 0 - size of the potential edges list
      int random = randGen.nextInt(this.getPotentialEdges().size());

      // if they are in the same set check to see if this edge has already been
      // added to the leftover edges array list.
      if (this.potentialEdges.get(random).compareSets()) {
        // if not, remove from potential edges
        // then add to leftover edges list
        if (!this.leftOverEdges.contains(this.potentialEdges.get(random))) {
          this.leftOverEdges.add(this.potentialEdges.get(random));
          this.potentialEdges.remove(random);
        }
      }
      // If they are not in the same set
      // we add them to the final list to use
      else {
        // This method invokes methods within the cave objects it connects
        this.potentialEdges.get(random).addNeighbors();
        this.finalEdges.add(this.potentialEdges.get(random));

        // Store the current random edge's left and right set
        int temporaryRightSet = this.potentialEdges.get(random).getRightSet();
        int permanentNewSet = this.potentialEdges.get(random).getLeftSet();

        // Remove from potential edges
        this.potentialEdges.remove(random);

        // Find all the caves (nodes) that are part of the same temporary set
        // such that they now point to the new permanent set
        for (int r = 0; r < rows; r++) {
          for (int c = 0; c < columns; c++) {
            if (Gameboard[r][c].getSet() == temporaryRightSet) {
              // Adjusting the set
              Gameboard[r][c].adjSet(permanentNewSet);
            }
          }
        }

        // Check if the current set list we have contained the temporary set that was just removed
        // If so, delete it
          setList.remove(setList.indexOf(temporaryRightSet));

        // Our base case
        // When the interconnectedness is 0, the set list must only contain 1 unique set
        // that is, one connected component
        if (setList.size() == 1 && interconnectivity == 0) {
          // Set the exit invariant
          exitInvariant = true;
        }

        // If the set list is 1, but we need > 1 path
        else if (setList.size() == 1 && interconnectivity > 0) {
          // Flush all the potential edges into the leftover edges
          for (int index = 0; index < this.potentialEdges.size(); ++index) {
            // Check before adding
            if (!this.leftOverEdges.contains(this.potentialEdges.get(index))) {
              this.leftOverEdges.add(this.potentialEdges.get(index));
              this.potentialEdges.remove(index);
            }
          }

          for (int index = 0; index < interconnectivity; ++index) {
            // There must be at least one edge left over
            if (leftOverEdges.size() <= 0) {
                throw new IllegalStateException("Left over edge list is already empty");
            } else {
              // The meat of taking a random edge from the leftover edges list
              int randomInt = randGen.nextInt(this.leftOverEdges.size());
              // Invoking the neighbours method
              this.leftOverEdges.get(randomInt).addNeighbors();
              // Adding to the final edges list
              this.finalEdges.add(this.leftOverEdges.get(randomInt));
              // Removing from the leftover edges list
              this.leftOverEdges.remove(randomInt);
            }

          }
          // Setting the exit invariant
          exitInvariant = true;
        }

      }
    }
  }

  @Override
  public int getStartPoint() {
    return this.startPoint;
  }

  @Override
  public int getEndPoint() {
    return this.endPoint;
  }

  @Override
  public ArrayList<Integer> getAllCaves() {
    return this.getCavesIndexArrayList();
  }
}
