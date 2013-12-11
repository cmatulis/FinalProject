/// BUGS/IMPROVEMENTS NEEDED:
// LARGER TITLE
// BUTTON TO OPEN HELP MANUAL
// INFO ABOUT WHAT GAME DOES
// COMMENT AND JAVADOC THIS FILE

/** 
 * AboutPanel.java
 * Purpose: Creates the first tab for the Trivial Pursuit GUI, which will 
 * contain information about the rules of the game
 * Written by: Catherine Matulis
 * Modified date: December 1, 2013
 */


import java.awt.*;
import javax.swing.*;

public class AboutPanel extends JPanel
{
  private JLabel l1, l2, l3, l4;
  
  // Constructor
  public AboutPanel()
  {
    setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
    Color backgroundColor = new Color(31, 190, 214);
    setBackground (backgroundColor);
    
    // add some information about the GUI and how to use it
    // THIS NEEDS TO BE MODIFIED
    // ADD A BUTTON THAT WILL OPEN PDF USER'S MANUAL
    l1 = new JLabel ("<html><b>Trivial Pursuit 2</b></html>");
    l2 = new JLabel("Created By Catherine Matulis and Jinglan Wang");
    l3 = new JLabel ("This GUI will allow you to play a trivial pursuit game");
   
    
    add (l1);
    add(l2);
    add (Box.createRigidArea (new Dimension (0, 10)));
    add (l3);
   
    
  }
}
