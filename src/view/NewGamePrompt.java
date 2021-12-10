package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

class NewGamePrompt extends JFrame {

  private static JLabel display;
  private static JButton submitButton;
  private static JButton exitButton;
  private static JButton toggleButton;
  private static JTextField inputTreasure;
  private static JTextField inputMonsters;
  private static JTextField inputDegree;
  private static JTextField inputRows;
  private static JTextField inputCols;
  private static JTextField inputWrapped;
  private static JTextField inputCLI;

  private String myString;
  private final ArrayList<String> gameParameters;

  protected NewGamePrompt() {
    super("New game");

    gameParameters = new ArrayList<>();

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());
    Container container = getContentPane();

    // Rows
    display = new JLabel("Rows: ");
    container.add(display);

    // the text field
    inputRows = new JTextField(2);
    container.add(inputRows);

    // Columns
    display = new JLabel("Cols: ");
    container.add(display);

    // the text field
    inputCols = new JTextField(2);
    container.add(inputCols);

    // Interconnect
    display = new JLabel("Interconnectedness: ");
    container.add(display);

    // the text field
    inputDegree = new JTextField(5);
    container.add(inputDegree);

    // Interconnect
    display = new JLabel("Wrapped (true/false): ");
    container.add(display);

    // the text field
    inputWrapped = new JTextField(3);
    container.add(inputWrapped);

    // Number of monsters
    display = new JLabel("Num of monsters: ");
    container.add(display);

    // the text field
    inputMonsters = new JTextField(2);
    container.add(inputMonsters);

    // Treasure Percentage
    display = new JLabel("Treasure %: ");
    container.add(display);

    // the text field
    inputTreasure = new JTextField(2);
    container.add(inputTreasure);

    // CLI/GUI
    display = new JLabel("CLI(true/false)?: ");
    container.add(display);

    // the text field
    inputCLI = new JTextField(2);
    container.add(inputCLI);

    // echo button
    submitButton = new JButton("Submit");
    submitButton.setActionCommand("Submit Button");
    container.add(submitButton);

//    // toggle button
//    toggleButton = new JButton("Toggle color");
//    toggleButton.setActionCommand("Toggle color button");
//    container.add(toggleButton);
//
//    // exit button
//    exitButton = new JButton("Exit");
//    exitButton.setActionCommand("Exit Button");
//    container.add(exitButton);

    NewGamePrompt.submitButton.addActionListener(e -> {

      myString = inputRows.getText();
      gameParameters.add(myString);
      clearString(inputRows);

      myString = inputCols.getText();
      gameParameters.add(myString);
      clearString(inputCols);

      myString = inputDegree.getText();
      gameParameters.add(myString);
      clearString(inputDegree);

      myString = inputMonsters.getText();
      gameParameters.add(myString);
      clearString(inputMonsters);

      myString = inputTreasure.getText();
      gameParameters.add(myString);
      clearString(inputTreasure);

      myString = inputWrapped.getText();
      gameParameters.add(myString);
      clearString(inputWrapped);

      myString = inputCLI.getText();
      gameParameters.add(myString);
      clearString(inputCLI);

      this.dispose();
    });

    pack();
    setVisible(true);

    while (gameParameters.size() < 6) {
      try {
        // Sleep till we get our game params
        Thread.sleep(200);
      }

      catch (InterruptedException e) {

      }
    }

  }

  protected void clearString(JTextField inputJButton) {
    inputJButton.setText("");
  }

  protected ArrayList<String> getUserParameters() {
    return new ArrayList<>(gameParameters);
  }

}
