package pt.isec.gps1819.breakfastnews;

import java.util.ArrayList;
import java.util.List;

import pt.isec.gps1819.breakfastnews.NewsItem;
import pt.isec.gps1819.breakfastnews.NewsPicker;

public class NewsSearch {
    private String validacao;
    private NewsPicker newsPicker;
    private List<NewsItem> newsListPicked;
    private List<NewsItem> selectedNewsList;
    private int maxNews;

    public NewsSearch(int max) {
        this.newsListPicked = new ArrayList<>();
        this.selectedNewsList = new ArrayList<>();
        this.maxNews = max;
        this.validacao = new String("");
    }

    public List<NewsItem> findNews(List<String> keywords, List<String> journalists) {

        boolean approved = false;
        newsPicker = new NewsPicker(this);
        //http://feeds.ojogo.pt/OJ-Ultimas
        //http://feeds.jn.pt/JN-Ultimas
        newsPicker.execute("" + maxNews, "http://feeds.jn.pt/JN-Ultimas", "http://feeds.ojogo.pt/OJ-Ultimas");

        while (!validacao.equals("aprovado")) {
        }
        if (newsListPicked != null) {
            int cont =0;
            for (NewsItem news : newsListPicked) {
                if (cont < maxNews) {
                    if (keywords != null) {
                        if (!keywords.isEmpty()) {
                            for (String keyword : keywords) {
                                String auxTitle = news.getTitle().toLowerCase();
                                String auxDescription = news.getDescription().toLowerCase();
                                String auxBody = news.getBody().toLowerCase();
                                String auxKeyword = keyword.toLowerCase();
                                if (auxTitle.contains(auxKeyword)) {
                                    approved = true;
                                } else if (auxDescription.contains(auxKeyword)) {
                                    approved = true;
                                } else if (auxBody.contains(auxKeyword)) {
                                    approved = true;
                                }
                            }
                        }
                    }
                    if (journalists != null) {
                        if (!journalists.isEmpty()) {
                            for (String journalist : journalists) {
                                if(news.getJournalist()!=null) {
                                    if (news.getJournalist().contains(journalist)) {
                                        approved = true;
                                    }
                                }
                            }
                        }
                    }
                    if (approved) {
                        selectedNewsList.add(news);
                        cont++;
                        approved = false;
                    }
                }else
                    break;
            }
            return this.selectedNewsList;
        }

        return null;
    }

    public void onBackgroundTaskCompleted(List<NewsItem> listNews) {
        newsListPicked.clear();
        this.newsListPicked = listNews;
        validacao = new String("aprovado");
    }

}