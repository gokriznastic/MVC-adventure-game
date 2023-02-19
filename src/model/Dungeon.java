package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import utils.Tuple;

/**
 * This class represents a dungeon in the adventure game.
 */
public class Dungeon implements IDungeon {
  private final Random rand;

  private final Location start;
  private final Location end;
  private final Location[][] grid;

  private final int rows;
  private final int cols;

  private final int MIN_DIST;

  /**
   * Construct a game dungeon with the given arguments.
   *
   * @param rows              the no. of rows in the game dungeon
   * @param columns           the no. of columns in the game dungeon
   * @param wrapping          if the game dungeon needs to be wrapping, yes if true, false otherwise
   * @param interconnectivity the degree of interconnectivity required for the dungeon
   * @param treasurePct       the percentage of caves to be filled with treasure, between 0 and 100
   * @param rand              the Random object, can be truly random or deterministic
   * @throws IllegalArgumentException if rows, columns are less or equal to 0
   *                                  or the interconnectivity is less than 0
   *                                  or the percent of treasure caves is not between 0 and 100
   * @throws IllegalStateException    if dungeon build is not successful using the given arguments
   */
  public Dungeon(int rows, int columns, boolean wrapping, int interconnectivity, double treasurePct,
                 int noOfMonsters, Random rand)
          throws IllegalArgumentException, IllegalStateException {
    if (rows <= 0) {
      throw new IllegalArgumentException("No. of rows should be positive!");
    } else if (columns <= 0) {
      throw new IllegalArgumentException("No. of columns should be positive!");
    } else if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity cannot be less than zero!");
    } else if (treasurePct < 0 || treasurePct > 100) {
      throw new IllegalArgumentException("Percentage of treasure caves should be "
              + "between 0 and 100!");
    } else if (noOfMonsters < 1) {
      throw new IllegalArgumentException("There should be atleast one monster at the end!");
    }

    this.rand = rand;

    DungeonBuilder builder = new DungeonBuilder(rows, columns, wrapping, interconnectivity, rand);
    this.grid = builder.buildDungeon();

    this.rows = this.grid.length;
    this.cols = this.grid[0].length;

    this.MIN_DIST = 5;

    Tuple<Location, Location> endpoints = this.getStartAndEnd();

    if (endpoints.getX().equals(endpoints.getY())) {
      throw new IllegalStateException("Couldn't find start and end caves at distance of 5 "
              + "for dungeon of this size and interconnectivity! Try rebuilding dungeon or"
              + " increasing size or decreasing interconnectivity.");
    }
    this.start = endpoints.getX();
    this.end = endpoints.getY();

    this.addTreasure(treasurePct);
    this.addArrows(treasurePct);
    this.addMonsters(noOfMonsters);
  }

  private Tuple<Location, Location> getStartAndEnd() {
    ArrayList<Location> caves = new ArrayList<>();

    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        Location cell = this.grid[row][col];
        if (cell.isCave()) {
          caves.add(cell);
        }
      }
    }

    int startX;
    int startY;
    int endX;
    int endY;

    while (caves.size() != 0) {
      int caveIndex = this.rand.nextInt(caves.size());
      Location startCave = caves.get(caveIndex);
      startX = startCave.getCoordinates()[0];
      startY = startCave.getCoordinates()[1];

      ArrayList<Location> probableEnds = (ArrayList<Location>) caves.clone();
      probableEnds.remove(startCave);

      for (Location endCave : probableEnds) {
        endX = endCave.getCoordinates()[0];
        endY = endCave.getCoordinates()[1];

        if (this.grid[startX][startY].isCave() && this.grid[endX][endY].isCave()
                && (startX != endX && startY != endY)) {
          if (this.minDist(startX, startY, endX, endY) >= this.MIN_DIST) {
            return new Tuple<>(this.grid[startX][startY], this.grid[endX][endY]);

          }
        }
      }
      caves.remove(caveIndex);
    }

    return new Tuple<>(this.grid[0][0], this.grid[0][0]);
  }

  private int minDist(int startX, int startY, int endX, int endY) {
    return this.breadthFirstSearch(startX, startY, endX, endY);
  }

  private int breadthFirstSearch(int startX, int startY, int endX, int endY) {
    Set<Location> visited = new HashSet<>();
    Queue<Location> q = new LinkedList<>();
    Queue<Integer> qHeight = new LinkedList<>();

    q.add(this.grid[startX][startY]);
    qHeight.add(0);

    while (!q.isEmpty()) {
      Location cell = q.remove();
      int height = qHeight.remove();

      if (cell.equals(this.grid[endX][endY])) {
        return height;
      }

      visited.add(cell);

      for (Direction d : cell.getPossibleDirections()) {
        int[] coordinates = cell.getNeighbour(d);
        int x = coordinates[0];
        int y = coordinates[1];
        Location child = this.grid[x][y];

        if (!visited.contains(child)) {
          q.add(child);
          qHeight.add(height + 1);
        }
      }
    }

    return 0;
  }

  private void addTreasure(double percent) {
    List<Cell> caves = getAllCaves();

    int noOfTreasureCaves = (int) Math.ceil((percent / 100.0) * this.getNoOfCaves());

    Treasure[] treasures = new Treasure[]{Treasure.DIAMOND, Treasure.RUBY, Treasure.SAPPHIRE};

    while (noOfTreasureCaves > 0) {
      int caveIndex = this.rand.nextInt(caves.size());
      int treasureIndex = this.rand.nextInt(treasures.length);

      if (caves.get(caveIndex).getContent().size() == 0) {
        noOfTreasureCaves -= 1;
      }

      caves.get(caveIndex).fill(treasures[treasureIndex]);
    }
  }

  private void addMonsters(int noOfMonsters) {
    List<Cell> caves = getAllCaves();

    caves.remove((Cell) this.start);

    if (noOfMonsters > caves.size()) {
      noOfMonsters = caves.size() - 1;
    }

    ((Cell) this.end).putMonster(new Otyugh(this.end));
    caves.remove((Cell) this.end);
    noOfMonsters -= 1;

    while (noOfMonsters > 0) {
      int caveIndex = this.rand.nextInt(caves.size());

      Cell currCave = caves.get(caveIndex);
      currCave.putMonster(new Otyugh(currCave));

      caves.remove(caveIndex);

      noOfMonsters -= 1;
    }
  }

  private void addArrows(double percent) {
    int noOfArrowLocations = (int) Math.ceil((percent / 100.0)
            * (this.getNoOfCaves() + this.getNoOfTunnels()));

    while (noOfArrowLocations > 0) {
      int locationX = this.rand.nextInt(this.rows);
      int locationY = this.rand.nextInt(this.cols);

      Cell currCell = (Cell) this.getLocation(locationX, locationY);
      if (!currCell.equals(this.end)
              || Collections.frequency(currCell.getContent(), Weapon.ARROW) == 0) {
        int noOfArrows = this.rand.nextInt(3) + 1;

        if (currCell.getContent().size() == 0 || !currCell.getContent().contains(Weapon.ARROW)) {
          noOfArrowLocations -= 1;
        }

        for (int i = 0; i < noOfArrows; i++) {
          currCell.fill(Weapon.ARROW);
        }
      }
    }
  }

  private List<Cell> getAllCaves() {
    List<Cell> caves = new ArrayList<>();

    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        Location cell = this.grid[row][col];
        if (cell.isCave()) {
          caves.add((Cell) cell);
        }
      }
    }

    return caves;
  }

  @Override
  public Location getStart() {
    return this.start;
  }

  @Override
  public Location getEnd() {
    return this.end;
  }

  @Override
  public Location getLocation(int x, int y) {
    if (x < 0 || x >= this.rows) {
      throw new IllegalArgumentException("x out of bounds for getting location!");
    }
    if (y < 0 || y >= this.cols) {
      throw new IllegalArgumentException("y out of bounds for getting location!");
    }
    return this.grid[x][y];
  }

  @Override
  public int getNoOfCaves() {
    int count = 0;

    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        if (this.grid[row][col].isCave()) {
          count += 1;
        }
      }
    }
    return count;
  }

  @Override
  public int getNoOfTunnels() {
    return this.rows * this.cols - this.getNoOfCaves();
  }

  @Override
  public Location[][] getDungeonGrid() {
    Location[][] gridCopy = new Location[this.rows][this.cols];

    for (int row = 0; row < this.rows; row += 1) {
      for (int col = 0; col < this.cols; col += 1) {
        gridCopy[row][col] = this.grid[row][col];
      }
    }
    return gridCopy;
  }

  String[][] getDungeonLayout() {
    String[][] layout = new String[this.rows * 3][this.cols * 3];

    int layoutRow = 1;
    int layoutCol = 1;
    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        Cell cell = (Cell) this.grid[row][col];
        Set<Direction> directions = cell.getPossibleDirections();

        if (cell.isCave()) {
          if (cell.getContent().size() > 0) {
            layout[layoutRow][layoutCol] = "{X}";
          } else {
            layout[layoutRow][layoutCol] = "(C)";
          }

          if (cell.hasMonster()) {
            if (cell.getMonster().getHitsTaken() < 2) {
              layout[layoutRow][layoutCol] = "<M>";
            }
          }
        } else {
          layout[layoutRow][layoutCol] = " + ";
        }

        for (Object d : directions) {
          if (d == Direction.NORTH) {
            layout[layoutRow - 1][layoutCol] = "| |";//" ↑ ";
          } else if (d == Direction.EAST) {
            layout[layoutRow][layoutCol + 1] = "===";//"--→";
          } else if (d == Direction.SOUTH) {
            layout[layoutRow + 1][layoutCol] = "| |";//" ↓ ";
          } else if (d == Direction.WEST) {
            layout[layoutRow][layoutCol - 1] = "===";//"←--";
          }
        }
        layoutCol += 3;
      }
      layoutCol = 1;
      layoutRow += 3;
    }

    int startRow = 1 + this.start.getCoordinates()[0] * 3;
    int startCol = 1 + this.start.getCoordinates()[1] * 3;
    int endRow = 1 + this.end.getCoordinates()[0] * 3;
    int endCol = 1 + this.end.getCoordinates()[1] * 3;

    String start = layout[startRow][startCol];
    layout[startRow][startCol] = start.charAt(0) + "S" + start.charAt(2);
    String end = layout[endRow][endCol];
    layout[endRow][endCol] = end.charAt(0) + "G" + end.charAt(2);

    return layout;
  }
}
