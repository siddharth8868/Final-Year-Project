package works.collegeportals;

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

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	String pid,ppass,pbranch,pposition;
	String name,pass;
	EditText edname,edpassword;
	TextView errormsg;
	Button offline;
	SharedPreferences pref;
	ProgressDialog pd;
	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initialization();
		if(!(pid.equals("empty"))){
			Intent i=new Intent(getApplicationContext(),MainPage.class);
			i.putExtra("online","yes");
			startActivity(i);
			this.finish();
		}
		
		setContentView(R.layout.login);		
		edname=(EditText)findViewById(R.id.loginname);
		edpassword=(EditText)findViewById(R.id.loginpassword);
		errormsg=(TextView)findViewById(R.id.loginerrormessage);
		offline= (Button)findViewById(R.id.loginoffline);
		url=getResources().getString(R.string.url);	
	}
	
	private void initialization() {
		
		pref=getSharedPreferences("mypref",MODE_APPEND);
		pid = pref.getString("pid","empty");
		ppass = pref.getString("ppass","empty");
		pbranch = pref.getString("pbranch","empty");
		pposition = pref.getString("pposition","empty");  
		 
	}

	public void offline(View v)
	{
		Intent i=new Intent(getApplicationContext(),MainPage.class);
		startActivity(i);
		finish();
	}
	
	public void login(View v) throws UnsupportedEncodingException
	{
		name=edname.getText().toString();
    	pass=edpassword.getText().toString();
		if(!isOnline())
		{
			 Toast.makeText(getApplicationContext(),"Internet not available, Please switch on your wifi or datapack",Toast.LENGTH_SHORT).show();
		}
		else if(name.equals("") || pass.equals(""))
    	{
    		errormsg.setText("Please fill both the columns");
    		errormsg.setVisibility(View.VISIBLE);
    	}
    	
    	else{
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
	    	HttpPost httppost = new HttpPost(url+"LoginOnlyServlet");
	    	Log.e("url",url+"LoginOnlyServlet");

        	try {
        	    // Add your data
        	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        	    nameValuePairs.add(new BasicNameValuePair("uid",name));
        	    nameValuePairs.add(new BasicNameValuePair("upass",pass));
        	    
  	    	    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  	    	    // Execute HTTP Post Request
  	    	    HttpResponse response = httpclient.execute(httppost);
  	    	    HttpEntity httpEntity = response.getEntity();
  	            output = EntityUtils.toString(httpEntity);
             
        	} catch (Exception e){
        		
        	}

	        return output;
	    }
	    

	    @Override
	    protected void onPostExecute(String output) {
            pd.dismiss();
            
            if(output.equals("invalid"))
        	{
        		//Toast.makeText(this,"inside for",100).show();
        		errormsg.setText("Invalid ID or Password");
        		errormsg.setVisibility(View.VISIBLE);
        	}
        	else if(output.equals("exception")){
            	errormsg.setText("sorry, trouble in loading try after some time");
        		errormsg.setVisibility(View.VISIBLE);
            }
        	
        	else
        	{
        		String a[]=output.split("/");
        		for (int i=0;i<a.length;i++)
        		{
        			Log.e("for"+i,a[i]);
        		}
        			Editor editor = pref.edit();
        			editor.putString("pid",a[0]);
        			editor.putString("ppass",a[1]);
        			editor.putString("pbranch",a[2]);
        			editor.putString("pposition",a[3]);
        			editor.commit();  
        			Intent i=new Intent(getApplicationContext(),MainPage.class);
        			i.putExtra("online","yes");
        			startActivity(i);
        			finish();
        	}
    

	    }

	}

	
}
