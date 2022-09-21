package com.example.callmate.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.callmate.data.entities.ContactsModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactsModelDao {
    @Insert
    void insert(ContactsModel contactsModel);

    @Update
    void updateRemark(ContactsModel contactsModel);

    @Query("SELECT * FROM contacts_table WHERE subscriberId is :subscriberId")
    ContactsModel getContactsModelBySubscriberId(int subscriberId);

    @Query("SELECT * FROM contacts_table WHERE fileId is :fileId")
    LiveData<List<ContactsModel>> getLiveDataContactsFromFileId(int fileId);

    @Query("SELECT * FROM contacts_table WHERE fileId LIKE :fileId")
    List<ContactsModel> getContactsFromFileId(int fileId);

    @Query("SELECT * FROM contacts_table")
    List<ContactsModel> getAllContacts();
}
