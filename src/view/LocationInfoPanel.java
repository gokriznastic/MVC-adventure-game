package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Item;
import model.ReadonlyGameModel;
import model.Treasure;
import model.Weapon;

/**
 * This class represents a custom JPanel that displays the information
 * about the player's current location.
 * For e.g. the treasure and no. of arrows contained in the location.
 */
class LocationInfoPanel extends JPanel {
  private final ReadonlyGameModel model;
  private final JLabel diamond;
  private final JLabel ruby;
  private final JLabel sapphire;
  private final JLabel arrow;

  /**
   * Constructor for initializing the location info panel.
   *
   * @param m the read-only model
   */
  public LocationInfoPanel(ReadonlyGameModel m) {
    this.model = m;

    List<Item> content = this.model.getPlayer().getCurrentLocation().getContent();

    this.setLayout(new FlowLayout());

    JLabel playerLabel = new JLabel(" Location :");
    this.add(playerLabel);

    diamond = createInfoJLabel(ImageCategory.DIAMOND,
            Collections.frequency(content, Treasure.DIAMOND));
    this.add(diamond);
    ruby = createInfoJLabel(ImageCategory.RUBY,
            Collections.frequency(content, Treasure.RUBY));
    this.add(ruby);
    sapphire = createInfoJLabel(ImageCategory.SAPPHIRE,
            Collections.frequency(content, Treasure.SAPPHIRE));
    this.add(sapphire);
    arrow = createInfoJLabel(ImageCategory.ARROW,
            Collections.frequency(content, Weapon.ARROW));
    this.add(arrow);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    List<Item> content = this.model.getPlayer().getCurrentLocation().getContent();

    diamond.setText(String.valueOf(Collections.frequency(content, Treasure.DIAMOND)));
    ruby.setText(String.valueOf(Collections.frequency(content, Treasure.RUBY)));
    sapphire.setText(String.valueOf(Collections.frequency(content, Treasure.SAPPHIRE)));
    arrow.setText(String.valueOf(Collections.frequency(content, Weapon.ARROW)));

    this.validate();
  }

  private JLabel createInfoJLabel(ImageCategory item, int count) {
    ImageIcon icon;

    try {
      InputStream imageStream = getClass().getResourceAsStream(item.getFilePath());
      icon = new ImageIcon(ImageIO.read(imageStream));
    } catch (IOException ioe) {
      throw new IllegalStateException("Cannot find icon location image!");
    }

    JLabel thumbnail = new JLabel();
    thumbnail.setIcon(icon);
    thumbnail.setText(" " + count + " ");

    return thumbnail;
  }
}
