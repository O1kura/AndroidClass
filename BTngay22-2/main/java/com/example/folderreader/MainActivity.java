package com.example.folderreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String FILE_SETTINGS = "my_settings";
    static final String STRING_DATA = "STRING_DATA";
    static final String INT_DATA = "INT_DATA";
    ListView  listView;
    List<FileInfo> list;
    File currentPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        listView = findViewById(R.id.listview);

        try {
            currentPath = Environment.getExternalStorageDirectory();
            initializeItems();
            registerForContextMenu(listView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File fileSelectedPath = list.get(position).getPath();

                    if(!list.get(position).isFile()){
                        currentPath = fileSelectedPath;
                        initializeItems();
                    }else{
                        try {
                            InputStream is = new FileInputStream(fileSelectedPath);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            StringBuilder builder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null)
                                builder.append(line).append("\n");
                            Log.v("Tag",builder.toString());
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public void initializeItems() {

        String[] files = currentPath.list();
        if (files != null){
            list.clear();

            for(String path:files){
                File file = new File(path);
                FileInfo fileInfo = new FileInfo(file.isFile(),file);
                list.add(fileInfo);
            }
            Adapter adapter = new Adapter(this, list);
            listView.setAdapter(adapter);
        }else{
            Log.v("Tag","Empty Folder");
        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Rename");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String menuItemIndex = (String) item.getTitle();
        String type = list.get(info.position).isFile() ? "File" : "Directory";
        if (menuItemIndex.equals("Delete")){
            showAlertDialog(this, list.get(info.position).getPath(), type);
        }else if(menuItemIndex.equals("Rename")){
            Log.v("Tag","Rename");
            return true;
        }
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.createfile){
            Log.v("Tag","create file");
            return true;
        }else if(item.getItemId()==R.id.createfolder){
            Log.v("Tag","create folder");
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            Log.v("TAG","PERMISSION_GRANTED");
        else
            Log.v("TAG","PERMISSION_DENIED");
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(STRING_DATA, "Hello");
        editor.putInt(INT_DATA, 1234);
        editor.apply();
    }
    private void showAlertDialog(MainActivity mainActivity, File path, String type) {
        new AlertDialog.Builder(mainActivity)
                .setTitle("Delete " + type.toLowerCase())
                .setMessage("Are you sure that you want to delete " + type.toLowerCase() + "?")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) { }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            if(type.equals("File")){
                                if(path.delete()){
                                    initializeItems();
                                    Toast.makeText(getApplicationContext(), "File has been deleted", Toast.LENGTH_LONG).show();
                                }

                            }else if(type.equals("Directory")){
                                if(deleteDirectory(path)){
                                    initializeItems();
                                    Toast.makeText(getApplicationContext(), "Directory has been deleted", Toast.LENGTH_LONG).show();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                })
                .create()
                .show();
    }// showMyAlertDialog

    public boolean deleteDirectory(File path) {
        if(path.exists()) {
            File[] files = path.listFiles();

            if (files == null) {
                return true;
            }

            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    if(!file.delete()) return false;
                }
            }
        }
        return path.delete();
    }
}