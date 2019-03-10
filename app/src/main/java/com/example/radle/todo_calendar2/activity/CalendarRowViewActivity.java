package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.CalendarRowView;

import java.time.LocalDateTime;
import java.time.Month;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarRowViewActivity extends Activity {

    @BindView(R.id.calendarRowView)
    CalendarRowView calendarRowView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_row_view_activity);
        ButterKnife.bind(this);

        this.calendarRowView.setParams(makeParams());
    }

    private CalendarRowView.RowParams makeParams() {
        return new CalendarRowView.RowParams(100, 7, 8, LocalDateTime.of(2019, Month.MARCH, 11, 8
                , 0));
    }
}
