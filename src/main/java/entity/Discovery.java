package entity;

import java.sql.Timestamp;

public class Discovery {
    private int discoveryID;
    private String phone;
    Timestamp dateTime;
    String tag;
    String content;
    String imgURL;

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Discovery() {

    }

    public int getDiscoveryID() {
        return discoveryID;
    }

    public void setDiscoveryID(int discoveryID) {
        this.discoveryID = discoveryID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }





    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
