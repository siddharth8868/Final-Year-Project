package works.collegeportals;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AttendanceActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance);
	}
	
	public void attendance(View v)
	{
		Intent i;
		PackageManager manager = getPackageManager();
		i = manager.getLaunchIntentForPackage("works.Attendance");
		if(i==null)
		{
			Toast.makeText(this,"sorry you have no access to the application\n try to install Attendance app first",400).show();
		}
		else{
			i.addCategory(Intent.CATEGORY_LAUNCHER);
			startActivity(i);
		}
	}

}
