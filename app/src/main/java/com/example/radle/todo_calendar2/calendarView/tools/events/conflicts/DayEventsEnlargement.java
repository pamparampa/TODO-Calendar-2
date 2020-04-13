package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DayEventsEnlargement {
    public List<ChainedCalendarEventPart> enlarge(final List<List<CalendarEventPart>> eventsSplitIntoColumns) {
        final Map<CalendarEventPart, ChainedCalendarEventPart> processedEventParts =
                new HashMap<>();
        return new Helper(eventsSplitIntoColumns, processedEventParts, 0,
                eventsSplitIntoColumns.size()).createChains();
    }

    private class Helper {

        private final List<List<CalendarEventPart>> eventsSplitIntoColumns;
        private final Map<CalendarEventPart, ChainedCalendarEventPart> processedEventParts;
        private final int columnShift;
        private final int numberOfColumns;

        Helper(final List<List<CalendarEventPart>> eventsSplitIntoColumns,
               final Map<CalendarEventPart, ChainedCalendarEventPart> processedEventParts,
               final int columnShift, final int numberOfColumns) {
            this.eventsSplitIntoColumns = eventsSplitIntoColumns;
            this.processedEventParts = processedEventParts;
            this.columnShift = columnShift;
            this.numberOfColumns = numberOfColumns;
        }

        List<ChainedCalendarEventPart> createChains() {
            if (this.eventsSplitIntoColumns.size() == 0) {
                return Collections.emptyList();
            } else {
                return getChainedCalendarEventParts();
            }
        }

        private List<ChainedCalendarEventPart> getChainedCalendarEventParts() {
            final List<ChainedCalendarEventPart> chainedCalendarEventParts = new LinkedList<>();
            for (final CalendarEventPart firstColumnEventPart : this.eventsSplitIntoColumns.get(0))
                chainedCalendarEventParts.add(
                        getChainedCalendarEventPart(firstColumnEventPart));
            return chainedCalendarEventParts;
        }

        private ChainedCalendarEventPart getChainedCalendarEventPart(final CalendarEventPart firstColumnEventPart) {
            final ChainedCalendarEventPart chainedCalendarEventPart;
            if (this.processedEventParts.containsKey(firstColumnEventPart))
                chainedCalendarEventPart = this.processedEventParts.get(firstColumnEventPart);
            else {
                chainedCalendarEventPart =
                        createChainedCalendarEventPart(firstColumnEventPart);
                this.processedEventParts.put(firstColumnEventPart, chainedCalendarEventPart);
            }
            return chainedCalendarEventPart;
        }

        private ChainedCalendarEventPart createChainedCalendarEventPart(
                final CalendarEventPart currentEventPart) {
            final ChainedCalendarEventPartProcessingParameters parameters =
                    countChainProcessingParameters(currentEventPart);
            return new ChainedCalendarEventPart(
                    currentEventPart.withWidth(parameters.getFrom(), parameters.getTo(),
                            this.numberOfColumns),
                    new Helper(
                            subList(parameters.getEventsFromRightColumns(),
                                    parameters.getColumnOfNextConflict()),
                            this.processedEventParts,
                            countAbsoluteIndexOfColumn(parameters.getColumnOfNextConflict()),
                            this.numberOfColumns)
                            .createChains());
        }

        private ChainedCalendarEventPartProcessingParameters countChainProcessingParameters(
                final CalendarEventPart currentEventPart) {
            List<List<CalendarEventPart>> eventsFromRightColumns = new ArrayList<>();
            final float from = this.columnShift;
            float to = this.columnShift + 1;
            int columnOfNextConflict = 0;
            if (this.eventsSplitIntoColumns.size() > 1) {
                eventsFromRightColumns =
                        getEventsFromRightColumnsWithConflicts(currentEventPart);
                columnOfNextConflict = getColumnOfNextConflict(eventsFromRightColumns);
                to = countAbsoluteIndexOfColumn(columnOfNextConflict);
            }
            return new ChainedCalendarEventPartProcessingParameters(eventsFromRightColumns,
                    columnOfNextConflict, from, to);
        }

        private List<List<CalendarEventPart>> getEventsFromRightColumnsWithConflicts(
                final CalendarEventPart currentEventPart) {
            final List<List<CalendarEventPart>> rightColumns =
                    subList(this.eventsSplitIntoColumns, 1);
            final List<List<CalendarEventPart>> eventsWithConflicts = new LinkedList<>();
            for (final List<CalendarEventPart> eventsFromColumn : rightColumns)
                eventsWithConflicts.add(createColumnOfEventsWithConflicts(currentEventPart,
                        eventsFromColumn));
            return eventsWithConflicts;
        }

        private List<List<CalendarEventPart>> subList(
                final List<List<CalendarEventPart>> eventsFromRightColumns, final int startIndex) {
            return eventsFromRightColumns.subList(startIndex,
                    eventsFromRightColumns.size());
        }

        private List<CalendarEventPart> createColumnOfEventsWithConflicts(final CalendarEventPart currentEventPart, final List<CalendarEventPart> eventsFromColumn) {
            final List<CalendarEventPart> columnOfEventsWithConflicts = new LinkedList<>();
            for (final CalendarEventPart eventFromColumn : eventsFromColumn) {
                if (currentEventPart.hasConflictWith(eventFromColumn))
                    columnOfEventsWithConflicts.add(eventFromColumn);
            }
            return columnOfEventsWithConflicts;
        }

        private int getColumnOfNextConflict(final List<List<CalendarEventPart>> eventsFromRightColumns) {
            for (int i = 0; i < eventsFromRightColumns.size(); i++) {
                if (eventsFromRightColumns.get(i).size() > 0) {
                    return i;
                }
            }
            return eventsFromRightColumns.size();
        }

        private int countAbsoluteIndexOfColumn(final int columnOfNextConflict) {
            return this.columnShift + columnOfNextConflict + 1;
        }

    }

    private class ChainedCalendarEventPartProcessingParameters {


        private final List<List<CalendarEventPart>> eventsFromRightColumns;
        private final int columnOfNextConflict;
        private final float from;
        private final float to;

        ChainedCalendarEventPartProcessingParameters(
                final List<List<CalendarEventPart>> eventsFromRightColumns,
                final int columnOfNextConflict, final float from, final float to) {
            this.eventsFromRightColumns = eventsFromRightColumns;
            this.columnOfNextConflict = columnOfNextConflict;
            this.from = from;
            this.to = to;
        }

        List<List<CalendarEventPart>> getEventsFromRightColumns() {
            return this.eventsFromRightColumns;
        }

        int getColumnOfNextConflict() {
            return this.columnOfNextConflict;
        }


        float getFrom() {
            return this.from;
        }

        float getTo() {
            return this.to;
        }
    }
}
