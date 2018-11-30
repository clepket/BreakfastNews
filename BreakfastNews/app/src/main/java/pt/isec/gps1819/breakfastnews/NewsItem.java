package pt.isec.gps1819.breakfastnews;

/**
 * Classe que serve como <i>dataset</i> das noticias a apresentar.
 *
 * @version v1
 * @since 2018-11-25
 * @author Carlos Pinho
 * @name NewsItem.java
 */
class NewsItem {
    private static int counter = 0;
    private int id;
    private String title, description, body, image, journalist;
    private String url;
    private boolean favourite;

    public NewsItem(String title, String image, String description, String body, String journalist, String url) {
        this.id = counter++;
        this.title = title;
        this.description = description;
        this.body = body;
        this.image = image;
        this.journalist = journalist;
        this.url = url;
        this.favourite = false;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
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

    @Override
    public String toString() {
        return "newsItem{" + "id=" + id
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", body='" + body + '\''
                + ", image='" + image + '\''
                + ", journalist='" + journalist + '\''
                + ", link='" + url + '\''
                + ", favourite=" + favourite
                + '}';
    }
}