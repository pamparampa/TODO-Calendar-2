package com.example.radle.todo_calendar2.todoList.view.dto;

import android.os.Parcel;

import com.example.radle.todo_calendar2.todoList.entity.Period;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_AUTUMN;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_MONTH;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_SPRING;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_WEEK;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_WINTER;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_YEAR;
import static com.example.radle.todo_calendar2.todoList.entity.Period.SOMETIME;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_AUTUMN;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_MONTH;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_SPRING;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_SUMMER;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_WEEK;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_WINTER;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_WINTER2;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_YEAR;
import static com.example.radle.todo_calendar2.todoList.entity.Period.TODAY;
import static com.example.radle.todo_calendar2.todoList.entity.Period.TOMORROW;

public class HeaderElement implements ToDoListElement {
    public static HeaderElement TODAY_HEADER = new HeaderElement("TODAY", Period.TODAY);
    public static HeaderElement TOMORROW_HEADER = new HeaderElement("TOMORROW", Period.TOMORROW);
    public static HeaderElement THIS_WEEK_HEADER = new HeaderElement("THIS WEEK", Period.THIS_WEEK);
    public static HeaderElement NEXT_WEEK_HEADER = new HeaderElement("NEXT WEEK", Period.NEXT_WEEK);
    public static HeaderElement THIS_MONTH_HEADER = new HeaderElement("THIS MONTH", Period.THIS_MONTH);
    public static HeaderElement NEXT_MONTH_HEADER = new HeaderElement("NEXT MONTH", Period.NEXT_MONTH);
    public static HeaderElement THIS_WINTER_HEADER = new HeaderElement("THIS WINTER", Period.THIS_WINTER);
    public static HeaderElement THIS_SPRING_HEADER = new HeaderElement("THIS SPRING", Period.THIS_SPRING);
    public static HeaderElement THIS_SUMMER_HEADER = new HeaderElement("THIS SUMMER", Period.THIS_SUMMER);
    public static HeaderElement THIS_AUTUMN_HEADER = new HeaderElement("THIS AUTUMN", Period.THIS_AUTUMN);
    public static HeaderElement THIS_WINTER2_HEADER = new HeaderElement("THIS WINTER", Period.THIS_WINTER);
    public static HeaderElement THIS_YEAR_HEADER = new HeaderElement("THIS YEAR", Period.THIS_YEAR);
    public static HeaderElement NEXT_WINTER_HEADER = new HeaderElement("NEXT WINTER", Period.NEXT_WINTER);
    public static HeaderElement NEXT_SPRING_HEADER = new HeaderElement("NEXT SPRING", Period.NEXT_SPRING);
    public static HeaderElement NEXT_SUMMER_HEADER = new HeaderElement("NEXT SUMMER", Period.NEXT_SUMMER);
    public static HeaderElement NEXT_AUTUMN_HEADER = new HeaderElement("NEXT AUTUMN", Period.NEXT_AUTUMN);
    public static HeaderElement NEXT_YEAR_HEADER = new HeaderElement("NEXT YEAR", Period.NEXT_YEAR);
    public static HeaderElement SOMETIME_HEADER = new HeaderElement("SOMETIME", Period.SOMETIME);

    private static final Map<Period, HeaderElement> headersByPeriods = new HashMap<Period, HeaderElement>() {
        {
            put(TODAY, TODAY_HEADER);
            put(TOMORROW, TOMORROW_HEADER);
            put(THIS_WEEK, THIS_WEEK_HEADER);
            put(NEXT_WEEK, NEXT_WEEK_HEADER);
            put(THIS_MONTH, THIS_MONTH_HEADER);
            put(NEXT_MONTH, NEXT_MONTH_HEADER);
            put(THIS_WINTER, THIS_WINTER_HEADER);
            put(THIS_SPRING, THIS_SPRING_HEADER);
            put(THIS_SUMMER, THIS_SUMMER_HEADER);
            put(THIS_AUTUMN, THIS_AUTUMN_HEADER);
            put(THIS_WINTER2, THIS_WINTER2_HEADER);
            put(THIS_YEAR, THIS_YEAR_HEADER);
            put(NEXT_WINTER, NEXT_WINTER_HEADER);
            put(NEXT_SPRING, NEXT_SPRING_HEADER);
            put(NEXT_AUTUMN, NEXT_AUTUMN_HEADER);
            put(NEXT_YEAR, NEXT_YEAR_HEADER);
            put(SOMETIME, SOMETIME_HEADER);
        }};
    private final String title;
    private Period period;

    public HeaderElement(String title, Period period) {
        this.title = title;
        this.period = period;
    }

    public static HeaderElement getForPeriod(Period period) {
        return headersByPeriods.get(period);
    }

    public static final Creator<HeaderElement>  CREATOR = new Creator<HeaderElement>() {
        @Override
        public HeaderElement createFromParcel(final Parcel in) {
            return new HeaderElement(in);
        }

        @Override
        public HeaderElement[] newArray(final int size) {
            return new HeaderElement[size];
        }
    };

    public HeaderElement(Parcel in) {
        in.readInt();
        this.title = in.readString();
        this.period = Period.valueOf(in.readString());
    }

    @Override
    public int getViewType() {
        return ToDoListElement.HEADER_VIEW_TYPE;
    }

    @Override
    public Period getPeriod() {
        return this.period;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getViewType());
        dest.writeString(this.title);
        dest.writeString(this.period.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderElement that = (HeaderElement) o;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "HeaderElement{" +
                "title='" + title + '\'' +
                '}';
    }
}
