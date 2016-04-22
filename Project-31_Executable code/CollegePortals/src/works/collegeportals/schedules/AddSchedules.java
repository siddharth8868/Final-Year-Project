package works.collegeportals.schedules;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import works.collegeportals.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddSchedules extends Activity{

	EditText subject,content;
	Spinner branch;
	DatePicker datepick;
	String url,pid,pbranch,ppass;
	Intent ii;
	ProgressDialog pd;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addschedules);
		subject=(EditText)findViewById(R.id.addschedulessubject);
		content=(EditText)findViewById(R.id.addschedulescontent);
		subject.requestFocus();
		content.requestFocus();
		branch=(Spinner)findViewById(R.id.addschedulesspinner);
		datepick=(DatePicker)findViewById(R.id.addschedulesdatePicker);
		ii=getIntent();
		pid=ii.getStringExtra("pid");
		ppass=ii.getStringExtra("ppass");
		pbranch=ii.getStringExtra("pbranch");
		url=getResources().getString(R.string.url);

	}

	
	public void save(View v){
		
		if(subject.getText().toString().equals("")){
			Toast.makeText(this,"subject cannot be empty",Toast.LENGTH_SHORT).show();
		}
		else{
			pd = new ProgressDialog(this);
			pd.setMessage("Loading. Please wait...");
			pd.show();
			
			final DownloadTask downloadTask = new DownloadTask(getApplicationContext());
			downloadTask.execute(url);

			pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
			    @Override
			    public void onCancel(DialogInterface dialog) {
			        downloadTask.cancel(true);
			    }
			});
		}
	}
	
	public String getDateToString(){
	    int day = datepick.getDayOfMonth();
	    int month = datepick.getMonth();
	    int year =  datepick.getYear();

	    month++;
	    return day+"-"+month+"-"+year;
	}
	
	public void clear(View v){
		subject.setText("");
		content.setText("");
	}
	

	

	//annonimus inner class
	class DownloadTask extends AsyncTask<String, Integer, String> {

	    private Context context;

	    public DownloadTask(Context context) {
	        this.context = context;
	    }

	    @SuppressWarnings("unused")
		@Override
	    protected String doInBackground(String... sUrl) {
	    	
	    	String output = null;
	        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
	             getClass().getName());
	        HttpClient httpclient = new DefaultHttpClient();
	    	HttpPost httppost = new HttpPost(url+"AddSchedulesAndroidServlet");

	    	try{
	    	    // Add your data
	    	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	    	    nameValuePairs.add(new BasicNameValuePair("id",pid));
	    	    nameValuePairs.add(new BasicNameValuePair("pass",ppass));
	    	    nameValuePairs.add(new BasicNameValuePair("pbranch",pbranch));
	    	    nameValuePairs.add(new BasicNameValuePair("subject",subject.getText().toString()));
	    	    nameValuePairs.add(new BasicNameValuePair("branch",branch.getSelectedItem().toString()));
	    	    nameValuePairs.add(new BasicNameValuePair("validity",getDateToString()));
	    	    nameValuePairs.add(new BasicNameValuePair("content",content.getText().toString()));
	    	    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    	    HttpResponse response = httpclient.execute(httppost);
	    	    HttpEntity httpEntity = response.getEntity();
	    	    output = EntityUtils.toString(httpEntity);
	    	}catch(Exception e)
	    	{
	    		
	    	}
	        return output;
	    }
	    
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        pd.dismiss();
	    	Log.e("result",result);
	    	if(result.equals("success"))
            {
    			Toast.makeText(context,"Successfully added",Toast.LENGTH_SHORT).show();
    			View v = null;
    			clear(v);
            }
            else{
    			Toast.makeText(context,"failed to add",Toast.LENGTH_SHORT).show();
            }
	    }
 
	}


	
}
