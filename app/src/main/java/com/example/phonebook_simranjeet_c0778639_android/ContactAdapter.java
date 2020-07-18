package com.example.phonebook_simranjeet_c0778639_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactAdapter extends ArrayAdapter {

    Context  mContext;
    int layoutRes;
    DataBase mDatabase;
    List<Contact> contactList;
    ArrayList<Contact> arraylist;


    public ContactAdapter(@NonNull Context mContext, int layoutRes, List<Contact> personList, DataBase mDatabase) {
        super(mContext, layoutRes,personList);
        this.mContext = mContext;
        this.layoutRes = layoutRes;
        this.contactList = personList;
        this.arraylist = new ArrayList<Contact>();
        this.arraylist.addAll(personList);
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(layoutRes,null);
        TextView tvname = v.findViewById(R.id.tvFirstName);
        TextView tvlstname = v.findViewById(R.id.tvLastName);
        TextView tvemail = v.findViewById(R.id.tvEmail);
        TextView tvno = v.findViewById(R.id.tvPhoneNumber);
        TextView tvadd = v.findViewById(R.id.tvAddress);


        final Contact contact = contactList.get(position);
        tvname.setText(contact.getFname().toUpperCase());
        tvlstname.setText(contact.getLname());
        tvemail.setText(contact.getEmail());
        tvno.setText(contact.getPhone());
        tvadd.setText(contact.getAddress());

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact(contact);
            }
        });

        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(contact);
            }
        });

        return  v;

    }

    private void deleteContact(final Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  String sql = "DELETE FROM employees WHERE id=?";
                // mDatabase.execSQL(sql,new Integer[]{employee.getId()});


                if( mDatabase.deletePerson(contact.getId())){
                    loadData();
                }


            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private void updateContact(final Contact contact) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Update Contact");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View customLayout = inflater.inflate(R.layout.update_contact, null);
        builder.setView(customLayout);

        final EditText updateFName = customLayout.findViewById(R.id.updateFirstName);
        final EditText updateLName = customLayout.findViewById(R.id.updateLastName);
        final EditText updateEmail = customLayout.findViewById(R.id.updateEmail);
        final EditText updatePhone = customLayout.findViewById(R.id.updatePhoneNumber);
        final EditText updateAddress = customLayout.findViewById(R.id.updateAddress);


        updateFName.setText(contact.getFname());
        updateLName.setText(contact.getLname());
        updateEmail.setText(contact.getEmail());
        updatePhone.setText(contact.getPhone());
        updateAddress.setText(contact.getAddress());


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        customLayout.findViewById(R.id.btn_update_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = updateFName.getText().toString().trim();
                String lname = updateLName.getText().toString().trim();
                String email = updateEmail.getText().toString().trim();
                String phone = updatePhone.getText().toString().trim();
                String address = updateAddress.getText().toString().trim();


                if (fname.isEmpty()){
                    updateFName.setError("Name is Mandadory");
                    updateFName.requestFocus();
                    return;

                }
                if (lname.isEmpty()){
                    updateLName.setError("Last Name is Mandadory");
                    updateLName.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    updateEmail.setError("Last Name is Mandadory");
                    updateEmail.requestFocus();
                    return;
                }


                if (phone.isEmpty()){
                    updatePhone.setError("Phone Number is Mandadory");
                    updatePhone.requestFocus();
                    return;
                }
                if (address.isEmpty()){
                    updateAddress.setError("Address is Mandadory");
                    updateAddress.requestFocus();
                    return;
                }

                if (mDatabase.updateContactData(contact.getId(),fname,lname,email,phone,address)){
                    Toast.makeText(mContext, "PERSON DATA UPDATED", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(mContext, "PERSON DATA IS NOT UPDATED", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            }
        });



    }



    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        contactList.clear();
        if (charText.length() == 0) {
            contactList.addAll(arraylist);
        } else {
            for (Contact contact : arraylist) {
                if (contact.getPhone().toLowerCase(Locale.getDefault())
                        .contains(charText) || contact.getFname().toLowerCase(Locale.getDefault())
                        .contains(charText) || contact.getLname().toLowerCase(Locale.getDefault())
                        .contains(charText) || contact.getAddress().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    contactList.add(contact);
                }
            }
        }
        notifyDataSetChanged();

    }
    private void loadData() {

        String sql = "SELECT * FROM employees";
        // Cursor c = mDatabase.rawQuery(sql,null);
        Cursor c = mDatabase.getAllData();
        contactList.clear();
        if (c.moveToFirst()){


            do {
                contactList.add(new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));

            }while (c.moveToNext());
            c.close();

        }
        notifyDataSetChanged();
    }


}
