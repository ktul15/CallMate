package com.example.callmate.ui.contacts;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.callmate.R;
import com.example.callmate.data.entities.ContactsModel;

public class RemarksDialog {
    private ContactsListActivity contactsListActivity;
    private ContactsViewModel contactsViewModel;
    private View dialogView;
    private RadioGroup radioGroup;

    public RemarksDialog(ContactsListActivity contactsListActivity, ContactsViewModel contactsViewModel, View dialogView, RadioGroup radioGroup) {
        this.contactsListActivity = contactsListActivity;
        this.contactsViewModel = contactsViewModel;
        this.dialogView = dialogView;
        this.radioGroup = radioGroup;
    }

    public AlertDialog.Builder createDialog(){
        return new AlertDialog.Builder(contactsListActivity)
                .setTitle("Choose calling remarks:")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String remarks;
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    EditText etOtherRemark = dialogView.findViewById(R.id.etOtherRemark);
                    String otherRemark = etOtherRemark.getText().toString();
                    Log.i("otherRemark", otherRemark);
                    System.out.println(otherRemark.length() > 0);
                    if(otherRemark.length() > 0){
                        remarks = otherRemark;
                    } else if(selectedId != -1){
                        Log.i("selectedId", String.valueOf(selectedId));
                        RadioButton rb = radioGroup.findViewById(selectedId);
                        remarks = rb.getText().toString();
                    } else {
                        remarks = "No remarks provided!";
                        Toast.makeText(contactsListActivity, "Choose at least one remark!", Toast.LENGTH_SHORT).show();
                    }
                    ContactsModel currentContact = contactsViewModel.getCurrentContact();
                    contactsViewModel.updateRemarks(remarks, currentContact);
                });
    }
}
