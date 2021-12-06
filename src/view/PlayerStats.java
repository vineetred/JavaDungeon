package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class PlayerStats extends JFrame {

  public static JLabel userTreasure;
  public static JLabel userArrows;
  public static JLabel userAlive;

  protected PlayerStats() {

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    this.setLayout(new FlowLayout());
    Container container = getContentPane();

    // userArrows
    userArrows = new JLabel("Arrows: 0 ", SwingConstants.CENTER);
    container.add(userArrows);


    // userTreasure
    userTreasure = new JLabel("Treasure: None ");
    userTreasure.setForeground(Color.RED);
    container.add(userTreasure);

    // userAlive
    userAlive = new JLabel("Alive: True ");
    container.add(userAlive);

    pack();
    setVisible(true);

    setSize(500, 250);

  }

  protected void changePlayerStats(List<String> inputUserData) {

    userArrows.setText("Arrows: " + inputUserData.get(0) + " ");
    userTreasure.setText("Treasure: " + inputUserData.get(1) + " ");
    userAlive.setText("Alive: " + inputUserData.get(2));

    this.repaint();
  }
}
