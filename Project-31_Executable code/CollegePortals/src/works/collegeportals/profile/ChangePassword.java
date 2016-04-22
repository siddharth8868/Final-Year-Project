package works.collegeportals.profile;

import java.io.UnsupportedEncodingException;
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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends Activity{

	TextView title,tvname,tvpass,errormsg;
	EditText edname,edpass;
	Button blogin,boffline;
	String name,pass,url,pid;
	Intent i;
	SharedPreferences pref;
	ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		title=(TextView)findViewById(R.id.logintitle);
		tvname=(TextView)findViewById(R.id.loginnameTextView);
		tvpass=(TextView)findViewById(R.id.loginpasswordTextView);
		errormsg=(TextView)findViewById(R.id.loginerrormessage);
		edname=(EditText)findViewById(R.id.loginname);
		edpass=(EditText)findViewById(R.id.loginpassword);
		blogin=((Button)findViewById(R.id.loginbutton));
		boffline=((Button)findViewById(R.id.loginoffline));
		title.setText("ChangePassword");
		tvname.setText("old");
		tvpass.setText("new");
		blogin.setText("Change");
		boffline.setText("Clear");
		url=getResources().getString(R.string.url);
		i=getIntent();
		pid=i.getStringExtra("pid");

	}
	
	public void login(View v) throws UnsupportedEncodingException
	{
		name=edname.getText().toString();
    	pass=edpass.getText().toString();
	
    	if(!isOnline())
		{
			 Toast.makeText(getApplicationContext(),"Internet not available, Please switch on your wifi or datapack",Toast.LENGTH_SHORT).show();
		}
    	else if(name.equals("") || pass.equals(""))
    	{
    		errormsg.setTextColor(Color.RED);
    		errormsg.setText("Please fill both the columns");
    		errormsg.setVisibility(View.VISIBLE);
    	}
    	
    	else{
    		errormsg.setText("");
            errormsg.setVisibility(View.GONE);
    		pd = new ProgressDialog(this);
    		pd.setMessage("Loading. Please wait...");
    		pd.show();
    		final ServerCall downloadTask = new ServerCall(getApplicationContext());
			downloadTask.execute(url);  
			pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
			    @Override
			    public void onCancel(DialogInterface dialog) {
			        downloadTask.cancel(true);
			    }
			});
    	}
    	
    	
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	
	//annonimus inner class
	class ServerCall extends AsyncTask<String, Integer, String> {

	    public ServerCall(Context context) {
	    }

		@Override
	    protected String doInBackground(String... sUrl) {
	    	
			String output = null;
	        HttpClient httpclient = new DefaultHttpClient();
	    	HttpPost httppost = new HttpPost(url+"ChangePasswordAndroidServlet");
	    	Log.e("url",url+"ChangePasswordAndroidServlet");

        	try {
        	    // Add your data
        	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        	    //Toast.makeText(getApplicationContext(),pid+"\n"+name+"\n"+pass,100).show();
        	    Log.e("error",pid+"\n"+name+"\n"+pass);
        	    nameValuePairs.add(new BasicNameValuePair("pid",pid));
        	    nameValuePairs.add(new BasicNameValuePair("oldp",name));
        	    nameValuePairs.add(new BasicNameValuePair("newp",pass));
        	    
  	    	    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
  	    	    // Execute HTTP Post Request
  	    	    HttpResponse response = httpclient.execute(httppost);
  	    	    HttpEntity httpEntity = response.getEntity();
  	            output = EntityUtils.toString(httpEntity);
             
        	} catch (Exception e){
        		e.printStackTrace();
        	}

	        return output;
	    }
	    

	    @Override
	    protected void onPostExecute(String output) {
            pd.dismiss();
            errormsg.setVisibility(View.VISIBLE);
            if(output.equals("Updated successfully"))
            {
            	errormsg.setTextColor(Color.GREEN);
            }
            else{
            	errormsg.setTextColor(Color.RED);
			}
            
            	errormsg.setText(output);

	    }

	}
	
	public void offline(View v){
		edname.setText("");
		edpass.setText("");
	}

	
}
