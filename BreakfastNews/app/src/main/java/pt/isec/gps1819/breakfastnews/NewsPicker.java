package pt.isec.gps1819.breakfastnews;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsPicker extends AsyncTask<String, Void, List<NewsItem>> {
    private NewsSearch newsSearch;
    private List<NewsItem> newsList;
    private String url = null;

    public NewsPicker(NewsSearch newsSearch) {
        this.newsSearch = newsSearch;
        newsList = new ArrayList<>();
    }

    @Override
    protected List<NewsItem> doInBackground(String... strings) {
        newsList.clear();
        int nNews = Integer.parseInt(strings[0]);

        int cont = 0;

        for (int i = 1; i < strings.length && cont < nNews; i++) {

            URL url = null;
            String newsText = null;
            boolean merge = false;

            try {
                url = new URL(strings[i]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                int statusCode = urlConnection.getResponseCode();
                if (statusCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), StandardCharsets.UTF_8));
                    String inputLine;
                    while (((inputLine = in.readLine()) != null) && cont < nNews) {
                        if ((inputLine.contains("<item>") && inputLine.contains("</item>")) && !merge) {
                            NewsItem news = getNews(inputLine);
                            if (news != null) {
                                System.out.println(news.toString());
                                this.newsList.add(news);
                                cont++;
                            }
                        } else {
                            if (!merge) {
                                newsText = inputLine;
                                merge = true;
                            } else {
                                newsText += inputLine;

                                NewsItem news = getNews(newsText);
                                if (news != null) {
                                    System.out.println(news.toString());
                                    this.newsList.add(news);
                                    cont++;
                                }
                                merge = false;
                            }
                        }
                    }
                    in.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        newsSearch.onBackgroundTaskCompleted(this.newsList);
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

    private static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    private static String after(String value, String a) {
        // Returns a substring containing all characters after a string.
        int posA = value.lastIndexOf(a);
        if (posA == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= value.length()) {
            return "";
        }
        return value.substring(adjustedPosA);
    }


    private NewsItem getNews(String infoNew) {

        URL url = null;
        String originalLink = between(infoNew, "<feedburner:origLink>", "</feedburner:origLink>");
        NewsItem news = null;
        boolean findArticle = false;

        if (!originalLink.isEmpty()) {
            try {
                url = new URL(originalLink);
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                String newsInfo = null;
                boolean merge = false;

                while (((inputLine = in.readLine()) != null)) {

                    if (inputLine.contains("<article ") && !findArticle) {
                        newsInfo = inputLine;
                        merge = true;
                        findArticle = true;
                    } else if (inputLine.contains("</article>") && findArticle) {
                        newsInfo += inputLine;
                        merge = false;
                        break;
                    } else if (merge) {
                        newsInfo += inputLine;
                    }
                }
                String info = before(newsInfo, "</article>");
                news = getNewsInfo(info, originalLink);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return news;
    }

    private NewsItem getNewsInfo(String article, String originalLink) {

        int cont = 0;

        List<String> split = Arrays.asList(article.split("(?=<)"));
        String pesquisa = split.get(cont);

        String title = new String("");
        boolean titleFlag = false;
        String img = null;
        boolean flagImg = false;
        String direitosImg = null;
        boolean flagDireitosImg = false;
        String description = null;
        boolean finishDescription = false;
        boolean getDescription = false;
        String body = new String("");
        boolean finishBody = false;
        String date = null;


        do {

            if ((pesquisa.contains("<h1 ") && !titleFlag) || (pesquisa.contains("</h1>") && !titleFlag)) {//PARA BUSCAR O TITULO DA NOTICIA
                if (pesquisa.contains("</h1>")) {
                    title += pesquisa.substring(0, pesquisa.length() - 2);
                    title = between(title, ">", "<");
                    titleFlag = true;
                } else
                    title += pesquisa.substring(1);
            } else if (pesquisa.contains("<img ") && !flagImg) { //PARA BUSCAR A IMAGEM
                img = "";
                String auxImg = between(pesquisa, " src=", "alt");
                List<String> aux = new ArrayList<>();
                aux = Arrays.asList(auxImg.split("&amp;"));
                for(int a=0; a<aux.size(); a++){
                    if(a+1==aux.size()) {
                        img += aux.get(a);
                    }else {
                        img += aux.get(a) + "&";
                    }
                }
                img = img.substring(1, img.length()-2);
                flagImg = true;
            } else if (pesquisa.contains("<p ") && pesquisa.contains("author") && flagImg && !flagDireitosImg) { //PARA BUSCAR OS DIREITOS DE AUTOR DA IMAGEM
                direitosImg = pesquisa + "<";
                direitosImg = between(direitosImg, ">", "<");
                flagDireitosImg = true;
                finishDescription = false;
                cont++;
            } else if ((pesquisa.contains("<strong>") || pesquisa.contains("</strong>")) && !finishDescription) {//PARA OBTER A DESCRIÇÃO DA NOTICIA
                if (pesquisa.contains("</strong>")) {
                    description += pesquisa.substring(0, pesquisa.length() - 2);
                    description = between(description, ">", "<");
                    finishDescription = true;
                } else
                    description += pesquisa.substring(1);
            } else if ((pesquisa.contains("<p ") || pesquisa.contains("</p>")) && !finishDescription) {//PARA OBTER A DESCRIÇÃO DA NOTICIA
                if (pesquisa.contains("</p>")) {
                    description += pesquisa.substring(0, pesquisa.length() - 2);
                    description = between(description, ">", "<");
                    finishDescription = true;
                } else
                    description += pesquisa.substring(1);
            } else if (pesquisa.contains("<p>") && !finishBody && finishDescription) {//PARA BUSCAR O CORPO DA NOTICIA
                body += "\t\t\t" + after(pesquisa, "<p>") + "\n";
                if (!split.get(cont + 2).contains("</p>") && !split.get(cont + 1).contains("aside")) {
                    finishBody = true;
                }
            } else if (pesquisa.contains("datetime")) {//PARA BUSCAR A DATA DA NOTICIA
                date = pesquisa + "<";
                date = between(date, "datetime=", "content");
                date = date.substring(1, (date.length() - 2));
            }

            pesquisa = split.get(cont);
            cont++;

        } while (cont < split.size());


        NewsItem news = new NewsItem(title, img, direitosImg, description, body, originalLink, null, date);

        return news;
    }
}
