package works.collegeportals.forums;

import works.collegeportals.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DisplayForums extends Activity {
	
	WebView wv;
	Intent i;
	String url,pid,ppass,pbranch,pposition,branch;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		pd = new ProgressDialog(this);
		pd.setMessage("Loading. Please wait...");
		wv=(WebView)findViewById(R.id.webviewwebView);
		wv.getSettings().setJavaScriptEnabled(true); 
		wv.setWebViewClient(new ourViewClient());
		wv.setWebChromeClient(new WebChromeClient());
		i=getIntent();
		pid= i.getStringExtra("pid");
		branch=i.getStringExtra("branch");
		url=getResources().getString(R.string.url);
		setpage();
	}
	
	
	public void setpage(){
		wv.loadUrl(url+"displayforumsandroid.jsp?id="+pid+"&branch="+branch);
		Log.e("url",url+"displayforumsandroid.jsp?id="+pid+"&branch="+branch);
		
	}
	
	
	public class ourViewClient extends WebViewClient {
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			wv.loadUrl("file:///android_asset/errorpage.html");
		}
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			pd.show();
		}
		
		public void onPageFinished(WebView view, String url) {
			pd.dismiss();
		}

	}
	
	@Override
	public void onBackPressed()
	{
	    if(wv.canGoBack())
	        wv.goBack();
	    else
	        super.onBackPressed();
	}
}
