//// BUGS/IMPROVEMENTS NEEDED:
// JAVADOC/COMMENT
// PUT REPETETIVE ACTIONS IN SEPARATE METHODS
// DISABLE ROLL DIE BUTTON WHILE ANSWERING QUESTION
// SIDE PANELS: ON SEPARATE LINES, SHOW # OF QUESTIONS ANSWERED CORRECTLY AND
// COLORS CATEGORIES REMAINING (IN VERSION 2)
// CHANGE COLOR FOR DIE ROLL RESULTS
// CHANGE COLOR/FONT FOR #1 AND #2 ON JBUTTONS
// RENAME DUPLICATE VARIABLES
// ADD TITLES TO JOPTIONPANES
// FIX THE IMAGES THAT APPEAR IN JOPTIONPANES

/** 
 * GamePanel.java
 * Purpose: Creates the panel that contains the game board and determines how
 * the board will respond to user interactions
 * Written by: Catherine Matulis
 * Modified date: December 11, 2013
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javafoundations.*;
import java.util.Random;


public class GamePanel extends JPanel
{
  // instance variables
  private JButton newGameButton, rollDieButton;
  private TrivialPursuit trivialpursuit;
  private Color backgroundColor;
  private JTextArea questionBox, player1Name, player2Name, player2Label, player1Label, dieRollResult, player1ScoreLabel;
  private JTextArea player1Score, player2ScoreLabel, player2Score, player1Colors, player1Red, player1Blue, player1Green, player1Yellow, player1Orange, player1Pink;
  private JTextArea player2Colors, player2Red, player2Blue, player2Green, player2Yellow, player2Orange, player2Pink;
  private JButton[][] buttons; 
  private JOptionPane getP1Name, getP2Name;
  private DefaultListModel listmodel;
  private JRadioButton answerA, answerB, answerC, answerD;
  private ButtonListener listener;
  private int p1i, p1j, p2i, p2j, gameVersion;
  private String boardPosition;
  
  
  
  
  
  // Constructor.  Takes an instance of TrivialPursuit as input
  public GamePanel(TrivialPursuit t)
  {
    setLayout(new BorderLayout(10,10));
    backgroundColor = new Color(31, 190, 214);
    setBackground (backgroundColor);
    listener = new ButtonListener();
    this.trivialpursuit = t;
    
    
    // add panels to tab
    add(makeSouthPanel(), BorderLayout.PAGE_END);
    add(makeCenterPanel(), BorderLayout.CENTER);
    add(makeNorthPanel(), BorderLayout.PAGE_START);
    add(makeWestPanel(), BorderLayout.LINE_START);
    add(makeEastPanel(), BorderLayout.LINE_END);
  }
  
  /**
   * Creates and returns the top panel.
   * This panel contains the start new game button.
   * @return JPanel The top panel of the GUI
   */ 
  
  private JPanel makeNorthPanel(){
    
    JPanel northPanel = new JPanel();
    
    northPanel.setBackground(Color.white);
    
    newGameButton = new JButton("Start New Game");
    newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    newGameButton.addActionListener(listener);
    
    northPanel.add(newGameButton);
    
    return northPanel;
  }
  
  /**
   * Creates and returns the center panel.
   * This panel contains the game board.
   * @return JPanel The center panel of the GUI
   */ 
  private JPanel makeCenterPanel(){
    JPanel centerPanel = new JPanel();
    
    // create the game board as a 10 x 10 grid of JButtons
    centerPanel.setLayout(new GridLayout(10, 10, 10, 10));
    buttons = new JButton[10][10];
    for (int i = 0; i < 10; i++){
      for (int j = 0; j < 10; j++) {
        buttons[i][j] = new JButton(" ");
        buttons[i][j].addActionListener(listener);
        buttons[i][j].setVisible(false);
        buttons[i][j].setEnabled(false);
        centerPanel.add(buttons[i][j]);
      }
    }
    return centerPanel;
  }
  
  /**
   * Creates and returns the west panel.
   * This panel contains information about player 1.
   * @return JPanel The west panel of the GUI.
   */ 
  private JPanel makeWestPanel(){
    
    Font font = new Font("Verdana", Font.BOLD, 12);
    
    JPanel westPanel = new JPanel();
    westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
    westPanel.setBackground(Color.white);
    
    // add a text area that will later contain information about player 1
    player1Label = new JTextArea("Player 1: ");
    player1Label.setEditable(false);
    player1Label.setVisible(false);
    //player1Name = new JTextArea("");
   // player1Name.setEditable(false);
   // player1Name.setFont(font);
   // player1Name.setForeground(Color.blue);
    player1ScoreLabel = new JTextArea("Questions answered correctly: ");
    player1ScoreLabel.setVisible(false);
    player1ScoreLabel.setEditable(false);
    player1Score = new JTextArea("");
    player1Score.setEditable(false);
    
    player1Colors = new JTextArea("Color categories remaining: ");
    player1Colors.setEditable(false);
    player1Colors.setVisible(false);
    player1Red = new JTextArea("Red");
    player1Red.setEditable(false);
   player1Red.setFont(font);
   player1Red.setBackground(Color.red);
   player1Red.setVisible(false);
    player1Orange = new JTextArea("Orange");
    player1Orange.setEditable(false);
   player1Orange.setFont(font);
   player1Orange.setBackground(Color.orange);
   player1Orange.setVisible(false);
    player1Blue = new JTextArea("Blue");
    player1Blue.setEditable(false);
   player1Blue.setFont(font);
   player1Blue.setBackground(Color.blue);
   player1Blue.setVisible(false);
    player1Green = new JTextArea("Green");
    player1Green.setEditable(false);
   player1Green.setFont(font);
   player1Green.setBackground(Color.green);
   player1Green.setVisible(false);
    player1Yellow = new JTextArea("Yellow");
    player1Yellow.setEditable(false);
   player1Yellow.setFont(font);
   player1Yellow.setBackground(Color.yellow);
   player1Yellow.setVisible(false);
    player1Pink = new JTextArea("Pink");
    player1Pink.setEditable(false);
   player1Pink.setFont(font);
   player1Pink.setBackground(Color.pink);
   player1Pink.setVisible(false);
    //private Color[] colors = {Color.red, Color.orange, Color.blue, Color.green, Color.yellow, Color.pink};
    
    
    westPanel.add(player1Label);
   // westPanel.add(player1Name);
    westPanel.add(player1ScoreLabel);
    westPanel.add(player1Score);
    westPanel.add(player1Colors);
    westPanel.add(player1Red);
    westPanel.add(player1Orange);
    westPanel.add(player1Blue);
    westPanel.add(player1Green);
    westPanel.add(player1Yellow);
    westPanel.add(player1Pink);
    westPanel.add(Box.createRigidArea(new Dimension(0,4000)));
    westPanel.add(Box.createVerticalGlue());
    
    
    return westPanel;
  }
  
  /**
   * Creates and returns the east panel.
   * This panel contains information about player 2.
   * @return JPanel The east panel of the GUI.
   */ 
  private JPanel makeEastPanel(){
    Font font = new Font("Verdana", Font.BOLD, 12);
    
    JPanel eastPanel = new JPanel();
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    eastPanel.setBackground(Color.white);
    
    // add a text area that will later contain information about player 2
    player2Label = new JTextArea("Player 2: ");
    player2Label.setVisible(false);
    player2Label.setEditable(false);
    //player2Name = new JTextArea("");
    //player2Name.setEditable(false);
    //player2Name.setFont(font);
    //player2Name.setForeground(Color.red);
    player2ScoreLabel = new JTextArea("Questions answered correctly: ");
    player2ScoreLabel.setVisible(false);
    player2ScoreLabel.setEditable(false);
    player2Score = new JTextArea("");
    player2Score.setEditable(false);
    player2Colors = new JTextArea("Color categories remaining: ");
    player2Colors.setEditable(false);
    player2Colors.setVisible(false);
    player2Red = new JTextArea("Red");
    player2Red.setEditable(false);
   player2Red.setFont(font);
   player2Red.setBackground(Color.red);
   player2Red.setVisible(false);
    player2Orange = new JTextArea("Orange");
    player2Orange.setEditable(false);
   player2Orange.setFont(font);
   player2Orange.setBackground(Color.orange);
   player2Orange.setVisible(false);
    player2Blue = new JTextArea("Blue");
    player2Blue.setEditable(false);
   player2Blue.setFont(font);
   player2Blue.setBackground(Color.blue);
   player2Blue.setVisible(false);
    player2Green = new JTextArea("Green");
    player2Green.setEditable(false);
   player2Green.setFont(font);
   player2Green.setBackground(Color.green);
   player2Green.setVisible(false);
    player2Yellow = new JTextArea("Yellow");
    player2Yellow.setEditable(false);
   player2Yellow.setFont(font);
   player2Yellow.setBackground(Color.yellow);
   player2Yellow.setVisible(false);
    player2Pink = new JTextArea("Pink");
    player2Pink.setEditable(false);
   player2Pink.setFont(font);
   player2Pink.setBackground(Color.pink);
   player2Pink.setVisible(false);
   
    
    
    eastPanel.add(player2Label);
    //eastPanel.add(player2Name);
    eastPanel.add(player2ScoreLabel);
    eastPanel.add(player2Score);
    eastPanel.add(player2Colors);
    eastPanel.add(player2Red);
    eastPanel.add(player2Orange);
    eastPanel.add(player2Blue);
    eastPanel.add(player2Green);
    eastPanel.add(player2Yellow);
    eastPanel.add(player2Pink);
    eastPanel.add(Box.createRigidArea(new Dimension(0,4000)));
    eastPanel.add(Box.createVerticalGlue());
    return eastPanel;
  }
  
  /**
   * Creates and returns the bottom panel.
   * 
   * @return JPanel The bottom panel of the GUI
   */ 
  
  // THIS SHOULD BE MODIFIED TO INCLUDE BUTTONS FOR ROLLING DIE
  private JPanel makeSouthPanel(){
    JPanel southPanel = new JPanel();
    southPanel.setBackground(Color.white);
    
    
    questionBox = new JTextArea("");
    questionBox.setEditable(false);
    
    rollDieButton = new JButton("Roll die");
    rollDieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    //ButtonListener listener = new ButtonListener();
    rollDieButton.addActionListener(listener);
    rollDieButton.setVisible(false);
    dieRollResult = new JTextArea("");
    dieRollResult.setEditable(false);
    
    
    southPanel.add(rollDieButton);
    southPanel.add(dieRollResult);
    
    southPanel.add(questionBox);
    
    return southPanel;
  }
  
  /**
   * ButtonListener is a private class for responding to button push events 
   * 
   */ 
  private class ButtonListener implements ActionListener
  {
    private Color[] colors = {Color.red, Color.orange, Color.blue, Color.green, Color.yellow, Color.pink};
    Font font = new Font("Verdana", Font.BOLD, 18);
    
    public void actionPerformed (ActionEvent event)
    {
      
      // respond to player starting a new game
      //Color[] colors = {Color.red, Color.orange, Color.blue, Color.green, Color.yellow, Color.pink};
      if (event.getSource() == newGameButton){
        
        trivialpursuit.resetStats();
        //trivialpursuit.legalPositionsi.clear();
        // trivialpursuit.legalPositionsj.clear();
        //player1Name.setText("");
        //player2Name.setText("");
        questionBox.setText("");
        player1Score.setText("");
        player2Score.setText("");
        gameVersion = 0;
        
        
        
        // prompt for player names
        getP1Name = new JOptionPane();
        String p1name = getP1Name.showInputDialog(null, "Enter Player 1's name", "Enter Player 1's Name", JOptionPane.PLAIN_MESSAGE);
        System.out.println("p1name: " + p1name);
        if (p1name != null){
          trivialpursuit.setP1Name(p1name);
          System.out.println(trivialpursuit.p1Name);
          player1Label.setVisible(true);
          //player1Name.setText("");
          player1Label.setText("Player 1: " + trivialpursuit.p1Name);
          player1ScoreLabel.setVisible(true);
          
        }
        else{
          return;
        }
        JOptionPane getP2Name = new JOptionPane();
        String p2name = getP2Name.showInputDialog(null, "Enter Player 2's name", "Enter Player 2's Name", JOptionPane.PLAIN_MESSAGE);
        if (p2name != null){
          trivialpursuit.setP2Name(p2name);
          player2Label.setVisible(true);
          //player2Name.setText("");
          player2Label.setText("Player 2: " + trivialpursuit.p2Name);
          System.out.println(p2name);
          player2ScoreLabel.setVisible(true);
          player2Score.setVisible(true);
        }
        else{
          return;
        }
        Object[] options = { "Version 1", "Version 2" };
        int selection = JOptionPane.showOptionDialog(null, "Select which game version you would like to play.  \n\nVersion 1: Compete to answer the most questions correctly, regardless of color. \n\nVersion 2: Roll the die to move about the board.\nCompete to be the first to answer one question from each color category correctly."
                                                       , "Select Game Version",
                                                     JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                     null, options, options[0]);
        System.out.println("selection: " + selection);
        
        setAllButtons(selection);
        
        for (int i = 7; i < 9; i++){
          for (int j = 1; j < 9; j++){
            buttons[i][j].setVisible(false);
          }
        }
        if (selection == 0){
          gameVersion = 0;
          JOptionPane startGame = new JOptionPane();
          JOptionPane.showMessageDialog(startGame, trivialpursuit.getTurn(gameVersion), "Take Turn", JOptionPane.PLAIN_MESSAGE);
          rollDieButton.setVisible(false);
          player1Colors.setVisible(false);
          player1Red.setVisible(false);
          player1Orange.setVisible(false);
          player1Blue.setVisible(false);
          player1Green.setVisible(false);
          player1Yellow.setVisible(false);
          player1Pink.setVisible(false);
          player2Colors.setVisible(false);
          player2Red.setVisible(false);
          player2Orange.setVisible(false);
          player2Blue.setVisible(false);
          player2Green.setVisible(false);
          player2Yellow.setVisible(false);
          player2Pink.setVisible(false);
          dieRollResult.setVisible(false);
          
        }
        else if (selection == 1){
          gameVersion = 1;
          System.out.println("one");
          rollDieButton.setVisible(true);
          player1Colors.setVisible(true);
          player1Red.setVisible(true);
          player1Orange.setVisible(true);
          player1Blue.setVisible(true);
          player1Green.setVisible(true);
          player1Yellow.setVisible(true);
          player1Pink.setVisible(true);
          player2Colors.setVisible(true);
          player2Red.setVisible(true);
          player2Orange.setVisible(true);
          player2Blue.setVisible(true);
          player2Green.setVisible(true);
          player2Yellow.setVisible(true);
          player2Pink.setVisible(true);
          buttons[0][0].setText("1");
          p1i = 0;
          p1j = 0;
          buttons[9][9].setText("2");
          p2i = 9;
          p2j = 9;
          JOptionPane startGame = new JOptionPane();
          JOptionPane.showMessageDialog(startGame, trivialpursuit.getTurn(gameVersion), "Take Turn", JOptionPane.PLAIN_MESSAGE);
        }
        else{ 
          return;
        }
        
        
        
      }
      
      if (!trivialpursuit.isGameOver(gameVersion)){
        // if (gameVersion == 0){
        for (int i = 0; i < 10; i++) {
          for (int j = 0; j < 10; j++) {
            //if (!trivialpursuit.isGameOver()){
            
            if (event.getSource() == buttons[i][j]) {
              if (trivialpursuit.whoseTurn() == 1 && gameVersion == 1){
                buttons[p1i][p1j].setText("");
                p1i = i;
                p1j = j;
              }
              if (trivialpursuit.whoseTurn() == 2 && gameVersion == 1){
                buttons[p2i][p2j].setText("");
                p2i = i;
                p2j = j;
              }
              trivialpursuit.askQuestion(buttons[i][j].getBackground());
              Object answerChoices[] = {trivialpursuit.choiceA, trivialpursuit.choiceB, trivialpursuit.choiceC, trivialpursuit.choiceD};
              Object selection = null;
              while (selection == null){
              selection = JOptionPane.showInputDialog(null, trivialpursuit.currentQuestion, "Answer question", JOptionPane.QUESTION_MESSAGE, null, answerChoices, "unknown");
              }
              if (gameVersion == 0){
                trivialpursuit.gamegraph.removeVertex(Integer.toString(i).concat(Integer.toString(j)));
                buttons[i][j].setText("X");
              }
              
              trivialpursuit.statistics(selection.equals(trivialpursuit.correctAnswer), buttons[i][j].getBackground());
              if (gameVersion == 1){
              setColorCategoriesArea(buttons[i][j].getBackground());
              }
              System.out.println("Player " + trivialpursuit.whoseTurn() + "'s categories answered: \n");
              if (trivialpursuit.whoseTurn() == 1){
                System.out.println(trivialpursuit.p1colors);
              }
              if (trivialpursuit.whoseTurn() == 2){
                System.out.println(trivialpursuit.p2colors);
              }
              if (gameVersion == 1){
                setAllButtons(gameVersion); 
                rollDieButton.setEnabled(true);
                
              }
              
              player1Score.setText((Integer.toString(trivialpursuit.p1score)));
              player2Score.setText((Integer.toString(trivialpursuit.p2score)));
              if (trivialpursuit.isGameOver(gameVersion)){
                JOptionPane showWinner = new JOptionPane();
                JOptionPane.showMessageDialog(showWinner, trivialpursuit.getWinner(), "Game Over", JOptionPane.PLAIN_MESSAGE);
                System.out.println(trivialpursuit.getWinner());
                return;
              }
              JOptionPane nextTurn = new JOptionPane();
              JOptionPane.showMessageDialog(nextTurn, trivialpursuit.getTurn(gameVersion), "Take Turn", JOptionPane.PLAIN_MESSAGE);
              if (gameVersion == 0){
                changeButtonState(i, j, false);
              }
              
            }
          }
        }
      }
      if(event.getSource() == rollDieButton){
        System.out.println("die rolled");
        trivialpursuit.rollDice();
        dieRollResult.setVisible(true);
        dieRollResult.setText("Die roll result: " + trivialpursuit.mostRecentRoll);
        rollDieButton.setEnabled(false);
        
        if (trivialpursuit.whoseTurn() == 1){
          boardPosition = (Integer.toString(p1i).concat(Integer.toString(p1j)));
        }
        else if (trivialpursuit.whoseTurn() == 2){
          boardPosition = (Integer.toString(p2i).concat(Integer.toString(p2j)));
        }
        trivialpursuit.legalPositions(boardPosition, trivialpursuit.mostRecentRoll);
        System.out.println("lpi " + trivialpursuit.legalPositionsi);
        System.out.println("lpj " + trivialpursuit.legalPositionsj);
        for (int m = 0; m < 10; m++){
          for (int n = 0; n < 10; n++){
            
            if (trivialpursuit.legalPositionsi.contains(m) && trivialpursuit.legalPositionsj.contains(n) && 
                (trivialpursuit.legalPositionsi.indexOf(m) == trivialpursuit.legalPositionsj.indexOf(n) |
                 trivialpursuit.legalPositionsi.indexOf(m) == trivialpursuit.legalPositionsj.lastIndexOf(n) |
                 trivialpursuit.legalPositionsi.lastIndexOf(m) == trivialpursuit.legalPositionsj.indexOf(n) |
                 trivialpursuit.legalPositionsi.lastIndexOf(m) == trivialpursuit.legalPositionsj.lastIndexOf(n))){
              buttons[m][n].setEnabled(true);
              
            }
            else{
              if (buttons[m][n].isVisible()){
                changeButtonState(m,n, false);
              }
            }
            
          }
        }
      }
    }
    
    
    
    public void setAllButtons(int selection){
      Random rand = new Random();
      int r = rand.nextInt(6);
      int k = 0;
      for (int i = 0; i < 10; i++){
        
        for(int j = 0; j < 10; j++){
          buttons[i][j].setVisible(true);
          buttons[i][j].setBorderPainted(false);
          buttons[i][j].setOpaque(true);
          buttons[i][j].setEnabled(false);
          buttons[i][j].setBackground(colors[(i+j+r+k)%colors.length]);
          buttons[i][j].setText("");
          buttons[i][j].setFont(font);
          if (selection == 0){
            buttons[i][j].setEnabled(true);
          }
        }
        k++;
      }
      
      // set some of the buttons to be invisible
      for (int i = 1; i < 3; i++){
        for (int j = 1; j < 9; j++){
          buttons[i][j].setVisible(false);
        }
      }
      
      for (int i = 4; i < 6; i++){
        for (int j = 1; j < 9; j++){
          buttons[i][j].setVisible(false);
        }
      }
      for (int i = 7; i < 9; i++){
        for (int j = 1; j < 9; j++){
          buttons[i][j].setVisible(false);
        }
      } 
      if (gameVersion == 1){
        if (p1i == p2i && p1j == p2j){
          buttons[p1i][p2j].setText("1, 2"); 
        }
        else{
          buttons[p1i][p1j].setText("1");
          buttons[p2i][p2j].setText("2");
        }
      }
      
    }
    
    
    public void changeButtonState(int m, int n, boolean k){
      buttons[m][n].setForeground(buttons[m][n].getBackground());
      buttons[m][n].setContentAreaFilled(k);
      buttons[m][n].setOpaque(k);
      buttons[m][n].setBorderPainted(!k);
      buttons[m][n].setEnabled(k);
      
      
    }
    
    public void setColorCategoriesArea(Color c){
      if (trivialpursuit.p1colors.contains(Color.red) && trivialpursuit.whoseTurn() == 2){
        player1Red.setVisible(false);
      }
      if (trivialpursuit.p1colors.contains(Color.orange) && trivialpursuit.whoseTurn() == 2){
        player1Orange.setVisible(false);
      }
      if (trivialpursuit.p1colors.contains(Color.green) && trivialpursuit.whoseTurn() == 2){
        player1Green.setVisible(false);
      }
      if (trivialpursuit.p1colors.contains(Color.blue) && trivialpursuit.whoseTurn() == 2){
        player1Blue.setVisible(false);
      }
      if (trivialpursuit.p1colors.contains(Color.yellow) && trivialpursuit.whoseTurn() == 2){
        player1Yellow.setVisible(false);
      }
      if (trivialpursuit.p1colors.contains(Color.pink) && trivialpursuit.whoseTurn() == 2){
        player1Pink.setVisible(false);
      }
      if (trivialpursuit.p2colors.contains(Color.red) && trivialpursuit.whoseTurn() == 1){
        player2Red.setVisible(false);
      }
      if (trivialpursuit.p2colors.contains(Color.orange) && trivialpursuit.whoseTurn() == 1){
        player2Orange.setVisible(false);
      }
      if (trivialpursuit.p2colors.contains(Color.green) && trivialpursuit.whoseTurn() == 1){
        player2Green.setVisible(false);
      }
      if (trivialpursuit.p1colors.contains(Color.blue) && trivialpursuit.whoseTurn() == 1){
        player2Blue.setVisible(false);
      }
      if (trivialpursuit.p2colors.contains(Color.yellow) && trivialpursuit.whoseTurn() == 1){
        player2Yellow.setVisible(false);
      }
      if (trivialpursuit.p2colors.contains(Color.pink) && trivialpursuit.whoseTurn() == 1){
        player2Pink.setVisible(false);
      }
      
        
      
      
      
    }
  }
  
  
  
  
}
