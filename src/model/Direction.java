package model;

enum Direction {
  NORTH(-1, 0),
  SOUTH(1, 0),
  EAST(0, 1),
  WEST(0, -1);

  private final int xadj;
  private final int yadj;

  Direction(int xadj, int yadj) {
    this.xadj = xadj;
    this.yadj = yadj;
  }
}
