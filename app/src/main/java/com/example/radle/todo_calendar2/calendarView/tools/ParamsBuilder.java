package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.BoardListView;
import com.example.radle.todo_calendar2.calendarView.CalendarRowView;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParamsBuilder {

    public CalendarRowView.RowParams getRowParamsByBoardParams(@NotNull final BoardListView.BoardParams boardParams, final int id, final List<IdWithDataTime> idsWithDateTimes) {
        return new CalendarRowView.RowParams(boardParams.getWidth(), boardParams.getRowHeight(),
                boardParams.getNumberOfColumns(), id, idsWithDateTimes.get(id).getDateTime());
    }
}
