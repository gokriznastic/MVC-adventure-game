package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import utils.Tuple;

/**
 * This class represents a Location in the dungeon.
 */
public class Cell implements Location {
  private final Tuple<Integer, Integer> coordinate;
  private final Map<Direction, String> possibleDirections;
  private final List<Item> content;
  private final List<Monster> occupant;
  private Smell smell;
  private boolean visited;

  /**
   * Construct a cell location given the arguments.
   *
   * @param x                  the x co-ordinate on the 2D grid
   * @param y                  the y co-ordinate on the 2D grid
   * @param possibleDirections the directions that lead away from the current cell location
   */
  public Cell(int x, int y, HashMap<Direction, String> possibleDirections)
          throws IllegalArgumentException {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Cell indices cannot be negative!");
    }
    if (!(possibleDirections.containsKey(Direction.NORTH)
            || possibleDirections.containsKey(Direction.SOUTH)
            || possibleDirections.containsKey(Direction.EAST)
            || possibleDirections.containsKey(Direction.WEST))) {
      throw new IllegalArgumentException("Possible cell directions should be valid!");
    }
    this.coordinate = new Tuple<>(x, y);
    this.possibleDirections = possibleDirections;
    this.content = new ArrayList<>();
    this.occupant = new ArrayList<>();
    this.smell = Smell.NONE;
    this.visited = false;
  }

  void fill(Item item) {
    this.content.add(item);
  }

  void pop(Item item) {
    this.content.remove(item);
  }

  void putMonster(Monster oytugh) throws IllegalStateException {
    if (this.occupant.size() > 0) {
      throw new IllegalStateException("Attempting to add monster to an already occupied cave!");
    }
    this.occupant.add(oytugh);
  }

  void updateSmell(Smell smell) {
    this.smell = smell;
  }

  void markVisited() {
    this.visited = true;
  }

  @Override
  public int[] getCoordinates() {
    return new int[]{this.coordinate.getX(), this.coordinate.getY()};
  }

  @Override
  public Set<Direction> getPossibleDirections() {

    return new HashSet<>(this.possibleDirections.keySet());
  }

  @Override
  public int[] getNeighbour(Direction d) {
    if (d == null) {
      throw new IllegalArgumentException("Given neighbour direction is null!");
    }

    int[] neighbourCoordinate = new int[2];
    String[] xy = this.possibleDirections.get(d).split(" ");
    neighbourCoordinate[0] = Integer.parseInt(xy[0]);
    neighbourCoordinate[1] = Integer.parseInt(xy[1]);

    return neighbourCoordinate;
  }

  @Override
  public List<Item> getContent() {
    return new ArrayList<>(this.content);
  }

  @Override
  public boolean hasMonster() {
    return (this.occupant.size() > 0);
  }

  @Override
  public Monster getMonster() throws IllegalStateException {
    if (!this.hasMonster()) {
      throw new IllegalStateException("No monster present!");
    }

    return this.occupant.get(0);
  }

  @Override
  public Smell getSmell() {
    return this.smell;
  }

  @Override
  public boolean isCave() {
    return this.possibleDirections.keySet().size() != 2;
  }

  @Override
  public boolean isVisited() {
    return this.visited;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Cell)) {
      return false;
    }

    Cell that = (Cell) o;

    return Arrays.equals(this.getCoordinates(), that.getCoordinates())
            && this.isCave() == that.isCave()
            && this.getContent().equals(that.getContent())
            && this.getPossibleDirections().equals(that.getPossibleDirections());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.isCave(), Arrays.hashCode(this.getCoordinates()),
            this.possibleDirections);
  }
}
