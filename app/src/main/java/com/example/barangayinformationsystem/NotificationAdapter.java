package com.example.barangayinformationsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private List<NotificationRecyclerViewItem> notificationItems;
    private OnNotificationInteractionListener listener;

    // Interface for handling notification interactions
    public interface OnNotificationInteractionListener {
        void onNotificationClick(NotificationRecyclerViewItem item, int position);
        void onNotificationDelete(NotificationRecyclerViewItem item, int position);
    }

    public NotificationAdapter(Context context, List<NotificationRecyclerViewItem> notificationItems) {
        this.context = context;
        this.notificationItems = notificationItems;
    }

    public void setOnNotificationInteractionListener(OnNotificationInteractionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item_view, parent, false);
        return new ViewHolder(view);
    }    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationRecyclerViewItem item = notificationItems.get(position);        holder.titleTextView.setText(item.getNameOfUser());
        holder.messageTextView.setText(item.getCaption());
        holder.iconImageView.setImageResource(item.getImage());

        // Use the actual timestamp from the notification item
        String timeAgo = getTimeAgoFromTimestamp(item.getTimestamp());
        holder.timestampTextView.setText(timeAgo);

        // Set click listener for notification item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNotificationClick(item, position);
            }
        });

        // Set click listener for delete button
        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNotificationDelete(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationItems != null ? notificationItems.size() : 0;
    }    // Method to calculate time ago from timestamp in milliseconds
    private String getTimeAgoFromTimestamp(long timestamp) {
        try {
            Date notificationDate = new Date(timestamp);
            Date now = new Date();
            long diff = now.getTime() - notificationDate.getTime();
            long minutes = diff / (60 * 1000);
            long hours = diff / (60 * 60 * 1000);
            long days = diff / (24 * 60 * 60 * 1000);

            if (minutes < 1) {
                return "Just now";
            } else if (minutes < 60) {
                return minutes + " minute" + (minutes == 1 ? "" : "s") + " ago";
            } else if (hours < 24) {
                return hours + " hour" + (hours == 1 ? "" : "s") + " ago";
            } else if (days < 7) {
                return days + " day" + (days == 1 ? "" : "s") + " ago";
            } else if (days < 30) {
                long weeks = days / 7;
                return weeks + " week" + (weeks == 1 ? "" : "s") + " ago";
            } else {
                SimpleDateFormat displayFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
                return displayFormat.format(notificationDate);
            }
        } catch (Exception e) {
            return "Just now";
        }
    }

    // Method to calculate actual time ago if you have a timestamp
    private String getTimeAgo(String createdAt) {
        try {
            SimpleDateFormat[] formats = {
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            };

            Date notificationDate = null;
            for (SimpleDateFormat format : formats) {
                try {
                    notificationDate = format.parse(createdAt);
                    break;
                } catch (ParseException e) {
                    // Try next format
                }
            }

            if (notificationDate == null) {
                return "Just now";
            }

            Date now = new Date();
            long diff = now.getTime() - notificationDate.getTime();
            long minutes = diff / (60 * 1000);
            long hours = diff / (60 * 60 * 1000);
            long days = diff / (24 * 60 * 60 * 1000);

            if (minutes < 1) {
                return "Just now";
            } else if (minutes < 60) {
                return minutes + " minute" + (minutes == 1 ? "" : "s") + " ago";
            } else if (hours < 24) {
                return hours + " hour" + (hours == 1 ? "" : "s") + " ago";
            } else if (days < 7) {
                return days + " day" + (days == 1 ? "" : "s") + " ago";
            } else if (days < 30) {
                long weeks = days / 7;
                return weeks + " week" + (weeks == 1 ? "" : "s") + " ago";
            } else {
                SimpleDateFormat displayFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
                return displayFormat.format(notificationDate);
            }
        } catch (Exception e) {
            return "Just now";
        }
    }    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView titleTextView;
        MaterialTextView messageTextView;
        MaterialTextView timestampTextView;
        ImageView iconImageView;
        ImageView deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notification_recycler_view_item_name_of_user_material_text_view);
            messageTextView = itemView.findViewById(R.id.notification_recycler_view_item_caption_of_user_material_text_view);
            timestampTextView = itemView.findViewById(R.id.notification_timestamp);
            iconImageView = itemView.findViewById(R.id.notification_recycler_view_item_image_view);
            deleteButton = itemView.findViewById(R.id.notification_delete_button);
        }
    }

    // Method to remove notification from list
    public void removeNotification(int position) {
        if (position >= 0 && position < notificationItems.size()) {
            notificationItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, notificationItems.size());
        }
    }
}