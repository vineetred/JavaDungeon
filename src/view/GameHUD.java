package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.util.List;

class GameHUD extends JFrame {

  private String userDirection;
  private JButton north;
  private JButton south;
  private JButton east;
  private JButton west;


  protected GameHUD () {

    setSize(400, 400);
    setLocation(500, 500);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    this.setLayout(new FlowLayout());
    Container container = getContentPane();

    north = new BasicArrowButton(BasicArrowButton.NORTH);
    south = new BasicArrowButton(BasicArrowButton.SOUTH);
    east = new BasicArrowButton(BasicArrowButton.EAST);
    west = new BasicArrowButton(BasicArrowButton.WEST);

    container.add(north);
    container.add(south);
    container.add(east);
    container.add(west);

    pack();
    setVisible(true);
    setSize(500, 250);

    north.addActionListener(e -> {
      this.userDirection = "N";
    });

    south.addActionListener(e -> {
      this.userDirection = "S";
    });

    east.addActionListener(e -> {
      this.userDirection = "E";
    });

    west.addActionListener(e -> {
      this.userDirection = "W";
    });
  }

  // Will always return empty string unless some choice is made
  protected String getUserDirection() {
    return new String(userDirection);
  }

  protected void resetUserDirection() {
    this.userDirection = "";
  }
}
