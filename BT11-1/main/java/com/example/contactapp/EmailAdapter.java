package com.example.contactapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<EmailModel> items;

    public EmailAdapter(List<EmailModel> items) {
        this.items = items;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EmailModel item = items.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.iconView.setText(String.valueOf(item.getSender().charAt(0)));
        viewHolder.senderView.setText(item.getSender());
        viewHolder.briefView.setText(item.getBrief());
        viewHolder.subjectView.setText(item.getSubject());
        viewHolder.dateView.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView itemView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.email_card, parent,false);
        return new ItemViewHolder(itemView);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView iconView;
        TextView senderView;
        TextView subjectView;
        TextView briefView;
        TextView dateView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.avatar);
            senderView = itemView.findViewById(R.id.sender);
            subjectView = itemView.findViewById(R.id.subject);
            briefView = itemView.findViewById(R.id.brief);
            dateView = itemView.findViewById(R.id.date);
        }
    }
}
