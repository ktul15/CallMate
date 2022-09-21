package com.example.callmate.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.callmate.data.ContactsDatabase;
import com.example.callmate.data.dao.ContactsModelDao;
import com.example.callmate.data.dao.FileModelDao;
import com.example.callmate.data.entities.ContactsModel;
import com.example.callmate.data.entities.FileModel;

import java.util.List;

public class FileRepository {
    private ContactsDatabase contactsDatabase;
    private FileModelDao dao;

    public FileRepository(ContactsDatabase contactsDatabase) {
        this.contactsDatabase = contactsDatabase;
        this.dao = contactsDatabase.fileModelDao();
    }

    public void insert(FileModel fileModel){
        dao.insert(fileModel);
    }

    public String getHeaderRow(int fileId){
        return dao.getHeaderRow(fileId);
    }

    public LiveData<List<FileModel>> getAllFileModels(){
        return dao.getAllFileModels();
    }

    public FileModel getLastAddedFile(){
        return dao.getLastAddedFile();
    }

    public List<FileModel> getAllFileModelsNoLiveData(){
        return dao.getAllFileModelsNoLiveData();
    }
    public static class InsertAsyncTask extends AsyncTask<FileModel, Void, Void> {
        private FileModelDao fileModelDao;

        public InsertAsyncTask(FileModelDao dao) {
            this.fileModelDao = dao;
        }

        @Override
        protected Void doInBackground(FileModel... fileModels) {
            fileModelDao.insert(fileModels[0]);
            return null;
        }
    }
}
