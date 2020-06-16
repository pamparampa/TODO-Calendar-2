package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.CalendarView;
import com.example.radle.todo_calendar2.calendarView.onNewWeekListener;
import com.example.radle.todo_calendar2.dao.CalendarEventsDao;

import java.time.LocalDateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements onNewWeekListener {

    @BindView(R.id.guiTestsButton)
    Button guiTestButton;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    private final CalendarEventsDao calendarEventsDao =
            new CalendarEventsDao(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manageToggle();
        this.calendarView.setOnNewWeekListener(this);
    }

    private void manageToggle() {
        if (Toggle.isTestOn()) {
            this.guiTestButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.guiTestsButton)
    public void clickGuiTests() {
        final Intent intent = new Intent(getApplicationContext(), GuiTestsListActivity.class);
        startActivity(intent);
    }

    @Override
    public void newWeek(final LocalDateTime firstDateTime) {
        this.calendarEventsDao.getEventsForWeek(firstDateTime,
                calendarEvents -> this.calendarView.addEvents(firstDateTime, calendarEvents));
    }
}
