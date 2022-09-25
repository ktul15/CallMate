package com.example.callmate.utils;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataProcessor {
    private Uri csvFileUri;
    private Context context;

    public DataProcessor(Uri csvFileUri, Context context) {
        this.csvFileUri = csvFileUri;
        this.context = context;
    }

    private String getFileName(Uri fileUri) throws FileNotFoundException {
        String filePath = fileUri.getLastPathSegment();
        return filePath;
    }

    private BufferedReader getFileReader(Context context, Uri fileUri) throws FileNotFoundException {
        ParcelFileDescriptor inputPFD = context.getContentResolver().openFileDescriptor(fileUri, "r");
        FileDescriptor fileDescriptor = inputPFD.getFileDescriptor();
        FileReader fileReader = new FileReader(fileDescriptor);
        return  new BufferedReader(fileReader);
    }

    private List<String> getRows() throws IOException {
        BufferedReader fileReader = getFileReader(this.context, this.csvFileUri);

        ArrayList<String> listOfRows = new ArrayList<>();

        String nextRow;
        while((nextRow = fileReader.readLine()) != null){
            listOfRows.add(nextRow);
        }

        return listOfRows;
    }

    public AppData transformCSVDataToAppData() throws IOException {
        HashMap<String, Integer> rowNumbers = new HashMap();
        List<String> rows = getRows();

        String headerRow = rows.get(0);
        String fileName = getFileName(this.csvFileUri);

        ArrayList<String> headerRowData = new ArrayList<>(List.of(headerRow.split(",")));
        for(String dataItem: headerRowData){
            switch (dataItem){
                case Constants.SUBSCRIBER_ID:
                    rowNumbers.put(Constants.SUBSCRIBER_ID, headerRowData.indexOf(dataItem));
                    break;
                case Constants.DATE_CREATED:
                    rowNumbers.put(Constants.DATE_CREATED, headerRowData.indexOf(dataItem));
                    break;
                case Constants.NAME:
                    rowNumbers.put(Constants.NAME, headerRowData.indexOf(dataItem));
                    break;
                case Constants.PRIMARY_PHONE:
                    rowNumbers.put(Constants.PRIMARY_PHONE, headerRowData.indexOf(dataItem));
                    break;
                case Constants.ALTERNATE_PHONE_1:
                    rowNumbers.put(Constants.ALTERNATE_PHONE_1, headerRowData.indexOf(dataItem));
                    break;
                case Constants.ALTERNATE_PHONE_2:
                    rowNumbers.put(Constants.ALTERNATE_PHONE_2, headerRowData.indexOf(dataItem));
                    break;
                case Constants.ALTERNATE_PHONE_3:
                    rowNumbers.put(Constants.ALTERNATE_PHONE_3, headerRowData.indexOf(dataItem));
                    break;
                case Constants.ALTERNATE_PHONE_4:
                    rowNumbers.put(Constants.ALTERNATE_PHONE_4, headerRowData.indexOf(dataItem));
                    break;
            }
        }

        ContactList contactList = new ContactList();
        for(int i = 1; i < rows.size(); i++){
            Integer columnNumber = 0;
            ArrayList<String> rowData = new ArrayList<>(List.of(rows.get(i).split(",")));
            Log.e("rowData", rowData.toString());
            Contact contact = new Contact();
            contact.setSubscriberId(Integer.parseInt(rowData.get(rowNumbers.get(Constants.SUBSCRIBER_ID))));
            contact.setDate_created(rowData.get(rowNumbers.get(Constants.DATE_CREATED)));
            contact.setName(rowData.get(rowNumbers.get(Constants.NAME)));
            contact.setMainPhoneNumber(rowData.get(rowNumbers.get(Constants.PRIMARY_PHONE)));

            ArrayList<String> alternatePhoneNumbers = new ArrayList<>();
            alternatePhoneNumbers.add(rowData.get(rowNumbers.get(Constants.ALTERNATE_PHONE_1)));
            alternatePhoneNumbers.add(rowData.get(rowNumbers.get(Constants.ALTERNATE_PHONE_2)));
            alternatePhoneNumbers.add(rowData.get(rowNumbers.get(Constants.ALTERNATE_PHONE_3)));
            alternatePhoneNumbers.add(rowData.get(rowNumbers.get(Constants.ALTERNATE_PHONE_4)));

            contact.setAlternate_phone_numbers(alternatePhoneNumbers);
            contactList.addNewContact(contact);
        }

        return new AppData(fileName, headerRow, contactList);
    }

    public class AppData{
        public String fileName;
        public String headerRow;
        public ContactList contactList;

        public AppData(String fileName, String headerRow, ContactList contactList) {
            this.fileName = fileName;
            this.headerRow = headerRow;
            this.contactList = contactList;
        }
    }
}
