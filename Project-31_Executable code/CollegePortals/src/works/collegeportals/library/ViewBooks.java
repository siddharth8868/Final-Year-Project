package works.collegeportals.library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import works.collegeportals.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ViewBooks extends Activity{
	
	WebView wv;
	String url,dwlink,fname;
	SQLiteDatabase db;
	Cursor cr;
	ProgressDialog mProgressDialog;
	DateFormat dateFormat;
	Date cdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewbooks);
		wv=(WebView)findViewById(R.id.viewbookswebView);
		wv.setWebViewClient(new ourViewClient());
		url=getResources().getString(R.string.url);
		dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
		cdate = new Date();
		fname=dateFormat.format(cdate);
		mProgressDialog = new ProgressDialog(getDialogContext());
		mProgressDialog.setMessage("Loading...");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);
		setpage();
		 wv.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype, long contentLength) {
				dwlink=url;
				createFiles();
				if(alreadyExists())
				{
					Toast.makeText(getApplicationContext(),"book already available in local lbrary",Toast.LENGTH_SHORT).show();
				}
				else{
				//Toast.makeText(getApplicationContext(),"haha="+url,100).show();
				Log.e("error",url);
				final DownloadTask downloadTask = new DownloadTask(getApplicationContext());
				downloadTask.execute(url);

				mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				    @Override
				    public void onCancel(DialogInterface dialog) {
				        downloadTask.cancel(true);
				    }
				});
				
				} //end of else
			
			}
		});
	}
	
	protected boolean alreadyExists() {
		String title=dwlink.substring(dwlink.lastIndexOf("/")+1,dwlink.lastIndexOf(".")).replace("%20"," ");
		cr = db.rawQuery("select id from books where title='"+title+"'",null);
		if (!cr.moveToNext()) {
			//Toast.makeText(getApplicationContext(),"cr mempty",100).show();
			return false;
		}
		return true;
	}

	public void setpage(){
		wv.loadUrl(url+"displaybooksandroid.jsp");
	}

	private Context getDialogContext() {
	    Context context;
	    if (getParent() != null) context = getParent();
	    else context = this;
	    return context;
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
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
	}
	
	//annonimus inner class
	class DownloadTask extends AsyncTask<String, Integer, String> {

	    private Context context;

	    public DownloadTask(Context context) {
	        this.context = context;
	    }

	    @SuppressWarnings("resource")
		@Override
	    protected String doInBackground(String... sUrl) {
	        // take CPU lock to prevent CPU from going off if the user 
	        // presses the power button during download
	        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
	             getClass().getName());
	        wl.acquire();

	        try {
	            InputStream input = null;
	            OutputStream output = null;
	            HttpURLConnection connection = null;
	            try {
	                URL url = new URL(sUrl[0]);
	                connection = (HttpURLConnection) url.openConnection();
	                connection.connect();

	                // expect HTTP 200 OK, so we don't mistakenly save error report 
	                // instead of the file
	                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
	                     return "Server returned HTTP " + connection.getResponseCode() 
	                         + " " + connection.getResponseMessage();

	                // this will be useful to display download percentage
	                // might be -1: server did not report the length
	                int fileLength = connection.getContentLength();

	                // download the file
	                input = connection.getInputStream();
	                Log.e("dwlink",dwlink);
	                Log.e("filename",dwlink.substring(dwlink.lastIndexOf("/")+1).replace("%20"," "));
	                output = new FileOutputStream(Environment.getExternalStorageDirectory()+"/CollegePortal/library/"+dwlink.substring(dwlink.lastIndexOf("/")+1).replace("%20"," "));

	                byte data[] = new byte[4096];
	                long total = 0;
	                int count;
	                while ((count = input.read(data)) != -1) {
	                    // allow canceling with back button
	                    if (isCancelled())
	                        return null;
	                    total += count;
	                    // publishing the progress....
	                    if (fileLength > 0) // only if total length is known
	                        publishProgress((int) (total * 100 / fileLength));
	                    output.write(data, 0, count);
	                }
	            } catch (Exception e) {
	                return e.toString();
	            } finally {
	                try {
	                    if (output != null)
	                        output.close();
	                    if (input != null)
	                        input.close();
	                } 
	                catch (IOException ignored) { }

	                if (connection != null)
	                    connection.disconnect();
	            }
	        } finally {
	            wl.release();
	        }
	        return null;
	    }
	    
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mProgressDialog.show();
	    }

	    @Override
	    protected void onProgressUpdate(Integer... progress) {
	        super.onProgressUpdate(progress);
	        // if we get here, length is known, now set indeterminate to false
	        mProgressDialog.setIndeterminate(false);
	        mProgressDialog.setMax(100);
	        mProgressDialog.setProgress(progress[0]);
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        mProgressDialog.dismiss();
	        if (result != null){
	            Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
	    	}
	        else
	            Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
            	saveInDatabase();

	    }

	}


	public void createFiles() {
		
		File direct = new File(Environment.getExternalStorageDirectory() + "/CollegePortal");
        if(!direct.exists()){
             if(direct.mkdir()){
            	 Log.d("cre","coll");
             }
        }
        direct = new File(Environment.getExternalStorageDirectory()+"/CollegePortal/library");
        if(!direct.exists()){
            if(direct.mkdir()){
            	Log.d("cre","sched");
            }
        }
        
        db=SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory()+"/CollegePortal/library/books.db",null);
        db.execSQL("create table if not exists "
				+"books"
				+"(id INTEGER PRIMARY KEY AUTOINCREMENT,title text UNIQUE unique,link text)");
        

	}

	public void saveInDatabase() {
		   
		SQLiteStatement st;
		st=db.compileStatement("insert into books values(?,?,?)");
		
		st.bindString(2,dwlink.substring(dwlink.lastIndexOf("/")+1,dwlink.lastIndexOf(".")).replace("%20"," "));
		Log.e("decide",dwlink.substring(dwlink.lastIndexOf("/")+1).replace("%20"," "));
		st.bindString(3,dwlink.substring(dwlink.lastIndexOf("/")+1).replace("%20"," "));
		try{
			st.executeInsert();
		}
		catch (Exception e){
			e.printStackTrace();
			Log.d("error",""+e.getMessage());
		}

	}
	
	public void openOnComplete(){
		File file = new File(Environment.getExternalStorageDirectory()+"/CollegePortal/library/"+dwlink.substring(dwlink.lastIndexOf("/")+1).replace("%20"," "));
		MimeTypeMap map = MimeTypeMap.getSingleton();
	    String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
	    String type = map.getMimeTypeFromExtension(ext);

	    if (type == null){
	        type = "*/*";
	    }

	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    Uri data = Uri.fromFile(file);
	    intent.setDataAndType(data, type);
	    try{
	    startActivity(intent);
	    }
	    catch(Exception e){
	    	Toast.makeText(getApplicationContext(),"error="+e.getMessage(),100).show();
	    	e.printStackTrace();
	    }
	}
	
	public void close(View v){
		this.finish();
	}
	
}
