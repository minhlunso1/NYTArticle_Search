package minhna.submission.nytarticle_minh.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minh on 3/20/2016.
 */
public class MyHttp {
//    http://api.nytimes.com/svc/search/v2/articlesearch.json?q=new+york+times&page=2&sort=oldest&api-key=####
//    http://api.nytimes.com/svc/search/v2/articlesearch.json?fq=romney&facet_field=day_of_week&begin_date=20120101&end_date=20120101&api-key=####
    public static final String NYT_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    public static final String API_KEY = "744a2d6b8214544ea10d64549657e7b4:10:74749717";
    public static String begin_date;
    public static String sort_mode="newest";
    public static boolean isArtChecked;
    public static boolean isFSChecked;
    public static boolean isSportChecked;

    public static String buildFilterQuery(){
        List<String> chosenDesk = new ArrayList<>();
        if (isArtChecked)
            chosenDesk.add("arts");
        if (isFSChecked)
            chosenDesk.add("fashion &amp; style");
        if (isSportChecked)
            chosenDesk.add("sports");
        if (chosenDesk.size()==0)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (String tmp:chosenDesk)
            stringBuilder.append(tmp).append(" ");

        return stringBuilder.toString();
    }

    public Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (Exception e)          { e.printStackTrace(); }
        return false;
    }
}
