package com.example.radle.todo_calendar2.calendarView.tools.events;

import android.graphics.Rect;

import com.example.radle.todo_calendar2.calendarView.BoardScrollView;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPartWithWidth;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class EventPartBoundsResolverTest {
    private final EventPartBoundsResolver subject =
            new EventPartBoundsResolver(new BoardScrollView.BoardParams(80, 4, 7,
                    LocalDate.of(2020, Month.JANUARY, 6).atStartOfDay()));

    @Test
    public void resolveBounds_shouldReturnWholeDayEventBound_whenEventPartLastsAllDay() {
        final CalendarEventPartWithWidth event =
                CalendarEventsCreateUtil.allDayEvent("event").withWidth(0, 1, 1);

        final Rect expected = expectedRect(10, 0, 20, 96);
        final Rect actual = this.subject.resolveBounds(event);
        assertRectsEqual(expected, actual);
    }

    @Test
    public void resolveBounds_shouldReturnPartOfDayEventBound_whenEventPartLastsFewHours() {
        final CalendarEventPartWithWidth event =
                CalendarEventsCreateUtil.newEvent("event",
                        LocalTime.of(9, 30), LocalTime.of(11, 45))
                        .withWidth(0, 1, 1);
        final Rect expected = expectedRect(10, 38, 20, 47);
        final Rect actual = this.subject.resolveBounds(event);

        assertRectsEqual(expected, actual);
    }

    @Test
    public void resolveBounds_shouldReturnNarrowedEventBound_whenEventCoexistsWithOtherEvents() {
        final CalendarEventPartWithWidth event =
                CalendarEventsCreateUtil.newEvent("event",
                        LocalTime.of(8, 0), LocalTime.of(9, 0))
                        .withWidth(1, 2.5f, 5);
        final Rect expected = expectedRect(12, 32, 15, 36);
        final Rect actual = this.subject.resolveBounds(event);

        assertRectsEqual(expected, actual);
    }

    private Rect expectedRect(final int left, final int top, final int right, final int bottom) {
        final Rect rect = new Rect();
        rect.left = left;
        rect.top = top;
        rect.right = right;
        rect.bottom = bottom;
        return rect;
    }

    private void assertRectsEqual(final Rect expected, final Rect actual) {
        Assert.assertEquals(expected.left, actual.left);
        Assert.assertEquals(expected.top, actual.top);
        Assert.assertEquals(expected.right, actual.right);
        Assert.assertEquals(expected.bottom, actual.bottom);
    }
}