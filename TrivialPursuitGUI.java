//// BUGS/IMPROVEMENTS NEEDED:
// JAVADOC/COMMENT

/** 
 * TrivialPursuitGUI.java
 * Purpose: Sets up the GUI for playing a Trivial Pursuit-esque game.
 * Written by: Catherine Matulis
 * Modified date: December 7, 2013
 */

import javax.swing.*;

public class TrivialPursuitGUI {
  
  public static void main (String[] args) {
    // creates the frame
    JFrame frame = new JFrame("Trivial Pursuit");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //creates an instance of TrivialPursuit
    TrivialPursuit trivialpursuit = new TrivialPursuit();

    //creates tabs, and adds them to the frame
    JTabbedPane tp = new JTabbedPane();
    tp.addTab ("About", new AboutPanel());
    tp.addTab("Play!", new GamePanel(trivialpursuit));
    frame.getContentPane().add(tp);
    frame.pack();
    frame.setSize(1400,800);
    frame.setVisible(true);
  }
}