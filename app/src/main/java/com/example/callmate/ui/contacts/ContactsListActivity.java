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
            contactsAdapter = new ContactsAdapter(getApplicationContext(), contactsViewModel, listOfContactModels);
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
//    public AlertDialog.Builder createDialog(View dialogView, RadioGroup radioGroup){
//        return new AlertDialog.Builder(ContactsListActivity.this)
//                .setTitle("Choose calling remarks:")
//                .setView(dialogView)
//                .setPositiveButton("Save", (dialog, which) -> {
//                    String remarks;
//                    int selectedId = radioGroup.getCheckedRadioButtonId();

//                    EditText etOtherRemark = dialogView.findViewById(R.id.etOtherRemark);
//                    String otherRemark = etOtherRemark.getText().toString();
//                    Log.i("otherRemark", otherRemark);
//                    System.out.println(otherRemark.length() > 0);
//                    if(otherRemark.length() > 0){
//                        remarks = otherRemark;
//                    } else if(selectedId != -1){
//                        Log.i("selectedId", String.valueOf(selectedId));
//                        RadioButton rb = radioGroup.findViewById(selectedId);
//                        remarks = rb.getText().toString();
//                    } else {
//                        remarks = "No remarks provided!";
//                        Toast.makeText(ContactsListActivity.this, "Choose at least one remark!", Toast.LENGTH_SHORT).show();
//                    }
//                    ContactsModel currentContact = contactsViewModel.getCurrentContact();
//                    contactsViewModel.updateRemarks(remarks, currentContact);
//                });
//    }
}