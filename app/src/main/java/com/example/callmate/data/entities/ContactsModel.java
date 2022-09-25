package com.example.callmate.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "contacts_table")
public class ContactsModel {
    @PrimaryKey(autoGenerate = true)
    public int contactId;
    int subscriberId;
    String dateCreated;
    String name;
    String mainPhoneNumber;
    ArrayList<String> alternatePhoneNumbers;
    String callingRemark;
    int fileId;

    public ContactsModel(int subscriberId, String dateCreated, String name, String mainPhoneNumber, ArrayList<String> alternatePhoneNumbers, int fileId) {
        this.subscriberId = subscriberId;
        this.dateCreated = dateCreated;
        this.name = name;
        this.mainPhoneNumber = mainPhoneNumber;
        this.alternatePhoneNumbers = alternatePhoneNumbers;
        this.fileId = fileId;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getName() {
        return name;
    }

    public String getMainPhoneNumber() {
        return mainPhoneNumber;
    }

    public ArrayList<String> getAlternatePhoneNumbers() {
        return alternatePhoneNumbers;
    }

    public String getCallingRemark() {
        return callingRemark;
    }

    public int getFileId() {
        return fileId;
    }

    public void setCallingRemark(String remark){
        this.callingRemark = remark;
    }
}
