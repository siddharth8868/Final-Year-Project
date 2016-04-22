package works.collegeportals.profile;

import works.collegeportals.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Test extends Activity{
	
	TextView id,pass,branch,position;
	String pid,ppass,pbranch,pposition;
	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		pref=getSharedPreferences("mypref",MODE_APPEND);
		id=(TextView)findViewById(R.id.testtextView1);
		pass=(TextView)findViewById(R.id.testtextView2);
		branch=(TextView)findViewById(R.id.testtextView3);
		position=(TextView)findViewById(R.id.testtextView4);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		pid = pref.getString("pid","empty");
		ppass = pref.getString("ppass","empty");
		pbranch = pref.getString("pbranch","empty");
		pposition = pref.getString("pposition","empty");  
		id.setText(pid);
		pass.setText(ppass);
		branch.setText(pbranch);
		position.setText(pposition);
	}

}
