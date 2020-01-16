package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class DayEventsArrangement {
    List<List<CalendarEventPart>> arrange(final List<CalendarEventPart> eventPartsInDay) {
        final List<List<CalendarEventPart>> eventsSplitIntoColumns = new LinkedList<>();
        final List<CalendarEventPart> restOfEventPartsInDay = new LinkedList<>(eventPartsInDay);
        if (restOfEventPartsInDay.size() > 0) {
            final CalendarEventPart currentEvent = restOfEventPartsInDay.get(0);
            restOfEventPartsInDay.remove(0);
            eventsSplitIntoColumns.add(splitColumn(restOfEventPartsInDay, currentEvent));
            eventsSplitIntoColumns.addAll(arrange(restOfEventPartsInDay));
        }

        return eventsSplitIntoColumns;
    }

    private List<CalendarEventPart> splitColumn(final List<CalendarEventPart> restOfEventPartsInDay, final CalendarEventPart currentEvent) {
        final List<CalendarEventPart> eventsSplitColumn = new LinkedList<>();
        eventsSplitColumn.add(currentEvent);
        final Iterator<CalendarEventPart> iterator = restOfEventPartsInDay.iterator();
        while (iterator.hasNext()) {
            final CalendarEventPart otherEvent = iterator.next();
            if (!hasAnyConflictWithThisColumn(new ArrayList<>(eventsSplitColumn), otherEvent)) {
                iterator.remove();
                eventsSplitColumn.add(otherEvent);
            }
        }
        return eventsSplitColumn;
    }

    private boolean hasAnyConflictWithThisColumn(final List<CalendarEventPart> eventsSplitColumn,
                                                 final CalendarEventPart otherEvent) {
        for (final CalendarEventPart anyEventInSplitColumn : eventsSplitColumn) {
            if (anyEventInSplitColumn.hasConflictWith(otherEvent))
                return true;
        }
        return false;
    }
}
