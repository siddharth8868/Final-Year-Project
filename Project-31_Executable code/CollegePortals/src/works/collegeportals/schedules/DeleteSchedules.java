package works.collegeportals.schedules;

import works.collegeportals.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DeleteSchedules extends Activity{
	
	WebView wv;
	String url,pid,pbranch,pposition;
	ProgressDialog pd;
	Intent i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		wv=(WebView)findViewById(R.id.webviewwebView);
		wv.getSettings().setJavaScriptEnabled(true); 
		wv.setWebViewClient(new ourViewClient());
		wv.setWebChromeClient(new WebChromeClient());
		i=getIntent();
		pid=i.getStringExtra("pid");
		pbranch=i.getStringExtra("pbranch");
		pposition=i.getStringExtra("pposition");
		url=getResources().getString(R.string.url);
		setpage();
	}
	
	public void setpage(){
		pd = ProgressDialog.show(this, "","Loading. Please wait...", true);
		wv.loadUrl(url+"deleteschedulesandroid.jsp?id="+pid+"&pbranch="+pbranch+"&position="+pposition);
		Log.e("url",url+"deleteschedulesandroid.jsp?id="+pid+"&pbranch="+pbranch+"&position="+pposition);
		
	}
	
	
	
	
	
	

	
	public class ourViewClient extends WebViewClient {
		@Override
		public void onLoadResource(WebView view, String url) {
			//view.clearHistory();
			super.onLoadResource(view, url);
		}
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			wv.loadUrl("file:///android_asset/errorpage.html");
		}
		
		public void onPageFinished(WebView view, String url) {
			pd.dismiss();
		}

	}
	
}
