package com.example.phonebook_simranjeet_c0778639_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowContact extends AppCompatActivity {

    DataBase mDatabase;
    EditText serchText;
    List<Contact> ContactList;

    ListView listView;
    ContactAdapter ContactAdpter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);

        mDatabase = new DataBase(this);
        listView = findViewById(R.id.lvUser);
        serchText = findViewById(R.id.searchView);
        ContactList = new ArrayList<>();
        loadData();

        ContactAdpter = new ContactAdapter(this,R.layout.cell_contact,ContactList,mDatabase);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(ContactAdpter);

        serchText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = serchText.getText().toString();
                (ContactAdpter).filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }



        });
}

    private void loadData() {


        Cursor cursor = mDatabase.getAllData();
        if (cursor.moveToFirst()){

            do {
                ContactList.add(new Contact(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                        ));


            }while (cursor.moveToNext());

            cursor.close();
        }

    }
}
