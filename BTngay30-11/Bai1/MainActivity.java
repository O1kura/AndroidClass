package com.example.bt1;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<TextView> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        Log.v("TAG", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView birthdayText = findViewById(R.id.textView7);
        TextView addText = findViewById(R.id.textView9);
        TextView fText = findViewById(R.id.textView3);
        TextView lText = findViewById(R.id.textView4);
        TextView eText = findViewById(R.id.textView11);

        list.add(birthdayText);
        list.add(addText);
        list.add(fText);
        list.add(lText);
        list.add(eText);

        CheckBox cbox = findViewById(R.id.checkBox);
        RadioGroup rbg = findViewById(R.id.radioGroup);
        RadioButton rb = findViewById(R.id.radioButton);
        RadioButton rb2 = findViewById(R.id.radioButton2);

        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb.setTextColor(Color.parseColor("black"));
                rb2.setTextColor(Color.parseColor("black"));
            }
        });

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                birthdayText.setText(i2+"/"+(i1+1)+"/"+i);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                dateSetListener, 2000, 1, 1);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }

        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = true;
                if(cbox.isChecked()){
                    String tmp;
                    for(int i = 0; i<list.size();i++){
                        tmp = list.get(i).getText().toString();
                        if(tmp.length()==0){
                            flag = false;
                            list.get(i).setHintTextColor(Color.parseColor("red"));
                        }
                    }

                    if(rbg.getCheckedRadioButtonId() == -1){
                        flag = false;
                        rb.setTextColor(Color.parseColor("red"));
                        rb2.setTextColor(Color.parseColor("red"));
                    }
                    if(flag){
                        Toast toast = Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(MainActivity.this, "Unfilled Criteria", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else{
                    Toast toast = Toast.makeText(MainActivity.this, "Accept Terms of Use", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }


}
