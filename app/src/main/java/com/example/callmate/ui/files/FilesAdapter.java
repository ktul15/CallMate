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
import com.example.callmate.ui.contacts.ContactsListActivity;
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
        holder.binding.tvFileName.setText(String.valueOf(currentFile.getFileName()));

        holder.binding.openFile.setOnClickListener(v -> {
            Log.i("id from adapter", String.valueOf(currentFile.getId()));
            //open new activity to show contacts from selected file
            Intent intent = new Intent(context, ContactsListActivity.class);
            intent.putExtra("fileId", currentFile.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
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
