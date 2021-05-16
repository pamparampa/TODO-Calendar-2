package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.BoardScrollView;
import com.example.radle.todo_calendar2.dto.CalendarSelection;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CalendarPositionsHandler {

    public static final Duration MIN_TIME_AMOUNT = Duration.of(30, ChronoUnit.MINUTES);
    public static final int MIN_NUMBER_OF_MINUTES = 30;
    private final BoardScrollView.BoardParams boardParams;

    public CalendarPositionsHandler(BoardScrollView.BoardParams boardParams) {
        this.boardParams = boardParams;
    }

    public CalendarSelection determineSelection(int x, int y) {
        LocalDateTime startTime = determineStartTime(x, y);
        LocalDateTime roundedStartTime = roundTime(startTime);
        int roundedX = roundX(x);
        int roundedY = roundY(y);
        return new CalendarSelection(roundedX, roundedY,
                roundedX + (boardParams.getWidth() / boardParams.getNumberOfColumns()),
                roundedY + (boardParams.getRowHeight() / 2),
                roundedStartTime,
                roundedStartTime.plus(MIN_TIME_AMOUNT));
    }

    private int roundX(int x) {
        int columnWidth = boardParams.getWidth() / boardParams.getNumberOfColumns();
        int numberOfRow = x / columnWidth;
        return numberOfRow * columnWidth;
    }

    private LocalDateTime determineStartTime(int x, int y) {
        int numberOfRow = x / (boardParams.getWidth() / boardParams.getNumberOfColumns());
        return boardParams.getFirstDateTime().plusDays(numberOfRow).plusMinutes(calculateMinutes(y));
    }


    private int calculateMinutes(int y) {
        return y * 60 / boardParams.getRowHeight();
    }

    private int roundY(int y) {
        int minSize = boardParams.getRowHeight() / 2;
        int selectionNumber = y / minSize;
        return selectionNumber * minSize;
    }

    private LocalDateTime roundTime(LocalDateTime startTime) {
        LocalDateTime startOfDay = startTime.withHour(0).withMinute(0);
        long numberOfMinutes = ChronoUnit.MINUTES.between(startOfDay, startTime);
        long selectionNumber = numberOfMinutes / MIN_NUMBER_OF_MINUTES;
        return startOfDay.plus(MIN_TIME_AMOUNT.multipliedBy(selectionNumber));
    }
}
