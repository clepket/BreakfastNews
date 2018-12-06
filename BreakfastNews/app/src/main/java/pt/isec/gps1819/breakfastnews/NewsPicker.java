package pt.isec.gps1819.breakfastnews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public static class NewsPicker{
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
                    yc.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            while (((inputLine = in.readLine()) != null)  && cont<nNews) {
                if((inputLine.contains("<item>") && inputLine.contains("</item>")) && !merge){
                    NewsItem news = getNews(inputLine);
                    if(news != null){
                        System.out.println(news.toString());
                        this.newsList.add(news);
                        cont++;
                    }
                }else{
                    if(!merge) {
                        newsText = inputLine;
                        merge = true;
                    }else{
                        newsText += inputLine;

                        NewsItem news = getNews(newsText);
                        if(news != null){
                            System.out.println(news.toString());
                            this.newsList.add(news);
                            cont++;
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


    private NewsItem getNews(String infoNew){

        URL url = null;
        String originalLink = between(infoNew, "<feedburner:origLink>", "</feedburner:origLink>");
        NewsItem news = null;

        if(!originalLink.isEmpty()) {
            try {
                url = new URL(originalLink);
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream(), StandardCharsets.UTF_8));
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
                news = getNewsInfo(info, originalLink);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return news;
    }

    private NewsItem getNewsInfo(String article, String originalLink){

        int cont=0;

        List<String> split = Arrays.asList(article.split("(?=<)"));
        String pesquisa = split.get(cont);

        String title = null;
        String description = null;
        boolean finishDescription = false;
        String img = null;
        boolean flagImg = false;
        String direitosImg = null;
        boolean flagDireitosImg = false;
        String body = new String("");
        boolean finishBody = false;
        String date = null;


        do{
            if(pesquisa.contains("<img") && !flagImg && !finishBody){ //PARA BUSCAR A IMAGEM
                img = between(pesquisa, " src=", "alt");
                flagImg = true;
            }else if(pesquisa.contains("<p")  && flagImg && !flagDireitosImg && !finishBody) { //PARA BUSCAR OS DIREITOS DE AUTOR DA IMAGEM
                direitosImg = pesquisa + "<";
                direitosImg = between(direitosImg, ">", "<");
                flagDireitosImg = true;
            }else if(pesquisa.contains("<p>") && !finishBody) {//PARA BUSCAR O CORPO DA NOTICIA
                body += pesquisa+"</p>";
                if (!split.get(cont + 2).contains("</p>") && !split.get(cont + 1).contains("aside")) {
                    finishBody = true;
                }
            }else if(pesquisa.contains("description")) {//PARA BUSCAR A DESCRIÇÃO DA NOTICIA
                finishDescription = true;
            }else if(pesquisa.contains("<p")  && finishDescription) {//PARA OBTER A DESCRIÇÃO DA NOTICIA
                description = pesquisa + "<";
                description = between(description, ">", "<");
                finishDescription = false;
            }else if(pesquisa.contains("<h1")){//PARA BUSCAR O TITULO DA NOTICIA
                title = pesquisa + "<";
                title = between(title, ">", "<");
            }else if(pesquisa.contains("datetime")){//PARA BUSCAR A DATA DA NOTICIA
                date = pesquisa + "<";
                date = between(date, "datetime=", "content");
                date = date.substring(1, (date.length()-2));
            }

            pesquisa = split.get(cont);
            cont++;

        }while(cont<split.size());
        NewsItem news = new NewsItem(title, img, direitosImg, description, body, originalLink, null, date);

        return news;
    }
}
