import java.util.*;
import java.math.BigInteger;

/**
 * This calculator is able to evaluate expressions and output
 * the result. It will only evaluate expressions if each operand
 * and operator are seperated by a blank space with a width equal
 * to a character.
 *
 * @author Miguel De Los Santos, 1129, delossantos6@csus.edu 
 * @version 16 December, 2016
 */
public class Calculator {
   
   public static void main(String[] args) {
      System.out.println("Welcome to BigNumCalculator. \nEnter an expression.");
      Scanner in = new Scanner(System.in);
      while(in.hasNextLine()) {
         String str = in.nextLine();
         try {
            System.out.println(evaluate(str));
         }
         catch (Exception e) {
            System.out.println("Error");
         }
         System.out.println("Enter an expression.");
      }
   }
   
   public static BigInteger evaluate(String expression) {
      List<String> list = Arrays.asList(expression.split(" "));
      return Evaluator.evalPostfix(Evaluator.infixToPostfix(list));
   }
}