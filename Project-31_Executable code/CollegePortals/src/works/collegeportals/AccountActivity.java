package works.collegeportals;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AccountActivity extends Activity{
	
	
	Spinner sposition,sbranch,swork;
	String pname,pbranch,pposition;
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		
		sbranch=(Spinner)findViewById(R.id.accountspinnerbranch);
		sposition=(Spinner)findViewById(R.id.accountspinnerposition);
		swork=(Spinner)findViewById(R.id.accountspinnerwork);
		initialization();
		
		if(pposition.equalsIgnoreCase("HOD"))
		{
			sbranch.setVisibility(View.GONE);
		}
		else{
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.accountspinnerpositionforadmin));
			        sposition.setAdapter(adapter);
		}
		
	}
	
	private void initialization() {
		
		pref=getSharedPreferences("mypref",MODE_APPEND);
		pname = pref.getString("pname","empty");
		pbranch = pref.getString("pbranch","empty");
		pposition = pref.getString("pposition","empty");  	
          
	}

}
