package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textField;
    String add = "add";
    String divide = "divide";
    String multiply = "multiply";
    String subtract = "subtract";
    String state = "";
    String curValue ="";
    String sum2 = "";
    Button selectedBtn;
    boolean isInState = false; // check if the operations and equal btn was pressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textField = (TextView) findViewById(R.id.textView3);

    }

    //clear the Text and the curValue to empty
    public void btnClear(View view){
        textField.setText("0");
        curValue = "";
    }


    public void btnDecimal(View view){
            if(!textField.getText().toString().contains(".") && !isInState) { //add decimal if not there already and not in a state;
                textField.setText(textField.getText() + ".");
            }else if(isInState){
                textField.setText("0.");
                isInState = false;
            }
    }

    public void btnEqual(View view){
        if(!curValue.equals("")) {
            if (sum2.equals("")) {
                sum2 = textField.getText().toString();
            }
            curValue = calculate(state, curValue);
            textField.setText(curValue);
            isInState = true;
        }
    }

    public void btnOperations(View view){
        selectedBtn  = (Button) findViewById(view.getId());
        //selectedBtn.setBackgroundColor( Color.WHITE);
        //selectedBtn.setTextColor( Color.rgb(225,152,0));
        selectedBtn.setSelected(true);
        if(state.equals("") || !sum2.equals("")){
                curValue = textField.getText().toString();
                sum2 = "";
            switch (view.getId()) {
                case R.id.btnAdd:
                    state = add;
                    break;
                case R.id.btnSubtract:
                    state = subtract;

                    break;
                case R.id.btnDivide:
                    state = divide;
                    break;
                case R.id.btnMultiply:
                    state = multiply;
                    break;
                default:
            }
        }else {
            sum2  = textField.getText().toString();
            curValue = calculate(state, curValue);
            textField.setText(curValue);
        }
        isInState = true;
    }

    // do the calculations
    public String calculate(String state, String sum1) {
        double result=0.0;
        switch (state){

            case "add":

                result = (Double.parseDouble(sum1)+Double.parseDouble(sum2)); // convert result to decimal

                if(result % 1 == 0){ // if result has no decimal place convert it int
                    return Integer.toString((int)result);
                }
                return Double.toString(result);

            case "subtract":
                result = (Double.parseDouble(sum1)-Double.parseDouble(sum2));
                if(result % 1 == 0){
                    return Integer.toString((int)result);
                }
                return Double.toString(result);

            case "divide":
                if(Double.isNaN((Double.parseDouble(sum1)/Double.parseDouble(sum2)))){ //avoid 0/0
                    state="";
                    isInState =true;
                    return "Error";
                }
                result = (Double.parseDouble(sum1)/Double.parseDouble(sum2));
                if(result % 1 == 0){
                    return Integer.toString((int)result);
                }
                return Double.toString(result);

            case "multiply":
                result = (Double.parseDouble(sum1)*Double.parseDouble(sum2));
                if(result % 1 == 0){
                    return Integer.toString((int)result);
                }
                return Double.toString(result);

            default:
        }
        return "";
    }

    public void btnNumbers(View view){
        Button btn = (Button) view;

        if(isInState){ // clear the text to get New value
            textField.setText("");
          // selectedBtn.setBackgroundTintList(  Color.rgb(225,152,0));
            selectedBtn.setSelected(false);

           // selectedBtn.setTextColor(Color.WHITE);
            isInState = false;
        }
        if(textField.getText().toString().equals("0")){
            textField.setText(btn.getText().toString());
        }else{
            textField.setText(textField.getText()+ btn.getText().toString());
        }
    }
}