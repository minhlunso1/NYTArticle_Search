package minhna.submission.nytarticle_minh;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import minhna.submission.nytarticle_minh.model.NYTAFeed;
import minhna.submission.nytarticle_minh.model.NYTAResponse;
import minhna.submission.nytarticle_minh.net.MyHttp;
import minhna.submission.nytarticle_minh.recyclerview.EndlessScrollListener;
import minhna.submission.nytarticle_minh.recyclerview.NYTAFeedAdapter;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    MainActivity activity;
    AsyncHttpClient client;
    Gson gson;
    NYTAFeedAdapter adapter;
    List<NYTAFeed> list;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    public static int queryPage = 1;
    private String keyword;
    private MyHttp myHttp;
    public static boolean isLoadingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        activity = this;
        myHttp = new MyHttp();
        client = new AsyncHttpClient();
        gson = new GsonBuilder().create();
        list = new ArrayList<>();
        adapter = new NYTAFeedAdapter(activity, list);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                progressBar.setVisibility(View.VISIBLE);
                RequestParams params = new RequestParams();
                params.put("api-key", MyHttp.API_KEY);
                params.put("q", keyword);
                params.put("page", queryPage);
                addMoreParams(params);
                client.get(MyHttp.NYT_URL, params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONObject object = response.getJSONObject("response");
                            NYTAResponse feeds = gson.fromJson(object.toString(), NYTAResponse.class);
                            list.addAll(feeds.getFeeds());
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                            isLoadingItem=false;
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            isLoadingItem=false;
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void addMoreParams(RequestParams params) {
        if (MyHttp.begin_date!=null)
            params.add("begin_date", MyHttp.begin_date);
        params.add("sort", MyHttp.sort_mode);
        if (MyHttp.buildFilterQuery()!=null)
            params.add("fq", MyHttp.buildFilterQuery());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (list!=null) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
                MainActivity.queryPage=1;
                if (myHttp.isNetworkAvailable(activity) && myHttp.isOnline()) {
                    progressBar.setVisibility(View.VISIBLE);
                    keyword = query;
                    RequestParams params = new RequestParams();
                    params.put("api-key", MyHttp.API_KEY);
                    params.put("q", keyword);
                    params.put("page", queryPage);
                    addMoreParams(params);
                    client.get(MyHttp.NYT_URL, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                JSONObject object = response.getJSONObject("response");
                                NYTAResponse feeds = gson.fromJson(object.toString(), NYTAResponse.class);
                                list.addAll(feeds.getFeeds());
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (JSONException e) {
                                progressBar.setVisibility(View.INVISIBLE);
                                e.printStackTrace();
                            }
                        }
                    });
                } else
                    Toast.makeText(activity, "Cannot access the internet", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SettingDialogFragment.newInstance().show(getSupportFragmentManager(), "setting_dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
