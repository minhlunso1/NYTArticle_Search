package minhna.submission.nytarticle_minh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import minhna.submission.nytarticle_minh.model.NYTAFeed;

/**
 * Created by Administrator on 23-Nov-15.
 */
public class WebviewActivity extends AppCompatActivity {
    @Bind(R.id.webView)
    WebView webview;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        progressDialog = ProgressDialog.show(this, "Wait", "loading", false, true);
        NYTAFeed feed = intent.getParcelableExtra("parcelable");
        webview.loadUrl(feed.getWeb_url());
        webview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
