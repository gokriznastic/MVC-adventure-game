package controller;

import java.util.HashMap;
import java.util.Map;

import model.Direction;
import model.GameModel;

/**
 * This class represents the shoot command for the controller.
 */
public class Shoot implements GameCommand {
  private final Direction direction;
  private final int distance;
  private boolean successfulHit;

  /**
   * Construct the shoot command object.
   *
   * @param direction the direction in which to shoot the arrow
   * @param distance  the no. of caves the arrow should possibly travel
   * @throws IllegalArgumentException if direction string is invalid or distance is non-integer
   */
  public Shoot(String direction, String distance) throws IllegalArgumentException {
    Map<String, Direction> shortDirections = new HashMap<>();
    direction = direction.toUpperCase();
    shortDirections.put("N", Direction.NORTH);
    shortDirections.put("S", Direction.SOUTH);
    shortDirections.put("E", Direction.EAST);
    shortDirections.put("W", Direction.WEST);

    if (!shortDirections.containsKey(direction)) {
      throw new IllegalArgumentException("Not a valid direction! Try again.");
    }

    this.direction = shortDirections.get(direction);

    try {
      this.distance = Integer.parseInt(distance);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Not a valid integer distance! Try again.");
    }
  }

  @Override
  public void execute(GameModel model) {
    this.successfulHit = model.shootArrow(this.direction, this.distance);
  }

  boolean hitMonster() {
    return this.successfulHit;
  }
}
