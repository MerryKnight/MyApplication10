package com.example.myapplication10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact> contacts;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView phoneTextView;
        private final TextView surnameTextView;
        private final TextView sxTextView;
        private final TextView ageTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.contact_name);
            surnameTextView = view.findViewById(R.id.contact_surname);
            phoneTextView = view.findViewById(R.id.contact_phone);
            ageTextView = view.findViewById(R.id.contact_age);
            sxTextView = view.findViewById(R.id.contact_sx);
        }

        public void bind(Contact contact) {
            if (contact != null) {
                nameTextView.setText(contact.getName());
                surnameTextView.setText(contact.getSurname());
                phoneTextView.setText(contact.getPhone());
                ageTextView.setText(contact.getAge());
                sxTextView.setText(contact.getSx());
            }
        }
    }

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }
}
