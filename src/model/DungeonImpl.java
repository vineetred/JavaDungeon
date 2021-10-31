package model;

import java.util.*;

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
  private final Cave[][] gameBoard;
  private final int seed;
  private ArrayList<Edge> potentialEdges;
  private ArrayList<Edge> leftOverEdges;
  private ArrayList<Edge> finalEdges;

  /** The dungeon constructor.
   * How many rows and columns there should be specified as integers.
   * The degree of interconnectivity(default is 0) or how many paths between nodes should there be.
   * An interconnectivity of 0 means that there is exactly 1 path between all nodes.
   * @param wraps boolean that is set true if you want it wrapped.
   * @param rows the number of rows
   * @param columns the number of columns
   * @param interconnect the degree of interconnectivity you want the maze to be generated with
   * @param treasure the percentage denominated out of 100 which
   * forms the minimum threshold during treasure generation
   */
  public DungeonImpl(boolean wraps, int rows, int columns, int interconnect, int treasure) {

    // Check the dungeon invariants!
    checkDungeonInvariants(wraps, rows, columns, interconnect, treasure);

    Cave[][] gameBoard = new Cave[rows][columns];
    this.wraps = wraps;
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnect;
    this.treasure = treasure;
    this.gameBoard = gameBoard;
    this.seed = 0;

    int temporaryStartPoint;
    int temporaryEndPoint;

    // Keep doing this till we find a suitable end-point
    while (true) {
      this.potentialEdges = new ArrayList<Edge>();
      this.leftOverEdges = new ArrayList<Edge>();
      this.finalEdges = new ArrayList<Edge>();

      // Check the dungeon invariants!
      checkDungeonInvariants(wraps, rows, columns, interconnect, treasure);
      // Generate a filled graph
      createConnectedGraph();
      // Run the algorithm
      runKruskalAlgorithm();
      // Fill the caves with treasure
      fillCavesWithTreasure(getCavesIndexArrayList(), 0);
      // Initialize the start and end points
      temporaryStartPoint = findStartPoint(getCavesIndexArrayList());
      // Catch the illegal state exception emitted when we
      // cannot find a viable end point.
      try {
        temporaryEndPoint = findEndPoint(temporaryStartPoint);
        break;
      }
      catch (IllegalStateException stateException) {
        // Do nothing as we want this to repeat
      }
    }
    // Assign it outside the loop as they are final
    this.startPoint = temporaryStartPoint;
    this.endPoint = temporaryEndPoint;
  }

  /** The dungeon constructor to generate a non-random dungeon.
   * Takes only the wrapping parameter to generate a 20% filled 5 x 6 dungeon.
   * @param wraps the boolean variable that tells the program if wrapping is needed.
   */
  public DungeonImpl(boolean wraps) {
    Cave[][] gameBoard = new Cave[5][6];
    this.gameBoard = gameBoard;
    this.wraps = wraps;
    this.rows = 5;
    this.columns = 6;
    this.interconnectivity = 0;
    this.treasure = 20;
    this.seed = 1;

    int temporaryStartPoint;
    int temporaryEndPoint;

    // Keep doing this till we find a suitable end-point
    while (true) {
      this.potentialEdges = new ArrayList<Edge>();
      this.leftOverEdges = new ArrayList<Edge>();
      this.finalEdges = new ArrayList<Edge>();

      // Check the dungeon invariants!
      checkDungeonInvariants(wraps, rows, columns, interconnectivity, treasure);
      // Generate a filled graph
      createConnectedGraph();
      // Run the algorithm
      runKruskalAlgorithm();
      // Fill the caves with treasure
      fillCavesWithTreasure(getCavesIndexArrayList(), 1);
      // Initialize the start and end points
      temporaryStartPoint = findStartPoint(getCavesIndexArrayList());
      // Catch the illegal state exception emitted when we
      // cannot find a viable end point.
      try {
        temporaryEndPoint = findEndPoint(temporaryStartPoint);
        break;
      }
      catch (IllegalStateException stateException) {
        // Do nothing as we want this to repeat
      }
    }
    // Assign it outside the loop as they are final
    this.startPoint = temporaryStartPoint;
    this.endPoint = temporaryEndPoint;
  }


  /** The dungeon constructor to generate a non-random dungeon for the driver.
   * This makes the choice of treasure random but everything else is predictable.
   *
   * @param wraps the boolean variable that tells the program if wrapping is needed.
   * @param randomCaveTreasureChoiceSeed the seed which the treasure filling method chooses a cave
   */
  public DungeonImpl(boolean wraps, int randomCaveTreasureChoiceSeed) {
    Cave[][] gameBoard = new Cave[5][6];
    this.gameBoard = gameBoard;
    this.wraps = wraps;
    this.rows = 5;
    this.columns = 6;
    this.interconnectivity = 0;
    this.treasure = 20;
    this.seed = 1;

    int temporaryStartPoint;
    int temporaryEndPoint;

    // Keep doing this till we find a suitable end-point
    while (true) {
      this.potentialEdges = new ArrayList<Edge>();
      this.leftOverEdges = new ArrayList<Edge>();
      this.finalEdges = new ArrayList<Edge>();

      // Check the dungeon invariants!
      checkDungeonInvariants(wraps, rows, columns, interconnectivity, treasure);
      // Generate a filled graph
      createConnectedGraph();
      // Run the algorithm
      runKruskalAlgorithm();
      // Fill the caves with treasure
      fillCavesWithTreasure(getCavesIndexArrayList(), randomCaveTreasureChoiceSeed);
      // Initialize the start and end points
      temporaryStartPoint = findStartPoint(getCavesIndexArrayList());
      // Catch the illegal state exception emitted when we
      // cannot find a viable end point.
      try {
        temporaryEndPoint = findEndPoint(temporaryStartPoint);
        break;
      }
      catch (IllegalStateException stateException) {
        // Do nothing as we want this to repeat
      }
    }
    // Assign it outside the loop as they are final
    this.startPoint = temporaryStartPoint;
    this.endPoint = temporaryEndPoint;
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
      throw new IllegalArgumentException("You must have at least 20% treasure. " +
          "This is not an acceptable threshold.");
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

    RandomNumberGenerator rand = new RandomNumberGenerator(0, caves.size() - 1, this.seed,
            1);
    return caves.get(rand.getRandomNumber());
  }

  private int findEndPoint(int startIndex) {
    // Conduct a DFS from the start point
    Set<Integer> visited = new HashSet<>();

    int depthFirstSearchOutput = this.depthFirstSearchDistance(startIndex, 5, visited);

    // Check if a node at least 5 units away from the start index can be reached
    if (depthFirstSearchOutput != -1) {
        return depthFirstSearchOutput;
      }
    // Else, throw an exception
    throw new IllegalStateException("Cannot find any viable endpoint!");
  }

  // Will return -1 to indicate that the searchDistance cannot be reached
  private int depthFirstSearchDistance(int caveIndex, int searchDistance, Set<Integer> visited) {

    // If we already have visited this node, disregard it
    if (visited.contains(caveIndex)) {
      return -1;
    }

    // Add to the visited set
    visited.add(caveIndex);

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
          depthFirstSearchDistance(caveObject.getNeighbors().get(index),
          searchDistance - 1, visited);

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
        gameBoard[r][c] = cave;

        index++;
      }
    }

    // Build the edges based on the wraps command!
    if (!wraps) {
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < columns; c++) {
          // CASE: NOT ON LAST EDGE
          if (c < columns - 1 && r < rows - 1) {
            Edge edge = new Edge(gameBoard[r][c], gameBoard[r + 1][c]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(gameBoard[r][c], gameBoard[r][c + 1]);
            potentialEdges.add(edge2);
          }
          // CASE: NODE ON BOTTOM RIGHT
          else if (c == columns - 1 && r == rows - 1) {
            // WE SIMPLY IGNORE
          }
          // CASE : LAST COLUMN
          else if (c == columns - 1 && r <= rows - 1) {
            Edge edge = new Edge(gameBoard[r][c], gameBoard[r + 1][c]);
            potentialEdges.add(edge);
          }
          // CASE : EVERYTHING ELSE
          else {
            Edge edge = new Edge(gameBoard[r][c], gameBoard[r][c + 1]);
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
            Edge edge = new Edge(gameBoard[r][c], gameBoard[r + 1][c]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(gameBoard[r][c], gameBoard[r][c + 1]);
            potentialEdges.add(edge2);
          }

          // CASE: bottom right edge, wrap right, wrap down
          else if (c == columns - 1 && r == rows - 1) {
            Edge edge = new Edge(gameBoard[r][c], gameBoard[0][c]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(gameBoard[r][c], gameBoard[r][0]);
            potentialEdges.add(edge2);
          }

          // CASE: Last column where everything wraps right
          else if (c == columns - 1 && r <= rows - 1) {
            Edge edge = new Edge(gameBoard[r][c], gameBoard[r + 1][c]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(gameBoard[r][c], gameBoard[r][0]);
            potentialEdges.add(edge2);
          }

          else {
            Edge edge = new Edge(gameBoard[r][c], gameBoard[r][c + 1]);
            potentialEdges.add(edge);
            Edge edge2 = new Edge(gameBoard[r][c], gameBoard[0][c]);
            potentialEdges.add(edge2);
          }
        }
      }
    }
  }

  private void fillCavesWithTreasure(ArrayList<Integer> caves, int randomCaveChoiceSeed) {
    // Make sure that the treasure amount is NOT 0.

    if (this.treasure != 0) {
      int numberOfCavesWithTreasure = (int) Math.ceil((caves.size() * treasure) / 100);

      // Generator that chooses which cave
      RandomNumberGenerator rand =
          new RandomNumberGenerator(0, caves.size() - 1,
              randomCaveChoiceSeed, 1);

      // Generator that chooses which treasure
      RandomNumberGenerator rand2 = new RandomNumberGenerator(0, 2,
          randomCaveChoiceSeed, 1);

      TreasureImpl.TreasureFactory treasureFactory = new TreasureImpl.TreasureFactory();

      for (int t = 0; t < numberOfCavesWithTreasure; t++) {
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
        if (gameBoard[r][c].getIndex() == index) {
          return gameBoard[r][c];
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
        if (gameBoard[r][c].getNeighbors().size() != 0 && gameBoard[r][c].getNeighbors().size() != 2) {
          caves.add(gameBoard[r][c].getIndex());
        }
      }
    }
    return caves;
  }

  private ArrayList<Integer> getAllCaves() {
    return this.getCavesIndexArrayList();
  }

  private ArrayList<Integer> getAllCavesAndTunnels() {
    ArrayList<Integer> allCavesAndTunnels = new ArrayList<>();

    // Get all the tunnels AND caves
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        if (gameBoard[r][c].getNeighbors().size() != 0) {
          allCavesAndTunnels.add(gameBoard[r][c].getIndex());
        }
      }
    }
    return allCavesAndTunnels;
  }

  private void runKruskalAlgorithm() {
    // Initializing the pseudo random number generators
    RandomNumberGenerator rand = new RandomNumberGenerator(0, this.getPotentialEdges().size(),
        this.seed, 1);
    Random randGen = new Random(rand.getRandomNumber());
    boolean exitInvariant = false;
    ArrayList<Integer> setList = new ArrayList<>();

    // Adding every node to the set list
    // Every node is its own set at the start
    for (int s = 0; s < rows * columns; s++) {
      setList.add(s);
    }

    if (setList.size() - 1 != gameBoard[rows - 1][columns - 1].getIndex()) {
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
            if (gameBoard[r][c].getSet() == temporaryRightSet) {
              // Adjusting the set
              gameBoard[r][c].adjSet(permanentNewSet);
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
  public Point2D getStartPoint() {
    return new Point2D(this.findCaveByIndex(this.startPoint).getLocation().getRow(),
        this.findCaveByIndex(this.startPoint).getLocation().getColumn());
  }

  @Override
  public Point2D getEndPoint() {
    return new Point2D(this.findCaveByIndex(this.endPoint).getLocation().getRow(),
        this.findCaveByIndex(this.endPoint).getLocation().getColumn());
  }

  @Override
  public List<String> getMovesAtCaveIndex(Point2D inputCavePoint) {

    Cave caveObject = null;

    // We go through tunnels AND caves.
    for (Integer caveIndex : this.getAllCavesAndTunnels()) {
      Cave temporaryObject = this.findCaveByIndex(caveIndex);
      if (temporaryObject.getRow() == inputCavePoint.getRow()
          && temporaryObject.getColumn() == inputCavePoint.getColumn()) {
        caveObject = this.findCaveByIndex(caveIndex);
        break;
      }
    }

    assert caveObject != null;
    ArrayList<Integer> caveNeighbours = caveObject.getNeighbors();
    ArrayList<String> possibleMoveDirections = new ArrayList<>();

    int currentRow = caveObject.getRow();
    int currentCol = caveObject.getColumn();

    for (Integer caveNeighbour : caveNeighbours) {
      Cave caveNeighbourObject = this.findCaveByIndex(caveNeighbour);

      if (currentRow + 1 == caveNeighbourObject.getRow()
          && currentCol == caveNeighbourObject.getColumn()) {
        possibleMoveDirections.add("South");
      }
      else if (currentRow - 1 == caveNeighbourObject.getRow()
          && currentCol == caveNeighbourObject.getColumn()) {
        possibleMoveDirections.add("North");
      }

      else if (currentRow == caveNeighbourObject.getRow()
          && currentCol + 1 == caveNeighbourObject.getColumn()) {
        possibleMoveDirections.add("West");
      }

      else if (currentRow == caveNeighbourObject.getRow()
          && currentCol - 1 == caveNeighbourObject.getColumn()) {
        possibleMoveDirections.add("East");
      }
  }
    return possibleMoveDirections;

  }

  @Override
  public List<Treasure> expungeCaveTreasure(Point2D inputCavePoint) {
    Cave caveObject = null;

    for (Integer caveIndex : this.getAllCaves()) {
      Cave temporaryObject = this.findCaveByIndex(caveIndex);
      if (temporaryObject.getRow() == inputCavePoint.getRow()
          && temporaryObject.getColumn() == inputCavePoint.getColumn()) {
        caveObject = this.findCaveByIndex(caveIndex);
        break;
      }
    }

    try {
      // No need for copy as we want to reassign same object to the player's list
      return caveObject.pickCaveTreasure();
    }

    catch (NullPointerException e) {
      return new ArrayList<>();
    }
  }

  @Override
  public List<Treasure> peekCaveTreasure(Point2D inputCavePoint) {
    Cave caveObject = null;

    for (Integer caveIndex : this.getAllCaves()) {
      Cave temporaryObject = this.findCaveByIndex(caveIndex);
      if (temporaryObject.getRow() == inputCavePoint.getRow()
          && temporaryObject.getColumn() == inputCavePoint.getColumn()) {
        caveObject = this.findCaveByIndex(caveIndex);
        break;
      }
    }

    try {
      // Underlying method already returns a deep copy
      return caveObject.getCaveTreasure();
    }

    catch (NullPointerException e) {
      return new ArrayList<>();

    }
  }

  @Override
  public Point2D getCaveInDirection(Point2D inputCavePoint, String direction) {

    Cave caveObject = null;

    for (Integer caveIndex : this.getAllCavesAndTunnels()) {
      Cave temporaryObject = this.findCaveByIndex(caveIndex);
      if (temporaryObject.getRow() == inputCavePoint.getRow()
          && temporaryObject.getColumn() == inputCavePoint.getColumn()) {
        caveObject = this.findCaveByIndex(caveIndex);
        break;
      }
    }

    assert caveObject != null;
    int inputLocationRow = caveObject.getRow();
    int inputLocationCol = caveObject.getColumn();

    for (Integer neighborIndex : caveObject.getNeighbors()) {

      Cave caveNeighbourObject = this.findCaveByIndex(neighborIndex);

      if (direction.equals("S") && inputLocationRow + 1 == caveNeighbourObject.getRow()
          && inputLocationCol == caveNeighbourObject.getColumn()) {
        return caveNeighbourObject.getLocation();
      } else if (direction.equals("N") && inputLocationRow - 1 == caveNeighbourObject.getRow()
          && inputLocationCol == caveNeighbourObject.getColumn()) {
        return caveNeighbourObject.getLocation();
      } else if (direction.equals("W") && inputLocationRow == caveNeighbourObject.getRow()
          && inputLocationCol + 1 == caveNeighbourObject.getColumn()) {
        return caveNeighbourObject.getLocation();
      } else if (direction.equals("E") && inputLocationRow == caveNeighbourObject.getRow()
          && inputLocationCol - 1 == caveNeighbourObject.getColumn()) {
        return caveNeighbourObject.getLocation();
      }
    }
    throw new IllegalStateException("Wrong direction! You cannot move there.");
  }

  @Override
  public boolean gameFinished(Point2D inputCavePoint) {
    return inputCavePoint.getRow() == this.getEndPoint().getRow()
        && inputCavePoint.getColumn() == this.getEndPoint().getColumn();
  }

}
