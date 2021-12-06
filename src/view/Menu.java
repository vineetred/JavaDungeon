package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu
{

  JMenu menu;
  JMenu submenu;

  String userChoice;


  // Constructor
  public Menu() {

    JFrame f = new JFrame("Dungeon Game");
    JMenuBar mb = new JMenuBar();
    this.userChoice = "";

    initializeGameMenu(f, mb);

  }

  static class exitMenu implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      System.exit(0);
    }
  }

  private void initializeGameMenu(JFrame inputJFrame, JMenuBar inputJMenuBar) {
    JMenuItem i2, i3;
    inputJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    menu = new JMenu("Settings");
    submenu = new JMenu("New Game");

    i2 = new JMenuItem("Command Line");
    i3 = new JMenuItem("GUI");

    submenu.add(i2); submenu.add(i3);
    menu.add(submenu);

    inputJMenuBar.add(menu);
    inputJFrame.setJMenuBar(inputJMenuBar);
    inputJFrame.setSize(400,400);
    inputJFrame.setLayout(null);
    inputJFrame.setVisible(true);

    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(new exitMenu());
    menu.add(exit);

    i2.addActionListener(e -> {
      this.userChoice = "CLI";
    });

    i3.addActionListener(e -> {
      this.userChoice = "GUI";
    });
  }

  // Will always return empty string unless some choice is made
  protected String getUserInGameChoice() {
    return new String(userChoice);
  }

  protected void resetUserChoice() {
    this.userChoice = "";
  }

}