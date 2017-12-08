import java.util.*;
import java.math.BigInteger;

/**
 * This program is able to convert an infix expression into a 
 * postfix expression. It is also able to evaluate postfix expressions
 * and output the result.
 *
 * @author Miguel De Los Santos, 1129, delossantos6@csus.edu
 * @version 4 December 2016
 */
public class Evaluator {

   /**
   * Takes an infix and converts it into postfix
   *
   * @throws IllegalArgumentException if an element contains incorrect characters
   *                                 or if the expression is in not the right order
   * @return the postfix expression
   * @param in is the infix expression that is to be processed
   */
   public static List<String> infixToPostfix(List<String> in) {
      try {
         if(!checkElement(in) || !checkOrder(in)) {
            throw new IllegalArgumentException();
         }
         Iterator<String> itr2 = in.iterator();
         int left = 0;
         int right = 0;
         while(itr2.hasNext()) {
            String str = itr2.next();
            if(str.equals("(")) {
               left++;
            }
            if(str.equals(")")) {
               right++;
            }
         }
         if(left!=right) {
            throw new IllegalArgumentException();
         }
         List<String> post = new ArrayList();
         Stack<String> s = new Stack();
         Iterator<String> itr = in.iterator();
         while(itr.hasNext()) {
            String str = itr.next();
            if((str.equals("(")) ||
            ((str.equals("^") || str.equals("*") || str.equals("/") || str.equals("%") || str.equals("+") || str.equals("-")) && (s.empty() || s.peek().equals("("))) ||
            (str.equals("^") && (s.peek().equals("*") || s.peek().equals("/") || s.peek().equals("+") || s.peek().equals("%") || s.peek().equals("-") || s.peek().equals("^"))) ||
            ((str.equals("*") || str.equals("/") || str.equals("%")) && ((s.peek().equals("+")) || s.peek().equals("-")))) {
               s.push(str);
            }
            else if(str.equals(")")) {
               while(!s.peek().equals("(")) {
                  post.add(s.pop());
               }
               s.pop();
            }
            else if((str.equals("+") || str.equals("-")) || ((str.equals("*") || str.equals("/") || str.equals("%")) && (s.peek().equals("*") || s.peek().equals("/") || s.peek().equals("%") || s.peek().equals("^")))) {
               if(str.equals("+") || str.equals("-")) {
                  while(!s.empty()) {
                     post.add(s.pop());
                  }
               }
               else{
                  while(!s.empty() && (s.peek().equals("*") || s.peek().equals("/") || s.peek().equals("%") || s.peek().equals("^"))) {
                     post.add(s.pop());
                  }
               }
               s.push(str);
            }
            else {
               post.add(str);
            }
         }
         while(!s.empty()) {
            post.add(s.pop());
         }
         return post;
      }
      catch (Exception e) {
         throw new IllegalArgumentException();
      }
   }
   
   /**
   * Evaluates postfix expressions and outputs the result
   *
   * @throws IllegalArgumentException if an element contains incorrect characters
   *                                 or if the expression contains extra operans
   * @return the end result of the expression
   * @param in is the postfix expression that is to be processed
   */
   public static BigInteger evalPostfix(List<String> in) {
      try {
         int num = 0;
         int op = 0;
         Iterator<String> itr2 = in.iterator();
         while(itr2.hasNext()) {
            String str = itr2.next();
            if(str.equals("^") || str.equals("*") || str.equals("/") || str.equals("%") || str.equals("+") || str.equals("-")) {
               op++;
            }
            else {
               num++;
            }
         }
         if(num>op+1) {
            throw new IllegalArgumentException();
         }
         Iterator<String> itr = in.iterator();
         Stack<BigInteger> s = new Stack();
         while(itr.hasNext()) {
            String str = itr.next();
            if(str.equals("^") || str.equals("*") || str.equals("/") || str.equals("%") || str.equals("+") || str.equals("-")) {
               BigInteger i;
               BigInteger i2;
               if(str.equals("^")) {
                  i=s.pop();
                  i2=s.pop();
                  s.push(i2.pow(i.intValueExact()));
               }
               else if(str.equals("*")) {
                  s.push(s.pop().multiply(s.pop()));
               }
               else if(str.equals("/")) {
                  i=s.pop();
                  i2=s.pop();
                  s.push(i2.divide(i));
               }
               else if(str.equals("%")) {
                  i=s.pop();
                  i2=s.pop();
                  s.push(i2.remainder(i));
               }
               else if(str.equals("+")) {
                  s.push(s.pop().add(s.pop()));
               }
               else {
                  i=s.pop();
                  i2=s.pop();
                  s.push(i2.subtract(i));
               }
            }
            else {
               s.push(new BigInteger(str));
            }
         }
         return s.pop();
      }
      catch (Exception e) {
         throw new IllegalArgumentException();
      }
   }
   
   private static boolean checkElement(List<String> in) {
      boolean pass = true;
      Iterator<String> itr = in.iterator();
      while(pass && itr.hasNext()) {
         String str = itr.next();
         if(str.equals("^") || str.equals("*") || str.equals("/") || str.equals("%") || str.equals("+") || str.equals("-") || str.equals("(") || str.equals(")")) {
            pass = true;
         }
         else {
            try {
               BigInteger num = new BigInteger(str);
            }
            catch (Exception e) {
               pass = false;
            }
         }
      }
      return pass;
   }
   
   private static boolean checkOrder(List<String> in) {
      boolean pass = true;
      Iterator<String> itr = in.iterator();
      Stack<String> s = new Stack();
      if(in.get(0).equals("*") || in.get(0).equals(")") || in.get(0).equals("/") || in.get(0).equals("%") || in.get(0).equals("^") || in.get(0).equals("+") || in.get(0).equals("-") ||
      in.get(in.size()-1).equals("*") || in.get(in.size()-1).equals("(") || in.get(in.size()-1).equals("/") || in.get(in.size()-1).equals("%") || in.get(in.size()-1).equals("^") || in.get(in.size()-1).equals("+") || in.get(in.size()-1).equals("-")) {
         pass = false;
      }
      s.push(itr.next());
      while(pass && itr.hasNext()) {
         String str = itr.next();
         if(((str.equals("^") || str.equals("*") || str.equals("/") || str.equals("%") || str.equals("+") || str.equals("-")) && 
         (s.peek().equals("*") || s.peek().equals("/") || s.peek().equals("+") || s.peek().equals("%") || s.peek().equals("-") || s.peek().equals("^") || s.peek().equals("("))) ||
         (str.equals(")") && (s.peek().equals("*") || s.peek().equals("/") || s.peek().equals("+") || s.peek().equals("%") || s.peek().equals("-") || s.peek().equals("^"))) || 
         (s.peek().equals(")") && str.equals("(")) || s.peek().equals("(") && str.equals(")")) {
            pass = false;
         }
         else {
            try {
               BigInteger test = new BigInteger(s.peek());
               BigInteger test2 = new BigInteger(str);
               pass = false;
            }
            catch (Exception e) {
            }
            if(s.peek().equals(")")) {
               try {
                  BigInteger test = new BigInteger(str);
                  pass = false;
               }
               catch (Exception e) {
               }
            }
            if(str.equals("(")) {
               try {
                  BigInteger test = new BigInteger(s.peek());
                  pass = false;
               }
               catch (Exception e) {
               }
            }
         }
         s.push(str);
      }
      return pass;
   }
}