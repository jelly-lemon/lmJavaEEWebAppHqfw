package entity;

import java.sql.Timestamp;

public class Comment {
    private int commentID;
    private String senderPhone;
    private Timestamp dateTime;

    private int discoveryID;
    private String content;

    public int getDiscoveryID() {
        return discoveryID;
    }

    public void setDiscoveryID(int discoveryID) {
        this.discoveryID = discoveryID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
