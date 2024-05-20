package com.example.myapplication10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn = findViewById(R.id.button);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);

        EditText e1 = findViewById(R.id.editTextText);
        EditText e2 = findViewById(R.id.editTextText2);
        EditText e3 = findViewById(R.id.editTextText3);
        EditText e4 = findViewById(R.id.editTextText4);
        EditText e5 = findViewById(R.id.editTextText5);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Contact> contacts = dbHelper.getAllContacts();
        RecyclerView contactsList = findViewById(R.id.contacts_list);
        ContactAdapter adapter = new ContactAdapter(contacts);
        contactsList.setLayoutManager(new LinearLayoutManager(this));
        contactsList.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() { //save
            @Override
            public void onClick(View view) {
                String name = e1.getText().toString();
                String surname = e2.getText().toString();
                String phone = e3.getText().toString();
                String age = e4.getText().toString();
                String sx = e5.getText().toString();
                if (dbHelper.addContact(new Contact(0, name, surname,phone, age, sx)))
                {
                    contacts.add(new Contact(0, name, surname,phone, age, sx));
                    adapter.notifyItemInserted(contacts.size() - 1);
                    Toast.makeText(MainActivity2.this, "Contact saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Failed to save contact",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() { //upd
            @Override
            public void onClick(View view) {
                String oldPhone = e3.getText().toString();
                String newName = e1.getText().toString();
                String newSurname = e2.getText().toString();
                String newPhone = e3.getText().toString();
                String newAge = e4.getText().toString();
                String newSx = e5.getText().toString();
                if (dbHelper.updateContact(oldPhone, newName, newSurname, newPhone, newAge, newSx)) {
                    Toast.makeText(MainActivity2.this, "Contact updated successfully!", Toast.LENGTH_SHORT).show();
                    refreshContactsList(dbHelper, contacts, adapter,
                            contactsList);
                } else {
                    Toast.makeText(MainActivity2.this, "Failed to update contact",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() { //delete
            @Override
            public void onClick(View view) {
                String phone = e3.getText().toString();
                if (dbHelper.deleteContact(phone)) {
                    int position = -1;
                    for (int i = 0; i < contacts.size(); i++) {
                        if (contacts.get(i).getPhone().equals(phone))
                        {
                            position = i;
                            contacts.remove(i);
                            break;
                        }
                    }
                    if (position != -1) {
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(MainActivity2.this, "Contact deleted successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity2.this, "Contact not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity2.this, "Failed to delete contact", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() { //find
            @Override
            public void onClick(View view) {
                String phone = e3.getText().toString();
                Contact foundContact = dbHelper.findContact(phone);
                if (foundContact != null) {
                    Toast.makeText(MainActivity2.this, "Contact found: " +
                            foundContact.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Contact not found",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void refreshContactsList(DatabaseHelper dbHelper,
                                     List<Contact> contacts, ContactAdapter adapter, RecyclerView
                                             contactsList) {
        contacts = dbHelper.getAllContacts();
        adapter = new ContactAdapter(contacts);
        contactsList.setAdapter(adapter);
    }
}