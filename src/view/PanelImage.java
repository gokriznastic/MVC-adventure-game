package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;


import model.Direction;
import model.Location;
import model.Smell;

/**
 * This class represents an object having the image for each panel, according to the current
 * location attributes according to the model.
 */
class PanelImage {
  private final Image image;

  public PanelImage(GridPanel panel, Location gridLoc, boolean player) {
    boolean otyugh = gridLoc.hasMonster() && gridLoc.getMonster().isAlive();
    boolean[] possDir = isPossibleDirection(gridLoc);

    Smell stench = gridLoc.getSmell();

    int panelWidth = panel.getWidth();
    int panelHeight = panel.getHeight();

    if (!gridLoc.isVisited()) {
      this.image = this.readImage(ImageCategory.BLANK.getFilePath())
              .getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
    } else {
      this.image = makeImage(player, otyugh, possDir[0], possDir[1], possDir[2], possDir[3],
              stench, panelWidth, panelHeight);
    }
  }

  private Image makeImage(boolean player, boolean otyugh,
                          boolean north, boolean south, boolean east, boolean west,
                          Smell stench, int w, int h) {

    // get base image for location according to directions possible
    List<String> directions = new ArrayList<>();
    if (east) {
      directions.add("E");
    }
    if (north) {
      directions.add("N");
    }
    if (south) {
      directions.add("S");
    }
    if (west) {
      directions.add("W");
    }

    Collections.sort(directions);
    List<String> directionsCopy = new ArrayList<>(directions);

    String baseImageFp = "";

    for (ImageCategory ic : ImageCategory.values()) {
      String fp = ic.getFilePath();
      fp = fp.substring(8, fp.length() - 4);

      for (String d : directions) {
        if (fp.contains(d)) {
          fp = fp.replace(d, "");
          directionsCopy.remove(d);
        } else {
          break;
        }
      }

      if (fp.equals("") && directionsCopy.size() == 0) {
        baseImageFp = ic.getFilePath();
        break;
      }
    }

    Image tempImage;

    tempImage = this.readImage(baseImageFp);

    if (otyugh) {
      tempImage = overlayImage((BufferedImage) tempImage,
              ImageCategory.OTYUGH.getFilePath(), 5, 10, 54, 36);
    }

    if (player) {
      if (stench == Smell.MORE_PUNGENT) {
        tempImage = overlayImage((BufferedImage) tempImage,
                ImageCategory.STENCH_HIGH.getFilePath(), 2, 2, 60, 60);
      } else if (stench == Smell.LESS_PUNGENT) {
        tempImage = overlayImage((BufferedImage) tempImage,
                ImageCategory.STENCH_LOW.getFilePath(), 2, 2, 60, 60);
      }

      tempImage = overlayImage((BufferedImage) tempImage,
              ImageCategory.PLAYER.getFilePath(), 5, 1, 60, 60);
    }

    return tempImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
  }

  private Image overlayImage(BufferedImage baseImage, String filePath, int x, int y, int w, int h) {
    Image image;
    try {
      image = overlay(baseImage, filePath, x, y, w, h);
    } catch (IOException ioe) {
      throw new IllegalStateException("Cannot overlay images correctly!");
    }

    return image;
  }

  Image getImage() {
    return this.image;
  }

  private Image overlay(BufferedImage starting, String fpath,
                        int xoffset, int yoffset, int width, int height) throws IOException {
    BufferedImage overlay = (BufferedImage) this.readImage(fpath);
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, xoffset, yoffset, width, height, null);

    return combined;
  }

  private boolean[] isPossibleDirection(Location gridLoc) {
    boolean[] dir = new boolean[4];

    if (gridLoc.getPossibleDirections().contains(Direction.NORTH)) {
      dir[0] = true;
    }
    if (gridLoc.getPossibleDirections().contains(Direction.SOUTH)) {
      dir[1] = true;
    }
    if (gridLoc.getPossibleDirections().contains(Direction.EAST)) {
      dir[2] = true;
    }
    if (gridLoc.getPossibleDirections().contains(Direction.WEST)) {
      dir[3] = true;
    }

    return dir;
  }

  private Image readImage(String filePath) {
    try {
      InputStream imageStream = getClass().getResourceAsStream(filePath);
      return ImageIO.read(imageStream);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot find base location image!");
    }
  }
}
