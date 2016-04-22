 package works.collegeportals.forums;

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
import android.widget.TextView;
import android.widget.Toast;

public class AddForums extends Activity{
	
	TextView title,validity;
	EditText subject,content;
	DatePicker dp;
	Spinner branch;
	String url,pid,ppass;
	Intent ii;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addschedules);
		title=(TextView)findViewById(R.id.addscheduletitle);
		validity=(TextView)findViewById(R.id.addschedulesdatepickertextview);
		dp=(DatePicker)findViewById(R.id.addschedulesdatePicker);
		title.setText("ADD FORUM");
		validity.setVisibility(View.GONE);
		dp.setVisibility(View.GONE);
		ii=getIntent();
		pid=ii.getStringExtra("pid");
		ppass=ii.getStringExtra("ppass");
		subject=(EditText)findViewById(R.id.addschedulessubject);
		content=(EditText)findViewById(R.id.addschedulescontent);
		branch=(Spinner)findViewById(R.id.addschedulesspinner);
		url=getResources().getString(R.string.url);

	}
	
	
	public void clear(View v){
		subject.setText("");
		content.setText("");
	}

	public void save(View v){
		if(subject.getText().toString().equals("")){
			Toast.makeText(this,"subject cannot be empty",Toast.LENGTH_SHORT).show();
		}
		else{
			pd = new ProgressDialog(this);
			pd.setMessage("Loading. Please wait...");
			pd.show();
			
			final AddForumServ downloadTask = new AddForumServ(getApplicationContext());
			downloadTask.execute(url);

			pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
			    @Override
			    public void onCancel(DialogInterface dialog) {
			        downloadTask.cancel(true);
			    }
			});
		}
	}

	
	//annonimus inner class
		class AddForumServ extends AsyncTask<String, Integer, String> {

		    private Context context;

		    public AddForumServ(Context context) {
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
		    	HttpPost httppost = new HttpPost(url+"AddForumAndoridServlet");

		    	try{
		    	    // Add your data
		    	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    	    nameValuePairs.add(new BasicNameValuePair("id",pid));
		    	    nameValuePairs.add(new BasicNameValuePair("pass",ppass ));
		    	    nameValuePairs.add(new BasicNameValuePair("subject",subject.getText().toString()));
		    	    nameValuePairs.add(new BasicNameValuePair("branch",branch.getSelectedItem().toString()));
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
	            else if(result.equals("error")){
	    			Toast.makeText(context,"failed to add, some problem at server",Toast.LENGTH_SHORT).show();
	            }
	            else if(result.equals("loginfailed")){
	    			Toast.makeText(context,"login failed",Toast.LENGTH_SHORT).show();
	            }
		    }
	 
		}


}
