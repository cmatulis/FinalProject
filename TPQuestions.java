//// BUGS/IMPROVEMENTS NEEDED
// JAVADOC/COMMENT

import java.io.*;
import java.util.*;

public class TPQuestions{
  LinkedList<String> redquestions, greenquestions, yellowquestions, bluequestions, orangequestions, pinkquestions;
  
  
  public TPQuestions(){
    redquestions = loadQuestions("redQuestions.txt");
    greenquestions = loadQuestions("greenQuestions.txt");
    yellowquestions = loadQuestions("yellowQuestions.txt");
    bluequestions = loadQuestions("blueQuestions.txt");
    orangequestions = loadQuestions("orangeQuestions.txt");
    pinkquestions = loadQuestions("pinkQuestions.txt");
    
  }
  
  public LinkedList<String> loadQuestions(String filename){
    LinkedList<String> questions = new LinkedList<String>();
    try {
      Scanner reader = new Scanner(new File(filename));
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        questions.add(line);
      }
    } catch (IOException e) {
      System.out.println(e);
    }
    
    LinkedList<Integer> questionOrder = new LinkedList<Integer>();
    
    for (int i = 0; i < questions.size(); i += 6){
      questionOrder.add(i);

    }
     Collections.shuffle(questionOrder);
    System.out.println(questionOrder);
    LinkedList<String> temp = new LinkedList<String>();
    for (int j: questionOrder){
      for (int i = 0; i < 6; i++){
        temp.add(questions.get(j+i));
      }
    }
    
    
    questions = temp;
    return questions;
 
  }
  
  public static void main (String[] args){
    TPQuestions test = new TPQuestions();
    System.out.println(test.bluequestions);
   
  }
  
}