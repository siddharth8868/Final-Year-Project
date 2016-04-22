package works.collegeportals.library;

import java.io.File;
import java.util.ArrayList;

import works.collegeportals.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class DownloadedBooks extends Activity{
	
	ListView lv;
	SQLiteStatement st;
	SQLiteDatabase db;
	ArrayList<String> al,al1;
	String selected;
	AlertDialog.Builder dialog;
	Cursor cr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloadschedules);
		lv=(ListView)findViewById(R.id.downloadscheduleslistView);
		al =new ArrayList<String>();
		al1 =new ArrayList<String>();
		setList();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String selected =al1.get(arg2);
				File file = new File(Environment.getExternalStorageDirectory()+"/CollegePortal/library/"+selected);
				Log.e("message",Environment.getExternalStorageDirectory()+"/CollegePortal/library/"+selected);
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(Intent.ACTION_VIEW);
				String type = "application/msword";
				intent.setDataAndType(Uri.fromFile(file), type);
				startActivity(intent);

				}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2,
					long arg3) {
					selected =al1.get(arg2);			
					dialog.show();
				
					return false;
			}
		});
		
		dialog= new AlertDialog.Builder(this);
		dialog.setMessage("Do you want to delete the book");
		dialog.setTitle("Conformation Message");
		dialog.setCancelable(false);
		//Positive Button
		dialog.setPositiveButton("ok",
				new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface d,int id){
				deleteList(selected);
			}
		});
		dialog.setNegativeButton("cancel",new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
	}
	
	
	private void setList() {
		
		al.clear();
		al1.clear();
		try{
		db=SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory()+"/CollegePortal/library/books.db",null);
        cr=db.rawQuery("select * from books",null);
        
        Log.d("error","entered  1");
        if(cr!=null)
        {
        	Log.d("error","entered 2");
        	if(cr.moveToFirst()){
        		do{
        			Log.d("error","entered 3");
        			al.add(cr.getString(cr.getColumnIndex("title")));
        			al1.add(cr.getString(cr.getColumnIndex("link")));
        		}while(cr.moveToNext());
        	}
        }
        Log.d("error","entered 4");
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,al);
		lv.setAdapter(ad);
		Log.d("error","entered 5");
	}
	catch(Exception e){
		e.printStackTrace();
	}
		
	}

	
	public void deleteList(String selected)
	{
		try{
			db.execSQL("delete from books where link='"+selected+"'");
			}
			catch(Exception e){
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"error="+e.getMessage(),200).show();
			}
		setList();
		
	}

}
