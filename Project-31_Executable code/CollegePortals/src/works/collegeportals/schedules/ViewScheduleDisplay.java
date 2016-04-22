package works.collegeportals.schedules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import works.collegeportals.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewScheduleDisplay extends Activity {
	
	String sub,ondate,vdate,content,link,uploader,add;
	TextView tvsub,tvondate,tvvdate,tvcontent,tvlink;
	WebView wb;
	Button but;
	String html;
	SQLiteDatabase db;
	ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewschedulesisplay1);

		wb=(WebView)findViewById(R.id.viewscheduledispaly1webView);
		but=(Button)findViewById(R.id.viewscheduledispaly1button);
		mProgressDialog = new ProgressDialog(getDialogContext());
		mProgressDialog.setMessage("A message");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);

		
		Intent i=getIntent();	
		sub=i.getStringExtra("subject");
		ondate = i.getStringExtra("ondate") ;
		vdate = i.getStringExtra("vdate");
		content=i.getStringExtra("content");
		link=i.getStringExtra("link");
		uploader=i.getStringExtra("uploader");
		if(!(link.equals("-"))){
			but.setVisibility(View.VISIBLE);
		}
		
		createHtml();
		Log.d("pp", ""+i.getStringExtra("sub"));
		sub=i.getStringExtra("subject");
		ondate=i.getStringExtra("ondate");
		vdate=i.getStringExtra("vdate");
		content=i.getStringExtra("content");
		//add=getResources().getString(R.string.url1)+"schedules/"+i.getStringExtra("link");
		add=getResources().getString(R.string.url1)+"schedules/"+link;
		//add="http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
		Log.d("add",add);
		
	}
	
	private void createHtml() {
		html="<body>  <h2 align=\"center\"; style=\"color:blue\">"+sub+"</h2><h4>"+uploader+"</h4><h4>"+ondate+"</h4><h4 align=\"right\">"+vdate+"</h4><br>"+content+"</body>";
		wb.loadData(html,"text/html","");
		createFiles();
	}
	
	private Context getDialogContext() {
	    Context context;
	    if (getParent() != null) context = getParent();
	    else context = this;
	    return context;
	}


	
public void downloads(View v){
		
		final DownloadTask downloadTask = new DownloadTask(getApplicationContext());
		downloadTask.execute(add);

		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
		    @Override
		    public void onCancel(DialogInterface dialog) {
		        downloadTask.cancel(true);
		    }
		});
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
	                
	                output = new FileOutputStream(Environment.getExternalStorageDirectory()+"/CollegePortal/schedules/"+link);

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
        direct = new File(Environment.getExternalStorageDirectory()+"/CollegePortal/schedules");
        if(!direct.exists()){
            if(direct.mkdir()){
            	Log.d("cre","sched");
            }
        }
        
        db=SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory()+"/CollegePortal/schedules/schedules.db",null);
        db.execSQL("create table if not exists "
				+"schedules"
				+"(id INTEGER PRIMARY KEY AUTOINCREMENT,subject text unique,link text)");
        

	}

	public void saveInDatabase() {
		
		SQLiteStatement st;
		st=db.compileStatement("insert into schedules values(?,?,?)");
		st.bindString(2,sub);
		st.bindString(3,link);
		try{
			st.executeInsert();
			openOnComplete();
		}
		catch (Exception e){
			e.printStackTrace();
			Log.d("error",""+e.getMessage());
		}

	}
	
	
	public void openOnComplete(){
		File file = new File(Environment.getExternalStorageDirectory()+"/CollegePortal/schedules/"+link);
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

	

}



