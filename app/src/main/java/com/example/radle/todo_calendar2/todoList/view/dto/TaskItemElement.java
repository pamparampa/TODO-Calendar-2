package com.example.radle.todo_calendar2.todoList.view.dto;

import android.os.Parcel;

import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class TaskItemElement implements ToDoListElement {
    private final String id;
    private String taskTitle;
    private Period period;
    private final boolean isDone;
    private int viewType;

    public static final Creator<TaskItemElement> CREATOR = new Creator<TaskItemElement>() {
        @Override
        public TaskItemElement createFromParcel(final Parcel in) {
            return new TaskItemElement(in);
        }

        @Override
        public TaskItemElement[] newArray(final int size) {
            return new TaskItemElement[size];
        }
    };

    public TaskItemElement(Task task) {
        this(task.id, task.title, task.period, task.isDone, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE);
    }

    public TaskItemElement(Task task, int itemType) {
        this(task.id, task.title, task.period, task.isDone, itemType);
    }

    public TaskItemElement(String id,String taskTitle, Period period, boolean isDone, int viewType) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.period = period;
        this.isDone = isDone;
        this.viewType = viewType;
    }

    public TaskItemElement(int id, String taskTitle, Period period, boolean isDone, int viewType) {
        this(String.valueOf(id), taskTitle, period, isDone, viewType);
    }

    public TaskItemElement(String taskTitle, Period period, boolean isDone, int viewType) {
        this(createIdFromDateTime(), taskTitle, period, isDone, viewType);
    }

    private static String createIdFromDateTime() { // nie do końca mi się to podoba
        return LocalDateTime.now(ZoneId.of("Z")).format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
    }

    public TaskItemElement(Parcel in) {
        this.id = in.readString();
        this.viewType = in.readInt();
        this.taskTitle = in.readString();
        this.period = Period.valueOf(in.readString());
        this.isDone = in.readBoolean();
    }


    @Override
    public int getViewType() {
        return this.viewType;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(CharSequence taskTitle) {
        this.taskTitle = taskTitle.toString();
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.viewType);
        dest.writeString(this.taskTitle);
        dest.writeString(this.period.name());
        dest.writeBoolean(this.isDone);
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskItemElement that = (TaskItemElement) o;
        return id == that.id &&
                isDone == that.isDone &&
                viewType == that.viewType &&
                taskTitle.equals(that.taskTitle) &&
                period == that.period;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskTitle, period, isDone, viewType);
    }

    @Override
    public String toString() {
        return "TaskItemElement{" +
                "id=" + id + '\'' +
                "taskTitle='" + taskTitle + '\'' +
                ", period=" + period +
                ", isDone=" + isDone +
                ", viewType=" + viewType +
                '}';
    }

    public Period getPeriod() {
        return this.period;
    }

    public TaskItemElement cloneWithViewType(int viewType) {
        return new TaskItemElement(id, taskTitle, period, isDone, viewType);
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
