package pt.isec.gps1819.breakfastnews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNewsFragment extends Fragment {

    private NewsItem singleNews;

    public OpenNewsFragment(NewsItem singleNews) {
        this.singleNews = singleNews;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_news, container, false);
    }

}
