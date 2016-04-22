package works.collegeportals.schedules;

import java.io.Serializable;
import android.annotation.SuppressLint;

public class ViewScheduleBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
		public String subject;
	    public String ondate;
	    public String vdate;
	    public String link;
	    public String content;
	    public String uploader;
	    
	    @SuppressLint("ParcelCreator")
		ViewScheduleBean(String subject,String ondate,String vdate,String link,String content,String uploader){
	        this.subject = subject;
	        this.ondate = ondate;
	        this.vdate = vdate;
	        this.link = link;
	        this.content = content;
	        this.uploader = uploader;
	    }
	    
}
