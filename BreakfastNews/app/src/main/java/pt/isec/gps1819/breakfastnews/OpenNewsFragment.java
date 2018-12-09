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
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
            title = bundle.getString("title");
            date = bundle.getString("date");
            image = bundle.getString("image");
            subtitle = bundle.getString("subtitle");
            body = bundle.getString("body");

            if(bundle.getString("journalist")== null)
                journalist = "";
            else
                journalist = bundle.getString("journalist");

            favourite = bundle.getBoolean("favourite");
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


            NewsItem favouriteNews = new NewsItem(title, image, "", subtitle, body, "", journalist, date);

            try {
                FileOutputStream fos = getContext().openFileOutput("favourites", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(favouriteNews);
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

