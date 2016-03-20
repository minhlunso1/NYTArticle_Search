package minhna.submission.nytarticle_minh.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import minhna.submission.nytarticle_minh.R;

/**
 * Created by Administrator on 20-Jan-16.
 */
public class NYTAFeedViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.content)
    RelativeLayout rlContent;
    @Bind(R.id.img_thumbnail)
    ImageView imgThumbnail;
    @Bind(R.id.ic_share)
    ImageView icShare;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    public NYTAFeedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
