package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * A class representing the calculator's visual interface on Android Studio.
 * Represents all the visual components including the buttons, screen, and layout.
 * Class MainActivity is a subclass that inherits the superclass AppCompatActivity
 * @author Aditya Kandel
 * @author Omar Abotahoon
 * @author Omar Aly
 * @author Raj Badial
 * @author Ak Au
 * @version 1.1
 */ 
public class MainActivity extends AppCompatActivity {
    calculator cal = new calculator();      //Represents a calculator object
    private TextView textField;             //Displays text on the calculator screen
    private Button selectedBtn;             //Represents the button that was last clicked
    // check if the operations and equal btn was pressed


    /**
     * Where the activity is initialized and setContentView() is called.
     * @param savedInstanceState is used to save the state of the activity when the activity is terminated
     */ 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textField = (TextView) findViewById(R.id.textView3);
    }



    /**
     * Represents the equal button on a calculator.
     * Whenever an operand is processed on two numbers, pressing the equals button (calling equals()) displys the result
     */
    public void equals(){
       // System.out.println(sum1+operation + sum2);
        if(!(cal.sum1.equals("") ||cal.sum2.equals(""))){cal.sum1 = cal.calculate(cal.sum1, cal.operation, cal.sum2);}
        System.out.println("result"+cal.sum1);
        textField.setText(cal.sum1);

        System.out.println("equalbtn_pressed");
    }


    /**
     * Represents the 4 rectangular boxes for the operation buttons that respond to user input.
     * Connects the xml design component to the Java logic
     * @param view A rectangular box that responds to the user clicking a button
     */
    public void btnOperations(View view){
        int btnpressed = view.getId();
        if(selectedBtn!=null){
        selectedBtn.setSelected(false);
        }
        selectedBtn  = (Button) findViewById(view.getId());
        selectedBtn.setSelected(true);
        if(cal.sum1.equals("")){
            cal.sum1 = textField.getText().toString();
            System.out.println("getting sum1" +cal.sum1);
        }else if(!cal.isInState){
            cal.sum2 = textField.getText().toString();
            System.out.println("getting sum1" + cal.sum2);
            equals();
        }else if(btnpressed == R.id.btnEqual) {equals(); cal.setSum1Deafult=true;}
        System.out.println("button pressed:" +((Button) findViewById(view.getId())).getText());

        cal.isInState = true;
            switch (btnpressed) {
                case R.id.btnAdd:
                    cal.operation = cal.add;
                   // System.out.println("operation set to :" + operation);
                    break;
                case R.id.btnSubtract:
                    cal.operation = cal.subtract;
                   // System.out.println("operation set to :" + operation);
                    break;
                case R.id.btnDivide:
                    cal.operation = cal.divide;
                   // System.out.println("operation set to :" + operation);
                    break;
                case R.id.btnMultiply:
                    cal.operation = cal.multiply;
                   // System.out.println("operation set to :" + operation);
                    break;

                case R.id.btnClear:
                    cal.sum1 = "";
                    cal.sum2 = "";
                    cal.operation = "";
                    cal.isInState = false;
                    System.out.println("clear");
                    textField.setText("0");
                    selectedBtn.setSelected(false);
                    break;
            }
    }


    /**
     * Represents all the rectangular boxes for the number buttons that respond to user input.
     * Connects the xml design component to the Java logic
     * @param view A rectangular box that responds to the user clicking a button
     */
    public void btnNumbers(View view) {
        Button btn= (Button) view;
        System.out.println("curent number on screen " + textField.getText().toString());
        if(cal.isInState){
            System.out.println("this should play");
            textField.setText("0");
            cal.isInState = false;
            selectedBtn.setSelected(false);
            if(selectedBtn.getId() == R.id.btnEqual|| cal.setSum1Deafult){
                cal.sum1="";
                cal.setSum1Deafult = false;
            }
        }
        if(view.getId()== R.id.btnDecimal){
            if(cal.isInState){
                textField.setText("0.");
                cal.isInState = false;
            }else if(!textField.getText().toString().contains(".")){
                System.out.println("decimal"+textField.getText().toString());
                textField.setText(textField.getText().toString()+".");
            }
        }else {
            if (textField.getText().toString().equals("0")) {
                textField.setText(btn.getText().toString());
            } else {
                textField.setText(textField.getText() + btn.getText().toString());
            }
        }
    }

}