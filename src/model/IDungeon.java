package model;

/**
 * This interface represents a dungeon in the adventure game.
 * A game dungeon has features like caves and tunnels, start and end
 * and is represented as a 2D grid.
 */
public interface IDungeon {

  /**
   * Method to get the location of the start of the game dungeon.
   *
   * @return  the location of the start cave
   */
  Location getStart();

  /**
   * Method to get the location of the end of the game dungeon.
   *
   * @return  the location of the end cave
   */
  Location getEnd();

  /**
   * Method to get the location of any general position in the game dungeon grid.
   *
   * @param x the x co-ordinate of the queried location
   * @param y the y co-ordinate of the queried location
   * @return the Location at (x, y) co-ordinate of the game dungeon
   * @throws IllegalArgumentException if x or y are out of bounds
   */
  Location getLocation(int x, int y);

  /**
   * Method to get the total number of caves in the game dungeon.
   *
   * @return  the number of caves
   */
  int getNoOfCaves();

  /**
   * Method to get the total number of tunnels in the game dungeon.
   *
   * @return  the number of tunnels
   */
  int getNoOfTunnels();

  Location[][] getDungeonGrid();
}
