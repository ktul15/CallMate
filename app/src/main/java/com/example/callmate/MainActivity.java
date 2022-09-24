package com.example.callmate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.callmate.data.ContactsDatabase;
import com.example.callmate.data.entities.ContactsModel;
import com.example.callmate.data.entities.FileModel;
import com.example.callmate.data.repositories.FileRepository;
import com.example.callmate.databinding.ActivityMainBinding;
import com.example.callmate.ui.contacts.ContactsViewModel;
import com.example.callmate.ui.files.FileViewModel;
import com.example.callmate.ui.files.FilesAdapter;
import com.example.callmate.utils.Contact;
import com.example.callmate.utils.DataProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ContactsViewModel contactsViewModel;
    ContactsDatabase contactsDatabase;
    FileRepository fileRepository;
    FileViewModel fileViewModel;

    FilesAdapter filesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.callmate.databinding.ActivityMainBinding binding = com.example.callmate.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkPermissions();

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        contactsDatabase = ContactsDatabase.getInstance(this);
        fileRepository = new FileRepository(contactsDatabase);
        fileViewModel = new FileViewModel(fileRepository);

        if(fileViewModel.getAllFileModelsNoLiveData().size() == 0){
            loadDummyData();
        }

        //Select a csv file from the phone
        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("*/*");
            activityResultLauncher.launch(intent);
        });

        fileViewModel.getAllFileModels().observe(this, fileModels -> {
            List<FileModel> listOfFiles;
            if (fileModels.size() > 0) {
                listOfFiles = fileModels;
            } else {
                listOfFiles = new ArrayList<>();
            }
            filesAdapter = new FilesAdapter(getApplicationContext(), fileViewModel, listOfFiles);
            binding.rvFiles.setAdapter(filesAdapter);
            binding.rvFiles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        });
    }

    private void loadDummyData() {
        Uri uri = getUri();

        try {
            DataProcessor dataProcessor = new DataProcessor(uri, this);
            DataProcessor.AppData appData = dataProcessor.transformCSVDataToAppData();

            ArrayList<Contact> listOfContacts = appData.contactList.getContacts();
            String fileName = appData.fileName;
            String headerRow = appData.headerRow;

            //Save fileModel in room database
            FileModel fileModel = new FileModel(fileName, headerRow);
            fileViewModel.insert(fileModel);

            //Save contactsModel in room database
            for(Contact contact: listOfContacts){
                int subscriberId = contact.getSubscriberId();
                String dateCreated = contact.getDate_created();
                String name = contact.getName();
                String mainPhoneNumber = contact.getMainPhoneNumber();
                ArrayList<String> alternatePhoneNumbers = contact.getAlternate_phone_numbers();
                String callingRemark = contact.getRemarks();
                int fileId = fileViewModel.getLastAddedFile().getId();

                ContactsModel contactsModel = new ContactsModel(subscriberId, dateCreated, name, mainPhoneNumber, alternatePhoneNumbers, callingRemark, fileId);
                contactsViewModel.insert(contactsModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Uri getUri() {
        AssetManager am = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = am.open("dumy_callmate.csv");
            Log.i("inputStream", inputStream.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = createFileFromInputStream(inputStream);
        Uri uri = Uri.fromFile(file);
        return uri;
    }

    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if(result.getResultCode() != RESULT_OK){
                            return;
                        }
                        assert result.getData() != null;

                        //Get the URI of the selected file
                        Uri csvFileUri = result.getData().getData();
                        try {
                            DataProcessor dataProcessor = new DataProcessor(csvFileUri, this);
                            DataProcessor.AppData appData = dataProcessor.transformCSVDataToAppData();

                            ArrayList<Contact> listOfContacts = appData.contactList.getContacts();
                            String fileName = appData.fileName;
                            String headerRow = appData.headerRow;

                            //Save fileModel in room database
                            FileModel fileModel = new FileModel(fileName, headerRow);
                            fileViewModel.insert(fileModel);

                            //Save contactsModel in room database
                            for(Contact contact: listOfContacts){
                                int subscriberId = contact.getSubscriberId();
                                String dateCreated = contact.getDate_created();
                                String name = contact.getName();
                                String mainPhoneNumber = contact.getMainPhoneNumber();
                                ArrayList<String> alternatePhoneNumbers = contact.getAlternate_phone_numbers();
                                String callingRemark = contact.getRemarks();
                                int fileId = fileViewModel.getLastAddedFile().getId();

                                ContactsModel contactsModel = new ContactsModel(subscriberId, dateCreated, name, mainPhoneNumber, alternatePhoneNumbers, callingRemark, fileId);
                                contactsViewModel.insert(contactsModel);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );

    private void checkPermissions() {
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }
    }

    private File createFileFromInputStream(InputStream inputStream) {
        try{
            File f = new File(getApplicationContext().getFilesDir(), "dummy_callmate.csv");
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}