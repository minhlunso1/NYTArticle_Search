package minhna.submission.nytarticle_minh.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Minh on 3/20/2016.
 */
public class Headline implements Parcelable {
    private String main;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.main);
    }

    public Headline() {
    }

    protected Headline(Parcel in) {
        this.main = in.readString();
    }

    public static final Creator<Headline> CREATOR = new Creator<Headline>() {
        @Override
        public Headline createFromParcel(Parcel source) {
            return new Headline(source);
        }

        @Override
        public Headline[] newArray(int size) {
            return new Headline[size];
        }
    };
}
