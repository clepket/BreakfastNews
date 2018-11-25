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


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsItemAdapter newsItemAdapter;
    private List<NewsItem> newsList;

    public FeedFragment() {
        newsList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

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
        newsList.add(new NewsItem(
                "A perigosa missão de resgatar o corpo do missionário morto pelos Sentinela",
                "https://static.globalnoticias.pt/jn/image.aspx?brand=JN&type=generate&guid=48beccea-98c2-4fe2-9c6b-9dcd01f77722&t=20181125213755",
                "A polícia indiana está a tentar resgatar o corpo do jovem norte-americano, que terá sido assassinado por membros de uma tribo, na passada semana. Os Sentinela não estão a facilitar a aproximação.",
                "As autoridades conseguiram chegar a 400 metros da ilha de Sentinela, onde vivem os Sentinela, uma tribo conhecida por não facilitar o contacto com o exterior e que, na passada semana, terá assassinado John Allen Chau, um missionário norte-americano, que quebrou as regras que impedem que estrangeiros se aproximem do local.\n" +
                        "\n" +
                        "\"Eles ficaram a olhar para nós e nós tivemos a oportunidade de os observar\", disse, citado pela \"Sky News\", Dependra Pathak, da polícia das Ilhas de Andamão e Nicobar. O barco que transportava as autoridades teve mesmo que dar meia volta para evitar confrontos com os membros da tribo.\n" +
                        "\n" +
                        "Seis pescadores e uma outra pessoa foram detidos, depois de a polícia ter confirmado que foram pagos por John para o levar até perto da ilha. \"Conseguimos mapear a área com a ajuda desses pescadores. Ainda não vimos o corpo, mas conhecemos a área em que se acredita que ele esteja enterrado\", disse o polícia à \"BBC\".\n" +
                        "\n" +
                        "Antes de as autoridades avançarem para mais uma tentativa de recuperar o corpo do estudante norte-americano, vão tentar \"estudar as nuances relacionadas com a conduta e o comportamento do grupo, particularmente neste tipo de situações de comportamentos violentos\".\n" +
                        "\n" +
                        "Sophie Grig, uma especialista que tem estudado a tribo, explicou à \"Sky\" que \"não acredita que exista uma possibilidade segura, tanto para a tribo como para as autoridades, de recuperarem o corpo\".\n" +
                        "\n" +
                        "A polícia está curiosa a tentar compreender se os membros da tribo repetem o mesmo comportamento de 2006, quando assassinaram dois pescadores ilegais que se aproximaram da costa da ilha. Uma semana depois das suas mortes, os corpos dos pescadores foram fixados a estacas de bambo virados para o mar. \"Criaram uma espécie de espantalho\", contou Dependra.\n" +
                        "\n" +
                        "Duplo dilema\n" +
                        "\n" +
                        "A polícia enfrenta agora um verdadeiro dilema. Enquanto os pais têm insistido com as autoridades para que o corpo do rapaz seja resgatado, vários especialistas alertam para as dificuldades da missão.\n" +
                        "\n" +
                        "Pankaj Sekhsaria, especialista em direitos tribais, admitiu que é praticamente impossível recuperar o corpo do norte-americano, acrescentando, ainda, que o melhor é mesmo não tentar chegar perto da ilha. \"Vai criar um conflito com a comunidade local\", disse, citado pela AFP.\n" +
                        "\n" +
                        "Também Anup Kapoor, antropologista da Universidade de Deli, explicou que para entrar em contacto com a tribo vai ser necessário demonstrar que \"estão todos na mesma linha\". \"Pouco se sabe sobre eles. O que sabemos é que foram mortos pelos ingleses e japoneses. Eles odeiam qualquer pessoa que se vista de uniforme. Se virem alguém assim, matam logo\", alertou. \"Deixem-nos estar, a eles e ao ambiente onde estão, em paz, ou eles ficarão mais agressivos.\"",
                "Carlos Pinho"));
        newsList.add(new NewsItem(
                "Homem aos tiros lança o pânico nas Caxinas",
                "https://static.globalnoticias.pt/jn/image.aspx?brand=JN&type=generate&guid=0e72a904-b96a-4d78-a7af-53df114bed7d&w=744&h=495&t=20181125175040",
                "A Polícia deteve, na tarde deste domingo, nas Caxinas, em Vila do Conde, um homem, de 47 anos, que lançou o pânico na Rua da Praia ao efetuar diversos disparos no interior de um prédio e, depois, na rua.",
                "O incidente ocorreu, cerca das 12 horas, na Rua da Praia, quando o homem, que estaria alcoolizado, entrou num prédio e desatou aos tiros contra o teto, lançando o pânico entre os moradores, que chamaram a Polícia.\n" +
                        "\n" +
                        "Entretanto, o homem saiu e andou pela rua a disparar para o ar, obrigando dezenas de pessoas, incluindo crianças, a procurarem refúgio em prédios ou estabelecimentos comerciais.\n" +
                        "\n" +
                        "O homem fugiu de carro, sendo perseguido pela PSP, que ainda efetuou um disparo de intimidação para o ar, embora ele tivesse prosseguido a fuga. Mas acabou por parar, sendo detido. Na sua posse tinha uma pistola do calibre 6.35, com quatro munições no carregador.",
                "Ricardo Reis"));
        newsList.add(new NewsItem(
                "A perigosa missão de resgatar o corpo do missionário morto pelos Sentinela",
                "https://static.globalnoticias.pt/jn/image.aspx?brand=JN&type=generate&guid=48beccea-98c2-4fe2-9c6b-9dcd01f77722&t=20181125213755",
                "A polícia indiana está a tentar resgatar o corpo do jovem norte-americano, que terá sido assassinado por membros de uma tribo, na passada semana. Os Sentinela não estão a facilitar a aproximação.",
                "As autoridades conseguiram chegar a 400 metros da ilha de Sentinela, onde vivem os Sentinela, uma tribo conhecida por não facilitar o contacto com o exterior e que, na passada semana, terá assassinado John Allen Chau, um missionário norte-americano, que quebrou as regras que impedem que estrangeiros se aproximem do local.\n" +
                        "\n" +
                        "\"Eles ficaram a olhar para nós e nós tivemos a oportunidade de os observar\", disse, citado pela \"Sky News\", Dependra Pathak, da polícia das Ilhas de Andamão e Nicobar. O barco que transportava as autoridades teve mesmo que dar meia volta para evitar confrontos com os membros da tribo.\n" +
                        "\n" +
                        "Seis pescadores e uma outra pessoa foram detidos, depois de a polícia ter confirmado que foram pagos por John para o levar até perto da ilha. \"Conseguimos mapear a área com a ajuda desses pescadores. Ainda não vimos o corpo, mas conhecemos a área em que se acredita que ele esteja enterrado\", disse o polícia à \"BBC\".\n" +
                        "\n" +
                        "Antes de as autoridades avançarem para mais uma tentativa de recuperar o corpo do estudante norte-americano, vão tentar \"estudar as nuances relacionadas com a conduta e o comportamento do grupo, particularmente neste tipo de situações de comportamentos violentos\".\n" +
                        "\n" +
                        "Sophie Grig, uma especialista que tem estudado a tribo, explicou à \"Sky\" que \"não acredita que exista uma possibilidade segura, tanto para a tribo como para as autoridades, de recuperarem o corpo\".\n" +
                        "\n" +
                        "A polícia está curiosa a tentar compreender se os membros da tribo repetem o mesmo comportamento de 2006, quando assassinaram dois pescadores ilegais que se aproximaram da costa da ilha. Uma semana depois das suas mortes, os corpos dos pescadores foram fixados a estacas de bambo virados para o mar. \"Criaram uma espécie de espantalho\", contou Dependra.\n" +
                        "\n" +
                        "Duplo dilema\n" +
                        "\n" +
                        "A polícia enfrenta agora um verdadeiro dilema. Enquanto os pais têm insistido com as autoridades para que o corpo do rapaz seja resgatado, vários especialistas alertam para as dificuldades da missão.\n" +
                        "\n" +
                        "Pankaj Sekhsaria, especialista em direitos tribais, admitiu que é praticamente impossível recuperar o corpo do norte-americano, acrescentando, ainda, que o melhor é mesmo não tentar chegar perto da ilha. \"Vai criar um conflito com a comunidade local\", disse, citado pela AFP.\n" +
                        "\n" +
                        "Também Anup Kapoor, antropologista da Universidade de Deli, explicou que para entrar em contacto com a tribo vai ser necessário demonstrar que \"estão todos na mesma linha\". \"Pouco se sabe sobre eles. O que sabemos é que foram mortos pelos ingleses e japoneses. Eles odeiam qualquer pessoa que se vista de uniforme. Se virem alguém assim, matam logo\", alertou. \"Deixem-nos estar, a eles e ao ambiente onde estão, em paz, ou eles ficarão mais agressivos.\"",
                "Carlos Pinho"));
        newsList.add(new NewsItem(
                "Homem aos tiros lança o pânico nas Caxinas",
                "https://static.globalnoticias.pt/jn/image.aspx?brand=JN&type=generate&guid=0e72a904-b96a-4d78-a7af-53df114bed7d&w=744&h=495&t=20181125175040",
                "A Polícia deteve, na tarde deste domingo, nas Caxinas, em Vila do Conde, um homem, de 47 anos, que lançou o pânico na Rua da Praia ao efetuar diversos disparos no interior de um prédio e, depois, na rua.",
                "O incidente ocorreu, cerca das 12 horas, na Rua da Praia, quando o homem, que estaria alcoolizado, entrou num prédio e desatou aos tiros contra o teto, lançando o pânico entre os moradores, que chamaram a Polícia.\n" +
                        "\n" +
                        "Entretanto, o homem saiu e andou pela rua a disparar para o ar, obrigando dezenas de pessoas, incluindo crianças, a procurarem refúgio em prédios ou estabelecimentos comerciais.\n" +
                        "\n" +
                        "O homem fugiu de carro, sendo perseguido pela PSP, que ainda efetuou um disparo de intimidação para o ar, embora ele tivesse prosseguido a fuga. Mas acabou por parar, sendo detido. Na sua posse tinha uma pistola do calibre 6.35, com quatro munições no carregador.",
                "Ricardo Reis"));
        newsList.add(new NewsItem(
                "A perigosa missão de resgatar o corpo do missionário morto pelos Sentinela",
                "https://static.globalnoticias.pt/jn/image.aspx?brand=JN&type=generate&guid=48beccea-98c2-4fe2-9c6b-9dcd01f77722&t=20181125213755",
                "A polícia indiana está a tentar resgatar o corpo do jovem norte-americano, que terá sido assassinado por membros de uma tribo, na passada semana. Os Sentinela não estão a facilitar a aproximação.",
                "As autoridades conseguiram chegar a 400 metros da ilha de Sentinela, onde vivem os Sentinela, uma tribo conhecida por não facilitar o contacto com o exterior e que, na passada semana, terá assassinado John Allen Chau, um missionário norte-americano, que quebrou as regras que impedem que estrangeiros se aproximem do local.\n" +
                        "\n" +
                        "\"Eles ficaram a olhar para nós e nós tivemos a oportunidade de os observar\", disse, citado pela \"Sky News\", Dependra Pathak, da polícia das Ilhas de Andamão e Nicobar. O barco que transportava as autoridades teve mesmo que dar meia volta para evitar confrontos com os membros da tribo.\n" +
                        "\n" +
                        "Seis pescadores e uma outra pessoa foram detidos, depois de a polícia ter confirmado que foram pagos por John para o levar até perto da ilha. \"Conseguimos mapear a área com a ajuda desses pescadores. Ainda não vimos o corpo, mas conhecemos a área em que se acredita que ele esteja enterrado\", disse o polícia à \"BBC\".\n" +
                        "\n" +
                        "Antes de as autoridades avançarem para mais uma tentativa de recuperar o corpo do estudante norte-americano, vão tentar \"estudar as nuances relacionadas com a conduta e o comportamento do grupo, particularmente neste tipo de situações de comportamentos violentos\".\n" +
                        "\n" +
                        "Sophie Grig, uma especialista que tem estudado a tribo, explicou à \"Sky\" que \"não acredita que exista uma possibilidade segura, tanto para a tribo como para as autoridades, de recuperarem o corpo\".\n" +
                        "\n" +
                        "A polícia está curiosa a tentar compreender se os membros da tribo repetem o mesmo comportamento de 2006, quando assassinaram dois pescadores ilegais que se aproximaram da costa da ilha. Uma semana depois das suas mortes, os corpos dos pescadores foram fixados a estacas de bambo virados para o mar. \"Criaram uma espécie de espantalho\", contou Dependra.\n" +
                        "\n" +
                        "Duplo dilema\n" +
                        "\n" +
                        "A polícia enfrenta agora um verdadeiro dilema. Enquanto os pais têm insistido com as autoridades para que o corpo do rapaz seja resgatado, vários especialistas alertam para as dificuldades da missão.\n" +
                        "\n" +
                        "Pankaj Sekhsaria, especialista em direitos tribais, admitiu que é praticamente impossível recuperar o corpo do norte-americano, acrescentando, ainda, que o melhor é mesmo não tentar chegar perto da ilha. \"Vai criar um conflito com a comunidade local\", disse, citado pela AFP.\n" +
                        "\n" +
                        "Também Anup Kapoor, antropologista da Universidade de Deli, explicou que para entrar em contacto com a tribo vai ser necessário demonstrar que \"estão todos na mesma linha\". \"Pouco se sabe sobre eles. O que sabemos é que foram mortos pelos ingleses e japoneses. Eles odeiam qualquer pessoa que se vista de uniforme. Se virem alguém assim, matam logo\", alertou. \"Deixem-nos estar, a eles e ao ambiente onde estão, em paz, ou eles ficarão mais agressivos.\"",
                "Carlos Pinho"));
        newsList.add(new NewsItem(
                "Homem aos tiros lança o pânico nas Caxinas",
                "https://static.globalnoticias.pt/jn/image.aspx?brand=JN&type=generate&guid=0e72a904-b96a-4d78-a7af-53df114bed7d&w=744&h=495&t=20181125175040",
                "A Polícia deteve, na tarde deste domingo, nas Caxinas, em Vila do Conde, um homem, de 47 anos, que lançou o pânico na Rua da Praia ao efetuar diversos disparos no interior de um prédio e, depois, na rua.",
                "O incidente ocorreu, cerca das 12 horas, na Rua da Praia, quando o homem, que estaria alcoolizado, entrou num prédio e desatou aos tiros contra o teto, lançando o pânico entre os moradores, que chamaram a Polícia.\n" +
                        "\n" +
                        "Entretanto, o homem saiu e andou pela rua a disparar para o ar, obrigando dezenas de pessoas, incluindo crianças, a procurarem refúgio em prédios ou estabelecimentos comerciais.\n" +
                        "\n" +
                        "O homem fugiu de carro, sendo perseguido pela PSP, que ainda efetuou um disparo de intimidação para o ar, embora ele tivesse prosseguido a fuga. Mas acabou por parar, sendo detido. Na sua posse tinha uma pistola do calibre 6.35, com quatro munições no carregador.",
                "Ricardo Reis"));
        newsList.add(new NewsItem(
                "A perigosa missão de resgatar o corpo do missionário morto pelos Sentinela",
                "https://static.globalnoticias.pt/jn/image.aspx?brand=JN&type=generate&guid=48beccea-98c2-4fe2-9c6b-9dcd01f77722&t=20181125213755",
                "A polícia indiana está a tentar resgatar o corpo do jovem norte-americano, que terá sido assassinado por membros de uma tribo, na passada semana. Os Sentinela não estão a facilitar a aproximação.",
                "As autoridades conseguiram chegar a 400 metros da ilha de Sentinela, onde vivem os Sentinela, uma tribo conhecida por não facilitar o contacto com o exterior e que, na passada semana, terá assassinado John Allen Chau, um missionário norte-americano, que quebrou as regras que impedem que estrangeiros se aproximem do local.\n" +
                        "\n" +
                        "\"Eles ficaram a olhar para nós e nós tivemos a oportunidade de os observar\", disse, citado pela \"Sky News\", Dependra Pathak, da polícia das Ilhas de Andamão e Nicobar. O barco que transportava as autoridades teve mesmo que dar meia volta para evitar confrontos com os membros da tribo.\n" +
                        "\n" +
                        "Seis pescadores e uma outra pessoa foram detidos, depois de a polícia ter confirmado que foram pagos por John para o levar até perto da ilha. \"Conseguimos mapear a área com a ajuda desses pescadores. Ainda não vimos o corpo, mas conhecemos a área em que se acredita que ele esteja enterrado\", disse o polícia à \"BBC\".\n" +
                        "\n" +
                        "Antes de as autoridades avançarem para mais uma tentativa de recuperar o corpo do estudante norte-americano, vão tentar \"estudar as nuances relacionadas com a conduta e o comportamento do grupo, particularmente neste tipo de situações de comportamentos violentos\".\n" +
                        "\n" +
                        "Sophie Grig, uma especialista que tem estudado a tribo, explicou à \"Sky\" que \"não acredita que exista uma possibilidade segura, tanto para a tribo como para as autoridades, de recuperarem o corpo\".\n" +
                        "\n" +
                        "A polícia está curiosa a tentar compreender se os membros da tribo repetem o mesmo comportamento de 2006, quando assassinaram dois pescadores ilegais que se aproximaram da costa da ilha. Uma semana depois das suas mortes, os corpos dos pescadores foram fixados a estacas de bambo virados para o mar. \"Criaram uma espécie de espantalho\", contou Dependra.\n" +
                        "\n" +
                        "Duplo dilema\n" +
                        "\n" +
                        "A polícia enfrenta agora um verdadeiro dilema. Enquanto os pais têm insistido com as autoridades para que o corpo do rapaz seja resgatado, vários especialistas alertam para as dificuldades da missão.\n" +
                        "\n" +
                        "Pankaj Sekhsaria, especialista em direitos tribais, admitiu que é praticamente impossível recuperar o corpo do norte-americano, acrescentando, ainda, que o melhor é mesmo não tentar chegar perto da ilha. \"Vai criar um conflito com a comunidade local\", disse, citado pela AFP.\n" +
                        "\n" +
                        "Também Anup Kapoor, antropologista da Universidade de Deli, explicou que para entrar em contacto com a tribo vai ser necessário demonstrar que \"estão todos na mesma linha\". \"Pouco se sabe sobre eles. O que sabemos é que foram mortos pelos ingleses e japoneses. Eles odeiam qualquer pessoa que se vista de uniforme. Se virem alguém assim, matam logo\", alertou. \"Deixem-nos estar, a eles e ao ambiente onde estão, em paz, ou eles ficarão mais agressivos.\"",
                "Carlos Pinho"));
        newsList.add(new NewsItem(
                "Homem aos tiros lança o pânico nas Caxinas",
                "https://static.globalnoticias.pt/jn/image.aspx?brand=JN&type=generate&guid=0e72a904-b96a-4d78-a7af-53df114bed7d&w=744&h=495&t=20181125175040",
                "A Polícia deteve, na tarde deste domingo, nas Caxinas, em Vila do Conde, um homem, de 47 anos, que lançou o pânico na Rua da Praia ao efetuar diversos disparos no interior de um prédio e, depois, na rua.",
                "O incidente ocorreu, cerca das 12 horas, na Rua da Praia, quando o homem, que estaria alcoolizado, entrou num prédio e desatou aos tiros contra o teto, lançando o pânico entre os moradores, que chamaram a Polícia.\n" +
                        "\n" +
                        "Entretanto, o homem saiu e andou pela rua a disparar para o ar, obrigando dezenas de pessoas, incluindo crianças, a procurarem refúgio em prédios ou estabelecimentos comerciais.\n" +
                        "\n" +
                        "O homem fugiu de carro, sendo perseguido pela PSP, que ainda efetuou um disparo de intimidação para o ar, embora ele tivesse prosseguido a fuga. Mas acabou por parar, sendo detido. Na sua posse tinha uma pistola do calibre 6.35, com quatro munições no carregador.",
                "Ricardo Reis"));

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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
