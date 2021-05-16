package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.radle.todo_calendar2.calendarView.tools.CalendarEventElementsComposer;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;
import com.example.radle.todo_calendar2.calendarView.tools.ParamsBuilder;
import com.example.radle.todo_calendar2.calendarView.tools.events.EventPartBoundsResolver;
import com.example.radle.todo_calendar2.calendarView.tools.events.EventsComposer;
import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.CalendarEventPartWithWidth;
import com.example.radle.todo_calendar2.dto.IdWithDataTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BoardScrollView extends ScrollView {

    private final LinearLayout linearLayout;
    private final Paint borderPaint = new Paint();
    private BoardParams params;
    private OnHorizontalScrollListener onHorizontalScrollListener;
    private List<CalendarRowView> rowViews = new ArrayList<>(24);
    private EventsComposer eventsComposer;
    private EventPartBoundsResolver eventPartBoundsResolver;
    private List<CalendarEvent> events = new LinkedList<>();
    private final TextPaint eventTiltePaint = new TextPaint();

    public BoardScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.linearLayout = new LinearLayout(context, null);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setAntiAlias(true);
        this.eventTiltePaint.setAntiAlias(true);
    }

    public void setOnHorizontalScrollListener(final OnHorizontalScrollListener onHorizontalScrollListener) {
        this.onHorizontalScrollListener = onHorizontalScrollListener;
        for (final CalendarRowView rowView : this.rowViews) {
            rowView.setOnHorizontalScrollListener(onHorizontalScrollListener);
        }
    }

    public void setParams(final BoardParams params) {
        this.params = params;
        try {
            createRowViews();
            initLinearLayout();
            addView(this.linearLayout);
            initEventsTools();
            this.eventTiltePaint
                    .setTextSize(new CalendarEventElementsComposer().getTextSize(this.params));

        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }

    private void initEventsTools() {
        try {
            this.eventsComposer = new EventsComposer(this.params.firstDateTime);
            this.eventPartBoundsResolver = new EventPartBoundsResolver(this.params);
        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }

    public void fillWithEvents(final List<CalendarEvent> events) {
        this.events = events;
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        for (final CalendarEventPartWithWidth composedEvent : this.eventsComposer.compose(
                this.events)) {
            drawEventPart(canvas, composedEvent);
        }
    }

    private void drawEventPart(final Canvas canvas,
                               final CalendarEventPartWithWidth composedEvent) {
        final Rect rect = drawEventRect(canvas, composedEvent);
        drawEventTitle(canvas, composedEvent, rect);
    }

    private Rect drawEventRect(final Canvas canvas,
                               final CalendarEventPartWithWidth composedEvent) {
        final Rect rect = this.eventPartBoundsResolver.resolveBounds(composedEvent);
        canvas.drawRect(rect, getEventPaint(composedEvent.getColor()));
        canvas.drawRect(rect, this.borderPaint);
        return rect;
    }

    private Paint getEventPaint(final int color) {
        final Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        return paint;
    }

    private void drawEventTitle(final Canvas canvas,
                                final CalendarEventPartWithWidth composedEvent, final Rect rect) {
        final StaticLayout staticLayout = StaticLayout.Builder.obtain(composedEvent.getTitle(),
                0, composedEvent.getTitle().length(), this.eventTiltePaint, rect.width())
                .setAlignment(Layout.Alignment.ALIGN_CENTER).build();
        canvas.save();
        canvas.translate(rect.left, rect.top);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    private void initLinearLayout() {
        this.linearLayout.removeAllViews();
        for (final CalendarRowView rowView : this.rowViews) {
            this.linearLayout.addView(rowView);
        }
        this.linearLayout
                .setLayoutParams(
                        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.linearLayout.setOrientation(LinearLayout.VERTICAL);
    }

    private void createRowViews() throws TimeNotAlignedException {
        final List<IdWithDataTime> idWithDataTimes =
                new DateTimesCollector().collectRowsForWeek(this.params.getFirstDateTime());
        this.rowViews = new ArrayList<>(24);
        for (final IdWithDataTime idWithDataTime : idWithDataTimes) {
            final CalendarRowView rowView = initRowView(idWithDataTime);
            this.rowViews.add(rowView);
        }
    }

    private CalendarRowView initRowView(final IdWithDataTime rowIdWithFirstDateTime) {
        final CalendarRowView calendarRowView = new CalendarRowView(getContext(), null);
        calendarRowView.setParams(new ParamsBuilder().getRowParamsByBoardParams(this.params,
                rowIdWithFirstDateTime));
        calendarRowView.setOnHorizontalScrollListener(this.onHorizontalScrollListener);
        return calendarRowView;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        if (this.onHorizontalScrollListener != null) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN)
                this.onHorizontalScrollListener.startScrolling(ev.getX());
            else if (ev.getAction() == MotionEvent.ACTION_UP) {
                this.onHorizontalScrollListener.finishScrolling(ev.getX(), ev.getY());
            }
        }
        return super.onTouchEvent(ev);
    }

    public void addEvents(final List<CalendarEvent> events) {
        this.events.addAll(events);
        invalidate();
    }

    public void handleClick(ClickPoint endPoint) {

    }

    public static class BoardParams {
        private final int numberOfColumns;
        private final LocalDateTime firstDateTime;
        private final int rowHeight;
        private final int width;

        public BoardParams(final int width, final int rowHeight,
                           final int numberOfColumns,
                           final LocalDateTime firstDateTime) {
            this.width = width;
            this.numberOfColumns = numberOfColumns;
            this.firstDateTime = firstDateTime;
            this.rowHeight = rowHeight;
        }

        public int getNumberOfColumns() {
            return this.numberOfColumns;
        }

        public int getRowHeight() {
            return this.rowHeight;
        }

        public int getWidth() {
            return this.width;
        }

        public LocalDateTime getFirstDateTime() {
            return this.firstDateTime;
        }
    }
}
