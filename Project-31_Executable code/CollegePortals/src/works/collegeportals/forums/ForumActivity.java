package works.collegeportals.forums;

import java.util.ArrayList;

import works.collegeportals.LoginPage2;
import works.collegeportals.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ForumActivity extends Activity {
	
	
	ListView lv;
	ArrayList<String> al;
	ArrayAdapter<String> ad;
	SharedPreferences pref;
	String pid,ppass,pbranch,pposition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forum);
		
		lv=( ListView)findViewById(R.id.forumlistView1);
		pref=getSharedPreferences("mypref",MODE_APPEND);
		al=new ArrayList<String>();
		ad= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
		al.add("Add Forum");
		al.add("Delete Forum");
		al.add("All");
		al.add("General");
		al.add("Acadamic Branch");
		al.add("Administration");
		al.add("Sports");
		al.add("Library");
		al.add("CIVIL");
		al.add("CSE");
		al.add("IT");
		al.add("ECE");
		al.add("EEE");
		al.add("MECH");
		al.add("AUTO");
		al.add("MBA");
		al.add("MCA");
		lv.setAdapter(ad);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i=null;
				String ss=(String)lv.getItemAtPosition(arg2);
				if(pid.equals("empty"))
				{
					i =new Intent(getApplicationContext(),LoginPage2.class);
				}
				else if(ss.equals("Add Forum")){
					i =new Intent(getApplicationContext(),AddForums.class);
					i.putExtra("pid",pid);
					i.putExtra("ppass",ppass);
				}
				else if (ss.equals("Delete Forum")) {
					i =new Intent(getApplicationContext(),DeleteForums.class);
					i.putExtra("pid",pid);
				}
				
				else{
					 i=new Intent(getApplicationContext(),DisplayForums.class);
					i.putExtra("branch",ss);
					i.putExtra("pid",pid);
				}
				
				startActivity(i);
			}
		});;
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
