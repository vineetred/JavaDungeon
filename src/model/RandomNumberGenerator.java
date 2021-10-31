package model;

class RandomNumberGenerator {
  private int min;
  private int max;
  private int seed;
  private int listSize;


  protected RandomNumberGenerator(int min, int max, int seed, int listSize) {
    this.min = min;
    this.max = max;
    this.seed = seed;
    this.listSize = listSize;

    if (listSize <= 0) {
      throw new IllegalArgumentException("Not allowed.");
    } else if (min >= max) {
      throw new IllegalArgumentException("Min cannot be more than max.");
    } else if (listSize == 1) {
      getRandomNumber();
    }
  }

  protected int getRandomNumber() {
    int returnInt;
    if (this.seed == 0) {
      returnInt = ((int) (Math.random() * ((this.max - this.min) + 1)) + this.min);
    } else {
      returnInt = 1;
    }
    return returnInt;
  }
}
