package entity;

import java.sql.Timestamp;

public class Article {
    private String article_id;
    private String phone;
    String content;
    String tag;
    Timestamp time;
    String img_url_json;

    public String getImg_url_json() {
        return img_url_json;
    }

    public void setImg_url_json(String img_url_json) {
        this.img_url_json = img_url_json;
    }

    public Article() {

    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
