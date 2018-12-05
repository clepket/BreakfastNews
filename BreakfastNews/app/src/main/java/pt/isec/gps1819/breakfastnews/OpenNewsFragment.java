package pt.isec.gps1819.breakfastnews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */

public class OpenNewsFragment extends Fragment {

    private String title;
    private String image;
    private String subtitle;
    private String body;
    //private String journalist;

    private TextView mTitleTextView;
    //private TextView mImageTextView;
    private TextView mSubtitleTextView;
    private TextView mBodyTextView;


    //private TextView mJournalistTextView;

    public static OpenNewsFragment newInstance(NewsItem singleNews) {
        Bundle bundle = new Bundle();
        bundle.putString("title", singleNews.getTitle());
        bundle.putString("image", singleNews.getImage());
        bundle.putString("subtitle", singleNews.getDescription());
        bundle.putString("body", singleNews.getBody());
        //bundle.putString("journalist", singleNews.getJournalist());

        OpenNewsFragment fragment = new OpenNewsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            title = bundle.getString("title");
            image = bundle.getString("image");
            subtitle = bundle.getString("subtitle");
            body = bundle.getString("body");
            //journalist = bundle.getString("journalist");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_open_news, container, false);
        mTitleTextView = (TextView)view.findViewById(R.id.titleTextView);
        //titleTextView = (TextView)view.findViewById(R.id.imageImageView);
        mSubtitleTextView = (TextView)view.findViewById(R.id.subtitleTextView);
        mBodyTextView = (TextView)view.findViewById(R.id.bodyTextView);

        readBundle(getArguments());

        mTitleTextView.setText(String.format(title));
        mSubtitleTextView.setText(String.format(subtitle));
        mBodyTextView.setText(String.format(body));

        return view;
    }

}

