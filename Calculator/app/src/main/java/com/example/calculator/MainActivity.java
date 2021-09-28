package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textField;
    String add = "add";
    String divide = "divide";
    String multiply = "multiply";
    String subtract = "subtract";
    String operation = "";
    String sum1 ="";
    String sum2 = "";
    Button selectedBtn;
    String previousState="";
    boolean isInState = false; // check if the operations and equal btn was pressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textField = (TextView) findViewById(R.id.textView3);

    }
    public void equals(){
        System.out.println(sum1+operation + sum2);
        if(!(sum1.equals("") ||sum2.equals(""))){
        sum1 = calculate(sum1, operation, sum2);}
        System.out.println("result"+sum1);
        textField.setText(sum1);
        System.out.println("equalbtn_pressed");
    }

    public void btnOperations(View view){
         int btnpressed = view.getId();
        if(selectedBtn!=null){
        selectedBtn.setSelected(false);
        }
        selectedBtn  = (Button) findViewById(view.getId());
        selectedBtn.setSelected(true);
        if(sum1.equals("")){
            sum1 = textField.getText().toString();
            System.out.println("getting sum1" + sum1);
        }else if(!isInState){
            sum2 = textField.getText().toString();
            System.out.println("getting sum1" + sum2);
            equals();
        }else if(btnpressed == R.id.btnEqual) {equals();}
        System.out.println("button pressed:" +((Button) findViewById(view.getId())).getText());

            isInState = true;
            switch (btnpressed) {
                case R.id.btnAdd:
                    operation = add;
                    System.out.println("operation set to :" + operation);
                    break;
                case R.id.btnSubtract:
                    operation = subtract;
                    System.out.println("operation set to :" + operation);
                    break;
                case R.id.btnDivide:
                    operation = divide;
                    System.out.println("operation set to :" + operation);
                    break;
                case R.id.btnMultiply:
                    operation = multiply;
                    System.out.println("operation set to :" + operation);
                    break;

                case R.id.btnClear:
                    sum1 = "";
                    sum2 = "";
                    operation = "";
                    isInState = false;
                    System.out.println("clear");
                    textField.setText("0");
                    selectedBtn.setSelected(false);
                    break;
            }
    }

    // do the calculations
    public String calculate(String sum1, String operation,String sum2) {
        double result;
        switch (operation){
            case "add":
                result = (Double.parseDouble(sum1)+Double.parseDouble(sum2)); // convert result to decimal
                if(result % 1 == 0) { // if result has no decimal place convert it int
                    return Integer.toString((int) result);
                }
                sum2="";
                return Double.toString(result);
            case "subtract":
                System.out.println(sum1+operation + sum2 + "from calculate method");
                result = (Double.parseDouble(sum1)-Double.parseDouble(sum2));

                System.out.println("result form the calcualte method"+ result);
                if(result % 1 == 0){
                    return Integer.toString((int)result);
                }
                sum2="";
                return Double.toString(result);

            case "divide":
                if(Double.isNaN((Double.parseDouble(sum1)/Double.parseDouble(sum2)))){ //avoid 0/0
                    operation="";
                    isInState =true;
                    return "Error";
                }
                result = (Double.parseDouble(sum1)/Double.parseDouble(sum2));
                if(result % 1 == 0){
                    return Integer.toString((int)result);
                }
                sum2="";
                return Double.toString(result);

            case "multiply":
                result = (Double.parseDouble(sum1)*Double.parseDouble(sum2));
                if(result % 1 == 0){
                    return Integer.toString((int)result);
                }
                sum2="";
                return Double.toString(result);

            default:
        }
        return sum1;
    }

    public void btnNumbers(View view) {
        Button btn= (Button) view;
        System.out.println("curent number on screen " + textField.getText().toString());
        if(isInState){
            System.out.println("this should play");
            textField.setText("0");
            isInState = false;
            selectedBtn.setSelected(false);
            if(selectedBtn.getId() == R.id.btnEqual){
                sum1="";
            }
        }
        if(view.getId()== R.id.btnDecimal){
            if(isInState){
                textField.setText("0.");
                isInState = false;
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