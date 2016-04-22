package works.collegeportals.schedules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewSchedule extends Activity{
	
	ListView lv;
	Spinner sp;
	String url,pid,ppass,pbranch,pposition;
	ArrayList<String> al;
	Intent i;
	ProgressDialog pd;
	ArrayList<ViewScheduleBean> alist;
	ViewScheduleBean ob = null;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewschedule);
		sp=(Spinner)findViewById(R.id.viewschedulespinner);
		lv=(ListView)findViewById(R.id.viewschedulelistView);
		url = getResources().getString(R.string.url);
		
		i=getIntent();
		pid=i.getStringExtra("pid");
		ppass=i.getStringExtra("ppass");
		pbranch=i.getStringExtra("pbranch");
		pposition=i.getStringExtra("pposition");
		
		
		al = new ArrayList<String>();
		alist= new ArrayList<ViewScheduleBean>();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				callServer();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				alist.get(arg2);
				i=new Intent(getApplicationContext(),ViewScheduleDisplay.class);
				i.putExtra("sub","hello");
				i.putExtra("subject",alist.get(arg2).subject);
				i.putExtra("ondate",alist.get(arg2).ondate);
				i.putExtra("vdate",alist.get(arg2).vdate);
				i.putExtra("link",alist.get(arg2).link);
				i.putExtra("content",alist.get(arg2).content);
				i.putExtra("uploader",alist.get(arg2).uploader);
				startActivity(i);
				
			}
		});
	}
	
	private void callServer(){

    	HttpClient httpclient = new DefaultHttpClient();
    	HttpPost httppost = new HttpPost(url+"DisplayScheduleAndroidServlet");
    	Log.d("server call",url+"DisplayScheduleAndroidServlet");

    	try {
    	    // Add your data
    	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    	    nameValuePairs.add(new BasicNameValuePair("branch",(String)sp.getSelectedItem()));
    	    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	    
    	    // Execute HTTP Post Request
    	    HttpResponse response = httpclient.execute(httppost);
    		pd = ProgressDialog.show(this, "","Loading. Please wait...", true);
    	    HttpEntity httpEntity = response.getEntity();
            pd.dismiss();
    	    String output = EntityUtils.toString(httpEntity);
            Log.d("got",output);
            if(output.equals("error")){
            	Toast.makeText(getApplicationContext(),"something went wrong try after some time",100).show();
            }
            else
            {
            setResultToListView(output);
            }
            
    	} catch (ClientProtocolException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}

	private int setResultToListView(String output) {
		
		alist.clear();
		al.clear();
		if(output.equals(""))
		{
			ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,al);
			lv.setAdapter(ad);
			return 1;
		
		}
		String[] row,td;
		row=output.split("##epnd");
		for(int i=0;i<row.length;i++)
		{
			td=row[i].split("/@#");
			
			for(int j=0;j<td.length;j++)
			{
				if(td[j].equals("null"))
				{
					Log.d("td["+j+"]=null",td[j]);
					td[j]="-";
				}
			}
				Log.d("test",td[0]);
				Log.d("test",td[1]);
				Log.d("test",td[2]);
				Log.d("test",td[3]);
				Log.d("test",td[4]);
				Log.d("test",td[5]);
				Log.d("top","got everything");
				ob=new ViewScheduleBean(td[0],td[1],td[2],td[3],td[4],td[5]);
				alist.add(ob);
				al.add(ob.subject);
			
		}
		
		ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,al);
		lv.setAdapter(ad);
		return 1;
	
	}

}
