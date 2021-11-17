package model;

import java.util.*;

/**
 * The implementation of the Dungeon interface. This interface has some private methods that help
 * it achieve the tasks outlined in the Dungeon interface. This implementation also does not talk
 * to the player class objects at any point; this job is that of the driver class.
 * We have various constructors to achieve differing randomness with the aim of ease of use and
 * debugging.
 */
public class DungeonImpl implements Dungeon {

  private final boolean wraps;
  private final int rows;
  private final int columns;
  private final int interconnectivity;
  private final int treasurePercentage;
  // TODO: Change this!
  private final int numberOfMonsters;
  private final ArrayList<Integer> minorSmell;
  private final ArrayList<Integer> majorSmell;
  private final int startPoint;
  private final int endPoint;
  private final Cave[][] gameBoard;
  private final int seed;
  private ArrayList<Edge> potentialEdges;
  private ArrayList<Edge> leftOverEdges;
  private ArrayList<Edge> finalEdges;

  /** The dungeon constructor that takes in the rows, the columns, if it is wrapping or not, the
   * degree of interconnectivity that is required by the user, and the treasure percentage in
   * increments of 1.
   *
   * @param wraps boolean that is set true if you want it wrapped.
   * @param rows the number of rows
   * @param columns the number of columns
   * @param interconnect the degree of interconnectivity you want the maze to be generated with
   * @param treasurePercentage the percentage denominated out of 100 of treasure filled caves
   *
   */
  public DungeonImpl(boolean wraps, int rows, int columns, int interconnect,
                     int treasurePercentage, int numberOfMonsters) {

    // Check the dungeon invariants!
    checkDungeonInvariants(wraps, rows, columns, interconnect, treasurePercentage);

    Cave[][] gameBoard = new Cave[rows][columns];
    this.wraps = wraps;
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnect;
    this.treasurePercentage = treasurePercentage;
    this.gameBoard = gameBoard;
    this.seed = 0;
    this.minorSmell = new ArrayList<>();
    this.majorSmell = new ArrayList<>();
    this.numberOfMonsters = numberOfMonsters;

    int temporaryStartPoint;
    int temporaryEndPoint;

    // Keep doing this till we find a suitable end-point
    while (true) {
      this.potentialEdges = new ArrayList<Edge>();
      this.leftOverEdges = new ArrayList<Edge>();
      this.finalEdges = new ArrayList<Edge>();

      // Check the dungeon invariants!
      checkDungeonInvariants(wraps, rows, columns, interconnect, treasurePercentage);
      // Generate a filled graph
      createConnectedGraph();
      // Run the algorithm
      runKruskalAlgorithm();
      // Fill the caves with treasure
      fillCavesWithTreasure(getCavesIndexArrayList(), 0);
      fillCavesWithArrows(getCavesIndexArrayList(), 0);
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
    this.treasurePercentage = 20;
    this.seed = 1;
    this.minorSmell = new ArrayList<>();
    this.majorSmell = new ArrayList<>();
    this.numberOfMonsters = 2;

    int temporaryStartPoint;
    int temporaryEndPoint;

    // Keep doing this till we find a suitable end-point
    while (true) {
      this.potentialEdges = new ArrayList<Edge>();
      this.leftOverEdges = new ArrayList<Edge>();
      this.finalEdges = new ArrayList<Edge>();

      // Check the dungeon invariants!
      checkDungeonInvariants(wraps, rows, columns, interconnectivity, treasurePercentage);
      // Generate a filled graph
      createConnectedGraph();
      // Run the algorithm
      runKruskalAlgorithm();
      // Fill the caves with treasure
      fillCavesWithTreasure(getCavesIndexArrayList(), 1);
      fillCavesWithArrows(getCavesIndexArrayList(), 1);

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

    // Fill the caves with Monsters
    fillCavesWithMonsters(getCavesIndexArrayList(), 0);

    // Fill the caves with Smells!
    // Note, we pass the tunnels too.
    fillCavesWithSmells(getAllCavesAndTunnels());
  }

  /** The dungeon constructor to generate a non-random dungeon for the driver.
   * This makes the choice of treasure random but everything else is predictable.
   *
   * @param wraps the boolean variable that tells the program if wrapping is needed.
   * @param randomCaveTreasureChoiceSeed the seed which the treasure filling method chooses a cave
   */
  public DungeonImpl(boolean wraps, int randomCaveTreasureChoiceSeed, int rows, int columns) {
    Cave[][] gameBoard = new Cave[rows][columns];
    this.gameBoard = gameBoard;
    this.wraps = wraps;
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = 0;
    this.treasurePercentage = 20;
    this.seed = 1;
    this.minorSmell = new ArrayList<>();
    this.majorSmell = new ArrayList<>();
    this.numberOfMonsters = 2;

    int temporaryStartPoint;
    int temporaryEndPoint;

    // Keep doing this till we find a suitable end-point
    while (true) {
      this.potentialEdges = new ArrayList<Edge>();
      this.leftOverEdges = new ArrayList<Edge>();
      this.finalEdges = new ArrayList<Edge>();

      // Check the dungeon invariants!
      checkDungeonInvariants(wraps, rows, columns, interconnectivity, treasurePercentage);
      // Generate a filled graph
      createConnectedGraph();
      // Run the algorithm
      runKruskalAlgorithm();
      // Fill the caves with treasure
      fillCavesWithTreasure(getCavesIndexArrayList(), randomCaveTreasureChoiceSeed);
      fillCavesWithArrows(getCavesIndexArrayList(), 0);
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
      throw new IllegalArgumentException("Cannot have 0 rows or columns!");
    } else if (rows == 1 && columns < 6 || rows < 6 && columns == 1) {
      throw new IllegalArgumentException("Need to have minimum of 6 rows/columns.");
    } else if (rows == 2 && columns < 3) {
      throw new IllegalArgumentException("You don't seem to have enough places for at least six " +
          "nodes");
    }

    if (treasure < 20) {
      throw new IllegalArgumentException("You must have at least 20% treasure. " +
          "This is not an acceptable threshold.");
    }

    if (interconnect < 0) {
      throw new IllegalArgumentException("The interconnectivity cannot be negative!");
    }

    if (interconnect > 0 && !wraps) {

      int maxEdges = 2 * rows * columns - rows - columns;
      if (interconnect > maxEdges -  (rows  * columns - 1)) {
        throw new IllegalArgumentException("The interconnectivity is too high based on the number" +
            " of rows and columns!");
      }
    } else if (interconnect > 0 && wraps) {

      int maxEdges = 2 * rows * columns;
      if (interconnect > maxEdges) {
        throw new IllegalArgumentException("The interconnectivity is too high based on the number" +
            " of rows and columns!");
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

    // If all nodes have been visited and the search distance control flow for when it is 0 still
    // has not executed, then return -1
    if (visited.size() == this.rows * this.columns) {
      return -1;
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

  private Set<Cave> breadthFirstSearchByLevel(int caveIndex, int level) {

    int levelAccumulator = 0;
    Queue<Cave> queue = new LinkedList<>();
    Set<Cave> visited = new HashSet<>();

    // Find the cave using the caveIndex
    Cave caveObject = this.findCaveByIndex(caveIndex);
    // Mark Cave as visited
    queue.add(caveObject);
    // Mark source as visited
    // TODO: Check if indices make it easier?
    visited.add(caveObject);

    while(!queue.isEmpty()) {
      Cave temporaryCaveObject = queue.poll();
      // Process all neighbours of temporaryCaveObject
      // Iterate through the neighbours
      for (int index = 0; index < temporaryCaveObject.getNeighbors().size(); index++) {
        if (!visited.contains(findCaveByIndex(temporaryCaveObject.getNeighbors().get(index)))) {
          queue.add(findCaveByIndex(temporaryCaveObject.getNeighbors().get(index)));
          if (findCaveByIndex(temporaryCaveObject.getNeighbors().get(index)).getCaveMonsters().size() >= 1) {
            visited.add(findCaveByIndex(temporaryCaveObject.getNeighbors().get(index)));
          }
        }
      }

      levelAccumulator++;
      if (levelAccumulator == level) {
        break;
      }
    }

    return visited;
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

    // If wraps is true
    if (wraps) {
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < columns; c++) {

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

    // If wraps is not true
    else {
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
            // WE NEED THIS TO ENSURE THAT IT DOES NOT TRIGGER
            // ANY OTHER CONTROL FLOWS.
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
    }
  }

  private void fillCavesWithTreasure(ArrayList<Integer> caves, int randomCaveChoiceSeed) {
    // Make sure that the treasure amount is NOT 0.

    if (this.treasurePercentage != 0) {
      int numberOfCavesWithTreasure = (int) Math.ceil((caves.size() * treasurePercentage) / 100);

      // Generator that chooses which cave
      RandomNumberGenerator rand =
          new RandomNumberGenerator(0, caves.size() - 1,
              randomCaveChoiceSeed, 1);

      // Generator that chooses which treasure
      RandomNumberGenerator rand2 = new RandomNumberGenerator(0, 2,
          randomCaveChoiceSeed, 1);
      // Checking the random number we get
      // to assign it a single type of treasure
      for (int t = 0; t < numberOfCavesWithTreasure; t++) {
        int treasureRandomChoice = rand2.getRandomNumber();
        if (treasureRandomChoice == 0 ) {
          for (int r = 0; r <= treasureRandomChoice + 1; r++) {
            findCaveByIndex(caves.get(rand.getRandomNumber()))
                .addTreasure(TreasureImpl.TreasureGeneratorHelperClass
                    .getTreasureFromEnum(TreasureImpl.TreasureType.RUBY));
          }
        } else if (treasureRandomChoice == 1 ) {
          for (int r = 0; r <= treasureRandomChoice + 1; r++) {
            findCaveByIndex(caves.get(rand.getRandomNumber()))
                .addTreasure(TreasureImpl.TreasureGeneratorHelperClass
                    .getTreasureFromEnum(TreasureImpl.TreasureType.DIAMOND));
          }
        } else {
          for (int r = 0; r <= treasureRandomChoice + 1; r++) {
            findCaveByIndex(caves.get(rand.getRandomNumber()))
                .addTreasure(TreasureImpl.TreasureGeneratorHelperClass
                    .getTreasureFromEnum(TreasureImpl.TreasureType.SAPPHIRE));
          }
        }
      }
    }
  }

  private void fillCavesWithMonsters(ArrayList<Integer> caves, int randomCaveChoiceSeed) {

    // Minus 1 because we must always have one at the end point
    int numberOfCavesWithMonsters = numberOfMonsters - 1;

    // Generator that chooses which cave
    RandomNumberGenerator rand =
        new RandomNumberGenerator(0, caves.size() - 2,
            randomCaveChoiceSeed, 1);

    while (numberOfCavesWithMonsters != 0) {

      int randomCaveIndex = caves.get(rand.getRandomNumber());
      Cave temporaryCave = findCaveByIndex(randomCaveIndex);
      if (randomCaveIndex != this.startPoint && randomCaveIndex != this.endPoint) {
        temporaryCave.addMonster(new Otyugh());
        numberOfCavesWithMonsters--;
      }
    }

    // Adding to the end point!
    if (numberOfCavesWithMonsters == 0) {
      findCaveByIndex(this.endPoint).addMonster(new Otyugh());
    }

  }

  private void fillCavesWithSmells(ArrayList<Integer> cavesAndTunnels) {
    for (int index = 0; index < cavesAndTunnels.size(); index++) {
      // DO BFS from this place
      if (breadthFirstSearchByLevel(cavesAndTunnels.get(index), 1).size() > 1) {
        majorSmell.add(cavesAndTunnels.get(index));
      }
      else {
        // Extra logic for 2nd level from current node if major smell was not invoked
        int caveIndicesForSmellFilling = breadthFirstSearchByLevel(cavesAndTunnels.get(index), 2).size() - 1;

        // If returned array size == 1, then we add it to minor smell
        if (caveIndicesForSmellFilling == 1 && !minorSmell.contains(cavesAndTunnels.get(index))) {
          minorSmell.add(cavesAndTunnels.get(index));
        }
        else if (caveIndicesForSmellFilling > 1 && !majorSmell.contains(cavesAndTunnels.get(index))) {
          majorSmell.add(cavesAndTunnels.get(index));
        }
      }

    }
  }

  private void fillCavesWithArrows(ArrayList<Integer> caves, int randomCaveChoiceSeed) {

    if (this.treasurePercentage != 0) {
      int numberOfCavesWithTreasure = (int) Math.ceil((caves.size() * treasurePercentage) / 100);

      // Generator that chooses which cave
      RandomNumberGenerator rand =
          new RandomNumberGenerator(0, caves.size() - 1,
              randomCaveChoiceSeed, 1);

      for (int t = 0; t < numberOfCavesWithTreasure; t++) {
        findCaveByIndex(caves.get(rand.getRandomNumber())).addCrookedArrow(new CrookedArrow());
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
        if (gameBoard[r][c].getNeighbors().size() != 0
            && gameBoard[r][c].getNeighbors().size() != 2) {
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
              gameBoard[r][c].changeSet(permanentNewSet);
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

  private Cave getCaveAtPoint2D(Point2D inputCavePoint) {
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

    return caveObject;
  }

  @Override
  public Point2D getStartPoint() {
    return new Point2DImpl(this.findCaveByIndex(this.startPoint).getLocation().getRow(),
        this.findCaveByIndex(this.startPoint).getLocation().getColumn());
  }

  @Override
  public Point2D getEndPoint() {
    return new Point2DImpl(this.findCaveByIndex(this.endPoint).getLocation().getRow(),
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
        possibleMoveDirections.add("East");
      }

      else if (currentRow == caveNeighbourObject.getRow()
          && currentCol - 1 == caveNeighbourObject.getColumn()) {
        possibleMoveDirections.add("West");
      }

      if (wraps) {

        if (currentRow == 0 && caveNeighbourObject.getRow() == this.rows - 1) {
          possibleMoveDirections.add("North");
        }

        else if (currentRow == this.rows - 1 && caveNeighbourObject.getRow() == 0) {
          possibleMoveDirections.add("South");
        }

        else if (currentCol == 0 && caveNeighbourObject.getColumn() == this.columns - 1) {
          possibleMoveDirections.add("West");
        }

        else if (currentCol == this.columns - 1 && caveNeighbourObject.getColumn() == 0) {
          possibleMoveDirections.add("East");
        }


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
      } else if (direction.equals("E") && inputLocationRow == caveNeighbourObject.getRow()
          && inputLocationCol + 1 == caveNeighbourObject.getColumn()) {
        return caveNeighbourObject.getLocation();
      } else if (direction.equals("W") && inputLocationRow == caveNeighbourObject.getRow()
          && inputLocationCol - 1 == caveNeighbourObject.getColumn()) {
        return caveNeighbourObject.getLocation();
      }

      if (wraps) {

        if (direction.equals("N") && inputLocationRow == 0
            && caveNeighbourObject.getRow() == this.rows - 1) {
          return caveNeighbourObject.getLocation();
        }

        else if (direction.equals("S") && inputLocationRow == this.rows - 1
            && caveNeighbourObject.getRow() == 0) {
          return caveNeighbourObject.getLocation();
        }

        else if (direction.equals("W") && inputLocationCol == 0
            && caveNeighbourObject.getColumn() == this.columns - 1) {
          return caveNeighbourObject.getLocation();
        }

        else if (direction.equals("E") && inputLocationCol == this.columns - 1
            && caveNeighbourObject.getColumn() == 0) {
          return caveNeighbourObject.getLocation();
        }


      }
    }
    throw new IllegalStateException("Wrong direction! You cannot move there.");
  }

  @Override
  public List<Monster> peekCaveMonsters(Point2D inputCavePoint) {
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
      return caveObject.getCaveMonsters();
    }

    catch (NullPointerException e) {
      return new ArrayList<>();
    }

  }

  @Override
  public List<CrookedArrow> expungeCaveCrookedArrows(Point2D inputCavePoint) {
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
      return caveObject.getCaveCrookedArrows();
    }

    catch (NullPointerException e) {
      return new ArrayList<>();
    }

  }

  @Override
  public boolean gameFinished(Point2D inputCavePoint) {
    return inputCavePoint.getRow() == this.getEndPoint().getRow()
        && inputCavePoint.getColumn() == this.getEndPoint().getColumn();
  }

  @Override
  public String toString() {
    return this.finalEdges.toString();
  }

  @Override
  public int shootCrookedArrow(Point2D inputCavePoint, String direction, int distance) {

    Point2D movingPoint = inputCavePoint;
    Cave movingCaveObject = this.getCaveAtPoint2D(inputCavePoint);

    // Return 0 if wall or no monster
    // Return 1 if hit but monster alive
    // Return 2 if hit and monster dead

    while (distance != 0) {

      // Try catch block here to catch the IllegalStateException
      // generated by a wall being found!
      try {
        movingPoint = this.getCaveInDirection(movingPoint, direction);
        movingCaveObject = this.getCaveAtPoint2D(movingPoint);
      }
      catch (IllegalStateException e) {
        return 0;
      }

      // Only decrement distance if not a tunnel
      if (movingCaveObject.getNeighbors().size() != 2) {
        distance--;
      }
    }

    // Check if there is a monster
    if (movingCaveObject.getCaveMonsters().size() > 0) {

      // If there is
      // Make the monster take a hit
      movingCaveObject.getCaveMonsters().get(0).takeHit();

      // Check if the monster is still alive
      if (movingCaveObject.getCaveMonsters().get(0).getHits() < 2) {
        return 1;
      }
      else {
        return 2;
      }
    }

    // If no monster was found, return 0
    return 0;
  }

  @Override
  public void stats() {
    System.out.println("Minor smells " + this.minorSmell.toString());
    System.out.println("Major smells " + this.majorSmell.toString());
    for (int index = 0; index < getCavesIndexArrayList().size(); index++) {
      if (findCaveByIndex(getCavesIndexArrayList().get(index)).getCaveMonsters().size() > 0) {
        System.out.println(findCaveByIndex(getCavesIndexArrayList().get(index)).getLocation());
        System.out.println(findCaveByIndex(getCavesIndexArrayList().get(index)).getCaveMonsters().toString());
      }

    }
  }

  @Override
  public boolean isMinorSmell(Point2D inputCavePoint) {
    Cave caveObject = this.getCaveAtPoint2D(inputCavePoint);
    return this.minorSmell.contains(caveObject.getIndex());

  }

  @Override
  public boolean isMajorSmell(Point2D inputCavePoint) {
    Cave caveObject = this.getCaveAtPoint2D(inputCavePoint);
    return this.majorSmell.contains(caveObject.getIndex());

  }
}
