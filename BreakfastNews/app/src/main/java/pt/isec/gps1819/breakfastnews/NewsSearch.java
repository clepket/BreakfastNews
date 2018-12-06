public class NewsSearch {
    private NewsPicker newsPicker;
    private List<NewsItem> newsListPicked;
    private List<NewsItem> selectedNewsList;
    private int maxNews;

    public NewsSearch(int max){
        newsListPicked = new ArrayList<>();
        selectedNewsList = new ArrayList<>();
        this.maxNews = max;
    }

    public List<NewsItem> findNews(List<String> keywords, List<String> journalists) {

        boolean approved = false;
        newsPicker = new NewsPicker();
        newsListPicked = newsPicker.findNews("http://feeds.ojogo.pt/OJ-Ultimas", maxNews);

        for(NewsItem news: newsListPicked){
            if(keywords != null) {
                if(!keywords.isEmpty()) {
                    for (String keyword : keywords) {
                        if (news.getTitle().contains(keyword)) {
                            approved=true;
                        } else if (news.getDescription().contains(keyword)) {
                            approved=true;
                        } else if (news.getBody().contains(keyword)) {
                            approved=true;
                        }
                    }
                }
            }
            if(journalists != null) {
                if(!journalists.isEmpty()) {
                    for (String journalist : journalists) {
                        if (news.getJournalist().contains(journalist)) {
                            approved=true;
                        }
                    }
                }
            }
            if(approved){
                selectedNewsList.add(news);
                approved=false;
            }
        }
        return this.selectedNewsList;
    }

}