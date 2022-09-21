package com.example.callmate.ui.contacts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.callmate.utils.Contact;
import com.example.callmate.data.entities.ContactsModel;
import com.example.callmate.data.repositories.ContactsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContactsViewModel extends AndroidViewModel {
    private ContactsRepository repository;
    private ArrayList<Contact> listOfContacts = new ArrayList<>();
    private ContactsModel currentContact;

    private static boolean isCallEnded = false;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactsRepository(application);
    }

    public void insert(ContactsModel contactsModel){
            repository.insert(contactsModel);
    }

    public void updateRemarks(String remark, ContactsModel contactsModel){
        contactsModel.setCallingRemark(remark);
        repository.update(contactsModel);
    }

    public LiveData<List<ContactsModel>> getLiveDataContactsFromFileId(int fileId){
        return repository.getLiveDataContactsFromFileId(fileId);
    }

    public List<ContactsModel> getContactsFromFileId(int fileId) throws ExecutionException, InterruptedException {
        return repository.getContactsFromFileId(fileId);
    }

    public List<ContactsModel> getAllContacts() throws ExecutionException, InterruptedException {
        return repository.getAllContacts();
    }

    public static void setIsCallEnded(boolean b){
        isCallEnded = b;
    }

    public void setisCallEnded(boolean b){isCallEnded = b;};

    public Boolean getIsCallEnded(){
        return isCallEnded;
    }

    public void setCurrentContact(ContactsModel contactsModel){
        this.currentContact = contactsModel;
    }

    public ContactsModel getCurrentContact(){
        return this.currentContact;
    }
}
