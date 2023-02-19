package model;

import java.util.List;
import java.util.Set;

/**
 * This interface represents a Location in the dungeon.
 * Each location has its own properties like its co-ordinates on the 2D grid, its neighbours,
 * and if it contains any items or houses any monsters.
 */
public interface Location {

  /**
   * Method to get the (x, y) co-ordinates of the location on the 2D grid.
   *
   * @return  the x and the y co-ordinates as a 2 length integer array.
   */
  int[] getCoordinates();

  /**
   * Method to get the possible directions the player can move from the current location.
   *
   * @return  the set of possible directions
   */
  Set<Direction> getPossibleDirections();

  /**
   * Method to get the neighbor of a location in given direction.
   *
   * @param d the given direction to move a player
   * @return  the co-ordinates of the neighbor
   *
   * @throws IllegalArgumentException if direction is null
   */
  int[] getNeighbour(Direction d);

  /**
   * Method to get the treasure contents of the location as a list.
   *
   * @return  the list of treasures available at the current location
   */
  List<Item> getContent();

  /**
   * Method to check if the given location houses a monster.
   *
   * @return true if the location houses a monster, else false
   */
  boolean hasMonster();

  /**
   * Method to get the Monster object if present at a current location.
   * The method 'hasMonster()' should be checked before calling this method.
   *
   * @return the monster
   * @throws IllegalStateException if there is no monster present at the given location
   */
  Monster getMonster();

  /**
   * Method to check the smell detected, if any, at the location.
   *
   * @return the intensity of smell detected
   */
  Smell getSmell();

  /**
   * Method to check if a given location is a cave.
   *
   * @return true if location is a cave, false if tunnel
   */
  boolean isCave();

  /**
   * Method to check if a location has been visited by the player.
   * @return true if location has been visited at least once, else false
   */
  boolean isVisited();
}
