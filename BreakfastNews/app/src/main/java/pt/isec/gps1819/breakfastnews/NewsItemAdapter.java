package pt.isec.gps1819.breakfastnews;


import android.content.Context;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;



/**
 * Esta classe tem a função de criar um adaptador
 * entre a <b>RecyclerView</b> e o <i>dataset</i> NewsItem.
 *
 * @author Carlos Pinho
 * @version v1
 * @since 2018-11-25
 * @name NewsItemAdapter.java
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.MyViewHolder> {
    private Context mContext;
    private List<NewsItem> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder{ 
        public TextView newsTitle, newsReadMore;
        public ImageView newsImage;
        public CardView cardViewItem;

        public MyViewHolder(View view){
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.newsTitle);
            newsReadMore = (TextView) view.findViewById(R.id.newsReadMore);
            newsImage = (ImageView) view.findViewById(R.id.newsImage);
            cardViewItem = (CardView) view.findViewById(R.id.cardViewItem);
        }
    }

    public NewsItemAdapter(Context mContext, List<NewsItem> newsList){
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final NewsItem newsItem = newsList.get(position);
        holder.newsTitle.setText(newsItem.getTitle());

        Glide.with(mContext)
                .load(newsItem.getImage())
                .error(R.mipmap.ic_launcher)
                .into(holder.newsImage);


        holder.newsReadMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Fragment fragment = OpenNewsFragment.newInstance(newsItem);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, fragment).addToBackStack(null).commit();

//                switch(view.getId()) {
//                    case R.id.newsReadMore:
//
//                        FragmentTransaction fragmentTransaction = myActivity.getSupportFragmentManager().beginTransaction();
//                        myActivity.getSupportFragmentManager().beginTransaction().add(R.id.openNewsFrame, fragment);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}