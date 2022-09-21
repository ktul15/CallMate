package com.example.callmate.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "file_table")
public class FileModel {
    @PrimaryKey(autoGenerate = true)
    public int fileId;
    String fileName;
    String headerRow;

    public FileModel(String fileName, String headerRow) {
        this.fileName = fileName;
        this.headerRow = headerRow;
    }

    public int getId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getHeaderRow() {
        return headerRow;
    }
}
