package com.example.callmate;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.callmate.databinding.ActivityMainBinding;
import com.example.callmate.utils.Contact;
import com.example.callmate.utils.DataProcessor;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.callmate.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkPermissions();

        //Select a csv file from the phone
        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("*/*");
            activityResultLauncher.launch(intent);
        });
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

                            Log.i("fileName", fileName);
                            Log.i("headerRow", headerRow);
                            Log.i("listOfContacts", listOfContacts.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );

    private void checkPermissions() {
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }
    }
}