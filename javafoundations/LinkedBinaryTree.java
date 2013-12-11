//*******************************************************************
//  LinkedBinaryTree.java       Java Foundations
//
//  Implements a binary tree using a linked representation.
//*******************************************************************

package javafoundations;

import java.util.Iterator;
import javafoundations.*;
import javafoundations.exceptions.*;

public class LinkedBinaryTree<T> implements BinaryTree<T>
{
  protected BTNode<T> root;
  
  //-----------------------------------------------------------------
  //  Creates an empty binary tree.
  //-----------------------------------------------------------------
  public LinkedBinaryTree()
  {
    root = null;
  }
  
  //-----------------------------------------------------------------
  //  Creates a binary tree with the specified element as its root.
  //-----------------------------------------------------------------
  public LinkedBinaryTree (T element)
  {
    root = new BTNode<T>(element);
  }
  
  //-----------------------------------------------------------------
  //  Creates a binary tree with the two specified subtrees.
  //-----------------------------------------------------------------
  public LinkedBinaryTree (T element, LinkedBinaryTree<T> left,
                           LinkedBinaryTree<T> right)
  {
    root = new BTNode<T>(element);
    root.setLeft(left.root);
    root.setRight(right.root);
  }
  
  //-----------------------------------------------------------------
  //  Returns the element stored in the root of the tree. Throws an
  //  EmptyCollectionException if the tree is empty.
  //-----------------------------------------------------------------
  public T getRootElement() {
    if (root == null)
      throw new EmptyCollectionException ("Get root operation "
                                            + "failed. The tree is empty.");
    
    return root.getElement();
  }
  
  //-----------------------------------------------------------------
  //  Returns the left subtree of the root of this tree.
  //-----------------------------------------------------------------
  public LinkedBinaryTree<T> getLeft() {
    if (root == null)
      throw new EmptyCollectionException ("Get left operation "
                                            + "failed. The tree is empty.");
    
    LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
    result.root = root.getLeft();
    
    return result;
  }
  
  //-----------------------------------------------------------------
  //  Returns the element in this binary tree that matches the
  //  specified target. Throws a ElementNotFoundException if the
  //  target is not found.
  //-----------------------------------------------------------------
  public T find (T target) {
    BTNode<T> node = null;
    
    if (root != null)
      node = root.find(target);
    
    if (node == null)
      throw new ElementNotFoundException("Find operation failed. "
                                           + "No such element in tree.");
    
    return node.getElement();
  }
  
  //-----------------------------------------------------------------
  //  Returns the number of elements in this binary tree.
  //-----------------------------------------------------------------
  public int size() {
    int result = 0;
    
    if (root != null)
      result = root.count();
    
    return result;
  }
  
  //-----------------------------------------------------------------
  //  Populates and returns an iterator containing the elements in
  //  this binary tree using an inorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> inorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.inorder (iter);
    
    return iter;
  }
  
  //-----------------------------------------------------------------
  //  Populates and returns an iterator containing the elements in
  //  this binary tree using a levelorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> levelorder() {
    LinkedQueue<BTNode<T>> queue = new LinkedQueue<BTNode<T>>();
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null) {
      queue.enqueue(root);
      while (!queue.isEmpty()) {
        BTNode<T> current = queue.dequeue();
        
        iter.add (current.getElement());
        
        if (current.getLeft() != null)
          queue.enqueue(current.getLeft());
        if (current.getRight() != null)
          queue.enqueue(current.getRight());
      }
    }
    return iter;
  }
  
  //-----------------------------------------------------------------
  //  Satisfies the Iterable interface using an inorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> iterator() {
    return inorder();
  }
  
  //-----------------------------------------------------------------
  //  The following methods are left as programming projects.
  //-----------------------------------------------------------------
  
  public String toString() {
    Iterator<T> iter = levelorder();
    String result = "";
    while (iter.hasNext())
      result += iter.next() + "\n";
    
    return result;

  }

  public LinkedBinaryTree<T> getRight() {
    if (root == null)
      throw new EmptyCollectionException ("Get right operation "
                                            + "failed. The tree is empty.");
    
    LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
    result.root = root.getRight();
    
    return result;

  }
  
  public boolean contains (T target) {
    Iterator<T> iter = iterator();
    boolean contains = false;
    while (iter.hasNext()){
      if (iter.next().equals(target)){
      contains = true;
    }
    } 
    return contains;
    
  }
  
  public boolean isEmpty() {
    return root == null;

  }
  
  public Iterator<T> preorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.preorder (iter);
    
    return iter;
  }
  
  public Iterator<T> postorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.postorder (iter);
    
    return iter;
    
  }
  
  public int height() {
    if (root == null)
      return 0;
    else
      return root.height();

  }
  
  public void spin() {
    if (root != null)
      root.spin();

  }
  
  public BTNode<T> getRoot() {
    return root;
  }
  
  public static void main (String [] args) {
    String e1 = "Is the animal big?";
    String e2 = "Does it live in a cage?";
    String e3 = "Does it eat grass?";
    String e4 = "Does it meow?";
    String e5 = "Does it chirp?";
    String e6 = "Does it have a long neck?";
    //String e6 = "Does it like water?";
    String e7 = "Does it moo?";
    String e8 = "It's a dog!";
    String e9 = "It's a cat!";
    String e10 = "It's a hamster!";
    String e11 = "It's a bird!";
    String e12 = "It's a bear!";
    String e13 = "It's a giraffe!";
    String e14 = "It's a horse!";
    String e15 = "It's a cow!";
    
    //create the LinkedBinarTree
    LinkedBinaryTree<String> t1, t2, t3, t4, t5, t6, t7;
    t1 = new LinkedBinaryTree<String> (e4, new LinkedBinaryTree<String>(e8), new LinkedBinaryTree<String>(e9));
    t2 = new LinkedBinaryTree<String> (e5, new LinkedBinaryTree<String>(e10), new LinkedBinaryTree<String>(e11));
    t3 = new LinkedBinaryTree<String> (e6, new LinkedBinaryTree<String>(e12), new LinkedBinaryTree<String>(e13));
    t4 = new LinkedBinaryTree<String> (e7, new LinkedBinaryTree<String>(e14), new LinkedBinaryTree<String>(e15));
    t5 = new LinkedBinaryTree<String> (e2, t1, t2);
    t6 = new LinkedBinaryTree<String> (e3, t3, t4);
    t7 = new LinkedBinaryTree<String> (e1, t5, t6);
    
    System.out.println(t7);
    t7.spin();
    System.out.println(t7);
    
  }
}

