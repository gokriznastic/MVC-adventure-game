package model;

/**
 * This enum class represents types of Treasure that can be present in the
 * caves of the game dungeon.
 */
public enum Treasure implements Item {
  DIAMOND("diamond"),
  RUBY("ruby"),
  SAPPHIRE("sapphire");

  private String treasureName;

  Treasure(String name) {
    this.treasureName = name;
  }

  @Override
  public String getName() {
    return this.treasureName;
  }

  @Override
  public void pick(Player player) {
    player.pickTreasure(this);
  }
}


