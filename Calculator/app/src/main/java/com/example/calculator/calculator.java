package com.example.calculator;

import android.widget.Button;
import android.widget.TextView;

/**
 * A class representing a calculator object that can use different operations like:
 * Addition using add(sum1, sum2), multiplication using multiply(sum1, sum2), subtraction using subtract(sum1, sum2) and division using divide(sum1, sum2).
 * @author Aditya Kandel
 * @author Omar Abotahoon
 * @author Omar Aly
 * @author Raj Badial
 * @author Ak Au
 * @version 1.1
 */
public class calculator {
     String add = "add";            //A String representing the addition function
     String divide = "divide";      //A String representing the division function
     String multiply = "multiply";  //A String representing the multiplication function
     String subtract = "subtract";  //A String representing the subtraction function
     String operation = "";         //A String representing the operation chosen
     String sum1 ="";               //A String representing the first number
     String sum2 = "";              //A String representing the second number
    boolean setSum1Deafult= false;  //A boolean value used to avoid 0/0 in the divide() method

    boolean isInState = false;      //A boolean value used to avoid 0/0 in the divide() method 

    public calculator(){

    }

    /**
     * Takes in 2 strings, converts them to doubles and computes their sum by adding them.
     * Add <code>sum1</code> and <code>sum2</code> and return the value
     * @param sum1 the first value
     * @param sum2 the second value
     * @return A String representing the sum of sum1 and sum2
     */
    public String add(String sum1, String sum2){
        double result;
        result = (Double.parseDouble(sum1)+Double.parseDouble(sum2)); // convert result to decimal
        if(result % 1 == 0) { // if result has no decimal place convert it int
            return Long.toString((long) result);
        }
        sum2="";
        return Double.toString(result);
    }



    /**
     * Takes in 2 strings, converts them to doubles and computes their product by multiplying them.
     * Multiply <code>sum1</code> and <code>sum2</code> and return the value
     * @param sum1 the first value
     * @param sum2 the second value
     * @return A String representing the product of sum1 and sum2
     */
    public String multiply(String sum1, String sum2){
        double result;
        result = (Double.parseDouble(sum1)*Double.parseDouble(sum2));
        if(result % 1 == 0){
            return Long.toString((long) result);
        }
        sum2="";
        return Double.toString(result);
    }




    /**
     * Takes in 2 strings, converts them to doubles and computes their difference by subtracting sum2 from sum1.
     * Subtract <code>sum1</code> and <code>sum2</code> and return the value
     * @param sum1 the first value
     * @param sum2 the second value
     * @return A String representing the difference of sum1 and sum2
     */
    public String subtract(String sum1, String sum2){
        double result;
        System.out.println(sum1+operation + sum2 + "from calculate method");
        result = (Double.parseDouble(sum1)-Double.parseDouble(sum2));

        System.out.println("result form the calcualte method"+ result);
        if(result % 1 == 0){
            return Long.toString((long) result);
        }
        sum2="";
        return Double.toString(result);
    }




    /**
     * Takes in 2 strings, converts them to doubles and computes their quotient by dividing them.
     * Divide <code>sum1</code> and <code>sum2</code> and return the value
     * @param sum1 the first value
     * @param sum2 the second value
     * @return A String representing the quotient of sum1 and sum2
     */
    public String divide(String sum1, String sum2){
        double result;
        if(Double.isNaN((Double.parseDouble(sum1)/Double.parseDouble(sum2)))){ //avoid 0/0
            operation="";
            isInState =true;
            setSum1Deafult=true;
            return "Error";

        }
        result = (Double.parseDouble(sum1)/Double.parseDouble(sum2));
        if(result % 1 == 0){
            return Long.toString((long) result);                }
        sum2="";
        return Double.toString(result);
    }


    

    /**
     * Used to call the operand methods that perform computations of sum1 and sum2.
     * @param sum1 the first value entered by user
     * @param operation the operation to perform
     * @param sum2 the second value entered by user
     * @return A String representing the result of the operation performed on sum1 and sum2
     */
    public String calculate(String sum1, String operation,String sum2) {
        switch (operation){
            case "add":
                return  add(sum1, sum2);
            case "subtract":
               return  subtract(sum1, sum2);

            case "divide":
                return  divide(sum1, sum2);
            case "multiply":
                return  multiply(sum1, sum2);

            default:
        }
        return sum1;
    }

}
