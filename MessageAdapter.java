package com.example.robert;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) { // User message
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_message, parent, false);
        } else { // Bot message
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bot_message, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.textViewMessage.setText(message.getText());


        if (message.isSentByUser()) {
            if (holder.imageViewUserIcon != null) {
                Glide.with(holder.imageViewUserIcon.getContext())
                        .load(R.drawable.user_icon)
                        .circleCrop()
                        .into(holder.imageViewUserIcon);
                holder.imageViewUserIcon.setVisibility(View.VISIBLE);
            }

            // Guard against null for bot icon
            if (holder.imageViewBotIcon != null) {
                holder.imageViewBotIcon.setVisibility(View.GONE);
            }

        } else {
            if (holder.imageViewBotIcon != null) {
                Glide.with(holder.imageViewBotIcon.getContext())
                        .load(R.drawable.robert_icon)
                        .circleCrop()
                        .into(holder.imageViewBotIcon);
                holder.imageViewBotIcon.setVisibility(View.VISIBLE);
            }

            // Guard against null for user icon
            if (holder.imageViewUserIcon != null) {
                holder.imageViewUserIcon.setVisibility(View.GONE);
            }
        }

        //if (holder.imageViewBotIcon != null) {
        //            Glide.with(holder.imageViewBotIcon.getContext())
        //                    .load(R.drawable.robert_icon)
        //                    .circleCrop()
        //                    .into(holder.imageViewBotIcon);
        //            holder.imageViewBotIcon.setVisibility(View.VISIBLE);
        //        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isSentByUser() ? 0 : 1;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;
        de.hdodenhof.circleimageview.CircleImageView imageViewUserIcon;
        de.hdodenhof.circleimageview.CircleImageView imageViewBotIcon;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);

            imageViewUserIcon = itemView.findViewById(R.id.imageViewUserIcon);
            imageViewBotIcon = itemView.findViewById(R.id.imageViewBotIcon);
        }
    }


}