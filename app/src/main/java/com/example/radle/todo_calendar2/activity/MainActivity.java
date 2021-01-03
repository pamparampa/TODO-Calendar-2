package com.example.radle.todo_calendar2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.CalendarView;
import com.example.radle.todo_calendar2.calendarView.onNewWeekListener;
import com.example.radle.todo_calendar2.dao.CalendarEventsDao;
import com.example.radle.todo_calendar2.fragment.CalendarChooseDialog;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements onNewWeekListener {

    @BindView(R.id.guiTestsButton)
    Button guiTestButton;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    private final CalendarEventsDao calendarEventsDao =
            new CalendarEventsDao(this);
    private final List<String> calendarIds = Arrays.asList("8", "12");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manageToggle();
        this.calendarView.setOnNewWeekListener(this);
        fillWithEvents(this.calendarView.getCurrentlyAccessibleTimeInterval());

        showCalendarChooseDialog();
    }

    private void showCalendarChooseDialog() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        new CalendarChooseDialog().show(fragmentManager, "alert");
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
        addEvents(firstDateTime);
    }

    private void addEvents(final LocalDateTime firstDateTime) {
        this.calendarEventsDao.getEventsForWeek(firstDateTime, this.calendarIds,
                calendarEvents -> this.calendarView.addEvents(firstDateTime, calendarEvents));
    }

    private void fillWithEvents(final CalendarView.TimeInterval timeInterval) {
        this.calendarEventsDao.getEvents(timeInterval.getFrom(), timeInterval.getTo(),
                this.calendarIds,
                calendarEvents -> this.calendarView.fillWithEvents(calendarEvents));
    }


}
