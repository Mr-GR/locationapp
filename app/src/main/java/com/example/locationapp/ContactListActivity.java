package com.example.locationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ArrayList<Contact> contacts;
    private ContactAdapter contactAdapter;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            // RecyclerView recyclerView = findViewById(R.id.rvContacts);
            //adapter = (ContactAdapter) viewHolder.getAdapterPosition();

            //Contact clickedContact = adapter.getItem(position);

            //int contactId = clickedContact.getContactID();

            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            intent.putExtra("contactID", position);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initListButton();
        initListMapButton();
        initSettingsButton();
        // retrieveAndDisplayContacts();
        initAddContactButton();
        initDeleteSwitch();
    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");
        ContactDataSource ds = new ContactDataSource(this);

        try {
            ds.open();
            contacts = ds.getContacts(sortBy, sortOrder);
            ds.close();
            if (contacts.size() > 0) {
                RecyclerView contactList = findViewById(R.id.rvContacts);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                contactList.setLayoutManager(layoutManager);
                contactAdapter = new ContactAdapter(contacts, this);
                contactList.setAdapter(contactAdapter);
                contactAdapter.setOnItemClickListener(onItemClickListener);
            } else {
                Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.contactlisticon);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initListMapButton() {
        ImageButton ibList = findViewById(R.id.mapicon);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.settingsicon);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
//
//    private void retrieveAndDisplayContacts() {
//        String sortBy = getSharedPreferences("MyContactListPreferences",
//                Context.MODE_PRIVATE).getString("sortfield", "contactname");
//        String sortOrder = getSharedPreferences("MyContactListPreferences",
//                Context.MODE_PRIVATE).getString("sortorder","ASC");
//        ContactDataSource ds = new ContactDataSource(this);
//        ArrayList<Contact> contacts;
//
//        try {
//            ds.open();
//            contacts = ds.getContacts(sortBy, sortOrder);
//            ds.close();
//            RecyclerView contactList = findViewById(R.id.rvContacts);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//            contactList.setLayoutManager(layoutManager);
//            contactAdapter = new ContactAdapter(contacts, this);
//            contactList.setAdapter(contactAdapter);
//            contactAdapter.setOnItemClickListener(onItemClickListener);
//        } catch (Exception e) {
//            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
//        }
//    }

    private void initAddContactButton() {
        Button newContact = findViewById(R.id.buttonAddContact);
        newContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (contactAdapter != null) {
                boolean status = compoundButton.isChecked();
                contactAdapter.setDelete(status);
                contactAdapter.notifyDataSetChanged();
//                }

            }
        });
    }


}