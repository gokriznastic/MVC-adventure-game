package model;

/**
 * This interface represents an item that can be found inside the dungeon.
 */
public interface Item {

  /**
   * Method to get name of the item.
   *
   * @return the name of the treasure type as string
   */
  String getName();

  /**
   * Method to pick the item.
   *
   * @param player the player in the game that is to pick the item
   */
  void pick(Player player);
}
