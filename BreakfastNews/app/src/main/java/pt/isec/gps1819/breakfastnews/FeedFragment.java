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
 * @since 2018-11-22
 * @version v1
 * @name FeedFragment.java
 */
public class FeedFragment extends Fragment implements Observer {
    private RecyclerView recyclerView;
    private NewsItemAdapter newsItemAdapter;
    private List<NewsItem> newsList;

    public FeedFragment() {
        newsList = new ArrayList<>();
    }

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

    private void prepareNews(){
        NewsSearch newsSearch = new NewsSearch(5);

        List<String> keywords = new ArrayList<>();
        List<String> jornalistas = new ArrayList<>();


        String buffer = ((MainActivity)getActivity()).readFile("perfil.txt");
        //keywords = ((MainActivity)getActivity()).devolveKeywords(buffer);
        //jornalistas = ((MainActivity)getActivity()).devolveJornalistas(buffer);

        keywords.add(" vai ");
        keywords.add(" a ");

        newsList.clear();
        newsList.addAll(newsSearch.findNews(keywords, jornalistas));
        if(newsList==null) {
            newsList.add(new NewsItem(
                    "Javier Zanetti: «Era capaz de levantar 500 quilos!»",
                    "https://cdn.record.pt/images/2018-11/img_920x518$2018_11_25_20_25_17_1476525.jpg",
                    "Blablabla",
                    "Antigo jogador recorda treinos de força que fazia nos tempos de jogador",
                    "Para os entendidos no mundo do fitness, nos treinos de ginásio em particular, aquilo que Javier Zanetti revelou este sábado é capaz de deixar muitos com uma pitada de inveja. Agora com 45 anos, e já afastado dos relvados há quatro, o antigo jogador do Inter Milão revelou que nos seus tempos áureos era uma verdadeira máquina de levantar pesos.\n" +
                            "\"Força sempre foi algo fundamental na minha carreira. Quando cheguei a Itália, em 1995, comecei a fazer muito trabalho desse género, tanto na equipa como por conta próprio. Um jogador com músculos fortes é menos propício a ter lesões e pode render mais\", explicou o atual vicepresidente do Inter, à Fox Sports Italia.\n" +
                            "\"Jogava sempre duas vezes por semana, mas mesmo assim nunca falhava o meu treino de força à segunda-feira. Mesmo estando cansado, era algo que me fazia sentir melhor no jogo seguinte. Fazia exercícios de peito e também fazia testes para perceber o peso que deveria levantar em cada momento da temporada. Era capaz de fazer 'leg press' com 500 quilos e com uma perna pegava nuns 310! Naturalmente que alternava esse trabalho com exercícios de explosão\", detalhou, lembrando depois o trabalho com José Mourinho no Inter Milão.\n" +
                            "\n" +
                            "\"O Mourinho, por exemplo, gostava muito de trabalhar com bola, com um treino de alta intensidade que envolvia muitas travagens e mudanças de direção. Acredito que no futebol atual tens de ter muito mais do que alguém que te oriente fisicamente. Precisas de uma pessoa que trabalhe noutras áreas, como por exemplo na força. Precisas de ter uma visão mais alargada\", disse o antigo internacional argentino.",
                    "https://www.google.pt/",
                    "Ricardo Reis",
                    "18/18/18"));
        }
        newsItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {

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
}
