package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.CalendarView;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.onNewWeekListener;
import com.example.radle.todo_calendar2.dao.CalendareventsDao;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements onNewWeekListener {

    @BindView(R.id.guiTestsButton)
    Button guiTestButton;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    private final CalendareventsDao calendarEventsDao = new CalendareventsDao() {
        @Override
        public List<CalendarEvent> getEventsForWeek(final LocalDateTime firstDateTime) {
            try {
                Thread.sleep(1000 * 10);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            return Arrays.asList(new CalendarEvent("asd", firstDateTime,
                    firstDateTime.plusHours(2)));
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manageToggle();
        fillWithEvents();
        this.calendarView.setOnNewWeekListener(this);
    }

    private void fillWithEvents() {
        this.calendarView.fillWithEvents(Arrays.asList(
                new CalendarEvent("akjdkio kaicamcao kaiow asdawd",
                        LocalDateTime.of(2020, Month.APRIL, 23, 8, 10, 12),
                        LocalDateTime.of(2020, Month.APRIL, 24, 1, 0, 0)),
                new CalendarEvent("jo",
                        LocalDateTime.of(2020, Month.APRIL, 24, 18, 30, 0),
                        LocalDateTime.of(2020, Month.APRIL, 24, 19, 30, 0)),
                new CalendarEvent("asd",
                        LocalDateTime.of(2020, Month.APRIL, 24, 19, 0, 0),
                        LocalDateTime.of(2020, Month.APRIL, 24, 20, 0, 0))));
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
        this.calendarView
                .addEvents(firstDateTime, this.calendarEventsDao.getEventsForWeek(firstDateTime));
    }
}
