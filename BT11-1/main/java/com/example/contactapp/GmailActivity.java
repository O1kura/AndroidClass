package com.example.contactapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GmailActivity extends AppCompatActivity {

    List<EmailModel> listOfEmails;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gmail_main_layout);

        listOfEmails = new ArrayList<>(Arrays.asList(EmailModel.samples()));
        EmailAdapter adapter = new EmailAdapter(listOfEmails);

        RecyclerView recyclerView = findViewById(R.id.emailList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


}
