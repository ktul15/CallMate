package com.example.callmate.data.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.callmate.data.entities.ContactsModel;
import com.example.callmate.data.ContactsDatabase;
import com.example.callmate.data.dao.ContactsModelDao;
import com.example.callmate.data.entities.ContactsModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContactsRepository {
    private ContactsModelDao contactsModelDao;
    private LiveData<List<ContactsModel>> contactsLiveData;

    public ContactsRepository(Application application) {
        ContactsDatabase contactsDatabase = ContactsDatabase.getInstance(application);
        contactsModelDao = contactsDatabase.contactsModelDao();
    }

    public void insert(ContactsModel contactsModel){
        new InsertAsyncTask(contactsModelDao).execute(contactsModel);
    }

    public void update(ContactsModel contactsModel){
        Log.i("ContactsRepository", "in update remark");
        new UpdateAsyncTask(contactsModelDao).execute(contactsModel);
    }

    public LiveData<List<ContactsModel>> getLiveDataContactsFromFileId(int fileId) {
        return contactsModelDao.getLiveDataContactsFromFileId(fileId);
    }

    public List<ContactsModel> getContactsFromFileId(int fileId) throws ExecutionException, InterruptedException {
//        return new GetByIdAsyncTask(contactsModelDao, fileId).execute().get();
        return contactsModelDao.getContactsFromFileId(fileId);
    }

    public List<ContactsModel> getAllContacts() throws ExecutionException, InterruptedException {
        return contactsModelDao.getAllContacts();
//        return new GetAsyncTask(contactsModelDao).execute().get();
    }

    public static class InsertAsyncTask extends AsyncTask<ContactsModel, Void, Void>{
        private ContactsModelDao contactsModelDao;

        public InsertAsyncTask(ContactsModelDao dao) {
            this.contactsModelDao = dao;
        }

        @Override
        protected Void doInBackground(ContactsModel... contactsModels) {
            contactsModelDao.insert(contactsModels[0]);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<ContactsModel, Void, Void>{
        private ContactsModelDao contactsModelDao;

        public UpdateAsyncTask(ContactsModelDao dao) {
            this.contactsModelDao = dao;
        }

        @Override
        protected Void doInBackground(ContactsModel... contactsModels) {
            Log.i("ContactsRepository", "in doInBackground");
            Log.i("contactsModels[0] name", contactsModels[0].getName());
            Log.i("contactsModels[0]", contactsModels[0].getCallingRemark());
            contactsModelDao.updateRemark(contactsModels[0]);
            return null;
        }
    }

    public static class GetAsyncTask extends AsyncTask<Void, Void, List<ContactsModel>> {
        private ContactsModelDao contactsModelDao;

        public GetAsyncTask(ContactsModelDao contactsModelDao) {
            this.contactsModelDao = contactsModelDao;
        }

        protected List<ContactsModel> doInBackground(Void... voids) {
            return contactsModelDao.getAllContacts();
        }
    }

    public static class GetByIdAsyncTask extends AsyncTask<Void, Void, List<ContactsModel>>{
        private final ContactsModelDao contactsModelDao;
        private final int fileId;

        public GetByIdAsyncTask(ContactsModelDao contactsModelDao, int fileId) {
            this.contactsModelDao = contactsModelDao;
            this.fileId = fileId;
        }

        protected List<ContactsModel> doInBackground(Void... voids) {
            return contactsModelDao.getContactsFromFileId(fileId);
        }
    }
}
