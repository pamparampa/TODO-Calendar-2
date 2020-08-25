package com.example.radle.todo_calendar2.calendarView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.scrolling.ScrollEffectParameters;
import com.example.radle.todo_calendar2.calendarView.scrolling.ScrollVelocity;
import com.example.radle.todo_calendar2.calendarView.scrolling.ScrollingHandler;
import com.example.radle.todo_calendar2.calendarView.tools.WeekBeginningDateTimeProvider;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class CalendarView extends HorizontalScrollView implements OnHorizontalScrollListener {
    private static final int CURRENTLY_VISBLE_VIEW_INDEX = 1;
    private static final int FIRST_VIEW_INDEX = 0;
    private static final int LAST_VIEW_INDEX = 2;
    private final LinearLayout linearLayout;
    private final WeekBeginningDateTimeProvider weekBeginningDateTimeProvider =
            new WeekBeginningDateTimeProvider();
    private int width;
    private Map<ScrollEffectParameters.Side, Integer> sideToPosition;
    private onNewWeekListener onNewWeekListener;
    private final Map<LocalDateTime, SinglePeriodView> periodViews = new HashMap<>();

    public CalendarView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        this.linearLayout = prepareLinearLayout(context, attrs);
        final LocalDateTime now = LocalDateTime.now();
        addPeriodView(previousWeekBeginning(now));
        addPeriodView(currentWeekBeginning(now));
        addPeriodView(nextWeekBeginning(now));
        addView(this.linearLayout);
    }

    public void setOnNewWeekListener(final onNewWeekListener onNewWeekListener) {
        this.onNewWeekListener = onNewWeekListener;
    }

    public TimeInterval getCurrentlyAccessibleTimeInterval() {
        return new TimeInterval(getFirstDateTimeAtIndex(FIRST_VIEW_INDEX),
                getFirstDateTimeAtIndex(LAST_VIEW_INDEX).plusWeeks(1));
    }

    public void addEvents(final LocalDateTime periodDateTime, final List<CalendarEvent> events) {
        if (this.periodViews.containsKey(periodDateTime)) {
            final SinglePeriodView singlePeriodView = this.periodViews.get(periodDateTime);
            if (singlePeriodView != null) {
                singlePeriodView.addEvents(events);
                singlePeriodView.invalidate();
            }
        }
    }

    public void fillWithEvents(final List<CalendarEvent> events) {
        for (int i = 0; i < this.linearLayout.getChildCount(); i++) {
            final SinglePeriodView singlePeriodView =
                    (SinglePeriodView) this.linearLayout.getChildAt(i);
            singlePeriodView.fillWithEvents(events);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP)
            return handleScroll();
        return super.onTouchEvent(ev);
    }

    @Override
    public void startScrolling(final float x) {
        ScrollVelocity.startMeasurement(getScrollX(), LocalTime.now());
    }

    @Override
    public void finishScrollingVertically() {
        ScrollVelocity.finishMeasurement(getScrollX(), LocalTime.now());
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r,
                            final int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(this.width, 0);
    }

    private LinearLayout prepareLinearLayout(final Context context, final AttributeSet attrs) {
        final LinearLayout linearLayout = new LinearLayout(context, attrs) {
            @Override
            protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
                super.onSizeChanged(w, h, oldw, oldh);
                CalendarView.this.width = w / 3;
                createSideToPositionMap();
            }
        };
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        return linearLayout;
    }

    private void createSideToPositionMap() {
        this.sideToPosition = new HashMap<ScrollEffectParameters.Side, Integer>() {{
            put(ScrollEffectParameters.Side.LEFT, 0);
            put(ScrollEffectParameters.Side.SAME, CalendarView.this.width);
            put(ScrollEffectParameters.Side.RIGHT, CalendarView.this.width * 2);
        }};
    }

    private void addPeriodView(final LocalDateTime firstDateTime) {
        final SingleWeekView periodView = newSingleWeekView(firstDateTime);
        this.linearLayout.addView(periodView);
        this.periodViews.put(firstDateTime, periodView);
    }

    private SingleWeekView newSingleWeekView(final LocalDateTime localDateTime) {
        final SingleWeekView singleWeekView = new SingleWeekView(getContext(), null);
        singleWeekView.setParams(new SinglePeriodView.PeriodParams(localDateTime));
        singleWeekView
                .setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        singleWeekView.setOnHorizontalScrollListener(this);

        return singleWeekView;
    }

    private LocalDateTime previousWeekBeginning(final LocalDateTime now) {
        return this.weekBeginningDateTimeProvider.getPrevWeekBeginning(now);
    }

    private LocalDateTime currentWeekBeginning(final LocalDateTime now) {
        return this.weekBeginningDateTimeProvider.getWeekBeginning(now);
    }

    private LocalDateTime nextWeekBeginning(final LocalDateTime now) {
        return this.weekBeginningDateTimeProvider.getNextWeekBeginning(now);
    }

    private boolean handleScroll() {
        final ScrollVelocity scrollVelocity =
                ScrollVelocity.finishMeasurement(getScrollX(), LocalTime.now());
        final ScrollEffectParameters scrollEffectParameters =
                new ScrollingHandler(this.width).handleScroll(scrollVelocity,
                        getCurrentlyVisibleDateTime());
        scroll(scrollEffectParameters);
        return true;
    }

    private LocalDateTime getCurrentlyVisibleDateTime() {
        return getFirstDateTimeAtIndex(CURRENTLY_VISBLE_VIEW_INDEX);
    }

    private LocalDateTime getFirstDateTimeAtIndex(final int viewIndex) {
        final SingleWeekView currentlyVisibleWeekView =
                (SingleWeekView) this.linearLayout.getChildAt(
                        viewIndex);
        return currentlyVisibleWeekView.getDateTime();
    }

    private void scroll(final ScrollEffectParameters parameters) {
        final int scrollPosition = this.sideToPosition.get(parameters.getSide());
        final ObjectAnimator animator = ObjectAnimator.ofInt(this, "scrollX", scrollPosition);
        prepareVerticalPosition(parameters);
        animator.addListener(new AnimationWithPostActionListener(parameters));
        animator.setDuration(parameters.getAnimationDuration()).start();
    }

    private void prepareVerticalPosition(final ScrollEffectParameters parameters) {
        if (parameters.getSide() == ScrollEffectParameters.Side.LEFT)
            prepareVerticalPositionOfChild(0);
        else if (parameters.getSide() == ScrollEffectParameters.Side.RIGHT)
            prepareVerticalPositionOfChild(2);
    }

    private void prepareVerticalPositionOfChild(final int i) {
        final SinglePeriodView nextVisibleWeekView =
                (SinglePeriodView) this.linearLayout.getChildAt(i);
        nextVisibleWeekView.scrollTo(getCurrentVisiblePosition());
    }

    private int getCurrentVisiblePosition() {
        final SinglePeriodView currentWeekView = (SinglePeriodView) this.linearLayout.getChildAt(1);
        return currentWeekView.getCurrentVisiblePosition();
    }

    private void changeCalendarStateAfterScroll(
            final ScrollEffectParameters.ElementsToChangeAfterScroll elementsToChange,
            final ScrollEffectParameters.Side side) {
        final LocalDateTime newPeriodViewDateTime = addNewPeriodView(elementsToChange, side);
        removeUnnecessaryView(elementsToChange);
        scrollTo(CalendarView.this.width, 0);
        this.onNewWeekListener.newWeek(newPeriodViewDateTime);
    }

    private LocalDateTime addNewPeriodView(final ScrollEffectParameters.ElementsToChangeAfterScroll elementsToChange, final ScrollEffectParameters.Side side) {
        final LocalDateTime newElementDateTime = elementsToChange.getNewElementDateTime();
        final SingleWeekView periodView = newSingleWeekView(newElementDateTime);
        CalendarView.this.linearLayout.addView(periodView,
                side == ScrollEffectParameters.Side.LEFT ? 0 :
                        CalendarView.this.linearLayout.getChildCount());
        this.periodViews.put(newElementDateTime, periodView);
        return newElementDateTime;
    }

    private void removeUnnecessaryView(final ScrollEffectParameters.ElementsToChangeAfterScroll elementsToChange) {
        final SinglePeriodView periodView =
                (SinglePeriodView) this.linearLayout
                        .getChildAt(elementsToChange.getElementToRemoveId());
        this.periodViews.remove(periodView.getDateTime());
        CalendarView.this.linearLayout
                .removeViewAt(elementsToChange.getElementToRemoveId());
    }

    private class AnimationWithPostActionListener implements Animator.AnimatorListener {

        private final ScrollEffectParameters parameters;

        AnimationWithPostActionListener(final ScrollEffectParameters parameters) {
            this.parameters = parameters;
        }

        @Override
        public void onAnimationStart(final Animator animator) {

        }

        @Override
        public void onAnimationEnd(final Animator animator) {
            final Optional<ScrollEffectParameters.ElementsToChangeAfterScroll>
                    elementsToChange = this.parameters.getElementsToChangeAfrerScroll();
            elementsToChange.ifPresent(
                    parameters -> changeCalendarStateAfterScroll(elementsToChange.get(),
                            this.parameters.getSide()));
        }


        @Override
        public void onAnimationCancel(final Animator animator) {

        }

        @Override
        public void onAnimationRepeat(final Animator animator) {

        }
    }

    public class TimeInterval {
        private final LocalDateTime from;
        private final LocalDateTime to;

        TimeInterval(final LocalDateTime from, final LocalDateTime to) {
            this.from = from;
            this.to = to;
        }

        public LocalDateTime getFrom() {
            return this.from;
        }

        public LocalDateTime getTo() {
            return this.to;
        }
    }
}
