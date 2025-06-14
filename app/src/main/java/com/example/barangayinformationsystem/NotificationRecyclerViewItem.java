package com.example.barangayinformationsystem;

import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationRecyclerViewItem {
    private String nameOfUser;
    private String caption;
    private int image;
    private long timestamp; // Optional: for real timestamps

    public NotificationRecyclerViewItem(String nameOfUser, String caption, int image) {
        this.nameOfUser = nameOfUser;
        this.caption = caption;
        this.image = image;
        this.timestamp = System.currentTimeMillis(); // Set current time as default
    }

    // Constructor with timestamp
    public NotificationRecyclerViewItem(String nameOfUser, String caption, int image, long timestamp) {
        this.nameOfUser = nameOfUser;
        this.caption = caption;
        this.image = image;
        this.timestamp = timestamp;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public String getCaption() {
        return caption;
    }

    public int getImage() {
        return image;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        NotificationRecyclerViewItem that = (NotificationRecyclerViewItem) obj;

        // Compare both title and caption to handle different types of notifications
        return Objects.equals(nameOfUser, that.nameOfUser) &&
                Objects.equals(caption, that.caption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfUser, caption);
    }

    // Convert notification to JSON string for storage
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("nameOfUser", nameOfUser);
            json.put("caption", caption);
            json.put("image", image);
            json.put("timestamp", timestamp);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create notification from JSON string
    public static NotificationRecyclerViewItem fromJson(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            String name = json.getString("nameOfUser");
            String caption = json.getString("caption");
            int image = json.getInt("image");
            long timestamp = json.optLong("timestamp", System.currentTimeMillis()); // Default to current time if not present
            return new NotificationRecyclerViewItem(name, caption, image, timestamp);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create a unique identifier for this notification
    public String getUniqueId() {
        String identifier = nameOfUser + "|" + caption;
        return String.valueOf(identifier.hashCode());
    }
}