package pt.isec.gps1819.breakfastnews;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class OpenNewsFragment extends Fragment {

    private static Bundle bundle = new Bundle();
    private String title;
    private boolean favourite;
    private String date;
    private String image;
    private String subtitle;
    private String body;
    private String journalist;

    private TextView mTitleTextView;
    private ImageView mStarImageView;
    private TextView mDateTextView;
    private TextView mSubtitleTextView;
    private TextView mBodyTextView;
    private TextView mJournalistTextView;

    private List<NewsItem> favouriteNewsList;

    public static OpenNewsFragment newInstance(NewsItem singleNews) {
        bundle.putString("title", singleNews.getTitle());
        bundle.putString("date", singleNews.getDate());
        bundle.putString("image", singleNews.getImage());
        bundle.putString("subtitle", singleNews.getDescription());
        bundle.putString("body", singleNews.getBody());
        bundle.putString("journalist", singleNews.getJournalist());
        bundle.putBoolean("favourite", singleNews.isFavourite());

        OpenNewsFragment fragment = new OpenNewsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            if(bundle.getString("title")== null)
                title = "";
            else
                title = bundle.getString("title");

            if(bundle.getString("date")== null)
                date = "";
            else
                date = bundle.getString("date");

            if(bundle.getString("image")== null)
                image = "";
            else
                image = bundle.getString("image");

            favourite = bundle.getBoolean("favourite");

            if(bundle.getString("subtitle")== null)
                subtitle = "";
            else
                subtitle = bundle.getString("subtitle");

            if(bundle.getString("body")== null)
                body = "";
            else
                body = bundle.getString("body");

            if(bundle.getString("journalist")== null)
                journalist = "";
            else
                journalist = bundle.getString("journalist");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_open_news, container, false);
        mTitleTextView = (TextView)view.findViewById(R.id.titleTextView);
        mDateTextView = (TextView)view.findViewById(R.id.dateTextView);
        mSubtitleTextView = (TextView)view.findViewById(R.id.subtitleTextView);
        mBodyTextView = (TextView)view.findViewById(R.id.bodyTextView);
        mJournalistTextView = (TextView)view.findViewById(R.id.journalistTextView);

        readBundle(getArguments());

        mTitleTextView.setText(String.format(title));
        mDateTextView.setText(String.format(date));
        mSubtitleTextView.setText(String.format(subtitle));
        mBodyTextView.setText(String.format(body));
        mJournalistTextView.setText(String.format(journalist));

        Glide.with(getContext())
                .load(image)
                .error(R.mipmap.ic_launcher)
                .into((ImageView) view.findViewById(R.id.imageImageView));

        if(favourite) {
            mStarImageView =(ImageView) view.findViewById(R.id.starImageView);
            mStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_yellow_24dp, null));

            favouriteNewsList = new ArrayList<>();
            NewsItem favouriteNews = new NewsItem(title, image, "", subtitle, body, "", journalist, date);
            favouriteNewsList.add(favouriteNews);


            try {
                FileOutputStream fos = getContext().openFileOutput("favourites", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(favouriteNewsList);
                oos.close();

                Toast.makeText( getContext(), "Saved to " + getContext().getFilesDir() + "/" + "favourites", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText( getContext(), "Error writing in file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }
        else if(!favourite) {
            mStarImageView =(ImageView) view.findViewById(R.id.starImageView);
            mStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_black_24dp, null));
        }


        mStarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!bundle.getBoolean("favourite")) {
                    bundle.putBoolean("favourite", true);

                    Fragment frg = getFragmentManager().findFragmentById(R.id.drawer_layout);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();
                }
                else if(bundle.getBoolean("favourite")) {
                    bundle.putBoolean("favourite", false);

                    Fragment frg = getFragmentManager().findFragmentById(R.id.drawer_layout);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();
                }

            }
        });

        return view;
    }

}

