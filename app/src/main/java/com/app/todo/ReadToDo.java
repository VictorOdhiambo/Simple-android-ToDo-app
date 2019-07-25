package com.app.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ReadToDo extends AppCompatActivity {
    private TextView text_title;
    private EditText text_content;
    private TextView save_btn;
    private ImageView date;
    private ImageView time;

    private String title, text, mDate;
    private int icon;

    private DbHelper dbHelper;

    private DatePicker datePicker;

    private int year, month, day, hour, minute;

    private Calendar calendar, timeCal;

    private StringBuilder dateFormat, timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_to_do);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My To-Do");

        Intent intent = getIntent();

        if(intent != null){
            title = intent.getStringExtra("title");
            text = intent.getStringExtra("content");
            mDate = intent.getStringExtra("date");
            icon = intent.getIntExtra("icon", Color.GRAY);
        }

        dbHelper = new DbHelper(getApplicationContext(), "ToDo", null, 1);

        text_title = (TextView) findViewById(R.id.text_title);
        text_content = (EditText) findViewById(R.id.text_content);
        date = (ImageView) findViewById(R.id.date);
        time = (ImageView) findViewById(R.id.time);
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        save_btn = (TextView) findViewById(R.id.save_btn);


        text_title.setText("Title: " + title);


        text_content.setText("\t\t\t\t\t\t\t\t" + mDate + "\n\n" +text);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 text = text_content.getText().toString().trim();
                 if(text.contains(mDate)) {
                     if (dbHelper.updateTodo(title, text.substring(10), dateFormat.toString(),timeFormat.toString())) {
                         Toast.makeText(getApplicationContext(), "Saved",
                                 Toast.LENGTH_SHORT)
                                 .show();

                     }
                 }else {
                     if (dbHelper.updateTodo(title, text, dateFormat.toString(), timeFormat.toString())) {
                         Toast.makeText(getApplicationContext(), "Saved",
                                 Toast.LENGTH_SHORT)
                                 .show();

                         Toast.makeText(ReadToDo.this, dateFormat.toString(), Toast.LENGTH_SHORT).show();
                     }
                 }

                Toast.makeText(ReadToDo.this, timeFormat, Toast.LENGTH_SHORT).show();
            }
        });

        currentDate();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog d = createDialog();
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                d.show();

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = timeDialog();
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                d.show();
            }
        });
    }

    private void currentDate(){
        calendar = Calendar.getInstance();
        timeCal = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dateFormat = new StringBuilder().append(day)
                .append("/")
                .append(month + 1)
                .append("/")
                .append(year)
                .append(" ");

        hour = timeCal.get(Calendar.HOUR_OF_DAY);
        minute = timeCal.get(Calendar.MINUTE);

        timeFormat = new StringBuilder()
                .append(hour)
                .append(" : ")
                .append(minute)
                .append(" ");

        datePicker.init(year, month, day, null);
    }

    private Dialog timeDialog(){
        return new TimePickerDialog(this, timeSetListener,
                hour, minute, true);
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int min) {
            hour = hourOfDay;
            minute = min;

            timeFormat = new StringBuilder()
                    .append(hour)
                    .append(" : ")
                    .append(minute)
                    .append(" ");
        }
    };

    private Dialog createDialog(){
        return new DatePickerDialog(this, datePickerListener,
                year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog
            .OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int mYear, int mMonth, int dayOfMonth) {
            year = mYear;
            month = mMonth;
            day = dayOfMonth;

            dateFormat = new StringBuilder().append(day)
                    .append("/")
                    .append(month + 1)
                    .append("/")
                    .append(year)
                    .append(" ");

            datePicker.init(year, month, day, null);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
