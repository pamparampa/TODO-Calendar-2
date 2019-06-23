package com.example.radle.todo_calendar2.calendarView.dto;

import java.util.Objects;

public class CalendarLabel {

    private final String text;
    private final float textX;
    private final float textY;
    private final int fieldWidth;

    public CalendarLabel(final String text, final int textX, final int textY,
                         final int fieldWidth) {
        this.text = text;
        this.textX = textX;
        this.textY = textY;
        this.fieldWidth = fieldWidth;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.text, this.textX, this.textY);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarLabel)) return false;
        final CalendarLabel that = (CalendarLabel) o;
        return Float.compare(that.textX, this.textX) == 0 &&
                Float.compare(that.textY, this.textY) == 0 &&
                Objects.equals(this.text, that.text);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "CalendarLabel{" +
                "text='" + this.text + '\'' +
                ", textX=" + this.textX +
                ", textY=" + this.textY +
                '}';
    }

    public String getText() {
        return this.text;
    }

    public float getTextX() {
        return this.textX;
    }

    public float getTextY() {
        return this.textY;
    }

    public int getFieldWidth() {
        return this.fieldWidth;
    }
}
