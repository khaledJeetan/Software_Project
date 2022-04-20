package model;

import java.sql.Timestamp;

public class Review {
    private User user;
    private String Comment;
    private int StarCount;
    private Timestamp creationDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getStarCount() {
        return StarCount;
    }

    public void setStarCount(int starCount) {
        StarCount = starCount;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
