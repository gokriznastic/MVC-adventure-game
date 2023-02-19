package controller;

import java.util.HashMap;
import java.util.Map;

import model.GameModel;
import model.Item;
import model.Treasure;
import model.Weapon;

/**
 * This class represents the pick command for the controller.
 */
public class Pick implements GameCommand {
  private final Item item;

  /**
   * Construct the pick command object.
   *
   * @param item the item to pick
   * @throws IllegalArgumentException if the item to pick is not valid
   */
  public Pick(String item) throws IllegalArgumentException {
    Map<String, Item> validItems = new HashMap<>();
    validItems.put("diamond", Treasure.DIAMOND);
    validItems.put("ruby", Treasure.RUBY);
    validItems.put("sapphire", Treasure.SAPPHIRE);
    validItems.put("arrow", Weapon.ARROW);

    if (!validItems.containsKey(item)) {
      throw new IllegalArgumentException("Not a valid item! Try again.");
    }

    this.item = validItems.get(item);
  }

  @Override
  public void execute(GameModel model) {
    model.pickItem(this.item);
  }
}
