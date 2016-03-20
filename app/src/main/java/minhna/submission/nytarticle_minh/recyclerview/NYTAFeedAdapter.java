package minhna.submission.nytarticle_minh.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import minhna.submission.nytarticle_minh.R;
import minhna.submission.nytarticle_minh.WebviewActivity;
import minhna.submission.nytarticle_minh.model.NYTAFeed;

/**
 * Created by Administrator on 13-Mar-16.
 */
public class NYTAFeedAdapter extends RecyclerView.Adapter<NYTAFeedViewHolder> {
    private List<NYTAFeed> list;
    private Context context;
    private int currentColor;
    private NYTAFeedClickListener listener;

    public NYTAFeedAdapter(Context context, List<NYTAFeed> list) {
        this.list = list;
        this.context = context;
    }

    public NYTAFeedAdapter(Context context, List<NYTAFeed> list, NYTAFeedClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public interface NYTAFeedClickListener {
        void onNYTAFeedClick(String NYTAFeedId);
    }

    @Override
    public NYTAFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);

        NYTAFeedViewHolder viewHolder = new NYTAFeedViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NYTAFeedViewHolder holder, int position) {
        final NYTAFeed item = list.get(position);

        if (item.getMultimediaList().size()==0)
            Picasso.with(context).load(R.drawable.placeholder2).into(holder.imgThumbnail);
        else {
            Picasso.with(context)
                    .load(item.getMultimediaList().get(0).getUrl())
                    .error(R.drawable.placeholder2)
                    .placeholder(R.drawable.placeholder2)
                    .into(holder.imgThumbnail);
        }
        holder.tvTitle.setText(item.getHeadline().getMain());

        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("parcelable", item);
                context.startActivity(intent);
            }
        });
        holder.icShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                share.putExtra(Intent.EXTRA_TEXT, item.getWeb_url());
                context.startActivity(Intent.createChooser(share, "Make a wish"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

