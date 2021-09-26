package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClear(View view){
        EditText textField = (EditText) findViewById(R.id.textView3);

        textField.setText("0");
    }
    public void btnDecimal(View view){
        EditText textField = (EditText) findViewById(R.id.textView3);
            if(!textField.getText().toString().contains(".")) {
                textField.setText(textField.getText() + ".");
            }
    }
    public void btnEqual(View view){
        EditText textField = (EditText) findViewById(R.id.textView3);

    }
    public void btnAdd(View view){
        EditText textField = (EditText) findViewById(R.id.textView3);
        view.setSelected(true);

    }
    public void btnSubtract(View view){
        EditText textField = (EditText) findViewById(R.id.textView3);

    }
    public void btnDivide(View view){
        EditText textField = (EditText) findViewById(R.id.textView3);

    }
    public void btnMultiply(View view){
        EditText textField = (EditText) findViewById(R.id.textView3);

    }


    @Override
    public void onClick(View view) {
        EditText textField = (EditText) findViewById(R.id.textView3);
    }

    public void btnNumbers(View view){
        EditText textField = (EditText) findViewById(R.id.textView3);
        Button btn = (Button) view;
        textField.setText(textField.getText()+ btn.getText().toString());
    }
}