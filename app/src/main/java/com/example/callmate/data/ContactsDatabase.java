package com.example.callmate.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.callmate.data.converters.Converters;
import com.example.callmate.data.dao.ContactsModelDao;
import com.example.callmate.data.dao.FileModelDao;
import com.example.callmate.data.entities.ContactsModel;
import com.example.callmate.data.entities.FileModel;

@Database(entities = {ContactsModel.class, FileModel.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class ContactsDatabase extends RoomDatabase {
    public static volatile ContactsDatabase instance;
    public abstract ContactsModelDao contactsModelDao();
    public abstract FileModelDao fileModelDao();

    public static ContactsDatabase getInstance(Context context){
        if(instance == null){
            synchronized (ContactsDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ContactsDatabase.class,
                            "contactsDb.db"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
