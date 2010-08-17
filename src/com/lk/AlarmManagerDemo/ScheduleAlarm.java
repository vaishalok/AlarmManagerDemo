package com.lk.AlarmManagerDemo;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class ScheduleAlarm extends Activity implements OnClickListener {

	Calendar scheduledAlarmTime = Calendar.getInstance();
	Button chooseDateButton, chooseTimeButton;
	
	/**=======================================================
     *  method onCreate()
     *  Called when the activity is first created. 
     * ======================================================= */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_alarm);
        
        chooseDateButton = (Button)findViewById(R.id.schedule_alarm_choose_date_button);
        chooseDateButton.setOnClickListener(this);
        
        chooseTimeButton = (Button)findViewById(R.id.schedule_alarm_choose_time_button);
        chooseTimeButton.setOnClickListener(this);
        
        
        ScheduleAlarm sa = (ScheduleAlarm)getLastNonConfigurationInstance();
        if(sa != null) {
        	scheduledAlarmTime = sa.scheduledAlarmTime;
        	setDateButtonText();
        	setTimeButtonText();
        }
        
        
        Button scheduleAlarmButton = (Button)findViewById(R.id.schedule_alarm_done_button);
        scheduleAlarmButton.setOnClickListener(this);
    }//end method onCreate()
    
    /**=========================================================
     * method onClick()
     * =========================================================*/
	@Override
	public void onClick(View clickedView) {
		int idOfClickedView = clickedView.getId();
		switch(idOfClickedView) {
			case R.id.schedule_alarm_choose_date_button: {
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int date = c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dpd = new DatePickerDialog(this, dateSetListener, year, month, date);
				dpd.show();
				break;
			}
			case R.id.schedule_alarm_choose_time_button: {
				Calendar c = Calendar.getInstance();
				int hour = c.get(Calendar.HOUR);
				int minute = c.get(Calendar.MINUTE);
				TimePickerDialog tpd = new TimePickerDialog(this, timeSetListener, hour, minute, true);
				tpd.show();
				break;
			}
			case R.id.schedule_alarm_done_button: {
				AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				
				Intent intent = new Intent(this, AlarmReceiver.class);
				
				PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				
				scheduledAlarmTime.set(Calendar.SECOND, 0);
				scheduledAlarmTime.set(Calendar.MILLISECOND, 0);
				
				long scheduledTimeInMillisec = scheduledAlarmTime.getTimeInMillis();
				
				am.set( AlarmManager.RTC_WAKEUP, scheduledTimeInMillisec, pi);
				
				TextView tvAlarmSet = (TextView) findViewById(R.id.schedule_alarm_alarm_set_text_view);
				tvAlarmSet.setVisibility(View.VISIBLE);
				break;
			}
		}
	}//end method onClick()
	
	//============================ DatePickerDialog.OnDateSetListener =============================//
	DatePickerDialog.OnDateSetListener dateSetListener = new  DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			scheduledAlarmTime.set(Calendar.YEAR, year);
			scheduledAlarmTime.set(Calendar.MONTH, monthOfYear);
			scheduledAlarmTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	
			setDateButtonText();
		}
	};
	
	//============================ TimePickerDialog.OnTimeSetListener =============================//
	TimePickerDialog.OnTimeSetListener timeSetListener = new  TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hour, int minute) {
			scheduledAlarmTime.set(Calendar.HOUR, hour);
			scheduledAlarmTime.set(Calendar.MINUTE, minute);
	
			setTimeButtonText();
		}
	};
	
	/**==========================================================
	 * method setDateButtonText()
	 * ==========================================================*/
	private void setDateButtonText() {
		String scheduledDate = (String)DateFormat.format("dd/MM/yyyy", scheduledAlarmTime);
		chooseDateButton.setText(scheduledDate);
	}//end method setDateButtonText
	
	/**==========================================================
	 * method setTimeButtonText()
	 * ==========================================================*/
	private void setTimeButtonText() {
		String scheduledTime = (String)DateFormat.format("hh:mm", scheduledAlarmTime);
		chooseTimeButton.setText(scheduledTime);
	}//end method setTimeButtonText
}//end class ScheduleAlarm