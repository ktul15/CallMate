package com.example.callmate.ui.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callmate.data.entities.ContactsModel;
import com.example.callmate.databinding.ContactViewHolderBinding;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    List<ContactsModel> allContacts;
    Context context;
    ContactsViewModel viewModel;

    public ContactsAdapter(Context context, ContactsViewModel viewModel, List<ContactsModel> allContacts){
        this.context = context;
        this.viewModel = viewModel;
        this.allContacts = allContacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactViewHolderBinding binding = ContactViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View view = binding.getRoot();
        return new ContactViewHolder(view, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ContactsModel currentContact = allContacts.get(holder.getAdapterPosition());
        holder.binding.tvContactSubId.setText(String.valueOf(currentContact.getSubscriberId()));
        holder.binding.tvContactName.setText(currentContact.getName());
        holder.binding.tvCallingRemarks.setText(currentContact.getCallingRemark());

        holder.binding.callBtn.setOnClickListener(v -> {
            viewModel.setCurrentContact(currentContact);

            String mainNumber = allContacts.get(position).getMainPhoneNumber();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+mainNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return allContacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        ContactViewHolderBinding binding;
        public ContactViewHolder(@NonNull View itemView, ContactViewHolderBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
