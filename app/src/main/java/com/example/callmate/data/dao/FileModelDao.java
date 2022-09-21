package com.example.callmate.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.callmate.data.entities.FileModel;

import java.util.List;

@Dao
public interface FileModelDao {
    @Insert
    void insert(FileModel fileModel);

    @Query("SELECT headerRow from file_table WHERE fileId = :fileId")
    String getHeaderRow(int fileId);

    @Query("SELECT * FROM file_table WHERE fileId is :id")
    FileModel getFileModelFromId(int id);

    @Query("SELECT * FROM file_table WHERE fileId = (SELECT max(fileId) FROM file_table )")
    FileModel getLastAddedFile();

    @Query("SELECT * FROM file_table")
    LiveData<List<FileModel>> getAllFileModels();

    @Query("SELECT * FROM file_table")
    List<FileModel> getAllFileModelsNoLiveData();
}
