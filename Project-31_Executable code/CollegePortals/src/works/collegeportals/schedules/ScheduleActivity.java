package works.collegeportals.schedules;

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

public class ScheduleActivity extends Activity {
	
	
	ListView lv;
	ArrayList<String> al;
	ArrayAdapter<String> ad;
	SharedPreferences pref;
	String pid,ppass,pbranch,pposition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myschedule);
		
		lv=( ListView)findViewById(R.id.myschedulelistView1);
		pref=getSharedPreferences("mypref",MODE_APPEND);
		al=new ArrayList<String>();
		ad= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
		al.add("Add Schedules");
		al.add("View Schedules");
		al.add("Delete Schedules");
		al.add("Downloaded Schedules");
		lv.setAdapter(ad);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = null;
				String selected =(String) (lv.getItemAtPosition(arg2));
				Log.e("pid",pid);
				if(selected.equals("Downloaded Schedules")){
					
					i =new Intent(getApplicationContext(),DownloadSchedule.class);
				}
				else if(pid.endsWith("empty"))
				{
					Log.e("error","its empty");
					i =new Intent(getApplicationContext(),LoginPage2.class);
				}
			
				else if(selected.equals("View Schedules"))
				{
					i =new Intent(getApplicationContext(),ViewSchedule.class);
					i.putExtra("pid",pid);
					i.putExtra("ppass",ppass);
					i.putExtra("pbranch",pbranch);
					i.putExtra("pposition",pposition);
				}
				else if(selected.equals("Add Schedules"))
				{
					i =new Intent(getApplicationContext(),AddSchedules.class);
					i.putExtra("pid",pid);
					i.putExtra("ppass",ppass);
					i.putExtra("pbranch",pbranch);
					i.putExtra("pposition",pposition);
				}
				else if(selected.equals("Delete Schedules")){
					i =new Intent(getApplicationContext(),DeleteSchedules.class);
					i.putExtra("pid",pid);
					i.putExtra("ppass",ppass);
					i.putExtra("pbranch",pbranch);
					i.putExtra("pposition",pposition);
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
