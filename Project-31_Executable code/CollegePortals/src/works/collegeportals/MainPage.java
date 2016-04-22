package works.collegeportals;

import works.collegeportals.forums.ForumActivity;
import works.collegeportals.library.LibraryActivity;
import works.collegeportals.profile.ProfileActivity;
import works.collegeportals.schedules.ScheduleActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainPage extends TabActivity{
	
	String pid,ppass,pbranch,pposition;
	SharedPreferences pref;
	boolean login;
	TabHost tabHost;
	RelativeLayout rl;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		initialization();
		rl=(RelativeLayout)findViewById(R.id.scraprelativelayout1);
		tv=(TextView)findViewById(R.id.scraptext);
		setTabs();
	}
	
	
	private void initialization() {
		
		pref=getSharedPreferences("mypref",MODE_APPEND);
		pid = pref.getString("pid","empty");
		ppass = pref.getString("ppass","empty");
		pbranch = pref.getString("pbranch","empty");
		pposition = pref.getString("pposition","empty");  
		 
	}
	
	private void setTabs() {
		
		View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.scrap,null);
		view.setBackgroundResource(R.drawable.profile);
		@SuppressWarnings("unused")
		TextView tv = (TextView)findViewById(R.id.scraptext);
		
		tabHost =getTabHost();
		//tabHost.getTabWidget().getChildAt(0).setp;

		 // Tab for Profile
		TabSpec profilepec = tabHost.newTabSpec("Profile");
        // setting Title and Icon for the Tab
		profilepec.setIndicator("Profile", getResources().getDrawable(R.drawable.profile));
		//rl.setBackgroundResource(R.drawable.profile);
		//tv.setText("profile");
       //profilepec.setIndicator(view);
        Intent profilesIntent = new Intent(this, ProfileActivity.class);
        profilepec.setContent(profilesIntent);
         
        // Tab for Library
        TabSpec librarypec = tabHost.newTabSpec("Library");        
        librarypec.setIndicator("Library", getResources().getDrawable(R.drawable.library)); 
        Intent libraryIntent = new Intent(this, LibraryActivity.class);
        librarypec.setContent(libraryIntent);
         
        // Tab for Schedule
        TabSpec schedulepec = tabHost.newTabSpec("Schedule");
       schedulepec.setIndicator("Schedule", getResources().getDrawable(R.drawable.schedule));
        Intent schedulesIntent = new Intent(this, ScheduleActivity.class);
        schedulepec.setContent(schedulesIntent);
        
        // Tab for MySpace
        //TabSpec myspacespec = tabHost.newTabSpec("MySpace");
        //myspacespec.setIndicator("MySpace", getResources().getDrawable(R.drawable.myspace));
        //Intent myspacesIntent = new Intent(this, MySpaceActivity.class);
        //myspacespec.setContent(myspacesIntent);
         
        
        // Tab for Forum
        TabSpec forumpec = tabHost.newTabSpec("Forum");        
        forumpec.setIndicator("Forum", getResources().getDrawable(R.drawable.forum));
        Intent forumIntent = new Intent(this,ForumActivity.class);
        forumpec.setContent(forumIntent);
        
        // Tab for Attendance
        TabSpec attendancepec = tabHost.newTabSpec("Attendance");        
        attendancepec.setIndicator("Attendance", getResources().getDrawable(R.drawable.attendance));
        Intent attendanceIntent = new Intent(this, AttendanceActivity.class);
        attendancepec.setContent(attendanceIntent);
        
        // Tab for account-(only admins)
        TabSpec accountpec = tabHost.newTabSpec("Account");        
        accountpec.setIndicator("Forum", getResources().getDrawable(R.drawable.forum));
        Intent accountIntent = new Intent(this,AccountActivity.class);
        accountpec.setContent(accountIntent);
        
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(librarypec); // Adding library tab
        tabHost.addTab(schedulepec); // Adding schedules tab
       // tabHost.addTab(myspacespec); // Adding myspaces tab
       // tabHost.addTab(chatspec); // Adding chats tab
        tabHost.addTab(forumpec); // Adding forum tab
        tabHost.addTab(profilepec); // Adding profiles tab
        tabHost.addTab(attendancepec); // Adding attendance tab
        //tabHost.addTab(locationspec); // Adding locations tab

       for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
    	   //tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.BLACK);
    	   //tabHost.getTabWidget().getChildAt(i).get
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().width = 120;
        }
        

		
	}
	
	

}
