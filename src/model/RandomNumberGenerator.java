package model;

class RandomNumberGenerator {
  private final int min;
  private final int max;
  private final int seed;


  protected RandomNumberGenerator(int min, int max, int seed, int listSize) {
    this.min = min;
    this.max = max;
    this.seed = seed;

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
