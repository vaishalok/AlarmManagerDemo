package com.lk.AlarmManagerDemo;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

public class AlarmReceiver extends Activity {
	
	/**============================================================
	 * method onCreate()
	 *=============================================================*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_receiver);
		
		TextView tv = (TextView)findViewById(R.id.alarm_receiver_text_view);
		
		Calendar c = Calendar.getInstance();
		String alarmReceiverTime = (String)DateFormat.format("dd/MM/yyyy hh:mm", c); 
		
		tv.setText("This alarm get fired at " + alarmReceiverTime);
		
	}//end method onCreate
}//end class AlarmReceiver