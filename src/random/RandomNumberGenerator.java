package random;

import java.util.ArrayList;

public class RandomNumberGenerator {
  private int min;
  private int max;
  private int seed;
  private int listSize;


  public RandomNumberGenerator(int min, int max, int seed, int listSize) {
    this.min = min;
    this.max = max;
    this.seed = 0;
    this.listSize = listSize;

    if (listSize <= 0) {
      throw new IllegalArgumentException("Cannot have a list of less than 1.");
    } else if (min >= max) {
      throw new IllegalArgumentException("The minimum value cannot be equal to or greater than the"
              + " maximum value");
    } else if (listSize == 1) {
      getRandomNumber();
    } else if (listSize > 1) {
      getRandomList(min, max, seed, listSize);
    }
  }

  public int getRandomNumber() {
    if (this.seed == 0) {
      int returnInt = ((int) (Math.random() * ((this.max - this.min) + 1)) + this.min);
      return returnInt;
    } else {
      int returnInt = 0;
      return returnInt;
    }
  }

  public ArrayList getRandomList(int min, int max, int seed, int listSize) {
    ArrayList numList = new ArrayList();
    for (int i = 0; i < listSize; i++) {
      //generate random number
      //add to list
    }
    return numList;
  }
}
