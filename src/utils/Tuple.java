package utils;

/**
 * This is a generic class representing an immutable tuple data type.
 * This is used to represent co-ordinates (x, y) of different locations in dungeon.
 *
 * @param <X> the x co-ordinate
 * @param <Y> the y co-ordinate
 */
public class Tuple<X, Y> {
  private final X x;
  private final Y y;

  /**
   * Construct a tuple object.
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   */
  public Tuple(X x, Y y) {
    this.x = x;
    this.y = y;
  }

  public X getX() {
    return this.x;
  }

  public Y getY() {
    return this.y;
  }
}