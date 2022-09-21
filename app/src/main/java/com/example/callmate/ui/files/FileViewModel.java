package com.example.callmate.ui.files;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.callmate.data.entities.FileModel;
import com.example.callmate.data.repositories.FileRepository;

import java.util.List;

public class FileViewModel extends ViewModel {
    private FileRepository fileRepository;

    public FileViewModel(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void insert(FileModel fileModel){
        fileRepository.insert(fileModel);
    }

    public String getHeaderRow(int fileId){
        return fileRepository.getHeaderRow(fileId);
    }

    public LiveData<List<FileModel>> getAllFileModels(){
        return fileRepository.getAllFileModels();
    }

    public List<FileModel> getAllFileModelsNoLiveData(){
        return fileRepository.getAllFileModelsNoLiveData();
    }

    public FileModel getLastAddedFile(){
        return fileRepository.getLastAddedFile();
    }
}
