package com.example.calculator;

import android.widget.Button;
import android.widget.TextView;

/**
 *
 */
public class calculator {
     String add = "add";
     String divide = "divide";
     String multiply = "multiply";
     String subtract = "subtract";
     String operation = "";
     String sum1 ="";
     String sum2 = "";
    boolean setSum1Deafult= false;

    boolean isInState = false;

    public calculator(){

    }

    /**
     * Add <code>sum1</code> and <code>sum2</code> and return the value
     * @param sum1 the first value
     * @param sum2 the second value
     * @return
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
     * Multiply <code>sum1</code> and <code>sum2</code> and return the value
     * @param sum1 the first value
     * @param sum2 the second value
     * @return
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
     * Subtract <code>sum1</code> and <code>sum2</code> and return the value
     * @param sum1 the first value
     * @param sum2 the second value
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
     * Divide <code>sum1</code> and <code>sum2</code> and return the value
     * @param sum1 the first value
     * @param sum2 the second value
     * @return
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
     *
     * @param sum1 the first value entered by user
     * @param operation the operation to perform
     * @param sum2 the second value entered by user
     * @return the result of the operation performed on sum1 and sum2
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
