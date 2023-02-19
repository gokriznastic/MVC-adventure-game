package controller;

import java.util.HashMap;
import java.util.Map;

import model.Direction;
import model.GameModel;

/**
 * This class represents the move command for the controller.
 */
public class Move implements GameCommand {
  private final Direction direction;

  /**
   * Construct the move command object.
   *
   * @param direction the direction to move the player in
   * @throws IllegalArgumentException if the direction string is not valid
   */
  public Move(String direction) throws IllegalArgumentException {
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
  }

  @Override
  public void execute(GameModel model) {
    model.movePlayer(this.direction);
  }
}
