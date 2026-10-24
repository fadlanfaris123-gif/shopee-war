package com.shopee.war;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;
import java.io.FileWriter;
import java.io.File;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView web = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.setWebViewClient(new WebViewClient());
        web.addJavascriptInterface(new WebAppInterface(this), "Android");
        web.loadUrl("file:///android_asset/control.html");
        setContentView(web);
    }

    public class WebAppInterface {
        Activity mContext;
        WebAppInterface(Activity c) { this.mContext = c; }

        @JavascriptInterface
        public void simpanKonfigurasi(String jsonStr) {
            try {
                File file = new File("/sdcard/config_shopee.json");
                FileWriter writer = new FileWriter(file);
                writer.write(jsonStr);
                writer.close();
                Toast.makeText(mContext, "Konfigurasi disimpan! Jalankan Termux.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(mContext, "Gagal: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
