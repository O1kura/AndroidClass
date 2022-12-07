package com.example.currencychange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.currencychange.R.id;

public class MainActivity extends AppCompatActivity {

    TextView fromText,toText;
    Double m;

    protected void updateResult(double money){
        money = money * 23925;
        toText.setText(String.valueOf(money));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromText = findViewById(R.id.editTextTextPersonName);
        toText = findViewById(R.id.editTextTextPersonName2);

        fromText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!fromText.getText().toString().equals("")){
                    updateResult(Double.parseDouble(charSequence.toString()));
                }else{
                    toText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromText.setText("");
                toText.setText("");
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fromText.getText().toString().equals("")){
                    m = Double.parseDouble(fromText.getText().toString());
                    updateResult(m);
                }
            }
        });
        findViewById(id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}