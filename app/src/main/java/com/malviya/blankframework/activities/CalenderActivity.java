package com.malviya.blankframework.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.malviya.blankframework.R;
import com.malviya.blankframework.utils.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;


public class CalenderActivity extends AppCompatActivity
{


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		HashSet<Date> events = new HashSet<>();
		events.add(new Date());

		CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
		cv.updateCalendar(events);

		// assign event handler
		cv.setEventHandler(new CalendarView.EventHandler()
		{
			@Override
			public void onDayLongPress(Date date)
			{
				// show returned day
				DateFormat df = SimpleDateFormat.getDateInstance();
				Toast.makeText(CalenderActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
			}
		});
	}


}
