package com.example.callmate.ui.files;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callmate.data.entities.FileModel;
import com.example.callmate.databinding.FileViewHolderBinding;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {
    private Context context;
    private List<FileModel> allFiles;

    public FilesAdapter(Context context , FileViewModel fileViewModel, List<FileModel> allFiles) {
        this.context = context;
        this.allFiles = allFiles;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FileViewHolderBinding binding = FileViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View view = binding.getRoot();
        return new FileViewHolder(view, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        FileModel currentFile = allFiles.get(position);
        holder.binding.tvFileName.setText(String.valueOf(currentFile.getId()));
    }

    @Override
    public int getItemCount() {
        return allFiles.size();
    }

    static class FileViewHolder extends RecyclerView.ViewHolder{
        private FileViewHolderBinding binding;

        public FileViewHolder(@NonNull View itemView, FileViewHolderBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
