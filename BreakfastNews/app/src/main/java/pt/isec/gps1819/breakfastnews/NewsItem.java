package pt.isec.gps1819.breakfastnews;

import java.io.Serializable;

/**
 * Classe que serve como <i>dataset</i> das noticias a apresentar.
 *
 * @version v1
 * @since 2018-11-25
 * @author Carlos Pinho
 * @name NewsItem.java
 */
public class NewsItem implements Serializable {
    private static int counter = 0;
    private int id;
    private String title, description, body, image, journalist;
    private String url, imageRights;
    private boolean favourite;
    private String date;

    public NewsItem() {

    }

    public NewsItem(String title, String image, String imageRights, String description, String body, String url, String journalist, String date){
        this.id = counter++;
        this.title = title;
        this.description = description;
        this.body = body;
        this.image = image;
        this.imageRights = imageRights;
        this.journalist = journalist;
        this.url = url;
        this.favourite = false;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }

    public String getImage() {
        return image;
    }

    public String getJournalist() {
        return journalist;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setJournalist(String journalist) {
        this.journalist = journalist;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return "newsItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", image='" + image + '\'' +
                ", imageRights='" + imageRights + '\'' +
                ", journalist='" + journalist + '\'' +
                ", link='" + url + '\'' +
                ", favourite='" + favourite + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}