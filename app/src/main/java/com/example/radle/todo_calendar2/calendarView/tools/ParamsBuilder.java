package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.BoardListView;
import com.example.radle.todo_calendar2.calendarView.CalendarRowView;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;

import org.jetbrains.annotations.NotNull;

public class ParamsBuilder {

    public CalendarRowView.RowParams getRowParamsByBoardParams(@NotNull final BoardListView.BoardParams boardParams, final IdWithDataTime idWithDataTime) {
        return new CalendarRowView.RowParams(boardParams.getWidth(), boardParams.getRowHeight(),
                boardParams.getNumberOfColumns(), idWithDataTime.getId(),
                idWithDataTime.getDateTime());
    }
}
