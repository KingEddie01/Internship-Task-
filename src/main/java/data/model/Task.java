package data.model;

import java.time.LocalDateTime;

public class Task {
    private String title;
    private String description;
    private LocalDateTime date;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String toString() {
        return "Title: " + title + "," +
                " Description: " + description + ", " +
                "Due Date: " + date;
    }


}
