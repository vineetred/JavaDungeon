package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InGameMenu
{

  JMenu menu;
  JMenu submenu;
  String userChoice;


  // Constructor
  public InGameMenu() {

    JFrame f = new JFrame("InGame Menu");
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
    JMenuItem i1, restartButton, newGameButton;
    inputJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    menu = new JMenu("Options");

    restartButton = new JMenuItem("Restart Game");
    newGameButton = new JMenuItem("New Game");

    menu.add(restartButton);
    menu.add(newGameButton);

    inputJMenuBar.add(menu);
    inputJFrame.setJMenuBar(inputJMenuBar);
    inputJFrame.setSize(400,400);
    inputJFrame.setLayout(null);
    inputJFrame.setVisible(true);

    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(new exitMenu());
    menu.add(exit);

    restartButton.addActionListener(e -> {
      this.userChoice = "Restart Game";
    });

    newGameButton.addActionListener(e -> {
      this.userChoice = "New Game";
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