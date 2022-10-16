package com.example.radle.todo_calendar2.todoList.view.dto;

import android.os.Parcel;

import com.example.radle.todo_calendar2.todoList.entity.Period;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_MONTH;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_WEEK;
import static com.example.radle.todo_calendar2.todoList.entity.Period.NEXT_YEAR;
import static com.example.radle.todo_calendar2.todoList.entity.Period.SOMETIME;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_AUTUMN;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_MONTH;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_SPRING;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_SUMMER;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_WEEK;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_WINTER;
import static com.example.radle.todo_calendar2.todoList.entity.Period.THIS_YEAR;
import static com.example.radle.todo_calendar2.todoList.entity.Period.TODAY;
import static com.example.radle.todo_calendar2.todoList.entity.Period.TOMORROW;

public class EncourageElement implements ToDoListElement {
    public static final EncourageElement ENCOURAGE_TO_ADD_FIRST_TASK = new EncourageElement("Add your first task");
    public static final EncourageElement ENCOURAGE_TO_ADD_TODAY_TASK = new EncourageElement("Add next task for today");
    public static final EncourageElement ENCOURAGE_TO_ADD_TOMORROW_TASK = new EncourageElement("Add next task for tomorrow");
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_WEEK_TASK = new EncourageElement("Add next task for this week");
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_WEEK_TASK = new EncourageElement("Add next task for next week");
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_MONTH_TASK = new EncourageElement("Add next task for this month");
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_MONTH_TASK = new EncourageElement("Add next task for next month");
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_WINTER_TASK = new EncourageElement("Add next task for this winter");
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_SPRING_TASK = new EncourageElement("Add next task for this spring");
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_SUMMER_TASK = new EncourageElement("Add next task for this summer");
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_AUTUMN_TASK = new EncourageElement("Add next task for this autumn");
    public static final EncourageElement ENCOURAGE_TO_ADD_THIS_YEAR_TASK = new EncourageElement("Add next task for this year");
    public static final EncourageElement ENCOURAGE_TO_ADD_NEXT_YEAR_TASK = new EncourageElement("Add next task for next year");
    public static final EncourageElement ENCOURAGE_TO_ADD_SOMETIME_TASK = new EncourageElement("Add next task for sometime");

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
            put(THIS_YEAR, ENCOURAGE_TO_ADD_THIS_YEAR_TASK);
            put(NEXT_YEAR, ENCOURAGE_TO_ADD_NEXT_YEAR_TASK);
            put(SOMETIME, ENCOURAGE_TO_ADD_SOMETIME_TASK);
        }};

    private final String message;

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

    public static EncourageElement getByPeriod(Period period) {
        return encouragesByPeriods.get(period);
    }

    private EncourageElement(Parcel parcel) {
        parcel.readInt();
        this.message = parcel.readString();
    }

    private EncourageElement(String message) {
        this.message = message;
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
}
