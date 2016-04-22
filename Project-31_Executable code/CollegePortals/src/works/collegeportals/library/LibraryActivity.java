package works.collegeportals.library;

import java.util.ArrayList;

import works.collegeportals.LoginPage2;
import works.collegeportals.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LibraryActivity extends Activity {
	
	
	ListView lv;
	ArrayList<String> al;
	ArrayAdapter<String> ad;
	SharedPreferences pref;
	String pid,ppass,pbranch,pposition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylibrary);
		//Toast.makeText(getApplicationContext(), "login",100).show();
		
		lv=( ListView)findViewById(R.id.mylibrarylistView1);
		pref=getSharedPreferences("mypref",MODE_APPEND);
		al=new ArrayList<String>();
		ad= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
		al.add("View Books");
		al.add("Downloaded Books");
		//al.add("renewal");
		lv.setAdapter(ad);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = null;
				String ss=(String)lv.getItemAtPosition(arg2);
				
				if(ss.equals("Downloaded Books"))
				{
					i= new Intent(getApplicationContext(),DownloadedBooks.class);
				}
				else if(pid.endsWith("empty"))
				{
					Log.e("error","its empty");
					i =new Intent(getApplicationContext(),LoginPage2.class);
				}
				else if(ss.equals("View Books"))
				{
					i= new Intent(getApplicationContext(),ViewBooks.class);
				}
				startActivity(i);
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		pid = pref.getString("pid","empty");
		ppass = pref.getString("ppass","empty");
		pbranch = pref.getString("pbranch","empty");
		pposition = pref.getString("pposition","empty");  
	}
}
