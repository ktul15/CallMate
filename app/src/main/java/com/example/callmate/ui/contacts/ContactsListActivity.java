package com.example.callmate.ui.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.example.callmate.R;
import com.example.callmate.data.entities.ContactsModel;
import com.example.callmate.databinding.ActivityContactsListBinding;
import com.example.callmate.ui.files.FileViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity {
    ActivityContactsListBinding binding;
    ContactsAdapter contactsAdapter;
    ContactsViewModel contactsViewModel;
    FileViewModel fileViewModel;
    List<ContactsModel> listOfContactModels = new ArrayList<>();
    int fileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactsListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fileId = getIntent().getIntExtra("fileId", -1);

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
//        fileViewModel = new ViewModelProvider(this).get(FileViewModel.class);

        contactsViewModel.getLiveDataContactsFromFileId(fileId).observe(this, contactModels -> {
            listOfContactModels = new ArrayList<>();
            Log.i("contactModels", contactModels.toString());
            if(contactModels.size() > 0){
                listOfContactModels = contactModels;
            }
            contactsAdapter = new ContactsAdapter(getApplicationContext(), ContactsListActivity.this, contactsViewModel, listOfContactModels);
            binding.rvContacts.setAdapter(contactsAdapter);
            binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
            contactsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(contactsViewModel.getIsCallEnded()) {
            contactsViewModel.setisCallEnded(false);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_calling_remarks, null);
            RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
            RemarksDialogBuilder dialogBuilder = new RemarksDialogBuilder(this, contactsViewModel, dialogView, radioGroup);
            dialogBuilder.createDialog().show();
        }
    }
}