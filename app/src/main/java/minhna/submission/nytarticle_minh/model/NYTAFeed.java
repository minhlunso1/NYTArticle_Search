package minhna.submission.nytarticle_minh.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minh on 3/20/2016.
 */
public class NYTAFeed implements Parcelable {
    private String web_url;
    @SerializedName("multimedia")
    private List<Multimedia> multimediaList;
    private Headline headline;

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public List<Multimedia> getMultimediaList() {
        return multimediaList;
    }

    public void setMultimediaList(List<Multimedia> multimediaList) {
        this.multimediaList = multimediaList;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.web_url);
        dest.writeList(this.multimediaList);
        dest.writeParcelable(this.headline, flags);
    }

    public NYTAFeed() {
    }

    protected NYTAFeed(Parcel in) {
        this.web_url = in.readString();
        this.multimediaList = new ArrayList<Multimedia>();
        in.readList(this.multimediaList, Multimedia.class.getClassLoader());
        this.headline = in.readParcelable(Headline.class.getClassLoader());
    }

    public static final Creator<NYTAFeed> CREATOR = new Creator<NYTAFeed>() {
        @Override
        public NYTAFeed createFromParcel(Parcel source) {
            return new NYTAFeed(source);
        }

        @Override
        public NYTAFeed[] newArray(int size) {
            return new NYTAFeed[size];
        }
    };
}
