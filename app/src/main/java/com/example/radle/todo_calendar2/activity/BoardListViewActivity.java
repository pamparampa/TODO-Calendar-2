package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.annotation.Nullable;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.BoardListView;
import com.example.radle.todo_calendar2.calendarView.tools.RowsMeasures;

import java.time.LocalDateTime;
import java.time.Month;

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
        final int smallerDimension = getSmallerScreenDimension();
        return new BoardListView.BoardParams(getSmallerScreenDimension(),
                new RowsMeasures().measureRowHeight(smallerDimension, 7), 7,
                LocalDateTime.of(2019, Month.MARCH, 18, 0, 0));
    }

    private int getSmallerScreenDimension() {
        final Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        return Math.min(size.x, size.y);
    }


}
