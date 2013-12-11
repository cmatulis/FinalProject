//// BUGS/IMPROVEMENTS NEEDED:
// SHUFFLE QUESTIONS
// JAVADOC/COMMENT
// FIX DUPLICATE VARIABLES

/** Code for playing a Trivial Pursuit-esque game.
  * 
  * Written by: Catherine Matulis
  * Modified date: December 9, 2013
  */

import java.util.Arrays;
import java.util.*;
import java.awt.Color;
import java.awt.*;
import javax.swing.*;

public class TrivialPursuit {
  
  String currentQuestion, choiceA, choiceB, choiceC, choiceD, correctAnswer, p1Name, p2Name;
  int numRounds, p1score, p2score, totalAnswered, mostRecentRoll, turn, diceSides = 6;
  TPQuestions questionbank;
  TPGraph<String> gamegraph;
  LinkedList<Integer> legalPositionsi, legalPositionsj;
  LinkedList<Color> p1colors, p2colors;
  
  
  /**
   * Class constructor.  Creates an object to represent a game of Trivial Pursuit 2, a game created by Catherine and Jing.
   * 
   */ 
  public TrivialPursuit() {
    questionbank = new TPQuestions();
    gamegraph = new TPGraph<String>("tpgraphtgf.tgf");
    legalPositionsi = new LinkedList<Integer>();
    legalPositionsj = new LinkedList<Integer>();
    mostRecentRoll = 0;
    p1colors = new LinkedList<Color>();
    p2colors = new LinkedList<Color>();
    totalAnswered = 0;
    turn = 1;
    
  }
  
  /**
   * Resets the statistics of a Trivial Pursuit game to their original values.  To be used if the players begin one game and
   * want to play another.
   *
   */ 
  public void resetStats(){
    gamegraph = new TPGraph<String>("tpgraphtgf.tgf");
    legalPositionsi = new LinkedList<Integer>();
    legalPositionsj = new LinkedList<Integer>();
    mostRecentRoll = 0;
    p1colors = new LinkedList<Color>();
    p2colors = new LinkedList<Color>();
    p1score = 0;
    p2score = 0;
    turn = 1;
    totalAnswered = 0;
  }
  
  /**
   * Returns an integer representing the player whose turn it currently is.
   *
   * @return 1 if it is player 1's turn, 2 if it is player 2's turn
   */ 
  public int whoseTurn(){
    if (totalAnswered%2 == 0 ){
      turn = 1;
    }
    else{
      turn = 2; 
    }
    
    return turn;
  }
  
  /**
   * Returns an string message with information about the winner of the game.
   *
   * @return a message congratulating the winner of the game
   */ 
  public String getWinner(){
    String winner = "";
    if (p1score > p2score){
      winner += "Player 1 wins!  Congratulations Player 1!";
    }
    else if (p2score > p1score){
      winner += "Player 2 wins! Congratulations Player 2!";
    }
    else 
      winner += "It's a tie!  Congratulations to all!";
    return winner;
  }
  
   /**
   * Setter for Player 1's name
   *
   * @param p1 the name of player 1
   */ 
  public void setP1Name(String p1){
    p1Name = p1;
  }
  
  /**
   * Setter for Player 2's name
   *
   * @param p2 the name of player 2
   */ 
  public void setP2Name(String p2){
    p2Name = p2;
  }
  
  /**
   * Gets a question from a linked list corresponding to a color.
   *
   * @param c a color corresponding to a question list
   */ 
  public void askQuestion(Color c){
    LinkedList<String> qlist = null;
    if (c.equals(Color.red)){
      qlist = questionbank.redquestions;
    }
    else if (c.equals(Color.orange)){
      qlist = questionbank.orangequestions;
    }
    else if (c.equals(Color.yellow)){
      qlist = questionbank.yellowquestions;
    }
    else if (c.equals(Color.green)){
      qlist = questionbank.greenquestions;
    }
    else if (c.equals(Color.blue)){
      qlist = questionbank.bluequestions;
    }
    else if (c.equals(Color.pink)){
      qlist = questionbank.pinkquestions;
    }
    currentQuestion = qlist.pop(); 
    choiceA = qlist.pop();
    choiceB = qlist.pop();
    choiceC = qlist.pop();
    choiceD = qlist.pop();
    correctAnswer = qlist.pop();
    qlist.add(currentQuestion);
    qlist.add(choiceA);
    qlist.add(choiceB);
    qlist.add(choiceC);
    qlist.add(choiceD);
    qlist.add(correctAnswer);
  }
  
  /**
   * Represents rolling a 6-sided die.  The result of the roll is a random number 
   * between 1 and 6.
   *
   */ 
  public void rollDice() {
    Random generator = new Random();
    mostRecentRoll = generator.nextInt(6)+1;
    
  }
  
   /**
   * Returns a message prompting a player to play their turn.
   * 
   * @param gameVersion 0 for version 1 (no die rolls involved), 1 for version 2
   * @return a message prompting the user to play their turn
   */ 
  public String getTurn(int gameVersion){
    String turnMessage = "";
    if (totalAnswered%2 == 0 ){
      turnMessage += "Player 1's turn.  ";
    }
    else if (totalAnswered%2 == 1){
      turnMessage += "Player 2's turn.  ";
    }
    if (gameVersion == 0){
      turnMessage += "Click a tile on the board to answer a question!";
    }
    else if (gameVersion == 1){
      turnMessage += "Roll the die and click a tile on the board to answer a question!";
    }
    return turnMessage;
    
  }
  
   /**
   * Determines the positions on the game board that a user can legally reach on their turn.  These
   * positions are stored in linked lists.
   * 
   * @param boardPosition a string corresponding to the player's current position on the game board
   * @param dieResult an integer representing the player's die roll
   */ 
  public void legalPositions(String boardPosition, int dieResult){
    legalPositionsi.clear();
    legalPositionsj.clear();
    LinkedList<String> stringPositions = gamegraph.getNeighbors(boardPosition, dieResult);
    for (int i = 0; i < stringPositions.size(); i++){
      String xcoord = Character.toString(stringPositions.get(i).charAt(0));
      String ycoord = Character.toString(stringPositions.get(i).charAt(1));
      legalPositionsi.add(Integer.parseInt(xcoord));
      legalPositionsj.add(Integer.parseInt(ycoord));
    }
  }
  
   /**
   * Determines if the current game is over.
   * 
   * @param gameVersion 0 for version 1 (no die rolls involved), 1 for version 2
   * @return true if the game is over, false if it is not
   */ 
  public boolean isGameOver(int gameVersion){
    if (gameVersion == 0){
      return gamegraph.isEmpty(); 
    }
    else{
      return (p1colors.size() == 6 | p2colors.size() == 6);
    }
  }
  
   /**
   * Keeps track of statistics for the game, including number of questions answered correctly
   * by each player, total number of questions answered, and color categories from which a question
   * has been answered correctly.
   * 
   * @param correctAnswer true if the most recently presented question has been answered correctly, false
   *                      if it has not
   * @param tileColor the color corresponding to the most recently answered question
   */ 
  public void statistics(boolean correctAnswer, Color tileColor){
    if (totalAnswered%2 == 0 && correctAnswer){
      p1score++;
      if (!p1colors.contains(tileColor)){
        p1colors.add(tileColor);
      }
    }
    if (totalAnswered%2 == 1 && correctAnswer){
      p2score++;
      if (!p2colors.contains(tileColor)){
        p2colors.add(tileColor);
      }
    }
    if (correctAnswer){
      JOptionPane correct = new JOptionPane();
      correct.showMessageDialog(correct, "Correct!");
    }
    else if (!correctAnswer){
      JOptionPane incorrect = new JOptionPane();
      incorrect.showMessageDialog(incorrect, "Sorry, Incorrect!");
    }
    totalAnswered++;
  }
}