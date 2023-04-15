package com.example.radle.todo_calendar2.todoList.view.dto;

import android.os.Parcel;

import com.example.radle.todo_calendar2.todoList.entity.Period;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_AUTUMN;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_MONTH;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_SPRING;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_SUMMER;
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

public class EncourageElement implements ToDoListElement {
    public static final EncourageElement ENCOURAGE_TO_ADD_FIRST_TASK = new EncourageElement("Add your first task", true);
    public static final EncourageElement ENCOURAGE_TO_ADD_TODAY_TASK = new EncourageElement("Add next task for today", TODAY);
    public static final EncourageElement ENCOURAGE_TO_ADD_TOMORROW_TASK = new EncourageElement("Add next task for tomorrow", TOMORROW);
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_WEEK_TASK = new EncourageElement("Add next task for this week", THIS_WEEK);
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_WEEK_TASK = new EncourageElement("Add next task for next week", NEXT_WEEK);
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_MONTH_TASK = new EncourageElement("Add next task for this month", THIS_MONTH);
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_MONTH_TASK = new EncourageElement("Add next task for next month", NEXT_MONTH);
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_WINTER_TASK = new EncourageElement("Add next task for this winter", THIS_WINTER);
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_SPRING_TASK = new EncourageElement("Add next task for this spring", THIS_SPRING);
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_SUMMER_TASK = new EncourageElement("Add next task for this summer", THIS_SUMMER);
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_AUTUMN_TASK = new EncourageElement("Add next task for this autumn", THIS_AUTUMN);
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_WINTER2_TASK = new EncourageElement("Add next task for this winter", THIS_WINTER2);
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_YEAR_TASK = new EncourageElement("Add next task for this year", THIS_YEAR);
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_WINTER_TASK = new EncourageElement("Add next task for next winter", NEXT_WINTER);
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_SPRING_TASK = new EncourageElement("Add next task for next spring", NEXT_SPRING);
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_SUMMER_TASK = new EncourageElement("Add next task for next summer", NEXT_SUMMER);
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_AUTUMN_TASK = new EncourageElement("Add next task for next autumn", NEXT_AUTUMN);
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_YEAR_TASK = new EncourageElement("Add next task for next year", NEXT_YEAR);
    public static final EncourageElement ENCOURAGE_TO_ADD_SOMETIME_TASK = new EncourageElement("Add next task for sometime", SOMETIME);

    private static final Map<Period, EncourageElement> encouragesByPeriods = new HashMap<Period, EncourageElement>() {
        {
            put(TODAY, ENCOURAGE_TO_ADD_TODAY_TASK);
            put(TOMORROW, ENCOURAGE_TO_ADD_TOMORROW_TASK);
            put(THIS_WEEK, ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
            put(NEXT_WEEK, ENCOURAGE_TO_ADD_NEXT_WEEK_TASK);
            put(THIS_MONTH, ENCOURAGE_TO_ADD_THIS_MONTH_TASK);
            put(NEXT_MONTH, ENCOURAGE_TO_ADD_NEXT_MONTH_TASK);
            put(THIS_WINTER, ENCOURAGE_TO_ADD_THIS_WINTER_TASK);
            put(THIS_SPRING, ENCOURAGE_TO_ADD_THIS_SPRING_TASK);
            put(THIS_SUMMER, ENCOURAGE_TO_ADD_THIS_SUMMER_TASK);
            put(THIS_AUTUMN, ENCOURAGE_TO_ADD_THIS_AUTUMN_TASK);
            put(THIS_WINTER2, ENCOURAGE_TO_ADD_THIS_WINTER2_TASK);
            put(THIS_YEAR, ENCOURAGE_TO_ADD_THIS_YEAR_TASK);
            put(NEXT_WINTER, ENCOURAGE_TO_ADD_NEXT_WINTER_TASK);
            put(NEXT_SPRING, ENCOURAGE_TO_ADD_NEXT_SPRING_TASK);
            put(NEXT_SUMMER, ENCOURAGE_TO_ADD_NEXT_SUMMER_TASK);
            put(NEXT_AUTUMN, ENCOURAGE_TO_ADD_NEXT_AUTUMN_TASK);
            put(NEXT_YEAR, ENCOURAGE_TO_ADD_NEXT_YEAR_TASK);
            put(SOMETIME, ENCOURAGE_TO_ADD_SOMETIME_TASK);
        }};


    public static final Creator<EncourageElement> CREATOR = new Creator<EncourageElement>() {
        @Override
        public EncourageElement createFromParcel(Parcel parcel) {
            return new EncourageElement(parcel);
        }

        @Override
        public EncourageElement[] newArray(int size) {
            return new EncourageElement[size];
        }
    };

    private final String message;
    private final Period period;
    private boolean isFirstEncourage = false;

    public static EncourageElement getByPeriod(Period period) {
        return encouragesByPeriods.get(period);
    }

    private EncourageElement(Parcel parcel) {
        parcel.readInt();
        this.message = parcel.readString();
        this.period = Period.valueOf(parcel.readString());
    }

    public EncourageElement(String message, boolean isFirstEncourage) {
        this(message, SOMETIME);
        this.isFirstEncourage = isFirstEncourage;
    }

    private EncourageElement(String message, Period period) {
        this.message = message;
        this.period = period;
    }

    @Override
    public int getViewType() {
        return ToDoListElement.ENCOURAGE_ELEMENT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getViewType());
        dest.writeString(this.message);
        dest.writeString(this.period.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncourageElement that = (EncourageElement) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "EncourageElement{" +
                "message='" + message + '\'' +
                '}';
    }

    public Period getPeriod() {
        return this.period;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isFirstEncourage() {
        return isFirstEncourage;
    }
}
