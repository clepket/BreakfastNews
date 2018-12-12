package pt.isec.gps1819.breakfastnews;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Fragmento da aplicação que irá apresentar o feed de noticias.
 *
 * @author Carlos Pinho
 * @version v1
 * @name FeedFragment.java
 * @since 2018-11-22
 */
public class FeedFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsItemAdapter newsItemAdapter;
    private List<NewsItem> newsList = new ArrayList<>();
    List<String> keywords = new ArrayList<>();
    List<String> jornalistas = new ArrayList<>();

    public FeedFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = (RecyclerView) thisView.findViewById(R.id.recyclerViewNews);
        newsItemAdapter = new NewsItemAdapter(getContext(), newsList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsItemAdapter);

        prepareNews();

        return thisView;
    }

    private void prepareNews() {

        int maxNews = 10;

        String buffer = ((MainActivity) getActivity()).readFile("perfil.txt");
        keywords = ((MainActivity)getActivity()).devolveKeywords(buffer);
        if(keywords == null){
            keywords = new ArrayList<>();
        }
        jornalistas = ((MainActivity)getActivity()).devolveJornalistas(buffer);
        if(jornalistas == null){
            jornalistas = new ArrayList<>();
        }

        newsList.clear();
        //newsList.addAll(newsSearch.findNews(keywords, jornalistas));


        NewsPicker newsPicker = new NewsPicker(this);
        //http://feeds.ojogo.pt/OJ-Ultimas
        //http://feeds.jn.pt/JN-Ultimas
        newsPicker.execute("" + maxNews, "http://feeds.jn.pt/JN-Ultimas", "http://feeds.ojogo.pt/OJ-Ultimas");


        newsItemAdapter.notifyDataSetChanged();

    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public void setNewsList(List<NewsItem> newsListPicked) {

        int maxShowNews = 10;
        boolean approved = false;
        List<NewsItem> auxList = new ArrayList<>();

        if (newsListPicked != null) {
            int cont = 0;
            for (NewsItem news : newsListPicked) {
                if (cont < maxShowNews) {
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
                    if (jornalistas != null) {
                        if (!jornalistas.isEmpty()) {
                            for (String journalist : jornalistas) {
                                if (news.getJournalist() != null) {
                                    if (news.getJournalist().contains(journalist)) {
                                        approved = true;
                                    }
                                }
                            }
                        }
                    }
                    if (approved) {
                        auxList.add(news);
                        cont++;
                        approved = false;
                    }
                } else
                    break;
            }
        }

        this.newsList = auxList;
        newsItemAdapter = new NewsItemAdapter(getContext(), this.newsList);
        recyclerView.setAdapter(newsItemAdapter);
        newsItemAdapter.notifyDataSetChanged();
    }
}
