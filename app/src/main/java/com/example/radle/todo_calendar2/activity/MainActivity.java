package com.example.radle.todo_calendar2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.CalendarRowView;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        final CalendarRowView rowView = this.findViewById(R.id.calendarRowView2);
        rowView.setParams(makeParams());
    }

    private CalendarRowView.RowParams makeParams() {
        return new CalendarRowView.RowParams(10, 100, 7, 0, LocalDateTime.now());
    }
}
