package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ReadonlyGameModel;
import model.Treasure;

/**
 * This class represents a custom JPanel that displays the information
 * about the player. For e.g. the treasure and no. of arrows possessed by him.
 */
class PlayerInfoPanel extends JPanel {
  private final ReadonlyGameModel model;
  private final JLabel diamond;
  private final JLabel ruby;
  private final JLabel sapphire;
  private final JLabel arrow;

  /**
   * Constructor for initializing the player info panel.
   *
   * @param m the read-only model
   */
  public PlayerInfoPanel(ReadonlyGameModel m) {
    this.model = m;

    Map<Treasure, Integer> possession = this.model.getPlayer().getTreasureCollected();
    int arrows = this.model.getPlayer().getArrowsLeft();

    this.setLayout(new FlowLayout());

    JLabel playerLabel = new JLabel(" Player:");
    this.add(playerLabel);

    diamond = createInfoJLabel(ImageCategory.DIAMOND,
            possession.getOrDefault(Treasure.DIAMOND, 0));
    this.add(diamond);
    ruby = createInfoJLabel(ImageCategory.RUBY,
            possession.getOrDefault(Treasure.RUBY, 0));
    this.add(ruby);
    sapphire = createInfoJLabel(ImageCategory.SAPPHIRE,
            possession.getOrDefault(Treasure.SAPPHIRE, 0));
    this.add(sapphire);
    arrow = createInfoJLabel(ImageCategory.ARROW, arrows);
    this.add(arrow);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Map<Treasure, Integer> possession = this.model.getPlayer().getTreasureCollected();
    int arrows = this.model.getPlayer().getArrowsLeft();

    diamond.setText(String.valueOf(possession.getOrDefault(Treasure.DIAMOND, 0)));
    ruby.setText(String.valueOf(possession.getOrDefault(Treasure.RUBY, 0)));
    sapphire.setText(String.valueOf(possession.getOrDefault(Treasure.SAPPHIRE, 0)));
    arrow.setText(String.valueOf(arrows));

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
