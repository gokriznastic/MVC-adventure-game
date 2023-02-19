package model;

/**
 * This enum class represents types of Weapons that can be present in the
 * caves or tunnels of the game dungeon.
 */
public enum Weapon implements Item {
  ARROW("arrow");

  private String weaponName;

  Weapon(String name) {
    this.weaponName = name;
  }

  @Override
  public String getName() {
    return this.weaponName;
  }

  @Override
  public void pick(Player player) {
    player.pickArrow(this);
  }
}
