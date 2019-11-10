package com.example.radle.todo_calendar2.calendarView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.radle.todo_calendar2.calendarView.scrolling.ScrollEffectParameters;
import com.example.radle.todo_calendar2.calendarView.scrolling.ScrollVelocity;
import com.example.radle.todo_calendar2.calendarView.scrolling.ScrollingHandler;
import com.example.radle.todo_calendar2.calendarView.tools.WeekBeginningDateTimeProvider;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class CalendarView extends HorizontalScrollView implements OnHorizontalScrollListener {
    private static final int CURRENTLY_VISBLE_VIEW_INDEX = 1;
    private final LinearLayout linearLayout;
    private final WeekBeginningDateTimeProvider weekBeginningDateTimeProvider =
            new WeekBeginningDateTimeProvider();
    private final Point outSize = new Point();
    private int width;
    private Map<ScrollEffectParameters.Side, Integer> sideToPosition;
    private int lastScrollPosition;
    private float lastTouchX;

    public CalendarView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        this.linearLayout = new LinearLayout(context, attrs) {
            @Override
            protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
                super.onSizeChanged(w, h, oldw, oldh);
                CalendarView.this.width = w / 100;
            }
        };
        this.linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.linearLayout.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        this.linearLayout.setDividerPadding(0);
        for (int i = -50; i < 50; i++) {
            this.linearLayout.addView(newSingleWeekView(
                    this.weekBeginningDateTimeProvider.getWeekBeginning(LocalDateTime.now(), i)));
        }

        this.width = getScreenWidth();
        createSideToPositionMap();

        addView(this.linearLayout);

    }

    private int getScreenWidth() {
        ((Activity) getContext()).getWindowManager().getDefaultDisplay()
                .getSize(this.outSize);
        return this.outSize.x;
    }

    private void createSideToPositionMap() {
        this.sideToPosition = new HashMap<ScrollEffectParameters.Side, Integer>() {{
            put(ScrollEffectParameters.Side.LEFT, 0);
            put(ScrollEffectParameters.Side.SAME, CalendarView.this.width);
            put(ScrollEffectParameters.Side.RIGHT, CalendarView.this.width * 2);
        }};
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r,
                            final int b) {
        super.onLayout(changed, l, t, r, b);
        System.out.println("On Layout");
        //scrollTo(this.width * 10, 0);
        this.lastScrollPosition = getScrollX();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                final ScrollVelocity scrollVelocity =
                        ScrollVelocity.finishMeasurement(getScrollX(), LocalTime.now());
                final ScrollEffectParameters scrollEffectParameters =
                        new ScrollingHandler(this.width).handleScroll(scrollVelocity,
                                getCurrentlyVisibleDateTime());
                scroll(scrollEffectParameters);
                return true;
            /*case MotionEvent.ACTION_MOVE:
                scrollBy((int) (this.lastTouchX - ev.getX()), 0);
                this.lastTouchX = ev.getX();
                return true;*/
        }
        return super.onTouchEvent(ev);
    }

    private LocalDateTime getCurrentlyVisibleDateTime() {
        final SingleWeekView currentlyVisibleWeekView =
                (SingleWeekView) this.linearLayout.getChildAt(
                        CURRENTLY_VISBLE_VIEW_INDEX);
        return currentlyVisibleWeekView.getDateTime();
    }

    private LocalDateTime nextWeekBeginning() {
        return this.weekBeginningDateTimeProvider.getNextWeekBeginning(LocalDateTime.now());
    }

    private LocalDateTime currentWeekBeginning() {
        return this.weekBeginningDateTimeProvider.getWeekBeginning(LocalDateTime.now());
    }

    private LocalDateTime previousWeekBeginning() {
        return this.weekBeginningDateTimeProvider.getPrevWeekBeginning(LocalDateTime.now());
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

    private void scroll(final ScrollEffectParameters parameters) {

        System.out.println(parameters.getSide());
        final int scrollPosition = this.lastScrollPosition + (parameters
                .getSide() == ScrollEffectParameters.Side.LEFT ? -this.width : this.width);
        final ObjectAnimator animator = ObjectAnimator.ofInt(this, "scrollX", scrollPosition);
        //animator.addListener(new AnimationWithPostActionListener(parameters));
        animator.setDuration(500).start();
        this.lastScrollPosition = scrollPosition;
    }

    @Override
    public void startScrolling(final float x) {
        this.lastTouchX = x;
        ScrollVelocity.startMeasurement(getScrollX(), LocalTime.now());
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
            if (elementsToChange.isPresent()) {
                CalendarView.this.linearLayout.addView(
                        newSingleWeekView(elementsToChange.get().getNewElementDateTime()),
                        this.parameters.getSide() == ScrollEffectParameters.Side.LEFT ? 0 :
                                CalendarView.this.linearLayout.getChildCount());
                CalendarView.this.linearLayout
                        .removeViewAt(elementsToChange.get().getElementToRemoveId());

                refreshScrollPosition();
            }
        }

        @Override
        public void onAnimationCancel(final Animator animator) {

        }

        @Override
        public void onAnimationRepeat(final Animator animator) {

        }

    }

    private void refreshScrollPosition() {
        scrollTo(this.width, 0);
    }
}
