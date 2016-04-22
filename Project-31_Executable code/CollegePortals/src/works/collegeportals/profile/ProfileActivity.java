package works.collegeportals.profile;

import java.util.ArrayList;
import works.collegeportals.LoginPage2;
import works.collegeportals.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ProfileActivity extends Activity{
	
	
	
	/*
	  <TextView
            android:id="@+id/profilecreateday"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Create Day"
            android:textSize="30sp"
            android:typeface="serif"
            android:onClick=""
            android:textAppearance="?android:attr/textAppearanceLarge" />
            
      */
	
	ListView lv;
	ArrayList<String> al;
	ArrayAdapter<String> ad;
	String pid,ppass,pbranch,pposition;
	SharedPreferences pref;
	ProgressDialog pd;
	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilemain);
		lv=( ListView)findViewById(R.id.profilelistView1);
		al=new ArrayList<String>();
		ad= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated methodstu b
				Intent i;
				String s=(String)lv.getItemAtPosition(arg2);
				if(s.equals("Create day"))
				{
					 //i=new Intent(getApplicationContext(),CreateDayActivity.class);
					// i.putExtra("number", s);
					 //startActivity(i);	
				}
				else if(s.equals("Event Reminder"))
				{
					i=new Intent(getApplicationContext(),EventRemainder.class);
					i.putExtra("number", s);
					startActivity(i);
				}
				else if(s.equals("test"))
				{
					i=new Intent(getApplicationContext(),Test.class);
					i.putExtra("number", s);
					startActivity(i);
				}
				else if(s.equals("Login"))
				{
					i=new Intent(getApplicationContext(),LoginPage2.class);
					startActivity(i);
				}			
				else if(s.equals("Logout"))
				{
					SharedPreferences pref;
					pref=getSharedPreferences("mypref",MODE_APPEND);
					Editor editor = pref.edit();
        			editor.putString("pid","empty");
        			editor.putString("ppass","empty");
        			editor.putString("pbranch","empty");
        			editor.putString("pposition","empty");
        			editor.commit(); 
					Toast.makeText(getApplicationContext(),"successfully logged out",Toast.LENGTH_SHORT).show();
					onResume();
				}
				else if(s.equals("Change Password"))
				{
					i=new Intent(getApplicationContext(),ChangePassword.class);
					i.putExtra("pid",pid);
					startActivity(i);
				}
			}
				
		});
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		al.clear();
		//al.add("Create day");
		//al.add("Event Reminder");
		//al.add("test");
		initialization();
		if(pid.equals("empty")){
			al.add("Login");
		}
		else{
			al.add("Change Password");
			al.add("Logout");
		}
		
		lv.setAdapter(ad);
		
	}
	
	private void initialization() {
		
		pref=getSharedPreferences("mypref",MODE_APPEND);
		pid = pref.getString("pid","empty");
		ppass = pref.getString("ppass","empty");
		pbranch = pref.getString("pbranch","empty");
		pposition = pref.getString("pposition","empty");  
		 
	}

}
