package com.example.contactapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    List<PersonInfo> list_of_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_of_person = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 1; i < 20; i++){
            list_of_person.add(new PersonInfo(
                    i, faker.name().fullName(), faker.phoneNumber().phoneNumber(), faker.internet().emailAddress()
            ));
        }

        ContactAdapter adapter = new ContactAdapter(this,list_of_person);
        ListView listView = findViewById(R.id.list_items);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "CALL");
        menu.add(0, v.getId(), 0, "SMS");
        menu.add(0, v.getId(), 0, "EMAIL");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        String menuItemIndex = (String) item.getTitle();
        if (menuItemIndex.equals("CALL")){
            String phoneNumber = "tel:" + list_of_person.get((int) info.id).getPhoneNumber();
            Intent callAction = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
            startActivity(callAction);
        }else if(menuItemIndex.equals("SMS")){
            String phoneNumber = "sms:" + list_of_person.get((int) info.id).getPhoneNumber();
            Intent textAction = new Intent(Intent.ACTION_SENDTO, Uri.parse(phoneNumber));
            startActivity(textAction);
        }else {
            String email = list_of_person.get((int) info.id).getEmail();
            Intent emailAction = new Intent(Intent.ACTION_SENDTO);
            emailAction.setData(Uri.parse("mailto:"));
            emailAction.putExtra(Intent.EXTRA_EMAIL, email);
            startActivity(Intent.createChooser(emailAction, "Chooser Title"));
        }
        return true;
    }
}