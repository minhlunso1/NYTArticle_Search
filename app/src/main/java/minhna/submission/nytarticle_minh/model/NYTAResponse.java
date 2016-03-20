package minhna.submission.nytarticle_minh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Minh on 3/20/2016.
 */
public class NYTAResponse {
    @SerializedName("docs")
    List<NYTAFeed> feeds;

    public List<NYTAFeed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<NYTAFeed> feeds) {
        this.feeds = feeds;
    }
}
