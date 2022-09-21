package com.example.callmate.utils;

import java.util.ArrayList;

public class ContactList {
    private ArrayList<Contact> contacts = new ArrayList<>();

    public void  addNewContact(Contact contact){
        contacts.add(contact);
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}
