package minhna.submission.nytarticle_minh.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Minh on 3/20/2016.
 */
public class Multimedia implements Parcelable {
    private String url;

    public String getUrl() {
        return "http://www.nytimes.com/"+url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public Multimedia() {
    }

    protected Multimedia(Parcel in) {
        this.url = in.readString();
    }

    public static final Creator<Multimedia> CREATOR = new Creator<Multimedia>() {
        @Override
        public Multimedia createFromParcel(Parcel source) {
            return new Multimedia(source);
        }

        @Override
        public Multimedia[] newArray(int size) {
            return new Multimedia[size];
        }
    };
}
