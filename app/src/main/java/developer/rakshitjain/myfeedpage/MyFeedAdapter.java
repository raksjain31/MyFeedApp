package developer.rakshitjain.myfeedpage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedAdapter.FeedViewHolder> {
    private ArrayList<FeedList> mfeedList;
    Context context;
    public static class FeedViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public ImageView imageView;
        public TextView name;
        public TextView date;
        public TextView textViews;
        public TextView textLikes;
        public TextView textShare;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.txtid);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.txtimagename);
            date = itemView.findViewById(R.id.textDate);
            textViews  = itemView.findViewById(R.id.textviews);
            textLikes = itemView.findViewById(R.id.textlikes);
            textShare = itemView.findViewById(R.id.textshare);

        }
    }
        //Contructor
    public MyFeedAdapter(Context c,ArrayList<FeedList> feedList){
        context = c;
        mfeedList = feedList;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list,parent,false);
        FeedViewHolder feedViewHolder = new FeedViewHolder(view);
        return feedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int i) {
        FeedList currentItem = mfeedList.get(i);
        feedViewHolder.id.setText(currentItem.getId());
        String imgUrl = currentItem.getThumbnail_image();
        Picasso.get()
                .load(imgUrl)
                .resize(400, 200)
                .centerCrop()
                .into(feedViewHolder.imageView);


        feedViewHolder.name.setText(currentItem.getEvent_name());
        feedViewHolder.date.setText(currentItem.getEvent_date());
        feedViewHolder.textViews.setText(currentItem.getViews());
        feedViewHolder.textLikes.setText(currentItem.getLikes());
        feedViewHolder.textShare.setText(currentItem.getShares());
    }


    @Override
    public int getItemCount() {
        return mfeedList.size();
    }


}
