package pt.isec.gps1819.breakfastnews;

import java.util.List;

static class NewsPicker{
    private List<NewsItem> newsList;

    public NewsPicker(){
        newsList = new ArrayList<>();
    }

    public List<NewsItem> findNews(String link, int nNews){
        URL url = null;
        String newsText = null;
        boolean merge=false;
        int cont=0;
        try {
            url = new URL(link);
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;
            while (((inputLine = in.readLine()) != null)  ) {
                if((inputLine.contains("<item>") && inputLine.contains("</item>")) && !merge){
                    NewsItem news = getNew(inputLine);
                    if(news != null){
                        System.out.println(news.toString());
                        this.newsList.add(news);
                    }
                }else{
                    if(!merge) {
                        newsText = inputLine;
                        merge = true;
                    }else{
                        newsText += inputLine;

                        NewsItem news = getNew(newsText);
                        if(news != null){
                            System.out.println(news.toString());
                            this.newsList.add(news);
                        }
                        merge = false;
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.newsList;
    }

    private static String between(String value, String a, String b) {
        // Return a substring between the two strings.
        int posA = value.lastIndexOf(a);
        if (posA == -1) {
            return "";
        }
        int posB = value.lastIndexOf(b);
        if (posB == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= posB) {
            return "";
        }
        return value.substring(adjustedPosA, posB);
    }

    private NewsItem getNew(String infoNew){

        URL url = null;
        String title = between(infoNew, "<title><![CDATA[", "]]></title>");

        if(!title.isEmpty()) {
            String description = between(infoNew, "<description>", "&lt");
            String originalLink = between(infoNew, "<feedburner:origLink>", "</feedburner:origLink>");
                /*System.out.println("\nFrase: " + infoNew + "\n");
                System.out.println("\nTitulo: " + title + "\n");
                System.out.println("Descricao: " + description + "\n");
                System.out.println("Link: " + originalLink + "\n");*/

            try {
                url = new URL(originalLink);
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream()));
                String inputLine;
                String newsInfo = null;
                boolean merge=false;

                while (((inputLine = in.readLine()) != null)) {

                    if(inputLine.contains("<article")) {
                        newsInfo = inputLine;
                        merge = true;
                    }else if(inputLine.contains("</article")){
                        newsInfo += inputLine;
                        merge = false;
                    }else if(merge){
                        newsInfo += inputLine;
                    }
                }
                String info = between(newsInfo, "<article", "</article>");
                //System.out.println(info);
                getNewsBody(info);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //NewsItem news = new SelectedNews(title, description, originalLink);
            //return news;
        }
        return null;
    }

    private String getNewsBody(String article){

        List<String> split = Arrays.asList(article.split("(?=<p>)"));

        int cont = 0;
        String paragrafo = null;

        while(cont<split.size()){
            paragrafo = between(split.get(cont).toString(), "<p>", "</p>");
            System.out.println("Split: " +split.get(cont).toString());
            System.out.println("Paragrafo: " + paragrafo);
            cont++;
        }
        System.out.println("\n");
        return null;
    }
}
