package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.BoardListView;

import java.time.LocalDateTime;
import java.time.Month;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BoardListViewActivity extends Activity {
    @BindView(R.id.boardListView)
    BoardListView boardListView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_lst_view_activity);
        ButterKnife.bind(this);

        this.boardListView.setParams(makeParams());
    }

    private BoardListView.BoardParams makeParams() {
        return new BoardListView.BoardParams(7, LocalDateTime.of(2019, Month.MARCH, 18, 0, 0));
    }


}
