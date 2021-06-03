package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.BoardScrollView;
import com.example.radle.todo_calendar2.dto.CalendarSelection;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CalendarPositionsHandler {

    public static final Duration MIN_TIME_AMOUNT = Duration.of(30, ChronoUnit.MINUTES);
    public static final int MIN_NUMBER_OF_MINUTES = 30;
    private static final int ADDITIONAL_LABEL_COLUMN = 1;
    private final BoardScrollView.BoardParams boardParams;

    public CalendarPositionsHandler(BoardScrollView.BoardParams boardParams) {
        this.boardParams = boardParams;
    }

    public CalendarSelection determineSelection(float x, float y) {
        LocalDateTime startTime = determineStartTime(x, y);
        if (itIsLabelColumn(startTime)) {
            return null;
        }
        LocalDateTime roundedStartTime = roundTime(startTime);
        int roundedX = roundX(x);
        int roundedY = roundY(y);
        return new CalendarSelection(roundedX, roundedY,
                roundedX + getColumnWidth(),
                roundedY + ((float) boardParams.getRowHeight() / 2),
                roundedStartTime,
                roundedStartTime.plus(MIN_TIME_AMOUNT));
    }

    private boolean itIsLabelColumn(LocalDateTime startTime) {
        return startTime.isBefore(boardParams.getFirstDateTime());
    }

    private int roundX(float x) {
        int columnWidth = getColumnWidth();
        int numberOfRow = (int) x / columnWidth;
        return numberOfRow * columnWidth;
    }

    private int getColumnWidth() {
        return boardParams.getWidth() /
                (boardParams.getNumberOfColumns() + ADDITIONAL_LABEL_COLUMN);
    }

    private LocalDateTime determineStartTime(float x, float y) {
        int numberOfColumn = (int) x / (boardParams.getWidth() /
                (boardParams.getNumberOfColumns() + ADDITIONAL_LABEL_COLUMN));
        return boardParams.getFirstDateTime().plusDays(numberOfColumn - 1).plusMinutes(calculateMinutes(y));
    }


    private int calculateMinutes(float y) {
        return (int) y * 60 / boardParams.getRowHeight();
    }

    private int roundY(float y) {
        int minSize = boardParams.getRowHeight() / 2;
        int selectionNumber = (int) y / minSize;
        return selectionNumber * minSize;
    }

    private LocalDateTime roundTime(LocalDateTime startTime) {
        LocalDateTime startOfDay = startTime.withHour(0).withMinute(0);
        long numberOfMinutes = ChronoUnit.MINUTES.between(startOfDay, startTime);
        long selectionNumber = numberOfMinutes / MIN_NUMBER_OF_MINUTES;
        return startOfDay.plus(MIN_TIME_AMOUNT.multipliedBy(selectionNumber));
    }
}
